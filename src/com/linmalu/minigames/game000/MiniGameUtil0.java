package com.linmalu.minigames.game000;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//달리기
public class MiniGameUtil0 extends MiniGameUtil
{
	public MiniGameUtil0(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.MAP_DEFAULT_SIZE, 20);
		configs.put(ConfigData.MAP_PLAYER_SIZE, 2);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.STAINED_GLASS);
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
			new MiniGameNoMoving0(player);
			ItemData.setItemStack(player, ItemData.속도, ItemData.점프, ItemData.투명, ItemData.중력);
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
