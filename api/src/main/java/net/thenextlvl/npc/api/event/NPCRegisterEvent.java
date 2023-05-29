package net.thenextlvl.npc.api.event;

import lombok.Getter;
import lombok.Setter;
import net.thenextlvl.npc.api.NPC;
import org.bukkit.event.Cancellable;

@Getter
@Setter
public class NPCRegisterEvent extends NPCEvent implements Cancellable {
    private boolean cancelled;

    public NPCRegisterEvent(NPC npc) {
        super(npc);
    }
}
