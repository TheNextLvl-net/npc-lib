package net.nonswag.tnl.npc.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.PlayerSubCommand;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.npc.api.exceptions.InvalidSelectionException;
import net.nonswag.tnl.npc.api.manager.NPCManager;
import net.nonswag.tnl.npc.api.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

class Skin extends PlayerSubCommand {

    Skin() {
        super("skin");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Invocation invocation) {
        TNLPlayer player = (TNLPlayer) invocation.source();
        String[] args = invocation.arguments();
        if (args.length <= 1) throw new InvalidUseException(this);
        FakePlayer selection = player.getManager(NPCManager.class).getSelection();
        if (selection == null) throw new InvalidSelectionException();
        TNLEntityPlayer entityPlayer = selection.getPlayer();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        var skin = net.nonswag.tnl.listener.api.player.Skin.getSkin(target.getUniqueId());
        player.messenger().sendMessage(Messages.CHANGED_SKIN, new Placeholder("skin", args[1]));
        entityPlayer.getGameProfile().setSkin(skin);
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/npc skin §8[§6Player§8]");
    }
}
