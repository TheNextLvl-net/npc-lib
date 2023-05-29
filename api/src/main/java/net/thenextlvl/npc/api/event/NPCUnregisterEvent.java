package net.thenextlvl.npc.api.event;

import lombok.Getter;
import lombok.Setter;
import net.thenextlvl.npc.api.NPC;
import org.bukkit.event.Cancellable;

@Getter
@Setter
public class NPCUnregisterEvent extends NPCEvent implements Cancellable {
    private boolean cancelled;

    public NPCUnregisterEvent(NPC npc) {
        super(npc);
    }
}
