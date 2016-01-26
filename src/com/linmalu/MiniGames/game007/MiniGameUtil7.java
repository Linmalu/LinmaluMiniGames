package com.linmalu.minigames.game007;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGames;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil7 extends MiniGameUtil
{
	public MiniGameUtil7(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 총 싸 움 게 임 ] = = = = =",
				"총싸움 게임은 총을 쏘는 게임입니다.",
				"총을 우 클릭으로 쏠 수 있습니다.",
				"총의 쿨타임은 1초입니다.",
				"총에 맞을 경우 10초 동안 게임에 참여할 수 없습니다.",
				"목표 점수에 먼저 도달하는 플레이어가 승리합니다."
		});
	}
	@Override
	public void createGameMap()
	{
	}
	@Override
	public MapData getMapData(World world)
	{
		mapHeight = 0;
		time = 5 * 60 * 20;
		cooldown = 0;
		topScore = true;
		score = 0;
		see = true;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총);
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
