package com.linmalu.minigames.game004;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent4 extends MiniGameEvent
{
	public MiniGameEvent4(MiniGame minigame)
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
			event.setCancelled(false);
			minigame.getInstance().addRandomItem(player);
		}
	}
	@EventHandler
	public void Event(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			ItemData.useItem(player, true, 0);
		}
	}
	@EventHandler
	public void Event(ProjectileHitEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()))
		{
			Block block = event.getHitBlock();
			if(block != null)
			{
				block.breakNaturally();
			}
		}
	}
}
