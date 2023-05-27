package net.thenextlvl.npc.api;

import com.destroystokyo.paper.SkinParts;
import com.destroystokyo.paper.profile.PlayerProfile;
import core.api.function.TriConsumer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

/**
 * An interface that represents a npc
 */
public interface NPC extends Cloneable {

    /**
     * Get the player profile of the npc
     *
     * @return the player profile of the npc
     */
    PlayerProfile getProfile();

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

    /**
     * Get the equipment of the npc
     *
     * @return the equipment of the npc
     */
    Equipment getEquipment();

    /**
     * Get the skin parts of the npc
     *
     * @return the skin parts of the npc
     */
    SkinParts getSkinParts();

    /**
     * Set the skin parts of the npc
     *
     * @param parts the new skin parts of the npc
     */
    void setSkinParts(SkinParts parts);

    /**
     * Get the loading range of the npc
     *
     * @return the loading range of the npc
     */
    int getLoadingRange();

    /**
     * Get the interaction consumer<br>
     * The boolean represents whether the interaction is an attack<br>
     * The equipment slot is the interaction hand
     *
     * @return the interaction consumer
     */
    TriConsumer<Boolean, EquipmentSlot, Player> onInteract();

    /**
     * Set the interaction consumer<br>
     * The boolean represents whether the interaction is an attack<br>
     * The equipment slot is the interaction hand
     *
     * @param consumer the interaction consumer
     */
    void onInteract(TriConsumer<Boolean, EquipmentSlot, Player> consumer);

    /**
     * Get the id of the npc
     *
     * @return the entity id
     */
    int getEntityId();

    /**
     * Creates a copy of this npc object
     *
     * @return the clone of this npc object
     */
    NPC clone();
}
