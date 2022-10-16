package net.nonswag.tnl.npc.commands;

import lombok.Getter;
import net.nonswag.tnl.listener.api.command.simple.SimpleCommand;

import javax.annotation.Nonnull;

public class NPCCommand extends SimpleCommand {

    @Getter
    @Nonnull
    public static final NPCCommand instance = new NPCCommand();

    private NPCCommand() {
        super("npc", "tnl.npc");
        addSubCommand(new Create());
        addSubCommand(new Delete());
        addSubCommand(new Select());
    }
}
