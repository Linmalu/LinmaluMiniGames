package com.linmalu.minigames.game010;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

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
		if(checkEvent(event.getVehicle().getWorld()) && event.getVehicle().getType() == EntityType.MINECART)
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
		Vehicle vehicle = event.getVehicle();
		if(checkEvent(vehicle.getWorld()) && vehicle.getType() == EntityType.MINECART)
		{
			Entity entity = vehicle.getPassenger();
			if(entity != null)
			{
				entity.leaveVehicle();
			}
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()) && event.getEntity().isInsideVehicle())
		{
			event.setCancelled(true);
		}
	}
}