package com.linmalu.minigames.game011;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MapData;

@SuppressWarnings("deprecation")
public class MiniGameRandomBlock11 implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();

	public MiniGameRandomBlock11()
	{
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public void run()
	{
		if(data.isGame2())
		{
			MapData md = data.getMapData();
			Random ran = new Random();
			int x = ran.nextInt(md.getX2() - md.getX1() +1) + md.getX1();
			int z = ran.nextInt(md.getZ2() - md.getZ1() +1) + md.getZ1();
			Block block = new Location(md.getWorld(), x, md.getMapHeight(), z).getBlock();
			if(block.getType() == Material.WOOL && block.getData() == 5)
			{
				new MiniGameChangeBlock11(block);
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
