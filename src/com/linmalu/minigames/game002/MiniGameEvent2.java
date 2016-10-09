package com.linmalu.minigames.game002;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent2 extends MiniGameEvent
{
	public MiniGameEvent2(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(BlockCanBuildEvent event)
	{
		if(checkEvent(event.getBlock().getWorld()) && (event.getMaterial() == Material.STAINED_CLAY || event.getMaterial() == Material.WOOL))
		{
			event.setBuildable(true);
		}
	}
	@EventHandler
	public void Event(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive() && (block.getType() == Material.WOOL || block.getType() == Material.STAINED_CLAY))
		{
			new MiniGameBreakBlock2(block);
			event.setCancelled(false);
			minigame.getHandle().addRandomItem(player);
		}
	}
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive())
		{
			pd.setScore(event.getTo().getBlockY() - 11);
		}
	}
	@EventHandler
	public void Event(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			minigame.getHandle().useItem(player, true, 0);
		}
	}
}