package net.dezilla.ctf;


import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang.SystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.Command.TestCommand;
import net.dezilla.ctf.Entity.BaseEntity;
import net.dezilla.ctf.GameType.BaseGame;
import net.dezilla.ctf.GameType.CTF;
import net.dezilla.ctf.Kits.BaseKit;
import net.dezilla.ctf.Kits.Heavy;
import net.dezilla.ctf.Kits.Soldier;
import net.dezilla.ctf.Structures.BaseStructure;
import net.dezilla.ctf.Structures.Spawner;
import net.dezilla.ctf.Structures.Turret;
@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener {
	//mConfig is the configuration master
	public static MapConfig mConfig;
	public static Main plugin;
	public static ArrayList<Player> Kplayers = new ArrayList<Player>();
	public static ArrayList<BaseKit> Kkits = new ArrayList<BaseKit>();
	public static ArrayList<Player> dedResp = new ArrayList<Player>();
	public static int timeleft = 60;
	public static boolean loaded = false;
	public static ArrayList<BaseStructure> structures = new ArrayList<BaseStructure>();
	public static ArrayList<BaseEntity> entities = new ArrayList<BaseEntity>();
	TimeManager timer;
	TimeManager timerquick;
	TimeManager timerslow;
	TimeManager timerveryquick;
	public static int playersToStart = 6;
	public static String fileSlash;
	
	@Override
	public void onLoad() {
		// Linux/Windows file pattern
		if(SystemUtils.IS_OS_WINDOWS){
			fileSlash = "\\";
		} else {
			fileSlash = "/";
		}
		//Command register
		//this.getCommand("testy").setExecutor(new TestCommand());
	}
	
	
	
	@Override
	public void onEnable() {
		org.bukkit.Bukkit.getServer().getPluginManager().registerEvents(this, this);
		plugin = this;
		getLogger().info("DeCTF by DeZilla ver 0.1");
		File BaseFolder = new File(Bukkit.getServer().getWorld("world").getWorldFolder().toString()+fileSlash+"playerdata");
		for(OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()){
			File playerFile = new File(BaseFolder.toString()+fileSlash+p.getUniqueId().toString()+".dat");
            playerFile.delete();
		}
		mConfig = MapLoader.createRandomGame();
		timer = new TimeManager(this, 1, 0, null, null, null, null, null);
		timer.runTaskTimer(this, 0, 20);
		timerquick = new TimeManager(this, 3, 0, null, null, null, null, null);
		timerquick.runTaskTimer(this, 0, 5);
		timerslow = new TimeManager(this, 5, 0, null, null, null, null, null);
		timerslow.runTaskTimer(this, 0, 100);
		timerveryquick = new TimeManager(this, 7, 0, null, null, null, null, null);
		timerveryquick.runTaskTimer(this, 0, 1);
		loaded = true;
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("Disabling DeCTF");
	}
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent e) {
		if(loaded){
			String motd = "";
			motd+=ChatColor.GRAY+"Mode: "+ChatColor.WHITE+mConfig.getGameTypeName()+ChatColor.DARK_AQUA+" Welcome to "+ChatColor.GOLD+"shittyeff"+"\n";
			motd+=ChatColor.GRAY+"Map: "+ChatColor.WHITE+mConfig.M_name+ChatColor.GRAY+" by "+ChatColor.WHITE+mConfig.M_creator;
			e.setMotd(motd);
		}
		else{
			e.setMotd(""+ChatColor.ITALIC+ChatColor.RED+"loading...");
		}
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();
		// Add player to a team
		BaseGame match = mConfig.getMatch();
		match.addPlayer(player);
		// Scoreboard
		ScoreManager.addPlayer(player);
		// Hunger
		player.setFoodLevel(18);
		// Kit
		if(!Kplayers.contains(player)){
			Kplayers.add(player);
			Kkits.add(new Heavy(player, 0));
		}
		// Teleport
		match.tpPlayerToSpawn(player);
		//hide and show players
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			p.hidePlayer(player);
			player.hidePlayer(p);
			p.showPlayer(player);
			player.showPlayer(p);
		}
		
		player.sendMessage(ChatColor.GOLD+"Welcome "+player.getName()+", this shit is still WIP, unstable and stuff. So ye if your read this you're most likely here to test stuff.");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(mConfig.M_gametype==1){
			CTF match = (CTF) mConfig.M_match;
			if(match.capturedBy.contains(p.getName())){
				match.restoreFlag(match.capturedBy.indexOf(p.getName()));
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		mConfig.getMatch().resPlayerToSpawn(e, p);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (alias.equalsIgnoreCase("spawn")) {
			Player player = (Player) sender;
			CommandMain.cSpawn(mConfig, player);
			return false;
		}
		
		else if (alias.equalsIgnoreCase("info")) {
			Player player = (Player) sender;
			CommandMain.cInfo(mConfig, player);
			return false;
		}
		
		else if (alias.equalsIgnoreCase("switch")) {
			Player player = (Player) sender;
			CommandMain.cSwitch(mConfig, player);
			return false;
		}
		
		else if (alias.equalsIgnoreCase("forcestart")) {
			Player p = (Player) sender;
			CommandMain.cForceStart(mConfig, p);
			return false;
		}
		
		else if (alias.equalsIgnoreCase("updatesb")) {
			Player p = (Player) sender;
			CommandMain.cUpdateSb(mConfig, p);
			return false;
		}
		else{
			CommandMain.aCommand(sender, command, alias, args);
		}
		return false;
			
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(final EntityDamageEvent e) {
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
			//No damage if match is not in progress
			if(!mConfig.M_match.isInProg) {
				e.setCancelled(true);
			}
			//No damage for Soldier
			else if(grabKit(p) instanceof Soldier && e.getCause() == DamageCause.FALL){
				e.setCancelled(true);
			}
			//No damage if player is in spawn (old zones spawn)
			else if(mConfig.M_oldspawn){
				byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};
				if(b.getType() == Material.LEGACY_WOOL && b.getData() == woolColorList[mConfig.M_teamColInt.get(mConfig.M_match.getPlayerTeam(p))]){
					if(b.getRelative(BlockFace.DOWN).getType() == Material.BEDROCK){
						e.setCancelled(true);
					}
				}
			}
			//No damage if player is in spawn
			else if(mConfig.getTeamSblock(mConfig.M_match.getPlayerTeam(p)) == b.getType()){
				e.setCancelled(true);
			}
			//Kit specific
			else {
				grabKit(p).onDamaged(e);
			}
		}
		
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			Player victim = (Player) e.getEntity();
			if(e.getDamager() instanceof Player){
				Player attacker = (Player) e.getDamager();
				grabKit(attacker).onDamage(victim, e);
			}
			else if(e.getDamager() instanceof Arrow){
				Arrow arrow = (Arrow) e.getDamager();
				if(arrow.getShooter() instanceof Player){
					Player attacker = (Player) arrow.getShooter();
					if(mConfig.M_match.getPlayerTeam(attacker) == mConfig.M_match.getPlayerTeam(victim)){
						e.setCancelled(true);
					}
					else{
						grabKit(attacker).onBowAttack(victim, e);
					}
				}
			}
			if(e.getDamager() instanceof Firework){
				e.setCancelled(true);
			}
		}
		else if(e.getEntity() instanceof ArmorStand){
			ArmorStand armor = (ArmorStand) e.getEntity();
			for(BaseStructure i : structures){
				if(i instanceof Turret){
					Turret turret = (Turret) i;
					if(armor == turret.disp){
						if(e.getDamager() instanceof Player){
							Player p = (Player) e.getDamager();
							if(p.getName().equalsIgnoreCase(turret.owner.getName())){
								turret.hp-=30.0;
							}
							else if(mConfig.M_match.getPlayerTeam(p) != turret.team){
								turret.hp-=e.getDamage();
							}
						}
						else if(e.getDamager() instanceof Arrow){
							Arrow arrow = (Arrow) e.getDamager();
							if(arrow.getShooter() instanceof Player){
								Player p = (Player) arrow.getShooter();
								if(mConfig.M_match.getPlayerTeam(p) != turret.team){
									turret.hp-=e.getDamage();
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerHungerEvent(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(mConfig.M_match.isInProg){
				grabKit(p).setHunger();
			}
			else{
				p.setFoodLevel(20);
			}
			e.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMoveEvent(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(isOnSpawn(p)){
			killStruc(p);
		}
		if(mConfig.M_match.isInProg){
			//ctf
			if(mConfig.M_gametype == 1){
				CTF match = (CTF) mConfig.M_match;
				match.stealCheck(p);
				match.dropCheck(p);
				if(match.capturedBy.contains(p.getName())){
					match.captureCheck(p);
					if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == mConfig.M_teamSblock.get(mConfig.M_match.getPlayerTeam(p))){
						match.restoreFlag(match.capturedBy.indexOf(p.getName()));
						p.removePotionEffect(PotionEffectType.GLOWING);
						grabKit(p).setHelmet();
						for(ItemStack is : p.getInventory().getContents()){
							if(is instanceof ItemStack){
								if(is.getType() == Material.LEGACY_BANNER){
									p.getInventory().remove(is);
								}
							}
						}
					}
				}
			}
			Block blok = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
			if(mConfig.M_oldspawn && p.getGameMode() == GameMode.SURVIVAL){
				byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};
				if(blok.getType() == Material.LEGACY_WOOL && blok.getData() != woolColorList[mConfig.M_teamColInt.get(mConfig.M_match.getPlayerTeam(p))]){
					if(blok.getRelative(BlockFace.DOWN).getType() == Material.BEDROCK){
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, (float) 10.0, (float) 1);
						p.sendMessage("Don't walk in the enemy's spawn dumbass.");
						p.setHealth(0);
					}
				}
			}
			else if(mConfig.M_teamSblock.contains(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType()) && p.getGameMode() == GameMode.SURVIVAL){
				if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != mConfig.M_teamSblock.get(mConfig.M_match.getPlayerTeam(p))){
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, (float) 10.0, (float) 1);
					p.sendMessage("Don't walk in the enemy's spawn dumbass.");
					p.setHealth(0);
				}
			}
			for(BaseStructure i : structures){
				if(i.strucBlocks.contains(p.getLocation().getBlock().getRelative(BlockFace.DOWN))){
					i.onStep(p);
				}
			}
		}
		if(p.getLocation().getY() < -20){
			if(p.getGameMode() == GameMode.SPECTATOR){
				p.teleport(mConfig.M_spawn);
			}
			else{
				if(mConfig.M_match.isInProg){
					EntityDamageEvent event = new EntityDamageEvent(p, DamageCause.VOID, 999.0);
					p.setLastDamageCause(event);
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
				else{
					mConfig.M_match.tpPlayerToSpawn(p);
				}
			}
		}
	}
	 @EventHandler
	 public void onPlayerDeath(PlayerDeathEvent e){
		 if(e.getEntity() instanceof Player){
			 Player p = e.getEntity();
			 //structures
			 killStruc(p);
			 //killscore
			 if(p.getKiller() instanceof Player){
				 ScoreManager.addKill(p.getKiller());
				 ScoreManager.addStreak(p.getKiller());
			 }
			 ScoreManager.addDeath(p);
			 ScoreManager.resStreak(p);
			 dedResp.add(p);
			 //New Respawn
			 p.setHealth(20.0);
			 p.setGameMode(GameMode.SPECTATOR);
			 TimeManager resTimer = new TimeManager(this, 8, 0, null, null, null, p, null);
			 resTimer.runTaskTimer(this, 0, 20);
			 //CTF
			 if(mConfig.M_gametype == 1){
				 CTF match = (CTF) mConfig.M_match;
				 if(match.capturedBy.contains(p.getName())){
					 match.dropFlag(p);
				 }
			 }
		 }
	 }
	 public static void killStruc(Player p){
		 ArrayList<BaseStructure> toRem = new ArrayList<BaseStructure>();
		 for(BaseStructure bs : structures){
			 if(bs.owner.getName().equalsIgnoreCase(p.getName())){
				 toRem.add(bs);
			 }
		 }
		 for(BaseStructure bs : toRem){
			 bs.destroy();
		 }
	 }
	
	 @EventHandler
	 public void onPlayerClick(PlayerInteractEvent e) {
		 Player p = e.getPlayer();
		 if(mConfig.M_match.isInProg){
			 if(!grabKit(p).strucClick(e)){
				 if(p.getItemInHand().getType() == Material.COOKED_BEEF){
					 if(p.getHealth() < 20){
						 p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, (float) 10.0, (float) 1);
						 if(p.getItemInHand().getAmount() == 1){
							 p.setItemInHand(new ItemStack(Material.AIR));
						 }
						 else {
							 p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
						 }
						 double hp = p.getHealth()+8;
						 if(hp > 20) hp=20;
						 p.setHealth(hp);
					 }
					 e.setCancelled(true);
					 
				 }
				 Kkits.get(Kplayers.indexOf(p)).onItemClick(e);
			 }
		 }
		 
	 }
	 @EventHandler
	 public void onPlayerInteract(PlayerInteractEntityEvent e){
		 Player p = e.getPlayer();
		 if(mConfig.M_match.isInProg){
			 grabKit(p).onEntityInteract(e);
		 }
	 }
	 public static BaseKit grabKit(Player p){
		 if(Kplayers.contains(p)){
			 BaseKit kit = Kkits.get(Kplayers.indexOf(p));
			 return kit;
		 }
		 else {
			 Kplayers.add(p);
			 BaseKit kit = new Heavy(p, 0);
			 Kkits.add(kit);
			 return kit;
		 }
	 }
	 @EventHandler
	 public void onEntityShootBowEvent(EntityShootBowEvent e){
		 if(e.getEntity() instanceof Player){
			 Player p = (Player) e.getEntity();
			 grabKit(p).onShootBow(e);
		 }
	 }
	 @EventHandler
	 public void onInventoryClickEvent(InventoryClickEvent e){
		 InventoryAction[] dropevents = {InventoryAction.DROP_ALL_CURSOR, InventoryAction.DROP_ALL_SLOT, InventoryAction.DROP_ONE_CURSOR, InventoryAction.DROP_ONE_SLOT};
		 for(InventoryAction ia : dropevents){
			 if(e.getAction() == ia){
				 e.setCancelled(true);
			 }
		 }
		 InventoryAction[] pickupEvents = {InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_HALF, InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_SOME, InventoryAction.MOVE_TO_OTHER_INVENTORY};
		 for(InventoryAction ia : pickupEvents){
			 if(e.getAction() == ia){
				 if(e.getRawSlot() >= 5 && e.getRawSlot() <= 8){
					 e.setCancelled(true);
				 }
			 }
		 }
	 }
	 @EventHandler
	 public void onPlayerDropItemEvent(PlayerDropItemEvent e){
		 //CTF
		 if(e.getItemDrop().getItemStack().getType() == Material.LEGACY_BANNER && mConfig.M_match.isCTF){
			 CTF match = (CTF) mConfig.M_match;
			 if(match.capturedBy.contains(e.getPlayer().getName())){
				 match.dropFlag(e.getPlayer());
				 e.getItemDrop().remove();
			 }
		 }
		 else{
			 e.setCancelled(true);
		 }
	 }
	 @EventHandler
	 public void onProjectileHitEvent(ProjectileHitEvent e){
		 if(e.getEntity().getShooter() instanceof Player){
			 Player p = (Player) e.getEntity().getShooter();
			 grabKit(p).onProjectileHitEvent(e);
		 }
		 
	 }
	 @EventHandler
	 public void onCreatureSpawn(CreatureSpawnEvent event){
	     if (event.getSpawnReason() == SpawnReason.EGG){
	        event.setCancelled(true);
	     }
	 }
	 @EventHandler
	 public void onBlockPlace(BlockPlaceEvent e) {
		 grabKit(e.getPlayer()).onBlockPlace(e);
	 }
	 @EventHandler
	 public void onBlockBreak(BlockBreakEvent e){
		 grabKit(e.getPlayer()).onBlockBreak(e);
	 }
	 @EventHandler
	 public void onPickUp(PlayerPickupItemEvent e){
		 e.getItem().remove();
		 e.setCancelled(true);
	 }
	 public static String secToStr(int time){
		 int minutes = 0;
		 while(time>=60){
			 minutes += 1;
			 time -=60;
		 }
		 String timeStr = Integer.toString(minutes)+":"+String.format("%02d", time);
		 return timeStr;
	 }
	 @EventHandler
	 public void onAPlayerSaidSomethingUseless(AsyncPlayerChatEvent e){
		 String msg = ChatColor.GRAY+e.getPlayer().getName()+mConfig.M_teamCol.get(mConfig.M_match.getPlayerTeam(e.getPlayer()))+" � ";
		 if(String.valueOf(e.getMessage().charAt(0)).equals("!")){
			 msg += ChatColor.GOLD+"!Team "+ChatColor.WHITE+e.getMessage().substring(1);
			 for(Player pla : Bukkit.getServer().getOnlinePlayers()){
				 if(mConfig.M_match.getPlayerTeam(e.getPlayer()) == mConfig.M_match.getPlayerTeam(pla)){
					 pla.sendMessage(msg);
				 }
			 }
			 e.setCancelled(true);
		 }
		 else{
			 msg += ChatColor.WHITE+e.getMessage();
			 Bukkit.getServer().broadcastMessage(msg);
			 e.setCancelled(true);
		 }
	 }
	 @EventHandler
	 public void onSpawnerEvent(SpawnerSpawnEvent e){
		 ArrayList<CreatureSpawner> spawners = new ArrayList<CreatureSpawner>();
		 for(BaseStructure bs : structures){
			 if(bs instanceof Spawner){
				 Spawner sp = (Spawner) bs;
				 spawners.add(sp.getSpawner());
			 }
		 }
		 if(spawners.contains(e.getSpawner())){
			 e.setCancelled(true);
		 }
	 }
	public static boolean isOnSpawn(Player p){
		 Block blok = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		 if(mConfig.M_oldspawn){
			 if(blok.getType() == Material.LEGACY_WOOL){
				 byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};
				 if(blok.getData() == woolColorList[mConfig.M_teamColInt.get(mConfig.M_match.getPlayerTeam(p))]){
					 if(blok.getRelative(BlockFace.DOWN).getType() == Material.BEDROCK){
						 return true;
					 }
				 }
			 }
		 }
		 else if(blok.getType() == mConfig.M_teamSblock.get(mConfig.M_match.getPlayerTeam(p))){
			 return true;
		 }
		 return false;
	 }
	 public static int getTeamId(Player p){
		 return mConfig.M_match.getPlayerTeam(p);
	 }
	 public static DyeColor getDyeColor(Player p) {
		 return MapConfig.flacCol[mConfig.M_teamColInt.get(getTeamId(p))];
	 }
}

