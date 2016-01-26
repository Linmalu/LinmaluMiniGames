package com.linmalu.MiniGames.Game01;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.MiniGames.Main;

public class MG01_Item
{
	public MG01_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		ItemMeta im;
		item = new ItemStack(Material.ANVIL, 64);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "맞으면 아픈 모루");
		item.setItemMeta(im);
		for(int i = 0; i < 9; i++)
		{
			items.add(item);
		}
	}
}
