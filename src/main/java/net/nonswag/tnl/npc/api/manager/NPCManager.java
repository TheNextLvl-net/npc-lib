package net.nonswag.tnl.npc.api.manager;

import lombok.Getter;
import net.nonswag.tnl.listener.api.packets.EntityMetadataPacket;
import net.nonswag.tnl.listener.api.player.manager.Manager;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;

import javax.annotation.Nullable;

@Getter
public abstract class NPCManager extends Manager {
    @Nullable
    private FakePlayer selection = null;

    public void setSelection(@Nullable FakePlayer selection) {
        if (this.selection != null) EntityMetadataPacket.create(this.selection.getPlayer().bukkit()).send(getPlayer());
        if ((this.selection = selection) == null) return;
        selection.getPlayer().setGlowing(true);
        EntityMetadataPacket.create(selection.getPlayer().bukkit()).send(getPlayer());
        selection.getPlayer().setGlowing(false);
    }
}
