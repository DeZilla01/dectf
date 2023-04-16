package net.dezilla.ctf.Kits;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.TimeManager;

public class Dragger extends BaseKit{
	ArrayList<Arrow> repealArrows = new ArrayList<Arrow>();

	public Dragger(Player player, int variation) {
		super(player, variation);
	}
	
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//legs
		ItemStack legs = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 0xFFFFFF);
		//chest
		ItemStack chest = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 0xFFFFFF);
		//sword
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		//pull
		ItemStack pull = BaseKit.setItemName(new ItemStack(Material.BONE_MEAL), ChatColor.RESET+"Pull");
		//push
		ItemStack push = BaseKit.setItemName(new ItemStack(Material.FEATHER), ChatColor.RESET+"Push");
		//blackhole
		ItemStack blackhole = BaseKit.setItemName(new ItemStack(Material.SNOWBALL), ChatColor.RESET+"Black Hole");
		//bow
		ItemStack bow = BaseKit.setItemName(new ItemStack(Material.BOW), ChatColor.RESET+"Bow of Levitation");
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		//apply
		inv.setItem(0, sword);
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF,4));
		inv.setItem(2, pull);
		inv.setItem(3, push);
		inv.setItem(4, bow);
		inv.setItem(5, blackhole);
		inv.setItem(9, new ItemStack(Material.ARROW));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.DIAMOND_BOOTS)));
		inv.setLeggings(legs);
		inv.setChestplate(chest);
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void resExp(){
		p.setExp((float) .99);
	}
	@Override
	public void onBowAttack(Player victim, EntityDamageByEntityEvent e){
		if(repealArrows.contains((Arrow) e.getDamager())){
			e.setDamage(e.getDamage()*4);
			repealArrows.remove((Arrow) e.getDamager());
		}
		else{
		e.setDamage(0);
		victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 2));
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onItemClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand().getType() == Material.BONE_MEAL){
				e.setCancelled(true);
			}
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
			if(p.getItemInHand().getType() == Material.IRON_SWORD && p.getExp()>=.1){
				p.setExp((float) (p.getExp()-.1));
				for(Entity ent : Main.mConfig.M_world.getEntities()){
					if(p.getLocation().distance(ent.getLocation()) <= 5 && !(ent instanceof Player)){
						if(ent instanceof Arrow){
							Arrow arr = (Arrow) ent;
							repealArrows.add(arr);
							arr.setShooter(p);
						}
						Vector vel = ent.getVelocity();
						vel.setX(vel.getX() * -1);
						vel.setY(vel.getY() * -1);
						vel.setZ(vel.getZ() * -1);
						ent.setVelocity(vel);
					}
				}
			}
		}
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
			if(p.getItemInHand().getType() == Material.BOW && p.getExp() >= .20){
				p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 2));
				p.setExp((float) (p.getExp()-.20));
			}
		}
		
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onProjectileHitEvent(ProjectileHitEvent e){
		if(e.getEntity() instanceof Snowball){
			if(p.getExp() >= .99){
				Snowball sb = (Snowball) e.getEntity();
				Location loc = sb.getLocation();
				loc.setY(loc.getY()-1);
				ArmorStand a = (ArmorStand) Main.mConfig.M_world.spawnEntity(loc, EntityType.ARMOR_STAND);
				ItemStack hat = new ItemStack(Material.LEGACY_WOOL, 1);
				hat.setDurability((short) 15);
				a.setHelmet(hat);
				a.setVisible(false);
				a.setAI(false);
				a.setGravity(false);
				TimeManager timer = new TimeManager(Main.plugin, 6, Main.mConfig.M_match.getPlayerTeam(p), null, null, a, null, null);
				timer.runTaskTimer(Main.plugin, 0, 2);
				p.setExp(0);
			}
			p.getInventory().addItem(BaseKit.setItemName(new ItemStack(Material.SNOWBALL), ChatColor.RESET+"Black Hole"));
		}
	}
	@Override
	public void onShootBow(EntityShootBowEvent e){
		if(p.getExp() >= .10){
			 p.setExp((float) (p.getExp()-.10));
		 }
		 else{
			 e.setCancelled(true);
		 }
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onEntityInteract(PlayerInteractEntityEvent e){
		if(p.getItemInHand().getType() == Material.BONE_MEAL || p.getItemInHand().getType() == Material.FEATHER){
			if(e.getRightClicked() instanceof Player && p.getExp() >= .12){
				Player victim = (Player) e.getRightClicked();
				if(!(Main.mConfig.M_match.getPlayerTeam(p) == Main.mConfig.M_match.getPlayerTeam(victim))){
					float yaw = p.getLocation().getYaw();
					double x = 1;
					double z = 1;
					if((yaw>=0 && yaw<=90)||(yaw<-270 && yaw >= -360)){
						if(yaw>=0 && yaw<=90){
							x = (yaw/90)*x;
							z = (yaw/90)*z;
							z = ((z-.5)*-1)+.5;
							x*=-1;
						}
						else{
							yaw*=-1;
							yaw-=270;
							x = (yaw/90)*x;
							z = (yaw/90)*z;
							x = ((x-.5)*-1)+.5;
							x*=-1;
						}
					}
					else if((yaw>90 && yaw<=180)||(yaw<-180 && yaw >= -270)){
						if(yaw>90 && yaw<=180){
							yaw -=90;
							x = (yaw/90)*x;
							x = ((x-.5)*-1)+.5;
							z = (yaw/90)*z;
							z*=-1;
							x*=-1;
						}
						else{
							yaw*=-1;
							yaw-=180;
							x = (yaw/90)*x;
							z = (yaw/90)*z;
							z = ((z-.5)*-1)+.5;
							z*=-1;
							x*=-1;
						}
					}
					else if((yaw<0 && yaw>=-90)||(yaw>270 && yaw<=360)){
						if(yaw<0 && yaw>=-90){
							yaw *=-1;
							x = (yaw/90)*x;
							z = (yaw/90)*z;
							z = ((z-.5)*-1)+.5;
						}
						else{
							yaw-=270;
							x = (yaw/90)*x;
							z = (yaw/90)*z;
							x = ((x-.5)*-1)+.5;
						}
					}
					else if((yaw<-90 && yaw>=-180)||(yaw>180 && yaw<=270)){
						if(yaw<-90 && yaw>=-180){
							yaw+=90;
							yaw*=-1;
							x = (yaw/90)*x;
							z = (yaw/90)*z;
							x = ((x-.5)*-1)+.5;
							z *=-1;
						}
						else{
							yaw-=180;
							x = (yaw/90)*x;
							z = (yaw/90)*z;
							z = ((z-.5)*-1)+.5;
							z *=-1;
						}
					}
					else{
						p.sendMessage(Double.toString(x));
						p.sendMessage(Double.toString(z));
					}
					if(p.getItemInHand().getType() == Material.BONE_MEAL){
						x*=-1;
						z*=-1;
					}
					victim.setVelocity(new Vector(x, (double) .2, z));
					p.setExp((float) (p.getExp()-.12));
				}
			}
		}
	}
	@Override
	public String getKitName() {
		return("Dragger");
	}

}
