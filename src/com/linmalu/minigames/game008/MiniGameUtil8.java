package com.linmalu.minigames.game008;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil8 extends MiniGameUtil
{
	public MiniGameUtil8(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 폭 탄 피 하 기 게 임 ] = = = = =", "폭탄피하기 게임은 폭탄을 피해서 도망가는 게임입니다.", "폭탄이 된 사람에게 맞으면 폭탄을 넘겨받게 됩니다.", "제한시간이 지나면 폭탄이 터지며 탈락합니다.", "1명이 남을 때까지 게임이 진행됩니다."});
		mapDefault = 20;
		mapPlayer = 0;
		timeDefault = 10;
		timePlayer = 0;
		cooldown = 5;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.REDSTONE_BLOCK);
		}
		return new MaterialData(Material.AIR);
		// cd.setRegion(0, MAP_DEFAULT_HEIGHT, 0, 16, MAP_DEFAULT_HEIGHT + 1, 16, Material.REDSTONE_BLOCK);
		// return cd;
	}
	@Override
	public void addRandomItem(Player player)
	{
		GameItem.setItemStack(player, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄);
		player.getInventory().setHelmet(GameItem.폭탄.getItemStack());
	}
	// @Override
	// public void reload() throws IOException
	// {
	// if(!config.contains(minigame.toString()))
	// {
	// config.set(getConfigPath(MAP_DEFAULT_SIZE), 5);
	// config.set(getConfigPath(MAP_PLAYER_SIZE), 1);
	// config.set(getConfigPath(TIME_DEFAULT), 10);
	// config.set(getConfigPath(TIME_PLAYER), 0);
	// }
	// mapDefault = config.getInt(getConfigPath(MAP_DEFAULT_SIZE));
	// mapPlayer = config.getInt(getConfigPath(MAP_PLAYER_SIZE));
	// timeDefault = config.getInt(getConfigPath(TIME_DEFAULT));
	// timePlayer = config.getInt(getConfigPath(TIME_PLAYER));
	// x2 = z2 = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
	// time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer));
	// cooldown = 3;
	// }
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
			loc.getWorld().createExplosion(loc, 4F, false);
			data.diePlayer(player.getUniqueId());
		}
	}
}
