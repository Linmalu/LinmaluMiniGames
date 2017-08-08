package com.linmalu.minigames.game001;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//모루피하기
public class MiniGameUtil1 extends MiniGameUtil
{
	public MiniGameUtil1(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.MAP_DEFAULT_SIZE, 10);
		configs.put(ConfigData.MAP_PLAYER_SIZE, 1);
		configs.put(ConfigData.MAP_HEIGHT, 20);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.IRON_BLOCK);
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
	public void runTimer(GameTimer timer)
	{
		MapData md = data.getMapData();
		int time = timer.getTime();
		for(int i = 0; i < time / 100 + 1; i++)
		{
			Random ran = new Random();
			FallingBlock fb = md.getWorld().spawnFallingBlock(data.getMapData().getRandomLocation(), new MaterialData(Material.ANVIL));
			float x1, z1;
			x1 = ran.nextFloat();
			if(ran.nextInt(2) == 0)
			{
				x1 *= -1;
			}
			z1 = ran.nextFloat();
			if(ran.nextInt(2) == 0)
			{
				z1 *= -1;
			}
			if(time / 10 > 30 && ran.nextInt(2) == 0)
			{
				fb.setVelocity(new Vector(x1, 0, z1));
			}
		}
		if(time / 10 == 60)
		{
			md.getWorld().setTime(13000L);
		}
		else if(time / 10 == 90)
		{
			md.getWorld().setTime(18000L);
		}
	}
	@Override
	public void endTimer()
	{
	}
}
