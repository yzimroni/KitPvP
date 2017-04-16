package net.yzimroni.kitpvp.kit;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class SpecialItem {

	private ItemStack item;
	private long cooldown = -1;
	private HashMap<UUID, Long> uses;
	
	public SpecialItem(ItemStack item) {
		this.item = item;
	}
	
	public SpecialItem(ItemStack item, long cooldown) {
		this.item = item;
		this.cooldown = cooldown;
		this.uses = new HashMap<UUID, Long>();
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public boolean isItem(ItemStack i) {
		if (i == null || i.getType() == null) {
			return false;
		}
		return item.isSimilar(i);
	}
	
	public boolean canUseCooldown(Player p) {
		if (cooldown == -1) {
			return true; //No cooldown
		}
		if (uses.containsKey(p.getUniqueId())) {
			return (System.currentTimeMillis() - uses.get(p.getUniqueId())) > cooldown;
		}
		
		return true;
	}
	
	public long getCooldownTime(Player p) {
		if (cooldown == -1) {
			return -1;
		}
		if (!uses.containsKey(p.getUniqueId())) {
			return -1;
		}
		
		return System.currentTimeMillis() - uses.get(p.getUniqueId());
	}
	
	public void sendCooldownMessage(Player p) {
		if (cooldown == -1) {
			return;
		}
		double time = ((double) cooldown - getCooldownTime(p)) / 1000;
		p.sendMessage(ChatColor.RED + "Please wait " + ChatColor.YELLOW + time + ChatColor.RED + " second" + (time == 1 ? "" : "s"));
	}
	
	public void addCooldown(Player p) {
		if (cooldown == -1) {
			return;
		}
		
		if (uses.containsKey(p.getUniqueId())) {
			uses.remove(p.getUniqueId());
		}
		
		uses.put(p.getUniqueId(), System.currentTimeMillis());
	}
	
	public final boolean onPlayerUseUnchecked(Player p, ItemStack i) {
		if (canUseCooldown(p)) {
			addCooldown(p);
			return onPlayerUse(p, i);
		} else {
			sendCooldownMessage(p);
		}
		return false;
	}
	
	public abstract boolean onPlayerUse(Player p, ItemStack i);
	
}
