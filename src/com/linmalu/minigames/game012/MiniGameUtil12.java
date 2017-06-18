package com.linmalu.minigames.game012;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil12 extends MiniGameUtil
{
	public MiniGameUtil12(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 경 마 게 임 ] = = = = =", "경마 게임은 말을 타고 경주하는 게임입니다.", "점프로 가속 스킬을 쓸 수 있습니다.", "떨어지면 체크포인트 지점에서 다시 시작합니다.", "목적지에 먼저 도착하는 플레이어가 승리합니다."});
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		return new MaterialData(Material.AIR);
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	public void startTimer()
	{
		for(int x = 2; x <= 12; x++)
		{
			new Location(data.getMapData().getWorld(), x, 22, 13).getBlock().setType(Material.AIR);
		}
		new MiniGameHorse();
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
