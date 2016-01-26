package com.linmalu.MiniGames.Game02;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MapData;

public class MG02_Falling implements Runnable
{
	private int taskId;
	private int count = 0;

	public MG02_Falling()
	{
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	@SuppressWarnings("deprecation")
	public void run()
	{
		GameData data = Main.getMain().getGameData();
		if(data.isGame2())
		{
			count++;
			MapData md = data.getMapData();
			Random ran = new Random();
			for(int i = 0; i < count / 20 / 30 + 2; i++)
			{
				int x = ran.nextInt(md.getX2() - md.getX1() -1) + md.getX1() + 1;
				int z = ran.nextInt(md.getZ2() - md.getZ1() -1) + md.getZ1() + 1;
				Location loc = new Location(md.getWorld(), x, md.getMapHeight(), z);
				if(loc.getBlock().isEmpty())
				{
					if(ran.nextInt(5) == 0)
					{
						md.getWorld().spawnFallingBlock(loc, Material.WOOL, (byte)ran.nextInt(16));
					}
					else
					{
						md.getWorld().spawnFallingBlock(loc, Material.STAINED_CLAY, (byte)ran.nextInt(16));
					}
				}
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
