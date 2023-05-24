package net.thenextlvl.npc.v1_19_R3;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCLoader;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

import static net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action.*;

public class CraftNPCLoader implements NPCLoader {
    private final ClientsideNPCLoader loader = new ClientsideNPCLoader(new NPCCache());

    @Override
    public void load(NPC npc, Player player) throws IllegalArgumentException, NullPointerException {
        Preconditions.checkArgument(!isLoaded(npc, player), "NPC is already loaded");
        Preconditions.checkArgument(canSee(player, npc), "NPC can't be seen by the player");
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
        return player.getWorld().equals(npc.getLocation().getWorld());
    }

    @Override
    public Collection<? extends NPC> getNPCs(Player player) {
        return loader.cache().getNPCs(player);
    }

    private record ClientsideNPCLoader(NPCCache cache) {

        private void load(CraftNPC npc, CraftPlayer player) {
            var connection = player.getHandle().connection;
            var list = npc.getPlayer().getEntityData().packDirty();
            var values = list != null ? list : new ArrayList<SynchedEntityData.DataValue<?>>();
            var equipment = new ArrayList<Pair<EquipmentSlot, ItemStack>>();
            connection.send(new ClientboundPlayerInfoUpdatePacket(ADD_PLAYER, npc.getPlayer()));
            connection.send(new ClientboundPlayerInfoUpdatePacket(UPDATE_LATENCY, npc.getPlayer()));
            connection.send(new ClientboundPlayerInfoUpdatePacket(UPDATE_DISPLAY_NAME, npc.getPlayer()));
            connection.send(new ClientboundAddPlayerPacket(npc.getPlayer()));
            connection.send(new ClientboundSetEntityDataPacket(npc.getId(), values));
            connection.send(new ClientboundSetEquipmentPacket(npc.getId(), equipment));
            connection.send(new ClientboundRotateHeadPacket(npc.getPlayer(), (byte) (360 / npc.getLocation().getYaw())));
            cache.addNPC(player, npc);
        }

        private void unload(CraftNPC npc, CraftPlayer player) {
            var connection = player.getHandle().connection;
            connection.send(new ClientboundRemoveEntitiesPacket(npc.getId()));
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
