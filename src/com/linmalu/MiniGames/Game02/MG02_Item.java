package com.linmalu.MiniGames.Game02;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.MiniGames.Main;

public class MG02_Item
{
	public MG02_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		ItemMeta im;
		item = new ItemStack(Material.SNOW_BALL);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "¥´µ¢¿Ã");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.GOLD_INGOT);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "¡°«¡");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.DIAMOND);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "≈ı∏Ì");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.NETHER_STAR);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "¿Ãµø");
		item.setItemMeta(im);
		items.add(item);
	}
}
