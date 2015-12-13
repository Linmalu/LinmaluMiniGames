package com.linmalu.MiniGames.Game06;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.linmalu.MiniGames.Main;

public class MG06_Item
{
	public MG06_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		for(short i = 0; i <= 15; i++)
		{
			item = new ItemStack(Material.WOOL, 64);
			item.setDurability(i);
			items.add(item);
		}
	}
}