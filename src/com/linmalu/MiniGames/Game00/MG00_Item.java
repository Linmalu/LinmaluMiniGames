package com.linmalu.MiniGames.Game00;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.MiniGames.Main;

public class MG00_Item
{
	public MG00_Item()
	{
		ArrayList<ItemStack> items = Main.getMain().getGameData().getItems();
		ItemStack item;
		ItemMeta im;
		item = new ItemStack(Material.IRON_INGOT);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "속도");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.GOLD_INGOT);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "점프");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.DIAMOND);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "투명");
		item.setItemMeta(im);
		items.add(item);
		item = new ItemStack(Material.EMERALD);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "중력");
		item.setItemMeta(im);
		items.add(item);
	}
}
