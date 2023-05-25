package net.thenextlvl.npc.v1_19_R3;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCFactory;
import net.thenextlvl.npc.api.skin.SkinParts;
import org.bukkit.Location;

public class CraftNPCFactory implements NPCFactory {
    @Override
    public NPC createNPC(Location location, PlayerProfile profile) {
        return createNPC(location, profile, profile.getName() != null
                ? Component.text(profile.getName())
                : Component.empty());
    }

    @Override
    public NPC createNPC(Location location, PlayerProfile profile, Component displayName) {
        return new CraftNPC(location.clone(), profile, displayName, new CraftEquipment(), SkinParts.DEFAULT.clone());
    }
}
