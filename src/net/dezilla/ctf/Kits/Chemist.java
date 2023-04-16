package net.dezilla.ctf.Kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTItem;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;


public class Chemist extends BaseKit{

	public Chemist(Player player, int variation) {
		super(player, variation);
	}
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//pot harm
		NBTItem harm = new NBTItem(new ItemStack(Material.SPLASH_POTION, 20));
		harm.setString("Potion", "minecraft:strong_harming");
		//pot poison
		NBTItem poison = new NBTItem(new ItemStack(Material.SPLASH_POTION, 15));
		poison.setInteger("CustomPotionColor", 0x277C10);
		poison.addCompound("display");
		NBTCompound poisonC = poison.getCompound("display");
		poisonC.setString("Name", ChatColor.RESET+"Splash Potion of Poison");
		//pot slowness
		NBTItem slow = new NBTItem(new ItemStack(Material.SPLASH_POTION, 10));
		slow.setString("Potion", "minecraft:slowness");
		//pot weakness
		NBTItem weak = new NBTItem(new ItemStack(Material.LINGERING_POTION, 5));
		weak.setString("Potion", "minecraft:weakness");
		//pot blindness
		NBTItem blind = new NBTItem(new ItemStack(Material.LINGERING_POTION, 3));
		blind.setInteger("CustomPotionColor", 0x000000);
		blind.addCompound("display");
		NBTCompound blindC = blind.getCompound("display");
		blindC.setString("Name", ChatColor.RESET+"Lingering Potion of Blindness");
		//pot health
		NBTItem heal = new NBTItem(new ItemStack(Material.SPLASH_POTION, 5));
		heal.setInteger("CustomPotionColor", 0xff7ad7);
		NBTCompound healC = heal.addCompound("display");
		healC.setString("Name", ChatColor.RESET+"Splash Potion of Health");
		ItemStack healIS = applyEffect(heal.getItem(), 3, 1, 6);
		//str
		NBTItem str = new NBTItem(new ItemStack(Material.SPLASH_POTION, 3));
		str.setInteger("CustomPotionColor", 0xff3f00);
		NBTCompound strC = str.addCompound("display");
		strC.setString("Name", ChatColor.RESET+"Splash Potion of Strength & Speed");
		ItemStack strIS = strAndSpeed(str.getItem());
		//apply
		inv.setItem(0, new ItemStack(Material.IRON_SWORD));
		inv.setItem(1, harm.getItem());
		inv.setItem(2, applyEffect(poison.getItem(), 1, 10, 19));
		inv.setItem(3, slow.getItem());
		inv.setItem(4, weak.getItem());
		inv.setItem(5, applyEffect(blind.getItem(), 1, 10, 15));
		inv.setItem(6, strIS);
		inv.setItem(7, healIS);
		inv.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		inv.setBoots(setAtkSpeed(new ItemStack(Material.LEATHER_BOOTS)));
		inv.setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
		inv.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
		setHunger();
		resExp();
		potEffect();
	}
	private ItemStack applyEffect(ItemStack pot, int ampli, int duration, int effectid){
		NBTTagList list = new NBTTagList();
		NBTTagCompound comp = new NBTTagCompound();
		comp.setInt("Amplifier", ampli-1);
		comp.setInt("Duration", 20*duration);
		comp.setInt("Id", effectid);
		list.add(comp);
		net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(pot);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		itemCompound.set("CustomPotionEffects", list);
		nmsItem.setTag(itemCompound);
		ItemStack result = CraftItemStack.asBukkitCopy(nmsItem);
		return result;
	}
	private ItemStack strAndSpeed(ItemStack pot){
		NBTTagList list = new NBTTagList();
		NBTTagCompound comp1 = new NBTTagCompound();
		comp1.setInt("Amplifier", 0);
		comp1.setInt("Duration", 1200);
		comp1.setInt("Id", 5);
		list.add(comp1);
		NBTTagCompound comp2 = new NBTTagCompound();
		comp2.setInt("Amplifier", 0);
		comp2.setInt("Duration", 1200);
		comp2.setInt("Id", 1);
		list.add(comp2);
		net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(pot);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		itemCompound.set("CustomPotionEffects", list);
		nmsItem.setTag(itemCompound);
		ItemStack result = CraftItemStack.asBukkitCopy(nmsItem);
		return result;
	}
	@Override
	public String getKitName() {
		return("Chemist");
	}

}
