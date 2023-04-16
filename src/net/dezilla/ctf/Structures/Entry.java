package net.dezilla.ctf.Structures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import net.dezilla.ctf.IDTools;
import net.dezilla.ctf.Main;
import net.dezilla.ctf.Kits.Engineer;

public class Entry extends BaseStructure{
	Exit exiT;

	@SuppressWarnings("deprecation")
	public Entry(Player p, Block loc) {
		super(p, loc);
		byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};
		//plate
		previousStuff.add(Material.AIR);
		loc.setType(Material.STONE_PRESSURE_PLATE);
		strucBlocks.add(loc);
		//wool
		Block woolblok = loc.getRelative(BlockFace.DOWN);
		previousStuff.add(woolblok.getType());
		woolblok.setType(Material.LEGACY_WOOL);
		IDTools idt = new IDTools();
		Material m = idt.getMaterial(35, (int) woolColorList[Main.mConfig.M_teamColInt.get(team)]);
		woolblok.setType(m);
		strucBlocks.add(woolblok);
	}
	@Override
	public void onStep(Player p){
		checkExit();
		if(exiT != null){
			if(Main.mConfig.M_match.getPlayerTeam(p) == team){
				p.teleport(exiT.l);
			}
		}
	}
	private void checkExit(){
		Engineer kit = (Engineer) Main.grabKit(owner);
		if(kit.exit != null){
			exiT = (Exit) kit.exit;
		}
		else{
			exiT = null;
		}
	}

}
