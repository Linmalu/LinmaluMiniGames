package com.linmalu.MiniGames.Game08;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.MiniGames.Main;

public class MG08_Item
{
	public MG08_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		ItemMeta im;
		item = new ItemStack(Material.TNT, 64);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "ÆøÅº");
		item.setItemMeta(im);
		for(int i = 0; i < 9; i++)
		{
			items.add(item);
		}
	}
}
