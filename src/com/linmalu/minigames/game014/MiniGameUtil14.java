package com.linmalu.minigames.game014;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil14 extends MiniGameUtil
{
	public MiniGameUtil14(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 진 짜 를 찾 아 라 게 임 ] = = = = =",
				"진짜를찾아라 게임은 진짜를 찾는 게임입니다.",
				"가짜를 공격했을 경우 반투명이 되며 5초 후에 풀립니다.",
				"제한시간 안에 점수가 높은 플레이어가 승리합니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 11;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		cooldown = 0;
		topScore = true;
		score = 0;
		see = true;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void createGameMap()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					if(y == 10)
					{
						Block block = md.getWorld().getBlockAt(x, y, z);
						block.setType(Material.WOOD);
					}
				}
			}
		}
		WorldBorder wb = md.getWorld().getWorldBorder();
		wb.setCenter(0.5, 0.5);
		wb.setSize(Math.abs(md.getX1()) + Math.abs(md.getX2()) + 1);
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	@Override
	public void reloadConfig() throws IOException
	{
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		if(!file.exists())
		{
			config.set(MAP_DEFAULT, 10);
			config.set(MAP_PLAYER, 2);
			config.set(TIME_DEFAULT, 180);
			config.set(TIME_PLAYER, 10);
		}
		mapDefault = config.getInt(MAP_DEFAULT);
		mapPlayer = config.getInt(MAP_PLAYER);
		timeDefault = config.getInt(TIME_DEFAULT);
		timePlayer = config.getInt(TIME_PLAYER);
		config.save(file);
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
