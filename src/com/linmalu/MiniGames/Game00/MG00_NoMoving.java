package com.linmalu.MiniGames.Game00;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;

public class MG00_NoMoving implements Runnable
{
	private int taskId;
	private Player player;
	private int x1, x2;
	private int z1, z2;
	private int count = 0;

	public MG00_NoMoving(Player player)
	{
		this.player = player;
		x1 = x2 = player.getLocation().getBlockX();
		z1 = z2 = player.getLocation().getBlockZ();
		FallingBlock();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 20L);
	}
	public void run()
	{
		GameData data = Main.getMain().getGameData();
		if(data.isGame2() && data.getPlayerData(player.getUniqueId()).isLive())
		{
			x1 = x2;
			z1 = z2;
			x2 = player.getLocation().getBlockX();
			z2 = player.getLocation().getBlockZ();
			if(x1 == x2 && z1 == z2)
			{
				count++;
			}
			else
			{
				count = 0;
			}
			if(count > 1)
			{
				count = 0;
				FallingBlock();
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
	@SuppressWarnings("deprecation")
	private void FallingBlock()
	{
		for(int x = x1 - 1; x <= x1 + 1; x++)
		{
			for(int z = z1 - 1; z <= z1 + 1; z++)
			{
				Block block = player.getWorld().getBlockAt(x, 10, z);
				if(block.getType() == Material.STAINED_GLASS && block.getData() == 0)
				{
					new MG00_FallingBlock(block);
				}
			}
		}
	}
}
