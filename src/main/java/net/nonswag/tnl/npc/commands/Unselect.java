package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.npc.api.exceptions.InvalidSelectionException;
import net.nonswag.tnl.npc.api.manager.NPCManager;
import net.nonswag.tnl.npc.api.messages.Messages;

import javax.annotation.Nonnull;

class Unselect extends PlayerSubCommand {

    Unselect() {
        super("unselect");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        TNLPlayer player = (TNLPlayer) invocation.source();
        if (player.getManager(NPCManager.class).getSelection() != null) {
            player.getManager(NPCManager.class).setSelection(null);
            player.messenger().sendMessage(Messages.UNSELECTED);
        } else throw new InvalidSelectionException();
    }
}
