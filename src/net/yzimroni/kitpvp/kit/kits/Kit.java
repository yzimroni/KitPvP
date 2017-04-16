package net.yzimroni.kitpvp.kit.kits;

import java.util.ArrayList;
import java.util.List;

import net.yzimroni.kitpvp.kit.KitManager;
import net.yzimroni.kitpvp.kit.MaterialData;
import net.yzimroni.kitpvp.kit.SpecialItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Kit implements Listener {

	protected KitManager manager;
	protected MaterialData type;
	protected final List<SpecialItem> specialitems = new ArrayList<SpecialItem>();
	
	public Kit(KitManager manager) {
		this.manager = manager;
		initType();
		
	}
	
	protected abstract void initType();
	
	public abstract int getId();
	
	public abstract String getName();
	
	public abstract double getPrice();
	
	public boolean isBuyable() {
		return true;
	}

	public void giveKit(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		giveBowls(p);
	}
	
	public void takeKit(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
	}
	
	public List<SpecialItem> getSpecialItems() {
		return specialitems;
	}
	
	public MaterialData getItemType() {
		return type;
	}
		
	protected void giveBowls(Player p) {
		p.getInventory().setItem(9, new ItemStack(Material.BOWL));
		ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
		for (int i = 0; i<36; i++) {
			ItemStack it = p.getInventory().getItem(i);
			if (it == null || it.getAmount() == 0 || it.getType() == null || it.getType() == Material.AIR) {
				p.getInventory().setItem(i, soup);
			}
		}
	}
	
	public ItemStack getItem() {
		ItemStack item = getItemType().toItemStack();
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.GREEN + getName());
		
		item.setItemMeta(meta);
		
		return item;
	}

	@Override
	public String toString() {
		return "Kit [manager=" + manager + ", type=" + type + ", specialitems="
				+ specialitems + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getPrice()=" + getPrice() + ", getItemType()="
				+ getItemType() + ", getItem()=" + getItem() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((specialitems == null) ? 0 : specialitems.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Kit other = (Kit) obj;
		if (specialitems == null) {
			if (other.specialitems != null)
				return false;
		} else if (!specialitems.equals(other.specialitems))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
