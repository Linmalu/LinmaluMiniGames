package com.linmalu.MiniGames.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import com.linmalu.LinmaluLibrary.API.LinmaluActionbar;
import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Game00.MG00_Item;
import com.linmalu.MiniGames.Game00.MG00_World;
import com.linmalu.MiniGames.Game01.MG01_Item;
import com.linmalu.MiniGames.Game01.MG01_World;
import com.linmalu.MiniGames.Game02.MG02_Item;
import com.linmalu.MiniGames.Game02.MG02_World;
import com.linmalu.MiniGames.Game03.MG03_Item;
import com.linmalu.MiniGames.Game03.MG03_World;
import com.linmalu.MiniGames.Game04.MG04_Item;
import com.linmalu.MiniGames.Game04.MG04_World;
import com.linmalu.MiniGames.Game05.MG05_World;
import com.linmalu.MiniGames.Game06.MG06_Item;
import com.linmalu.MiniGames.Game06.MG06_World;
import com.linmalu.MiniGames.Game07.MG07_Item;
import com.linmalu.MiniGames.Game08.MG08_Item;
import com.linmalu.MiniGames.Game08.MG08_World;
import com.linmalu.MiniGames.Game09.MG09_World;
import com.linmalu.MiniGames.Game10.MG10_World;
import com.linmalu.MiniGames.Game11.MG11_World;

public class CreateWorldTimer implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();
	private int count = 10;

	public CreateWorldTimer()
	{
		createWorld();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 20L);
	}
	public void run()
	{
		if(data.isGame1() && count >= 0)
		{
			String message = ChatColor.YELLOW + "°ÔÀÓ¸ÊÀ¸·Î ÀÌµ¿±îÁö " + ChatColor.GOLD + count + ChatColor.YELLOW + "ÃÊÀü";
			for(Player player : data.getPlayers())
			{
				LinmaluActionbar.setMessage(player, message);
			}
			count--;
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
			if(data.isGame1())
			{
				new MoveWorldTimer();
			}
		}
	}
	private void createWorld()
	{
		int x1, x2, z1, z2;
		x1 = x2 = z1 = z2 = 0;
		try
		{
			File file = new File("./" + Main.world + "/region/");
			file.mkdirs();
			int number = 0;
			if(data.getMinigame() == MiniGames.ÃÑ½Î¿ò)
			{
				number = 1;
			}
			else if(data.getMinigame() == MiniGames.°æ¸¶)
			{
				number = 2;
			}
			if(number != 0)
			{
				ReadableByteChannel in = Channels.newChannel(getClass().getResourceAsStream("/com/linmalu/MiniGames/World/world" + number + ".zip"));
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
					Bukkit.broadcastMessage(ChatColor.GREEN + "¸ÊÁ¦ÀÛÀÚ : " + ChatColor.YELLOW + "HGMstudio");
					Bukkit.broadcastMessage(ChatColor.GREEN + "¸ÊÃâÃ³ : " + ChatColor.YELLOW + "http://hgmstudio.tistory.com/category");
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
			Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "¸ÊÀ» »ý¼ºÇÒ ¼ö ¾ø½À´Ï´Ù.");
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
		boolean topScore, see;
		int mapHeight, time, cooldown, score, size;
		int playerCount = data.getPlayerAllCount();
		switch(data.getMinigame())
		{
		case ´Þ¸®±â:
			size = 20 + (data.getPlayerAllCount() * 2);
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 10;
			time = 0;
			cooldown = 0;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG00_World();
			new MG00_Item();
			break;
		case ¸ð·çÇÇÇÏ±â:
			size = 20;
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 30;
			time = 0;
			cooldown = 0;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG01_World();
			new MG01_Item();
			break;
		case µî¹Ý:
			size = 15;
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 30;
			time = 0;
			cooldown = 0;
			topScore = true;
			score = mapHeight - 10;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG02_World();
			new MG02_Item();
			break;
		case ´«Ä¡:
			size = 5 + playerCount;
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 15;
			time = 3 * 60 * 20;;
			cooldown = 0;
			topScore = true;
			score = 0;
			see = true;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG03_World();
			new MG03_Item();
			break;
		case ¶¥ÆÄ±â:
			size = 10 + (playerCount * 2);
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 20;
			time = 0;
			cooldown = 0;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG04_World();
			new MG04_Item();
			break;
		case ²¿¸®ÇÇÇÏ±â:
			size = 20 + (playerCount);
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 20;
			time = 0;
			cooldown = 0;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG05_World();
			break;
		case ¶¥µû¸Ô±â:
			size = 10 + (playerCount * 2);
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 10;
			time = 0;
			cooldown = 0;
			topScore = true;
			score = 200 + (playerCount * 10);
			see = true;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG06_World();
			new MG06_Item();
			break;
		case ÃÑ½Î¿ò:
			mapHeight = 0;
			time = 5 * 60 * 20;
			cooldown = 0;
			topScore = true;
			score = 0;
			see = true;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG07_Item();
			break;
		case ÆøÅºÇÇÇÏ±â:
			size = 20;
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 20;
			time = 10 * 20;
			cooldown = 3 * 20;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG08_World();
			new MG08_Item();
			break;
		case ¾çÅÐÃ£±â:
			size = 15;
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 10;
			time = 5 * 20;
			cooldown = 2 * 20;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG09_World();
			break;
		case Ä«Æ®Å¸±â:
			size = 15;
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 20;
			time = 10 * 20;
			cooldown = 5 * 20;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG10_World();
			break;
		case ½ÅÈ£µîºí·Ï:
			size = 20;
			x1 = z1 = -size;
			x2 = z2 = size;
			mapHeight = 10;
			time = 0;
			cooldown = 0;
			topScore = false;
			score = 0;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			new MG11_World();
			break;
		case °æ¸¶:
			mapHeight = 20;
			time = 0;
			cooldown = 0;
			topScore = true;
			score = 1;
			see = false;
			data.setMapData(new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see));
			world.setTime(18000L);
			break;
		}
	}
}
