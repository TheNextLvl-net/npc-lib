package net.thenextlvl.npc;

import net.thenextlvl.npc.api.NPCProvider;
import net.thenextlvl.npc.listener.NPCListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class FakePlayerAPI extends JavaPlugin {
    @Override
    public void onEnable() {
        var provider = getNPCProvider();
        Bukkit.getServicesManager().register(NPCProvider.class, provider, this, ServicePriority.Normal);
        Bukkit.getPluginManager().registerEvents(new NPCListener(this, provider), this);
    }

    private NPCProvider getNPCProvider() {
        var version = Bukkit.getBukkitVersion();
        if (version.contains("1.19.4"))
            return new net.thenextlvl.npc.v1_19_R3.CraftNPCProvider();
        if (version.contains("1.20.1"))
            return new net.thenextlvl.npc.v1_20_R1.CraftNPCProvider();
        throw new IllegalStateException("Your server version is not supported: " + version);
    }

    @Override
    public void onDisable() {
        Bukkit.getServicesManager().unregisterAll(this);
    }
}
