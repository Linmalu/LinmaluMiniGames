package com.linmalu.minigames.game009;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGames;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil9 extends MiniGameUtil
{
	public MiniGameUtil9(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 양 털 찾 기 게 임 ] = = = = =",
				"양털찾기 게임은 제한시간 안에 양털을 찾는 게임입니다.",
				"제한시간이 되면 지정되지 않은 양털은 모두 사라집니다.",
				"떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		for(int x = md.getX1(); x <= md.getX2(); x++)
		{
			for(int z = md.getZ1(); z <= md.getZ2(); z++)
			{
				Block block = md.getWorld().getBlockAt(x, md.getMapHeight(), z);
				block.setType(Material.WOOL);
				block.setData((byte)0);
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 15;
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 10;
		time = 5 * 20;
		cooldown = 2 * 20;
		topScore = false;
		score = 0;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
