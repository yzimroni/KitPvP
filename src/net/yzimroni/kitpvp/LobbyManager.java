package net.yzimroni.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyManager implements Listener {
	
	/*
	 * command /lobby
	 * scoreboard
	 */

	private KitPvPPlugin plugin;
	
	public LobbyManager(KitPvPPlugin p) {
		plugin = p;
	}
	
	public Location getLocation() {
		return new Location(Bukkit.getWorld("world"), 172, 65, 175); //TODO
	}
	
	public void teleport(Player p) {
		p.teleport(getLocation());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		teleport(e.getPlayer());
	}
	
}
