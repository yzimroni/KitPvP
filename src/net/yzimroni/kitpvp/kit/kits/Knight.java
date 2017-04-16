package net.yzimroni.kitpvp.kit.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.yzimroni.kitpvp.kit.KitManager;
import net.yzimroni.kitpvp.kit.MaterialData;

public class Knight extends Kit {

	public Knight(KitManager manager) {
		super(manager);
	}
	
	@Override
	protected void initType() {
		type = new MaterialData(Material.IRON_SWORD);
	}

	@Override
	public int getId() {
		return 1;
	}

	@Override
	public String getName() {
		return "Knight";
	}

	@Override
	public double getPrice() {
		return 0;
	}

	@Override
	public void giveKit(Player p) {
		super.giveKit(p);
		PlayerInventory pi = p.getInventory();
		pi.setItem(0, new ItemStack(Material.IRON_SWORD));
		
		pi.setHelmet(new ItemStack(Material.IRON_HELMET));
		pi.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		pi.setBoots(new ItemStack(Material.IRON_BOOTS));
		
	}
	
}
