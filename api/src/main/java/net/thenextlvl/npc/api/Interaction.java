package net.thenextlvl.npc.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public interface Interaction {
    /**
     * @param attack whether the interaction was an attack
     * @param hand   the hand that was used for the interaction
     * @param player the player that interacted
     */
    void onInteract(boolean attack, EquipmentSlot hand, Player player);
}
