package com.linmalu.minigames.game011;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.linmalu.minigames.Main;

@SuppressWarnings("deprecation")
public class MiniGameChangeBlock11 implements Runnable
{
	private final int taskId;
	private final Block block;
	private int count = 0;

	public MiniGameChangeBlock11(Block block)
	{
		this.block = block;
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 20L);
	}
	public void run()
	{
		if(Main.getMain().getGameData().isGame2() && count <= 10)
		{
			if(count == 0)
			{
				block.setData((byte)4);
			}
			else if(count == 5)
			{
				block.setData((byte)14);
			}
			else if(count == 10)
			{
				block.setType(Material.AIR);
			}
			count++;
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
