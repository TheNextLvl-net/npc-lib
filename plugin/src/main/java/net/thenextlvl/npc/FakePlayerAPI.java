package net.thenextlvl.npc;

import net.thenextlvl.npc.api.NPCProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class FakePlayerAPI extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getServicesManager().register(NPCProvider.class, getNPCProvider(), this, ServicePriority.Normal);
    }

    private NPCProvider getNPCProvider() {
        var version = Bukkit.getBukkitVersion();
        if (version.contains("1.19.4"))
            return new net.thenextlvl.npc.v1_19_R3.CraftNPCProvider(this);
        throw new IllegalStateException("Your server version is not supported: " + version);
    }

    @Override
    public void onDisable() {
        Bukkit.getServicesManager().unregisterAll(this);
    }
}
