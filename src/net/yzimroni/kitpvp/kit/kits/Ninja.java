package net.yzimroni.kitpvp.kit.kits;

import net.yzimroni.kitpvp.kit.KitManager;
import net.yzimroni.kitpvp.kit.MaterialData;
import net.yzimroni.kitpvp.kit.SpecialItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Ninja extends Kit {
	
	public Ninja(KitManager manager) {
		super(manager);
		initSpecialItems();
	}
	
	@Override
	protected void initType() {
		type = new MaterialData(Material.EMERALD);
	}
	
	private void initSpecialItems() {
		specialitems.clear();
		SpecialItem emerald = new SpecialItem(new ItemStack(Material.EMERALD), 3000) {
			
			@Override
			public boolean onPlayerUse(Player p, ItemStack i) {
				Vector v = p.getLocation().getDirection().multiply(3).setY(3.5);
				p.setVelocity(v);
				return true;
			}
		};
		
		specialitems.add(emerald);
		
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public String getName() {
		return "Ninja";
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
		pi.setItem(1, new ItemStack(Material.EMERALD));
		
		pi.setHelmet(new ItemStack(Material.GOLD_HELMET));
		pi.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		pi.setBoots(new ItemStack(Material.GOLD_BOOTS));
		
		p.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 0));
	}
	
	@Override
	public void takeKit(Player p) {
		super.takeKit(p);
		p.removePotionEffect(PotionEffectType.SPEED);
	}

}
