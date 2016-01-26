package com.linmalu.MiniGames.Game04;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.MapData;

public class MG04_World
{
	@SuppressWarnings("deprecation")
	public MG04_World()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					Block block = md.getWorld().getBlockAt(x, y, z);
					if(y == 10 || y == 13 || y == 16)
					{
						block.setType(Material.SNOW_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
}
