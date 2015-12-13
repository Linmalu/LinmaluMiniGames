package com.linmalu.MiniGames.Game02;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG02_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.µî¹Ý;

	@EventHandler
	public void Event(BlockCanBuildEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getBlock().getWorld().getName().equals(Main.world) && (event.getMaterial() == Material.STAINED_CLAY || event.getMaterial() == Material.WOOL))
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
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive() && (block.getType() == Material.WOOL || block.getType() == Material.STAINED_CLAY))
		{
			new MG02_Breaking(block);
			event.setCancelled(false);
			int ran = new Random().nextInt(10);
			if(0 <= ran && ran < 5)
			{
				player.getInventory().addItem(data.getItems().get(0));
			}
			else if(5 <= ran && ran < 8)
			{
				player.getInventory().addItem(data.getItems().get(1));
			}
			else if(8 <= ran && ran < 9)
			{
				player.getInventory().addItem(data.getItems().get(2));
			}
			else if(9 <= ran && ran < 10)
			{
				player.getInventory().addItem(data.getItems().get(3));
			}
		}
	}
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive())
		{
			pd.setScore(event.getTo().getBlockY() - 11);
		}
	}
	@EventHandler
	public void Event(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			data.useItem(player, true);
		}
	}
}