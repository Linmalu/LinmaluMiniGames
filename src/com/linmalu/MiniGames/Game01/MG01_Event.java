package com.linmalu.MiniGames.Game01;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG01_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.모루피하기;

	@EventHandler
	public void Event(BlockCanBuildEvent event)
	{
		Block block = event.getBlock();
		if(data.isGame2() && data.getMinigame() == minigame && block.getWorld().getName().equals(Main.world))
		{
			for(Player player : data.getPlayers())
			{
				PlayerData pd = data.getPlayerData(player.getUniqueId());
				if(pd.isLive() && block.getLocation().distance(player.getLocation()) <= 1)
				{
					data.diePlayer(player.getUniqueId());
				}
			}
		}
	}
	@EventHandler
	public void Event(ItemSpawnEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.world))
		{
			event.setCancelled(true);
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
}
