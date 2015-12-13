package com.linmalu.MiniGames.Game10;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;

public class MG10_Remove
{
	public MG10_Remove()
	{
		GameData data = Main.getMain().getGameData();
		ArrayList<UUID> players = new ArrayList<>();
		for(Entity e : data.getEntitys())
		{
			if(!e.isEmpty())
			{
				Entity p = e.getPassenger();
				if(p.getType() == EntityType.PLAYER)
				{
					players.add(p.getUniqueId());
					e.eject();
				}
			}
			e.remove();
		}
		data.getEntitys().clear();
		for(Player player : data.getPlayers())
		{
			if(!players.contains(player.getUniqueId()))
			{
				data.diePlayer(player.getUniqueId());
			}
		}
	}
}
