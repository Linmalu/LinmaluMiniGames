package com.linmalu.minigames.game009;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil9 extends MiniGameUtil
{
	public MiniGameUtil9(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 양 털 찾 기 게 임 ] = = = = =", "양털찾기 게임은 제한시간 안에 양털을 찾는 게임입니다.", "제한시간이 되면 지정되지 않은 양털은 모두 사라집니다.", "떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."});
		mapDefault = 20;
		mapPlayer = 0;
		cooldown = 2;
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
	public MapData getMapData(World world)
	{
		return new MapData(world, x1, z1, x2, z2, mapHeight >= 0 ? mapHeight : MAP_DEFAULT_HEIGHT, 5, cooldown, topScore, score, see);
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
