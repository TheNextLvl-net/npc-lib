package net.nonswag.tnl.npc.api.manager;

import lombok.Getter;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.tnl.listener.api.packets.outgoing.EntityMetadataPacket;
import net.nonswag.tnl.listener.api.player.manager.Manager;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;

@Getter
@FieldsAreNullableByDefault
@ParametersAreNullableByDefault
public abstract class NPCManager extends Manager {
    private FakePlayer selection = null;

    public void setSelection(@Nullable FakePlayer selection) {
        if (this.selection != null) EntityMetadataPacket.create(this.selection.getPlayer().bukkit()).send(getPlayer());
        if ((this.selection = selection) == null) return;
        selection.getPlayer().setGlowing(true);
        EntityMetadataPacket.create(selection.getPlayer().bukkit()).send(getPlayer());
        selection.getPlayer().setGlowing(false);
    }
}
