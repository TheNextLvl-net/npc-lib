package net.thenextlvl.npc.api;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.thenextlvl.hologram.api.Hologram;
import net.thenextlvl.npc.api.equipment.Equipment;
import net.thenextlvl.npc.api.skin.Skin;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

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
     * Get the nametag of the npc
     *
     * @return the hologram nametag of the npc
     */
    Hologram getNameTag();

    /**
     * Get the skin of the npc
     *
     * @return the current skin of the npc
     */
    Skin getSkin();

    /**
     * Set the skin of the npc
     *
     * @param skin the new skin of the npc
     */
    void setSkin(Skin skin);

    /**
     * Get the equipment of the npc
     *
     * @return the equipment of the npc
     */
    Equipment getEquipment();

    /**
     * Get the loading range of the npc
     *
     * @return the loading range of the npc
     */
    int getLoadingRange();

    /**
     * Get the interaction
     *
     * @return the interaction
     */
    @Nullable Interaction getInteraction();

    /**
     * Set the interaction
     *
     * @param interaction the interaction
     */
    void setInteraction(@Nullable Interaction interaction);

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
