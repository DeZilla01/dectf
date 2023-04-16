package net.dezilla.ctf;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import net.dezilla.ctf.GameType.BaseGame;
import net.dezilla.ctf.GameType.CTF;
import net.dezilla.ctf.GameType.KOTH;
import net.dezilla.ctf.GameType.Zones;

public class MapLoader {
	
	private static ArrayList<String> getWorldList(File curDir) {
		File[] filesList = curDir.listFiles();
		ArrayList<String> WorldList = new ArrayList<String>();
		for(File f : filesList){
			if(f.isDirectory()){
				WorldList.add(f.getName());
			}
		}
		return WorldList;
	}
	
	private static String grabMapName() {
		// Select Game map randomly
		Random rand = new Random();
		File curDir = new File("GAMEMAP"+Main.fileSlash);
		ArrayList<String> Wlist = getWorldList(curDir);
		int n = rand.nextInt(Wlist.size()) + 0;
		String SelMap = Wlist.get(n);
		return SelMap;
	}
	
	@SuppressWarnings("deprecation")
	public static MapConfig createRandomGame() {
		String wPath = "GAMEMAP"+Main.fileSlash+grabMapName();
		WorldCreator GameMap = new WorldCreator(wPath);
		World w = Bukkit.getServer().createWorld(GameMap);
		MapConfig wConfig = new MapConfig(wPath+Main.fileSlash+"config.txt", wPath);
		//Bukkit.getServer().unloadWorld(w, false);
		w.setAutoSave(false);
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("doMobSpawning", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("doDaylightCycle", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("doWeatherCycle", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("keepInventory", "true");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("doEntityDrops", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("doFireTick", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("mobGriefing", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("naturalRegeneration", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setGameRuleValue("doFireTick", "false");
		Bukkit.getServer().getWorld(wConfig.getWorldPath()).setTime(wConfig.M_time);
		if(wConfig.getGameType() == 1) {
			wConfig.setMatch(new CTF(wConfig));
		}
		else if(wConfig.getGameType() == 2) {
			wConfig.setMatch(new Zones(wConfig));
		}
		//else if(wConfig.getGameType() == 3) {
			//DeathMatch match = new DeathMatch(wConfig);
		//}
		else if (wConfig.getGameType() == 5){
			wConfig.setMatch(new KOTH(wConfig));
		}
		else {
			wConfig.setMatch(new BaseGame(wConfig));
		}
		return wConfig;
	}
	
	
}
