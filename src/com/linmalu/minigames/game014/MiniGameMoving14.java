package com.linmalu.minigames.game014;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.linmalu.library.api.LinmaluMath;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;

public class MiniGameMoving14 implements Runnable
{
	private final GameData data = Main.getMain().getGameData();
	public final HashMap<Sheep, Location> sheeps = new HashMap<>();
	private final int taskId;

	public MiniGameMoving14()
	{
		for(int i = 0; i < data.getPlayerAllCount() * 10; i++)
		{
			Sheep sheep = data.getMapData().getWorld().spawn(data.getMapData().getRandomLocation(), Sheep.class);
			sheep.setTicksLived(100);
			data.addEntity(sheep);
			sheeps.put(sheep, null);
		}
		for(Player player : data.getLivePlayers())
		{
			WrapperPlayServerSpawnEntityLiving packet = new WrapperPlayServerSpawnEntityLiving(player);
			packet.setType(EntityType.SHEEP);
			for(Player p : data.getLivePlayers())
			{
				if(player != p)
				{
					packet.sendPacket(p);
				}
			}
		}
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 2L);
	}
	@Override
	public void run()
	{
		if(data.isGame2())
		{
			for(Sheep sheep : sheeps.keySet())
			{
				if(!sheep.isDead())
				{
					if(sheep.getLocation().getY() == data.getMapData().getMapHeight())
					{
						Location loc = sheeps.get(sheep);
						if(loc != null && loc.distance(sheep.getLocation()) > 1 && sheep.getTicksLived() > 100)
						{
							float angle = (float)LinmaluMath.yawAngle(loc, sheep.getLocation());
							loc = sheep.getLocation();
							loc.setYaw(angle);
							loc.setPitch(0);
							sheep.teleport(loc);
							sheep.setVelocity(loc.getDirection().multiply(0.2F));
						}
						else
						{
							loc = data.getMapData().getRandomLocation();
							sheeps.put(sheep, loc);
						}
					}
					else
					{
						sheeps.put(sheep, null);
					}
				}
			}
		}
		else
		{
			sheeps.clear();
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
