package com.linmalu.MiniGames.Game03;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.MiniGames.Main;

public class MG03_Item
{
	public MG03_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		ItemMeta im;
		for(int i = 0; i < 3; i++)
		{
			item = new ItemStack(Material.GOLD_SPADE);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "ÁÖ¸Ô");
			im.spigot().setUnbreakable(true);
			item.setItemMeta(im);
			items.add(item);
			item = new ItemStack(Material.GOLD_PICKAXE);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "°¡À§");
			im.spigot().setUnbreakable(true);
			item.setItemMeta(im);
			items.add(item);
			item = new ItemStack(Material.GOLD_AXE);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "º¸");
			im.spigot().setUnbreakable(true);
			item.setItemMeta(im);
			items.add(item);
		}
	}
}