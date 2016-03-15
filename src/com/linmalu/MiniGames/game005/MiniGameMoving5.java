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
	private final GameData data = Main.getMain().getGameData();
	private final ArrayList<Sheep> sheeps = new ArrayList<>();
	private final int taskId;
	private final Player player;
	private int count;

	public MiniGameMoving5(Player player)
	{
		this.player = player;
		count = 0;
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public void run()
	{
		if(data.isGame2() && data.getPlayerData(player.getUniqueId()).isLive())
		{
			count++;
			Location loc = player.getLocation();
			loc.setPitch(10);
			player.setVelocity(loc.getDirection().multiply(0.4F));
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
			for(Sheep sheep : sheeps)
			{
				if(sheep.isDead())
				{
					continue;
				}
				float speed = sheep.getLocation().distance(loc) < 1 ? 0.2F : 0.8F;
				float angle = (float)LinmaluMath.yawAngle(loc, sheep.getLocation());
				loc = sheep.getLocation();
				loc.setYaw(angle);
				loc.setPitch(10);
				sheep.teleport(loc);
				sheep.setVelocity(loc.getDirection().multiply(speed));
			}
			if(count % 40 == 0)
			{
				Sheep sheep = player.getWorld().spawn(loc, Sheep.class);
				sheep.setCustomName("jeb_");
				sheeps.add(sheep);
				data.addEntity(sheep);
			}
		}
		else
		{
			for(Sheep sheep : sheeps)
			{
				sheep.remove();
			}
			sheeps.clear();
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
