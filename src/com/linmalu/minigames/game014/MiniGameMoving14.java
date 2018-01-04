package com.linmalu.minigames.game014;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.util.Vector;

import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.linmalu.library.api.LinmaluMath;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MapData;

public class MiniGameMoving14 implements Runnable
{
	private final GameData data = Main.getMain().getGameData();
	public final HashMap<Sheep, Location> sheeps = new HashMap<>();
	private final int taskId;

	public MiniGameMoving14()
	{
		for(int i = 0; i < data.getPlayerAllCount() * 10; i++)
		{
			Location loc = data.getMapData().getRandomLocation();
			loc.setY(50);
			for(int y = loc.getWorld().getMaxHeight(); y > 0; y--)
			{
				if(loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getType() != Material.AIR)
				{
					loc.setY(y + 1);
					break;
				}
			}
			Sheep sheep = data.getMapData().getWorld().spawn(loc, Sheep.class);
			sheep.setSilent(true);
			// sheep.setAI(false);
			sheep.setTicksLived(100);
			data.addEntity(sheep);
			sheeps.put(sheep, sheep.getLocation().clone());
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
					MapData md = data.getMapData();
					if(LinmaluMath.distance(sheeps.get(sheep), sheep.getLocation()) > 5 || sheep.getTicksLived() > 100)
					{
						if(sheep.getTicksLived() > 130 && sheep.getTicksLived() < 200)
						{
							continue;
						}
						Location loc = sheep.getLocation();
						loc.setYaw(new Random().nextInt(360));
						sheep.teleport(loc);
						sheep.setTicksLived(new Random().nextInt(200) + 1);
						sheeps.put(sheep, sheep.getLocation().clone());
					}
					if(!sheep.getLocation().toVector().setY(0).isInAABB(new Vector(md.getX1() + 0.5D, 0, md.getZ1() + 0.5D), new Vector(md.getX2() + 0.5D, 1, md.getZ2() + 0.5D)))
					{
						Location loc = sheep.getLocation();
						loc.setYaw((float)LinmaluMath.yawAngle(new Location(sheep.getWorld(), md.getX2() - ((md.getX2() - md.getX1()) / 2) + 0.5D, md.getMapHeight() + 1, md.getZ2() - ((md.getZ2() - md.getZ1()) / 2) + 0.5D), sheep.getLocation()));
						sheep.teleport(loc);
					}
					sheep.setVelocity(sheep.getLocation().getDirection().multiply(0.25D).setY(0));
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
