package com.linmalu.minigames.game000;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGames;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil0 extends MiniGameUtil
{
	public MiniGameUtil0(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 달 리 기 게 임 ] = = = = =",
				"달리기 게임은 자신의 아래에 있는 블록이 시간이 지나면 떨어집니다.",
				"서로 공격할 수 있습니다.",
				"아이템을 우 클릭 시 스킬이 사용되며 쿨타임은 30초입니다.",
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
				block.setType(Material.STAINED_GLASS);
				block.setData((byte)0);
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 20 + (Main.getMain().getGameData().getPlayerAllCount() * 2);
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
		for(Player player : data.getLivePlayers())
		{
			new MiniGameNoMoving0(player);
			GameItem.setItemStack(player, GameItem.속도, GameItem.점프, GameItem.투명, GameItem.중력);
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
