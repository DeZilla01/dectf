package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.dezilla.ctf.Entity.NecroZombie;
import net.dezilla.ctf.Structures.Spawner;


public class Necro extends BaseKit{
	//public ArrayList<Spawner> spawners = new ArrayList<Spawner>();
	public Spawner spawner = null;

	public Necro(Player player, int variation) {
		super(player, variation);
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//apply
		inv.setItem(0, new ItemStack(Material.DIAMOND_PICKAXE));
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF,5));
		inv.setItem(2, new ItemStack(Material.GOLDEN_SWORD));
		inv.setItem(3, new ItemStack(Material.SPAWNER));
		inv.setItem(4, new ItemStack(Material.ZOMBIE_SPAWN_EGG));
		inv.setItem(5, new ItemStack(Material.SKELETON_SPAWN_EGG));
		inv.setItem(7, new ItemStack(Material.LAPIS_ORE));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.IRON_BOOTS)));
		inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.IRON_HELMET));
		setHunger();
		resExp();
		potEffect();
	}
	public void onBlockPlace(BlockPlaceEvent e){
		activeCheck();
		if(e.getBlock().getType() == Material.SPAWNER){
			if(Spawner.SpaceCheck(e.getBlock()) && spawner == null){
				spawner = new Spawner(p, e.getBlock());
				p.getInventory().setItemInMainHand(new ItemStack(Material.SPAWNER));
			}
		}
		e.setCancelled(true);
	}
	@Override
	public void onItemClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getInventory().getItemInMainHand().getType() == Material.LAPIS_ORE) {
			new NecroZombie(p, e.getClickedBlock().getRelative(BlockFace.UP).getLocation());
			e.setCancelled(true);
		}
	}
	@Override
	public void update5(){
		activeCheck();
		
	}
	private void activeCheck(){
		if(spawner != null) {
			if(!spawner.active) {
				spawner = null;
			}
		}
	}
	@Override
	public String getKitName() {
		return("Necro");
	}
}
