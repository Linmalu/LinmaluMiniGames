package com.linmalu.minigames.game;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.Languages;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game013.MiniGameBlockItem13;
import com.linmalu.minigames.types.ConfigType;

public abstract class MiniGameUtil
{
	protected static final int MAP_DEFAULT_HEIGHT = 10;

	public abstract MaterialData getChunkData(int y);
	public abstract void addRandomItem(Player player);
	public abstract void startTimer();
	public abstract void runTimer(GameTimer timer);
	public abstract void endTimer();

	protected final GameData data = Main.getMain().getGameData();
	protected final MiniGame minigame;
	protected boolean see = false;
	protected boolean barrier = true;

	private Map<ConfigType, Object> configs = new LinkedHashMap<>();

	public MiniGameUtil(MiniGame minigame)
	{
		this.minigame = minigame;
		setConfigData(ConfigType.USE_MAP, false);
		setConfigData(ConfigType.MAP_WORLD, "world");
		setConfigData(ConfigType.MAP_X1, 0);
		setConfigData(ConfigType.MAP_Z1, 0);
		setConfigData(ConfigType.MAP_X2, 0);
		setConfigData(ConfigType.MAP_Z2, 0);
	}
	protected boolean isConfigData(ConfigType ... types)
	{
		for(ConfigType type : types)
		{
			if(!configs.containsKey(type))
			{
				return false;
			}
		}
		return true;
	}
	protected int getConfigInt(ConfigType type)
	{
		if(configs.containsKey(type))
		{
			Object obj = configs.get(type);
			if(obj instanceof Integer)
			{
				return (int)obj;
			}
		}
		return 0;
	}
	protected boolean getConfigBoolean(ConfigType type)
	{
		if(configs.containsKey(type))
		{
			Object obj = configs.get(type);
			if(obj instanceof Boolean)
			{
				return (boolean)obj;
			}
		}
		return false;
	}
	protected String getConfigString(ConfigType type)
	{
		if(configs.containsKey(type))
		{
			Object obj = configs.get(type);
			if(obj instanceof String)
			{
				return (String)obj;
			}
		}
		return "";
	}
	protected void setConfigData(ConfigType type, Object obj)
	{
		configs.put(type, obj);
	}
	public void reload()
	{
		synchronized(configs)
		{
			configs.forEach((k, v) -> configs.put(k, data.getConfig().get(minigame.toString() + "." + k.getName(), v)));
		}
	}
	public void save()
	{
		synchronized(configs)
		{
			configs.forEach((k, v) -> data.getConfig().set(minigame.toString() + "." + k.getName(), v));
		}
	}
	public void gameruleMessage()
	{
		data.getPlayers().forEach(player ->
		{
			Languages.getRules(minigame).forEach(msg -> player.sendMessage(ChatColor.GREEN + msg));
		});
	}
	public void CreateWrold(World world, int x1, int z1, int x2, int z2)
	{
		try
		{
			File file = new File("./" + Main.WORLD_NAME + "/region/");
			file.mkdirs();
			if(world == null && getConfigBoolean(ConfigType.USE_MAP))
			{
				world = Bukkit.getWorld(getConfigString(ConfigType.MAP_WORLD));
				x1 = getConfigInt(ConfigType.MAP_X1);
				x2 = getConfigInt(ConfigType.MAP_X2);
				z1 = getConfigInt(ConfigType.MAP_Z1);
				z2 = getConfigInt(ConfigType.MAP_Z2);
			}
			if(minigame == MiniGame.블록부수기 || minigame == MiniGame.양털찾기 || minigame == MiniGame.진짜를찾아라 || minigame == MiniGame.신호등블록)
			{
				world = null;
			}
			if(world != null)
			{
				if(x1 > x2)
				{
					int x = x1;
					x1 = x2;
					x2 = x;
				}
				if(z1 > z2)
				{
					int z = z1;
					z1 = z2;
					z2 = z;
				}
				for(int x = x1 / 512 + (x1 < 0 ? -1 : 0) - 1; x <= x2 / 512 + (x2 < 0 ? -1 : 0) + 1; x++)
				{
					for(int z = z1 / 512 + (z1 < 0 ? -1 : 0) - 1; z <= z2 / 512 + (z2 < 0 ? -1 : 0) + 1; z++)
					{
						try
						{
							String name = "/region/r." + x + "." + z + ".mca";
							Files.copy(new File("./" + world.getName() + name).toPath(), new File("./" + Main.WORLD_NAME + name).toPath(), StandardCopyOption.REPLACE_EXISTING);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			else if(minigame == MiniGame.총싸움)
			{
				x1 = 19;
				x2 = 208;
				z1 = 16;
				z2 = 188;
				Files.copy(new File(Main.getMain().getDataFolder(), "world0").toPath(), new File("./" + Main.WORLD_NAME + "/region/r.0.0.mca").toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			else if(minigame == MiniGame.경마)
			{
				x1 = 1;
				x2 = 13;
				z1 = 1;
				z2 = 13;
				Files.copy(new File(Main.getMain().getDataFolder(), "world1").toPath(), new File("./" + Main.WORLD_NAME + "/region/r.0.0.mca").toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			else
			{
				x1 = z1 = 0;
				x2 = z2 = getConfigInt(ConfigType.MAP_DEFAULT_SIZE) + (data.getPlayerAllCount() * getConfigInt(ConfigType.MAP_PLAYER_SIZE));
			}
			World world1 = world;
			int x3 = x1;
			int x4 = x2;
			int z3 = z1;
			int z4 = z2;
			world = WorldCreator.name(Main.WORLD_NAME).type(WorldType.FLAT).environment(World.Environment.NORMAL).generator(new ChunkGenerator()
			{
				@Override
				public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome)
				{
					ChunkData cd = Bukkit.createChunkData(world);
					if(minigame == MiniGame.블록부수기)
					{
						int height = MAP_DEFAULT_HEIGHT;
						for(int y = height; y <= height + 4; y++)
						{
							for(int numX = 0; numX < 16; numX++)
							{
								for(int numZ = 0; numZ < 4; numZ++)
								{
									if(x >= 0 && z >= 0 && (x * 16) + numX < data.getPlayerAllCount() * 4 && (z * 16) + numZ < 4)
									{
										if(numX % 4 == 0 || numX % 4 == 3 || numZ % 4 == 0 || numZ % 4 == 3 || y == height || y == height + 5)
										{
											cd.setBlock(numX, y, numZ, Material.BARRIER);
										}
										else if(y == height + 4)
										{
											cd.setBlock(numX, y, numZ, MiniGameBlockItem13.DIRT.getMaterial());
										}
									}
								}
							}
						}
						return cd;
					}
					else if(world1 == null)
					{
						for(int i = 0; i <= 15; i++)
						{
							int numX = (x * 16) + i;
							for(int j = 0; j <= 15; j++)
							{
								int numZ = (z * 16) + j;
								if(barrier && (x3 - 1 == numX || x4 + 1 == numX || z3 - 1 == numZ || z4 + 1 == numZ))
								{
									cd.setRegion(i, 0, j, i + 1, world.getMaxHeight(), j + 1, Material.BARRIER);
								}
								if(x3 <= numX && numX <= x4 && z3 <= numZ && numZ <= z4)
								{
									for(int y = 0; y < world.getMaxHeight(); y++)
									{
										cd.setRegion(i, y, j, i + 1, y + 1, j + 1, getChunkData(y));
									}
								}
							}
						}
					}
					return cd;
				}
				@Override
				public Location getFixedSpawnLocation(World world, Random random)
				{
					return new Location(world, 0, 0, 0);
				}
			}).createWorld();
			world.setAutoSave(false);
			world.setGameRuleValue("keepInventory", "true");
			world.setGameRuleValue("doDaylightCycle", "false");
			world.setGameRuleValue("doWeatherCycle", "false");
			world.setGameRuleValue("doMobSpawning", "false");
			world.setGameRuleValue("doFireTick", "false");
			world.setGameRuleValue("doTileDrops", "false");
			world.setGameRuleValue("doMobLoot", "false");
			world.setGameRuleValue("mobGriefing", "false");
			world.setGameRuleValue("naturalRegeneration", "false");
			world.getLivingEntities().forEach(LivingEntity::remove);
			if(data.getMinigame() == MiniGame.경마)
			{
				world.setTime(18000L);
			}
			else
			{
				world.setTime(6000L);
			}
			world.setDifficulty(Difficulty.NORMAL);
			data.setMapData(new MapData(world, x1, z1, x2, z2, MAP_DEFAULT_HEIGHT + getConfigInt(ConfigType.MAP_HEIGHT), getConfigInt(ConfigType.TIME_DEFAULT) + (data.getPlayerAllCount() * getConfigInt(ConfigType.TIME_PLAYER)), getConfigInt(ConfigType.COOLDOWN), getConfigInt(ConfigType.SCORE_DEFAULT) + (data.getPlayerAllCount() * getConfigInt(ConfigType.SCORE_PLAYER)), isConfigData(ConfigType.SCORE_DEFAULT, ConfigType.SCORE_PLAYER), see));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "맵을 생성할 수 없습니다.");
			data.GameStop();
		}
	}
	public Location teleport(Entity entity)
	{
		Location loc = data.getMapData().getRandomLocation();
		loc.setYaw(entity.getLocation().getYaw());
		loc.setPitch(entity.getLocation().getPitch());
		loc.setY(50);
		for(int y = loc.getWorld().getMaxHeight(); y > 0; y--)
		{
			if(loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getType() != Material.AIR)
			{
				loc.setY(y + 1);
				break;
			}
		}
		entity.leaveVehicle();
		entity.setFallDistance(0);
		entity.teleport(loc);
		return loc;
	}
}
