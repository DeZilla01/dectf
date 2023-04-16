package net.dezilla.ctf.Structures;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.GameType.Zones;

public class BaseStructure {
	public Player owner;
	public int team;
	public boolean active;
	public ArrayList<Material> previousStuff = new ArrayList<Material>();
	public ArrayList<Block> strucBlocks = new ArrayList<Block>();
	
	public BaseStructure(Player p, Block loc){
		this.owner = p;
		Main.structures.add(this);
		this.active=true;
		this.team = Main.mConfig.M_match.getPlayerTeam(p);
	}
	public void destroy(){
		IntStream.range(0, strucBlocks.size()).forEachOrdered(n -> {
			strucBlocks.get(n).setType(previousStuff.get(n));
		});
		Main.structures.remove(this);
		active = false;
	}
	public void onStep(Player p){
		
	}
	public void onClick(Player p){
		
	}
	public void update1tick(){
		
	}
	public void update5tick(){
		
	}
	public void update20tick(){
		
	}
	public void update5sec(){
		
	}
	
	@SuppressWarnings("deprecation")
	public static boolean SpaceCheck(Block loc){
		boolean check = true;
		//CTF flags
		if(Main.mConfig.M_match.isCTF){
			for(Location l : Main.mConfig.M_teamFlag){
				if(l.distance(loc.getLocation()) <= 10){
					check = false;
				}
			}
		}
		//Zones
		else if(Main.mConfig.M_match.isZones){
			Zones match = (Zones) Main.mConfig.M_match;
			for(ArrayList<Block> arblock : match.zoneblock){
				for(Block blok : arblock){
					if(loc.getRelative(BlockFace.DOWN).getLocation().distance(blok.getLocation()) < .5){
						check = false;
					}
				}
			}
		}
		//Spawn
		if(Main.mConfig.M_match.isZones){
			if(loc.getRelative(BlockFace.DOWN).getType() == Material.LEGACY_WOOL){
				if(loc.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.BEDROCK){
					check = false;
				}
			}
		}
		else{
			if(Main.mConfig.M_teamSblock.contains(loc.getRelative(BlockFace.DOWN).getType())){
				check = false;
			}
		}
		//block under
		if(loc.getRelative(BlockFace.DOWN).getType() == Material.AIR){
			check=false;
		}
		return check;
	}

}
