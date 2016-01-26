package com.linmalu.linmaluminigames.game011;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MapData;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.game.MiniGameUtil;

public class MiniGameUtil11 extends MiniGameUtil
{
	public MiniGameUtil11(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 신 호 등 블 럭 게 임 ] = = = = =",
				"신호등블록 게임은 블록의 색이 변하여 사라지는 게임입니다.",
				"블록의 순서는 초록색 -> 노란색 -> 빨간색 -> 사라짐 순서입니다.",
				"서로 공격할 수 있습니다.",
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
				block.setData((byte)5);
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 20;
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 10;
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
		new MiniGameRandomBlock11();
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
