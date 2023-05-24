package net.thenextlvl.npc.api;

/**
 * A class that provides all necessities for managing NPCs
 */
public interface NPCProvider {
    /**
     * Get the corresponding {@link NPCRegistry}
     *
     * @return the {@link NPCRegistry}
     */
    NPCRegistry getNPCRegistry();

    /**
     * Get the corresponding {@link NPCFactory}
     *
     * @return the {@link NPCFactory}
     */
    NPCFactory getNPCFactory();

    /**
     * Get the corresponding {@link NPCLoader}
     *
     * @return the {@link NPCLoader}
     */
    NPCLoader getNPCLoader();
}
