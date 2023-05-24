package net.thenextlvl.npc.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.inventory.InventoryHolder;

/**
 * An interface that represents a npc
 */
public interface NPC extends InventoryHolder, Cloneable {

    /**
     * Get the player profile of the npc
     *
     * @return the player profile of the npc
     */
    PlayerProfile getPlayerProfile();

    /**
     * Get the location of the npc
     *
     * @return the current location the npc is
     */
    Location getLocation();

    /**
     * Set the location of the npc
     *
     * @param location the location the npc should appear
     */
    void setLocation(Location location);

    /**
     * Get the display name of the npc
     *
     * @return the current display name of the npc
     */
    Component getDisplayName();

    /**
     * Set the display name of the npc
     *
     * @param displayName the new name of the npc
     */
    void setDisplayName(Component displayName);
}
