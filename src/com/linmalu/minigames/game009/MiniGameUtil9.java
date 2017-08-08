package com.linmalu.minigames.game009;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//양털찾기
public class MiniGameUtil9 extends MiniGameUtil
{
	public MiniGameUtil9(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.MAP_DEFAULT_SIZE, 20);
		configs.put(ConfigData.MAP_PLAYER_SIZE, 0);
		configs.put(ConfigData.TIME_DEFAULT, 5);
		configs.put(ConfigData.TIME_PLAYER, 0);
		configs.put(ConfigData.COOLDOWN, 2);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.WOOL);
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
		new MiniGameChangeBlock9(true);
		data.setTargetNumber(new Random().nextInt(16));
		for(Player player : data.getLivePlayers())
		{
			player.getInventory().clear();
			ItemStack item = new ItemStack(Material.WOOL);
			item.setDurability((short)data.getTargetNumber());
			for(int i = 0; i < 9; i++)
			{
				player.getInventory().setItem(i, item);
			}
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
	}
	@Override
	public void endTimer()
	{
		new MiniGameChangeBlock9(false);
		MapData md = data.getMapData();
		if(md.getTime() > 15)
		{
			md.setTime(md.getTime() - 5);
		}
	}
}
