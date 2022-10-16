package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.npc.api.config.Storage;
import net.nonswag.tnl.npc.api.exceptions.InvalidSelectionException;
import net.nonswag.tnl.npc.api.manager.NPCManager;

import javax.annotation.Nonnull;

class Delete extends PlayerSubCommand {

    Delete() {
        super("delete");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        TNLPlayer player = (TNLPlayer) invocation.source();
        NPCManager manager = player.getManager(NPCManager.class);
        if (manager.getSelection() == null) throw new InvalidSelectionException();
        Storage.NPCs.remove(manager.getSelection().unregister());
    }
}
