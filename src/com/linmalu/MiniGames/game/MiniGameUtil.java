package com.linmalu.minigames.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Collection;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.Cooldown;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGames;
import com.linmalu.minigames.game000.MiniGameFallingBlock0;
import com.linmalu.minigames.game007.MiniGameShoot7;

public abstract class MiniGameUtil
{
	private final String[] msgs;
	private final File file;
	protected final MiniGames minigame;
	protected boolean topScore, see;
	protected int x1, x2, z1, z2, mapHeight, time, cooldown, score, size;
	protected final GameData data;

	public MiniGameUtil(MiniGames minigame, String[] msgs)
	{
		this.minigame = minigame;
		this.msgs = msgs;
		file = new File(Main.getMain().getDataFolder(), minigame.toString());
		data = Main.getMain().getGameData();
		load();
	}
	public void sendMessage(Collection<Player> players)
	{
		for(Player player : players)
		{
			for(String msg : msgs)
			{
				player.sendMessage(ChatColor.GREEN + msg);
			}
		}		
	}

	protected LinmaluYamlConfiguration load()
	{
		return LinmaluYamlConfiguration.loadConfiguration(file);
	}
	protected void save(LinmaluYamlConfiguration config)
	{
		try
		{
			config.save(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void CreateWrold()
	{
		try
		{
			File file = new File("./" + Main.world + "/region/");
			file.mkdirs();
			int number = -1;
			if(data.getMinigame() == MiniGames.총싸움)
			{
				number = 0;
			}
			else if(data.getMinigame() == MiniGames.경마)
			{
				number = 1;
			}
			if(number != -1)
			{
				ReadableByteChannel in = Channels.newChannel(getClass().getResourceAsStream("/com/linmalu/minigames/world/world" + number + ".zip"));
				WritableByteChannel out = Channels.newChannel(new FileOutputStream("./" + Main.world + "/region/r.0.0.mca"));
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				while(in.read(buffer) != -1)
				{
					buffer.flip();
					out.write(buffer);
					buffer.clear();
				}
				in.close();
				out.close();
				switch(number)
				{
				case 1:
					x1 = 19;
					x2 = 208;
					z1 = 16;
					z2 = 188;
					Bukkit.broadcastMessage(ChatColor.GREEN + "맵제작자 : " + ChatColor.YELLOW + "HGMstudio");
					Bukkit.broadcastMessage(ChatColor.GREEN + "맵출처 : " + ChatColor.YELLOW + "http://hgmstudio.tistory.com/category");
					break;
				case 2:
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
			Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "맵을 생성할 수 없습니다.");
			data.GameStop();
			return;
		}
		World world = WorldCreator.name(Main.world).type(WorldType.FLAT).environment(World.Environment.NORMAL).generator(new ChunkGenerator()
		{
			public byte[] generate(World world, Random random, int x, int z)
			{
				byte[] result = new byte[0];
				return result;
			}
		}).createWorld();
		world.setAutoSave(false);
		world.setGameRuleValue("keepInventory", "true");
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("doFireTick", "false");
		world.setGameRuleValue("doTileDrops", "false");
		world.setGameRuleValue("doMobLoot", "false");
		world.setGameRuleValue("mobGriefing", "false");
		world.setGameRuleValue("naturalRegeneration", "false");
		world.setTime(6000L);
		world.setDifficulty(Difficulty.NORMAL);
		data.setMapData(getMapData(world));
		createGameMap();
	}
	public abstract void createGameMap();
	public abstract MapData getMapData(World world);
	public abstract void initializeMiniGame();
	public abstract void addRandomItem(Player player);
	
	@SuppressWarnings("deprecation")
	public void useItem(Player player, boolean remove, int cooldown)
	{
		GameData data = Main.getMain().getGameData();
		ItemStack item = player.getItemInHand();
		if(item != null && item.getType() != Material.AIR && item.hasItemMeta())
		{
			GameItem gameItem = GameItem.getGameItem(item);
			switch(gameItem)
			{
			case 공기:
				break;
			case 속도:
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, true, false), true);
				break;
			case 점프:
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 3, true, false), true);
				break;
			case 투명:
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, true, false), true);
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
					if(!player.getUniqueId().equals(p.getUniqueId()))
					{
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, true, false), true);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 1, true, false), true);
						p.sendMessage(gameItem.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템 효과에 걸렸습니다.");
					}
				}
				break;
			case 어둠:
				for(Player p : data.getLivePlayers())
				{
					if(!player.getUniqueId().equals(p.getUniqueId()))
					{
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, true, false), true);
						p.sendMessage(gameItem.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템 효과에 걸렸습니다.");
					}
				}
				break;
			case 총:
				new MiniGameShoot7(player);
				break;
			default:
				return;
			}
			if(remove)
			{
				if(item.getAmount() > 1)
				{
					item.setAmount(item.getAmount() -1);
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
			player.sendMessage(gameItem.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템을 사용했습니다.");
		}
	}
	protected enum GameItem
	{
		공기(Material.AIR),
		속도(Material.IRON_INGOT),
		점프(Material.GOLD_INGOT),
		투명(Material.DIAMOND),
		중력(Material.EMERALD),
		모루(Material.ANVIL),
		//		im.setDisplayName(ChatColor.GREEN + "맞으면 아픈 모루");
		눈덩이(Material.SNOW_BALL),
		이동(Material.NETHER_STAR),
		주먹(Material.GOLD_SPADE),
		가위(Material.GOLD_PICKAXE),
		보(Material.GOLD_AXE),
		느림(Material.STRING),
		어둠(Material.INK_SACK),
		삽(Material.DIAMOND_SPADE),
		총(Material.GOLD_HOE),
		폭탄(Material.TNT);

		private ItemStack item;

		private GameItem(Material material)
		{
			item = new ItemStack(material);
			if(material != Material.AIR)
			{
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(ChatColor.GREEN + toString());
				im.spigot().setUnbreakable(true);
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
