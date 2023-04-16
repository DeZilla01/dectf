package net.dezilla.ctf;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.dezilla.ctf.Entity.BaseEntity;
import net.dezilla.ctf.GameType.CTF;
import net.dezilla.ctf.GameType.KOTH;
import net.dezilla.ctf.GameType.Zones;
import net.dezilla.ctf.Kits.BaseKit;
import net.dezilla.ctf.Kits.Dragger;
import net.dezilla.ctf.Kits.Engineer;
import net.dezilla.ctf.Kits.Mage;
import net.dezilla.ctf.Kits.Medic;
import net.dezilla.ctf.Structures.BaseStructure;

public class TimeManager extends BukkitRunnable {
	public static ArrayList<Firework> toDetonate = new ArrayList<Firework>();
	Main plugin;
	int type;
	int counter;
	int flagid;
	CTF ctfobj;
	Block block;
	ArmorStand armor;
	Player player;
	Arrow spell_arrow;
	public TimeManager(Main plugin, int argType, int flagortime, CTF obj, Block blo, ArmorStand arm, Player p, Arrow arrow) {
		this.plugin = plugin;
		this.type = argType;
		if(this.type == 2){
			this.counter = 15;
			this.flagid = flagortime;
			this.ctfobj = obj;
		}
		if(this.type==4){
			this.block = blo;
			this.counter = flagortime;
		}
		if(this.type==6){
			this.armor = arm;
			this.flagid = flagortime;
			this.counter = 40;
		}
		if(this.type==8){
			this.player = p;
			this.counter = 5;
		}
		if(this.type==9){
			this.player = p;
			this.spell_arrow=arrow;
			this.counter=1;
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if(this.type == 3){
			// Every 5 ticks
			for(Player p : Bukkit.getServer().getOnlinePlayers()){
				Main.grabKit(p).update5();
				// Drager xp regen
				if(Main.grabKit(p) instanceof Dragger && p.getExp() < .99){
					p.setExp((float) (p.getExp()+.01));
				}
				if(Main.grabKit(p) instanceof Engineer){
					if(p.getExp() >= .015){
						p.setExp((float) (p.getExp()-.015));
					}
					else{
						p.setExp((float) 0.0);
					}
				}
			}
			//Structures
			for(BaseStructure struc : Main.structures){
				struc.update5tick();
			}
			//Entities
			for(BaseEntity entity : Main.entities) {
				entity.update5();
			}
		}
		//Every 1 sec
		if(this.type == 1){
			//spawn heal
			for(Player p : Bukkit.getServer().getOnlinePlayers()){
				int pteam = Main.mConfig.M_match.getPlayerTeam(p);
				Block blok = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
				if(Main.mConfig.M_oldspawn){
					byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};
					if(blok.getType() == Material.LEGACY_WOOL && blok.getData() == woolColorList[Main.mConfig.M_teamColInt.get(Main.mConfig.M_match.getPlayerTeam(p))]){
						if(blok.getRelative(BlockFace.DOWN).getType() == Material.BEDROCK){
							if(p.getHealth()<=19 && !p.isDead()){
								p.setHealth(p.getHealth()+1);
							}
						}
					}
				}
				else if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Main.mConfig.M_teamSblock.get(pteam) && p.getHealth()<=19){
					p.setHealth(p.getHealth()+1);
				}
			}
			// Kit update
			for(BaseKit i : Main.Kkits){
				i.update20();
			}
			//Zones
			if(Main.mConfig.M_gametype == 2 && Main.mConfig.M_match.isInProg){
				Zones match = (Zones) Main.mConfig.M_match;
				match.update();
			}
			else if(Main.mConfig.M_gametype == 5 && Main.mConfig.M_match.isInProg){
				KOTH match = (KOTH) Main.mConfig.M_match;
				match.update();
			}
			//Potion Effect
			ArrayList<Player> toRemove = new ArrayList<Player>();
			for(Player p : Main.dedResp){
				if(!p.isOnline()){
					toRemove.add(p);
				}
				else if(!p.isDead()){
					Main.grabKit(p).potEffect();
					Main.grabKit(p).resExp();
					Main.grabKit(p).setHunger();
					toRemove.add(p);
				}
			}
			for(Player p : toRemove){
				Main.dedResp.remove(p);
			}
			//Structures
			for(BaseStructure struc : Main.structures){
				struc.update20tick();
			}
			//Entities
			for(BaseEntity entity : Main.entities) {
				entity.update20();
			}
			//Time Left
			Main.timeleft = Main.timeleft-1;
			if(Main.mConfig.M_match.isInPre && Bukkit.getServer().getOnlinePlayers().size() < Main.playersToStart){
				Main.timeleft = 60;
			}
			if(Main.timeleft <=0){
				if(Main.mConfig.M_match.isInPre){
					Main.mConfig.M_match.StartGame();
				}
				else if(Main.mConfig.M_match.isInProg){
					Main.mConfig.M_match.endGame();
				}
				else {
					Bukkit.getServer().shutdown();
				}
			}
			//Scoreboard
			ScoreManager.update(Main.mConfig);
			
		}
		//Droped Flag
		else if(this.type == 2){
			if(this.counter == 0){
				ctfobj.dropedToHome(flagid);
				int id = this.getTaskId();
				Bukkit.getServer().getScheduler().cancelTask(id);
			}
			else {
				ctfobj.setFlagReset(counter, flagid);
				this.counter -=1;
			}
		}
		//Medic Web
		else if(this.type==4){
			if(counter != 0){
				counter -=1;
			}
			else{
				block.setType(Material.AIR);
				Bukkit.getServer().getScheduler().cancelTask(this.getTaskId());
			}
		}
		//every 5 seconds
		else if(this.type == 5){
			for(Player p : Bukkit.getOnlinePlayers()){
				if(Main.grabKit(p) instanceof Medic){
					if(p.getHealth()<=19 && !p.isDead()){
						p.setHealth(p.getHealth()+1);
					}
					int amountWeb = 0;
					for(ItemStack is : p.getInventory().getContents()){
						if(is instanceof ItemStack){
							if(is.getType() == Material.SNOWBALL){
								amountWeb += is.getAmount();
							}
						}
					}
					if(amountWeb<5){
						p.getInventory().addItem(new ItemStack(Material.SNOWBALL));
					}
				}
			}
			ArrayList<BaseStructure> strucs = new ArrayList<BaseStructure>(Main.structures);
			for(BaseStructure struc : strucs){
				struc.update5sec();
			}
		}
		// Dragger blackhole
		else if(this.type == 6){
			counter -=1;
			for(Entity ent : Main.mConfig.M_world.getEntities()){
				boolean pass = true;
				if(ent instanceof Player){
					Player victim = (Player) ent;
					if(Main.mConfig.M_match.getPlayerTeam(victim) == flagid){
						pass = false;
					}
				}
				if(armor.getLocation().distance(ent.getLocation()) <= 5 && pass){
					double x = armor.getLocation().getX() - ent.getLocation().getX();
					double y = armor.getLocation().getY() - ent.getLocation().getY();
					double z = armor.getLocation().getY() - ent.getLocation().getY();
					if(x<0){x*=-1;}if(y<0){y*=-1;}if(z<0){z*=-1;}
					x = ((x-2.5)*-1)+2.5;
					y = ((y-2.5)*-1)+2.5;
					z = ((z-2.5)*-1)+2.5;
					x *= 0.1;
					y *= 0.1;
					z *= 0.1;
					if(armor.getLocation().getX() - ent.getLocation().getX() < 0){x*=-1;}
					if(armor.getLocation().getY() - ent.getLocation().getY() < 0){y*=-1;}
					if(armor.getLocation().getZ() - ent.getLocation().getZ() < 0){z*=-1;}
					x+=ent.getVelocity().getX();
					y+=ent.getVelocity().getY();
					z+=ent.getVelocity().getZ();
					Vector vec = new Vector(x, y, z);
					ent.setVelocity(vec);
				}
			}
			if(counter <= 0){
				armor.remove();
				Bukkit.getServer().getScheduler().cancelTask(this.getTaskId());
			}
		}
		// Every 1 tick
		else if(this.type == 7){
			for(BaseKit i : Main.Kkits){
				i.update1();
			}
			ArrayList<BaseStructure> strucs = new ArrayList<BaseStructure>(Main.structures);
			for(BaseStructure struc : strucs){
				struc.update1tick();
			}
			//Entities
			for(BaseEntity entity : Main.entities) {
				entity.update1();
			}
			if(toDetonate.size()>0){
				for(Firework i : toDetonate){
					i.detonate();
				}
				while(!toDetonate.isEmpty()){
					toDetonate.remove(0);
				}
			}
		}
		// New Respawn
		else if(this.type == 8){
			if(counter <= 0){
				Main.mConfig.M_match.tpPlayerToSpawn(player);
				player.setGameMode(GameMode.SURVIVAL);
				Bukkit.getServer().getScheduler().cancelTask(this.getTaskId());
			}
			counter -=1;
		}
		//Mage Dmg Spell
		else if(this.type == 9){
			if(counter ==1){
				counter = 0;
			}
			else{
				if(!(spell_arrow.isDead() || spell_arrow.isOnGround())){
					Mage.dmgExplosion(spell_arrow.getLocation(), Main.mConfig.M_teamColInt.get(Main.getTeamId(player)));
					spell_arrow.remove();
				}
				Bukkit.getServer().getScheduler().cancelTask(this.getTaskId());
			}
		}
	}
}
