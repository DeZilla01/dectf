package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.dezilla.ctf.Structures.BaseStructure;
import net.dezilla.ctf.Structures.Dispencer;
import net.dezilla.ctf.Structures.Entry;
import net.dezilla.ctf.Structures.Exit;
import net.dezilla.ctf.Structures.Turret;

public class Engineer extends BaseKit {
	public BaseStructure dispencer = null;
	public BaseStructure turret = null;
	public BaseStructure entry = null;
	public BaseStructure exit = null;

	public Engineer(Player player, int variation) {
		super(player, variation);
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//pick
		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
		//pick.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		//pick.addEnchantment(Enchantment.KNOCKBACK, 1);
		//apply
		inv.setItem(0, pick);
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF,4));
		inv.setItem(2, new ItemStack(Material.WOODEN_SWORD));
		inv.setItem(3, new ItemStack(Material.DISPENSER));
		inv.setItem(4, new ItemStack(Material.CAKE));
		inv.setItem(5, new ItemStack(Material.STONE_PRESSURE_PLATE));
		inv.setItem(6, new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.IRON_BOOTS)));
		inv.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.IRON_HELMET));
		setHunger();
		resExp();
		potEffect();
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onBlockPlace(BlockPlaceEvent e){
		activeCheck();
		if(e.getBlock().getType() == Material.CAKE){
			if(Dispencer.SpaceCheck(e.getBlock()) && this.dispencer == null){
				this.dispencer = new Dispencer(p, e.getBlock());
				p.setItemInHand(new ItemStack(Material.CAKE));
			}
			else{
				e.setCancelled(true);
			}
		}
		else if(e.getBlock().getType() == Material.DISPENSER){
			if(Turret.SpaceCheck(e.getBlock()) && this.turret == null && p.getExp() == 0){
				this.turret = new Turret(p, e.getBlock());
				p.setItemInHand(new ItemStack(Material.DISPENSER));
				p.setExp((float) 1.0);
			}
			e.setCancelled(true);
		}
		else if(e.getBlock().getType() == Material.STONE_PRESSURE_PLATE){
			if(BaseStructure.SpaceCheck(e.getBlock()) && this.entry == null){
				this.entry = new Entry(p, e.getBlock());
				p.setItemInHand(new ItemStack(Material.STONE_PRESSURE_PLATE));
			}
			else{
				e.setCancelled(true);
			}
		}
		else if(e.getBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE){
			if(BaseStructure.SpaceCheck(e.getBlock()) && this.exit == null){
				this.exit = new Exit(p, e.getBlock());
				p.setItemInHand(new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE));
			}
			else{
				e.setCancelled(true);
			}
		}
		else{
			e.setCancelled(true);
		}
	}
	@Override
	public void onBowAttack(Player victim, EntityDamageByEntityEvent e){
		e.setDamage(3.0);
	}
	private void activeCheck(){
		if(dispencer != null){
			if(!dispencer.active){
				dispencer = null;
			}
		}
		if(turret != null){
			if(!turret.active){
				turret = null;
			}
		}
		if(entry != null){
			if(!entry.active){
				entry = null;
			}
		}
		if(exit != null){
			if(!exit.active){
				exit = null;
			}
		}
	}
	@Override
	public String getKitName() {
		return("Engineer");
	}
}
