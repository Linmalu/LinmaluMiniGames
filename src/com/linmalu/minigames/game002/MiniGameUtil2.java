package com.linmalu.minigames.game002;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;
import com.linmalu.minigames.types.ConfigType;

//등반
public class MiniGameUtil2 extends MiniGameUtil
{
	public MiniGameUtil2(MiniGame minigame)
	{
		super(minigame);
		setConfigData(ConfigType.MAP_DEFAULT_SIZE, 5);
		setConfigData(ConfigType.MAP_PLAYER_SIZE, 1);
		setConfigData(ConfigType.TIME_DEFAULT, 120);
		setConfigData(ConfigType.TIME_PLAYER, 0);
		setConfigData(ConfigType.SCORE_DEFAULT, 20);
		setConfigData(ConfigType.SCORE_PLAYER, 1);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.QUARTZ_BLOCK);
		}
		return new MaterialData(Material.AIR);
	}
	@Override
	public void addRandomItem(Player player)
	{
		switch(new Random().nextInt(50))
		{
			case 0:
				ItemData.addItemStack(player, ItemData.투명);
				break;
			case 1:
				ItemData.addItemStack(player, ItemData.이동);
				break;
			case 2:
				ItemData.addItemStack(player, ItemData.점프);
				break;
			default:
				ItemData.addItemStack(player, ItemData.눈덩이);
				break;
		}
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			ItemData.setItemStack(player, ItemData.양털가위);
		}
	}
	@Override
	@SuppressWarnings("deprecation")
	public void runTimer(GameTimer timer)
	{
		MapData md = data.getMapData();
		Random ran = new Random();
		for(int i = 0; i < timer.getCount() / 100 + 1; i++)
		{
			Location loc = data.getMapData().getRandomLocation();
			loc.setY(MAP_DEFAULT_HEIGHT + md.getScore());
			if(loc.getBlock().isEmpty())
			{
				md.getWorld().spawnFallingBlock(loc, Material.WOOL, (byte)ran.nextInt(16));
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
