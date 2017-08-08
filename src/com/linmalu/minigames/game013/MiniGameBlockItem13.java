package com.linmalu.minigames.game013;

import org.bukkit.Material;

import com.linmalu.minigames.data.ItemData;

public enum MiniGameBlockItem13
{
	DIRT(Material.DIRT, ItemData.삽),
	CLAY(Material.CLAY, ItemData.삽),
	SOUL_SAND(Material.SOUL_SAND, ItemData.삽),
	WOOD(Material.WOOD, ItemData.도끼),
	LOG(Material.LOG, ItemData.도끼),
	LOG_2(Material.LOG_2, ItemData.도끼),
	STONE(Material.STONE, ItemData.곡괭이),
	BRICK(Material.BRICK, ItemData.곡괭이),
	MOSSY_COBBLESTONE(Material.MOSSY_COBBLESTONE, ItemData.곡괭이);

	private final Material type;
	private final ItemData item;

	private MiniGameBlockItem13(Material type, ItemData item)
	{
		this.type = type;
		this.item = item;
	}
	public Material getMaterial()
	{
		return type;
	}

	public static boolean isItemData(Material type, ItemData item)
	{
		for(MiniGameBlockItem13 bi : values())
		{
			if(bi.type == type && bi.item == item)
			{
				return true;
			}
		}
		return false;
	}
}
