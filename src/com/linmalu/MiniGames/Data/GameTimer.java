package com.linmalu.minigames.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluBossbar;
import com.linmalu.library.api.LinmaluRanking;
import com.linmalu.minigames.Main;

public class GameTimer implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();
	private MapData md = data.getMapData();
	private int time = md.getTime();
	private int cooldown = 0;
	private boolean timer = true;
	private boolean timerStart = true;
	private int score = md.getScore();

	public GameTimer()
	{
		data.setGameItem();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public int getTime()
	{
		return time;
	}
	public void setTime(int time)
	{
		this.time = time;
	}
	public void run()
	{
		if(data.isGame2())
		{
			String msg = String.format(ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d" , time / 20 / 60 , time / 20 % 60, time % 20 * 5);
			float health = 100F;
			if(cooldown > 0)
			{
				msg = String.format(Main.getMain().getTitle() + ChatColor.GOLD + "%02d" + ChatColor.YELLOW + "초" , cooldown / 20);
				health = cooldown * 100F / md.getCooldown();
			}
			else if(timerStart && cooldown == 0)
			{
				startTimer();
			}
			else if(timer)
			{
				health = time * 100F / md.getTime();
				if(time == 0)
				{
					endTimer();
				}
				else
				{
					time--;
				}
			}
			else
			{
				if(md.isTopScore())
				{
					for(Player player : data.getPlayers())
					{
						if(data.getPlayerData(player.getUniqueId()).getScore() >= score)
						{
							endGame(player.getUniqueId());
							return;
						}
					}
				}
				time++;
			}
			for(Player player : data.getPlayers())
			{
				LinmaluBossbar.sendMessage(player, msg, health);
			}
			if(cooldown == 0)
			{
				data.setObjectiveDisplayName(" " + msg);
				runTimer();
			}
			else
			{
				cooldown--;
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
	private void startTimer()
	{
		timerStart = false;
		time = md.getTime();
		data.getMinigame().getHandle().startTimer();
	}
	private void runTimer()
	{
		data.getMinigame().getHandle().runTimer(this);
	}
	private void endTimer()
	{
		data.getMinigame().getHandle().endTimer();
		timer = false;
		if(md.isTopScore() && score == 0)
		{
			HashMap<String, Integer> map = new HashMap<>();
			for(Player player : data.getPlayers())
			{
				map.put(player.getUniqueId().toString(), data.getPlayerData(player.getUniqueId()).getScore());
			}
			LinkedHashMap<String, Integer> rank = LinmaluRanking.getRanking(map, false);
			int top = -1;
			UUID uuid = null;
			for(String s : rank.keySet())
			{
				int i = rank.get(s);
				if(top < i)
				{
					top = i;
					uuid = UUID.fromString(s);
				}
				else if(top == i)
				{
					uuid = null;
					break;
				}
			}
			if(uuid == null)
			{
				score = top + 1;
				data.getScore(ChatColor.YELLOW + "목표점수").setScore(score);
			}
			else
			{
				endGame(uuid);
			}
		}
		else if(md.getCooldown() != 0)
		{
			timerStart = timer = true;
		}
		cooldown = md.getCooldown();
		data.setObjectiveDisplayName("");
	}
	private void endGame(UUID uuid)
	{
		for(Player player : data.getPlayers())
		{
			if(!uuid.equals(player.getUniqueId()))
			{
				data.diePlayer(player.getUniqueId());
			}
		}
	}
}
