package com.linmalu.minigames.game001;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent1 extends MiniGameEvent
{
	public MiniGameEvent1(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(BlockCanBuildEvent event)
	{
		Block block = event.getBlock();
		if(checkEvent(block.getWorld()))
		{
			for(Player player : data.getPlayers())
			{
				PlayerData pd = data.getPlayerData(player.getUniqueId());
				if(pd != null && pd.isLive() && block.getLocation().add(0.5, 0, 0.5).distance(player.getLocation()) <= 1)
				{
					data.diePlayer(player.getUniqueId());
				}
			}
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
}
