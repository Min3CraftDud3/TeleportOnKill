package com.min3craftdud3.ToK;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ToK extends JavaPlugin implements Listener {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onGSKill(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p1 = (Player) e.getEntity();
			if (p1.getKiller() instanceof Player) {
				final Player p2 = (Player) p1.getKiller();
				if (p2.getItemInHand().getType() == Material.GOLD_SWORD) {
					new BukkitRunnable() {
						int seconds = 5;

						public void run() {
							p2.sendMessage(seconds + ".");
							seconds--;
							if (seconds <= 0) {
								p2.teleport(getClosestPlayer(p2, 200.0));
								cancel();
							}
						}
					}.runTaskTimer(this, 0, 20);
				}
			}
		}
	}

	public Player getClosestPlayer(Player player, double radius) {
		double minimalDistance = Math.pow(radius, 2);
		double curDist;
		Player closest = null;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.equals(player)) {
				curDist = player.getLocation().distanceSquared(p.getLocation());
				if (curDist < minimalDistance) {
					minimalDistance = curDist;
					closest = p;
				}
			}
		}
		return closest;
	}
}
