package com.linmalu.minigames.game001;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

import com.linmalu.minigames.Main;
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
		if(data.isGame2() && data.getMinigame() == minigame && block.getWorld().getName().equals(Main.WORLD_NAME))
		{
			for(Player player : data.getPlayers())
			{
				PlayerData pd = data.getPlayerData(player.getUniqueId());
				if(pd != null && pd.isLive() && block.getLocation().distance(player.getLocation()) <= 1)
				{
					data.diePlayer(player.getUniqueId());
				}
			}
		}
	}
	@EventHandler
	public void Event(ItemSpawnEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
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
