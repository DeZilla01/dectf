package net.dezilla.ctf.Entity;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;

import net.dezilla.ctf.Main;

public class NecroSkeleton extends BaseEntity{
	Skeleton skeleton = null;

	public NecroSkeleton(Player player, Location loc) {
		super(player, loc);
		summon(loc);
	}
	
	@Override
	public void summon(Location loc) {
		mainEntity = loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		skeleton = (Skeleton) mainEntity;
		skeleton.setCustomName(Main.mConfig.M_teamCol.get(Main.getTeamId(owner))+owner.getName()+"'s minion");
		skeleton.setCustomNameVisible(true);
		EntityEquipment equip = skeleton.getEquipment();
		equip.setHelmet(getHelmet());
	}
	
	@Override
	public void update1() {
		if(skeleton.getTarget() instanceof Player) {
			if(Main.getTeamId((Player) skeleton.getTarget()) == teamId) {
				skeleton.setTarget(null);
			}
			else if(skeleton.getTarget().getLocation().distance(skeleton.getLocation())<=15) {
				return;
			}
		}
		Player nextTarget = null;
		double distance = 15;
		for(Player target : Bukkit.getServer().getOnlinePlayers()) {
			if(Main.getTeamId(target) == teamId || target.getGameMode() != GameMode.SURVIVAL || target.getLocation().distance(skeleton.getLocation())>15) {
				continue;
			}
			if(target.getLocation().distance(skeleton.getLocation()) < distance) {
				nextTarget = target;
				distance = target.getLocation().distance(skeleton.getLocation());
			}
		}
		skeleton.setTarget(nextTarget);
		
	}


}
