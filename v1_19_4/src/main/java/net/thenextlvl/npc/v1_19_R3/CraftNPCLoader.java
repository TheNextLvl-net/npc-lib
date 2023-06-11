package net.thenextlvl.npc.v1_19_R3;

import com.google.common.base.Preconditions;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.Unpooled;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.thenextlvl.hologram.api.HologramProvider;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCLoader;
import net.thenextlvl.npc.api.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

import static net.minecraft.network.protocol.game.ClientboundAnimatePacket.SWING_MAIN_HAND;
import static net.minecraft.world.entity.player.Player.DATA_PLAYER_MODE_CUSTOMISATION;

public class CraftNPCLoader implements NPCLoader {
    private final ClientsideNPCLoader loader = new ClientsideNPCLoader(new NPCCache());
    private static final @Nullable HologramProvider provider;

    static {
        var registration = Bukkit.getServicesManager().getRegistration(HologramProvider.class);
        if (registration == null) provider = null;
        else provider = registration.getProvider();
    }

    @Override
    public void load(NPC npc, Player player) throws IllegalArgumentException, NullPointerException {
        load(npc, player, player.getLocation());
    }

    @Override
    public void load(NPC npc, Player player, Location location) throws IllegalArgumentException, NullPointerException {
        Preconditions.checkArgument(!isLoaded(npc, player), "NPC is already loaded");
        Preconditions.checkArgument(canSee(location, npc), "NPC can't be seen by the player");
        Preconditions.checkNotNull(npc.getLocation().getWorld(), "World can't be null");
        loader.load((CraftNPC) npc, (CraftPlayer) player);
    }

    @Override
    public void unload(NPC npc, Player player) throws IllegalArgumentException {
        Preconditions.checkArgument(isLoaded(npc, player), "NPC is not loaded");
        loader.unload((CraftNPC) npc, (CraftPlayer) player);
    }

    @Override
    public void update(NPC npc, Player player) throws IllegalArgumentException, NullPointerException {
        unload(npc, player);
        load(npc, player);
    }

    @Override
    public void showTablistName(NPC npc, Player player) throws IllegalStateException {
        Preconditions.checkState(isLoaded(npc, player), "NPC is not loaded");
        Preconditions.checkState(isTablistNameHidden(npc, player), "Tablist name is already shown");
        loader.showTablistName(((CraftNPC) npc), ((CraftPlayer) player), false);
    }

    @Override
    public void hideTablistName(NPC npc, Player player) {
        Preconditions.checkState(isLoaded(npc, player), "NPC is not loaded");
        Preconditions.checkState(!isTablistNameHidden(npc, player), "Tablist name is already hidden");
        loader.hideTablistName((CraftNPC) npc, (CraftPlayer) player);
    }

    @Override
    public boolean isTablistNameHidden(NPC npc, Player player) {
        var npcs = loader.cache().get(player);
        return npcs != null && npcs.containsKey((CraftNPC) npc);
    }

    @Override
    public boolean isLoaded(NPC npc, Player player) {
        var npcs = loader.cache().get(player);
        return npcs != null && npcs.contains((CraftNPC) npc);
    }

    @Override
    public boolean canSee(Player player, NPC npc) {
        return canSee(player.getLocation(), npc);
    }

    @Override
    public boolean canSee(Location location, NPC npc) {
        var rangeSquared = npc.getLoadingRange() * npc.getLoadingRange();
        return location.getWorld().equals(npc.getLocation().getWorld())
                && npc.getLocation().distanceSquared(location) <= rangeSquared;
    }

    @Override
    public Collection<? extends NPC> getNPCs(Player player) {
        return new ArrayList<>(loader.cache().getNPCs(player));
    }

    private record ClientsideNPCLoader(NPCCache cache) {

        private void load(CraftNPC npc, CraftPlayer player) {
            var equipment = getEquipment(npc);
            var connection = player.getHandle().connection;
            if (npc.getSkin() != null) updateServerPlayer(npc, npc.getSkin());
            connection.send(ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of(npc.getPlayer())));
            connection.send(createAddPlayerPacket(npc));
            npc.getPlayer().getEntityData().refresh(player.getHandle());
            if (!equipment.isEmpty()) connection.send(new ClientboundSetEquipmentPacket(npc.getEntityId(), equipment));
            byte yaw = (byte) ((int) (npc.getLocation().getYaw() % 360) * 256 / 360);
            connection.send(new ClientboundRotateHeadPacket(npc.getPlayer(), yaw));
            connection.send(new ClientboundAnimatePacket(npc.getPlayer(), SWING_MAIN_HAND));
            handleScoreboards(player, npc);
            showNameTag(player, npc);
            cache.addNPC(player, npc);
        }

        private void updateServerPlayer(CraftNPC npc, Skin skin) {
            npc.getPlayer().getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, (byte) skin.parts().getRaw());
            npc.getPlayer().getGameProfile().getProperties().removeAll("textures");
            npc.getPlayer().getGameProfile().getProperties().put("textures",
                    new Property("textures", skin.value(), skin.signature()));
        }

        @NotNull
        private static List<Pair<EquipmentSlot, ItemStack>> getEquipment(CraftNPC npc) {
            var equipment = new ArrayList<Pair<EquipmentSlot, ItemStack>>(npc.getEquipment().getSize());
            npc.getEquipment().getItems().forEach((slot, itemStack) ->
                    equipment.add(new Pair<>(switch (slot) {
                        case HAND -> EquipmentSlot.MAINHAND;
                        case OFF_HAND -> EquipmentSlot.OFFHAND;
                        case FEET -> EquipmentSlot.FEET;
                        case LEGS -> EquipmentSlot.LEGS;
                        case CHEST -> EquipmentSlot.CHEST;
                        case HEAD -> EquipmentSlot.HEAD;
                    }, CraftItemStack.asNMSCopy(itemStack))));
            return equipment;
        }

        private static void handleScoreboards(Player player, CraftNPC npc) {
            if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            var team = player.getScoreboard().getTeam("npc-lib");
            if (team == null) team = player.getScoreboard().registerNewTeam("npc-lib");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            team.color(NamedTextColor.DARK_GRAY);
            team.addEntry(npc.getPlayer().getGameProfile().getName());
        }

        private static Packet<?> createAddPlayerPacket(CraftNPC npc) {
            var buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeVarInt(npc.getEntityId());
            buf.writeUUID(npc.getPlayer().getGameProfile().getId());
            buf.writeDouble(npc.getLocation().getX());
            buf.writeDouble(npc.getLocation().getY());
            buf.writeDouble(npc.getLocation().getZ());
            buf.writeByte((byte) ((int) npc.getLocation().getYaw() * 256F / 360F));
            buf.writeByte((byte) ((int) npc.getLocation().getPitch() * 256F / 360F));
            return new ClientboundAddPlayerPacket(buf);
        }

        private void unload(CraftNPC npc, CraftPlayer player) {
            var connection = player.getHandle().connection;
            connection.send(new ClientboundRemoveEntitiesPacket(npc.getEntityId()));
            connection.send(new ClientboundPlayerInfoRemovePacket(List.of(npc.getPlayer().getUUID())));
            hideNameTag(player, npc);
            cache.removeNPC(player, npc);
        }

        public void showTablistName(CraftNPC npc, CraftPlayer player, boolean initial) {
            var connection = player.getHandle().connection;
            connection.send(ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of(npc.getPlayer())));
            if (!initial) cache().setTablistNameHidden(player, npc, false);
        }

        public void hideTablistName(CraftNPC npc, CraftPlayer player) {
            var connection = player.getHandle().connection;
            connection.send(new ClientboundPlayerInfoRemovePacket(List.of(npc.getPlayer().getUUID())));
            cache().setTablistNameHidden(player, npc, true);
        }

        private void showNameTag(Player player, CraftNPC npc) {
            var tag = npc.getNameTag();
            if (tag == null || provider == null) return;
            if (provider.getHologramLoader().isLoaded(tag, player)) return;
            if (!provider.getHologramLoader().canSee(player, tag)) return;
            provider.getHologramLoader().load(tag, player);
        }

        private void hideNameTag(CraftPlayer player, CraftNPC npc) {
            var tag = npc.getNameTag();
            if (tag == null || provider == null) return;
            if (!provider.getHologramLoader().isLoaded(tag, player)) return;
            provider.getHologramLoader().unload(tag, player);
        }
    }

    private static class NPCCache extends WeakHashMap<Player, Collection<CraftNPC>> {

        private Collection<CraftNPC> getNPCs(Player player) {
            return getOrDefault(player, new ArrayList<>());
        }

        private void addNPC(Player player, CraftNPC npc) {
            var npcs = getNPCs(player);
            npcs.add(npc);
            put(player, npcs);
        }

        private void removeNPC(Player player, CraftNPC npc) {
            getNPCs(player).remove(npc);
        }

        private void setTablistNameHidden(Player player, CraftNPC npc, boolean hidden) {
            var npcs = getNPCs(player);
            if (!npcs.containsKey(npc)) return;
            npcs.put(npc, hidden);
            put(player, npcs);
        }
    }
}
