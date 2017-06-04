package com.linmalu.minigames.game007;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil7 extends MiniGameUtil
{
	public MiniGameUtil7(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 총 싸 움 게 임 ] = = = = =", "총싸움 게임은 총을 쏘는 게임입니다.", "총은 우 클릭으로 쏠 수 있습니다.", "총의 쿨타임은 1초입니다.", "총에 맞을 경우 무작위 한 위치에서 재시작합니다.", "제한시간 안에 점수가 높은 플레이어가 승리합니다."});
		timeDefault = 180;
		timePlayer = 10;
		score = 0;
		see = true;
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
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총, GameItem.총);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		if(timer.getTime() % 10 == 0)
		{
			for(Player player : data.getPlayers())
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false), true);
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 1, false, false), true);
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
