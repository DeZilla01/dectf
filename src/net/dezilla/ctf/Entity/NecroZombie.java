package net.dezilla.ctf.Entity;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;

import net.dezilla.ctf.Main;

public class NecroZombie extends BaseEntity{
	Zombie zombie;

	public NecroZombie(Player player, Location loc) {
		super(player, loc);
		summon(loc);
	}
	
	@Override
	public void summon(Location loc) {
		mainEntity = loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		zombie = (Zombie) mainEntity;
		zombie.setCustomName(Main.mConfig.M_teamCol.get(Main.getTeamId(owner))+owner.getName()+"'s minion");
		zombie.setCustomNameVisible(true);
		EntityEquipment equip = zombie.getEquipment();
		equip.setHelmet(getHelmet());
	}
	
	@Override
	public void update1() {
		if(zombie.getTarget() instanceof Player) {
			if(Main.getTeamId((Player) zombie.getTarget()) == teamId) {
				zombie.setTarget(null);
			}
			else if(zombie.getTarget().getLocation().distance(zombie.getLocation())<=15) {
				return;
			}
		}
		Player nextTarget = null;
		double distance = 15;
		for(Player target : Bukkit.getServer().getOnlinePlayers()) {
			if(Main.getTeamId(target) == teamId || target.getGameMode() != GameMode.SURVIVAL || target.getLocation().distance(zombie.getLocation())>15) {
				continue;
			}
			if(target.getLocation().distance(zombie.getLocation()) < distance) {
				nextTarget = target;
				distance = target.getLocation().distance(zombie.getLocation());
			}
		}
		zombie.setTarget(nextTarget);
		
	}

}
