package net.yzimroni.kitpvp.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.yzimroni.kitpvp.kit.KitManager;
import net.yzimroni.kitpvp.kit.MaterialData;

public class Archer extends Kit {

	public Archer(KitManager manager) {
		super(manager);
	}

	@Override
	protected void initType() {
		type = new MaterialData(Material.BOW);
	}

	@Override
	public int getId() {
		return 2;
	}

	@Override
	public String getName() {
		return "Archer";
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
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		pi.setItem(1, bow);
		pi.setItem(28, new ItemStack(Material.ARROW));
		
		pi.setHelmet(new ItemStack(Material.IRON_HELMET));
		pi.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		pi.setBoots(new ItemStack(Material.IRON_BOOTS));

	}

}
