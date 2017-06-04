package com.linmalu.minigames.game004;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil4 extends MiniGameUtil
{
	public MiniGameUtil4(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 땅 파 기 게 임 ] = = = = =", "땅파기 게임은 블록을 부셔서 다른 플레이어를 떨어트리는 게임입니다.", "서로 공격할 수 있습니다.", "블록을 부시면 일정 확률로 아이템이 나옵니다.", "떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."});
		mapDefault = 10;
		mapPlayer = 2;
		mapHeight = 6;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(MAP_DEFAULT_HEIGHT <= y && y <= mapHeight && (y - MAP_DEFAULT_HEIGHT) % 3 == 0)
		{
			return new MaterialData(Material.SNOW_BLOCK);
		}
		return new MaterialData(Material.AIR);
	}
	@Override
	public void addRandomItem(Player player)
	{
		switch(new Random().nextInt(100))
		{
			case 0:
				GameItem.addItemStack(player, GameItem.속도);
				break;
			case 1:
				GameItem.addItemStack(player, GameItem.점프);
				break;
			case 2:
				GameItem.addItemStack(player, GameItem.투명);
				break;
			case 3:
				GameItem.addItemStack(player, GameItem.느림);
				break;
			case 4:
				GameItem.addItemStack(player, GameItem.어둠);
				break;
			case 5:
				GameItem.addItemStack(player, GameItem.이동);
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
			GameItem.setItemStack(player, GameItem.삽);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		if(timer.getTime() % 10 == 0)
		{
			for(Player player : data.getPlayers())
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 300, 0, false, false), true);
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
