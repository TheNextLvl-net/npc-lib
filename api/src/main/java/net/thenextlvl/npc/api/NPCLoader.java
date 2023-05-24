package net.thenextlvl.npc.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * A loader class that handles loading, unloading and updating of NPCs
 */
public interface NPCLoader {
    /**
     * Loads the npc for the specified player
     *
     * @param npc the npc to load
     * @param player the player to load the npc for
     *
     * @throws IllegalArgumentException thrown if the npc is
     * {@link NPCLoader#isLoaded(NPC, Player) already loaded} or not
     * {@link NPCLoader#canSee(Player, NPC) visible} to the player
     *
     * @throws NullPointerException thrown if the
     * {@link Location#getWorld() world} of the npc is null
     */
    void load(NPC npc, Player player) throws IllegalArgumentException, NullPointerException;

    /**
     * Unloads the npc for the specified player
     *
     * @param npc the npc to unload
     * @param player the player to unload the npc for
     *
     * @throws IllegalArgumentException thrown if the npc is not
     * {@link NPCLoader#isLoaded(NPC, Player) loaded}
     */
    void unload(NPC npc, Player player) throws IllegalArgumentException;

    /**
     * Updates the npc for the specified player
     *
     * @param npc the npc to update
     * @param player the player to update the npc for
     *
     * @throws IllegalArgumentException thrown if the npc is
     * {@link NPCLoader#isLoaded(NPC, Player) already loaded} or not
     * {@link NPCLoader#canSee(Player, NPC) visible} to the player
     *
     * @throws NullPointerException thrown if the
     * {@link Location#getWorld() world} of the npc is null
     */
    void update(NPC npc, Player player) throws IllegalArgumentException, NullPointerException;

    /**
     * Checks if the npc is loaded for the player
     *
     * @param npc the npc
     * @param player the player
     * @return true if the npc is loaded for the player
     */
    boolean isLoaded(NPC npc, Player player);

    /**
     * Checks if the player could possibly see the npc
     *
     * @param player the player
     * @param npc the npc
     * @return true if the npc can be seen by the player
     */
    boolean canSee(Player player, NPC npc);

    /**
     * All the npcs that are currently loaded for the player
     *
     * @param player the player
     * @return the npcs currently loaded for the player
     */
    Collection<? extends NPC> getNPCs(Player player);
}
