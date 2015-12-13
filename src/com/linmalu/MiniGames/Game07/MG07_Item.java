package com.linmalu.MiniGames.Game07;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.MiniGames.Main;

public class MG07_Item
{
	public MG07_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		ItemMeta im;
		item = new ItemStack(Material.GOLD_HOE);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "รั");
		im.spigot().setUnbreakable(true);
		item.setItemMeta(im);
		for(int i = 0; i < 9; i++)
		{
			items.add(item);
		}
	}
}