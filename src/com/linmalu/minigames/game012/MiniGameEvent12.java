package com.linmalu.minigames.game012;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent12 extends MiniGameEvent
{
	public MiniGameEvent12(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(VehicleMoveEvent event)
	{
		Bukkit.broadcastMessage(event.getEventName());
	}
	@EventHandler
	public void Event(VehicleUpdateEvent event)
	{
		Bukkit.broadcastMessage(event.getEventName());
	}
	@EventHandler
	public void Event(VehicleExitEvent event)
	{
		Location loc = event.getExited().getLocation();
		if(checkEvent(loc.getWorld()) && loc.getY() > 0)
		{
			Bukkit.broadcastMessage(event.getEventName());
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), () -> event.getVehicle().addPassenger(event.getExited()));
			// Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), () ->
			// {
			// // event.getVehicle().eject();
			// Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), () ->
			// {
			// event.getVehicle().setVelocity(new Vector(0, 5, 0));
			// Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), () ->
			// {
			// event.getVehicle().addPassenger(event.getExited());
			// });
			// });
			// });
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(HorseJumpEvent event)
	{
		AbstractHorse horse = event.getEntity();
		if(checkEvent(horse.getWorld()))
		{
			// horse.eject();
			// horse.getPassengers().forEach(horse::removePassenger);
			// TODO
			Bukkit.broadcastMessage(event.getEventName() + " / " + event.getPower());
			// Arrow arrow = horse.getWorld().spawn(horse.getLocation().add(0, 0, 0), Arrow.class);
			if(horse.getPassengers().get(0) instanceof ProjectileSource)
			{
				Arrow arrow = ((ProjectileSource)horse.getPassengers().get(0)).launchProjectile(Arrow.class, horse.getLocation().getDirection().multiply(event.getPower() != 1 ? event.getPower() * 2 : 8));
				arrow.addPassenger(horse);
			}
			// arrow.setVelocity(horse.getLocation().getDirection().normalize().setY(1).multiply(event.getPower() != 1 ? event.getPower() * 2 : 8));
			// horse.setVelocity(horse.getVelocity().add(horse.getLocation().getDirection().normalize().setY(-1).multiply(event.getPower() != 1 ? event.getPower() * 2 : 8)));
		}
	}
	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive())
		{
			int yFrom = event.getFrom().getBlockY();
			int yTo = event.getTo().getBlockY();
			if(yFrom != yTo && yTo < 0)
			{
				Location loc = player.getLocation();
				switch(pd.getScore())
				{
					case 0:
						loc.setX(7);
						loc.setY(22);
						loc.setZ(7);
						break;
					case 1:
						loc.setX(96);
						loc.setY(30);
						loc.setZ(111);
						break;
					case 2:
						loc.setX(268);
						loc.setY(30);
						loc.setZ(24);
						break;
				}
				player.teleport(loc);
				new MiniGameHorse(player);
			}
			else if(player.getLocation().toVector().isInAABB(new Vector(92, 27, 107), new Vector(100, 35, 115)))
			{
				pd.setScore(1);
			}
			else if(player.getLocation().toVector().isInAABB(new Vector(264, 27, 22), new Vector(272, 35, 28)))
			{
				pd.setScore(2);
			}
			if(player.getLocation().toVector().isInAABB(new Vector(149, 62, 58), new Vector(153, 65, 62)))
			{
				pd.setScore(3);
			}
		}
	}
}