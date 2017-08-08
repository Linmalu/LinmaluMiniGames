package com.linmalu.minigames.data;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluConfig;
import com.linmalu.minigames.Main;

public class PlayerData
{
	private static final LinmaluConfig config = new LinmaluConfig(new File(Main.getMain().getDataFolder(), "players.yml"));

	public static PlayerData getPlayerData(UUID uuid)
	{
		if(config.isSet(uuid.toString()))
		{
			PlayerData pd = new PlayerData();
			pd.uuid = uuid;
			try
			{
				pd.gm = GameMode.valueOf(config.getString(uuid.toString() + ".gm"));
			}
			catch(Exception e)
			{
				pd.gm = GameMode.SURVIVAL;
			}
			pd.allowFlight = config.getBoolean(uuid.toString() + ".allowFlight", false);
			pd.flying = config.getBoolean(uuid.toString() + ".flying", false);
			pd.maxHealth = config.getDouble(uuid.toString() + ".maxHealth", 20D);
			pd.health = config.getDouble(uuid.toString() + ".health", 20D);
			pd.healthScale = config.getDouble(uuid.toString() + ".healthScale", 20D);
			pd.level = config.getInt(uuid.toString() + ".level", 0);
			pd.exp = (float)config.getDouble(uuid.toString() + ".exp", 0D);
			pd.food = config.getInt(uuid.toString() + ".food", 20);
			World w = Bukkit.getWorld(config.getString(uuid.toString() + ".world", "world"));
			if(w == null)
			{
				w = Bukkit.getWorlds().get(0);
			}
			pd.loc = config.getVector(uuid.toString() + ".loc", w.getSpawnLocation().toVector()).toLocation(w);
			List<ItemStack> inv = config.getListData(uuid.toString() + ".armors");
			pd.armors = inv.toArray(new ItemStack[inv.size()]);
			inv = config.getListData(uuid.toString() + ".items");
			pd.items = inv.toArray(new ItemStack[inv.size()]);
			return pd;
		}
		return null;
	}

	private UUID uuid;
	private String name;
	private boolean live;
	private GameMode gm;
	private boolean allowFlight;
	private boolean flying;
	private float flySpeed;
	private float walkSpeed;
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

	private PlayerData()
	{
	}
	public PlayerData(Player player, int number)
	{
		uuid = player.getUniqueId();
		name = player.getName();
		live = true;
		gm = player.getGameMode();
		allowFlight = player.getAllowFlight();
		flying = player.isFlying();
		flySpeed = player.getFlySpeed();
		walkSpeed = player.getWalkSpeed();
		healthScale = player.getHealthScale();
		maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
		health = player.getHealth();
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
	public void save()
	{
		if(!config.isSet(uuid.toString()))
		{
			config.set(uuid.toString() + ".name", name);
			config.set(uuid.toString() + ".gm", gm.toString());
			config.set(uuid.toString() + ".allowFlight", allowFlight);
			config.set(uuid.toString() + ".flying", flying);
			config.set(uuid.toString() + ".flySpeed", flySpeed);
			config.set(uuid.toString() + ".walkSpeed", walkSpeed);
			config.set(uuid.toString() + ".healthScale", healthScale);
			config.set(uuid.toString() + ".maxHealth", maxHealth);
			config.set(uuid.toString() + ".health", health);
			config.set(uuid.toString() + ".level", level);
			config.set(uuid.toString() + ".exp", exp);
			config.set(uuid.toString() + ".food", food);
			config.set(uuid.toString() + ".world", loc.getWorld().getName());
			config.set(uuid.toString() + ".loc", loc.toVector());
			config.set(uuid.toString() + ".armors", Arrays.asList(armors));
			config.set(uuid.toString() + ".items", Arrays.asList(items));
		}
	}
	public void setPlayer()
	{
		Player player = Bukkit.getPlayer(uuid);
		if(player != null)
		{
			for(PotionEffectType potion : PotionEffectType.values())
			{
				if(potion != null)
				{
					player.removePotionEffect(potion);
				}
			}
			gm = player.getGameMode();
			allowFlight = player.getAllowFlight();
			flying = player.isFlying();
			healthScale = player.getHealthScale();
			maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
			health = player.getHealth();
			level = player.getLevel();
			exp = player.getExp();
			food = player.getFoodLevel();
			loc = player.getLocation();
			armors = player.getInventory().getArmorContents();
			items = player.getInventory().getContents();
			player.setGameMode(GameMode.SURVIVAL);
			player.setAllowFlight(false);
			player.setFlying(false);
			player.setFlySpeed(0.1F);
			player.setWalkSpeed(0.2F);
			player.setHealthScale(20);
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
			player.setHealth(20);
			player.setLevel(0);
			player.setExp(0);
			player.setFoodLevel(20);
			player.getInventory().setArmorContents(null);
			player.getInventory().clear();
			save();
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
			player.setHealthScale(healthScale);
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
			player.setHealth(health < maxHealth ? health : maxHealth);
			player.setLevel(level);
			player.setExp(exp);
			player.setFoodLevel(food);
			player.setFallDistance(0);
			player.leaveVehicle();
			player.teleport(loc);
			config.remove(uuid.toString());
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