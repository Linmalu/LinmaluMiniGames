package com.linmalu.minigames.game004;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGames;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil4 extends MiniGameUtil
{
	public MiniGameUtil4(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 땅 파 기 게 임 ] = = = = =",
				"땅파기 게임은 블록을 부셔서 다른 플레이어를 떨어트리는 게임입니다.",
				"서로 공격할 수 있습니다.",
				"블록을 부시면 일정 확률로 아이템이 나옵니다.",
				"떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		});
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
					if(y == 10 || y == 13 || y == 16)
					{
						block.setType(Material.SNOW_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 10 + (Main.getMain().getGameData().getPlayerAllCount() * 2);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 20;
		time = 0;
		cooldown = 0;
		topScore = false;
		score = 0;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.삽);
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
}
