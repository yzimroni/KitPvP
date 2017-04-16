package net.yzimroni.kitpvp;

import net.yzimroni.kitpvp.kit.KitManager;
import net.yzimroni.kitpvp.player.PlayerManager;
import net.yzimroni.kitpvp.pvp.PvPManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPvPPlugin extends JavaPlugin {
	
	/*
	 * TODO
	 * PlayerManager (and player object, data loader interface and impl) V
	 * 		MySQL Data saver
	 * 		Auto Saving
	 * 
	 * Economy System (Vault) V
	 * KitManager V
	 * Kit interface V
	 * Add Kits
	 * 		Add more kits: TODO
	 * Kit Shop
	 * 		Better shop and info about each kit
	 * Kit Selector V
	 * 		Better selector
	 * Lobby Manager 
	 * 		Features in the lobby (scoreboard etc.)
	 * General Events (food etc) V
	 * Combat log system
	 * Report system
	 * Team system
	 * Custom death message V
	 * Glow to items
	 * Commands (and kit give etc.)
	 */
	
	private PvPManager pvpManager;
	private KitManager kitManager;
	private PlayerManager playerManager;
	private Events events;
	private LobbyManager lobby;

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		pvpManager = new PvPManager(this);
		kitManager = new KitManager(this);
		playerManager = new PlayerManager(this);
		events = new Events(this);
		lobby = new LobbyManager(this);
		
		Bukkit.getPluginManager().registerEvents(pvpManager, this);
		Bukkit.getPluginManager().registerEvents(playerManager, this);
		Bukkit.getPluginManager().registerEvents(kitManager, this);
		Bukkit.getPluginManager().registerEvents(events, this);
		Bukkit.getPluginManager().registerEvents(lobby, this);
	}
	
	@Override
	public void onDisable() {
		kitManager.onDisable();
		playerManager.onDisable();
		
		events = null;
		kitManager = null;
		playerManager = null;
	}
	
	public PvPManager getPvpManager() {
		return pvpManager;
	}

	public KitManager getKitManager() {
		return kitManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public Events getEvents() {
		return events;
	}

	public LobbyManager getLobby() {
		return lobby;
	}
	
}
