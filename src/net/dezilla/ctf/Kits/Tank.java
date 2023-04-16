package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.Main;

// Class to be deleted and merged with Heavy
public class Tank extends BaseKit {

	public Tank(Player player, int variation) {
		super(player, variation);
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD));
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF,3));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.DIAMOND_BOOTS)));
		inv.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		ItemStack shield = new ItemStack(Material.SHIELD);
		BlockStateMeta bmeta = (BlockStateMeta) shield.getItemMeta();
		Banner banner = (Banner) bmeta.getBlockState();
		banner.setBaseColor(Main.getDyeColor(p));
		bmeta.setBlockState(banner);
		shield.setItemMeta(bmeta);
		inv.setItemInOffHand(shield);
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void setHelmet(){
		p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
	}
	@Override
	public void setHunger(){
		p.setFoodLevel(6);
	}
	@Override
	public void potEffect(){
		for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 0));
	}
	@Override
	public String getKitName() {
		return("Tank");
	}

}
