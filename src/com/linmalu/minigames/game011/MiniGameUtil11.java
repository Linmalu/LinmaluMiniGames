package com.linmalu.minigames.game011;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil11 extends MiniGameUtil
{
	public MiniGameUtil11(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 신 호 등 블 럭 게 임 ] = = = = =", "신호등블록 게임은 블록의 색이 변하여 사라지는 게임입니다.", "블록의 순서는 초록색 -> 노란색 -> 빨간색 -> 사라짐 순서입니다.", "서로 공격할 수 있습니다.", "떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."});
		mapDefault = 10;
		mapPlayer = 2;
	}
	@SuppressWarnings("deprecation")
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.WOOL, (byte)5);
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
	@SuppressWarnings("deprecation")
	public void runTimer(GameTimer timer)
	{
		Block block = data.getMapData().getRandomBlock().getBlock();
		if(block.getType() == Material.WOOL && block.getData() == 5)
		{
			new MiniGameChangeBlock11(block);
		}
	}
	@Override
	public void endTimer()
	{
	}
}
