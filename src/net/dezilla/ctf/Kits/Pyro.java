package net.dezilla.ctf.Kits;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.Structures.PyroFire;

public class Pyro extends BaseKit{
	static String[] Variations = {"Default", "Overcharged"};
	ArrayList<Arrow> explosivearrows = new ArrayList<Arrow>();
	//Charged xp meter
	double charge = 0.0;
	public boolean charged = false;

	public Pyro(Player player, int variation) {
		super(player, variation);
		if(variation==1) {
			variationID=1;
		}
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//helmet
		ItemStack helm = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_HELMET), 0xDB730A);
		//chest
		ItemStack chest = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 0xBF3005);
		//legs
		ItemStack legs = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 0xAF0000);
		//boots
		ItemStack boots = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 0x990000);
		//apply
		if(variationID==1) {
			inv.setItem(0, new ItemStack(Material.STONE_AXE));
		} else {
			inv.setItem(0, new ItemStack(Material.WOODEN_AXE));
		}
		inv.setItem(2, new ItemStack(Material.BOW));
		inv.setItem(3, new ItemStack(Material.FLINT_AND_STEEL));
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF,4));
		inv.setItem(9, new ItemStack(Material.ARROW,32));
		inv.setBoots(setAtkSpeed(boots));
		inv.setLeggings(legs);
		inv.setChestplate(chest);
		inv.setHelmet(helm);
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void setHelmet(){
		//helmet
		ItemStack helm = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_HELMET), 0xDB730A);
		//apply
		p.getInventory().setHelmet(helm);
	}
	@Override
	public void onProjectileHitEvent(ProjectileHitEvent e){
		if(e.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) e.getEntity();
			if(explosivearrows.contains(arrow)){
				Location loc = arrow.getLocation();
				Main.mConfig.M_world.createExplosion(loc, (float) 0.0);
				for(Player victim : Bukkit.getServer().getOnlinePlayers()){
					if(Main.mConfig.M_match.getPlayerTeam(p) != Main.mConfig.M_match.getPlayerTeam(victim) && loc.distance(victim.getLocation()) <= 1.0){
						victim.damage(8.0);
						victim.setFireTicks(100);
					}
					else if(Main.mConfig.M_match.getPlayerTeam(p) != Main.mConfig.M_match.getPlayerTeam(victim) && loc.distance(victim.getLocation()) <= 2.0){
						victim.damage(5.0);
						victim.setFireTicks(90);
					}
					else if(Main.mConfig.M_match.getPlayerTeam(p) != Main.mConfig.M_match.getPlayerTeam(victim) && loc.distance(victim.getLocation()) <= 3.0){
						victim.damage(2.0);
						victim.setFireTicks(80);
					}
					else if(Main.mConfig.M_match.getPlayerTeam(p) != Main.mConfig.M_match.getPlayerTeam(victim) && loc.distance(victim.getLocation()) <= 4.5){
						victim.damage(1.0);
						victim.setFireTicks(60);
					}
				}
				explosivearrows.remove(arrow);
			}
		}
	}
	@Override
	public void onBowAttack(Player victim, EntityDamageByEntityEvent e){
		e.setCancelled(true);
	}
	@Override
	public void onShootBow(EntityShootBowEvent e){
		if(e.getForce()>=1){
			 explosivearrows.add((Arrow) e.getProjectile());
		 }
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onDamage(Player victim, EntityDamageByEntityEvent e){
		if(Main.mConfig.M_match.getPlayerTeam(p) == Main.mConfig.M_match.getPlayerTeam(victim)){
			e.setCancelled(true);
		}
		if(victim.getFireTicks()>0 && p.getItemInHand().getType() == Material.WOODEN_AXE && !(Main.grabKit(victim) instanceof Medic) && !victim.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)){
			e.setDamage(999.0);
		}
		if(variationID==1 && charged==false) {
			charge += e.getDamage()/175;
			if(charge>1) {charge = 1.0;};
			p.setExp((float) charge); 
			e.setDamage(e.getDamage()*.65);
		}
		else if(variationID==1) {
			if(victim.getFireTicks()>0) {
				e.setDamage(e.getDamage()*1.85);
			}
			else {
				e.setDamage(e.getDamage()*1.2);
			}
		}
	}
	@Override
	public void update1(){
		if(charged) {
			charge-=.005;
			if(charge<=0) {
				charge=0;
				uncharge();
			}
			p.setExp((float) charge);
		}
	}
	public void uncharge() {
		for(ItemStack i : p.getInventory().getContents()) {
			if(i != null) {
			if(i.getType() == Material.STONE_AXE) {
				i.removeEnchantment(Enchantment.FIRE_ASPECT);
			}}
		}
		charged=false;
	}
	@Override
	public void potEffect(){
		for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
		p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999999, 0));
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onItemClick(PlayerInteractEvent e){
		//FLINT AND STEEL
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL){
				Block blok = e.getClickedBlock().getRelative(BlockFace.UP);
				if(blok.getType() == Material.AIR){
					new PyroFire(p, blok);
					//blok.setType(Material.FIRE);
					//TimeManager timer = new TimeManager(Main.plugin, 4, 3, null, blok, null, null, null);
					//timer.runTaskTimer(Main.plugin, 0, 20);
					//p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability()-1));
				}
				e.setCancelled(true);
			}
		}
		//Activate Charge
		if(e.getAction()== Action.RIGHT_CLICK_BLOCK || e.getAction()== Action.RIGHT_CLICK_AIR) {
			if(p.getInventory().getItemInMainHand().getType() == Material.STONE_AXE && charge == 1) {
				p.getInventory().getItemInMainHand().addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
				p.getWorld().playSound(p.getLocation(), "entity.ender_eye.death", 1.0F, 1.0F);
				charged = true;
			}
		}
	}
	@Override
	public String getKitName() {
		if(variationID==1) {
			return("Pyro (Overcharged)");
		}
		return("Pyro (Default)");
	}

}
