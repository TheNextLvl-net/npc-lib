package net.thenextlvl.npc.v1_19_R3;

import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.thenextlvl.npc.api.Equipment;
import net.thenextlvl.npc.api.NPC;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor
public class CraftNPC implements NPC {
    private Location location;
    private PlayerProfile playerProfile;
    private Component displayName;
    private Equipment equipment;

    @Override
    public NPC clone() {
        try {
            var clone = (CraftNPC) super.clone();
            clone.playerProfile = (PlayerProfile) playerProfile.clone();
            clone.equipment = equipment.clone();
            clone.location = location.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
