package com.linmalu.minigames.game010;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;

public class MiniGameRemoveCart10
{
	public MiniGameRemoveCart10()
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
