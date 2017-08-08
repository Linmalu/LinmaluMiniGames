package com.linmalu.minigames.game010;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//카트타기
public class MiniGameUtil10 extends MiniGameUtil
{
	public MiniGameUtil10(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.MAP_HEIGHT, 20);
		configs.put(ConfigData.MAP_DEFAULT_SIZE, 5);
		configs.put(ConfigData.MAP_PLAYER_SIZE, 1);
		configs.put(ConfigData.TIME_DEFAULT, 10);
		configs.put(ConfigData.TIME_PLAYER, 0);
		configs.put(ConfigData.COOLDOWN, 5);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.GOLD_BLOCK);
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
		MapData md = data.getMapData();
		for(int i = 0; i < data.getPlayerLiveCount() - 1; i++)
		{
			data.addEntity(md.getWorld().spawnEntity(md.getRandomLocation(), EntityType.MINECART));
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
	}
	@Override
	public void endTimer()
	{
		ArrayList<UUID> players = new ArrayList<>();
		for(Entity e : data.getEntitys())
		{
			if(!e.isEmpty())
			{
				e.getPassengers().forEach(entity ->
				{
					if(entity.getType() == EntityType.PLAYER)
					{
						players.add(entity.getUniqueId());
					}
				});
				e.eject();
			}
			e.remove();
		}
		data.getEntitys().clear();
		for(Player player : data.getLivePlayers())
		{
			if(!players.contains(player.getUniqueId()))
			{
				data.diePlayer(player.getUniqueId());
			}
		}
	}
}
