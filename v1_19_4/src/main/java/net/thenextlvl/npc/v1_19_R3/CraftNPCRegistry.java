package net.thenextlvl.npc.v1_19_R3;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.thenextlvl.npc.api.NPC;
import net.thenextlvl.npc.api.NPCRegistry;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CraftNPCRegistry implements NPCRegistry {
    private final Collection<NPC> nPCs = new ArrayList<>();

    @Override
    public void register(NPC npc) throws IllegalArgumentException {
        Preconditions.checkArgument(!isRegistered(npc), "NPC already registered");
        nPCs.add(npc);
    }

    @Override
    public void unregister(NPC npc) throws IllegalArgumentException {
        Preconditions.checkArgument(isRegistered(npc), "NPC not registered");
        nPCs.remove(npc);
    }

    @Override
    public boolean isRegistered(NPC npc) {
        return nPCs.contains(npc);
    }
}
