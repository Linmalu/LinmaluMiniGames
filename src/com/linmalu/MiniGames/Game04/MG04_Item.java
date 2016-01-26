package com.linmalu.MiniGames.Game04;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.MiniGames.Main;

public class MG04_Item
{
	public MG04_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		ItemMeta im;
		item = new ItemStack(Material.IRON_INGOT);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "¼Óµµ");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.GOLD_INGOT);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Á¡ÇÁ");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.DIAMOND);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Åõ¸í");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.STRING);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "´À¸²");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.INK_SACK);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "¾îµÒ");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.NETHER_STAR);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "ÀÌµ¿");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.SNOW_BALL);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "´«µ¢ÀÌ");
		item.setItemMeta(im);
		items.add(item);
	}
}