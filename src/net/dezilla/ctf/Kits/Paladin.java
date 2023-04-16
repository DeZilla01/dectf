package net.dezilla.ctf.Kits;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.Main;

public class Paladin extends BaseKit{
	boolean archerimunity = false;
	Material lastUsed = Material.AIR;

	public Paladin(Player player, int variation) {
		super(player, variation);
	}
	@Override
	public void newInventory() {
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//items
		ItemStack chest = BaseKit.setItemName(new ItemStack(Material.DIAMOND_CHESTPLATE), ChatColor.RESET+"Holy Chestplate");
		ItemStack legs = BaseKit.setItemName(new ItemStack(Material.IRON_LEGGINGS), ChatColor.RESET+"Holy Leggings");
		ItemStack boots = BaseKit.setItemName(new ItemStack(Material.DIAMOND_BOOTS), ChatColor.RESET+"Holy Boots");
		ItemStack sword = BaseKit.setItemName(new ItemStack(Material.IRON_SWORD), ChatColor.RESET+"Paladin Sword");
		ItemStack door = BaseKit.setItemName(new ItemStack(Material.IRON_DOOR), ChatColor.RESET+"Paladin Shield");
		ItemStack purpledisc = BaseKit.setItemName(new ItemStack(Material.MUSIC_DISC_MALL), ChatColor.RESET+"Aura of Resistance");
		ItemStack greendisc = BaseKit.setItemName(new ItemStack(Material.MUSIC_DISC_CAT), ChatColor.RESET+"Aura of Leaping");
		ItemStack brokendisc = BaseKit.setItemName(new ItemStack(Material.MUSIC_DISC_11), ChatColor.RESET+"Aura of Slowness");
		ItemStack reddisc = BaseKit.setItemName(new ItemStack(Material.MUSIC_DISC_CHIRP), ChatColor.RESET+"Aura of Weakness");
		ItemStack golddisc = BaseKit.setItemName(new ItemStack(Material.MUSIC_DISC_13), ChatColor.RESET+"Aura of Regeneration");
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		legs.addEnchantment(Enchantment.PROTECTION_FIRE, 2);
		boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
		door.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
		//apply
		inv.setItem(0, sword);
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 2));
		inv.setItem(2, door);
		inv.setItem(3, purpledisc);
		inv.setItem(4, greendisc);
		inv.setItem(5, brokendisc);
		inv.setItem(6, reddisc);
		inv.setItem(7, golddisc);
		inv.setBoots(BaseKit.setAtkSpeed(boots));
		inv.setLeggings(legs);
		inv.setChestplate(chest);
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void update5() {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item!=null) {
			//SHIELD
			if(item.getType() == Material.IRON_DOOR && !archerimunity) {
				archerimunity = true;
				if(p.getInventory().getHelmet() == null) {
					p.getInventory().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
				}
				if(lastUsed != Material.IRON_DOOR) {
					p.playSound(p.getLocation(), "block.anvil.land", 1.0F, 1.0F);
				}
			}
			else if(archerimunity && item.getType() != Material.IRON_DOOR){
				archerimunity = false;
				if(p.getInventory().getHelmet() != null) {
					p.getInventory().setHelmet(null);
				}
			}
			//AURA
			if(item.getType() == Material.MUSIC_DISC_MALL) {
				for(Player i : getNearPlayers(5, true)) {
					i.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30, 0));
				}
				if(lastUsed != Material.MUSIC_DISC_MALL) {
					p.playSound(p.getLocation(), "item.shield.block", 1.0F, 1.0F);
				}
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 3);
			}
			if(item.getType() == Material.MUSIC_DISC_CAT) {
				for(Player i : getNearPlayers(5, true)) {
					i.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30, 1));
				}
				if(lastUsed != Material.MUSIC_DISC_CAT) {
					p.playSound(p.getLocation(), "entity.rabbit.hurt", 1.0F, 1.0F);
				}
			}
			if(item.getType() == Material.MUSIC_DISC_11) {
				for(Player i : getNearPlayers(5, false)) {
					i.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 0));
				}
				if(lastUsed != Material.MUSIC_DISC_11) {
					p.playSound(p.getLocation(), "item.bottle.fill_dragonbreath", 1.0F, 1.0F);
				}
			}
			if(item.getType() == Material.MUSIC_DISC_CHIRP) {
				for(Player i : getNearPlayers(5, false)) {
					i.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30, 0));
				}
				if(lastUsed != Material.MUSIC_DISC_CHIRP) {
					p.playSound(p.getLocation(), "enchant.thorns.hit", 1.0F, 1.0F);
				}
			}
			if(item.getType() == Material.MUSIC_DISC_13) {
				for(Player i : getNearPlayers(5, true)) {
					if(!i.hasPotionEffect(PotionEffectType.REGENERATION)) {
						i.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 0));
					}
				}
				if(lastUsed != Material.MUSIC_DISC_13) {
					p.playSound(p.getLocation(), "block.brewing_stand.brew", 1.0F, 1.0F);
				}
			}
			lastUsed = p.getInventory().getItemInMainHand().getType();
		}
	}
	@Override
	public void onDamaged(EntityDamageEvent e) {
		super.onDamaged(e);
		if(archerimunity) {
			 p.setVelocity(p.getLocation().getDirection().multiply(0));
			 if(e.getCause() == DamageCause.PROJECTILE) {
				 e.setDamage(e.getDamage()*.2);
			 }
		}
	}
	public String getKitName() {
		return("Paladin");
	}
	private Player[] getNearPlayers(int radius, boolean sameTeam){
		ArrayList<Player> list = new ArrayList<Player>();
		for(Player i : Bukkit.getServer().getOnlinePlayers()) {
			if(p.getLocation().distance(i.getLocation()) < radius) {
				if(Main.getTeamId(p) == Main.getTeamId(i) && sameTeam && p != i) {
					list.add(i);
				}
				else if(Main.getTeamId(p) != Main.getTeamId(i) && !sameTeam && p != i) {
					list.add(i);
				}
			}
		}
		return list.toArray(new Player[list.size()]);
	}

}
