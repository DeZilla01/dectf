package net.dezilla.ctf.Structures;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.dezilla.ctf.Entity.BaseEntity;
import net.dezilla.ctf.Entity.NecroSkeleton;
import net.dezilla.ctf.Entity.NecroZombie;

public class Spawner extends BaseStructure{
	EntityType monsterType = EntityType.ZOMBIE;
	CreatureSpawner spawner;
	Block fence;
	boolean fenceplaced = false;
	int delayTillNextSpawn = 3;
	ArrayList<BaseEntity> zombies = new ArrayList<BaseEntity>();

	public Spawner(Player p, Block loc) {
		super(p, loc);
		//fence
		previousStuff.add(Material.AIR);
		loc.setType(Material.NETHER_BRICK_FENCE);
		strucBlocks.add(loc);
		fence = loc;
		//spawn
		Block spblock = loc.getRelative(BlockFace.UP);
		previousStuff.add(spblock.getType());
		spblock.setType(Material.SPAWNER);
		strucBlocks.add(spblock);
		spawner = (CreatureSpawner) spblock.getState();
		spawner.setSpawnedType(EntityType.ZOMBIE);
		spawner.setDelay(60000000);
		spawner.update();
	}
	@Override
	public void update1tick(){
		if(!fenceplaced){
			fence.setType(Material.NETHER_BRICK_FENCE);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void update20tick() {
		//Remove dead zombies from list
		ArrayList<BaseEntity> zombiz = (ArrayList<BaseEntity>) zombies.clone();
		for(BaseEntity zombie : zombiz) {
			if(!zombie.isAlive()) {
				zombies.remove(zombie);
			}
		}
		//Check if Zombie list is full
		if(zombies.size() >=5) {
			delayTillNextSpawn = 15;
		}
		//Spawn a new zombie
		if(delayTillNextSpawn <= 0) {
			if(monsterType == EntityType.SKELETON) {
				zombies.add(new NecroSkeleton(owner, fence.getLocation()));
			} else {
				zombies.add(new NecroZombie(owner, fence.getLocation()));
			}
			delayTillNextSpawn = 15;
		}
		//countdown
		delayTillNextSpawn-=1;
		//Change Type
		if(spawner.getSpawnedType() != monsterType) {
			spawner.setSpawnedType(monsterType);
			spawner.update();
		}
	}
	@Override
	public void onClick(Player p){
		if(p == owner) {
			Material item = owner.getInventory().getItemInMainHand().getType();
			if(item == Material.ZOMBIE_SPAWN_EGG) {
				monsterType = EntityType.ZOMBIE;
			}
			else if(item == Material.SKELETON_SPAWN_EGG) {
				monsterType = EntityType.SKELETON;
			}
		}
	}
	public void setType(EntityType type) {
		monsterType = type;
	}
	public CreatureSpawner getSpawner(){
		return spawner;
	}
	@Override
	public void destroy() {
		super.destroy();
		for(BaseEntity zombie : zombies) {
			zombie.kill();
		}
	}
	public static boolean spaceCheck(Block loc){
		boolean check = BaseStructure.SpaceCheck(loc);
		if(check){
			if(loc.getRelative(BlockFace.UP).getType() != Material.AIR){
				check=false;
			}
		}
		return check;
	}

}
