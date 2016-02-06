package com.linmalu.minigames.data;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.Main;

public class PlayerData
{
	private UUID uuid;
	private String name;
	private boolean live;
	private GameMode gm;
	private boolean allowFlight;
	private boolean flying;
	private double maxHealth;
	private double health;
	private double healthScale;
	private int level;
	private float exp;
	private int food;
	private Location loc;
	private ItemStack[] armors;
	private ItemStack[] items;
	private boolean skill;
	private int score;
	private int number;
	private boolean cooldown;
	private boolean observer;

	public PlayerData(Player player, int number)
	{
		uuid = player.getUniqueId();
		name = player.getName();
		live = true;
		gm = player.getGameMode();
		allowFlight = player.getAllowFlight();
		flying = player.isFlying();
		maxHealth = player.getMaxHealth();
		health = player.getHealth();
		healthScale = player.getHealthScale();
		level = player.getLevel();
		exp = player.getExp();
		food = player.getFoodLevel();
		loc = player.getLocation();
		armors = player.getInventory().getArmorContents();
		items = player.getInventory().getContents();
		skill = true;
		score = 0;
		this.number = number;
		cooldown = true;
		observer = false;
	}
	public void setPlayer()
	{
		Player player = Bukkit.getPlayer(uuid);
		if(player != null)
		{
			gm = player.getGameMode();
			allowFlight = player.getAllowFlight();
			flying = player.isFlying();
			maxHealth = player.getMaxHealth();
			health = player.getHealth();
			healthScale = player.getHealthScale();
			level = player.getLevel();
			exp = player.getExp();
			food = player.getFoodLevel();
			loc = player.getLocation();
			armors = player.getInventory().getArmorContents();
			items = player.getInventory().getContents();
			player.setMaxHealth(20);
			player.setHealth(20);
			player.setHealthScale(20);
			player.setFoodLevel(20);
			player.setHealth(player.getMaxHealth());
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			player.setGameMode(GameMode.SURVIVAL);
			player.setAllowFlight(false);
		}
	}
	public void resetPlayer()
	{
		Player player = Bukkit.getPlayer(uuid);
		if(player != null)
		{
			player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			if(player.getGameMode() != GameMode.CREATIVE)
			{
				player.setAllowFlight(false);
			}
			for(PotionEffectType potion : PotionEffectType.values())
			{
				if(potion != null)
				{
					player.removePotionEffect(potion);
				}
			}
			player.getInventory().setArmorContents(armors);
			player.getInventory().setContents(items);
			player.setGameMode(gm);
			player.setAllowFlight(allowFlight);
			player.setFlying(flying);
			player.setMaxHealth(maxHealth);
			player.setHealth(health);
			player.setHealthScale(healthScale);
			player.setLevel(level);
			player.setExp(exp);
			player.setFoodLevel(food);
			player.setFallDistance(0);
			player.leaveVehicle();
			player.teleport(loc);
			if(!Main.getMain().getGameData().isResourcePack())
			{
				player.setResourcePack(Main.RESOURCEPACK_DEFAULT);
			}

		}
	}
	public String getName()
	{
		return name;
	}
	public boolean isLive()
	{
		return live;
	}
	public void setLive(boolean live)
	{
		this.live = live;
	}
	public boolean isSkill()
	{
		return skill;
	}
	public void setSkill(boolean skill)
	{
		this.skill = skill;
	}
	public int getScore()
	{
		return score;
	}
	public void setScore(int score)
	{
		this.score = score;
		Main.getMain().getGameData().getScore(ChatColor.GOLD + name).setScore(score);
	}
	public void addScore()
	{
		score++;
		Main.getMain().getGameData().getScore(ChatColor.GOLD + name).setScore(score);
	}
	public void subScore()
	{
		score--;
		Main.getMain().getGameData().getScore(ChatColor.GOLD + name).setScore(score);
	}
	public int getNumber()
	{
		return number;
	}
	public boolean isCooldown()
	{
		return cooldown;
	}
	public void setCooldown(boolean cooldown)
	{
		this.cooldown = cooldown;
	}
	public boolean isObserver()
	{
		return observer;
	}
	public void setObserver()
	{
		observer = true;
	}
}