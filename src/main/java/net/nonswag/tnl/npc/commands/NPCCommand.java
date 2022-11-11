package net.nonswag.tnl.npc.commands;

import lombok.Getter;
import net.nonswag.tnl.listener.api.command.simple.SimpleCommand;

public class NPCCommand extends SimpleCommand {

    @Getter
    public static final NPCCommand instance = new NPCCommand();

    private NPCCommand() {
        super("npc", "tnl.npc");
        addSubCommand(new Skin());
        addSubCommand(new Rename());
        addSubCommand(new Create());
        addSubCommand(new Delete());
        addSubCommand(new Select());
        addSubCommand(new Unselect());
    }
}
