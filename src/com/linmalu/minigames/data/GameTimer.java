package com.linmalu.minigames.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluRanking;
import com.linmalu.library.api.LinmaluTitle;
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
			// Bukkit.broadcastMessage(type.toString());
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
				return String.format(ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d", time / 10 / 60, time / 10 % 60, time % 10 * 11);
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
				// TODO 디버깅용으로 시간 변경 : 기본 100
				maxtime = time = 20;
				break;
			case 게임타이머:
				time = data.getMapData().getTime();
				if(timer)
				{
					score = data.getMapData().getScore();
					maxtime = time;
				}
				else
				{
					maxtime = 0;
				}
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
		if(time % 10 == 0)
		{
			data.getBossbar().setTitle(Main.getMain().getTitle() + message);
		}
		data.getPlayers().forEach(player ->
		{
			data.getBossbar().addPlayer(player);
		});
		switch(type)
		{
			case 맵이동:
			case 게임준비:
				if(time % 10 == 0)
				{
					data.getPlayers().forEach(player ->
					{
						// LinmaluActionbar.sendMessage(player, message);
						LinmaluTitle.sendMessage(player, Main.getMain().getTitle(), message, 0, 40, 0);
					});
				}
				break;
			case 게임타이머:
				if(!timer && data.getMapData().isTopScore())
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
		// if(data.isGame2())
		// {
		// String msg = String.format(ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d", time / 20 / 60, time / 20 % 60, time % 20 * 5);
		// float health = 100F;
		// if(cooldown > 0)
		// {
		// msg = String.format(Main.getMain().getTitle() + ChatColor.GOLD + "%02d" + ChatColor.YELLOW + "초", cooldown / 20);
		// health = cooldown / data.getMapData().getCooldown();
		// }
		// else if(timerStart && cooldown == 0)
		// {
		// startTimer();
		// }
		// else if(timer)
		// {
		// health = (float)time / data.getMapData().getTime();
		// if(time == 0)
		// {
		// endTimer();
		// }
		// else
		// {
		// time--;
		// }
		// }
		// else
		// {
		// if(data.getMapData().isTopScore())
		// {
		// for(Player player : data.getPlayers())
		// {
		// if(data.getPlayerData(player.getUniqueId()).getScore() >= data.getMapData().getScore())
		// {
		// endGame(player.getUniqueId());
		// return;
		// }
		// }
		// }
		// time++;
		// }
		// for(Player player : data.getPlayers())
		// {
		// bar.addPlayer(player);
		// }
		// bar.setTitle(msg);
		// bar.setProgress(health);
		// if(cooldown == 0)
		// {
		// data.setObjectiveDisplayName(" " + msg);
		// runTimer();
		// }
		// else
		// {
		// cooldown--;
		// }
		// }
		// else
		// {
		// Bukkit.getScheduler().cancelTask(taskId);
		// bar.setVisible(false);
		// }
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
					data.setObjectiveDisplayName("");
				}
				else
				{
					timer = false;
					maxtime = -1;
					time = 0;
					if(data.getMapData().isTopScore() && data.getMapData().getScore() == 0)
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
				}
				// else
				// {
				// type = TimerType.쿨타임타이머;
				// setTime(data.getMapData().getCooldown());
				// data.setObjectiveDisplayName("");
				// }
				data.getMinigame().getInstance().endTimer();
				break;
			case 쿨타임타이머:
				if(data.getMapData().getCooldown() > 0)
				{
					type = TimerType.게임타이머;
					// maxtime = time = data.getMapData().getTime();
					// Bukkit.broadcastMessage("T : " + data.getMapData().getTime() + " / " + maxtime + " / " + time);
					// setTime(data.getMapData().getCooldown());
					// data.setObjectiveDisplayName("");
				}
				break;
			// default:
			// Bukkit.getScheduler().cancelTask(taskId);
			//// bar.setVisible(false);
			// break;
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
