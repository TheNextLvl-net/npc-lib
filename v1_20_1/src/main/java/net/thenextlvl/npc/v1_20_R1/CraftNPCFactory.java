package net.thenextlvl.npc.v1_20_R1;

import com.destroystokyo.paper.profile.PlayerProfile;
import core.util.StringUtil;
import net.kyori.adventure.text.Component;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCFactory;
import net.thenextlvl.npc.v1_20_R1.equipment.CraftEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CraftNPCFactory implements NPCFactory {
    @Override
    public NPC createNPC(Location location) {
        return createNPC(location, Component.empty());
    }

    @Override
    public NPC createNPC(Location location, Component displayName) {
        var name = "[NPC] " + StringUtil.random(10);
        var uuid = new UUID(ThreadLocalRandom.current().nextLong(), 0);
        var profile = Bukkit.createProfile(uuid, name);
        return createNPC(location, profile, displayName);
    }

    @Override
    public NPC createNPC(Location location, PlayerProfile profile) {
        return createNPC(location, profile, profile.getName() != null
                ? Component.text(profile.getName())
                : Component.empty());
    }

    @Override
    public NPC createNPC(Location location, PlayerProfile profile, Component displayName) {
        return new CraftNPC(location.clone(), profile, displayName, new CraftEquipment(), null);
    }
}
