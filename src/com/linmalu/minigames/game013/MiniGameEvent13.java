package com.linmalu.minigames.game013;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent13 extends MiniGameEvent
{
	public MiniGameEvent13(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive())
		{
			pd.addScore();
			event.setCancelled(false);
		}
	}
}