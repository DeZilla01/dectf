package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.TimeManager;

public class Medic extends BaseKit{

	public Medic(Player player, int variation) {
		super(player, variation);
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//sword
		ItemStack sword = new ItemStack(Material.GOLDEN_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		//apply
		inv.setItem(0, sword);
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF,6));
		inv.setItem(2, new ItemStack(Material.SNOWBALL,5));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.GOLDEN_BOOTS)));
		inv.setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
		setHunger();
		resExp();
		potEffect();
	}
	public void setHelmet(){
		p.getInventory().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onEntityInteract(PlayerInteractEntityEvent e){
		if(p.getItemInHand().getType() == Material.GOLDEN_SWORD){
			if(e.getRightClicked() instanceof Player){
				Player target = (Player) e.getRightClicked();
				if(Main.mConfig.M_match.getPlayerTeam(p) == Main.mConfig.M_match.getPlayerTeam(target)){
					target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 3));
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onDamage(Player victim, EntityDamageByEntityEvent e){
		if(p.getItemInHand().getType() == Material.GOLDEN_SWORD){
			if(Main.mConfig.M_match.getPlayerTeam(p) == Main.mConfig.M_match.getPlayerTeam(victim)){
				victim.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 3));
				e.setCancelled(true);
			}
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onProjectileHitEvent(ProjectileHitEvent e){
		if(e.getEntity() instanceof Snowball){
			Snowball sb = (Snowball) e.getEntity();
			Block loc = sb.getLocation().getBlock();
			if(loc.getType() != Material.AIR){
				if(loc.getRelative(BlockFace.UP).getType() == Material.AIR){
					loc = loc.getRelative(BlockFace.UP);
					loc.setType(Material.LEGACY_WEB);
					TimeManager timer = new TimeManager(Main.plugin, 4, 0, null, loc, null, null, null);
					timer.runTaskTimer(Main.plugin, 0, 20);
				}
			}
			else{
				loc.setType(Material.LEGACY_WEB);
				TimeManager timer = new TimeManager(Main.plugin, 4, 4, null, loc, null, null, null);
				timer.runTaskTimer(Main.plugin, 0, 20);
			}
		}
	}
	@Override
	public void potEffect(){
		for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
		p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999999, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 999999999, 0));
	}
	@Override
	public String getKitName() {
		return("Medic");
	}


}
