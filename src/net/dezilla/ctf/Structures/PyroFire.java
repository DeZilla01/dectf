package net.dezilla.ctf.Structures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.Kits.Pyro;

public class PyroFire extends BaseStructure{
	int timeLeft = 100;

	public PyroFire(Player p, Block loc) {
		super(p, loc);
		//Main Fire
		previousStuff.add(Material.AIR);
		strucBlocks.add(loc);
		loc.setType(Material.FIRE);
		Pyro kit = (Pyro) Main.grabKit(p);
		if(kit.charged) {
			BlockFace[] faces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH_WEST, BlockFace.NORTH_EAST, BlockFace.SOUTH_WEST, BlockFace.SOUTH_EAST};
			for (BlockFace i : faces){
				if(loc.getRelative(i).getType() == Material.AIR) {
					previousStuff.add(Material.AIR);
					strucBlocks.add(loc.getRelative(i));
					loc.getRelative(i).setType(Material.FIRE);
				}
			}
		}
		
	}
	
	@Override
	public void update1tick(){
		if(timeLeft<=0) {
			destroy();
		}
		timeLeft-=1;
	}

}
