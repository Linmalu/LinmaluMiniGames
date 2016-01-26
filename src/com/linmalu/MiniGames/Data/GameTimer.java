package com.linmalu.MiniGames.Data;

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

import com.linmalu.LinmaluLibrary.API.LinmaluBossbar;
import com.linmalu.LinmaluLibrary.API.LinmaluRanking;
import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Game08.MG08_Change;
import com.linmalu.MiniGames.Game09.MG09_Block;
import com.linmalu.MiniGames.Game10.MG10_Remove;
import com.linmalu.MiniGames.Game10.MG10_Spawn;
import com.linmalu.MiniGames.Game12.MG12_Horse;

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
				msg = String.format(Main.getMain().getTitle() + ChatColor.GOLD + "%02d" + ChatColor.YELLOW + "��" , cooldown / 20);
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
				LinmaluBossbar.setMessage(player, msg, health);
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
		if(data.getMinigame() == MiniGames.����ã��)
		{
			new MG09_Block();
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
		else if(data.getMinigame() == MiniGames.��ź���ϱ�)
		{
			new MG08_Change();
		}
		else if(data.getMinigame() == MiniGames.īƮŸ��)
		{
			new MG10_Spawn();
		}
		else if(data.getMinigame() == MiniGames.�渶)
		{
			new MG12_Horse(null);
		}
	}
	private void TimerRun()
	{
		if(time % 20 == 0)
		{
			if(data.getMinigame() == MiniGames.���ı�)
			{
				for(Player player : data.getPlayers())
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0, true, false), true);
				}
			}
			else if(data.getMinigame() == MiniGames.�����Ա�)
			{
				if(time / 20 > 60)
				{
					for(Player player : data.getPlayers())
					{
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, (time / 20 / 60) -1, true, false), true);
					}
				}
			}
			else if(data.getMinigame() == MiniGames.�ѽο�)
			{
				for(Player player : data.getPlayers())
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, true, false), true);
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 1, true, false), true);
				}
			}
		}
		if(data.getMinigame() == MiniGames.��ź���ϱ�)
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
		if(data.getMinigame() == MiniGames.��ź���ϱ�)
		{
			Player player = Bukkit.getPlayer(data.getTargetPlayer());
			if(player != null)
			{
				Location loc = player.getLocation();
				loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 4F, false, false);
				data.diePlayer(player.getUniqueId());
			}
		}
		else if(data.getMinigame() == MiniGames.����ã��)
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
		else if(data.getMinigame() == MiniGames.īƮŸ��)
		{
			new MG10_Remove();
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
				LinkedHashMap<String, Integer> rank = LinmaluRanking.getRanking(map);
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
					data.getScore(ChatColor.GREEN + "��ǥ����").setScore(score);
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
