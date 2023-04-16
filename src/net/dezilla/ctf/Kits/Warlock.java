package net.dezilla.ctf.Kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTItem;
import net.dezilla.ctf.Main;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;

public class Warlock extends BaseKit{
	float eyeReload = 1.0F;
	float coalReload = 1.0F;

	public Warlock(Player player, int variation) {
		super(player, variation);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void newInventory(){
		PlayerInventory inv = p.getInventory();
		inv.clear();
		//helmet
		ItemStack helm = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_HELMET), 0x000000);
		//legs
		ItemStack legs = BaseKit.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 0x000000);
		//boots
		NBTItem boots = new NBTItem(new ItemStack(Material.LEATHER_BOOTS));
		boots.addCompound("display");
		NBTCompound bootsC = boots.getCompound("display");
		bootsC.setInteger("color", 0x000000);
		//pot harm
		NBTItem harm = new NBTItem(new ItemStack(Material.LINGERING_POTION, 3));
		harm.setInteger("CustomPotionColor", 0x000000);
		NBTCompound harmC = harm.addCompound("display");
		harmC.setString("Name", ChatColor.RESET+"Menacing Brew");
		ItemStack harmIS = applyEffect(harm.getItem(), 1, 1, 7);
		//eye
		NBTItem eye = new NBTItem(new ItemStack(Material.ENDER_EYE));
		NBTCompound eyeC = eye.addCompound("display");
		eyeC.setString("Name", ChatColor.RESET+"Dominate Mind");
		//coal
		NBTItem coal = new NBTItem(new ItemStack(Material.COAL_BLOCK));
		NBTCompound coalC = coal.addCompound("display");
		coalC.setString("Name", ChatColor.RESET+"Dark Envelope");
		//apply
		inv.setItem(0, new ItemStack(Material.WOODEN_SWORD));
		inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 2));
		inv.setItem(2, harmIS);
		inv.setItem(3, eye.getItem());
		inv.setItem(4, coal.getItem());
		inv.setItem(5, new ItemStack(Material.LEGACY_MONSTER_EGG));
		inv.setHelmet(helm.getItem());
		inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		inv.setLeggings(legs.getItem());
		inv.setBoots(BaseKit.setAtkSpeed(boots.getItem()));
		//effects and shit
		setHunger();
		resExp();
		potEffect();
	}
	@SuppressWarnings("deprecation")
	@Override
	public void update5(){
		if(eyeReload < 1-.0125){
			eyeReload+=.0125;
		}
		else if(eyeReload != 1){
			eyeReload = 1.0F;
			for(ItemStack is : p.getInventory().getContents()){
				if(is != null){
					if(is.getType() == Material.ENDER_PEARL){
						is.setType(Material.ENDER_EYE);
					}
				}
			}
		}
		if(coalReload < 1-.0166){
			coalReload+=.0166;
		}
		else if(coalReload != 1){
			coalReload = 1.0F;
			for(ItemStack is : p.getInventory().getContents()){
				if(is != null){
					if(is.getType() == Material.IRON_BLOCK){
						is.setType(Material.COAL_BLOCK);
					}
				}
			}
		}
		if(p.getItemInHand().getType() == Material.ENDER_PEARL){
			p.setExp(eyeReload);
		}
		else if(p.getItemInHand().getType() == Material.IRON_BLOCK){
			p.setExp(coalReload);
		}
		else{
			p.setExp(0.0F);
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onItemClick(PlayerInteractEvent e){
		if(e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL){
			e.setCancelled(true);
		}
		if(e.getPlayer().getItemInHand().getType() == Material.ENDER_EYE && eyeReload == 1){
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
				Player target = BaseKit.getTargetPlayer(p, 12);
				if(target != null){
					if(Main.getTeamId(p) != Main.getTeamId(target)){
						target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
						target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1));
						target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 1));
						target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
						target.playSound(target.getLocation(), "entity.elder_guardian.curse", 1F, 1F);
						p.playSound(p.getLocation(), "entity.endermen.teleport", 1F, 1F);
						eyeReload = 0.0F;
						for(ItemStack is : p.getInventory().getContents()){
							if(is != null){
								if(is.getType() == Material.ENDER_EYE){
									is.setType(Material.ENDER_PEARL);
								}
							}
						}
						e.setCancelled(true);
					}
				}
			}
		}
		if(p.getItemInHand().getType() == Material.COAL_BLOCK && coalReload == 1){
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
				Player target = BaseKit.getTargetPlayer(p, 3);
				boolean forSelf = true;
				if(target!=null){
					if(Main.getTeamId(p) == Main.getTeamId(target)){
						target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0));
						p.playSound(p.getLocation(), "block.chorus_flower.grow", 1F, 1F);
						target.playSound(target.getLocation(), "block.chorus_flower.grow", 1F, 1F);
						forSelf = false;
					}
				}
				if(forSelf){
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0));
					p.playSound(p.getLocation(), "block.chorus_flower.grow", 1F, 1F);
				}
				coalReload = 0.0F;
				for(ItemStack is : p.getInventory().getContents()){
					if(is != null){
						if(is.getType() == Material.COAL_BLOCK){
							is.setType(Material.IRON_BLOCK);
						}
					}
				}
			}
		}
	}
	@Override
	public void onProjectileHitEvent(ProjectileHitEvent e){
		//if(e.getEntity() instanceof ){
		//	Linge
		//}
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
	@Override
	public String getKitName() {
		return("Warlock");
	}

}
