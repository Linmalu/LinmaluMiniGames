package com.linmalu.linmaluminigames.game002;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MapData;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.game.MiniGameUtil;

public class MiniGameUtil2 extends MiniGameUtil
{
	public MiniGameUtil2(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 등 반 게 임 ] = = = = =",
				"등반 게임은 쌓이는 블록을 올라가는 게임입니다.",
				"서로 공격할 수 있습니다.",
				"블록은 부실 수 있으며 부실 경우 아이템이 나옵니다.",
				"목표 위치까지 먼저 올라가는 플레이어가 승리하게 됩니다."
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
					if(y == 10 || (x == md.getX1() || x == md.getX2() || z == md.getZ1() || z == md.getZ2()))
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
	public MapData getMapData(World world)
	{
		size = 15;
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 30;
		time = 0;
		cooldown = 0;
		topScore = true;
		score = mapHeight - 10;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		new MiniGameFallingBlock2();
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
}
