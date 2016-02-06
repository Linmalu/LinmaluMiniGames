package com.linmalu.minigames.game005;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import com.linmalu.library.api.LinmaluMath;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.PlayerData;

public class MiniGameMoving5 implements Runnable
{
	private int taskId;
	private Player player;
	private int count;
	private ArrayList<Sheep> sheeps = new ArrayList<>();
	private Location location;

	public MiniGameMoving5(Player player)
	{
		this.player = player;
		count = 0;
		location = player.getLocation();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public void run()
	{
		GameData data = Main.getMain().getGameData();
		if(data.isGame2() && data.getPlayerData(player.getUniqueId()).isLive())
		{
			count++;
			Location loc = player.getLocation();
			loc.setPitch(10);
			player.setVelocity(loc.getDirection().multiply(0.3));
			float speed = 0.3F;
			boolean one = true;
			for(Sheep sheep : sheeps)
			{
				if(one)
				{
					one = false;
					continue;
				}
				for(Player p : data.getPlayers())
				{
					PlayerData pd = data.getPlayerData(p.getUniqueId());
					if(pd != null && pd.isLive() && !sheep.isDead() && p.getLocation().distance(sheep.getLocation()) < 1)
					{
						data.diePlayer(p.getUniqueId());
					}
				}
			}
			one = true;
			if(count % 2 == 0)
			{
				for(Sheep sheep : sheeps)
				{
					if(sheep.isDead())
					{
						continue;
					}
					if(one)
					{
						one = false;
						if(player.getLocation().distance(sheep.getLocation()) > 1)
						{
							speed = 0.5F;
						}
					}
					float yaw = (float)LinmaluMath.yawAngle(loc, sheep.getLocation());
					loc = sheep.getLocation();
					loc.setYaw(yaw);
					loc.setPitch(0);
					sheep.teleport(loc);
					sheep.setVelocity(loc.getDirection().multiply(speed));
				}
				if(count % 20 == 0)
				{
					Sheep sheep = player.getWorld().spawn(location, Sheep.class);
					sheep.setCustomName("jeb_");
					sheeps.add(sheep);
					data.addEntity(sheep);
				}
				if(count % 10 == 0)
				{
					location = loc;
				}
			}
		}
		else
		{
			for(Sheep sheep : sheeps)
			{
				sheep.remove();
			}
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
