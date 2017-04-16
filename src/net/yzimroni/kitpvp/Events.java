package net.yzimroni.kitpvp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Events implements Listener {

	private KitPvPPlugin plugin;
	
	public Events(KitPvPPlugin p) {
		plugin = p;
	}
	
	public void disable() {
		plugin = null;
	}
	
	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		e.setRespawnLocation(plugin.getLobby().getLocation());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerAfterRespawn(PlayerRespawnEvent e) {
		plugin.getLobby().teleport(e.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		e.setDroppedExp(0);
		e.setKeepInventory(false);
		e.setKeepLevel(false);
		e.setNewExp(0);
		e.setNewLevel(0);
		e.setNewTotalExp(0);
		e.getDrops().clear();
	}
	
}
