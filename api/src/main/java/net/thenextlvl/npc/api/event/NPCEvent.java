package net.thenextlvl.npc.api.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.thenextlvl.npc.api.NPC;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NPCEvent extends Event {
    private static final @Getter HandlerList handlerList = new HandlerList();
    private final NPC nPC;

    @Override
    public @NotNull HandlerList getHandlers() {
        return getHandlerList();
    }
}
