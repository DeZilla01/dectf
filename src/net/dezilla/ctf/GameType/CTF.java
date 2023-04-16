package net.dezilla.ctf.GameType;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.MapConfig;
import net.dezilla.ctf.ScoreManager;
import net.dezilla.ctf.TimeManager;
import net.dezilla.ctf.Kits.Ninja;

public class CTF extends BaseGame implements Listener{
	short[] flagCol = {0, 4, 2, 6, 1, 5, 14, 7, 8, 4, 10, 12, 1, 13, 11, 15}; 
	public ArrayList<ArmorStand> Flags = new ArrayList<ArmorStand>();
	public ArrayList<Boolean> isHome = new ArrayList<Boolean>();
	public ArrayList<Boolean> isDroped = new ArrayList<Boolean>();
	public ArrayList<ArmorStand> dropedFlags = new ArrayList<ArmorStand>();
	public ArrayList<String> capturedBy = new ArrayList<String>();
	public ArrayList<Integer> captures = new ArrayList<Integer>();
	public ArrayList<TimeManager> timers = new ArrayList<TimeManager>();
	public ArrayList<Integer> flagResets = new ArrayList<Integer>();

	public CTF(MapConfig conf) {
		super(conf);
		IntStream.range(0, wConfig.M_teams).forEachOrdered(n -> {
			captures.add(0);
		});
	}
	
	@Override
	public void StartGame() {
		isInProg = true;
		isInPre = false;
		Main.timeleft = wConfig.M_timelimit;
		IntStream.range(0, wConfig.M_teams).forEachOrdered(n -> {
			ArmorStand a = (ArmorStand) wConfig.M_world.spawnEntity(wConfig.M_teamFlag.get(n), EntityType.ARMOR_STAND);
			ItemStack flagItem = getFlag(n);
			a.setHelmet(flagItem);
			a.setAI(false);
			a.setGravity(false);
			a.setVisible(false);
			a.setMarker(true);
			a.setSmall(true);
			a.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
			Flags.add(a);
			isHome.add(true);
			isDroped.add(false);
			capturedBy.add(null);
			dropedFlags.add(null);
			timers.add(null);
			flagResets.add(0);
		});
		for(Player p : Bukkit.getOnlinePlayers()) {
			tpPlayerToSpawn(p);
		}
		
	}
	
	public void stealCheck(Player p){
		IntStream.range(0, wConfig.M_teams).forEachOrdered(n -> {
			boolean ninjacheck = true;
			if(Main.grabKit(p) instanceof Ninja){
				Ninja kit = (Ninja) Main.grabKit(p);
				if(kit.isHidden){
					ninjacheck = false;
				}
			}
			if(getPlayerTeam(p) != n && wConfig.M_teamFlag.get(n).distance(p.getLocation()) <= (double) 2 && isHome.get(n) && ninjacheck && p.getGameMode() == GameMode.SURVIVAL){
				isHome.remove(n);
				isHome.add(n, false);
				capturedBy.remove(n);
				capturedBy.add(n, p.getName());
				Flags.get(n).setHelmet(new ItemStack(Material.BLACK_BANNER));
				Flags.get(n).removePotionEffect(PotionEffectType.GLOWING);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
				ItemStack flagItem = getFlag(n);
				p.getInventory().addItem(flagItem);
				p.getInventory().setHelmet(flagItem);
				wConfig.M_world.strikeLightningEffect(p.getLocation());
				
			}
		});
	}
	
	public void captureCheck(Player p){
		IntStream.range(0, wConfig.M_teams).forEachOrdered(n -> {
			int flagid = capturedBy.indexOf(p.getName());
			if(getPlayerTeam(p) == n && isHome.get(n) && !isHome.get(flagid) && wConfig.M_teamFlag.get(n).distance(p.getLocation()) <= (double) 2){
				isHome.remove(flagid);
				isHome.add(flagid, true);
				capturedBy.remove(flagid);
				capturedBy.add(flagid, null);
				ItemStack flagItem = getFlag(flagid);
				Flags.get(flagid).setHelmet(flagItem);
				Flags.get(flagid).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
				p.removePotionEffect(PotionEffectType.GLOWING);
				//p.getInventory().remove(arg0);
				Main.grabKit(p).setHelmet();
				for(ItemStack is : p.getInventory().getContents()){
					if(is instanceof ItemStack){
						if(is.getType() == flagItem.getType()){
							p.getInventory().remove(is);
						}
					}
				}
				wConfig.M_world.strikeLightningEffect(p.getLocation());
				ScoreManager.addCapture(p);
				int capcount = captures.get(n);
				captures.remove(n);
				captures.add(n, capcount+1);
				if(capcount+1 >= wConfig.M_capLimit){
					endGame();
				}
			}
		});
	}
	@SuppressWarnings("deprecation")
	public void dropFlag(Player p){
		int flagid = capturedBy.indexOf(p.getName());
		capturedBy.remove(flagid);
		capturedBy.add(flagid, null);
		isDroped.remove(flagid);
		isDroped.add(flagid, true);
		Location flagloc = p.getLocation();
		flagloc.add(0, 0, 0);
		flagloc.setPitch(0);
		ArmorStand a = (ArmorStand) wConfig.M_world.spawnEntity(flagloc, EntityType.ARMOR_STAND);
		a.setVisible(false);
		a.setSmall(true);
		a.setAI(false);
		a.setGravity(false);
		a.setMarker(true);
		ItemStack flagItem = getFlag(flagid);
		a.setHelmet(flagItem);
		a.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
		for(ItemStack is : p.getInventory().getContents()){
			if(is instanceof ItemStack){
				if(is.getType() == Material.LEGACY_BANNER){
					p.getInventory().remove(is);
				}
			}
		}
		Main.grabKit(p).setHelmet();
		p.removePotionEffect(PotionEffectType.GLOWING);
		dropedFlags.remove(flagid);
		dropedFlags.add(flagid, a);
		TimeManager timer = new TimeManager(Main.plugin, 2, flagid, this, null, null, null, null);
		timer.runTaskTimer(Main.plugin, 0, 20);
		timers.remove(flagid);
		timers.add(flagid, timer);
		flagResets.remove(flagid);
		flagResets.add(flagid, 15);
	}
	public void setFlagReset(int time, int teamid){
		//just time info
		flagResets.remove(teamid);
		flagResets.add(teamid, time);
	}
	public void dropedToHome(int flagid){
		ArmorStand a = dropedFlags.get(flagid);
		dropedFlags.remove(flagid);
		dropedFlags.add(flagid, null);
		isDroped.remove(flagid);
		isDroped.add(flagid, false);
		isHome.remove(flagid);
		isHome.add(flagid, true);
		ItemStack flagItem = getFlag(flagid);
		Flags.get(flagid).setHelmet(flagItem);
		Flags.get(flagid).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
		a.remove();
	}
	public void restoreFlag(int flagid){
		capturedBy.remove(flagid);
		capturedBy.add(flagid, null);
		isHome.remove(flagid);
		isHome.add(flagid, true);
		ItemStack flagItem = getFlag(flagid);
		Flags.get(flagid).setHelmet(flagItem);
		Flags.get(flagid).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
	}
	public void dropCheck(Player p ){
		IntStream.range(0, wConfig.M_teams).forEachOrdered(n -> {
			if(isDroped.get(n)){
				boolean ninjacheck = true;
				if(Main.grabKit(p) instanceof Ninja){
					Ninja kit = (Ninja) Main.grabKit(p);
					if(kit.isHidden){
						ninjacheck = false;
					}
				}
				if(dropedFlags.get(n).getLocation().distance(p.getLocation()) <= 2 && flagResets.get(n) < 15 && ninjacheck && p.getGameMode() == GameMode.SURVIVAL){
					if(getPlayerTeam(p) == n){
						//recovery
						ArmorStand a = dropedFlags.get(n);
						dropedFlags.remove(n);
						dropedFlags.add(n, null);
						isDroped.remove(n);
						isDroped.add(n, false);
						isHome.remove(n);
						isHome.add(n, true);
						ItemStack flagItem = getFlag(n);
						Flags.get(n).setHelmet(flagItem);
						Flags.get(n).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
						a.remove();
						ScoreManager.addRecovery(p);
						Bukkit.getServer().getScheduler().cancelTask(timers.get(n).getTaskId());
					}
					else{
						//steal
						ArmorStand a = dropedFlags.get(n);
						dropedFlags.remove(n);
						dropedFlags.add(n, null);
						isDroped.remove(n);
						isDroped.add(n, false);
						capturedBy.remove(n);
						capturedBy.add(n, p.getName());
						p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999999, 1));
						ItemStack flagItem = getFlag(n);
						p.getInventory().addItem(flagItem);
						p.getInventory().setHelmet(flagItem);
						a.remove();
						wConfig.M_world.strikeLightningEffect(p.getLocation());
						Bukkit.getServer().getScheduler().cancelTask(timers.get(n).getTaskId());
					}
				}
			}
		});
	}
	private static ItemStack getFlag(int teamID) {
		Material[] flagList = {Material.BLACK_BANNER, Material.BLUE_BANNER, Material.GREEN_BANNER, Material.CYAN_BANNER, Material.RED_BANNER, Material.PURPLE_BANNER, Material.ORANGE_BANNER, Material.LIGHT_GRAY_BANNER, Material.GRAY_BANNER, Material.BLUE_BANNER, Material.LIME_BANNER, Material.LIGHT_BLUE_BANNER, Material.RED_BANNER, Material.MAGENTA_BANNER, Material.YELLOW_BANNER, Material.WHITE_BANNER};
		int colorID = Main.mConfig.M_teamColInt.get(teamID);
		ItemStack flag = new ItemStack(flagList[colorID]);
		return flag;
	}


}
