package com.linmalu.minigames.game005;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil5 extends MiniGameUtil
{
	public MiniGameUtil5(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 꼬 리 피 하 기 게 임 ] = = = = =", "꼬리피하기 게임은 꼬리에 부딪치지 않고 피하는 게임입니다.", "플레이어는 자동으로 앞으로 움직입니다.", "첫 번째 꼬리에 닿아도 죽지 않습니다.", "부딪치면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."});
		mapDefault = 20;
		mapPlayer = 1;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.ENDER_STONE);
		}
		return new MaterialData(Material.AIR);
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			new MiniGameMoving5(player);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
	}
	@Override
	public void endTimer()
	{
	}
}
