package net.dezilla.ctf;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.IntStream;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import net.dezilla.ctf.GameType.BaseGame;


public class MapConfig {
	public String M_name;
	public String M_creator;
	public String M_worldPath;
	public String M_confPath;
	public long M_time;
	public int M_gametype;
	public int M_teams;
	public Location M_spawn;
	public double[] M_d_spawn;
	public BaseGame M_match;
	public World M_world;
	public ArrayList<String> M_teamStr = new ArrayList<String>();
	public ArrayList<Location> M_teamLoc = new ArrayList<Location>();
	public ArrayList<ChatColor> M_teamCol = new ArrayList<ChatColor>();
	public ArrayList<Integer> M_teamColInt = new ArrayList<Integer>();
	public static DyeColor[] flacCol = {DyeColor.BLACK, DyeColor.BLUE, DyeColor.GREEN, DyeColor.CYAN, DyeColor.RED, DyeColor.PURPLE, DyeColor.ORANGE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLUE, DyeColor.LIME, DyeColor.LIGHT_BLUE, DyeColor.RED, DyeColor.MAGENTA, DyeColor.YELLOW, DyeColor.WHITE};
	public ArrayList<Material> M_teamSblock = new ArrayList<Material>();
	public int M_timelimit;
	//CTF
	public ArrayList<Location> M_teamFlag = new ArrayList<Location>();
	public int M_capLimit;
	//Zones
	public int M_amountzones;
	public ArrayList<Location> M_zoneloc = new ArrayList<Location>();
	public ArrayList<String> M_zonename = new ArrayList<String>();
	public ArrayList<Integer> M_zoneratio = new ArrayList<Integer>();
	public boolean M_oldspawn = false;
	//KOTH
	public Location M_hill = null;
	public int M_pointlimit = 0;
	
	public MapConfig(String confPath, String worldPath) {
		// confPath = GAMEMAP\world\config.txt
		// worldPath = GAMEMAP\world
		M_name = grabStr(confPath, "name");
		M_creator = grabStr(confPath, "creator");
		M_worldPath = worldPath;
		M_confPath = confPath;
		M_time = (long) grabInt(confPath, "time");
		M_gametype = grabInt(confPath, "gametype");
		M_timelimit = grabInt(confPath, "timelimit");
		M_teams = grabInt(confPath, "teams");
		double[] dub = grabCoord(confPath, "spawn");
		M_d_spawn = dub;
		M_world = org.bukkit.Bukkit.getWorld(worldPath);
		M_spawn = new Location(M_world, dub[0], dub[1], dub[2]);
		M_spawn.setYaw((float) dub[3]);
		IntStream.range(0, M_teams).forEachOrdered(n -> {M_teamStr.add(grabStr(confPath, "team"+Integer.toString(n)+"str"));});
		IntStream.range(0, M_teams).forEachOrdered(n -> {
			double[] dubt = grabCoord(confPath, "team"+Integer.toString(n)+"spawn");
			Location temp_loc = new Location(M_world, dubt[0], dubt[1], dubt[2]);
			temp_loc.setYaw((float) dubt[3]);
			M_teamLoc.add(temp_loc);
		});
		
		IntStream.range(0, M_teams).forEachOrdered(n -> {
			Material m = Material.matchMaterial(grabStr(confPath, "team"+Integer.toString(n)+"sblock"));
			M_teamSblock.add(m);
		});
		
		//color
		IntStream.range(0, M_teams).forEachOrdered(n -> {
			int col = grabInt(confPath, "team"+Integer.toString(n)+"color");
			ChatColor tCol = ChatColor.WHITE;
			if(col==0){
				tCol = ChatColor.BLACK;
			}
			else if(col==1){
				tCol = ChatColor.DARK_BLUE;
			}
			else if(col==2){
				tCol = ChatColor.DARK_GREEN;
			}
			else if(col==3){
				tCol = ChatColor.DARK_AQUA;
			}
			else if(col==4){
				tCol = ChatColor.DARK_RED;
			}
			else if(col==5){
				tCol = ChatColor.DARK_PURPLE;
			}
			else if(col==6){
				tCol = ChatColor.GOLD;
			}
			else if(col==7){
				tCol = ChatColor.GRAY;
			}
			else if(col==8){
				tCol = ChatColor.DARK_GRAY;
			}
			else if(col==9){
				tCol = ChatColor.BLUE;
			}
			else if(col==10){
				tCol = ChatColor.GREEN;
			}
			else if(col==11){
				tCol = ChatColor.AQUA;
			}
			else if(col==12){
				tCol = ChatColor.RED;
			}
			else if(col==13){
				tCol = ChatColor.LIGHT_PURPLE;
			}
			else if(col==14){
				tCol = ChatColor.YELLOW;
			}
			else if(col==15){
				tCol = ChatColor.WHITE;
			}
			M_teamColInt.add(col);
			M_teamCol.add(tCol);
		});
		//color end
		
		//CTF
		if(M_gametype == 1){
			IntStream.range(0, M_teams).forEachOrdered(n -> {
				double[] dubt = grabCoord(confPath, "team"+Integer.toString(n)+"flag");
				Location temp_loc = new Location(M_world, dubt[0], dubt[1], dubt[2]);
				temp_loc.setYaw((float) dubt[3]);
				temp_loc.add((double) 0, (double) 0, (double) 0);
				if(dubt[3]==0.0){
					temp_loc.add((double) 0, (double) 0, (double) .2);
				}
				else if(dubt[3]==180.0 || dubt[3]==-180.0){
					temp_loc.add((double) 0, (double) 0, (double) -.2);
				}
				else if(dubt[3]==-90.0){
					temp_loc.add((double) .2, (double) 0, (double) 0);
				}
				else if(dubt[3]==90.0){
					temp_loc.add((double) -.2, (double) 0, (double) 0);
				}
				M_teamFlag.add(temp_loc);
			});
			M_capLimit = grabInt(confPath, "captures");
		}
		//Zones
		if(M_gametype == 2){
			M_amountzones = grabInt(confPath, "amountzones");
			if(grabInt(confPath, "zoneoldspawn") == 1){
				M_oldspawn = true;
			}
			IntStream.range(0, M_amountzones).forEachOrdered(n -> {
				double[] dubt = grabCoord(confPath, "zone"+Integer.toString(n)+"loc");
				M_zoneloc.add(new Location(M_world, dubt[0], dubt[1], dubt[2]));
				M_zonename.add(grabStr(confPath, "zone"+Integer.toString(n)+"name"));
				M_zoneratio.add(grabInt(confPath, "zone"+Integer.toString(n)+"ratio"));
			});
		}
		//KOTH
		if(M_gametype == 5){
			double[] dubt = grabCoord(confPath, "hill");
			M_hill = new Location(M_world, dubt[0], dubt[1], dubt[2]);
			M_pointlimit = grabInt(confPath, "pointlimit");
		}
		
		
	}
	
	public String getName() {
		return M_name;
	}
	
	
	public String getCreator() {
		return M_creator;
	}
	
	public String getWorldPath() {
		return M_worldPath;
	}
	
	public int getGameType() {
		return M_gametype;
	}
	
	public String getGameTypeName() {
		String[] gametypes = {"Sandbox Mode", "Capture The Flag", "Zone Control", "Team Deathmatch", "Payload", "King of the Hill"};
		String gametype = gametypes[M_gametype];
		return gametype;
	}
	
	public int getTeams() {
		return M_teams;
	}
	
	public Location getSpawn() {
		return M_spawn;
	}
	
	public double[] getdSpawn() {
		return M_d_spawn;
	}
	
	public void setMatch(BaseGame g) {
		M_match = g;
	}
	public BaseGame getMatch() {
		return M_match;
	}
	public String getTeamName(int n) {
		return M_teamStr.get(n);
	}
	public Location getTeamLoc(int n) {
		return M_teamLoc.get(n);
	}
	public ChatColor getTeamCol(int n) {
		return M_teamCol.get(n);
	}
	public Material getTeamSblock(int n){
		return M_teamSblock.get(n);
	}
	
	private static int grabInt(String confFile, String confName) {
		try (FileReader reader = new FileReader(confFile)) {
			Properties config = new Properties();
			config.load(reader);
			
			String conf = config.getProperty(confName);
			return Integer.parseInt(conf);
		} catch (Exception e) {
			;
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String grabStr(String confFile, String confName) {
		try (FileReader reader = new FileReader(confFile)) {
			Properties config = new Properties();
			config.load(reader);
			
			String conf = config.getProperty(confName);
			return conf;
		} catch (Exception e) {
			;
			e.printStackTrace();
		}
		return "";
	}
	
	private static double[] grabCoord(String confFile, String confName) {
		double[] coord = new double[4];
		try (FileReader reader = new FileReader(confFile)) {
			Properties config = new Properties();
			config.load(reader);
			String conf = config.getProperty(confName);
			String[] parts = conf.split(",");
			coord[0] = Double.parseDouble(parts[0]);
			coord[1] = Double.parseDouble(parts[1]);
			coord[2] = Double.parseDouble(parts[2]);
			coord[3] = Double.parseDouble(parts[3]);
			return coord;
		} catch (Exception e) {
			;
			e.printStackTrace();
		}
		return coord;
	}
	
	
}
