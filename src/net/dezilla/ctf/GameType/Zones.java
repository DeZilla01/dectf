package net.dezilla.ctf.GameType;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import net.dezilla.ctf.IDTools;
import net.dezilla.ctf.Main;
import net.dezilla.ctf.MapConfig;
import net.dezilla.ctf.Kits.Ninja;

public class Zones extends BaseGame{
	public ArrayList<ArrayList<Block>> zoneblock = new ArrayList<ArrayList<Block>>();
	public ArrayList<Integer> whodominate = new ArrayList<Integer>();
	public ArrayList<Boolean> isDominated = new ArrayList<Boolean>();
	public ArrayList<Boolean> isBeingCapped = new ArrayList<Boolean>();
	public ArrayList<Integer> capProgress = new ArrayList<Integer>();
	byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};

	public Zones(MapConfig conf) {
		super(conf);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void StartGame(){
		isInProg = true;
		isInPre = false;
		Main.timeleft = wConfig.M_timelimit;
		for(Location l : wConfig.M_zoneloc){
			if(l.getBlock().getType() != Material.LEGACY_WOOL){
				Bukkit.getServer().broadcastMessage("Zone not properly configured, please check map config");
				zoneblock.add(new ArrayList<Block>());
			}
			else{
				ArrayList<Block> tmpzone = new ArrayList<Block>();
				ArrayList<Block> tocheck = new ArrayList<Block>();
				tmpzone.add(l.getBlock());
				tocheck.add(l.getBlock());
				boolean done = false;
				while(!done){
					ArrayList<Block> tmpblocklist = new ArrayList<Block>();
					for(Block b : tocheck){
						tmpblocklist.add(b.getRelative(BlockFace.EAST));
						tmpblocklist.add(b.getRelative(BlockFace.NORTH));
						tmpblocklist.add(b.getRelative(BlockFace.WEST));
						tmpblocklist.add(b.getRelative(BlockFace.SOUTH));
					}
					while(!tocheck.isEmpty()){
						tocheck.remove(0);
					}
					for(Block bt : tmpblocklist){
						if(bt.getType() == Material.LEGACY_WOOL && ! tmpzone.contains(bt)){
							tmpzone.add(bt);
							tocheck.add(bt);
						}
					}
					if(tocheck.isEmpty()){
						done=true;
					}
				}//while
				zoneblock.add(tmpzone);
			}//else
			whodominate.add(-1);
			isDominated.add(false);
			isBeingCapped.add(false);
			capProgress.add(0);
		}//for
		for(Player p : Bukkit.getOnlinePlayers()) {
			tpPlayerToSpawn(p);
		}
	}
	@SuppressWarnings("deprecation")
	public void update(){
		IntStream.range(0, wConfig.M_amountzones).forEachOrdered(n -> {
			int ratio = wConfig.M_zoneratio.get(n);
			// take over dominated zone
			if(isDominated.get(n)){
				boolean teamOn = false;
				int amountCap = 0;
				for(Player p : Bukkit.getServer().getOnlinePlayers()){
					boolean ninjacheck = true;
					if(Main.grabKit(p) instanceof Ninja){
						Ninja kit = (Ninja) Main.grabKit(p);
						if(kit.isHidden){
							ninjacheck = false;
						}
					}
					if(zoneblock.get(n).contains(p.getLocation().getBlock().getRelative(BlockFace.DOWN)) && ninjacheck && p.getGameMode() == GameMode.SURVIVAL){
						if(getPlayerTeam(p) == whodominate.get(n)){
							amountCap += ratio;
						}
						else{
							amountCap -=ratio;
							teamOn = true;
						}
					}
				}
				if(!teamOn){
					amountCap+=ratio;
				}
				int currentProg = capProgress.get(n)+amountCap;
				if(currentProg<=0){
					isDominated.remove(n);
					isDominated.add(n, false);
					whodominate.remove(n);
					whodominate.add(n, -1);
					isBeingCapped.remove(n);
					isBeingCapped.add(n, false);
					wConfig.M_world.strikeLightningEffect(wConfig.M_zoneloc.get(n));
					capProgress.remove(n);
					capProgress.add(n, 0);
				}
				else if(currentProg>=100){
					capProgress.remove(n);
					capProgress.add(n, 100);
				}
				else{
					capProgress.remove(n);
					capProgress.add(n, currentProg);
				}
			}
			// take over non-dominated zone
			else if(isBeingCapped.get(n)){
				boolean teamOn = false;
				int amountCap = 0;
				for(Player p : Bukkit.getServer().getOnlinePlayers()){
					boolean ninjacheck = true;
					if(Main.grabKit(p) instanceof Ninja){
						Ninja kit = (Ninja) Main.grabKit(p);
						if(kit.isHidden){
							ninjacheck = false;
						}
					}
					if(zoneblock.get(n).contains(p.getLocation().getBlock().getRelative(BlockFace.DOWN)) && ninjacheck && p.getGameMode() == GameMode.SURVIVAL){
						if(getPlayerTeam(p) == whodominate.get(n)){
							teamOn = true;
							amountCap += ratio;
						}
						else{
							amountCap -=ratio;
						}
					}
				}
				if(!teamOn){
					amountCap-=ratio;
				}
				int currentProg = capProgress.get(n)+amountCap;
				if(currentProg<=0){
					whodominate.remove(n);
					whodominate.add(n, -1);
					isBeingCapped.remove(n);
					isBeingCapped.add(n, false);
					capProgress.remove(n);
					capProgress.add(n, 0);
				}
				if(currentProg>=100){
					isDominated.remove(n);
					isDominated.add(n, true);
					wConfig.M_world.strikeLightningEffect(wConfig.M_zoneloc.get(n));
					capProgress.remove(n);
					capProgress.add(n, 100);
					checkEnd();
				}
				else{
					capProgress.remove(n);
					capProgress.add(n, currentProg);
				}
			}
			// take over 0% zone
			else{
				ArrayList<Integer> amountPlayers = new ArrayList<Integer>();
				IntStream.range(0, wConfig.M_teams).forEachOrdered(m -> {
					amountPlayers.add(0);
				});
				for(Player p : Bukkit.getServer().getOnlinePlayers()){
					boolean ninjacheck = true;
					if(Main.grabKit(p) instanceof Ninja){
						Ninja kit = (Ninja) Main.grabKit(p);
						if(kit.isHidden){
							ninjacheck = false;
						}
					}
					if(zoneblock.get(n).contains(p.getLocation().getBlock().getRelative(BlockFace.DOWN)) && ninjacheck && p.getGameMode() == GameMode.SURVIVAL){
						int pTeam = getPlayerTeam(p);
						int amount = amountPlayers.get(pTeam);
						amountPlayers.remove(pTeam);
						amountPlayers.add(pTeam, amount+ratio);
					}
				}
				int biggest = 0;
				int whoisbig = -1;
				boolean equal = true;
				int count = 0;
				for(int num : amountPlayers){
					if(num >= biggest){
						if(num == biggest){
							equal = true;
						}
						else{
							equal = false;
							whoisbig = count;
							biggest = num;
						}
					}
					count += 1;
				}
				if(!equal){
					whodominate.remove(n);
					whodominate.add(n, whoisbig);
					isBeingCapped.remove(n);
					isBeingCapped.add(n, true);
					capProgress.remove(n);
					capProgress.add(n, biggest);
				}
			}
			//update wool color
			double amounttocolor = (double) capProgress.get(n)/100*zoneblock.get(n).size();
			byte color = (byte) 0;
			if(isBeingCapped.get(n)){
				color = woolColorList[wConfig.M_teamColInt.get(whodominate.get(n))];
			}
			int count = 0;for(Block blok : zoneblock.get(n)){
				IDTools idt = new IDTools();
				if(count<=amounttocolor && amounttocolor != 0){
					if(blok.getData() != color){
						blok.setType(idt.getMaterial(Double.parseDouble("35."+Byte.toString(color))));
					}
				}
				else{
					if(blok.getData() != 0){
						blok.setType(Material.WHITE_WOOL);
					}
				}
				count+=1;
			}
		});
	}
	public void checkEnd(){
		boolean finish = true;
		int team = whodominate.get(0);int count = 0;for(int n : whodominate){
			if(n!=team||!isDominated.get(count)){
				finish = false;
			}
			count+=1;
		}
		if(finish){
			endGame();
		}
	}

}
