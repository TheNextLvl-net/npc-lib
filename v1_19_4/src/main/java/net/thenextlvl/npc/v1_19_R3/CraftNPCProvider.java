package net.thenextlvl.npc.v1_19_R3;

import lombok.Getter;
import net.thenextlvl.npc.api.NPCProvider;

@Getter
public class CraftNPCProvider implements NPCProvider {
    private final CraftNPCRegistry nPCRegistry = new CraftNPCRegistry();
    private final CraftNPCFactory nPCFactory = new CraftNPCFactory();
    private final CraftNPCLoader nPCLoader = new CraftNPCLoader();
}
