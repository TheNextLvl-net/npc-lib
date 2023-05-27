package net.thenextlvl.npc.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

/**
 * A factory that creates {@link NPC NPCs}
 */
public interface NPCFactory {
    /**
     * Create a new npc object
     *
     * @param location the location of the npc
     * @return the new npc
     */
    NPC createNPC(Location location);

    /**
     * Create a new npc object with a custom display name
     *
     * @param location    the location of the npc
     * @param displayName the display name of the npc
     * @return the new npc
     */
    NPC createNPC(Location location, Component displayName);

    /**
     * Create a new npc object with a custom profile
     *
     * @param location the location of the npc
     * @param profile  the profile of the npc
     * @return the new npc
     */
    NPC createNPC(Location location, PlayerProfile profile);

    /**
     * Create a new npc object with a custom profile and display name
     *
     * @param location    the location of the npc
     * @param profile     the profile of the npc
     * @param displayName the display name of the npc
     * @return the new npc
     */
    NPC createNPC(Location location, PlayerProfile profile, Component displayName);
}
