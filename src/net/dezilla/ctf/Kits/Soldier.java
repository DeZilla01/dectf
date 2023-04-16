package net.dezilla.ctf.Kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class Soldier extends BaseKit{
	static String[] Variations = {"Default", "Cannon"};
	String[] swordNames = {ChatColor.RESET+"Sword 1", ChatColor.RESET+"Sword 2", ChatColor.RESET+"Sword 3"};
	float[] charge = {0.0F, 0.0F, 0.0F};

	public Soldier(Player player, int variation) {
		super(player, variation);
		if(variation==1) {
			variationID=1;
		}
	}
	@Override
	public void onItemClick(PlayerInteractEvent e){
		EquipmentSlot slot = e.getHand();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && slot.equals(EquipmentSlot.HAND)){
			if(p.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD && variationID==0){
				p.setVelocity(new Vector(0,.8,0));
			}
			if(p.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD && variationID==1) {
				int c = 0;
				for(String i : swordNames) {
					if(i.equalsIgnoreCase(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()) && charge[c] >.15) {
						p.setVelocity(oppositeVector(directionCoord(p, (double) 5.0*charge[c])));
						charge[c] = 0F;
					}
					c+=1;
				}
			}
		}
		if(e.getAction() == Action.RIGHT_CLICK_AIR && slot.equals(EquipmentSlot.HAND)) {
			if(p.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD && variationID==1) {
				int c = 0;
				for(String i : swordNames) {
					if(i.equalsIgnoreCase(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
						p.setVelocity(directionCoord(p, (double) 5.0*charge[c]*.65));
						charge[c] = 0F;
					}
					c+=1;
				}
			}
		}
		
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		if (variationID==1) {
			inv.setItem(0, BaseKit.setItemName(new ItemStack(Material.IRON_SWORD), ChatColor.RESET+"Sword 1"));
			inv.setItem(1, BaseKit.setItemName(new ItemStack(Material.IRON_SWORD), ChatColor.RESET+"Sword 2"));
			inv.setItem(2, BaseKit.setItemName(new ItemStack(Material.IRON_SWORD), ChatColor.RESET+"Sword 3"));
			inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 3));
		}
		else {
			inv.setItem(0, new ItemStack(Material.IRON_SWORD));
			inv.setItem(1, new ItemStack(Material.COOKED_BEEF,4));
		}
		inv.setBoots(setAtkSpeed(new ItemStack(Material.IRON_BOOTS)));
		inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		inv.setHelmet(new ItemStack(Material.IRON_HELMET));
		setHunger();
		resExp();
		potEffect();
	}
	@Override
	public void setHelmet(){
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
	}
	@Override
	public void update5() {
		//Charging
		if(variationID==1) {
			ItemStack item = p.getInventory().getItemInMainHand();
			if(item != null) {
				if(item.getType()==Material.IRON_SWORD) {
					int c = 0;
					for(String i : swordNames) {
						if(item.getItemMeta().getDisplayName().equalsIgnoreCase(i)) {
							if(p.isSneaking()) {
								charge[c] += .03;
							}
							else {
								charge[c] += .008;
							}
							if(charge[c]>1) {
								charge[c]=1;
							}
						}
						c+=1;
					}
				}
			}
		}
		//Updating
		if(variationID==1) {
			ItemStack item = p.getInventory().getItemInMainHand();
			if(item != null) {
				int c = 0;
				for(String i : swordNames) {
					if(item.getType()==Material.IRON_SWORD && item.getItemMeta().getDisplayName().equalsIgnoreCase(i)) {
						p.setExp(charge[c]);
					}
					c+=1;
				}
				if(item.getType()!=Material.IRON_SWORD) {
					p.setExp(0F);
				}
			}
		}
	}
	@Override
	public String getKitName() {
		if(variationID==1) {
			return("Soldier (Cannon)");
		}
		return("Soldier (Default)");
	}
	private static Vector oppositeVector(Vector v) {
		double x = v.getX()*-1;
		double y = v.getY()*-1;
		double z = v.getZ()*-1;
		return new Vector(x, y, z);
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

}
