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
     * Loads the npc for the specified player
     *
     * @param npc the npc to load
     * @param player the player to load the npc for
     * @param location the location to perform the checks at
     *
     * @throws IllegalArgumentException thrown if the npc is
     * {@link NPCLoader#isLoaded(NPC, Player) already loaded} or not
     * {@link NPCLoader#canSee(Player, NPC) visible} to the player
     *
     * @throws NullPointerException thrown if the
     * {@link Location#getWorld() world} of the npc is null
     */
    void load(NPC npc, Player player, Location location) throws IllegalArgumentException, NullPointerException;

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
    void update(NPC npc, Player player) throws IllegalStateException, NullPointerException;

    /**
     * Add the npc to the tablist of a certain player
     *
     * @param npc the npc to add
     * @param player the player
     * @see NPCLoader#isTablistNameHidden(NPC, Player)
     * @see NPCLoader#hideTablistName(NPC, Player)
     * @throws IllegalStateException thrown if the npc is not
     * {@link NPCLoader#isLoaded(NPC, Player) loaded} or the tablist name is not
     * {@link NPCLoader#isTablistNameHidden(NPC, Player) hidden} from the player
     */
    void showTablistName(NPC npc, Player player) throws IllegalStateException;

    /**
     * Remove the tablist name of the npc for the specified player
     *
     * @param npc the npc
     * @param player the player
     * @throws IllegalStateException thrown if the npc was already removed from the tablist
     * @apiNote This can have the side effect of removing the skin of the npc
     * @see NPCLoader#isTablistNameHidden(NPC, Player)
     * @see NPCLoader#showTablistName(NPC, Player)
     * @throws IllegalStateException thrown if the npc is not
     * {@link NPCLoader#isLoaded(NPC, Player) loaded} or the tablist name is already
     * {@link NPCLoader#isTablistNameHidden(NPC, Player) hidden} from the player
     */
    void hideTablistName(NPC npc, Player player) throws IllegalStateException;

    /**
     * Checks if the npc was removed from the tablist of the player
     *
     * @param npc the npc
     * @param player the player
     * @return true if the npc was removed from the player's tablist
     */
    boolean isTablistNameHidden(NPC npc, Player player);

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
     * Checks if the npc can be seen at the given location
     *
     * @param location the location
     * @param npc the npc
     * @return true if the npc can be seen at the location
     */
    boolean canSee(Location location, NPC npc);

    /**
     * All the npcs that are currently loaded for the player
     *
     * @param player the player
     * @return the npcs currently loaded for the player
     */
    Collection<? extends NPC> getNPCs(Player player);
}
