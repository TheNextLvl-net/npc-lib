package net.nonswag.tnl.npc.api.exceptions;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.exceptions.CommandException;
import net.nonswag.tnl.npc.api.messages.Messages;

import javax.annotation.Nonnull;

public class InvalidSelectionException extends CommandException {

    @Override
    public void handle(@Nonnull Invocation invocation) {
        invocation.source().sendMessage(Messages.INVALID_SELECTION);
    }
}
