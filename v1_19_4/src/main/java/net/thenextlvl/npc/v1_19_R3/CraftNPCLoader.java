package net.thenextlvl.npc.v1_19_R3;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCLoader;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

import static net.minecraft.world.entity.player.Player.DATA_PLAYER_MODE_CUSTOMISATION;

public class CraftNPCLoader implements NPCLoader {
    private final ClientsideNPCLoader loader = new ClientsideNPCLoader(new NPCCache());

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
            var connection = player.getHandle().connection;
            var equipment = new ArrayList<Pair<EquipmentSlot, ItemStack>>();
            npc.getPlayer().getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, (byte) npc.getSkinParts().getRaw());
            connection.send(ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of(npc.getPlayer())));
            connection.send(createAddPlayerPacket(npc));
            npc.getPlayer().getEntityData().refresh(player.getHandle());
            // connection.send(new ClientboundSetEquipmentPacket(npc.getEntityId(), equipment));
            connection.send(new ClientboundRotateHeadPacket(npc.getPlayer(), (byte) npc.getLocation().getYaw()));
            cache.addNPC(player, npc);
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
            cache.removeNPC(player, npc);
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
    }
}
