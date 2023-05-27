package net.thenextlvl.npc.v1_19_R3;

import com.destroystokyo.paper.SkinParts;
import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import core.api.function.TriConsumer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.thenextlvl.npc.api.Equipment;
import net.thenextlvl.npc.api.NPC;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftNPC implements NPC {
    private Location location;
    private PlayerProfile profile;
    private Component displayName;
    private Equipment equipment;
    private SkinParts skinParts;
    @Accessors(fluent = true, chain = false)
    private @Nullable TriConsumer<Boolean, EquipmentSlot, Player> onInteract;
    private final ServerPlayer player;

    public CraftNPC(Location location, PlayerProfile profile, Component displayName, Equipment equipment, SkinParts skinParts) {
        this(location, profile, displayName, equipment, skinParts, null, new ServerPlayer(
                MinecraftServer.getServer(),
                ((CraftWorld) location.getWorld()).getHandle(),
                ((CraftPlayerProfile) profile).getGameProfile()
        ));
    }

    @Override
    public int getEntityId() {
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
            clone.skinParts = new net.thenextlvl.npc.api.skin.SkinParts(skinParts.getRaw());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
