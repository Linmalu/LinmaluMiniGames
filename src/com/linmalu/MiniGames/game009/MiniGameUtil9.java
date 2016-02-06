package com.linmalu.minigames.game009;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil9 extends MiniGameUtil
{
	public MiniGameUtil9(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 양 털 찾 기 게 임 ] = = = = =",
				"양털찾기 게임은 제한시간 안에 양털을 찾는 게임입니다.",
				"제한시간이 되면 지정되지 않은 양털은 모두 사라집니다.",
				"떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 10;
		int time = 5 * 20;
		cooldown = 2 * 20;
		topScore = false;
		score = 0;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		for(int x = md.getX1(); x <= md.getX2(); x++)
		{
			for(int z = md.getZ1(); z <= md.getZ2(); z++)
			{
				Block block = md.getWorld().getBlockAt(x, md.getMapHeight(), z);
				block.setType(Material.WOOL);
				block.setData((byte)0);
			}
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
			config.set(MAP_DEFAULT, 5);
			config.set(MAP_PLAYER, 1);
		}
		mapDefault = config.getInt(MAP_DEFAULT);
		mapPlayer = config.getInt(MAP_PLAYER);
		config.save(file);
	}
	@Override
	public void startTimer()
	{
		new MiniGameChangeBlock9();
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
	@SuppressWarnings("deprecation")
	public void endTimer()
	{
		MapData md = data.getMapData();
		for(int x = md.getX1(); x <= md.getX2(); x++)
		{
			for(int z = md.getZ1(); z <= md.getZ2(); z++)
			{
				Block block = md.getWorld().getBlockAt(x, md.getMapHeight(), z);
				if(block.getData() != data.getTargetNumber())
				{
					block.setType(Material.AIR);
				}
			}
		}
		if(md.getTime() > 30)
		{
			md.setTime(md.getTime() - 5);
		}
	}
}
