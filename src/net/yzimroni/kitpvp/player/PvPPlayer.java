package net.yzimroni.kitpvp.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PvPPlayer {

	private UUID uuid;
	private int kills;
	private int deaths;
	private int killStreak;
	private int currentKS;
	private List<Integer> kits = new ArrayList<Integer>();
	private List<Integer> newKits = new ArrayList<Integer>();

	public PvPPlayer(UUID uuid, int kills, int deaths, int killStreak) {
		this.uuid = uuid;
		this.kills = kills;
		this.deaths = deaths;
		this.killStreak = killStreak;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public PvPPlayer(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void addKills(int kills) {
		this.kills += kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void addDeath(int deaths) {
		this.deaths += deaths;
	}

	public int getKillStreak() {
		return killStreak;
	}

	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}

	public List<Integer> getKits() {
		return kits;
	}

	public void setKits(List<Integer> kits) {
		this.kits = kits;
	}

	public List<Integer> getNewKits() {
		return newKits;
	}

	public void clearNewKits() {
		newKits.clear();
	}

	public void addKit(int id) {
		if (!kits.contains(id)) {
			kits.add(id);
			newKits.add(id);
		}
	}

	public boolean hasKit(int id) {
		return kits.contains(id);
	}

	@Override
	public String toString() {
		return "PvPPlayer [uuid=" + uuid + ", kills=" + kills + ", deaths=" + deaths + ", killStreak=" + killStreak + ", kits=" + kits + ", newKits="
			+ newKits + "]";
	}
	
	public int getCurrentKillStreak() {
		return currentKS;
	}

	public void setCurrentKillStreak(int currentKillStreak) {
		this.currentKS = currentKillStreak;
	}

	public void addKillStreak() {
		this.currentKS += 1;
		if (currentKS > killStreak) {
			killStreak = currentKS;
			getPlayer().sendMessage(ChatColor.GREEN + "New kill streak of " + killStreak + "!");
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deaths;
		result = prime * result + killStreak;
		result = prime * result + kills;
		result = prime * result + ((kits == null) ? 0 : kits.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PvPPlayer other = (PvPPlayer) obj;
		if (deaths != other.deaths)
			return false;
		if (killStreak != other.killStreak)
			return false;
		if (kills != other.kills)
			return false;
		if (kits == null) {
			if (other.kits != null)
				return false;
		} else if (!kits.equals(other.kits))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}


}
