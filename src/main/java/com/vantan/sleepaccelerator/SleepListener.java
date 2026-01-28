package com.vantan.sleepaccelerator;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class SleepListener implements Listener {

    private final SleepAccelerator plugin;
    private final Set<Player> sleepingPlayers = new HashSet<>();
    private BukkitRunnable nightSkipper;

    public SleepListener(SleepAccelerator plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        sleepingPlayers.add(event.getPlayer());
        startSkippingNight();
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        sleepingPlayers.remove(event.getPlayer());

        if (sleepingPlayers.isEmpty() && nightSkipper != null) {
            nightSkipper.cancel();
            nightSkipper = null;
        }
    }

    private void startSkippingNight() {
        if (nightSkipper != null) return;

        nightSkipper = new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getWorlds().get(0); // основной мир
                int sleeping = sleepingPlayers.size();

                if (sleeping == 0) {
                    this.cancel();
                    nightSkipper = null;
                    return;
                }

                long time = world.getTime();
                long skipAmount = 2L * sleeping * sleeping; // ускорение увеличивается с квадратом количества

                if (time >= 12541 && time <= 23460) { // ночь в Minecraft
                    world.setTime(time + skipAmount);
                }

                String message = "§b夜晚加速倍率: §e" + (sleeping * sleeping) + "x";
                for (Player player : sleepingPlayers) {
                    player.sendActionBar(Component.text(message));
                }
            }
        };

        nightSkipper.runTaskTimer(plugin, 0L, 2L); // каждые 2 тика
    }
}
