package com.linmalu.minigames.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluActionbar;
import com.linmalu.library.api.LinmaluBossbar;
import com.linmalu.minigames.Main;

public class MoveWorldTimer implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();
	private int time = 10 * 20;

	public MoveWorldTimer()
	{
		data.getMinigame().getUtil().sendMessage(data.getPlayers());
		data.setGamePlayer();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public void run()
	{
		if(data.isGame1() && time >= 0)
		{
			String message = ChatColor.GREEN + data.getMinigame().toString() + "게임 시작 " + ChatColor.YELLOW + (time / 20) + ChatColor.GREEN + "초전";
			for(Player player : data.getPlayers())
			{
				player.setLevel(time / 20);
				player.setExp(time % 20 * 0.05F);
				LinmaluActionbar.sendMessage(player, message);
				LinmaluBossbar.sendMessage(player, message, player.getExp() * 100);
			}
			time--;
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
			if(data.isGame1())
			{
				new GameTimer();
			}
		}
	}
}
