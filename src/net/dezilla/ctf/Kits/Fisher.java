package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Fisher extends BaseKit {
	int[] flagCol = {0, 4, 2, 6, 1, 5, 14, 7, 8, 4, 10, 12, 1, 13, 11, 15}; 

	public Fisher(Player player, int variation) {
		super(player, variation);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setItem(0, new ItemStack(Material.FISHING_ROD));
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF,3));
		inv.setItem(2, new ItemStack(Material.LEGACY_RAW_FISH,10));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.LEATHER_BOOTS)));
		inv.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		setHunger();
		resExp();
		potEffect();
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onItemClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.LEGACY_RAW_FISH){
			p.sendMessage("wat");
		}
	}
	@Override
	public void setHelmet(){
		p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
	}
	@Override
	public String getKitName() {
		return("Fisher");
	}

}
