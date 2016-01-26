package com.linmalu.minigames.game001;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MapData;

public class MiniGameFallingBlock1 implements Runnable
{
	private int taskId;
	private int count = 0;

	public MiniGameFallingBlock1()
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
			for(int i = 0; i < count / 20 / 10 + 1; i++)
			{
				Random ran = new Random();
				int x = ran.nextInt(md.getX2() - md.getX1() -1) + md.getX1() + 1;
				int z = ran.nextInt(md.getZ2() - md.getZ1() -1) + md.getZ1() + 1;
				Location loc = new Location(md.getWorld(), x, md.getMapHeight(), z);
				FallingBlock fb = md.getWorld().spawnFallingBlock(loc, Material.ANVIL, (byte) 0);
				float x1, z1;
				x1 = ran.nextFloat();
				if(ran.nextInt(2) == 0)
				{
					x1 *= -1;
				}
				z1 = ran.nextFloat();
				if(ran.nextInt(2) == 0)
				{
					z1 *= -1;
				}
				if(count / 20 > 30 && ran.nextInt(2) == 0)
				{
					fb.setVelocity(new Location(md.getWorld(), x1, 0, z1).toVector());
				}
				if(count / 20 == 60)
				{
					md.getWorld().setTime(13000L);
				}
				else if(count / 20 == 90)
				{
					md.getWorld().setTime(18000L);
				}
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
