package com.linmalu.minigames.game003;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//눈치
public class MiniGameUtil3 extends MiniGameUtil
{
	public MiniGameUtil3(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.MAP_DEFAULT_SIZE, 5);
		configs.put(ConfigData.MAP_PLAYER_SIZE, 1);
		configs.put(ConfigData.TIME_DEFAULT, 180);
		configs.put(ConfigData.TIME_PLAYER, 0);
		configs.put(ConfigData.SCORE_DEFAULT, 5);
		configs.put(ConfigData.SCORE_PLAYER, 1);
		see = true;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.HAY_BLOCK);
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
			ItemData.setItemStack(player, ItemData.주먹, ItemData.가위, ItemData.보, ItemData.주먹, ItemData.가위, ItemData.보, ItemData.주먹, ItemData.가위, ItemData.보);
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
