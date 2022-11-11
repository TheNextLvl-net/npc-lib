package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.npc.api.manager.NPCManager;
import net.nonswag.tnl.npc.api.messages.Messages;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

class Select extends PlayerSubCommand {

    Select() {
        super("select");
    }

    @Override
    protected void execute(Invocation invocation) {
        try {
            TNLPlayer player = (TNLPlayer) invocation.source();
            String[] args = invocation.arguments();
            if (args.length <= 1) throw new InvalidUseException(this);
            UUID uuid = UUID.fromString(args[1]);
            FakePlayer fakePlayer = getFakePlayer(player, uuid);
            if (fakePlayer == null) throw new InvalidUseException(this);
            NPCManager manager = player.getManager(NPCManager.class);
            if(!fakePlayer.equals(manager.getSelection())) {
                player.messenger().sendMessage(Messages.SELECTED);
                manager.setSelection(fakePlayer);
            } else player.messenger().sendMessage(Messages.NOTHING_CHANGED);
        } catch (IllegalArgumentException e) {
            throw new InvalidUseException(this);
        }
    }

    @Override
    protected List<String> suggest(Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        Collection<FakePlayer> npcs = ((TNLPlayer) invocation.source()).npcFactory().getFakePlayers();
        npcs.forEach(fakePlayer -> suggestions.add(fakePlayer.getPlayer().getGameProfile().getUniqueId().toString()));
        return suggestions;
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/npc select §8[§6ID§8]");
    }

    @Nullable
    private FakePlayer getFakePlayer(TNLPlayer player, UUID uuid) {
        FakePlayer npc = null;
        for (FakePlayer fakePlayer : player.npcFactory().getFakePlayers()) {
            if (fakePlayer.getPlayer().getGameProfile().getUniqueId().equals(uuid)) npc = fakePlayer;
        }
        return npc;
    }
}
