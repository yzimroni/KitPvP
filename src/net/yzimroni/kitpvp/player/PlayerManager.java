package net.yzimroni.kitpvp.player;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.yzimroni.kitpvp.KitPvPPlugin;
import net.yzimroni.kitpvp.player.data.DataLoader;
import net.yzimroni.kitpvp.player.data.FileData;

public class PlayerManager implements Listener {

	private KitPvPPlugin plugin;
	private Economy economy;
	private HashMap<UUID, PvPPlayer> players;
	private DataLoader data; //TODO
	
	public PlayerManager(KitPvPPlugin p) {
		this.plugin = p;
		
		data = new FileData(plugin);
		
		players = new HashMap<UUID, PvPPlayer>();
		
		initEconomy();
		
		if (!Bukkit.getOnlinePlayers().isEmpty()) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				loadPlayer(pl);
			}
		}
	}
	
	public void onDisable() {
		for (PvPPlayer p : players.values()) {
			savePlayer(p);
		}
		players.clear();
		
		data = null;
		players = null;
		economy = null;

		plugin = null;
	}
	
	private void initEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = plugin.getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		if (economy == null) {
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
	}
	
	public EconomyResponse depositPlayer(OfflinePlayer p, double a, String name) {
		if (p.isOnline()) {
			p.getPlayer().sendMessage(ChatColor.GOLD + "+" + a + " coins, " + name);
		}
		return economy.depositPlayer(p, a);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		loadPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		savePlayer(getPlayer(e.getPlayer()));
		players.remove(e.getPlayer().getUniqueId());
	}

	public Economy getEconomy() {
		return economy;
	}
	
	private void loadPlayer(Player p) {
		players.put(p.getUniqueId(), data.loadPlayer(p));
	}
	
	private void savePlayer(PvPPlayer p) {
		data.savePlayer(p);
	}
	
	public PvPPlayer getPlayer(UUID u) {
		return players.get(u);
	}
	
	public PvPPlayer getPlayer(Player p) {
		return players.get(p.getUniqueId());
	}
	
}
