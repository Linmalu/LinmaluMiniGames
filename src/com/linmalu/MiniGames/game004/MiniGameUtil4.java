package com.linmalu.minigames.game004;

import java.io.IOException;
import java.util.Random;

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
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil4 extends MiniGameUtil
{
	public MiniGameUtil4(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 땅 파 기 게 임 ] = = = = =",
				"땅파기 게임은 블록을 부셔서 다른 플레이어를 떨어트리는 게임입니다.",
				"서로 공격할 수 있습니다.",
				"블록을 부시면 일정 확률로 아이템이 나옵니다.",
				"떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
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
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					Block block = md.getWorld().getBlockAt(x, y, z);
					if((y - 10) % 3 == 0)
					{
						block.setType(Material.SNOW_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
		switch(new Random().nextInt(100))
		{
		case 0:
			GameItem.addItemStack(player, GameItem.속도);
			break;
		case 1:
			GameItem.addItemStack(player, GameItem.점프);
			break;
		case 2:
			GameItem.addItemStack(player, GameItem.투명);
			break;
		case 3:
			GameItem.addItemStack(player, GameItem.느림);
			break;
		case 4:
			GameItem.addItemStack(player, GameItem.어둠);
			break;
		case 5:
			GameItem.addItemStack(player, GameItem.이동);
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
			config.set(MAP_DEFAULT, 10);
			config.set(MAP_PLAYER, 2);
			config.set(MAP_HEIGHT, 6);
		}
		mapDefault = config.getInt(MAP_DEFAULT);
		mapPlayer = config.getInt(MAP_PLAYER);
		mapHeight = config.getInt(MAP_HEIGHT) * 3;
		config.save(file);
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.삽);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		if(timer.getTime() % 20 == 0)
		{
			for(Player player : data.getPlayers())
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0, true, false), true);
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
