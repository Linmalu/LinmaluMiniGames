package com.linmalu.minigames.game000;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.linmalu.minigames.Main;

@SuppressWarnings("deprecation")
public class MiniGameFallingBlock0 implements Runnable
{
	private int taskId;
	private Block block;
	private byte data;

	public MiniGameFallingBlock0(Block block)
	{
		this.block = block;
		data = block.getData();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 4L);
	}
	public void run()
	{
		if(Main.getMain().getGameData().isGame2())
		{
			if(data < 15)
			{
				data++;
				block.setData(data);
			}
			else
			{
				block.setType(Material.AIR);
				block.getWorld().spawnFallingBlock(block.getLocation(), Material.STAINED_GLASS, data);
				Bukkit.getScheduler().cancelTask(taskId);
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
