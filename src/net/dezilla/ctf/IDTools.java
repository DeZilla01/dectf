package net.dezilla.ctf;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

/*
 * @Author StevenLPHD
 * @Version 1.0
 * 
 * @Description: A IDList to convert IDs + SubIDs direct to BlockData
 * 
 * @INFO: Items aren't included!!!
 * 
 */

public class IDTools {
	
	// The List for the IDs and SubIDs to get the Material for each ID and subID
	private Map<Double, Material> IdList = new HashMap<>();
	
	// Intialization for the IDList
	public IDTools() {
		
		// For these guys who Intialize the Class more than one
		IdList.clear();
		
		// Just added all the Materials with a ID
		// If you want, you can edit the IDs and subIDs
		IdList.put(0.0, Material.AIR);
		IdList.put(0.1, Material.CAVE_AIR);
		IdList.put(1.0, Material.STONE);
		IdList.put(1.1, Material.GRANITE);
		IdList.put(1.2, Material.POLISHED_GRANITE);
		IdList.put(1.3, Material.DIORITE);
		IdList.put(1.4, Material.POLISHED_DIORITE);
		IdList.put(1.5, Material.ANDESITE);
		IdList.put(1.6, Material.POLISHED_ANDESITE);
		IdList.put(2.0, Material.GRASS_BLOCK);
		IdList.put(3.0, Material.DIRT);
		IdList.put(3.1, Material.COARSE_DIRT);
		IdList.put(3.2, Material.PODZOL);
		IdList.put(4.0, Material.COBBLESTONE);
		IdList.put(5.0, Material.OAK_PLANKS);
		IdList.put(5.1, Material.SPRUCE_PLANKS);
		IdList.put(5.2, Material.BIRCH_PLANKS);
		IdList.put(5.3, Material.JUNGLE_PLANKS);
		IdList.put(5.4, Material.ACACIA_PLANKS);
		IdList.put(5.5, Material.DARK_OAK_PLANKS);
		IdList.put(6.0, Material.OAK_SAPLING);
		IdList.put(6.1, Material.SPRUCE_SAPLING);
		IdList.put(6.2, Material.BIRCH_SAPLING);
		IdList.put(6.3, Material.JUNGLE_SAPLING);
		IdList.put(6.4, Material.ACACIA_SAPLING);
		IdList.put(6.5, Material.DARK_OAK_SAPLING);
		IdList.put(7.0, Material.BEDROCK);
		IdList.put(8.0, Material.WATER);
		IdList.put(9.0, Material.WATER);
		IdList.put(10.0, Material.LAVA);
		IdList.put(11.0, Material.LAVA);
		IdList.put(12.0, Material.SAND);
		IdList.put(12.1, Material.RED_SAND);
		IdList.put(13.0, Material.GRAVEL);
		IdList.put(14.0, Material.GOLD_ORE);
		IdList.put(15.0, Material.IRON_ORE);
		IdList.put(16.0, Material.COAL_ORE);
		IdList.put(17.0, Material.OAK_LOG);
		IdList.put(17.1, Material.SPRUCE_LOG);
		IdList.put(17.2, Material.BIRCH_LOG);
		IdList.put(17.3, Material.JUNGLE_LOG);
		IdList.put(17.4, Material.ACACIA_LOG);
		IdList.put(17.5, Material.DARK_OAK_LOG);
		IdList.put(18.0, Material.OAK_LEAVES);
		IdList.put(18.1, Material.SPRUCE_LEAVES);
		IdList.put(18.2, Material.BIRCH_LEAVES);
		IdList.put(18.3, Material.JUNGLE_LEAVES);
		IdList.put(18.4, Material.ACACIA_LEAVES);
		IdList.put(18.5, Material.DARK_OAK_LEAVES);
		IdList.put(19.0, Material.SPONGE);
		IdList.put(19.1, Material.WET_SPONGE);
		IdList.put(20.0, Material.GLASS);
		IdList.put(21.0, Material.LAPIS_ORE);
		IdList.put(22.0, Material.LAPIS_BLOCK);
		IdList.put(23.0, Material.DISPENSER);
		IdList.put(24.0, Material.SANDSTONE);
		IdList.put(24.1, Material.CHISELED_SANDSTONE);
		IdList.put(24.2, Material.SMOOTH_SANDSTONE);
		IdList.put(25.0, Material.NOTE_BLOCK);
		IdList.put(26.0, Material.WHITE_BED);
		IdList.put(26.1, Material.ORANGE_BED);
		IdList.put(26.2, Material.MAGENTA_BED);
		IdList.put(26.3, Material.LIGHT_BLUE_BED);
		IdList.put(26.4, Material.YELLOW_BED);
		IdList.put(26.5, Material.LIME_BED);
		IdList.put(26.6, Material.PINK_BED);
		IdList.put(26.7, Material.GRAY_BED);
		IdList.put(26.8, Material.LIGHT_GRAY_BED);
		IdList.put(26.9, Material.CYAN_BED);
		IdList.put(26.10, Material.PURPLE_BED);
		IdList.put(26.11, Material.BLUE_BED);
		IdList.put(26.12, Material.BROWN_BED);
		IdList.put(26.13, Material.GREEN_BED);
		IdList.put(26.14, Material.RED_BED);
		IdList.put(26.15, Material.BLACK_BED);
		IdList.put(27.0, Material.POWERED_RAIL);
		IdList.put(28.0, Material.DETECTOR_RAIL);
		IdList.put(29.0, Material.STICKY_PISTON);
		IdList.put(30.0, Material.COBWEB);
		IdList.put(31.0, Material.GRASS);
		IdList.put(31.1, Material.FERN);
		IdList.put(32.0, Material.DEAD_BUSH);
		IdList.put(33.0, Material.PISTON);
		IdList.put(34.0, Material.PISTON_HEAD);
		IdList.put(35.0, Material.WHITE_WOOL);
		IdList.put(35.1, Material.ORANGE_WOOL);
		IdList.put(35.2, Material.MAGENTA_WOOL);
		IdList.put(35.3, Material.LIGHT_BLUE_WOOL);
		IdList.put(35.4, Material.YELLOW_WOOL);
		IdList.put(35.5, Material.LIME_WOOL);
		IdList.put(35.6, Material.PINK_WOOL);
		IdList.put(26.7, Material.GRAY_WOOL);
		IdList.put(35.8, Material.LIGHT_GRAY_WOOL);
		IdList.put(35.9, Material.CYAN_WOOL);
		IdList.put(35.10, Material.PURPLE_WOOL);
		IdList.put(35.11, Material.BLUE_WOOL);
		IdList.put(35.12, Material.BROWN_WOOL);
		IdList.put(35.13, Material.GREEN_WOOL);
		IdList.put(35.14, Material.RED_WOOL);
		IdList.put(35.15, Material.BLACK_WOOL);
		IdList.put(36.0, Material.POPPY);
		IdList.put(36.1, Material.DANDELION);
		IdList.put(36.2, Material.BLUE_ORCHID);
		IdList.put(36.3, Material.ALLIUM);
		IdList.put(36.4, Material.AZURE_BLUET);
		IdList.put(36.5, Material.RED_TULIP);
		IdList.put(36.6, Material.ORANGE_TULIP);
		IdList.put(36.7, Material.WHITE_TULIP);
		IdList.put(36.8, Material.PINK_TULIP);
		IdList.put(36.9, Material.OXEYE_DAISY);
		IdList.put(37.0, Material.BROWN_MUSHROOM);
		IdList.put(37.1, Material.RED_MUSHROOM);
		IdList.put(38.0, Material.GOLD_BLOCK);
		IdList.put(39.0, Material.IRON_BLOCK);
		IdList.put(40.0, Material.STONE_SLAB);
		IdList.put(40.1, Material.SANDSTONE_SLAB);
		IdList.put(40.2, Material.COBBLESTONE_SLAB);
		IdList.put(40.3, Material.BRICK_SLAB);
		IdList.put(40.4, Material.STONE_BRICK_SLAB);
		IdList.put(40.5, Material.NETHER_BRICK_SLAB);
		IdList.put(40.6, Material.QUARTZ_SLAB);
		IdList.put(40.7, Material.RED_SANDSTONE_SLAB);
		IdList.put(40.8, Material.PURPUR_SLAB);
		IdList.put(40.9, Material.PRISMARINE_SLAB);
		IdList.put(40.10, Material.PRISMARINE_BRICK_SLAB);
		IdList.put(40.11, Material.DARK_PRISMARINE_SLAB);
		IdList.put(41.0, Material.OAK_SLAB);
		IdList.put(41.1, Material.SPRUCE_SLAB);
		IdList.put(41.2, Material.BIRCH_SLAB);
		IdList.put(41.3, Material.JUNGLE_SLAB);
		IdList.put(41.4, Material.ACACIA_SLAB);
		IdList.put(41.5, Material.DARK_OAK_SLAB);
		IdList.put(42.0, Material.BRICK);
		IdList.put(43.0, Material.TNT);
		IdList.put(44.0, Material.BOOKSHELF);
		IdList.put(45.0, Material.MOSSY_COBBLESTONE);
		IdList.put(46.0, Material.OBSIDIAN);
		IdList.put(47.0, Material.TORCH);
		IdList.put(47.1, Material.WALL_TORCH);
		IdList.put(48.0, Material.FIRE);
		IdList.put(49.0, Material.SPAWNER);
		IdList.put(50.0, Material.OAK_STAIRS);
		IdList.put(50.1, Material.SPRUCE_STAIRS);
		IdList.put(50.2, Material.BIRCH_STAIRS);
		IdList.put(50.3, Material.JUNGLE_STAIRS);
		IdList.put(50.4, Material.ACACIA_STAIRS);
		IdList.put(50.5, Material.DARK_OAK_STAIRS);
		IdList.put(51.0, Material.CHEST);
		IdList.put(52.0, Material.REDSTONE_WIRE);
		IdList.put(53.0, Material.DIAMOND_ORE);
		IdList.put(54.0, Material.DIAMOND_BLOCK);
		IdList.put(55.0, Material.CRAFTING_TABLE);
		IdList.put(56.0, Material.WHEAT);
		IdList.put(56.1, Material.WHEAT_SEEDS);
		IdList.put(57.0, Material.FARMLAND);
		IdList.put(58.0, Material.FURNACE);
		IdList.put(59.0, Material.OAK_SIGN);
		IdList.put(59.1, Material.OAK_WALL_SIGN);
		IdList.put(60.0, Material.OAK_DOOR);
		IdList.put(60.1, Material.SPRUCE_DOOR);
		IdList.put(60.2, Material.BIRCH_DOOR);
		IdList.put(60.3, Material.JUNGLE_DOOR);
		IdList.put(60.4, Material.ACACIA_DOOR);
		IdList.put(60.5, Material.DARK_OAK_DOOR);
		IdList.put(61.0, Material.LADDER);
		IdList.put(62.0, Material.RAIL);
		IdList.put(63.0, Material.SANDSTONE_STAIRS);
		IdList.put(63.1, Material.COBBLESTONE_STAIRS);
		IdList.put(63.2, Material.BRICK_STAIRS);
		IdList.put(63.3, Material.STONE_BRICK_STAIRS);
		IdList.put(63.4, Material.NETHER_BRICK_STAIRS);
		IdList.put(63.5, Material.QUARTZ_STAIRS);
		IdList.put(63.6, Material.RED_SANDSTONE_STAIRS);
		IdList.put(63.7, Material.PURPUR_STAIRS);
		IdList.put(63.8, Material.PRISMARINE_STAIRS);
		IdList.put(63.9, Material.PRISMARINE_BRICK_STAIRS);
		IdList.put(63.10, Material.DARK_PRISMARINE_STAIRS);
		IdList.put(64.0, Material.LEVER);
		IdList.put(65.0, Material.STONE_PRESSURE_PLATE);
		IdList.put(66.0, Material.IRON_DOOR);
		IdList.put(67.0, Material.OAK_PRESSURE_PLATE);
		IdList.put(67.1, Material.SPRUCE_PRESSURE_PLATE);
		IdList.put(67.2, Material.BIRCH_PRESSURE_PLATE);
		IdList.put(67.3, Material.JUNGLE_PRESSURE_PLATE);
		IdList.put(67.4, Material.ACACIA_PRESSURE_PLATE);
		IdList.put(67.5, Material.DARK_OAK_PRESSURE_PLATE);
		IdList.put(68.0, Material.REDSTONE_ORE);
		IdList.put(69.0, Material.REDSTONE_TORCH);
		IdList.put(69.1, Material.REDSTONE_WALL_TORCH);
		IdList.put(70.0, Material.STONE_BUTTON);
		IdList.put(71.0, Material.SNOW);
		IdList.put(72.0, Material.ICE);
		IdList.put(73.0, Material.SNOW_BLOCK);
		IdList.put(74.0, Material.CACTUS);
		IdList.put(75.0, Material.CLAY);
		IdList.put(76.0, Material.SUGAR_CANE);
		IdList.put(77.0, Material.JUKEBOX);
		IdList.put(78.0, Material.OAK_FENCE);
		IdList.put(78.1, Material.SPRUCE_FENCE);
		IdList.put(78.2, Material.BIRCH_FENCE);
		IdList.put(78.3, Material.JUNGLE_FENCE);
		IdList.put(78.4, Material.ACACIA_FENCE);
		IdList.put(78.5, Material.DARK_OAK_FENCE);
		IdList.put(79.0, Material.PUMPKIN);
		IdList.put(80.0, Material.NETHERRACK);
		IdList.put(81.0, Material.SOUL_SAND);
		IdList.put(82.0, Material.GLOWSTONE);
		IdList.put(83.0, Material.NETHER_PORTAL);
		IdList.put(84.0, Material.CARVED_PUMPKIN);
		IdList.put(85.0, Material.CAKE);
		IdList.put(86.0, Material.REPEATER);
		IdList.put(87.0, Material.WHITE_STAINED_GLASS);
		IdList.put(87.1, Material.ORANGE_STAINED_GLASS);
		IdList.put(87.2, Material.MAGENTA_STAINED_GLASS);
		IdList.put(87.3, Material.LIGHT_BLUE_STAINED_GLASS);
		IdList.put(87.4, Material.YELLOW_STAINED_GLASS);
		IdList.put(87.5, Material.LIME_STAINED_GLASS);
		IdList.put(87.6, Material.PINK_STAINED_GLASS);
		IdList.put(87.7, Material.GRAY_STAINED_GLASS);
		IdList.put(87.8, Material.LIGHT_GRAY_STAINED_GLASS);
		IdList.put(87.9, Material.CYAN_STAINED_GLASS);
		IdList.put(87.10, Material.PURPLE_STAINED_GLASS);
		IdList.put(87.11, Material.BLUE_STAINED_GLASS);
		IdList.put(87.12, Material.BROWN_STAINED_GLASS);
		IdList.put(87.13, Material.GREEN_STAINED_GLASS);
		IdList.put(87.14, Material.RED_STAINED_GLASS);
		IdList.put(87.15, Material.BLACK_STAINED_GLASS);
		IdList.put(88.0, Material.OAK_TRAPDOOR);
		IdList.put(88.1, Material.SPRUCE_TRAPDOOR);
		IdList.put(88.2, Material.BIRCH_TRAPDOOR);
		IdList.put(88.3, Material.JUNGLE_TRAPDOOR);
		IdList.put(88.4, Material.ACACIA_TRAPDOOR);
		IdList.put(88.5, Material.DARK_OAK_TRAPDOOR);
		IdList.put(89.0, Material.STONE_BRICKS);
		IdList.put(89.1, Material.MOSSY_STONE_BRICKS);
		IdList.put(89.2, Material.CRACKED_STONE_BRICKS);
		IdList.put(89.3, Material.CHISELED_STONE_BRICKS);
		IdList.put(90.0, Material.MUSHROOM_STEM);
		IdList.put(90.1, Material.BROWN_MUSHROOM_BLOCK);
		IdList.put(90.2, Material.RED_MUSHROOM_BLOCK);
		IdList.put(91.0, Material.IRON_BARS);
		IdList.put(92.0, Material.GLASS_PANE);
		IdList.put(93.0, Material.MELON);
		IdList.put(94.0, Material.PUMPKIN_STEM);
		IdList.put(95.0, Material.MELON_STEM);
		IdList.put(96.0, Material.VINE);
		IdList.put(97.0, Material.OAK_FENCE_GATE);
		IdList.put(97.1, Material.SPRUCE_FENCE_GATE);
		IdList.put(97.2, Material.BIRCH_FENCE_GATE);
		IdList.put(97.3, Material.JUNGLE_FENCE_GATE);
		IdList.put(97.4, Material.ACACIA_FENCE_GATE);
		IdList.put(97.5, Material.DARK_OAK_FENCE_GATE);
		IdList.put(98.0, Material.MYCELIUM);
		IdList.put(99.0, Material.LILY_PAD);
		IdList.put(100.0, Material.NETHER_BRICK);
		IdList.put(101.0, Material.NETHER_BRICK_FENCE);
		IdList.put(102.0, Material.NETHER_WART);
		IdList.put(103.0, Material.ENCHANTING_TABLE);
		IdList.put(104.0, Material.BREWING_STAND);
		IdList.put(105.0, Material.CAULDRON);
		IdList.put(106.0, Material.END_PORTAL);
		IdList.put(107.0, Material.END_PORTAL_FRAME);
		IdList.put(108.0, Material.END_STONE);
		IdList.put(109.0, Material.DRAGON_EGG);
		IdList.put(110.0, Material.REDSTONE_LAMP);
		IdList.put(111.0, Material.EMERALD_ORE);
		IdList.put(112.0, Material.ENDER_CHEST);
		IdList.put(113.0, Material.TRIPWIRE_HOOK);
		IdList.put(114.0, Material.TRIPWIRE);
		IdList.put(115.0, Material.EMERALD_BLOCK);
		IdList.put(116.0, Material.COMMAND_BLOCK);
		IdList.put(116.1, Material.REPEATING_COMMAND_BLOCK);
		IdList.put(116.2, Material.CHAIN_COMMAND_BLOCK);
		IdList.put(117.0, Material.BEACON);
		IdList.put(118.0, Material.COBBLESTONE_WALL);
		IdList.put(118.1, Material.MOSSY_COBBLESTONE_WALL);
		IdList.put(119.0, Material.FLOWER_POT);
		IdList.put(120.0, Material.CARROTS);
		IdList.put(121.0, Material.POTATOES);
		IdList.put(122.0, Material.OAK_BUTTON);
		IdList.put(122.1, Material.SPRUCE_BUTTON);
		IdList.put(122.2, Material.BIRCH_BUTTON);
		IdList.put(122.3, Material.JUNGLE_BUTTON);
		IdList.put(122.4, Material.ACACIA_BUTTON);
		IdList.put(122.5, Material.DARK_OAK_BUTTON);
		IdList.put(123.0, Material.SKELETON_SKULL);
		IdList.put(123.1, Material.SKELETON_WALL_SKULL);
		IdList.put(124.0, Material.WITHER_SKELETON_SKULL);
		IdList.put(124.1, Material.WITHER_SKELETON_WALL_SKULL);
		IdList.put(125.0, Material.ANVIL);
		IdList.put(125.1, Material.DAMAGED_ANVIL);
		IdList.put(125.2, Material.CHIPPED_ANVIL);
		IdList.put(126.0, Material.TRAPPED_CHEST);
		IdList.put(127.0, Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
		IdList.put(128.0, Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
		IdList.put(129.0, Material.COMPARATOR);
		IdList.put(130.0, Material.DAYLIGHT_DETECTOR);
		IdList.put(131.0, Material.REDSTONE_BLOCK);
		IdList.put(132.0, Material.NETHER_QUARTZ_ORE);
		IdList.put(133.0, Material.HOPPER);
		IdList.put(134.0, Material.QUARTZ_BLOCK);
		IdList.put(134.1, Material.CHISELED_QUARTZ_BLOCK);
		IdList.put(134.2, Material.QUARTZ_PILLAR);
		IdList.put(135.0, Material.ACTIVATOR_RAIL);
		IdList.put(136.0, Material.DROPPER);
		IdList.put(137.0, Material.WHITE_TERRACOTTA);
		IdList.put(137.1, Material.ORANGE_TERRACOTTA);
		IdList.put(137.2, Material.MAGENTA_TERRACOTTA);
		IdList.put(137.3, Material.LIGHT_BLUE_TERRACOTTA);
		IdList.put(137.4, Material.YELLOW_TERRACOTTA);
		IdList.put(137.5, Material.LIME_TERRACOTTA);
		IdList.put(137.6, Material.PINK_TERRACOTTA);
		IdList.put(137.7, Material.GRAY_TERRACOTTA);
		IdList.put(137.8, Material.LIGHT_GRAY_TERRACOTTA);
		IdList.put(137.9, Material.CYAN_TERRACOTTA);
		IdList.put(137.10, Material.PURPLE_TERRACOTTA);
		IdList.put(137.11, Material.BLUE_TERRACOTTA);
		IdList.put(137.12, Material.BROWN_TERRACOTTA);
		IdList.put(137.13, Material.GREEN_TERRACOTTA);
		IdList.put(137.14, Material.RED_TERRACOTTA);
		IdList.put(137.15, Material.BLACK_TERRACOTTA);
		IdList.put(138.0, Material.WHITE_GLAZED_TERRACOTTA);
		IdList.put(138.1, Material.ORANGE_GLAZED_TERRACOTTA);
		IdList.put(138.2, Material.MAGENTA_GLAZED_TERRACOTTA);
		IdList.put(138.3, Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
		IdList.put(138.4, Material.YELLOW_GLAZED_TERRACOTTA);
		IdList.put(138.5, Material.LIME_GLAZED_TERRACOTTA);
		IdList.put(138.6, Material.PINK_GLAZED_TERRACOTTA);
		IdList.put(138.7, Material.GRAY_GLAZED_TERRACOTTA);
		IdList.put(138.8, Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
		IdList.put(138.9, Material.CYAN_GLAZED_TERRACOTTA);
		IdList.put(138.10, Material.PURPLE_GLAZED_TERRACOTTA);
		IdList.put(138.11, Material.BLUE_GLAZED_TERRACOTTA);
		IdList.put(138.12, Material.BROWN_GLAZED_TERRACOTTA);
		IdList.put(138.13, Material.GREEN_GLAZED_TERRACOTTA);
		IdList.put(138.14, Material.RED_GLAZED_TERRACOTTA);
		IdList.put(138.15, Material.BLACK_GLAZED_TERRACOTTA);
		IdList.put(139.0, Material.WHITE_STAINED_GLASS_PANE);
		IdList.put(139.1, Material.ORANGE_STAINED_GLASS_PANE);
		IdList.put(139.2, Material.MAGENTA_STAINED_GLASS_PANE);
		IdList.put(139.3, Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		IdList.put(139.4, Material.YELLOW_STAINED_GLASS_PANE);
		IdList.put(139.5, Material.LIME_STAINED_GLASS_PANE);
		IdList.put(139.6, Material.PINK_STAINED_GLASS_PANE);
		IdList.put(139.7, Material.GRAY_STAINED_GLASS_PANE);
		IdList.put(139.8, Material.LIGHT_GRAY_STAINED_GLASS_PANE);
		IdList.put(139.9, Material.CYAN_STAINED_GLASS_PANE);
		IdList.put(139.10, Material.PURPLE_STAINED_GLASS_PANE);
		IdList.put(139.11, Material.BLUE_STAINED_GLASS_PANE);
		IdList.put(139.12, Material.BROWN_STAINED_GLASS_PANE);
		IdList.put(139.13, Material.GREEN_STAINED_GLASS_PANE);
		IdList.put(139.14, Material.RED_STAINED_GLASS_PANE);
		IdList.put(139.15, Material.BLACK_STAINED_GLASS_PANE);
		IdList.put(140.0, Material.SLIME_BLOCK);
		IdList.put(141.0, Material.BARRIER);
		IdList.put(142.0, Material.IRON_TRAPDOOR);
		IdList.put(143.0, Material.PRISMARINE);
		IdList.put(143.1, Material.PRISMARINE_BRICKS);
		IdList.put(143.2, Material.DARK_PRISMARINE);
		IdList.put(144.0, Material.SEA_LANTERN);
		IdList.put(145.0, Material.HAY_BLOCK);
		IdList.put(146.0, Material.WHITE_CARPET);
		IdList.put(146.1, Material.ORANGE_CARPET);
		IdList.put(146.2, Material.MAGENTA_CARPET);
		IdList.put(146.3, Material.LIGHT_BLUE_CARPET);
		IdList.put(146.4, Material.YELLOW_CARPET);
		IdList.put(146.5, Material.LIME_CARPET);
		IdList.put(146.6, Material.PINK_CARPET);
		IdList.put(146.7, Material.GRAY_CARPET);
		IdList.put(146.8, Material.LIGHT_GRAY_CARPET);
		IdList.put(146.9, Material.CYAN_CARPET);
		IdList.put(146.10, Material.PURPLE_CARPET);
		IdList.put(146.11, Material.BLUE_CARPET);
		IdList.put(146.12, Material.BROWN_CARPET);
		IdList.put(146.13, Material.GREEN_CARPET);
		IdList.put(146.14, Material.RED_CARPET);
		IdList.put(146.15, Material.BLACK_CARPET);
		IdList.put(147.0, Material.TERRACOTTA);
		IdList.put(148.0, Material.COAL_BLOCK);
		IdList.put(149.0, Material.PACKED_ICE);
		IdList.put(150.0, Material.SUNFLOWER);
		IdList.put(150.1, Material.LILAC);
		IdList.put(150.2, Material.TALL_GRASS);
		IdList.put(150.3, Material.LARGE_FERN);
		IdList.put(150.4, Material.ROSE_BUSH);
		IdList.put(150.5, Material.PEONY);
		IdList.put(151.0, Material.WHITE_BANNER);
		IdList.put(151.1, Material.ORANGE_BANNER);
		IdList.put(151.2, Material.MAGENTA_BANNER);
		IdList.put(151.3, Material.LIGHT_BLUE_BANNER);
		IdList.put(151.4, Material.YELLOW_BANNER);
		IdList.put(151.5, Material.LIME_BANNER);
		IdList.put(151.6, Material.PINK_BANNER);
		IdList.put(151.7, Material.GRAY_BANNER);
		IdList.put(151.8, Material.LIGHT_GRAY_BANNER);
		IdList.put(151.9, Material.CYAN_BANNER);
		IdList.put(151.10, Material.PURPLE_BANNER);
		IdList.put(151.11, Material.BLUE_BANNER);
		IdList.put(151.12, Material.BROWN_BANNER);
		IdList.put(151.13, Material.GREEN_BANNER);
		IdList.put(151.14, Material.RED_BANNER);
		IdList.put(151.15, Material.BLACK_BANNER);
		IdList.put(152.0, Material.WHITE_WALL_BANNER);
		IdList.put(152.1, Material.ORANGE_WALL_BANNER);
		IdList.put(152.2, Material.MAGENTA_WALL_BANNER);
		IdList.put(152.3, Material.LIGHT_BLUE_WALL_BANNER);
		IdList.put(152.4, Material.YELLOW_WALL_BANNER);
		IdList.put(152.5, Material.LIME_WALL_BANNER);
		IdList.put(152.6, Material.PINK_WALL_BANNER);
		IdList.put(152.7, Material.GRAY_WALL_BANNER);
		IdList.put(152.8, Material.LIGHT_GRAY_WALL_BANNER);
		IdList.put(152.9, Material.CYAN_WALL_BANNER);
		IdList.put(152.10, Material.PURPLE_WALL_BANNER);
		IdList.put(152.11, Material.BLUE_WALL_BANNER);
		IdList.put(152.12, Material.BROWN_WALL_BANNER);
		IdList.put(152.13, Material.GREEN_WALL_BANNER);
		IdList.put(152.14, Material.RED_WALL_BANNER);
		IdList.put(152.15, Material.BLACK_WALL_BANNER);
		IdList.put(153.0, Material.RED_SANDSTONE);
		IdList.put(153.1, Material.CHISELED_RED_SANDSTONE);
		IdList.put(153.2, Material.SMOOTH_RED_SANDSTONE);
		IdList.put(154.0, Material.END_ROD);
		IdList.put(155.0, Material.CHORUS_PLANT);
		IdList.put(155.1, Material.CHORUS_FLOWER);
		IdList.put(156.0, Material.PURPUR_BLOCK);
		IdList.put(156.1, Material.PURPUR_PILLAR);
		IdList.put(157.0, Material.END_STONE_BRICKS);
		IdList.put(158.0, Material.GRASS_PATH);
		IdList.put(159.0, Material.END_GATEWAY);
		IdList.put(160.0, Material.FROSTED_ICE);
		IdList.put(161.0, Material.MAGMA_BLOCK);
		IdList.put(162.0, Material.NETHER_WART_BLOCK);
		IdList.put(163.0, Material.RED_NETHER_BRICKS);
		IdList.put(164.0, Material.BONE_BLOCK);
		IdList.put(165.0, Material.STRUCTURE_VOID);
		IdList.put(166.0, Material.OBSERVER);
		IdList.put(167.0, Material.WHITE_SHULKER_BOX);
		IdList.put(167.1, Material.ORANGE_SHULKER_BOX);
		IdList.put(167.2, Material.MAGENTA_SHULKER_BOX);
		IdList.put(167.3, Material.LIGHT_BLUE_SHULKER_BOX);
		IdList.put(167.4, Material.YELLOW_SHULKER_BOX);
		IdList.put(167.5, Material.LIME_SHULKER_BOX);
		IdList.put(167.6, Material.PINK_SHULKER_BOX);
		IdList.put(167.7, Material.GRAY_SHULKER_BOX);
		IdList.put(167.8, Material.LIGHT_GRAY_SHULKER_BOX);
		IdList.put(167.9, Material.CYAN_SHULKER_BOX);
		IdList.put(167.10, Material.PURPLE_SHULKER_BOX);
		IdList.put(167.11, Material.BLUE_SHULKER_BOX);
		IdList.put(167.12, Material.BROWN_SHULKER_BOX);
		IdList.put(167.13, Material.GREEN_SHULKER_BOX);
		IdList.put(167.14, Material.RED_SHULKER_BOX);
		IdList.put(167.15, Material.BLACK_SHULKER_BOX);
		IdList.put(168.0, Material.WHITE_CONCRETE);
		IdList.put(168.1, Material.ORANGE_CONCRETE);
		IdList.put(168.2, Material.MAGENTA_CONCRETE);
		IdList.put(168.3, Material.LIGHT_BLUE_CONCRETE);
		IdList.put(168.4, Material.YELLOW_CONCRETE);
		IdList.put(168.5, Material.LIME_CONCRETE);
		IdList.put(168.6, Material.PINK_CONCRETE);
		IdList.put(168.7, Material.GRAY_CONCRETE);
		IdList.put(168.8, Material.LIGHT_GRAY_CONCRETE);
		IdList.put(168.9, Material.CYAN_CONCRETE);
		IdList.put(168.10, Material.PURPLE_CONCRETE);
		IdList.put(168.11, Material.BLUE_CONCRETE);
		IdList.put(168.12, Material.BROWN_CONCRETE);
		IdList.put(168.13, Material.GREEN_CONCRETE);
		IdList.put(168.14, Material.RED_CONCRETE);
		IdList.put(168.15, Material.BLACK_CONCRETE);
		IdList.put(169.0, Material.WHITE_CONCRETE_POWDER);
		IdList.put(169.1, Material.ORANGE_CONCRETE_POWDER);
		IdList.put(169.2, Material.MAGENTA_CONCRETE_POWDER);
		IdList.put(169.3, Material.LIGHT_BLUE_CONCRETE_POWDER);
		IdList.put(169.4, Material.YELLOW_CONCRETE_POWDER);
		IdList.put(169.5, Material.LIME_CONCRETE_POWDER);
		IdList.put(169.6, Material.PINK_CONCRETE_POWDER);
		IdList.put(169.7, Material.GRAY_CONCRETE_POWDER);
		IdList.put(169.8, Material.LIGHT_GRAY_CONCRETE_POWDER);
		IdList.put(169.9, Material.CYAN_CONCRETE_POWDER);
		IdList.put(169.10, Material.PURPLE_CONCRETE_POWDER);
		IdList.put(169.11, Material.BLUE_CONCRETE_POWDER);
		IdList.put(169.12, Material.BROWN_CONCRETE_POWDER);
		IdList.put(169.13, Material.GREEN_CONCRETE_POWDER);
		IdList.put(169.14, Material.RED_CONCRETE_POWDER);
		IdList.put(169.15, Material.BLACK_CONCRETE_POWDER);
		IdList.put(170.0, Material.STRUCTURE_BLOCK);
		IdList.put(171.0, Material.INFESTED_STONE);
		IdList.put(172.0, Material.INFESTED_COBBLESTONE);
		IdList.put(173.0, Material.INFESTED_STONE_BRICKS);
		IdList.put(173.1, Material.INFESTED_MOSSY_STONE_BRICKS);
		IdList.put(173.2, Material.INFESTED_CRACKED_STONE_BRICKS);
		IdList.put(173.2, Material.INFESTED_CHISELED_STONE_BRICKS);
		IdList.put(174.0, Material.BLUE_ICE);
		IdList.put(175.0, Material.BUBBLE_COLUMN);
		IdList.put(176.0, Material.CONDUIT);
		IdList.put(177.0, Material.SEAGRASS);
		IdList.put(177.1, Material.TALL_SEAGRASS);
		IdList.put(178.0, Material.SEA_PICKLE);
		IdList.put(179.0, Material.KELP_PLANT);
		IdList.put(180.0, Material.DRIED_KELP_BLOCK);
		IdList.put(181.0, Material.SHULKER_BOX);
		IdList.put(182.0, Material.TUBE_CORAL);
		IdList.put(182.1, Material.BRAIN_CORAL);
		IdList.put(182.2, Material.BUBBLE_CORAL);
		IdList.put(182.3, Material.FIRE_CORAL);
		IdList.put(182.4, Material.HORN_CORAL);
		IdList.put(183.0, Material.TUBE_CORAL_FAN);
		IdList.put(183.1, Material.BRAIN_CORAL_FAN);
		IdList.put(183.2, Material.BUBBLE_CORAL_FAN);
		IdList.put(183.3, Material.FIRE_CORAL_FAN);
		IdList.put(183.4, Material.HORN_CORAL_FAN);
		IdList.put(184.0, Material.TUBE_CORAL_WALL_FAN);
		IdList.put(184.1, Material.BRAIN_CORAL_WALL_FAN);
		IdList.put(184.2, Material.BUBBLE_CORAL_WALL_FAN);
		IdList.put(184.3, Material.FIRE_CORAL_WALL_FAN);
		IdList.put(184.4, Material.HORN_CORAL_WALL_FAN);
		IdList.put(185.0, Material.DEAD_TUBE_CORAL_FAN);
		IdList.put(185.1, Material.DEAD_BRAIN_CORAL_FAN);
		IdList.put(185.2, Material.DEAD_BUBBLE_CORAL_FAN);
		IdList.put(185.3, Material.DEAD_FIRE_CORAL_FAN);
		IdList.put(185.4, Material.DEAD_HORN_CORAL_FAN);
		IdList.put(186.0, Material.DEAD_TUBE_CORAL_WALL_FAN);
		IdList.put(186.1, Material.DEAD_BRAIN_CORAL_WALL_FAN);
		IdList.put(186.2, Material.DEAD_BUBBLE_CORAL_WALL_FAN);
		IdList.put(186.3, Material.DEAD_FIRE_CORAL_WALL_FAN);
		IdList.put(186.4, Material.DEAD_HORN_CORAL_WALL_FAN);
		IdList.put(187.0, Material.TUBE_CORAL_BLOCK);
		IdList.put(187.1, Material.BRAIN_CORAL_BLOCK);
		IdList.put(187.2, Material.BUBBLE_CORAL_BLOCK);
		IdList.put(187.3, Material.FIRE_CORAL_BLOCK);
		IdList.put(187.4, Material.HORN_CORAL_BLOCK);
		IdList.put(188.0, Material.DEAD_TUBE_CORAL_BLOCK);
		IdList.put(188.1, Material.DEAD_BRAIN_CORAL_BLOCK);
		IdList.put(188.2, Material.DEAD_BUBBLE_CORAL_BLOCK);
		IdList.put(188.3, Material.DEAD_FIRE_CORAL_BLOCK);
		IdList.put(188.4, Material.DEAD_HORN_CORAL_BLOCK);
		IdList.put(189.0, Material.OAK_WOOD);
		IdList.put(189.1, Material.SPRUCE_WOOD);
		IdList.put(189.2, Material.BIRCH_WOOD);
		IdList.put(189.3, Material.JUNGLE_WOOD);
		IdList.put(189.4, Material.ACACIA_WOOD);
		IdList.put(189.5, Material.DARK_OAK_WOOD);
		IdList.put(190.0, Material.STRIPPED_OAK_LOG);
		IdList.put(190.1, Material.STRIPPED_SPRUCE_LOG);
		IdList.put(190.2, Material.STRIPPED_BIRCH_LOG);
		IdList.put(190.3, Material.STRIPPED_JUNGLE_LOG);
		IdList.put(190.4, Material.STRIPPED_ACACIA_LOG);
		IdList.put(190.5, Material.STRIPPED_DARK_OAK_LOG);
		IdList.put(191.0, Material.STRIPPED_OAK_WOOD);
		IdList.put(191.1, Material.STRIPPED_SPRUCE_WOOD);
		IdList.put(191.2, Material.STRIPPED_BIRCH_WOOD);
		IdList.put(191.3, Material.STRIPPED_JUNGLE_WOOD);
		IdList.put(191.4, Material.STRIPPED_ACACIA_WOOD);
		IdList.put(191.5, Material.STRIPPED_DARK_OAK_WOOD);
		IdList.put(192.0, Material.TURTLE_EGG);
		IdList.put(425.0, Material.WHITE_BANNER);
		IdList.put(425.1, Material.ORANGE_BANNER);
		IdList.put(425.2, Material.MAGENTA_BANNER);
		IdList.put(425.3, Material.LIGHT_BLUE_BANNER);
		IdList.put(425.4, Material.YELLOW_BANNER);
		IdList.put(425.5, Material.LIME_BANNER);
		IdList.put(425.6, Material.PINK_BANNER);
		IdList.put(425.7, Material.GRAY_BANNER);
		IdList.put(425.8, Material.LIGHT_GRAY_BANNER);
		IdList.put(425.9, Material.CYAN_BANNER);
		IdList.put(425.10, Material.PURPLE_BANNER);
		IdList.put(425.11, Material.BLUE_BANNER);
		IdList.put(425.12, Material.BROWN_BANNER);
		IdList.put(425.13, Material.GREEN_BANNER);
		IdList.put(425.14, Material.RED_BANNER);
		IdList.put(425.15, Material.BLACK_BANNER);
	
	}
	
	// Now we come to the BlockData Methods
	public BlockData convertToBlockData(Integer Id, Integer subId) {
		
		Material mat = getMaterial(Id, subId);
		
		if(mat != null) {
			
			return Bukkit.createBlockData(mat);
			
		}
		
		return null;
		
	}
	
	public BlockData convertToBlockData(double Id) {
		
		Material mat = getMaterial(Id);
		
		if(mat != null) {
			
			return Bukkit.createBlockData(mat);
			
		}
		
		return null;
		
	}
	
	public BlockData convertToBlockData(String idwithsub) {
		
		Material mat = getMaterial(idwithsub);
		
		if(mat != null) {
			
			return Bukkit.createBlockData(mat);
			
		}
		
		return null;
		
	}
	
	public BlockData convertToBlockData(String idwithsub, String splitChar) {
		
		Material mat = getMaterial(idwithsub, splitChar);
		
		if(mat != null) {
			
			return Bukkit.createBlockData(mat);
			
		}
		
		return null;
		
	}
	
	// Just if you want to get the Name of the Block Material
	public String getName(Integer Id, Integer subId) {
		
		Material mat = getMaterial(Id, subId);
		
		if(mat != null) {
			
			return mat.name();
			
		}
		
		return null;
		
	}
	
	public String getName(double Id) {
		
		Material mat = getMaterial(Id);
		
		if(mat != null) {
			
			return mat.name();
			
		}
		
		return null;
		
	}
	
	public String getName(String idwithsub) {
		
		Material mat = getMaterial(idwithsub);
		
		if(mat != null) {
			
			return mat.name();
			
		}
		
		return null;
		
	}
	
	public String getName(String idwithsub, String splitChar) {
		
		Material mat = getMaterial(idwithsub, splitChar);
		
		if(mat != null) {
			
			return mat.name();
			
		}
		
		return null;
		
	}
	
	// And the important getter for the Materials
	public Material getMaterial(Integer Id, Integer subId) {
		
		if(subId == null) {
			subId = 0;
		}
		
		if(Id == null) {
			Id = 0;
		}

		double id = Double.valueOf(Id + "." + 0);
		double gid = Double.valueOf(Id + "." + subId);
		
		if (IdList.containsKey(gid)) {
			
			return IdList.get(gid);
			
		} else {
			
			if(IdList.containsKey(id)) {

				return IdList.get(id);
				
			}
			
		}
		
		return null;
		
	}
	
	public Material getMaterial(double Id) {
		
		if (IdList.containsKey(Id)) {
			
			return IdList.get(Id);
			
		} 
		
		return null;
		
	}
	
	public Material getMaterial(String idwithsub) {
		
		String[] ids = idwithsub.split(":");
		
		if(ids.length == 2) {
			
			if(StringUtils.isNumeric(ids[0]) && StringUtils.isNumeric(ids[1])) {
				
				Integer Id = Integer.valueOf(ids[0]);
				Integer subId = Integer.valueOf(ids[1]);

				double id = Double.valueOf(Id + "." + 0);
				double gid = Double.valueOf(Id + "." + subId);
		
				if (IdList.containsKey(gid)) {
			
					return IdList.get(gid);
			
				} else {
					
					if(IdList.containsKey(id)) {
						
						return IdList.get(id);
						
					}
			
				}
		
			}
		
		}
		
		return null;
		
	}
	
	public Material getMaterial(String idwithsub, String splitChar) {
		
		String[] ids = idwithsub.split(splitChar);
		
		if(ids.length == 2) {
			
			if(StringUtils.isNumeric(ids[0]) && StringUtils.isNumeric(ids[1])) {
				
				Integer Id = Integer.valueOf(ids[0]);
				Integer subId = Integer.valueOf(ids[1]);

				double id = Double.valueOf(Id + "." + 0);
				double gid = Double.valueOf(Id + "." + subId);
		
				if (IdList.containsKey(gid)) {
			
					return IdList.get(gid);
			
				} else {
					
					if(IdList.containsKey(id)) {
						
						return IdList.get(id);
						
					}
			
				}
		
			}
		
		}
		
		return null;
		
	}
	
	public double getID(Material material) {
		
		if(IdList.containsValue(material)) {
			return idCheck(material);
		}
		
		return 0.0;
		
	}
	
	public double getID(String materialName) {
		
		Material mat = Material.getMaterial(materialName);
		
		if(IdList.containsValue(mat)) {
			return idCheck(mat);
		}
		
		return 0.0;
		
	}
	
	private double idCheck(Material mat) {
		
		Double[] db = IdList.keySet().toArray(new Double[0]);
		
		for(double d : db) {
			
			if(mat == IdList.get(d)) {
				
				return d;
				
			}
			
		}
		
		return 0.0;
		
	}

}