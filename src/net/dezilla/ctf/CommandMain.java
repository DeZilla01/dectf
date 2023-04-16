package net.dezilla.ctf;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.dezilla.ctf.GameType.BaseGame;
import net.dezilla.ctf.Kits.BaseKit;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;

public class CommandMain {
	public static void aCommand(CommandSender sender, Command command, String alias, String[] args){
		Player p = (Player) sender;
		//CLASS SWITCH
		String[] kitnames = {"heavy", "archer", "medic", "soldier", "ninja", "assassin", "engineer", "chemist", "pyro", "dwarf", "elf", "mage", "fashionista", "necro", "scout", "dragger", "fisher", "jumper", "warlock", "wraith", "tester", "paladin"};
		for(String i : kitnames) {
			if(alias.equalsIgnoreCase(i)) {
				if(args.length>0) {
					if(StringUtils.isNumeric(args[0])) {
						BaseKit.kitSwitch(i, p, Integer.parseInt(args[0]));
					}
					else {
						BaseKit.kitSwitch(i, p, BaseKit.getVariation(i, args[0]));
					}
				}
				else {
					BaseKit.kitSwitch(i, p, 0);
				}
				Main.mConfig.getMatch().tpPlayerToSpawn(p);
				Main.grabKit(p).newInventory();
				p.sendMessage("Switched to "+Main.grabKit(p).getKitName());
			}
		}
		//CLASS SELECTOR
		if(alias.equalsIgnoreCase("class") || alias.equalsIgnoreCase("classes") || alias.equalsIgnoreCase("kit") || alias.equalsIgnoreCase("kits")){
			sender.sendMessage(ChatColor.DARK_AQUA+"Click on a class to switch:");
			for(String s : kitnames){
				String json = "{\"text\":\"/"+s+"\",\"color\":\"gold\",\"italic\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/"+s+"\"}}";
				PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(json));
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}
	
	// Info
	public static void cInfo(MapConfig mConfig, Player p) {
		String[] gametypes = {"Sandbox Mode", "Capture The Flag", "Zones Control", "Team Deathmatch", "cart pushing future content"};
		String gametype = gametypes[mConfig.getGameType()];
		BaseGame match = mConfig.getMatch();
		int pTeam = match.getPlayerTeam(p);
		
		p.sendMessage("----------------Map Info----------------");
		p.sendMessage("Game Type: "+gametype);
		p.sendMessage("Map Name: "+mConfig.getName());
		p.sendMessage("Creator: "+mConfig.getCreator());
		p.sendMessage("--------------Player Info---------------");
		p.sendMessage("Team: " + Integer.toString(pTeam) + " (" + mConfig.getTeamName(pTeam) + ")");
		p.sendMessage("----------------------------------------");
	}
	
	// Spawn
	public static void cSpawn(MapConfig mConfig, Player p) {
		p.sendMessage("Teleporting to spawn (maybe)");
		mConfig.getMatch().tpPlayerToSpawn(p);
	}
	
	// Switch
	public static void cSwitch(MapConfig mConfig, Player p) {
		BaseGame match = mConfig.getMatch();
		boolean switched = match.switchPlayer(p);
		if(switched) {
			match.tpPlayerToSpawn(p);
			String pTeam = mConfig.getTeamName(match.getPlayerTeam(p));
			ChatColor pCol=mConfig.getTeamCol(mConfig.M_match.getPlayerTeam(p));
			p.sendMessage("Switch to Team "+pCol+pTeam);
		}
	}
	
	public static void cForceStart(MapConfig wConfig, Player p) {
		if(wConfig.getMatch().isInPre){
			wConfig.getMatch().StartGame();
		}
		else{
			p.sendMessage("The match has already been started dumbass.");
		}
	}
	
	public static void cUpdateSb(MapConfig wConfig, Player p) {
		ScoreManager.update(wConfig);
		p.sendMessage("Scoreboard updated, idk maybe");
	}
	
	
}
