package com.vantan;
import org.bukkit.plugin.java.JavaPlugin;

public class SleepAccelerator extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("SleepAccelerator Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SleepAccelerator Disabled.");
    }
}
