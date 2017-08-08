package com.linmalu.minigames.game012;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//경마
public class MiniGameUtil12 extends MiniGameUtil
{
	public MiniGameUtil12(MiniGame minigame)
	{
		super(minigame);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		return new MaterialData(Material.AIR);
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	public void startTimer()
	{
		for(int x = 2; x <= 12; x++)
		{
			new Location(data.getMapData().getWorld(), x, 22, 13).getBlock().setType(Material.AIR);
		}
		new MiniGameHorse();
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
