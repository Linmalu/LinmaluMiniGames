package com.linmalu.minigames.game014;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil14 extends MiniGameUtil
{
	public MiniGameUtil14(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 진 짜 를 찾 아 라 게 임 ] = = = = =", "진짜를찾아라 게임은 진짜를 찾는 게임입니다.", "가짜를 공격했을 경우 반투명이 되며 5초 후에 풀립니다.", "제한시간 안에 점수가 높은 플레이어가 승리합니다."});
		mapDefault = 15;
		mapPlayer = 2;
		timeDefault = 180;
		timePlayer = 0;
		scoreDefault = 10;
		scorePlayer = 1;
		see = true;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.WOOD);
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
		new MiniGameMoving14();
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
