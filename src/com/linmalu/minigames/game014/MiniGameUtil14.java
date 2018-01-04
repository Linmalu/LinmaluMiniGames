package com.linmalu.minigames.game014;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;
import com.linmalu.minigames.types.ConfigType;

//진짜를찾아라
public class MiniGameUtil14 extends MiniGameUtil
{
	public MiniGameUtil14(MiniGame minigame)
	{
		super(minigame);
		setConfigData(ConfigType.MAP_DEFAULT_SIZE, 15);
		setConfigData(ConfigType.MAP_PLAYER_SIZE, 2);
		setConfigData(ConfigType.TIME_DEFAULT, 180);
		setConfigData(ConfigType.TIME_PLAYER, 0);
		setConfigData(ConfigType.SCORE_DEFAULT, 10);
		setConfigData(ConfigType.SCORE_PLAYER, 1);
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
