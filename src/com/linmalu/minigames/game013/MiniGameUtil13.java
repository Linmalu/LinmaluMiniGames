package com.linmalu.minigames.game013;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil13 extends MiniGameUtil
{
	private final BlockItem[] blockItems = {
			new BlockItem(Material.DIRT, GameItem.삽), new BlockItem(Material.SAND, GameItem.삽), new BlockItem(Material.GRAVEL, GameItem.삽),
			new BlockItem(Material.WOOD, GameItem.도끼), new BlockItem(Material.LOG, GameItem.도끼), new BlockItem(Material.LOG_2, GameItem.도끼),
			new BlockItem(Material.STONE, GameItem.곡괭이), new BlockItem(Material.BRICK, GameItem.곡괭이), new BlockItem(Material.MOSSY_COBBLESTONE, GameItem.곡괭이)
	};

	public MiniGameUtil13(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 블 록 부 수 기 게 임 ] = = = = =",
				"블록부수기 게임은 불록을 빨리 부수는 게임입니다.",
				"블록을 먼저 부수는 플레이어가 승리합니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		x2 = z2 = (int) (Math.ceil(Math.sqrt(data.getPlayerAllCount())) * 3);
		cooldown = 0;
		topScore = true;
		score = mapHeight - 12;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void createGameMap()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		int size = (int) Math.ceil(Math.sqrt(data.getPlayerAllCount())) * 3;
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			int ran = new Random().nextInt(blockItems.length);
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					Block block = md.getWorld().getBlockAt(x, y, z);
					if(x % 3 == 1 && z % 3 == 1 && y != md.getMapHeight())
					{
						if(y != md.getMapHeight() -1 && y != md.getMapHeight() - 2)
						{
							block.setType(blockItems[ran].getMaterial());
						}
					}
					else
					{
						block.setType(Material.BARRIER);
					}
				}
			}
		}
	}
	@Override
	public void moveWorld(Player player)
	{
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		int size = (int)Math.ceil(Math.sqrt(data.getPlayerAllCount()));
		int number = pd.getNumber();
		Location loc = player.getLocation();
		loc.setWorld(data.getMapData().getWorld());
		loc.setX(number % size * 3 + 1.5);
		loc.setY(mapHeight - 2);
		loc.setZ(number / size * 3 + 1.5);
		player.teleport(loc);
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	@Override
	public void reloadConfig() throws IOException
	{
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		if(!file.exists())
		{
			config.set(MAP_HEIGHT, 50);
		}
		mapHeight = config.getInt(MAP_HEIGHT) + 10;
		config.save(file);
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.삽, GameItem.도끼, GameItem.곡괭이, GameItem.삽, GameItem.도끼, GameItem.곡괭이, GameItem.삽, GameItem.도끼, GameItem.곡괭이);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		for(Player player : data.getLivePlayers())
		{
			Location loc = player.getLocation();
			loc.setY(mapHeight - data.getPlayerData(player.getUniqueId()).getScore() - 3);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 4, false, false), true);
			for(BlockItem blockItem : blockItems)
			{
				if(loc.getBlock().getType() == blockItem.getMaterial() && GameItem.getGameItem(player.getItemInHand()) == blockItem.getGameItem())
				{
					player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
					continue;
				}
			}
		}
	}
	@Override
	public void endTimer()
	{
	}

	private class BlockItem
	{
		private Material material;
		private GameItem gameItem;

		private BlockItem(Material material, GameItem gameItem)
		{
			this.material = material;
			this.gameItem = gameItem;
		}
		private Material getMaterial()
		{
			return material;
		}
		private GameItem getGameItem()
		{
			return gameItem;
		}
	}
}
