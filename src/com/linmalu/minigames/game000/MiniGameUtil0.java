package com.linmalu.minigames.game000;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil0 extends MiniGameUtil
{
	public MiniGameUtil0(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 달 리 기 게 임 ] = = = = =", "달리기 게임은 자신의 아래에 있는 블록이 시간이 지나면 떨어집니다.", "서로 공격할 수 있습니다.", "아이템을 우 클릭 시 스킬이 사용되며 쿨타임은 30초입니다.", "떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."});
		mapDefault = 20;
		mapPlayer = 2;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.STAINED_GLASS);
		}
		return new MaterialData(Material.AIR);
		// cd.setRegion(0, MAP_DEFAULT_HEIGHT, 0, 16, MAP_DEFAULT_HEIGHT + 1, 16, Material.STAINED_GLASS);
		// return cd;
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	// @Override
	// public void reload()
	// {
	// mapDefault = config.getInt(getConfigPath(MAP_DEFAULT_SIZE), 20);
	// mapPlayer = config.getInt(getConfigPath(MAP_PLAYER_SIZE), 2);
	// x2 = z2 = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
	// time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer));
	// }
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			new MiniGameNoMoving0(player);
			GameItem.setItemStack(player, GameItem.속도, GameItem.점프, GameItem.투명, GameItem.중력);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
	}
	@Override
	public void endTimer()
	{
	}
}
