package com.linmalu.linmaluminigames.game005;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.game.MiniGameEvent;

public class MiniGameEvent5 extends MiniGameEvent
{
	public MiniGameEvent5(MiniGames minigame)
	{
		super(minigame);
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