package com.linmalu.linmaluminigames.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.linmalulibrary.api.LinmaluActionbar;
import com.linmalu.linmalulibrary.api.LinmaluBossbar;
import com.linmalu.linmaluminigames.Main;

public class CreateWorldTimer implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();
	private int count = 10;

	public CreateWorldTimer()
	{
		data.getMinigame().getUtil().CreateWrold();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 20L);
	}
	public void run()
	{
		if(data.isGame1() && count >= 0)
		{
			String message = ChatColor.YELLOW + "게임맵으로 이동까지 " + ChatColor.GOLD + count + ChatColor.YELLOW + "초전";
			for(Player player : data.getPlayers())
			{
				LinmaluActionbar.sendMessage(player, message);
				LinmaluBossbar.sendMessage(player, message, count * 100 / 10);
			}
			count--;
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
			if(data.isGame1())
			{
				new MoveWorldTimer();
			}
		}
	}
}
