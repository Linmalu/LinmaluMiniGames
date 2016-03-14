package com.linmalu.minigames.game009;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent9 extends MiniGameEvent
{
	public MiniGameEvent9(MiniGame minigame)
	{
		super(minigame);
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