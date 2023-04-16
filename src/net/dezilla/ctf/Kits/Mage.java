package net.dezilla.ctf.Kits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.TimeManager;

public class Mage extends BaseKit{
	Float[] cool = {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F}; // dmg, fire, lightning, ice, heal, weee
	Material[] hoes = {Material.DIAMOND_HOE, Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.STONE_SHOVEL};
	Float[] ratio = {.065F, .02F, .015F, .02F, .02F, .02F};

	public Mage(Player player, int variation) {
		super(player, variation);
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//legs
		ItemStack legs = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 0x7B1899);
		//chest
		ItemStack chest = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 0x9915C1);
		//boots
		ItemStack boots = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 0x9915C1);
		//apply
		inv.setItem(0, new ItemStack(Material.DIAMOND_HOE));
		inv.setItem(1, new ItemStack(Material.WOODEN_HOE));
		inv.setItem(2, new ItemStack(Material.STONE_HOE));
		inv.setItem(3, new ItemStack(Material.IRON_HOE));
		inv.setItem(4, new ItemStack(Material.GOLDEN_HOE));
		inv.setItem(5, new ItemStack(Material.STONE_SHOVEL));
		inv.setBoots(boots);
		inv.setLeggings(legs);
		inv.setChestplate(chest);
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void onBowAttack(Player victim, EntityDamageByEntityEvent e){
		dmgExplosion(e.getEntity().getLocation(), Main.mConfig.M_teamColInt.get(Main.getTeamId(p)));
		e.setDamage(6.0);
	}
	@Override
	public void onProjectileHitEvent(ProjectileHitEvent e){
		if(e.getEntity() instanceof Arrow){
			dmgExplosion(e.getEntity().getLocation(), Main.mConfig.M_teamColInt.get(Main.getTeamId(p)));
			e.getEntity().remove();
		}
		if(e.getEntity() instanceof EnderPearl){
			EnderPearl pearl = (EnderPearl) e.getEntity();
			pearl.setShooter(null);
			if(e.getHitEntity() instanceof Player){
				Player victim = (Player) e.getHitEntity();
				if(Main.getTeamId(p) != Main.getTeamId(victim)){
					victim.damage(2);
					victim.setFireTicks(80);
				}
			}
			pearl.remove();
		}
	}
	@Override
	public void onItemClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			//DAMAGE SPELL
			if(p.getInventory().getItemInMainHand().getType() == hoes[0] && cool[0] == 1.0F){
				cool[0] = 0F;
				Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
				Arrow arrow = (Arrow) p.getWorld().spawnEntity(loc, EntityType.ARROW);
				arrow.setVelocity(directionCoord(p, 4));arrow.setGravity(false);
				arrow.setShooter(p);
				TimeManager timer = new TimeManager(Main.plugin, 9, 3, null, null, null, p, arrow);
				timer.runTaskTimer(Main.plugin, 0, 6);
			}
			//FIRE SPELL
			if(p.getInventory().getItemInMainHand().getType() == hoes[1] && cool[1] == 1.0F){
				cool[1] = 0F;
				Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
				EnderPearl fireB = (EnderPearl) p.getWorld().spawnEntity(loc, EntityType.ENDER_PEARL);
				fireB.setVelocity(directionCoord(p, 2));
				fireB.setFireTicks(999);
				fireB.setShooter(p);
				fireB.setBounce(true);
			}
			//LIGHTNING SPELL
			if(p.getInventory().getItemInMainHand().getType() == hoes[2] && cool[2] == 1.0F){
				cool[2] = 0F;
				p.sendMessage("lightning SPELL");
			}
			//FREEZE SPELL
			if(p.getInventory().getItemInMainHand().getType() == hoes[3] && cool[3] == 1.0F){
				cool[3] = 0F;
				p.sendMessage("freeze SPELL");
			}
			//HEAL SPELL
			if(p.getInventory().getItemInMainHand().getType() == hoes[4] && cool[4] == 1.0F){
				//pot health
				ItemStack heal = BaseKit.setItemName(new ItemStack(Material.SPLASH_POTION), ChatColor.RESET+"Splash Potion of Health");
				PotionMeta healMeta = (PotionMeta) heal.getItemMeta();
				healMeta.setColor(BaseKit.cColor[Main.getTeamId(p)]);
				healMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 3), false);
				
				cool[4] = 0F;
				Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
				ThrownPotion potion = (ThrownPotion) p.getWorld().spawnEntity(loc, EntityType.SPLASH_POTION);
				//potion.setItem(new ItemStack(Material.RAW_FISH, 1, (short) 3));
				//potion.setItem(new ItemStack(Material.POTION))
				potion.setItem(heal);
				p.sendMessage("hp SPELL");
			}
			//WEEE SPELL
			if(p.getInventory().getItemInMainHand().getType() == hoes[5] && cool[5] == 1.0F){
				cool[5] = 0F;
				Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
				Pig pig = (Pig) p.getWorld().spawnEntity(loc, EntityType.PIG);
				pig.setVelocity(directionCoord(p, 3));
				pig.addPassenger(p);
			}
		}
	}
	@Override
	public void update1(){
		boolean nospell=true;
		for(int i= 0; i<6; i++){
			if(cool[i]+ratio[i] > 1){
				cool[i] = 1.0F;
			}
			else{
				cool[i] = cool[i]+ratio[i];
			}
			if(p.getInventory().getItemInMainHand().getType() == hoes[i]){
				p.setExp(cool[i]);
				nospell=false;
			}
		}
		if(nospell){
			p.setExp(1.0F);
		}
	}
	public static void dmgExplosion(Location loc, int tColor){
		Color[] fCol = {Color.BLACK, Color.BLUE, Color.OLIVE, Color.TEAL, Color.RED, Color.PURPLE, Color.ORANGE, Color.GRAY, Color.GRAY, Color.BLUE, Color.GREEN, Color.AQUA, Color.RED, Color.FUCHSIA, Color.YELLOW, Color.WHITE};
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fm = fw.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder().withColor(Color.PURPLE, fCol[tColor]).with(Type.BALL).build());
		fw.setFireworkMeta(fm);
		TimeManager.toDetonate.add(fw);
	}
	private static Vector directionCoord(Player p, double multiplyer){
		double yaw = p.getEyeLocation().getYaw();
		double pitch=p.getEyeLocation().getPitch();
		while(yaw<0){yaw+=360;}
		while(yaw>360){yaw-=360;}
		double x = 1;double y = 1; double z = 1;
		if(yaw<=90){
			x=-(yaw/90);
			z-=(yaw/90);
		}
		else if(yaw<=180){
			yaw-=90;
			x=-(1-yaw/90);
			z*=-(yaw/90);
		}
		else if(yaw<=270){
			yaw-=180;
			x=yaw/90;
			z=-1+(yaw/90);
		}
		else{
			yaw-=270;
			z=yaw/90;
			x=1-(yaw/90);
		}
		if(pitch<0){
			pitch*=-1;
			y=pitch/90;
			double a = ((pitch-45)*-1)+45;
			x*=a/90;
			z*=a/90;
		}
		else{
			y=-(pitch/90);
			double a = ((pitch-45)*-1)+45;
			x*=a/90;
			z*=a/90;
		}
		x*=multiplyer;
		y*=multiplyer;
		z*=multiplyer;
		return new Vector(x, y, z);
	}
	@Override
	public String getKitName() {
		return("Mage");
	}

}
