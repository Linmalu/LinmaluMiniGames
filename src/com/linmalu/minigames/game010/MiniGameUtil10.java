package com.linmalu.minigames.game010;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil10 extends MiniGameUtil
{
	public MiniGameUtil10(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 카 트 타 기 게 임 ] = = = = =", "카트타기 게임은 제한시간 안에 카트를 타는 게임입니다.", "카트는 살아있는 사람 수보다 적게 나옵니다.", "서로 공격할 수 있으며, 카트를 공격하여 탑승자를 내리게 할 수 있습니다.", "제한시간이 지나면 카트에 타지 않은 사람은 탈락합니다.", "최종 1인이 남을 때까지 게임이 진행됩니다."});
		if(!config.contains(minigame.toString()))
		{
			config.set(getConfigPath(MAP_DEFAULT_SIZE), 5);
			config.set(getConfigPath(MAP_PLAYER_SIZE), 1);
			config.set(getConfigPath(TIME_DEFAULT), 10);
			config.set(getConfigPath(TIME_PLAYER), 0);
		}
		mapDefault = 5;
		mapPlayer = 1;
		timeDefault = 10;
		timePlayer = 0;
		mapHeight = 20;
		cooldown = 5;
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
