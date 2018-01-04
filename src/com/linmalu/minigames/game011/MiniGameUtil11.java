package com.linmalu.minigames.game011;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;
import com.linmalu.minigames.types.ConfigType;

//신호등블럭
public class MiniGameUtil11 extends MiniGameUtil
{
	public MiniGameUtil11(MiniGame minigame)
	{
		super(minigame);
		setConfigData(ConfigType.MAP_DEFAULT_SIZE, 10);
		setConfigData(ConfigType.MAP_PLAYER_SIZE, 2);
	}
	@SuppressWarnings("deprecation")
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.WOOL, (byte)5);
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
	}
	@Override
	@SuppressWarnings("deprecation")
	public void runTimer(GameTimer timer)
	{
		Block block = data.getMapData().getRandomLocation().getBlock();
		if(block.getType() == Material.WOOL && block.getData() == 5)
		{
			new MiniGameChangeBlock11(block);
		}
	}
	@Override
	public void endTimer()
	{
	}
}
