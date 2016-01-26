package com.linmalu.linmaluminigames.game012;

import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.util.Vector;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.data.PlayerData;
import com.linmalu.linmaluminigames.game.MiniGameEvent;

public class MiniGameEvent12 extends MiniGameEvent
{
	public MiniGameEvent12(MiniGames minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(VehicleExitEvent event)
	{
		Location loc = event.getExited().getLocation();
		if(data.isGame2() && data.getMinigame() == minigame && loc.getWorld().getName().equals(Main.world) && loc.getY() > 0)
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(HorseJumpEvent event)
	{
		Horse horse = event.getEntity();
		if(data.isGame2() && data.getMinigame() == minigame && horse.getWorld().getName().equals(Main.world))
		{
			horse.setVelocity(horse.getVelocity().add(horse.getLocation().getDirection().normalize().setY(-1).multiply(event.getPower() != 1 ? event.getPower() * 2 : 8)));
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
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive())
		{
			int yFrom = event.getFrom().getBlockY();
			int yTo = event.getTo().getBlockY();
			if(yFrom != yTo && yTo < 0)
			{
				//				player.leaveVehicle();
				Location loc = player.getLocation();
				switch(pd.getNumber())
				{
				case 0:
					loc.setX(7);
					loc.setY(22);
					loc.setZ(7);
					break;
				case 1:
					loc.setX(268);
					loc.setY(30);
					loc.setZ(24);
					break;
				case 2:
					loc.setX(96);
					loc.setY(30);
					loc.setZ(111);
					break;
				}
				player.teleport(loc);
				new MiniGameHorse(player);
			}
			else if(player.getLocation().toVector().isInAABB(new Vector(264, 27, 22), new Vector(272, 35, 28)))
			{
				pd.setNumber(1);
			}
			else if(player.getLocation().toVector().isInAABB(new Vector(92, 27, 107), new Vector(100, 35, 115)))
			{
				pd.setNumber(2);
			}
			if(player.getLocation().toVector().isInAABB(new Vector(149, 62, 58), new Vector(153, 65, 62)))
			{
				pd.addScore();
			}
		}
	}
}