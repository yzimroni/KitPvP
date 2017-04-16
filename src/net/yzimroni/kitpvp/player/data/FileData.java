package net.yzimroni.kitpvp.player.data;

import java.io.File;
import java.io.IOException;

import net.yzimroni.kitpvp.KitPvPPlugin;
import net.yzimroni.kitpvp.player.PvPPlayer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileData extends DataLoader {
	
	File file = null;
	FileConfiguration data;
	
	public FileData(KitPvPPlugin p) {
		super(p);
		
		file = new File(p.getDataFolder(), "players.yml");
		data = YamlConfiguration.loadConfiguration(file);
	}
	
	public void save() {
		try {
			data.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PvPPlayer loadPlayer(Player p) {
		if (!data.contains(p.getUniqueId().toString())) {
			return newPlayer(p.getUniqueId());
		}
		PvPPlayer player = new PvPPlayer(p.getUniqueId());
		player.setKills(data.getInt(p.getUniqueId() + ".kills"));
		player.setDeaths(data.getInt(p.getUniqueId() + ".death"));
		player.setKillStreak(data.getInt(p.getUniqueId() + ".killStreak"));
		player.setKits(data.getIntegerList(p.getUniqueId() + ".kits"));
		return player;
	}

	@Override
	public void savePlayer(PvPPlayer p) {
		data.set(p.getUuid() + ".kills", p.getKills());
		data.set(p.getUuid() + ".death", p.getDeaths());
		data.set(p.getUuid() + ".killStreak", p.getKillStreak());
		data.set(p.getUuid() + ".kits", p.getKits());
		save();
	}

}
