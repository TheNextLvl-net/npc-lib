package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.npc.api.config.Storage;
import net.nonswag.tnl.npc.api.exceptions.InvalidSelectionException;
import net.nonswag.tnl.npc.api.manager.NPCManager;
import net.nonswag.tnl.npc.api.messages.Messages;

class Delete extends PlayerSubCommand {

    Delete() {
        super("delete");
    }

    @Override
    protected void execute(Invocation invocation) {
        TNLPlayer player = (TNLPlayer) invocation.source();
        NPCManager manager = player.getManager(NPCManager.class);
        if (manager.getSelection() == null) throw new InvalidSelectionException();
        Storage.NPCs.remove(manager.getSelection().unregister());
        player.messenger().sendMessage(Messages.DELETED);
        manager.setSelection(null);
    }
}
