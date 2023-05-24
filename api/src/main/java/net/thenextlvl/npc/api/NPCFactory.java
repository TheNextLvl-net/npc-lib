package net.thenextlvl.npc.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Location;

/**
 * A factory that creates {@link NPC NPCs}
 */
public interface NPCFactory {
    /**
     * Create a new npc object
     *
     * @param location the location of the npc
     * @param profile the profile of the npc
     * @return the new npc
     */
    NPC createNPC(Location location, PlayerProfile profile);
}
