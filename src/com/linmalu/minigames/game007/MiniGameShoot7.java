package com.linmalu.minigames.game007;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;

public class MiniGameShoot7 implements Runnable
{
	private final GameData data = Main.getMain().getGameData();
	private final int taskId;
	private final Snowball snowball;
	private final Vector shoot;

	public MiniGameShoot7(Player player)
	{
		for(Player p : data.getPlayers())
		{
			p.playSound(player.getLocation(), "gun", 5, 1);
			p.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1, 0);
		}
		shoot = player.getLocation().getDirection().multiply(5);
		snowball = player.launchProjectile(Snowball.class, shoot);
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public void run()
	{
		if(data.isGame2() && !snowball.isDead())
		{
			snowball.setVelocity(shoot);
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}
}
