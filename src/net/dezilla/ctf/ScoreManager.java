package net.dezilla.ctf;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.dezilla.ctf.GameType.CTF;
import net.dezilla.ctf.GameType.Zones;

public class ScoreManager{
	public static ScoreboardManager scoreMan = Bukkit.getScoreboardManager();
	//list for players
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Scoreboard> scores = new ArrayList<Scoreboard>();
	//stats
	public static ArrayList<Integer> kills = new ArrayList<Integer>();
	public static ArrayList<Integer> deaths = new ArrayList<Integer>();
	public static ArrayList<Integer> streaks = new ArrayList<Integer>();
	public static ArrayList<Integer> recoveries = new ArrayList<Integer>();
	public static ArrayList<Integer> captures = new ArrayList<Integer>();
	
	//list for teams
	public static Scoreboard teams;
	public static ArrayList<ArrayList<Team>> Ateams= new ArrayList<ArrayList<Team>>();
	//other stuff
	
	static int count = 0;
	static int teamCount = 0;
	static int waitanimation = 0;
	static String waitplayers;
	static String waitstatus;
	static String winner;
	
	@SuppressWarnings("deprecation")
	public static void addPlayer(Player p) {
		boolean newp = true;
		for(Player pla : players){
			if(pla.getName().equalsIgnoreCase(p.getName())){
				newp=false;
				int temp_index = players.indexOf(pla);
				players.remove(temp_index);
				players.add(temp_index, p);
				p.setScoreboard(scores.get(temp_index));
				break;
			}
		}
		if(newp){
			Scoreboard score = scoreMan.getNewScoreboard();
			Objective objec = score.registerNewObjective("DeCTF-loading", "dummy");
			objec.setDisplaySlot(DisplaySlot.SIDEBAR);
			objec.getScore("init").setScore(0);
			p.setScoreboard(score);
			int kill = 0;
			int death = 0;
			int streak = 0;
			int recovery = 0;
			int capture = 0;
			
			players.add(p);
			scores.add(score);
			kills.add(kill);
			deaths.add(death);
			streaks.add(streak);
			recoveries.add(recovery);
			captures.add(capture);
			
			ArrayList<Team> at = new ArrayList<Team>();
			IntStream.range(0, Main.mConfig.M_teams).forEachOrdered(n -> {
				Team t = score.registerNewTeam(Integer.toString(n));
				t.setPrefix(Main.mConfig.getTeamCol(n)+"");
				//t.setAllowFriendlyFire(false);
				t.setDisplayName(Main.mConfig.getTeamName(n));
				ArrayList<Player> tp = Main.mConfig.M_match.TeamPlayer.get(n);
				for(Player pt : tp){
					t.addPlayer(pt);
				}
				at.add(t);
			Ateams.add(at);
			});
		}
	}
	@SuppressWarnings("deprecation")
	public static void addPlayerToTeam(Player p) {
		int pTeam = Main.mConfig.M_match.getPlayerTeam(p);
		for(ArrayList<Team> at : Ateams){
			at.get(pTeam).addPlayer(p);;
		}
	}
	@SuppressWarnings("deprecation")
	public static void remPlayerToTeam(Player p) {
		int pTeam = Main.mConfig.M_match.getPlayerTeam(p);
		for(ArrayList<Team> at : Ateams){
			at.get(pTeam).removePlayer(p);;
		}
	}
	public static void addKill(Player p){
		int index = players.indexOf(p);
		int amountKills = kills.get(index);
		kills.remove(index);
		kills.add(index, amountKills+1);
	}
	public static void addDeath(Player p){
		int index = players.indexOf(p);
		int amountDeaths = deaths.get(index);
		deaths.remove(index);
		deaths.add(index, amountDeaths+1);
	}
	public static void remDeath(Player p){
		int index = players.indexOf(p);
		int amountDeaths = deaths.get(index);
		deaths.remove(index);
		deaths.add(index, amountDeaths-1);
	}
	public static void addStreak(Player p){
		int index = players.indexOf(p);
		int amountStreaks = streaks.get(index);
		streaks.remove(index);
		streaks.add(index, amountStreaks+1);
	}
	public static void resStreak(Player p){
		int index = players.indexOf(p);
		streaks.remove(index);
		streaks.add(index, 0);
	}
	public static void addRecovery(Player p){
		int index = players.indexOf(p);
		int amountRecovery = recoveries.get(index);
		recoveries.remove(index);
		recoveries.add(index, amountRecovery+1);
	}
	public static void addCapture(Player p){
		int index = players.indexOf(p);
		int amountCapture = captures.get(index);
		captures.remove(index);
		captures.add(index, amountCapture+1);
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public static void update(MapConfig mConfig) {
		if(mConfig.M_match.isInProg) {
			//
			// CTF
			//
			if(mConfig.M_match.isCTF) {
				CTF match = (CTF) mConfig.M_match;
				//
				// 2 Teams
				//
				if(mConfig.M_teams<3){
					ArrayList<String> Captures = new ArrayList<String>();
					ArrayList<String> Flags = new ArrayList<String>();
					ArrayList<String> TakenBy = new ArrayList<String>();
					IntStream.range(0, mConfig.M_teams).forEachOrdered(n -> {
						String cap = mConfig.getTeamCol(n)+" Captures "+ChatColor.WHITE+Integer.toString(match.captures.get(n))+"/"+Integer.toString(mConfig.M_capLimit);
						Captures.add(cap);
						String fla;
						String tby;
						if(match.isHome.get(n)){
							fla = mConfig.getTeamCol(n)+" Flag "+ChatColor.GREEN+"Home";
							tby = mConfig.getTeamCol(n)+" ";
						}
						else {
							if(match.isDroped.get(n)){
								fla = mConfig.getTeamCol(n)+" Flag "+ChatColor.YELLOW+"Droped";
								tby = ChatColor.WHITE+" Resets in "+Integer.toString(match.flagResets.get(n))+"s";
								//Location loc = match.dropedFlags.get(n).getLocation();
								//tby = ChatColor.WHITE+" x:"+Integer.toString(loc.getBlockX())+" y:"+Integer.toString(loc.getBlockY())+" z:"+Integer.toString(loc.getBlockX());
							}
							else {
								fla = mConfig.getTeamCol(n)+" Flag "+ChatColor.DARK_RED+"Taken";
								Player pla = Bukkit.getServer().getPlayer(match.capturedBy.get(n));
								tby = ChatColor.WHITE+" Held by "+mConfig.getTeamCol(match.getPlayerTeam(pla))+match.capturedBy.get(n);
							}
						}
						Flags.add(fla);
						TakenBy.add(tby);
					});
					String endsin = Main.secToStr(Main.timeleft);
					IntStream.range(0, players.size()).forEachOrdered(n -> {
						if(players.get(n).isOnline()){
							count = 0;
							teamCount = 0;
							Scoreboard sb = scores.get(n);
							Objective oldobj = sb.getObjective(DisplaySlot.SIDEBAR);
							oldobj.unregister();
							Objective objec = sb.registerNewObjective("init", "dummy");
							
							objec.setDisplayName("Ends in "+endsin);
							int pTeam = mConfig.M_match.getPlayerTeam(players.get(n));
							boolean firstturn=true;while(teamCount != mConfig.M_teams) {
								if(firstturn) {
									objec.getScore(ChatColor.BOLD + mConfig.getTeamName(pTeam) + ChatColor.RESET+" - Your Team").setScore(count);count-=1;
									objec.getScore(Captures.get(pTeam)).setScore(count);count-=1;
									objec.getScore(Flags.get(pTeam)).setScore(count);count-=1;
									objec.getScore(" "+TakenBy.get(pTeam)).setScore(count);count-=1;
									firstturn=false;
								}
								else if(teamCount == pTeam){
									teamCount+=1;
								}
								else {
									objec.getScore(ChatColor.BOLD + mConfig.getTeamName(teamCount)).setScore(count);count-=1;
									objec.getScore(Captures.get(teamCount)).setScore(count);count-=1;
									objec.getScore(Flags.get(teamCount)).setScore(count);count-=1;
									objec.getScore(" "+TakenBy.get(teamCount)).setScore(count);count-=1;
									teamCount+=1;
								}
							}
							objec.getScore(ChatColor.BOLD+"Your Stats").setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Kills "+ChatColor.WHITE+Integer.toString(kills.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Deaths "+ChatColor.WHITE+Integer.toString(deaths.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Streak "+ChatColor.WHITE+Integer.toString(streaks.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Recoveries "+ChatColor.WHITE+Integer.toString(recoveries.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Captures "+ChatColor.WHITE+Integer.toString(captures.get(n))).setScore(count);count-=1;
							
							objec.setDisplaySlot(DisplaySlot.SIDEBAR);
						}
					});
				}
				//
				// 4 Teams
				//
				else if(mConfig.M_teams>=4 && mConfig.M_teams<8){
					ArrayList<String> Stats = new ArrayList<String>();
					IntStream.range(0, mConfig.M_teams).forEachOrdered(n -> {
						String stat = mConfig.getTeamCol(n)+" Cap "+ChatColor.WHITE+Integer.toString(match.captures.get(n))+"/"+Integer.toString(mConfig.M_capLimit);
						if(match.isHome.get(n)){
							stat += mConfig.getTeamCol(n)+" Flag "+ChatColor.GREEN+"Home";
						}
						else {
							if(match.isDroped.get(n)){
								stat += mConfig.getTeamCol(n)+" Flag "+ChatColor.YELLOW+"Droped";
							}
							else {
								stat += mConfig.getTeamCol(n)+" Flag ";
								Player pla = Bukkit.getServer().getPlayer(match.capturedBy.get(n));
								stat += mConfig.getTeamCol(match.getPlayerTeam(pla))+match.capturedBy.get(n);
							}
						}
						Stats.add(stat);
					});
					String endsin = Main.secToStr(Main.timeleft);
					IntStream.range(0, players.size()).forEachOrdered(n -> {
						if(players.get(n).isOnline()){
							count = 0;
							teamCount = 0;
							Scoreboard sb = scores.get(n);
							Objective oldobj = sb.getObjective(DisplaySlot.SIDEBAR);
							oldobj.unregister();
							Objective objec = sb.registerNewObjective("init", "dummy");
							
							objec.setDisplayName("Ends in "+endsin);
							int pTeam = mConfig.M_match.getPlayerTeam(players.get(n));
							boolean firstturn=true;while(teamCount != mConfig.M_teams) {
								if(firstturn) {
									objec.getScore(ChatColor.BOLD + mConfig.getTeamName(pTeam) + ChatColor.RESET+" - Your Team").setScore(count);count-=1;
									objec.getScore(Stats.get(pTeam)).setScore(count);count-=1;
									firstturn=false;
								}
								else if(teamCount == pTeam){
									teamCount+=1;
								}
								else {
									objec.getScore(ChatColor.BOLD + mConfig.getTeamName(teamCount)).setScore(count);count-=1;
									objec.getScore(Stats.get(teamCount)).setScore(count);count-=1;
									teamCount+=1;
								}
							}
							objec.getScore(ChatColor.BOLD+"Your Stats").setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Kills "+ChatColor.WHITE+Integer.toString(kills.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Deaths "+ChatColor.WHITE+Integer.toString(deaths.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Streak "+ChatColor.WHITE+Integer.toString(streaks.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Recoveries "+ChatColor.WHITE+Integer.toString(recoveries.get(n))).setScore(count);count-=1;
							objec.getScore(ChatColor.GOLD+" Captures "+ChatColor.WHITE+Integer.toString(captures.get(n))).setScore(count);count-=1;
							
							objec.setDisplaySlot(DisplaySlot.SIDEBAR);
						}
					});
				}
				else {
					ArrayList<String> Stats = new ArrayList<String>();
					IntStream.range(0, mConfig.M_teams).forEachOrdered(n -> {
						String stat = mConfig.getTeamCol(n)+" Cap "+ChatColor.WHITE+Integer.toString(match.captures.get(n))+"/"+Integer.toString(mConfig.M_capLimit);
						if(match.isHome.get(n)){
							stat += mConfig.getTeamCol(n)+" Flag "+ChatColor.GREEN+"Home";
						}
						else {
							if(match.isDroped.get(n)){
								stat += mConfig.getTeamCol(n)+" Flag "+ChatColor.YELLOW+"Droped";
							}
							else {
								stat += mConfig.getTeamCol(n)+" Flag ";
								Player pla = Bukkit.getServer().getPlayer(match.capturedBy.get(n));
								stat += mConfig.getTeamCol(match.getPlayerTeam(pla))+match.capturedBy.get(n);
							}
						}
						Stats.add(stat);
					});
					String endsin = Main.secToStr(Main.timeleft);
					IntStream.range(0, players.size()).forEachOrdered(n -> {
						count = 0;
						teamCount = 0;
						Scoreboard sb = scores.get(n);
						Objective oldobj = sb.getObjective(DisplaySlot.SIDEBAR);
						oldobj.unregister();
						Objective objec = sb.registerNewObjective("init", "dummy");
						
						objec.setDisplayName("Ends in "+endsin);
						int pTeam = mConfig.M_match.getPlayerTeam(players.get(n));
						boolean firstturn=true;while(teamCount != mConfig.M_teams) {
							if(firstturn) {
								objec.getScore(ChatColor.BOLD + mConfig.getTeamName(pTeam) + ChatColor.RESET+mConfig.getTeamCol(pTeam)+" - Your Team").setScore(count);count-=1;
								objec.getScore(">"+Stats.get(pTeam)).setScore(count);count-=1;
								firstturn=false;
							}
							else if(teamCount == pTeam){
								teamCount+=1;
							}
							else {
								objec.getScore(Stats.get(teamCount)).setScore(count);count-=1;
								teamCount+=1;
							}
						}
						objec.getScore(ChatColor.BOLD+"Your Stats").setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Kills "+ChatColor.WHITE+Integer.toString(kills.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Deaths "+ChatColor.WHITE+Integer.toString(deaths.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Streak "+ChatColor.WHITE+Integer.toString(streaks.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Recoveries "+ChatColor.WHITE+Integer.toString(recoveries.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Captures "+ChatColor.WHITE+Integer.toString(captures.get(n))).setScore(count);count-=1;
						
						objec.setDisplaySlot(DisplaySlot.SIDEBAR);
					});
				}
			}
			// Zones
			else if(mConfig.M_match.isZones){
				Zones match = (Zones) mConfig.M_match;
				ArrayList<String> zonestats = new ArrayList<String>();
				ChatColor[] scofix = {ChatColor.WHITE, ChatColor.BLACK, ChatColor.BLUE, ChatColor.DARK_AQUA, ChatColor.DARK_GRAY, ChatColor.DARK_RED, ChatColor.GOLD, ChatColor.GRAY, ChatColor.GOLD, ChatColor.RED};
				IntStream.range(0, mConfig.M_amountzones).forEachOrdered(n -> {
					ChatColor dominator = ChatColor.WHITE;
					String teamname = "";
					if(match.isBeingCapped.get(n)){
						dominator = mConfig.M_teamCol.get(match.whodominate.get(n));
						teamname = " "+dominator+mConfig.getTeamName(match.whodominate.get(n));
					}
					String title;
					if(match.isDominated.get(n)){
						title = " " + dominator + ChatColor.BOLD + mConfig.M_zonename.get(n);
					}
					else{
						title = " " + ChatColor.WHITE + ChatColor.BOLD + mConfig.M_zonename.get(n);
					}
					title += ""+ChatColor.RESET+dominator+teamname;
					//\u2593
					int prog = match.capProgress.get(n);
					String progress =scofix[n]+""+ dominator + "";
					int left = 20;
					while(prog >=5){
						progress+="\u2593";
						prog-=5;
						left-=1;
					}
					progress+= ChatColor.GRAY+"";
					while(left!=0){
						progress+="\u2593";
						left-=1;
					}
					title+=ChatColor.WHITE+" "+Integer.toString(match.capProgress.get(n))+"%";
					zonestats.add(title);
					zonestats.add(progress);
					
				});
				String endsin = Main.secToStr(Main.timeleft);
				IntStream.range(0, players.size()).forEachOrdered(n -> {
					if(players.get(n).isOnline()){
						count = 0;
						Scoreboard sb = scores.get(n);
						Objective oldobj = sb.getObjective(DisplaySlot.SIDEBAR);
						oldobj.unregister();
						Objective objec = sb.registerNewObjective("init", "dummy");
						
						objec.setDisplayName("Ends in "+endsin);
						for(String str : zonestats){
							objec.getScore(str).setScore(count);count-=1;
						}
						objec.getScore(ChatColor.BOLD+" ").setScore(count);count-=1;
						objec.getScore(ChatColor.BOLD+"Your Stats").setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Kills "+ChatColor.WHITE+Integer.toString(kills.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Deaths "+ChatColor.WHITE+Integer.toString(deaths.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Streak "+ChatColor.WHITE+Integer.toString(streaks.get(n))).setScore(count);count-=1;
						
						objec.setDisplaySlot(DisplaySlot.SIDEBAR);
					}
				});
			}
			//KOTH
			else if(mConfig.M_match.isKOTH){
				String endsin = Main.secToStr(Main.timeleft);
				IntStream.range(0, players.size()).forEachOrdered(n -> {
					if(players.get(n).isOnline()){
						count = 0;
						Scoreboard sb = scores.get(n);
						Objective oldobj = sb.getObjective(DisplaySlot.SIDEBAR);
						oldobj.unregister();
						Objective objec = sb.registerNewObjective("init", "dummy");
						
						objec.setDisplayName("Ends in "+endsin);
						ArrayList<String> stats = mConfig.M_match.getDisplay(players.get(n));
						for(String str : stats){
							objec.getScore(str).setScore(count);count-=1;
						}
						objec.getScore(ChatColor.BOLD+" ").setScore(count);count-=1;
						objec.getScore(ChatColor.BOLD+"Your Stats").setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Kills "+ChatColor.WHITE+Integer.toString(kills.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Deaths "+ChatColor.WHITE+Integer.toString(deaths.get(n))).setScore(count);count-=1;
						objec.getScore(ChatColor.GOLD+" Streak "+ChatColor.WHITE+Integer.toString(streaks.get(n))).setScore(count);count-=1;
						
						objec.setDisplaySlot(DisplaySlot.SIDEBAR);
					}
				});
			}
		}
		else if(mConfig.M_match.isInPre) {
			waitplayers = ChatColor.GRAY+"";
			waitstatus = ChatColor.GRAY+"";
			if(Bukkit.getServer().getOnlinePlayers().size() < Main.playersToStart){
				if(waitanimation == 0){
					waitplayers += "\u204E\u2043\u2043";
					waitanimation += 1;
				}
				else if(waitanimation == 1){
					waitplayers += "\u2043\u204E\u2043";
					waitanimation += 1;
				}
				else if(waitanimation == 2){
					waitplayers += "\u2043\u2043\u204E";
					waitanimation += 1;
				}
				else if(waitanimation == 3){
					waitplayers += "\u2043\u204E\u2043";
					waitanimation = 0;
				}
				waitstatus += "Waiting for players";
				waitplayers += " "+Integer.toString(Bukkit.getServer().getOnlinePlayers().size())+"/"+Integer.toString(Main.playersToStart);
			}
			else{
				waitstatus += "Get Ready!";
				waitplayers+= "Starts in "+Main.secToStr(Main.timeleft);
			}
			IntStream.range(0, players.size()).forEachOrdered(n -> {
				if(players.get(n).isOnline()){
					count = 0;
					Scoreboard sb = scores.get(n);
					Objective oldobj = sb.getObjective(DisplaySlot.SIDEBAR);
					oldobj.unregister();
					Objective objec = sb.registerNewObjective("init", "dummy");
					
					objec.setDisplayName("Pre-round");
					objec.getScore(ChatColor.GRAY+""+ChatColor.BOLD+mConfig.getGameTypeName()).setScore(count);count-=1;
					objec.getScore(ChatColor.GRAY + "Map: "+ChatColor.WHITE+mConfig.M_name).setScore(count);count-=1;
					objec.getScore(ChatColor.GRAY +"by "+ChatColor.WHITE+mConfig.M_creator).setScore(count);count-=1;
					String pTeam = mConfig.getTeamName(mConfig.M_match.getPlayerTeam(players.get(n)));
					ChatColor pCol = mConfig.getTeamCol(mConfig.M_match.getPlayerTeam(players.get(n)));
					objec.getScore("§r").setScore(count);count-=1;
					objec.getScore(waitstatus).setScore(count);count-=1;
					objec.getScore(waitplayers).setScore(count);count-=1;
					objec.getScore("§b").setScore(count);count-=1;
					objec.getScore(ChatColor.GRAY+"Your Team: "+pCol+pTeam).setScore(count);count-=1;
					
					objec.setDisplaySlot(DisplaySlot.SIDEBAR);
				}
			});
		}
		else{
			winner = "";
			if(mConfig.M_match.winner == -1){
				winner = ChatColor.WHITE + "Tie";
			}
			else{
				winner = mConfig.M_teamCol.get(mConfig.M_match.winner)+mConfig.M_teamStr.get(mConfig.M_match.winner);
			}
			String endsin = Main.secToStr(Main.timeleft);
			IntStream.range(0, players.size()).forEachOrdered(n -> {
				if(players.get(n).isOnline()){
					count = 0;
					Scoreboard sb = scores.get(n);
					Objective oldobj = sb.getObjective(DisplaySlot.SIDEBAR);
					oldobj.unregister();
					Objective objec = sb.registerNewObjective("init", "dummy");
					
					objec.setDisplayName("Game Over");
					objec.getScore(ChatColor.GRAY+"Winner: "+winner).setScore(count);count-=1;
					objec.getScore(ChatColor.GRAY+"Reload in "+endsin).setScore(count);count-=1;
					objec.getScore("§r").setScore(count);count-=1;
					objec.getScore(ChatColor.BOLD+"Your Stats").setScore(count);count-=1;
					objec.getScore(ChatColor.GOLD+" Kills "+ChatColor.WHITE+Integer.toString(kills.get(n))).setScore(count);count-=1;
					objec.getScore(ChatColor.GOLD+" Deaths "+ChatColor.WHITE+Integer.toString(deaths.get(n))).setScore(count);count-=1;
					objec.getScore(ChatColor.GOLD+" Streak "+ChatColor.WHITE+Integer.toString(streaks.get(n))).setScore(count);count-=1;
					objec.getScore(ChatColor.GOLD+" Recoveries "+ChatColor.WHITE+Integer.toString(recoveries.get(n))).setScore(count);count-=1;
					objec.getScore(ChatColor.GOLD+" Captures "+ChatColor.WHITE+Integer.toString(captures.get(n))).setScore(count);count-=1;
					
					objec.setDisplaySlot(DisplaySlot.SIDEBAR);
				}
			});
		}
	}

	
	
}
