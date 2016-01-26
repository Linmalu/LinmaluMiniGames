package com.linmalu.MiniGames.Game05;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;

public class MG05_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.²¿¸®ÇÇÇÏ±â;

	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.world))
		{
			event.setCancelled(true);
		}
	}
}