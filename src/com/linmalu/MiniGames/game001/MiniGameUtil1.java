package com.linmalu.minigames.game001;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil1 extends MiniGameUtil
{
	public MiniGameUtil1(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 모 루 피 하 기 게 임 ] = = = = =",
				"모루피하기 게임은 하늘에서 떨어지는 모루를 피하는 게임입니다.",
				"시간이 지날수록 떨어지는 블록은 늘어납니다.",
				"모루 맞으면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 30;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		cooldown = 0;
		topScore = false;
		score = 0;
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
						block.setType(Material.IRON_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	@Override
	public void reloadConfig() throws IOException
	{
		final String defaultSize = "기본 맵크기";
		final String playerSize = "인원수 비율 맵크기";
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		if(!file.exists())
		{
			config.set(defaultSize, 20);
			config.set(playerSize, 0);
		}
		this.mapDefault = config.getInt(defaultSize);
		this.mapPlayer = config.getInt(playerSize);
		config.save(file);		
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루);
		}
	}
	@Override
	@SuppressWarnings("deprecation")
	public void runTimer(GameTimer timer)
	{
		MapData md = data.getMapData();
		int time = timer.getTime();
		for(int i = 0; i < time / 20 / 10 + 1; i++)
		{
			Random ran = new Random();
			FallingBlock fb = md.getWorld().spawnFallingBlock(data.getMapData().getRandomBlockLocation(), Material.ANVIL, (byte) 0);
			float x1, z1;
			x1 = ran.nextFloat();
			if(ran.nextInt(2) == 0)
			{
				x1 *= -1;
			}
			z1 = ran.nextFloat();
			if(ran.nextInt(2) == 0)
			{
				z1 *= -1;
			}
			if(time / 20 > 30 && ran.nextInt(2) == 0)
			{
				fb.setVelocity(new Vector(x1, 0, z1));
			}
		}
		if(time / 20 == 60)
		{
			md.getWorld().setTime(13000L);
		}
		else if(time / 20 == 90)
		{
			md.getWorld().setTime(18000L);
		}
	}
	@Override
	public void endTimer()
	{
	}
}
