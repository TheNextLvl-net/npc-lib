package net.thenextlvl.npc.v1_19_R3;

import net.thenextlvl.npc.api.Equipment;

public class CraftEquipment implements Equipment {
    @Override
    public Equipment clone() {
        try {
            return (Equipment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
