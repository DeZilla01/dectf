package net.dezilla.ctf.Kits;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.GameType.CTF;


public class Ninja extends BaseKit{
	static String[] Variations = {"Default", "Classic"};
	float invis = 1F;
	public boolean isHidden;

	public Ninja(Player player, int variation) {
		super(player, variation);
		if(variation==1) {
			variationID=1;
		}
		isHidden = false;
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//sword
		ItemStack sword = new ItemStack(Material.GOLDEN_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
		//nugget
		ItemStack nugget = setAtkSpeed(new ItemStack(Material.IRON_NUGGET));
		//apply
		inv.setItem(0, sword);
		if(variationID==1) {
			inv.setItem(1, new ItemStack(Material.ENDER_PEARL,10));
			inv.setItem(2, new ItemStack(Material.EGG,10));
			inv.setItem(3, new ItemStack(Material.REDSTONE,64));
		}
		else {
			inv.setItem(1, new ItemStack(Material.ENDER_PEARL));
			inv.setItem(2, new ItemStack(Material.EGG,10));
			inv.setItem(3, new ItemStack(Material.REDSTONE));
		}
		inv.setBoots(nugget);
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void onDamage(Player victim, EntityDamageByEntityEvent e){
		if(isHidden){
			e.setCancelled(true);
		}
	}
	@Override
	public void potEffect(){
		for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
	}
	@Override
	public void onProjectileHitEvent(ProjectileHitEvent e){
		if(e.getEntity() instanceof Egg){
			Egg egg = (Egg) e.getEntity();
			Location loc = egg.getLocation();
			Main.mConfig.M_world.createExplosion(loc, (float) 0.0);
			for(Player victim : Bukkit.getServer().getOnlinePlayers()){
				if(Main.mConfig.M_match.getPlayerTeam(p) != Main.mConfig.M_match.getPlayerTeam(victim) && loc.distance(victim.getLocation()) <= 3.0){
					victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
					victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 90, 3));
				}
			}
		}
		if(e.getEntity() instanceof EnderPearl && variationID != 1) {
			p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
		}
	}
	@Override
	public void update20() {
		if(p.isSneaking() && p.getHealth()<=19 && !p.isDead() && p.getInventory().getItemInMainHand().getType() == Material.GOLDEN_SWORD) {
			p.setHealth(p.getHealth()+1);
		}
	}
	@Override
	public void update5() {
		if(isHidden && p.getInventory().getItemInMainHand().getType() != Material.REDSTONE && variationID == 1){
			isHidden = false;
			for(Player ps : Bukkit.getServer().getOnlinePlayers()){
				ps.showPlayer(Main.plugin, p);
			}
		}
		if(isHidden && variationID != 1 && invis < .05){
			isHidden = false;
			for(Player ps : Bukkit.getServer().getOnlinePlayers()){
				ps.showPlayer(Main.plugin, p);
			}
		}
		else if(!isHidden && p.getInventory().getItemInMainHand().getType() == Material.REDSTONE&& invis>=.05){
			if(Main.mConfig.M_match instanceof CTF){
				CTF match = (CTF) Main.mConfig.M_match;
				if(!match.capturedBy.contains(p.getName())){
					isHidden = true;
					for(Player ps : Bukkit.getServer().getOnlinePlayers()){
						ps.hidePlayer(Main.plugin, p);
					}
				}
			}
			else{
				isHidden = true;
				for(Player ps : Bukkit.getServer().getOnlinePlayers()){
					ps.hidePlayer(Main.plugin, p);
				}
			}
		}
		if(isHidden){
			if(variationID == 1) {
				ItemStack redstone = p.getInventory().getItemInMainHand();
				if(redstone.getAmount() == 1){
					p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				}
				else{
					redstone.setAmount(redstone.getAmount()-2);
				}
			}
			else {
				invis-=.05;
				if(invis<0) {
					invis=0F;
				}
				p.setExp(invis);
			}
		}
		if(!isHidden && variationID!=1) {
			invis+=.01;
			if(invis>1) {
				invis=1F;
			}
			p.setExp(invis);
		}
	}
	@Override
	public String getKitName() {
		if(variationID==1) {
			return("Ninja (Classic)");
		}
		return("Ninja (Default)");
	}

}
