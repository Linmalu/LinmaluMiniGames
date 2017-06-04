package com.linmalu.minigames.game001;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil1 extends MiniGameUtil
{
	public MiniGameUtil1(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 모 루 피 하 기 게 임 ] = = = = =", "모루피하기 게임은 하늘에서 떨어지는 모루를 피하는 게임입니다.", "시간이 지날수록 떨어지는 블록은 늘어납니다.", "모루 맞으면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."});
		mapDefault = 10;
		mapPlayer = 1;
		mapHeight = 20;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.IRON_BLOCK);
		}
		return new MaterialData(Material.AIR);
//		cd.setRegion(0, MAP_DEFAULT_HEIGHT, 0, 16, MAP_DEFAULT_HEIGHT + 1, 16, Material.IRON_BLOCK);
//		return cd;
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
//	@Override
//	public void reload()
//	{
//		mapDefault = config.getInt(getConfigPath(MAP_DEFAULT_SIZE), 5);
//		mapPlayer = config.getInt(getConfigPath(MAP_PLAYER_SIZE), 1);
//		x2 = z2 = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
//		mapHeight = 30;
//		time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer));
//	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루, GameItem.모루);
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void runTimer(GameTimer timer)
	{
		MapData md = data.getMapData();
		int time = timer.getTime();
		for(int i = 0; i < time / 10 / 10 + 1; i++)
		{
			Random ran = new Random();
			FallingBlock fb = md.getWorld().spawnFallingBlock(data.getMapData().getRandomLocation(), Material.ANVIL, (byte)0);
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
