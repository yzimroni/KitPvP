package net.yzimroni.kitpvp.pvp;

import java.util.HashMap;
import java.util.UUID;

import net.yzimroni.kitpvp.KitPvPPlugin;
import net.yzimroni.kitpvp.kit.kits.Kit;
import net.yzimroni.kitpvp.player.PvPPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PvPManager implements Listener {

	private KitPvPPlugin plugin;
	
	private HashMap<UUID, Integer> playerkits;
	
	public PvPManager(KitPvPPlugin p) {
		plugin = p;
		playerkits = new HashMap<UUID, Integer>();
	}
	
	public void onDisable() {
		playerkits.clear();
		
		playerkits = null;
	}
	
	public int getPlayerKitId(Player p) {
		if (!playerkits.containsKey(p.getUniqueId())) {
			return -1;
		}
		return playerkits.get(p.getUniqueId());
	}
	
	public Kit getPlayerKit(Player p) {
		int id = getPlayerKitId(p);
		if (id == -1) {
			return null;
		}
		return plugin.getKitManager().getKitById(id);
	}
	
	public boolean useKit(Player p, int id) {
		return getPlayerKitId(p) == id;
	}
	
	public boolean useKit(Player p, Kit kit) {
		return getPlayerKit(p).equals(kit);
	}
	
	public boolean isPvP(Player p) {
		return playerkits.containsKey(p.getUniqueId());
	}
	
	public void joinPvP(Player p, Kit k) {
		plugin.getPlayerManager().getPlayer(p).setCurrentKillStreak(0);
		teleport(p);
		playerkits.put(p.getUniqueId(), k.getId());
		k.giveKit(p);
		p.setGameMode(GameMode.ADVENTURE);
		p.sendMessage(ChatColor.GREEN + "You are now " + k.getName() + ", Have fun!");
		
	}
	
	public void exitPvP(Player p) {
		if (isPvP(p)) {
			Kit k = getPlayerKit(p);
			k.takeKit(p);
			playerkits.remove(p.getUniqueId());
			plugin.getPlayerManager().getPlayer(p).setCurrentKillStreak(0);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		exitPvP(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (isPvP(e.getEntity())) {
			String message = ChatColor.RED + e.getEntity().getName() + ChatColor.YELLOW + " died.";
			plugin.getPlayerManager().getPlayer(e.getEntity()).addDeath(1);
			Player killer = e.getEntity().getKiller();
			if (killer != null && !killer.getUniqueId().equals(e.getEntity().getUniqueId())) {
				PvPPlayer kp = plugin.getPlayerManager().getPlayer(killer);
				kp.addKills(1);
				kp.addKillStreak();
				message = ChatColor.RED + e.getEntity().getName() + ChatColor.YELLOW + " killed by " + ChatColor.GREEN + killer.getName();
				plugin.getPlayerManager().depositPlayer(killer, 10, "Kill a player");
			}
			e.setDeathMessage(message);
			exitPvP(e.getEntity());
		}
	}
	
	public void teleport(Player p) {
		p.teleport(new Location(Bukkit.getWorld("world"), 199, 64, 160));
	}
	
}
