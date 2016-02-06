package com.linmalu.minigames.game002;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil2 extends MiniGameUtil
{
	public MiniGameUtil2(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 등 반 게 임 ] = = = = =",
				"등반 게임은 쌓이는 블록을 올라가는 게임입니다.",
				"서로 공격할 수 있습니다.",
				"블록은 부실 수 있으며 부실 경우 아이템이 나옵니다.",
				"목표 위치까지 먼저 올라가는 플레이어가 승리하게 됩니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		cooldown = 0;
		topScore = true;
		score = mapHeight - 10;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		int mapX1 = md.getX1() - 1;
		int mapX2 = md.getX2() + 1;
		int mapZ1 = md.getZ1() - 1;
		int mapZ2 = md.getZ2() + 1;
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			for(int x = mapX1; x <= mapX2; x++)
			{
				for(int z = mapZ1; z <= mapZ2; z++)
				{
					if(y == 10 || (x == mapX1 || x == mapX2 || z == mapZ1 || z == mapZ2))
					{
						Block block = md.getWorld().getBlockAt(x, y, z);
						if(y == md.getMapHeight())
						{
							block.setType(Material.REDSTONE_BLOCK);
							block.setData((byte) 0);
						}
						else
						{
							block.setType(Material.QUARTZ_BLOCK);
							block.setData((byte) 1);
						}
					}
				}
			}
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
		switch(new Random().nextInt(10))
		{
		case 0:
			GameItem.addItemStack(player, GameItem.투명);
			break;
		case 1:
			GameItem.addItemStack(player, GameItem.이동);
			break;
		case 2:
		case 3:
		case 4:
			GameItem.addItemStack(player, GameItem.점프);
			break;
		default:
			GameItem.addItemStack(player, GameItem.눈덩이);
			break;
		}
	}
	@Override
	public void reloadConfig() throws IOException
	{
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		if(!file.exists())
		{
			config.set(MAP_DEFAULT, 5);
			config.set(MAP_PLAYER, 1);
			config.set(MAP_HEIGHT, 20);
		}
		mapDefault = config.getInt(MAP_DEFAULT);
		mapPlayer = config.getInt(MAP_PLAYER);
		mapHeight = config.getInt(MAP_HEIGHT) + 10;
		config.save(file);
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.양털가위);
		}
	}
	@Override
	@SuppressWarnings("deprecation")
	public void runTimer(GameTimer timer)
	{
		MapData md = data.getMapData();
		Random ran = new Random();
		for(int i = 0; i < timer.getTime() / 20 / 30 + 2; i++)
		{
			Location loc = data.getMapData().getRandomBlockLocation();
			if(loc.getBlock().isEmpty())
			{
				if(ran.nextInt(5) == 0)
				{
					md.getWorld().spawnFallingBlock(loc, Material.WOOL, (byte)ran.nextInt(16));
				}
				else
				{
					md.getWorld().spawnFallingBlock(loc, Material.STAINED_CLAY, (byte)ran.nextInt(16));
				}
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
