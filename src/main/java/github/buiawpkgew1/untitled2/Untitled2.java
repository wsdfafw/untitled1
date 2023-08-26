package github.buiawpkgew1.untitled2;

import org.bukkit.plugin.java.JavaPlugin;

public final class Untitled2 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new RandomTeleportOnDeath(), this);
        getLogger().info("RandomTeleportPlugin 已启用！");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("RandomTeleportPlugin 已禁用！");
    }
}
