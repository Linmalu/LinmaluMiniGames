package com.linmalu.minigames.game005;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil5 extends MiniGameUtil
{
	public MiniGameUtil5(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 꼬 리 피 하 기 게 임 ] = = = = =",
				"꼬리피하기 게임은 꼬리에 부딪치지 않고 피하는 게임입니다.",
				"플레이어는 자동으로 앞으로 움직입니다.",
				"첫 번째 꼬리에 닿아도 죽지 않습니다.",
				"부딪치면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap() {
		MapData md = Main.getMain().getGameData().getMapData();
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					Block block = md.getWorld().getBlockAt(x, y, z);
					if(y == 10 || (x == md.getX1() || x == md.getX2() || z == md.getZ1() || z == md.getZ2()))
					{
						block.setType(Material.ENDER_STONE);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 20;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		cooldown = 0;
		topScore = false;
		score = 0;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		for(Player player : data.getLivePlayers())
		{
			new MiniGameMoving5(player);
		}
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
			config.set(MAP_DEFAULT, 20);
			config.set(MAP_PLAYER, 1);
		}
		mapDefault = config.getInt(MAP_DEFAULT);
		mapPlayer = config.getInt(MAP_PLAYER);
		config.save(file);
	}
}
