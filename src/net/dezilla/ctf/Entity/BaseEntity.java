package net.dezilla.ctf.Entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.Kits.BaseKit;

public class BaseEntity {
	Player owner;
	int teamId;
	Entity mainEntity;
	boolean alive = true;
	
	public BaseEntity(Player player, Location loc) {
		owner = player;
		teamId = Main.getTeamId(player);
		Main.entities.add(this);
	}
	
	public void summon(Location loc) {
		
	}
	
	public void kill() {
		if(mainEntity != null) {
			mainEntity.remove();
		}
		Main.entities.remove(this);
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public ItemStack getHelmet() {
		int[] colors = {0x000000, 0x0000be, 0x00be00, 0x00bebe, 0xbe0000, 0xbe00be, 0xd9a334, 0xbebebe, 0x3f3f3f, 0x3f3ffe, 0x3ffe3f, 0x3ffefe, 0xfe3f3f, 0xfe3ffe, 0xfefe3f, 0xffffff};
		ItemStack helmet = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_HELMET), colors[Main.mConfig.M_teamColInt.get(Main.getTeamId(owner))]);
		return helmet;
	}
	
	public void update5() {
		if(mainEntity.isDead()) {
			kill();
		}
	}
	public void update1() {
		
	}
	public void update20() {
	}

}
