package com.linmalu.minigames.game009;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import com.linmalu.minigames.Main;
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
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
}