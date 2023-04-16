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

public class Heavy extends BaseKit {
	static String[] Variations = {"Default", "Tank"};

	public Heavy(Player player, int variation) {
		super(player, variation);
		if(variation==1) {
			variationID=1;
		}
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
		if(variationID==1) {
			ItemStack shield = new ItemStack(Material.SHIELD);
			BlockStateMeta bmeta = (BlockStateMeta) shield.getItemMeta();
			Banner banner = (Banner) bmeta.getBlockState();
			banner.setBaseColor(Main.getDyeColor(p));
			bmeta.setBlockState(banner);
			shield.setItemMeta(bmeta);
			inv.setItemInOffHand(shield);
		}
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
		if(variationID==1) {
			p.setFoodLevel(6);
		}
		else {
			super.setHunger();
		}
	}
	@Override
	public void potEffect(){
		if(variationID==1) {
			for (PotionEffect effect : p.getActivePotionEffects())
		        p.removePotionEffect(effect.getType());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 0));
		}
		else {
			super.potEffect();
		}
	}
	@Override
	public String getKitName() {
		String msg = "Heavy (";
		if(variationID==1) {
			msg+="Tank)";
		}
		else {
			msg+="Default)";
		}
		return msg;
	}

}
