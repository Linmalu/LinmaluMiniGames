package com.linmalu.minigames.game002;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil2 extends MiniGameUtil
{
	public MiniGameUtil2(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 등 반 게 임 ] = = = = =", "등반 게임은 쌓이는 블록을 올라가는 게임입니다.", "서로 공격할 수 있습니다.", "블록은 부실 수 있으며 부실 경우 아이템이 나옵니다.", "목표 위치까지 먼저 올라가는 플레이어가 승리하게 됩니다."});
		mapDefault = 6;
		mapPlayer = 1;
		mapHeight = 20;
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
	public MapData getMapData(World world)
	{
		topScore = true;
		return new MapData(world, x1, z1, x2, z2, mapHeight >= 0 ? mapHeight : MAP_DEFAULT_HEIGHT, time, cooldown, topScore, mapHeight - MAP_DEFAULT_HEIGHT, see);
	}
	@Override
	public void addRandomItem(Player player)
	{
		switch(new Random().nextInt(10))
		{
			case 0:
				GameItem.addItemStack(player, GameItem.투명);
				break;
			case 1:
				GameItem.addItemStack(player, GameItem.이동);
				break;
			case 2:
			case 3:
			case 4:
				GameItem.addItemStack(player, GameItem.점프);
				break;
			default:
				GameItem.addItemStack(player, GameItem.눈덩이);
				break;
		}
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.양털가위);
		}
	}
	@Override
	@SuppressWarnings("deprecation")
	public void runTimer(GameTimer timer)
	{
		MapData md = data.getMapData();
		Random ran = new Random();
		for(int i = 0; i < timer.getTime() / 10 / 30 + 2; i++)
		{
			Location loc = data.getMapData().getRandomLocation();
			if(loc.getBlock().isEmpty())
			{
				if(ran.nextInt(5) == 0)
				{
					md.getWorld().spawnFallingBlock(loc, Material.WOOL, (byte)ran.nextInt(16));
				}
				else
				{
					md.getWorld().spawnFallingBlock(loc, Material.STAINED_CLAY, (byte)ran.nextInt(16));
				}
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
