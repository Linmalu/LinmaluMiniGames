package com.linmalu.minigames.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluActionbar;
import com.linmalu.library.api.LinmaluRanking;
import com.linmalu.minigames.Main;

public class GameTimer implements Runnable
{
	private enum TimerType
	{
		맵이동, 게임준비, 게임타이머, 쿨타임타이머
	};

	private final int taskId;
	private final GameData data = Main.getMain().getGameData();
	private TimerType type = TimerType.맵이동;
	private boolean timer = true;
	private int maxtime = 0;
	private int time = 0;
	private int score = 0;

	public GameTimer()
	{
		data.getMinigame().getInstance().CreateWrold();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 2L);
	}
	public void run()
	{
		if(data.isGame1())
		{
			if(time == maxtime)
			{
				startTimer();
				runTimer();
			}
			else if(time >= 0)
			{
				runTimer();
			}
			else
			{
				endTimer();
			}
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
	private String getMessage()
	{
		switch(type)
		{
			case 맵이동:
				return ChatColor.YELLOW + "게임맵으로 이동까지 " + ChatColor.GOLD + (time / 10) + ChatColor.YELLOW + "초전";
			case 게임준비:
				return ChatColor.GREEN + data.getMinigame().toString() + "게임 시작 " + ChatColor.YELLOW + (time / 10) + ChatColor.GREEN + "초전";
			case 게임타이머:
				return String.format(ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d", time / 10 / 60, time / 10 % 60, (time % 10) * 11);
			case 쿨타임타이머:
				return String.format(ChatColor.GREEN + "게임 재시작 " + ChatColor.YELLOW + "%02d" + ChatColor.GOLD + "초전", time / 10);
			default:
				return "";
		}
	}
	private void startTimer()
	{
		switch(type)
		{
			case 맵이동:
			case 게임준비:
				//TODO 디버깅용 시간조절
				maxtime = time = 100;
//				maxtime = time = 20;
				break;
			case 게임타이머:
				maxtime = time = data.getMapData().getTime();
				score = data.getMapData().getScore();
				data.setGameItem();
				data.getMinigame().getInstance().startTimer();
				break;
			default:
				break;
		}
	}
	private void runTimer()
	{
		String message = getMessage();
		data.getBossbar().setProgress(maxtime > 0D ? (time > maxtime ? 1 : time / (double)maxtime) : 1D);
		data.getBossbar().setTitle(Main.getMain().getTitle() + message);
		data.getPlayers().forEach(data.getBossbar()::addPlayer);
		switch(type)
		{
			case 맵이동:
			case 게임준비:
				if(time % 10 == 0)
				{
					data.getPlayers().forEach(player ->
					{
						LinmaluActionbar.sendMessage(player, message);
						player.sendTitle(Main.getMain().getTitle(), message, 0, 40, 0);
					});
				}
				break;
			case 게임타이머:
				if(score > 0)
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
				data.getMinigame().getInstance().runTimer(this);
				break;
			case 쿨타임타이머:
				break;
		}
		if(timer)
		{
			time--;
		}
		else
		{
			time++;
		}
	}
	private void endTimer()
	{
		maxtime = time = 0;
		switch(type)
		{
			case 맵이동:
				data.getMinigame().getInstance().gameruleMessage();
				data.setGamePlayer();
				type = TimerType.게임준비;
				break;
			case 게임준비:
				type = TimerType.게임타이머;
				break;
			case 게임타이머:
				if(data.getMapData().getCooldown() > 0)
				{
					type = TimerType.쿨타임타이머;
					time = data.getMapData().getCooldown();
				}
				else
				{
					if(data.getMapData().getScore() >= 0)
					{
						Map<UUID, Integer> map = new HashMap<>();
						for(Player player : data.getPlayers())
						{
							map.put(player.getUniqueId(), data.getPlayerData(player.getUniqueId()).getScore());
						}
						int top = -1;
						UUID uuid = null;
						for(Entry<UUID, Integer> data : LinmaluRanking.getRanking(map, false).entrySet())
						{
							if(top < data.getValue())
							{
								uuid = data.getKey();
								top = data.getValue();
							}
							else if(top == data.getValue())
							{
								uuid = null;
								break;
							}
						}
						if(uuid != null && score > 0)
						{
							endGame(uuid);
						}
						score = top + 1;
						data.getScore(ChatColor.YELLOW + "목표점수").setScore(score);
					}
					timer = false;
					maxtime = -1;
					time = 0;
				}
				data.getMinigame().getInstance().endTimer();
				break;
			case 쿨타임타이머:
				if(data.getMapData().getCooldown() > 0)
				{
					type = TimerType.게임타이머;
				}
				break;
		}
	}
	public int getTime()
	{
		return time;
	}
	public void setTime(int time)
	{
		this.time = time;
	}
	private void endGame(UUID uuid)
	{
		data.getPlayers().forEach(player ->
		{
			if(!uuid.equals(player.getUniqueId()))
			{
				data.diePlayer(player.getUniqueId());
			}
		});
	}
}
