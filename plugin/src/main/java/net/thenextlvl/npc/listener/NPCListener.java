package net.thenextlvl.npc.listener;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import lombok.RequiredArgsConstructor;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCProvider;
import net.thenextlvl.npc.api.event.NPCRegisterEvent;
import net.thenextlvl.npc.api.event.NPCUnregisterEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.Objects;

@RequiredArgsConstructor
public class NPCListener implements Listener {
    private final NPCProvider provider;

    @EventHandler
    public void onInteract(PlayerUseUnknownEntityEvent event) {
        var npcs = provider.getNPCLoader().getNPCs(event.getPlayer());
        var interaction = npcs.stream()
                .filter(all -> all.getEntityId() == event.getEntityId())
                .map(NPC::getInteraction)
                .filter(Objects::nonNull)
                .findFirst();
        interaction.ifPresent(value -> value.onInteract(event.isAttack(), event.getHand(), event.getPlayer()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        loadNPCs(event.getPlayer(), event.getPlayer().getLocation());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        unloadNPCs(event.getPlayer());
    }

    @EventHandler
    public void onWorldChanged(PlayerChangedWorldEvent event) {
        updateNPCs(event.getPlayer(), event.getPlayer().getLocation());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        updateNPCs(event.getPlayer(), event.getRespawnLocation());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        updateNPCs(event.getPlayer(), event.getTo());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        updateNPCs(event.getPlayer(), event.getTo());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onNPCRegister(NPCRegisterEvent event) {
        var loader = provider.getNPCLoader();
        event.getNPC().getLocation().getWorld().getPlayers().stream()
                .filter(player -> !loader.isLoaded(event.getNPC(), player))
                .filter(player -> loader.canSee(player, event.getNPC()))
                .forEach(player -> loader.load(event.getNPC(), player));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onNPCUnregister(NPCUnregisterEvent event) {
        var loader = provider.getNPCLoader();
        event.getNPC().getLocation().getWorld().getPlayers().stream()
                .filter(player -> loader.isLoaded(event.getNPC(), player))
                .forEach(player -> loader.unload(event.getNPC(), player));
    }

    private void updateNPCs(Player player, Location location) {
        var loader = provider.getNPCLoader();
        loader.getNPCs(player).stream()
                .filter(npc -> {
                    var loadingRange = npc.getLoadingRange() * npc.getLoadingRange();
                    return npc.getLocation().getWorld() != location.getWorld()
                            || npc.getLocation().distanceSquared(location) > loadingRange;
                }).forEach(npc -> loader.unload(npc, player));
        loadNPCs(player, location);
    }

    private void reloadNPCs(Player player, Location location) {
        unloadNPCs(player);
        loadNPCs(player, location);
    }

    private void loadNPCs(Player player, Location location) {
        var loader = provider.getNPCLoader();
        provider.getNPCRegistry().getNPCs().stream()
                .filter(npc -> !loader.isLoaded(npc, player))
                .filter(npc -> loader.canSee(location, npc))
                .forEach(npc -> loader.load(npc, player, location));
    }

    private void unloadNPCs(Player player) {
        var loader = provider.getNPCLoader();
        loader.getNPCs(player).forEach(npc ->
                loader.unload(npc, player));
    }
}
