package com.linmalu.MiniGames.Game09;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.MapData;

@SuppressWarnings("deprecation")
public class MG09_Block
{
	public MG09_Block()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		for(int x = md.getX1(); x <= md.getX2(); x++)
		{
			for(int z = md.getZ1(); z <= md.getZ2(); z++)
			{
				Block block = md.getWorld().getBlockAt(x, md.getMapHeight(), z);
				{
					block.setType(Material.WOOL);
					int r = new Random().nextInt(16);
					block.setData((byte)r);
				}
			}
		}
	}
}
