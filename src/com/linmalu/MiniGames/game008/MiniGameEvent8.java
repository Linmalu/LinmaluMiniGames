package com.linmalu.minigames.game008;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent8 extends MiniGameEvent
{
	public MiniGameEvent8(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(EntityDamageByEntityEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME) && event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player player1 = (Player)event.getDamager();
			Player player2 = (Player) event.getEntity();
			PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
			PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
			if(pd1 != null && pd2 != null && pd1.isLive() && pd2.isLive() && data.getTargetPlayer().equals(player1.getUniqueId()))
			{
				new MiniGameBombChange8(player2);
			}
			else
			{
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void Event(EntityDamageByBlockEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME) && event.getEntity() instanceof Player && event.getCause() == DamageCause.BLOCK_EXPLOSION)
		{
			event.setCancelled(true);
		}
	}
}