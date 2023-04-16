package net.dezilla.ctf.Structures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Cake;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.ctf.IDTools;
import net.dezilla.ctf.Main;

public class Dispencer extends BaseStructure{
	public Block cake;

	public Dispencer(Player p, Block loc) {
		super(p, loc);
		//Glass
		byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};
		previousStuff.add(Material.AIR);
		IDTools idt = new IDTools();
		loc.setType(idt.getMaterial(95, (int) woolColorList[Main.mConfig.M_teamColInt.get(team)]));
		strucBlocks.add(loc);
		//Cake
		this.cake = loc.getRelative(BlockFace.UP);
		previousStuff.add(cake.getType());
		cake.setType(Material.CAKE);
		//cake.setData((byte) 6);
		Cake c = (Cake) cake.getBlockData();
		c.setBites(0);
		cake.setBlockData(c);
		strucBlocks.add(cake);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(Player p){
		if(Main.mConfig.M_match.getPlayerTeam(p) == team){
			if(cake.getData()<6){
				if(p.getHealth()<20){
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 4));
				}
				else{
					Main.grabKit(p).newInventory();
				}
				//cake.setData((byte) (cake.getData()+1));
				Cake c = (Cake) cake.getBlockData();
				c.setBites(this.cake.getData()+1);
				cake.setBlockData(c);
			}
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void update5sec(){
		if(!owner.isOnline()){
			destroy();
		}
		if(this.cake.getData()>0){
			//this.cake.setData((byte) (this.cake.getData()-1));
			Cake c = (Cake) cake.getBlockData();
			c.setBites(this.cake.getData()-1);
			cake.setBlockData(c);
		}
	}
	public static boolean SpaceCheck(Block loc){
		boolean check = BaseStructure.SpaceCheck(loc);
		if(check){
			if(loc.getRelative(BlockFace.UP).getType() != Material.AIR){
				check=false;
			}
		}
		return check;
	}

}
