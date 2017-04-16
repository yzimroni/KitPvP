package net.yzimroni.kitpvp.kit;

import java.util.ArrayList;
import java.util.List;

import net.yzimroni.kitpvp.KitPvPPlugin;
import net.yzimroni.kitpvp.kit.kits.Archer;
import net.yzimroni.kitpvp.kit.kits.Kit;
import net.yzimroni.kitpvp.kit.kits.Knight;
import net.yzimroni.kitpvp.kit.kits.Ninja;
import net.yzimroni.kitpvp.player.PvPPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KitManager implements Listener {
	
	private KitPvPPlugin plugin;

	private List<Kit> kits;
	private List<SpecialItem> globalitems;
	private KitShop shop;
	private KitSelector selector;
	
	public KitManager(KitPvPPlugin p) {
		plugin = p;
		
		kits = new ArrayList<Kit>();
		initKits();
		selector = new KitSelector(plugin, this);
		
		globalitems = new ArrayList<SpecialItem>();
		initGlobalItems();
		
		shop = new KitShop(plugin, this);
		
		Bukkit.getPluginManager().registerEvents(selector, plugin);
		Bukkit.getPluginManager().registerEvents(shop, plugin);
	}
	
	public void onDisable() {
		kits.clear();
		globalitems.clear();
		
		selector = null;
		shop = null;
		kits = null;
		globalitems = null;
		
		plugin = null;
	}
	
	private void initKits() {
		kits.clear();
		addKit(new Ninja(this));
		addKit(new Knight(this));
		addKit(new Archer(this));
	}
	
	private void addKit(Kit kit) {
		kits.add(kit);
		Bukkit.getPluginManager().registerEvents(kit, plugin);
	}
	
	public List<Kit> getKits() {
		return kits;
	}
	
	public void handleSpecialItem(Player p, ItemStack i) {
		Kit kit = plugin.getPvpManager().getPlayerKit(p);
		if (kit == null) {
			return;
		}
		for (SpecialItem si : globalitems) {
			if (si.isItem(i)) {
				si.onPlayerUseUnchecked(p, i);
				return;
			}
		}
		for (SpecialItem si : kit.getSpecialItems()) {
			if (si.isItem(i)) {
				si.onPlayerUseUnchecked(p, i);
				return;
			}
		}
	}
	
	private void initGlobalItems() {
		SpecialItem bowls = new SpecialItem(new ItemStack(Material.MUSHROOM_SOUP)) {
			
			@Override
			public boolean onPlayerUse(Player p, ItemStack i) {
				if (p.getHealth() < p.getMaxHealth()) {
					i.setType(Material.BOWL);
					p.getItemInHand().setType(Material.BOWL);
					p.setItemInHand(new ItemStack(Material.BOWL));
					p.updateInventory();
					p.setHealth(Math.min(p.getHealth() + 4, p.getMaxHealth()));
					return true;				
				}
				return false;
			}
		};
		globalitems.add(bowls);
	}
	
	public boolean hasKit(PvPPlayer p, Kit k) {
		if (k.getPrice() == 0) {
			return true;
		}
		return p.hasKit(k.getId());
	}
	
	public boolean buyKit(Player pl, Kit k) {
		PvPPlayer p = plugin.getPlayerManager().getPlayer(pl);
		if (!k.isBuyable()) {
			pl.sendMessage(ChatColor.RED + "You can't buy this kit");
			return false;
		}
		if (k.getPrice() == 0) {
			pl.sendMessage(ChatColor.YELLOW + "This kit is free!");
			return false;
		}
		if (p.hasKit(k.getId())) {
			pl.sendMessage("You already have this kit!");
			return false;
		}
		
		if (plugin.getPlayerManager().getEconomy().withdrawPlayer(pl, k.getPrice()).transactionSuccess()) {
			p.addKit(k.getId());
			pl.sendMessage(ChatColor.GREEN + "You bought " + k.getName() + " kit!");
			return true;
		} else {
			pl.sendMessage(ChatColor.RED + "You don't have enough money");
			return false;
		}
	}
	
	public Kit getKitByName(String name) {
		for (Kit k : kits) {
			if (k.getName().equalsIgnoreCase(name)) {
				return k;
			}
		}
		return null;
	}
	
	public Kit getKitById(int id) {
		for (Kit k : kits) {
			if (k.getId() == id) {
				return k;
			}
		}
		return null;
	}
	
	public Kit getKitByItem(ItemStack i) {
		if (i == null || i.getType() == null || i.getAmount() == 0 || i.getType() == Material.AIR) return null;
		for (Kit k : kits) {
			if (k.getItem().isSimilar(i)) {
				return k;
			}
		}
		return null;
	}
		
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (plugin.getPvpManager().isPvP(e.getPlayer())) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				handleSpecialItem(e.getPlayer(), e.getItem()); //TODO
			}	
		}
	}
	
	public KitShop getShop() {
		return shop;
	}
	
	public KitSelector getSelector() {
		return selector;
	}
	
}
