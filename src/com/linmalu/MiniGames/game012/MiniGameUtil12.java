package com.linmalu.minigames.game012;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil12 extends MiniGameUtil
{
	public MiniGameUtil12(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 경 마 게 임 ] = = = = =",
				"경마 게임은 말을 타고 경주하는 게임입니다.",
				"점프로 가속 스킬을 쓸 수 있습니다.",
				"떨어지면 시작 지점에서 다시 시작합니다.",
				"목적지에 먼저 도착하는 플레이어가 승리합니다."
		});
	}
	@Override
	public void createGameMap()
	{
	}
	@Override
	public MapData getMapData(World world)
	{
		world.setTime(18000L);
		mapHeight = 20;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		cooldown = 0;
		topScore = true;
		score = 3;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		for(int x = 2; x <= 12; x++)
		{
			new Location(data.getMapData().getWorld(), x, 22, 13).getBlock().setType(Material.AIR);
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	@Override
	public void reloadConfig() throws IOException
	{
	}
}
