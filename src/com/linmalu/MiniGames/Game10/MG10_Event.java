package com.linmalu.MiniGames.Game10;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG10_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.카트타기;

	@EventHandler
	public void Test(VehicleEnterEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getVehicle().getWorld().getName().equals(Main.world) && event.getVehicle().getType() == EntityType.MINECART)
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
		if(data.isGame2() && data.getMinigame() == minigame && event.getVehicle().getWorld().getName().equals(Main.world) && event.getVehicle().getType() == EntityType.MINECART)
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.world))
		{
			event.setCancelled(true);
		}
	}
}