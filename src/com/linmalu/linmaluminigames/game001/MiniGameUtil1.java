package com.linmalu.linmaluminigames.game001;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MapData;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.game.MiniGameUtil;

public class MiniGameUtil1 extends MiniGameUtil
{
	public MiniGameUtil1(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 모 루 피 하 기 게 임 ] = = = = =",
				"모루피하기 게임은 하늘에서 떨어지는 모루를 피하는 게임입니다.",
				"시간이 지날수록 떨어지는 블록은 늘어납니다.",
				"모루 맞으면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		for(int y = 10; y < md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					if(y == 10 || (x == md.getX1() || x == md.getX2() || z == md.getZ1() || z == md.getZ2()))
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
	public MapData getMapData(World world)
	{
		size = 20;
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 30;
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
		new MiniGameFallingBlock1();
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루);
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
