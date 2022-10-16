package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.npc.api.config.Storage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Create extends PlayerSubCommand {

    Create() {
        super("create");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        String[] args = invocation.arguments();
        TNLPlayer player = (TNLPlayer) invocation.source();
        String name = "";
        if (args.length >= 2) name = String.join(" ", Arrays.asList(args).subList(1, args.length));
        Storage.NPCs.add(new FakePlayer(name, player.worldManager().getLocation()).register());
    }

    @Override
    public void usage(@Nonnull Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/npc create §8(§6Name§8)");
    }

    @Nonnull
    @Override
    protected List<String> suggest(@Nonnull Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        Placeholder.Registry.placeholders().forEach(placeholder -> suggestions.add("%%%s%%".formatted(placeholder.placeholder())));
        return suggestions;
    }
}
