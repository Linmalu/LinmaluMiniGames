package com.linmalu.minigames.data;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import com.linmalu.minigames.Main;

public class PlayFirework implements Runnable
{
	private final Firework firework;

	public PlayFirework(Location location)
	{
		firework = location.getWorld().spawn(location, Firework.class);
		FireworkMeta fwm = firework.getFireworkMeta();
		fwm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(Type.BURST).withColor(Color.RED).withFade(Color.RED).build());
		fwm.setPower(0);
		firework.setFireworkMeta(fwm);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), this, 2L);
	}
	public void run()
	{
		firework.detonate();
	}
}
