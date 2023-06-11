package net.thenextlvl.npc.api;

import java.util.Collection;

/**
 * A registry that holds all registered npcs
 */
public interface NPCRegistry {
    /**
     * Get all the registered npcs
     *
     * @return the registered npcs
     */
    Collection<? extends NPC> getNPCs();

    /**
     * Register a new npcs
     *
     * @param npc the npc to register
     * @throws IllegalStateException thrown if the npc is already registered
     */
    void register(NPC npc) throws IllegalStateException;

    /**
     * Unregister a registered npc
     *
     * @param npc the npc to unregister
     * @throws IllegalStateException thrown if the npc is not registered
     */
    void unregister(NPC npc) throws IllegalStateException;

    /**
     * Checks if a certain npc is registered
     *
     * @param npc the npc
     * @return true if the npc is registered
     */
    boolean isRegistered(NPC npc);
}
