package com.linmalu.minigames.game004;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;
import com.linmalu.minigames.types.ConfigType;

//땅파기
public class MiniGameUtil4 extends MiniGameUtil
{
	public MiniGameUtil4(MiniGame minigame)
	{
		super(minigame);
		setConfigData(ConfigType.MAP_HEIGHT, 3);
		setConfigData(ConfigType.MAP_DEFAULT_SIZE, 10);
		setConfigData(ConfigType.MAP_PLAYER_SIZE, 2);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(MAP_DEFAULT_HEIGHT <= y && y <= MAP_DEFAULT_HEIGHT + (getConfigInt(ConfigType.MAP_HEIGHT) * 3) && (y - MAP_DEFAULT_HEIGHT) % 3 == 0)
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
				ItemData.addItemStack(player, ItemData.속도);
				break;
			case 1:
				ItemData.addItemStack(player, ItemData.점프);
				break;
			case 2:
				ItemData.addItemStack(player, ItemData.투명);
				break;
			case 3:
				ItemData.addItemStack(player, ItemData.느림);
				break;
			case 4:
				ItemData.addItemStack(player, ItemData.어둠);
				break;
			case 5:
				ItemData.addItemStack(player, ItemData.이동);
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
			ItemData.setItemStack(player, ItemData.삽);
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
