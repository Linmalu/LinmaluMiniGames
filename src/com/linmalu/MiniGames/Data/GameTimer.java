package com.linmalu.minigames.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluBossbar;
import com.linmalu.library.api.LinmaluRanking;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.game008.MiniGameBombChange8;
import com.linmalu.minigames.game009.MiniGameChangeBlock9;
import com.linmalu.minigames.game010.MiniGameRemoveCart10;
import com.linmalu.minigames.game010.MiniGameSpawnCart10;
import com.linmalu.minigames.game012.MiniGameHorse;

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
	public void run()
	{
		if(data.isGame2())
		{
			String msg;
			float health;
			if(cooldown > 0)
			{
				msg = String.format(Main.getMain().getTitle() + ChatColor.GOLD + "%02d" + ChatColor.YELLOW + "초" , cooldown / 20);
				health = cooldown * 100F / md.getCooldown();
			}
			else if(timerStart && cooldown == 0)
			{
				msg = String.format(ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d", time / 20 / 60 , time / 20 % 60);
				health = 100F;
				TimerStart();
			}
			else if(timer)
			{
				msg = String.format(ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d", time / 20 / 60 , time / 20 % 60);
				health = time * 100F / md.getTime();
				if(time == 0)
				{
					TimerEnd();
				}
				else
				{
					time--;
				}
			}
			else
			{
				msg = String.format(ChatColor.YELLOW + "%02d" + ChatColor.GOLD + " : " + ChatColor.YELLOW + "%02d", time / 20 / 60 , time / 20 % 60);
				health = 100F;
				if(md.isTopScore())
				{
					for(Player player : data.getPlayers())
					{
						if(data.getPlayerData(player.getUniqueId()).getScore() >= score)
						{
							gameEnd(player.getUniqueId());
							return;
						}
					}
				}
				time++;
			}
			for(Player player : data.getPlayers())
			{
				LinmaluBossbar.sendMessage(player, msg, health);
//				PlayerData pd = data.getPlayerData(player.getUniqueId());
//				if(timer && md.isTopScore())
//				{
//					player.setExp(health / 100);
//				}
			}
			if(cooldown == 0)
			{
				data.setObjectiveDisplayName(ChatColor.WHITE + " - " + msg);
				TimerRun();
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
	private void TimerStart()
	{
		timerStart = false;
		time = md.getTime();
		if(data.getMinigame() == MiniGame.양털찾기)
		{
			new MiniGameChangeBlock9();
			data.setTargetNumber(new Random().nextInt(16));
			for(Player player : data.getPlayers())
			{
				player.getInventory().clear();
				ItemStack item = new ItemStack(Material.WOOL);
				item.setDurability((short)data.getTargetNumber());
				for(int i = 0; i < 9; i++)
				{
					player.getInventory().setItem(i, item);
				}
			}
		}
		else if(data.getMinigame() == MiniGame.폭탄피하기)
		{
			new MiniGameBombChange8();
		}
		else if(data.getMinigame() == MiniGame.카트타기)
		{
			new MiniGameSpawnCart10();
		}
		else if(data.getMinigame() == MiniGame.경마)
		{
			new MiniGameHorse(null);
		}
	}
	private void TimerRun()
	{
		if(time % 20 == 0)
		{
			if(data.getMinigame() == MiniGame.땅파기)
			{
				for(Player player : data.getPlayers())
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0, true, false), true);
				}
			}
			else if(data.getMinigame() == MiniGame.땅따먹기)
			{
				if(time / 20 > 60)
				{
					for(Player player : data.getPlayers())
					{
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, (time / 20 / 60) -1, true, false), true);
					}
				}
			}
			else if(data.getMinigame() == MiniGame.총싸움)
			{
				for(Player player : data.getPlayers())
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, true, false), true);
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 1, true, false), true);
				}
			}
		}
		if(data.getMinigame() == MiniGame.폭탄피하기)
		{
			if(!data.getPlayerData(data.getTargetPlayer()).isLive())
			{
				time = 0;
			}
		}
	}
	@SuppressWarnings("deprecation")
	private void TimerEnd()
	{
		if(data.getMinigame() == MiniGame.폭탄피하기)
		{
			Player player = Bukkit.getPlayer(data.getTargetPlayer());
			if(player != null)
			{
				Location loc = player.getLocation();
				loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 4F, false, false);
				data.diePlayer(player.getUniqueId());
			}
		}
		else if(data.getMinigame() == MiniGame.양털찾기)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					Block block = md.getWorld().getBlockAt(x, md.getMapHeight(), z);
					if(block.getData() != data.getTargetNumber())
					{
						block.setType(Material.AIR);
					}
				}
			}
			MapData md = data.getMapData();
			if(md.getTime() > 30)
			{
				md.setTime(md.getTime() - 5);
			}
		}
		else if(data.getMinigame() == MiniGame.카트타기)
		{
			new MiniGameRemoveCart10();
		}
		else
		{
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
					data.getScore(ChatColor.GREEN + "목표점수").setScore(score);
				}
				else
				{
					gameEnd(uuid);
				}
			}
		}
		cooldown = md.getCooldown();
		timerStart = timer;
	}
	private void gameEnd(UUID uuid)
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
