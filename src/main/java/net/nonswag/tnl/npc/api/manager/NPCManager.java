package net.nonswag.tnl.npc.api.manager;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.player.manager.Manager;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;

import javax.annotation.Nullable;

@Getter
@Setter
public abstract class NPCManager extends Manager {
    @Nullable
    private FakePlayer selection = null;
}
