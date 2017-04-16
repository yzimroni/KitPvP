package net.yzimroni.kitpvp.player.data;

import java.util.UUID;

import net.yzimroni.kitpvp.KitPvPPlugin;
import net.yzimroni.kitpvp.player.PvPPlayer;

import org.bukkit.entity.Player;

public abstract class DataLoader {
	
	protected KitPvPPlugin plugin;
	
	public DataLoader(KitPvPPlugin p) {
		plugin = p;
	}
	
	protected PvPPlayer newPlayer(UUID uuid) {
		PvPPlayer player = new PvPPlayer(uuid, 0, 0, 0);
		return player;
	}

	public abstract PvPPlayer loadPlayer(Player p);
	
	public abstract void savePlayer(PvPPlayer p);
	
}
