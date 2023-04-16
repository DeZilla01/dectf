package net.dezilla.ctf.Kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Tester extends BaseKit{
	boolean godmode = false;

	public Tester(Player player, int variation) {
		super(player, variation);
	}
	
	@Override
	public void newInventory() {
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setItem(0, BaseKit.setItemName(new ItemStack(Material.BRICK), "God Mode"));
		setHunger();
		resExp();
		potEffect();
	}
	
	@Override
	public void onItemClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getInventory().getItemInMainHand().getType() == Material.BRICK) {
				if(!godmode) {
					p.sendMessage("God Mode Activated");
					godmode = true;
				}
				else {
					p.sendMessage("God Mode Deactivated");
					godmode = false;
				}
			}
		}
	}
	
	@Override
	public void update5() {
		if(godmode && p.getHealth() < 20) {
			p.setHealth(20.0);
		}
	}
	public String getKitName() {
		return("Tester");
	}

}
