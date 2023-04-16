package net.dezilla.ctf.Menu;

import org.bukkit.entity.Player;

import net.dezilla.ctf.Main;
import net.dezilla.ctf.Kits.Archer;

public class ArcherArrowMenu extends BaseMenu{
	Archer kit;

	public ArcherArrowMenu(Player player) {
		super(player);
		kit = (Archer) Main.grabKit(p);
	}

}
