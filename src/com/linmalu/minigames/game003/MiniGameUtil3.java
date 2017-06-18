package com.linmalu.minigames.game003;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil3 extends MiniGameUtil
{
	public MiniGameUtil3(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 눈 치 게 임 ] = = = = =", "눈치 게임은 가위바위보 게임입니다.", "주먹 -> 가위 -> 보 -> 주먹 순서입니다.", "가위바위보 변경은 3초에 한 번씩 변경할 수 있습니다.", "공격을 하면 가위바위보를 하게 되어 승패가 결정됩니다.", "눈치게임에 졌을 경우 10초 동안 게임에 참여할 수 없습니다.", "제한시간 안에 점수가 높은 플레이어가 승리합니다."});
		mapDefault = 5;
		mapPlayer = 1;
		timeDefault = 180;
		timePlayer = 0;
		scoreDefault = 5;
		scorePlayer = 1;
		see = true;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.HAY_BLOCK);
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
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.주먹, GameItem.가위, GameItem.보, GameItem.주먹, GameItem.가위, GameItem.보, GameItem.주먹, GameItem.가위, GameItem.보);
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
