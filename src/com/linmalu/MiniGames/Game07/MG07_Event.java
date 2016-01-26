package com.linmalu.MiniGames.Game07;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.Cooldown;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG07_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.ÃÑ½Î¿ò;

	@EventHandler
	public void Event(BlockBreakEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getPlayer().getWorld().getName().equals(Main.world))
		{
			Material type = event.getBlock().getType();
			if(type == Material.GLASS || type == Material.THIN_GLASS || type == Material.STAINED_GLASS || type == Material.STAINED_GLASS_PANE)
			{
				event.setCancelled(false);
			}
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
	@EventHandler
	public void Event(EntityDamageByEntityEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.world) && event.getEntity() instanceof Player && event.getDamager() instanceof Snowball)
		{
			Player player2 = (Player) event.getEntity();
			Snowball snowball = (Snowball)event.getDamager();
			PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
			if(snowball.getShooter() instanceof Player)
			{
				Player player1 = (Player)snowball.getShooter();
				PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
				if(pd1 != null && pd2 != null && pd2.isLive() && pd2.isCooldown())
				{
					for(Player player : data.getPlayers())
					{
						player.sendMessage(ChatColor.GOLD + pd1.getName() + ChatColor.YELLOW + "´ÔÀÌ " + ChatColor.GOLD + pd2.getName() + ChatColor.YELLOW + "´ÔÀ» ¸ÂÃß¾ú½À´Ï´Ù.");
					}
					pd1.addScore();
					new Cooldown(0, player2, true);
					data.teleportPlayer(player2);
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void Event(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		ItemStack item = event.getItem();
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive() && pd.isSkill() && pd.isCooldown() && item != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			if(data.useItem(player, false))
			{
				new Cooldown(1, player, false);
			}
		}
	}
}