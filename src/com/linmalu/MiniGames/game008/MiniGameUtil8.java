package com.linmalu.minigames.game008;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil8 extends MiniGameUtil
{
	public MiniGameUtil8(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 폭 탄 피 하 기 게 임 ] = = = = =",
				"폭탄피하기 게임은 폭탄을 피해서 도망가는 게임입니다.",
				"폭탄이 된 사람에게 맞으면 폭탄을 넘겨받게 됩니다.",
				"제한시간이 지나면 폭탄이 터지며 탈락합니다.",
				"1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 20;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		cooldown = 3 * 20;
		topScore = false;
		score = 0;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
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
					if(y == 10 || (x == md.getX1() || x == md.getX2() || z == md.getZ1() || z == md.getZ2()))
					{
						Block block = md.getWorld().getBlockAt(x, y, z);
						block.setType(Material.REDSTONE_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
		GameItem.setItemStack(player, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄);
		player.getInventory().setHelmet(GameItem.폭탄.getItemStack());
	}
	@Override
	public void reloadConfig() throws IOException
	{
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		if(!file.exists())
		{
			config.set(MAP_DEFAULT, 5);
			config.set(MAP_PLAYER, 1);
			config.set(TIME_DEFAULT, 10);
			config.set(TIME_PLAYER, 0);
		}
		mapDefault = config.getInt(MAP_DEFAULT);
		mapPlayer = config.getInt(MAP_PLAYER);
		timeDefault = config.getInt(TIME_DEFAULT);
		timePlayer = config.getInt(TIME_PLAYER);
		config.save(file);
	}
	@Override
	public void startTimer()
	{
		new MiniGameBombChange8();
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		if(!data.getPlayerData(data.getTargetPlayer()).isLive())
		{
			timer.setTime(0);
		}
	}
	@Override
	public void endTimer()
	{
		Player player = Bukkit.getPlayer(data.getTargetPlayer());
		if(player != null)
		{
			Location loc = player.getLocation();
			loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 4F, false, false);
			data.diePlayer(player.getUniqueId());
		}
	}
}
