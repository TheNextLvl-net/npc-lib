package net.thenextlvl.npc.v1_19_R3;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.thenextlvl.npc.api.Equipment;
import net.thenextlvl.npc.api.NPC;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftNPC implements NPC {
    private Location location;
    private PlayerProfile profile;
    private Component displayName;
    private Equipment equipment;
    private final ServerPlayer player;

    public CraftNPC(Location location, PlayerProfile profile, Component displayName, Equipment equipment) {
        this(location, profile, displayName, equipment, new ServerPlayer(
                MinecraftServer.getServer(),
                ((CraftWorld) location.getWorld()).getHandle(),
                ((CraftPlayerProfile) profile).getGameProfile()
        ));
    }

    public int getId() {
        return getPlayer().getId();
    }

    @Override
    public int getLoadingRange() {
        return ((CraftWorld) getLocation().getWorld()).getHandle().spigotConfig.displayTrackingRange;
    }

    @Override
    public NPC clone() {
        try {
            var clone = (CraftNPC) super.clone();
            clone.profile = (PlayerProfile) profile.clone();
            clone.equipment = equipment.clone();
            clone.location = location.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
