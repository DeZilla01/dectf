package net.dezilla.ctf.Structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.dezilla.ctf.IDTools;
import net.dezilla.ctf.Main;

public class Exit extends BaseStructure{
	public Location l;

	@SuppressWarnings("deprecation")
	public Exit(Player p, Block loc) {
		super(p, loc);
		byte[] woolColorList = {15, 11, 13, 9, 14, 10, 1, 8, 7, 11, 5, 3, 14, 2, 4, 0};
		//plate
		previousStuff.add(Material.AIR);
		loc.setType(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
		strucBlocks.add(loc);
		//wool
		Block woolblok = loc.getRelative(BlockFace.DOWN);
		previousStuff.add(woolblok.getType());
		woolblok.setType(Material.LEGACY_WOOL);
		IDTools idt = new IDTools();
		Material m = idt.getMaterial(35, (int) woolColorList[Main.mConfig.M_teamColInt.get(team)]);
		woolblok.setType(m);
		strucBlocks.add(woolblok);
		//location of tp
		l = loc.getLocation();
		l.add(new Vector(0.5,0.0,0.5));
	}

}
