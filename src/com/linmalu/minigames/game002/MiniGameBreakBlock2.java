package com.linmalu.minigames.game002;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;

public class MiniGameBreakBlock2 implements Runnable
{
	private final GameData data = Main.getMain().getGameData();
	private final int taskId;
	private Block block;

	public MiniGameBreakBlock2(Block block)
	{
		this.block = block;
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 5L);
	}
	@SuppressWarnings("deprecation")
	public void run()
	{
		if(data.isGame2() && block.getLocation().getBlockY() <= data.getMapData().getMapHeight() + data.getMapData().getScore())
		{
			block = block.getRelative(BlockFace.UP);
			if(!block.isEmpty())
			{
				block.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), block.getType(), block.getData());
				block.setType(Material.AIR);
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
