package net.thenextlvl.npc.api.equipment;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Equipment extends Cloneable {

    /**
     * Get all slots and items
     *
     * @return a map of all slots and items
     */
    Map<EquipmentSlot, ItemStack> getItems();

    /**
     * Get the item from a certain slot
     *
     * @param slot the slot to get the item from
     * @return the item on the slot
     */
    @Nullable ItemStack getItem(EquipmentSlot slot);

    /**
     * Set an item on a certain slot
     *
     * @param slot      the slot to change
     * @param itemStack the item to set
     */
    void setItem(EquipmentSlot slot, ItemStack itemStack);

    /**
     * Remove the item from a certain slot
     *
     * @param slot the slot to remove the item from
     */
    void removeItem(EquipmentSlot slot);

    /**
     * Get the amount of all items
     *
     * @return the amount of all items
     */
    int getSize();

    /**
     * Creates a copy of this equipment object
     *
     * @return the clone of this equipment object
     */
    Equipment clone();
}
