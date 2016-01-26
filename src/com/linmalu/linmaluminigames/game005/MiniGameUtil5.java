package com.linmalu.linmaluminigames.game005;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MapData;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.game.MiniGameUtil;

public class MiniGameUtil5 extends MiniGameUtil
{
	public MiniGameUtil5(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 꼬 리 피 하 기 게 임 ] = = = = =",
				"꼬리피하기 게임은 꼬리에 부딪치지 않고 피하는 게임입니다.",
				"플레이어는 자동으로 앞으로 움직입니다.",
				"첫 번째 꼬리에 닿아도 죽지 않습니다.",
				"부딪치면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap() {
		MapData md = Main.getMain().getGameData().getMapData();
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					Block block = md.getWorld().getBlockAt(x, y, z);
					if(y == 10 || (x == md.getX1() || x == md.getX2() || z == md.getZ1() || z == md.getZ2()))
					{
						block.setType(Material.ENDER_STONE);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 20 + (Main.getMain().getGameData().getPlayerAllCount());
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
			new MiniGameMoving5(player);
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
