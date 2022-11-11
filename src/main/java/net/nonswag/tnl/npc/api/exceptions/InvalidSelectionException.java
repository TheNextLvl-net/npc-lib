package net.nonswag.tnl.npc.api.exceptions;

import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.exceptions.CommandException;
import net.nonswag.tnl.npc.api.messages.Messages;

@FieldsAreNonnullByDefault
public class InvalidSelectionException extends CommandException {

    @Override
    public void handle(Invocation invocation) {
        invocation.source().sendMessage(Messages.INVALID_SELECTION);
    }
}
