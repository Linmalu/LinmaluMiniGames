package com.linmalu.minigames.game005;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerEntityHeadRotation;
import com.comphenix.packetwrapper.WrapperPlayServerEntityTeleport;
import com.linmalu.library.api.LinmaluMath;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.PlayerData;

public class MiniGameMoving5 implements Runnable
{
	private int delay = 10;
	private final GameData data = Main.getMain().getGameData();
	private final Player player;
	private final Location loc;
	private int count;

	public MiniGameMoving5(Player player, Location loc)
	{
		this.player = player;
		this.loc = loc;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), this, delay);
	}
	public void run()
	{
		try
		{
			if(data.isGame2())
			{
				if(data.getPlayerData(player.getUniqueId()).isLive())
				{
					if(data.getPlayerEntitys().get(player.getUniqueId()).size() > count)
					{
						Entity e = data.getPlayerEntitys().get(player.getUniqueId()).get(count);
						if(!e.isDead())
						{
							WrapperPlayServerEntityTeleport tp = new WrapperPlayServerEntityTeleport();
							tp.setEntityID(e.getEntityId());
							tp.setX(loc.getX());
							tp.setY(loc.getY());
							tp.setZ(loc.getZ());
							tp.setYaw(loc.getYaw());
							tp.setPitch(loc.getPitch());
							tp.setOnGround(e.isOnGround());
							WrapperPlayServerEntityHeadRotation head = new WrapperPlayServerEntityHeadRotation();
							head.setEntityID(e.getEntityId());
							head.setHeadYaw((byte)(loc.getYaw() * 256 / 360));
							for(Player p : data.getPlayers())
							{
								tp.sendPacket(p);
								head.sendPacket(p);
								PlayerData pd = data.getPlayerData(p.getUniqueId());
								if(count > 0 && pd != null && pd.isLive() && LinmaluMath.distance(p.getLocation(), loc) < 1)
								{
									data.diePlayer(p.getUniqueId());
								}
							}
							count++;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), this, delay);
						}
					}
				}
				else
				{
					data.getPlayerEntitys().get(player.getUniqueId()).forEach(Entity::remove);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
