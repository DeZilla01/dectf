package net.dezilla.ctf.Kits;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.Structures.BaseStructure;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;

public class BaseKit {
	//Color Reference
	static Color[] cColor = {Color.BLACK, Color.BLUE, Color.GREEN, Color.TEAL, Color.RED, Color.PURPLE, Color.ORANGE, Color.SILVER, Color.GRAY, Color.BLUE, Color.LIME, Color.TEAL, Color.RED, Color.FUCHSIA, Color.YELLOW, Color.WHITE};
	//BaseKit
	Player p;
	int variationID = 0;
	
	public BaseKit(Player player, int variation){
		this.p = player;
	}
	
	public void onItemClick(PlayerInteractEvent e){
	}
	public boolean strucClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			Block blok = e.getClickedBlock();
			for(BaseStructure struc : Main.structures){
				for(Block sblok : struc.strucBlocks){
					if(blok.getLocation().distance(sblok.getLocation()) < .5){
						struc.onClick(p);
						e.setCancelled(true);
						return true;
					}
				}
			}
		}
		return false;
	}
	public void onEntityInteract(PlayerInteractEntityEvent e){
		
	}
	
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		setHunger();
		resExp();
		potEffect();
	}
	
	public void onDamage(Player victim, EntityDamageByEntityEvent e){
		if(Main.mConfig.M_match.getPlayerTeam(p) == Main.mConfig.M_match.getPlayerTeam(victim)){
			e.setCancelled(true);
		}
	}
	
	public void onBowAttack(Player victim, EntityDamageByEntityEvent e){
		
	}
	public void setHelmet(){
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
	}
	public void setHunger(){
		p.setFoodLevel(18);
	}
	public void update20() {
	}
	public void update5(){
	}
	public void update1(){
		
	}
	public void resExp(){
		p.setExp(0);
	}
	public void potEffect(){
		for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
	}
	public void onDamaged(EntityDamageEvent e) {
		
	}
	public void onProjectileHitEvent(ProjectileHitEvent e){
	}
	public void onBlockPlace(BlockPlaceEvent e){
		e.setCancelled(true);
	}
	public void onBlockBreak(BlockBreakEvent e){
		boolean check = true;
		ArrayList<BaseStructure> strucs = new ArrayList<BaseStructure>();
		for(BaseStructure i : Main.structures){
			strucs.add(i);
		}
		for(BaseStructure struc : strucs){
			for(Block blok : struc.strucBlocks){
				if(e.getBlock().getLocation().distance(blok.getLocation()) <.5){
					if(p.getName().equalsIgnoreCase(struc.owner.getName())){
						struc.destroy();
						check = false;
					}
					else if(struc.team != Main.mConfig.M_match.getPlayerTeam(p)){
						struc.destroy();
						check = false;
					}
				}
			}
		}
		if(check){
			e.setCancelled(true);
		}
	}
	public void onShootBow(EntityShootBowEvent e){
	}
	public static void kitSwitch(String kitName, Player player, int variation){
		if(kitName.equalsIgnoreCase("heavy")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Heavy(player, variation));
		}
		if(kitName.equalsIgnoreCase("medic")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Medic(player, variation));
		}
		if(kitName.equalsIgnoreCase("archer")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Archer(player, variation));
		}
		if(kitName.equalsIgnoreCase("soldier")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Soldier(player, variation));
		}
		if(kitName.equalsIgnoreCase("dragger")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Dragger(player, variation));
		}
		if(kitName.equalsIgnoreCase("chemist")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Chemist(player, variation));
		}
		if(kitName.equalsIgnoreCase("pyro")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Pyro(player, variation));
		}
		if(kitName.equalsIgnoreCase("ninja")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Ninja(player, variation));
		}
		if(kitName.equalsIgnoreCase("tank")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Tank(player, variation));
		}
		if(kitName.equalsIgnoreCase("engineer")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Engineer(player, variation));
		}
		if(kitName.equalsIgnoreCase("mage")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Mage(player, variation));
		}
		if(kitName.equalsIgnoreCase("warlock")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Warlock(player, variation));
		}
		if(kitName.equalsIgnoreCase("necro")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Necro(player, variation));
		}
		if(kitName.equalsIgnoreCase("fisher")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Fisher(player, variation));
		}
		if(kitName.equalsIgnoreCase("assassin")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Assassin(player, variation));
		}
		if(kitName.equalsIgnoreCase("dwarf")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Dwarf(player, variation));
		}
		if(kitName.equalsIgnoreCase("elf")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Elf(player, variation));
		}
		if(kitName.equalsIgnoreCase("fashionista")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Fashionista(player, variation));
		}
		if(kitName.equalsIgnoreCase("scout")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Scout(player, variation));
		}
		if(kitName.equalsIgnoreCase("wraith")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Wraith(player, variation));
		}
		if(kitName.equalsIgnoreCase("tester")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Tester(player, variation));
		}
		if(kitName.equalsIgnoreCase("paladin")){
			BaseKit kit = Main.grabKit(player);
			int index = Main.Kkits.indexOf(kit);
			Main.Kkits.remove(index);
			Main.Kkits.add(index, new Paladin(player, variation));
		}
	}
	public static int getVariation(String classname, String variation) {
		if(classname.equalsIgnoreCase("heavy")) {
			int count = 0;
			for(String i : Heavy.Variations) {
				if(i.equalsIgnoreCase(variation)) {
					return count;
				}
				count+=1;
			}
		}
		return 0;
	}
	public String getKitName() {
		return("Kit");
	}
	public static String[] getKitList(){
		String[] kList = {"heavy", "medic", "soldier", "archer", "dragger", "tank", "ninja", "pyro", "chemist", "engineer", "warlock", "necro"};
		return kList;
	}
	public static ItemStack setAtkSpeed(ItemStack item){
		NBTTagList list = new NBTTagList();
		NBTTagCompound comp = new NBTTagCompound();
		comp.setInt("Amount", 99999999);
		comp.setString("AttributeName", "generic.attackSpeed");
		comp.setString("Name", "generic.attackSpeed");
		comp.setInt("Operation", 0);
		comp.setInt("UUIDLeast", 161150);
		comp.setInt("UUIDMost", 90498);
		list.add(comp);
		net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		itemCompound.set("AttributeModifiers", list);
		nmsItem.setTag(itemCompound);
		ItemStack result = CraftItemStack.asBukkitCopy(nmsItem);
		return result;
	}
	public static ItemStack setLeatherColor(ItemStack item, int color) {
		LeatherArmorMeta imeta = (LeatherArmorMeta) item.getItemMeta();
		imeta.setColor(Color.fromRGB(color));
		item.setItemMeta(imeta);
		return item;
	}
	public static ItemStack setItemName(ItemStack item, String name) {
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName(name);
		item.setItemMeta(imeta);
		return item;
	}
	public static Player getTargetPlayer(Player p, int range){
		Location loc = p.getLocation();
		float yaw   = loc.getYaw();
		float pitch = loc.getPitch();
		double x = 1.0;
		double y = 1.0;
		double z = 1.0;
		while(yaw < 0){
			yaw+=360;
		}
		while(yaw>=360){
			yaw-=360;
		}
		if(yaw<90){
			double a = yaw/90;
			z-= a;
			x*=-a;
		}
		else if(yaw<180){
			double b = yaw-90;
			double a = b/90;
			x*=-1;
			x+= a;
			z*=-a;
		}
		else if(yaw<270){
			double b = yaw-180;
			double a = b/90;
			z*=-1;
			z+= a;
			x*= a;
		}
		else{
			double b = yaw-270;
			double a = b/90;
			x-=a;
			z*=a;
		}
		if(pitch<90){
			double a = pitch/-90;
			double b = ((a-.5)*-1)+.5;
			x*=b;
			z*=b;
			y*=a;
		}
		else{
			double a = pitch/90;
			double b = ((a-.5)*-1)+.5;
			x*=b;
			z*=b;
			y*=-a;
		}
		int count = 0;
		loc.add(0.0, 1.0, 0.0);
		while(count < range){
			count+=1;
			loc.add(x, y, z);
			for(Entity ent : loc.getWorld().getNearbyEntities(loc, 1.0, 0.8, 1.0)){
				if(ent instanceof Player && ent != p){
					return (Player) ent;
				}
			}
			if(loc.getBlock().getType() != Material.AIR){
				count = range;
			}
		}
		return null;
	}

}
