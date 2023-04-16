package net.dezilla.ctf.Structures;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.dezilla.ctf.Main;

public class Turret extends BaseStructure{
	public ArmorStand disp;
	public double hp;
	Block fence;
	Location pos;
	Player target;
	boolean fenceplaced;
	ChatColor color;

	@SuppressWarnings("deprecation")
	public Turret(Player p, Block loc) {
		super(p, loc);
		hp = 30.0;
		color = Main.mConfig.M_teamCol.get(team);
		//fence
		previousStuff.add(Material.AIR);
		loc.setType(Material.LEGACY_FENCE);
		strucBlocks.add(loc);
		fence = loc;
		fenceplaced = false;
		//loc
		pos = loc.getLocation();
		pos.setX(pos.getX()+.5);
		pos.setZ(pos.getZ()+.5);
		pos.setY(pos.getY()-.6);
		//dispencer
		disp = (ArmorStand) Main.mConfig.M_world.spawnEntity(pos, EntityType.ARMOR_STAND);
		disp.setHelmet(new ItemStack(Material.DISPENSER));
		disp.setInvulnerable(false);
		disp.setVisible(false);
		disp.setGravity(false);
		disp.setCustomNameVisible(true);
		disp.setCustomName(color+Double.toString(hp)+" \u2764");
		//loc2
		pos.setY(pos.getY()+1.9);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void update1tick(){
		if(!fenceplaced){
			fence.setType(Material.LEGACY_FENCE);
		}
		double range = 15.0;
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			if(pos.distance(p.getLocation()) < range && Main.mConfig.M_match.getPlayerTeam(p) != team){
				target = p;
				range = pos.distance(p.getLocation());
			}
		}
		if(range == 15.0){
			target = null;
		}
		if(target != null){
			Location lt = target.getLocation();
			double x = pos.getX()-lt.getX();
			double y = pos.getY()-(lt.getY()+1);
			double z = pos.getZ()-lt.getZ();
			Location ls = disp.getLocation();
			ls.setDirection(new Vector(x, y, z));
			ls.setYaw(ls.getYaw()+180);
			disp.teleport(ls);
		}
		disp.setCustomName(color+Double.toString(hp)+" \u2764");
		if(disp.isDead() || !owner.isOnline() || hp<=0){
			destroy();
		}
	}
	@Override
	public void update20tick(){
		if(target!=null){
			Location l = target.getLocation();
			double x = pos.getX()-l.getX();
			double y = pos.getY()-(l.getY()+1);
			double z = pos.getZ()-l.getZ();
			x*=5;
			y*=5;
			z*=5;
			Arrow arrow = (Arrow) Main.mConfig.M_world.spawnEntity(pos, EntityType.ARROW);
			arrow.setVelocity(new Vector(x, y, z));
			arrow.setShooter(owner);
		}
	}
	@Override
	public void destroy(){
		super.destroy();
		if(!disp.isDead()){
			disp.remove();
		}
	}
	public static boolean SpaceCheck(Block loc){
		boolean check = BaseStructure.SpaceCheck(loc);
		if(check){
			if(loc.getRelative(BlockFace.UP).getType() != Material.AIR){
				check=false;
			}
		}
		return check;
	}

}
