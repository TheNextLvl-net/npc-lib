package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.npc.api.exceptions.InvalidSelectionException;
import net.nonswag.tnl.npc.api.manager.NPCManager;
import net.nonswag.tnl.npc.api.messages.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Rename extends PlayerSubCommand {

    Rename() {
        super("rename");
    }

    @Override
    protected void execute(Invocation invocation) {
        TNLPlayer player = (TNLPlayer) invocation.source();
        NPCManager manager = player.getManager(NPCManager.class);
        if (manager.getSelection() == null) throw new InvalidSelectionException();
        if (invocation.arguments().length <= 1) throw new InvalidUseException(this);
        manager.getSelection().setName(String.join(" ", Arrays.asList(invocation.arguments()).subList(1, invocation.arguments().length)));
        player.messenger().sendMessage(Messages.RENAMED, new Placeholder("name", name(player, manager.getSelection())));
    }

    @Override
    protected List<String> suggest(Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        Placeholder.Registry.placeholders().forEach(placeholder -> suggestions.add("%%%s%%".formatted(placeholder.placeholder())));
        return suggestions;
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/npc rename §8[§6Name§8]");
    }

    private String name(TNLPlayer player, FakePlayer fakePlayer) {
        String name = Message.format(fakePlayer.getName(), player);
        return name.length() > 10 ? name.substring(0, 10) + "..." : name;
    }
}
