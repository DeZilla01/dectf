package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.ScoreManager;

public class Archer extends BaseKit{
	String[] variations = {"Default", "New"};

	public Archer(Player player, int variation) {
		super(player, variation);
		if(variation==1) {
			variationID=1;
		}
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setItem(0, new ItemStack(Material.STONE_SWORD));
		inv.setItem(1, new ItemStack(Material.BOW));
		inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
		inv.setItem(9, new ItemStack(Material.ARROW,64));
		inv.setItem(10, new ItemStack(Material.ARROW,64));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.CHAINMAIL_BOOTS)));
		inv.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void onBowAttack(Player victim, EntityDamageByEntityEvent e){
		boolean isSpawn = victim.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() !=  Main.mConfig.M_teamSblock.get(Main.mConfig.M_match.getPlayerTeam(victim));
		//Snipe Insta-Kill
		if(p.getLocation().distance(victim.getLocation()) >= 30 && isSpawn && variationID==0){
			if(Main.grabKit(victim) instanceof Paladin) {
				Paladin kit = (Paladin) Main.grabKit(victim);
				if(kit.archerimunity) {
					return;
				}
			}
			ScoreManager.remDeath(victim);
			victim.sendMessage("Sniped by "+p.getName());
			e.setDamage(999.0);
		}
	}
	@Override
	public void setHelmet(){
		p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
	}
	@Override
	public String getKitName() {
		if(variationID==1) {
			return("Archer (New) - need to find a name later");
		}
		return("Archer (Default)");
	}

}
