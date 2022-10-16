package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;

import javax.annotation.Nonnull;

class Select extends PlayerSubCommand {

    Select() {
        super("select");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
    }
}
