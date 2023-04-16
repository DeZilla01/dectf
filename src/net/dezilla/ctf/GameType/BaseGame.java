package net.dezilla.ctf.GameType;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.MapConfig;

public class BaseGame {
	public ArrayList<ArrayList<Player>> TeamPlayer = new ArrayList<ArrayList<Player>>();
	public ArrayList<Integer> finalpoints = new ArrayList<Integer>();
	public boolean isInProg = false;
	public boolean isInPre = true;
	public boolean isCTF = false;
	public boolean isZones = false;
	public boolean isDM = false;
	public boolean isCart = false;
	public boolean isSandbox = false;
	public boolean isKOTH = false;
	public MapConfig wConfig;
	public int winner = -1;
	
	
	public BaseGame(MapConfig conf) {
		wConfig = conf;
		IntStream.range(0, wConfig.getTeams()).forEachOrdered(n -> {TeamPlayer.add(new ArrayList<Player>());});
		if(wConfig.getGameType() == 1) {
			isCTF = true;
		}
		else if(wConfig.getGameType() == 2) {
			isZones = true;
		}
		else if(wConfig.getGameType() == 3) {
			isDM = true;
		}
		else if(wConfig.getGameType() == 4) {
			isCart = true;
		}
		else {
			isSandbox = true;
		}
		
	}
	public void endGame(){
		isInProg = false;
		if(isCTF){
			CTF match = (CTF) this;
			finalpoints = match.captures;
		}
		else if(isZones){
			Zones match = (Zones) this;
			IntStream.range(0, wConfig.getTeams()).forEachOrdered(n -> {finalpoints.add(0);});
			int count = 0;for(int n : match.capProgress){
				if(match.isBeingCapped.get(count)){
					int team = match.whodominate.get(count);
					int points = finalpoints.get(team)+n;
					finalpoints.remove(team);
					finalpoints.add(team, points);
				}
				count+=1;
			}
		}
		int count = 0;boolean equal = true; int highest = 0;for(int n : finalpoints){
			if(n>highest){
				winner = count;
				equal=false;
				highest=n;
			}
			else if(n==highest){
				equal=true;
			}
			count+=1;
		}
		if(equal){
			winner=-1;
		}
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			tpPlayerToSpawn(p);
			Main.timeleft = 30;
			p.getInventory().clear();
		}
		
	}
	
	public void addPlayer(Player p) {
		//add the player to a team
		verifOnline();
		int[] amountPlayer = new int[TeamPlayer.size()];
		int count = 0;for(ArrayList<Player> i : TeamPlayer) {
			amountPlayer[count] = i.size();
			count += 1;
		}
		int select = 0;count = 0; int lowest = 999;for(int i : amountPlayer) {
			if(i<=lowest){
				select = count;
				lowest = i;
			}
			count += 1;
		}
		TeamPlayer.get(select).add(p);
		net.dezilla.ctf.ScoreManager.addPlayerToTeam(p);
	}
	
	public int getPlayerTeam(Player p) {
		int count = 0;for(ArrayList<Player> i : TeamPlayer) {
			if(i.contains(p)){
				return count;
			}
			count+=1;
		}
		count = 0;for(ArrayList<Player> i : TeamPlayer) {
			for(Player pla : i){
				if(pla.getName().equalsIgnoreCase(p.getName())){
					return count;
				}
				count+=1;
			}
		}
		System.out.println("[DeCTF] Error, could not find team for "+p.getName());
		return -1;
	}
	
	public void StartGame() {
		isInProg = true;
		isInPre = false;
		Main.timeleft = wConfig.M_timelimit;
		for(Player p : Bukkit.getOnlinePlayers()) {
			tpPlayerToSpawn(p);
		}
		
	}
	
	private void verifOnline() {
		ArrayList<Player> notOnline;
		for(ArrayList<Player> l : TeamPlayer) {
			notOnline = new ArrayList<Player>();
			for(Player p : l) {
				if(! p.isOnline()){
					notOnline.add(p);
				}
			}
			for(Player p : notOnline) {
				l.remove(p);
				System.out.println("[Debug] Player not online: "+p.getName());
			}
		}
	}
	
	public boolean switchPlayer(Player p) {
		//switch the player's team
		verifOnline();
		int[] amountPlayer = new int[TeamPlayer.size()];
		int count = 0;for(ArrayList<Player> i : TeamPlayer) {
			amountPlayer[count] = i.size();
			count += 1;
		}
		int select = 0;count = 0; int lowest = 9999;for(int i : amountPlayer) {
			if(i<=lowest){
				select = count;
				lowest = i;
			}
			count += 1;
		}
		if(select!=getPlayerTeam(p)){
			int oldTeam = getPlayerTeam(p);
			net.dezilla.ctf.ScoreManager.remPlayerToTeam(p);
			TeamPlayer.get(oldTeam).remove(TeamPlayer.get(oldTeam).indexOf(p));
			int newTeam;
			if(oldTeam+1==wConfig.getTeams()) {
				newTeam = 0;
			}
			else {
				newTeam = oldTeam+1;
			}
			TeamPlayer.get(newTeam).add(p);
			net.dezilla.ctf.ScoreManager.addPlayerToTeam(p);
			return true;
			
		}
		else {
			p.sendMessage("Cannot change team due to balance.");
			return false;
		}
	}
	
	public void tpPlayerToSpawn(Player p) {
		if(isInProg) {
			Location loc = wConfig.getTeamLoc(getPlayerTeam(p));
			p.teleport(loc);
			Main.killStruc(p);
		}
		else {
			p.teleport(wConfig.getSpawn());
		}
		p.setHealth(20.0);
		//p.setSaturation((float) 9.0);
		p.setExhaustion(1);
		if(isInProg){
			Main.Kkits.get(Main.Kplayers.indexOf(p)).newInventory();
		}
		
	}
	public void resPlayerToSpawn(PlayerRespawnEvent e, Player p) {
		if(isInProg) {
			Location loc = wConfig.getTeamLoc(getPlayerTeam(p));
			e.setRespawnLocation(loc);
		}
		else {
			e.setRespawnLocation(wConfig.getSpawn());
		}
		p.setHealth(20.0);
		//p.setSaturation((float) 9.0);
		//p.setExhaustion(1);
		p.setFoodLevel(1);
		if(isInProg){
			Main.Kkits.get(Main.Kplayers.indexOf(p)).newInventory();
		}
	}
	
	public boolean isInProgress() {
		return isInProg;
	}
	public ArrayList<String> getDisplay(Player p){
		return null;
	}
}
