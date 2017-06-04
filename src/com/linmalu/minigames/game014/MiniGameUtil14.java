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
		timePlayer = 10;
		score = 0;
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
		// cd.setRegion(0, MAP_DEFAULT_HEIGHT, 0, 16, MAP_DEFAULT_HEIGHT + 1, 16, Material.WOOD);
		// return cd;
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	// @Override
	// public void reload() throws IOException
	// {
	// mapDefault = config.getInt(getConfigPath(MAP_DEFAULT_SIZE) , 10);
	// mapPlayer = config.getInt(getConfigPath(MAP_PLAYER_SIZE), 2);
	// timeDefault = config.getInt(getConfigPath(TIME_DEFAULT), 180);
	// timePlayer = config.getInt(getConfigPath(TIME_PLAYER), 10);
	// x2 = z2 = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
	// time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer));
	// topScore = true;
	// see = true;
	// config.remove(minigame.toString());
	// config.set(getConfigPath(MAP_DEFAULT_SIZE), mapDefault);
	// config.set(getConfigPath(MAP_PLAYER_SIZE), mapPlayer);
	// config.set(getConfigPath(TIME_DEFAULT), timeDefault);
	// config.set(getConfigPath(TIME_PLAYER), timePlayer);
	// }
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
