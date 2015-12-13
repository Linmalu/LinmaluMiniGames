package com.linmalu.MiniGames.Game07;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;

public class MG07_Shoot implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();
	private Snowball snowball;
	private Vector shoot;

	public MG07_Shoot(Player player)
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
