package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Wraith extends BaseKit{

	public Wraith(Player player, int variation) {
		super(player, variation);
	}
	
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setItem(0, new ItemStack(Material.IRON_SWORD));
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 3));
		inv.setItem(2, new ItemStack(Material.BLAZE_ROD));
		inv.setItem(3, new ItemStack(Material.HOPPER));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.LEATHER_BOOTS)));
		inv.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public String getKitName() {
		return("Wraith");
	}

}
