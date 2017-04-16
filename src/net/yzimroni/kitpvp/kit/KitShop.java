package net.yzimroni.kitpvp.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.yzimroni.kitpvp.KitPvPPlugin;
import net.yzimroni.kitpvp.kit.kits.Kit;
import net.yzimroni.kitpvp.player.PvPPlayer;

public class KitShop implements Listener {

	private KitPvPPlugin plugin;
	private KitManager manager;
	
	public KitShop(KitPvPPlugin p, KitManager m) {
		plugin = p;
		manager = m;
	}
	
	public void openKitShopGui(Player p) {
		if (!plugin.getPvpManager().isPvP(p)) {
			Inventory i = Bukkit.createInventory(p, InventoryType.CHEST, "Kits Shop");
			PvPPlayer player = plugin.getPlayerManager().getPlayer(p);
			for (Kit k : manager.getKits()) {
				if (k.isBuyable() && !manager.hasKit(player, k)) {
					i.addItem(k.getItem());
				}
			}
			p.openInventory(i);
		}
	}
	
	
	@EventHandler
	public void onInvenotryClick(InventoryClickEvent e) {
		if (e.getInventory().getTitle().equals("Kits Shop")) {
			ItemStack i = e.getCurrentItem();
			Kit k = manager.getKitByItem(i);
			Player p = (Player) e.getWhoClicked();
			if (k != null) {
				if (!manager.hasKit(plugin.getPlayerManager().getPlayer(p), k)) {
					manager.buyKit(p, k);
				} else {
					p.sendMessage(ChatColor.RED + "You already have this kit");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/shop")) {
			openKitShopGui(e.getPlayer());
			e.setCancelled(true);
		}
	}
	
}
