package net.thenextlvl.npc.v1_20_R2;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCRegistry;
import net.thenextlvl.npc.api.event.NPCRegisterEvent;
import net.thenextlvl.npc.api.event.NPCUnregisterEvent;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CraftNPCRegistry implements NPCRegistry {
    private final Collection<NPC> nPCs = new ArrayList<>();

    @Override
    public void register(NPC npc) throws IllegalStateException {
        Preconditions.checkState(!isRegistered(npc), "NPC already registered");
        if (new NPCRegisterEvent(npc).callEvent()) nPCs.add(npc);
    }

    @Override
    public void unregister(NPC npc) throws IllegalStateException {
        Preconditions.checkState(isRegistered(npc), "NPC not registered");
        if (new NPCUnregisterEvent(npc).callEvent()) nPCs.remove(npc);
    }

    @Override
    public boolean isRegistered(NPC npc) {
        return nPCs.contains(npc);
    }
}
