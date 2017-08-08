package com.linmalu.minigames.game008;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//폭탄피하기
public class MiniGameUtil8 extends MiniGameUtil
{
	public MiniGameUtil8(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.MAP_DEFAULT_SIZE, 20);
		configs.put(ConfigData.MAP_PLAYER_SIZE, 0);
		configs.put(ConfigData.TIME_DEFAULT, 10);
		configs.put(ConfigData.TIME_PLAYER, 0);
		configs.put(ConfigData.COOLDOWN, 5);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.REDSTONE_BLOCK);
		}
		return new MaterialData(Material.AIR);
	}
	@Override
	public void addRandomItem(Player player)
	{
		ItemData.setItemStack(player, ItemData.폭탄, ItemData.폭탄, ItemData.폭탄, ItemData.폭탄, ItemData.폭탄, ItemData.폭탄, ItemData.폭탄, ItemData.폭탄, ItemData.폭탄);
		player.getInventory().setHelmet(ItemData.폭탄.getItemStack());
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
			loc.getWorld().createExplosion(loc, 4F, false);
			data.diePlayer(player.getUniqueId());
		}
	}
}
