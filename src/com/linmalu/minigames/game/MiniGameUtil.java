package com.linmalu.minigames.game;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
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
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;

import com.linmalu.library.api.LinmaluConfig;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game013.MiniGameBlockItem13;

public abstract class MiniGameUtil
{
	protected static final LinmaluConfig config = new LinmaluConfig(new File(Main.getMain().getDataFolder(), "config.yml"));
	protected static final int MAP_DEFAULT_HEIGHT = 10;

	public static void reloadConfig()
	{
		config.reload();
		Arrays.asList(MiniGame.values()).forEach(mg -> mg.getInstance().reload());
		config.clear();
		Arrays.asList(MiniGame.values()).forEach(mg -> mg.getInstance().save());
	}

	public abstract MaterialData getChunkData(int y);
	public abstract void addRandomItem(Player player);
	public abstract void startTimer();
	public abstract void runTimer(GameTimer timer);
	public abstract void endTimer();

	protected final GameData data = Main.getMain().getGameData();
	protected final MiniGame minigame;
	protected boolean see;
	protected int x1, z1, x2, z2;
	protected Map<ConfigData, Integer> configs = new HashMap<>();

	public MiniGameUtil(MiniGame minigame)
	{
		this.minigame = minigame;
	}
	public void gameruleMessage()
	{
		data.getPlayers().forEach(player ->
		{
			minigame.getGamerule().forEach(msg -> player.sendMessage(ChatColor.GREEN + msg));
		});
	}
	public void CreateWrold()
	{
		try
		{
			File file = new File("./" + Main.WORLD_NAME + "/region/");
			file.mkdirs();
			int number = -1;
			if(data.getMinigame() == MiniGame.총싸움)
			{
				number = 0;
			}
			else if(data.getMinigame() == MiniGame.경마)
			{
				number = 1;
			}
			if(number != -1)
			{
				Files.copy(new File(Main.getMain().getDataFolder(), "world" + number).toPath(), new File("./" + Main.WORLD_NAME + "/region/r.0.0.mca").toPath(), StandardCopyOption.REPLACE_EXISTING);
				switch(number)
				{
					case 0:
						x1 = 19;
						x2 = 208;
						z1 = 16;
						z2 = 188;
						Bukkit.broadcastMessage(ChatColor.GREEN + "맵제작자 : " + ChatColor.YELLOW + "HGMstudio");
						Bukkit.broadcastMessage(ChatColor.GREEN + "맵출처 : " + ChatColor.YELLOW + "http://hgmstudio.tistory.com/category");
						break;
					case 1:
						x1 = 1;
						x2 = 13;
						z1 = 1;
						z2 = 13;
						break;
				}
			}
			else
			{
				x2 = z2 = getConfigData(ConfigData.MAP_DEFAULT_SIZE) + (data.getPlayerAllCount() * getConfigData(ConfigData.MAP_PLAYER_SIZE));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "맵을 생성할 수 없습니다.");
			data.GameStop();
			return;
		}
		World world = WorldCreator.name(Main.WORLD_NAME).type(WorldType.FLAT).environment(World.Environment.NORMAL).generator(new ChunkGenerator()
		{
			@Override
			public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome)
			{
				ChunkData cd = Bukkit.createChunkData(world);
				if(minigame == MiniGame.블록부수기)
				{
					for(int y = MAP_DEFAULT_HEIGHT; y <= MAP_DEFAULT_HEIGHT + 4; y++)
					{
						for(int numX = 0; numX < 16; numX++)
						{
							for(int numZ = 0; numZ < 4; numZ++)
							{
								if(x >= 0 && z >= 0 && (x * 16) + numX < data.getPlayerAllCount() * 4 && (z * 16) + numZ < 4)
								{
									if(numX % 4 == 0 || numX % 4 == 3 || numZ % 4 == 0 || numZ % 4 == 3 || y == MAP_DEFAULT_HEIGHT || y == MAP_DEFAULT_HEIGHT + 4)
									{
										cd.setBlock(numX, y, numZ, Material.BARRIER);
									}
									else if(y == MAP_DEFAULT_HEIGHT + 3)
									{
										cd.setBlock(numX, y, numZ, MiniGameBlockItem13.DIRT.getMaterial());
									}
								}
							}
						}

					}
					return cd;
				}
				else
				{
					for(int i = 0; i <= 15; i++)
					{
						int numX = (x * 16) + i;
						for(int j = 0; j <= 15; j++)
						{
							int numZ = (z * 16) + j;
							if(x1 - 1 == numX || x2 + 1 == numX || z1 - 1 == numZ || numZ == z2 + 1)
							{
								cd.setRegion(i, 0, j, i + 1, world.getMaxHeight(), j + 1, Material.BARRIER);
							}
							if(x1 <= numX && numX <= x2 && z1 <= numZ && numZ <= z2)
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
		if(data.getMinigame() == MiniGame.경마)
		{
			world.setTime(18000L);
		}
		else
		{
			world.setTime(6000L);
		}
		world.setDifficulty(Difficulty.NORMAL);
		data.setMapData(new MapData(world, x1, z1, x2, z2, MAP_DEFAULT_HEIGHT + getConfigData(ConfigData.MAP_HEIGHT), getConfigData(ConfigData.TIME_DEFAULT) + (data.getPlayerAllCount() * getConfigData(ConfigData.TIME_PLAYER)), getConfigData(ConfigData.COOLDOWN), getConfigData(ConfigData.SCORE_DEFAULT) + (data.getPlayerAllCount() * getConfigData(ConfigData.SCORE_PLAYER)), isConfigData(ConfigData.SCORE_DEFAULT, ConfigData.SCORE_PLAYER), see));
	}
	protected boolean isConfigData(ConfigData ... configData)
	{
		for(ConfigData cd : configData)
		{
			if(!configs.containsKey(cd))
			{
				return false;
			}
		}
		return true;
	}
	public void moveWorld(Player player)
	{
		data.teleport(player);
	}
	protected int getConfigData(ConfigData configData)
	{
		if(configs.containsKey(configData))
		{
			return configs.get(configData);
		}
		return 0;
	}
	protected void reload()
	{
		synchronized(configs)
		{
			configs.forEach((k, v) -> configs.put(k, config.getInt(minigame.toString() + "." + k.getName(), v)));
		}
	}
	protected void save()
	{
		synchronized(configs)
		{
			configs.forEach((k, v) -> config.set(minigame.toString() + "." + k.getName(), v));
		}
	}
}
