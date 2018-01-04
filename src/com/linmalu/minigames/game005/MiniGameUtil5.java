package com.linmalu.minigames.game005;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.material.MaterialData;

import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;
import com.linmalu.minigames.types.ConfigType;

//꼬리피하기
public class MiniGameUtil5 extends MiniGameUtil
{
	public MiniGameUtil5(MiniGame minigame)
	{
		super(minigame);
		setConfigData(ConfigType.MAP_DEFAULT_SIZE, 20);
		setConfigData(ConfigType.MAP_PLAYER_SIZE, 1);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.ENDER_STONE);
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
			data.getPlayerEntitys().put(player.getUniqueId(), new ArrayList<>());
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		for(Player player : data.getLivePlayers())
		{
			Location loc = player.getLocation();
			loc.setPitch(0);
			player.setVelocity(loc.getDirection().multiply(0.25F).setY(player.getVelocity().getY()));
			if((timer.getTime() + 1) % 20 == 0)
			{
				List<Entity> list = data.getPlayerEntitys().get(player.getUniqueId());
				if(list.size() <= 20)
				{
					Sheep sheep = player.getWorld().spawn(player.getLocation().add(0, -100, 0), Sheep.class);
					sheep.setCustomName("jeb_");
					sheep.setAI(false);
					list.add(sheep);
					data.addEntity(sheep);
					WrapperPlayServerSpawnEntityLiving entity = new WrapperPlayServerSpawnEntityLiving(sheep);
					data.getPlayers().forEach(entity::sendPacket);
				}
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
