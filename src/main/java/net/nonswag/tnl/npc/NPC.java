package net.nonswag.tnl.npc;

import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.npc.api.config.Storage;
import net.nonswag.tnl.npc.api.manager.NPCManager;
import net.nonswag.tnl.npc.api.messages.Messages;
import net.nonswag.tnl.npc.commands.NPCCommand;

import javax.annotation.Nonnull;

public class NPC extends TNLPlugin {

    @Override
    public void enable() {
        Storage.loadAll();
        Messages.loadAll();
        getCommandManager().registerCommand(NPCCommand.getInstance());
        getRegistrationManager().registerManager(NPCManager.class, player -> new NPCManager() {
            @Nonnull
            @Override
            public TNLPlayer getPlayer() {
                return player;
            }
        });
    }

    @Override
    public void disable() {
        Storage.exportAll();
        getRegistrationManager().unregisterManager(NPCManager.class);
    }
}