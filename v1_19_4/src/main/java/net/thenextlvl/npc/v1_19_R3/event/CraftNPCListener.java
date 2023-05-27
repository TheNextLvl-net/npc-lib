package net.thenextlvl.npc.v1_19_R3.event;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import lombok.RequiredArgsConstructor;
import net.thenextlvl.npc.v1_19_R3.CraftNPCProvider;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

@RequiredArgsConstructor
public class CraftNPCListener implements Listener {
    private final CraftNPCProvider provider;

    @EventHandler
    public void onInteract(PlayerUseUnknownEntityEvent event) {
        var npcs = provider.getNPCLoader().getNPCs(event.getPlayer());
        var npc = npcs.stream().filter(all -> all.getEntityId() == event.getEntityId()).findFirst();
        npc.ifPresent(value -> value.onInteract().accept(event.isAttack(), event.getHand(), event.getPlayer()));
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
        reloadNPCs(event.getPlayer(), event.getPlayer().getLocation());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        reloadNPCs(event.getPlayer(), event.getRespawnLocation());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        reloadNPCs(event.getPlayer(), event.getTo());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(PlayerTeleportEvent event) {
        reloadNPCs(event.getPlayer(), event.getTo());
    }

    private void reloadNPCs(Player player, Location location) {
        unloadNPCs(player);
        loadNPCs(player, location);
    }

    private void loadNPCs(Player player, Location location) {
        var loader = provider.getNPCLoader();
        provider.getNPCRegistry().getNPCs().stream()
                .filter(npc -> loader.canSee(location, npc))
                .forEach(npc -> loader.load(npc, player, location));
    }

    private void unloadNPCs(Player player) {
        var loader = provider.getNPCLoader();
        loader.getNPCs(player).forEach(npc ->
                loader.unload(npc, player));
    }
}
