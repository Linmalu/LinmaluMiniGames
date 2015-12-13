package com.linmalu.MiniGames.Game11;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MapData;

@SuppressWarnings("deprecation")
public class MG11_Block implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();

	public MG11_Block()
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
				new MG11_Data(block);
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
