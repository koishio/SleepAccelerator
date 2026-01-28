package com.vantan.sleepaccelerator;

import org.bukkit.plugin.java.JavaPlugin;

public class SleepAccelerator extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SleepAccelerator Enabled!");
        getServer().getPluginManager().registerEvents(new SleepListener(this), this); // Регистрируем слушателя событий
    }

    @Override
    public void onDisable() {
        getLogger().info("SleepAccelerator Disabled!");
    }
}
