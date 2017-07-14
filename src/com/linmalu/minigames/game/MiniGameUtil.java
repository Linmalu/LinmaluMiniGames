package com.linmalu.minigames.game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluConfig;
import com.linmalu.library.api.LinmaluMath;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.Cooldown;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game000.MiniGameFallingBlock0;
import com.linmalu.minigames.game007.MiniGameShoot7;

public abstract class MiniGameUtil
{
	protected static final String MAP_DEFAULT_SIZE = "기본맵 크기";
	protected static final String MAP_PLAYER_SIZE = "인원수 추가 게임맵 크기";
	protected static final String SCORE_DEFAULT = "기본 목표점수";
	protected static final String SCORE_PLAYER = "플레이어 추가 목표점수";
	protected static final String MAP_HEIGHT = "게임맵 높이";
	protected static final String TIME_DEFAULT = "기본 제한 시간(초)";
	protected static final String TIME_PLAYER = "인원수 추가 제한 시간(초)";
	protected static final LinmaluConfig config = new LinmaluConfig(new File(Main.getMain().getDataFolder(), "config.yml"));
	protected static final int MAP_DEFAULT_HEIGHT = 10;

	public static void reloadConfig()
	{
		config.reload();
		config.getKeys(false).stream().filter(k ->
		{
			for(MiniGame mg : MiniGame.values())
			{
				if(k.equalsIgnoreCase(mg.toString()))
				{
					return false;
				}
			}
			return true;
		}).forEach(config::remove);
		for(MiniGame minigame : MiniGame.values())
		{
			minigame.getInstance().reload();
			minigame.getInstance().save();
		}
	}

	public abstract MaterialData getChunkData(int y);
	public abstract void addRandomItem(Player player);
	public abstract void startTimer();
	public abstract void runTimer(GameTimer timer);
	public abstract void endTimer();

	protected final GameData data = Main.getMain().getGameData();
	protected final MiniGame minigame;
	protected final String[] msgs;
	protected int mapDefault, mapPlayer, x1, z1, x2, z2, scoreDefault, score, scorePlayer, mapHeight, time, timeDefault, timePlayer, cooldown;
	protected boolean see;

	public MiniGameUtil(MiniGame minigame, String[] msgs)
	{
		this.minigame = minigame;
		this.msgs = msgs;
		mapHeight = MAP_DEFAULT_HEIGHT;
		mapDefault = mapPlayer = scoreDefault = scorePlayer = mapHeight = timeDefault = timePlayer = -1;
	}
	public String getConfigPath(String path)
	{
		return minigame.toString() + "." + path;
	}
	public void reload()
	{
		if(mapDefault >= 0)
		{
			int data = config.getInt(getConfigPath(MAP_DEFAULT_SIZE), mapDefault);
			if(data >= 0)
			{
				mapDefault = data;
			}
		}
		if(mapPlayer >= 0)
		{
			int data = config.getInt(getConfigPath(MAP_PLAYER_SIZE), mapPlayer);
			if(data >= 0)
			{
				mapPlayer = data;
			}
		}
		if(scoreDefault >= 0)
		{
			int data = config.getInt(getConfigPath(SCORE_DEFAULT), scoreDefault);
			if(data >= 0)
			{
				scoreDefault = data;
			}
		}
		if(scorePlayer >= 0)
		{
			int data = config.getInt(getConfigPath(SCORE_PLAYER), scorePlayer);
			if(data >= 0)
			{
				scorePlayer = data;
			}
		}
		if(mapHeight >= 0)
		{
			int data = config.getInt(getConfigPath(MAP_HEIGHT), mapHeight) + MAP_DEFAULT_HEIGHT;
			if(data >= 0)
			{
				mapHeight = data;
			}
		}
		if(timeDefault >= 0)
		{
			int data = config.getInt(getConfigPath(TIME_DEFAULT), timeDefault);
			if(data >= 0)
			{
				timeDefault = data;
			}
		}
		if(timePlayer >= 0)
		{
			int data = config.getInt(getConfigPath(TIME_PLAYER), timePlayer);
			if(data >= 0)
			{
				timePlayer = data;
			}
		}
	}
	public void save()
	{
		config.remove(minigame.toString());
		if(mapDefault >= 0)
		{
			config.set(getConfigPath(MAP_DEFAULT_SIZE), mapDefault);
		}
		if(mapPlayer >= 0)
		{
			config.set(getConfigPath(MAP_PLAYER_SIZE), mapPlayer);
		}
		if(scoreDefault >= 0)
		{
			config.set(getConfigPath(SCORE_DEFAULT), scoreDefault);
		}
		if(scorePlayer >= 0)
		{
			config.set(getConfigPath(SCORE_PLAYER), scorePlayer);
		}
		if(mapHeight - MAP_DEFAULT_HEIGHT >= 0)
		{
			config.set(getConfigPath(MAP_HEIGHT), mapHeight - MAP_DEFAULT_HEIGHT);
		}
		if(timeDefault >= 0)
		{
			config.set(getConfigPath(TIME_DEFAULT), timeDefault);
		}
		if(timePlayer >= 0)
		{
			config.set(getConfigPath(TIME_PLAYER), timePlayer);
		}
	}
	public void gameruleMessage()
	{
		data.getPlayers().forEach(player ->
		{
			for(String msg : msgs)
			{
				player.sendMessage(ChatColor.GREEN + msg);
			}
		});
	}
	public MapData getMapData(World world)
	{
		return new MapData(world, x1, z1, x2, z2, mapHeight >= 0 ? mapHeight : MAP_DEFAULT_HEIGHT, time, cooldown, score, see);
	}
	public void CreateWrold()
	{
		x2 = z2 = mapDefault + (data.getPlayerAllCount() * mapPlayer);
		time = timeDefault + (data.getPlayerAllCount() * timePlayer);
		score = scoreDefault + (data.getPlayerAllCount() * scorePlayer);
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
				try(BufferedInputStream in = new BufferedInputStream(getClass().getResourceAsStream("/com/linmalu/minigames/world/world" + number + ".zip")); BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("./" + Main.WORLD_NAME + "/region/r.0.0.mca"));)
				{
					byte[] bytes = new byte[1024];
					while(in.read(bytes) != -1)
					{
						out.write(bytes);
					}
					out.flush();
				}
				catch(Exception e)
				{
					throw e;
				}
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
					Material[] blockItems = {Material.DIRT, Material.SAND, Material.GRAVEL, Material.WOOD, Material.LOG, Material.LOG_2, Material.STONE, Material.BRICK, Material.MOSSY_COBBLESTONE};
					Random r = new Random(world.getSeed());
					for(int y = MAP_DEFAULT_HEIGHT; y <= mapHeight; y++)
					{
						int ran = r.nextInt(blockItems.length);
						for(int numX = 0; numX < 16; numX++)
						{
							for(int numZ = 0; numZ < 16; numZ++)
							{
								if(y != MAP_DEFAULT_HEIGHT && y != mapHeight && ((x * 16) + numX) % 5 == 2 && ((z * 16) + numZ) % 5 == 2)
								{
									if(y < mapHeight - 2)
									{
										cd.setBlock(numX, y, numZ, blockItems[ran]);
									}
								}
								else
								{
									cd.setBlock(numX, y, numZ, Material.BARRIER);
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
		data.setMapData(getMapData(world));
	}
	public void moveWorld(Player player)
	{
		data.teleportPlayer(player);
	}
	@SuppressWarnings("deprecation")
	public void useItem(Player player, boolean remove, int cooldown)
	{
		GameData data = Main.getMain().getGameData();
		ItemStack item = player.getItemInHand();
		if(item != null && item.getType() != Material.AIR && item.hasItemMeta())
		{
			GameItem gameItem = GameItem.getGameItem(item);
			boolean message = true;
			switch(gameItem)
			{
				case 공기:
					break;
				case 속도:
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, false, false), true);
					break;
				case 점프:
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 3, false, false), true);
					break;
				case 투명:
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false), true);
					break;
				case 중력:
					Location loc = player.getLocation();
					int size = 8;
					for(int x = loc.getBlockX() - size; x <= loc.getBlockX() + size; x++)
					{
						for(int z = loc.getBlockZ() - size; z <= loc.getBlockZ() + size; z++)
						{
							Block block = player.getWorld().getBlockAt(x, data.getMapData().getMapHeight(), z);
							if(block.getType() == Material.STAINED_GLASS && block.getData() == 0 && loc.distance(block.getLocation()) <= size)
							{
								new MiniGameFallingBlock0(block);
							}
						}
					}
					break;
				case 이동:
					data.teleportPlayer(player);
					break;
				case 느림:
					for(Player p : data.getLivePlayers())
					{
						if(!player.getUniqueId().equals(p.getUniqueId()) && LinmaluMath.distance(p.getLocation(), player.getLocation()) < 15)
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, false, false), true);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 1, false, false), true);
							p.sendMessage(gameItem.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템 효과에 걸렸습니다.");
						}
					}
					break;
				case 어둠:
					for(Player p : data.getLivePlayers())
					{
						if(!player.getUniqueId().equals(p.getUniqueId()) && LinmaluMath.distance(p.getLocation(), player.getLocation()) < 15)
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, false, false), true);
							p.sendMessage(gameItem.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템 효과에 걸렸습니다.");
						}
					}
					break;
				case 총:
					new MiniGameShoot7(player);
					message = false;
					break;
				default:
					return;
			}
			if(remove)
			{
				if(item.getAmount() > 1)
				{
					item.setAmount(item.getAmount() - 1);
				}
				else
				{
					player.getInventory().remove(item);
				}
			}
			if(cooldown > 0)
			{
				new Cooldown(cooldown, player, false);
			}
			if(message)
			{
				player.sendMessage(gameItem.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템을 사용했습니다.");
			}
		}
	}

	protected enum GameItem
	{
		공기(Material.AIR), 속도(Material.IRON_INGOT), 점프(Material.GOLD_INGOT), 투명(Material.DIAMOND), 중력(Material.EMERALD), 모루(Material.ANVIL), 양털가위(Material.SHEARS), 눈덩이(Material.SNOW_BALL), 이동(Material.NETHER_STAR), 주먹(Material.GOLD_SPADE), 가위(Material.GOLD_PICKAXE), 보(Material.GOLD_AXE), 느림(Material.STRING), 어둠(Material.INK_SACK), 삽(Material.DIAMOND_SPADE), 총(Material.GOLD_HOE), 폭탄(Material.TNT), 도끼(Material.DIAMOND_AXE), 곡괭이(Material.DIAMOND_PICKAXE);

		private ItemStack item;

		private GameItem(Material material)
		{
			item = new ItemStack(material);
			if(material != Material.AIR)
			{
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(ChatColor.GREEN + toString());
				im.setUnbreakable(true);
				item.setItemMeta(im);
			}
		}
		public ItemStack getItemStack()
		{
			return item;
		}
		public boolean equalsItemStack(ItemStack item)
		{
			return this.item.getType() == item.getType() && item.getItemMeta().hasDisplayName() && this.item.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName());
		}
		public static GameItem getGameItem(ItemStack item)
		{
			for(GameItem gameItem : values())
			{
				if(gameItem.equalsItemStack(item))
				{
					return gameItem;
				}
			}
			return 공기;
		}
		public static void addItemStack(Player player, GameItem ... gameItems)
		{
			for(GameItem gameItem : gameItems)
			{
				for(ItemStack item : player.getInventory().getContents())
				{
					if(item != null && item.getType() == gameItem.getItemStack().getType() && item.getAmount() < 100)
					{
						item.setAmount(item.getAmount() + 1);
						return;
					}
				}
				player.getInventory().addItem(gameItem.getItemStack());
			}
		}
		public static void setItemStack(Player player, GameItem ... gameItems)
		{
			for(int i = 0; i < gameItems.length; i++)
			{
				player.getInventory().setItem(i, gameItems[i].getItemStack());
			}
		}
	}
}
