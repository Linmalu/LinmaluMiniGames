package com.linmalu.minigames.game007;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;
import com.linmalu.minigames.types.ConfigType;

//총싸움
public class MiniGameUtil7 extends MiniGameUtil
{
	public MiniGameUtil7(MiniGame minigame)
	{
		super(minigame);
		setConfigData(ConfigType.TIME_DEFAULT, 180);
		setConfigData(ConfigType.TIME_PLAYER, 0);
		setConfigData(ConfigType.SCORE_DEFAULT, 10);
		setConfigData(ConfigType.SCORE_PLAYER, 1);
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
			ItemData.setItemStack(player, ItemData.총, ItemData.총, ItemData.총, ItemData.총, ItemData.총, ItemData.총, ItemData.총, ItemData.총, ItemData.총);
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
