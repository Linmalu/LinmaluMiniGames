package com.linmalu.minigames.game009;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;

@SuppressWarnings("deprecation")
public class MiniGameChangeBlock9
{
	public MiniGameChangeBlock9(boolean random)
	{
		MapData md = Main.getMain().getGameData().getMapData();
		for(int x = md.getX1(); x <= md.getX2(); x++)
		{
			for(int z = md.getZ1(); z <= md.getZ2(); z++)
			{
				Block block = md.getWorld().getBlockAt(x, md.getMapHeight(), z);
				{
					if(random)
					{
						block.setType(Material.WOOL);
						int r = new Random().nextInt(16);
						block.setData((byte)r);
					}
					else if(block.getData() != Main.getMain().getGameData().getTargetNumber())
					{
						block.setType(Material.AIR);
					}
				}
			}
		}
	}
}
