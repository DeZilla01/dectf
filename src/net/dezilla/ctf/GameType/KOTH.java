package net.dezilla.ctf.GameType;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.MapConfig;

public class KOTH extends BaseGame{
	ArrayList<Block> hill = new ArrayList<Block>();
	ArrayList<Integer> points = new ArrayList<Integer>();

	@SuppressWarnings("deprecation")
	public KOTH(MapConfig conf) {
		super(conf);
		isKOTH = true;
		//grabing the blocks
		ArrayList<Block> toCheck = new ArrayList<Block>();
		toCheck.add(conf.M_hill.getBlock());
		while(!toCheck.isEmpty()){
			BlockFace[] bf = {BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH};
			for(int n = 0; n < 4; n++ ){
				if(toCheck.get(0).getRelative(bf[n]).getType() == Material.LEGACY_WOOL && !hill.contains(toCheck.get(0).getRelative(bf[n]))){
					toCheck.add(toCheck.get(0).getRelative(bf[n]));
				}
			}
			hill.add(toCheck.get(0));
			toCheck.remove(0);
		}
		for(int i = 0; i<conf.M_teams; i++){
			points.add(0);
		}
	}
	
	public void update(){
		for(Player i : Bukkit.getServer().getOnlinePlayers()){
			Block block = i.getLocation().getBlock().getRelative(BlockFace.DOWN);
			if(hill.contains(block)){
				int point = points.get(Main.getTeamId(i))+1;
				points.remove(Main.getTeamId(i));
				points.add(Main.getTeamId(i), point);
			}
		}
		for(int i = 0; i<points.size(); i++){
			if(points.get(i)>=wConfig.M_pointlimit){
				endGame();
			}
		}
	}
	@Override
	public ArrayList<String> getDisplay(Player p){
		ArrayList<String> score = new ArrayList<String>();
		score.add(ChatColor.BOLD+"KOTH"+ChatColor.RESET+" - to "+Integer.toString(wConfig.M_pointlimit));
		for(int i = 0; i<Main.mConfig.M_teams; i++){
			score.add(Main.mConfig.M_teamCol.get(i)+Main.mConfig.M_teamStr.get(i)+ChatColor.RESET+" : "+ChatColor.GRAY+Integer.toString(points.get(i)));
		}
		return score;
	}

}
