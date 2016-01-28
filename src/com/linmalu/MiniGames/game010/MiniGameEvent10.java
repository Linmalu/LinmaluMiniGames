package com.linmalu.minigames.game010;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent10 extends MiniGameEvent
{
	public MiniGameEvent10(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Test(VehicleEnterEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getVehicle().getWorld().getName().equals(Main.WORLD) && event.getVehicle().getType() == EntityType.MINECART)
		{
			PlayerData pd = data.getPlayerData(event.getEntered().getUniqueId());
			if(pd != null && !pd.isLive())
			{
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void Test(VehicleDestroyEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getVehicle().getWorld().getName().equals(Main.WORLD) && event.getVehicle().getType() == EntityType.MINECART)
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.WORLD))
		{
			event.setCancelled(true);
		}
	}
}