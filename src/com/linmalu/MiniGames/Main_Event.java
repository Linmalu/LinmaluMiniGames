package com.linmalu.minigames;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.linmalu.library.api.LinmaluAutoRespawn;
import com.linmalu.library.api.LinmaluVersion;
import com.linmalu.minigames.data.Cooldown;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;

public class Main_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();

	@EventHandler
	public void Event(WeatherChangeEvent event)
	{
		if(data.isGame1() && event.getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(FoodLevelChangeEvent event)
	{
		if(data.isGame1() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(EntityExplodeEvent event)
	{
		if(data.isGame1() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.blockList().clear();
		}
	}
	@EventHandler
	public void Event(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame1() && pd == null && player.getWorld().getName().equals(Main.WORLD_NAME))
		{
			player.sendMessage(ChatColor.RED + "미니게임맵에 들어갈 권한이 없습니다.");
			player.teleport(event.getFrom().getSpawnLocation());
		}
	}
	@EventHandler
	public void Event(PlayerRespawnEvent event)
	{
		final Player player = event.getPlayer();
		final PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && pd != null)
		{
			event.setRespawnLocation(data.teleportPlayer(player));
		}
	}
	@EventHandler
	public void Event(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.isOp())
		{
			LinmaluVersion.check(Main.getMain(), player);
		}
		if(data.isGame1() && data.getPlayerData(player.getUniqueId()) != null)
		{
			data.setScoreboard(player);
			data.teleportPlayer(player);
			if(!data.isResourcePack())
			{
				player.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
				player.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 적용됩니다.");
			}
		}
		else if(!data.isGame1())
		{
			data.restorePlayer(player);
		}
		if(data.isResourcePack())
		{
			player.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
			player.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 적용됩니다.");
		}
	}
	@EventHandler
	public void Event(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if(data.isGame2() && player.getWorld().getName().equals(Main.WORLD_NAME))
		{
			data.diePlayer(player.getUniqueId());
		}
	}
	@EventHandler
	public void Event(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		if(data.isGame2() && player.getWorld().getName().equals(Main.WORLD_NAME))
		{
			LinmaluAutoRespawn.respawn(player);
		}
	}
	@EventHandler
	public void Event(PlayerPickupItemEvent event)
	{
		if(data.isGame1() && event.getPlayer().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.getItem().remove();
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(PlayerDropItemEvent event)
	{
		if(data.isGame1() && event.getPlayer().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(BlockCanBuildEvent event)
	{
		if(data.isGame1() && event.getBlock().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setBuildable(false);
		}
	}
	@EventHandler
	public void Event(BlockPlaceEvent event)
	{
		if(data.isGame1() && event.getPlayer().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(BlockBreakEvent event)
	{
		if(data.isGame1() && event.getPlayer().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(PlayerInteractEvent event)
	{
		if(data.isGame1() && event.getPlayer().getWorld().getName().equals(Main.WORLD_NAME) && event.getAction() == Action.PHYSICAL)
		{
			if(event.getClickedBlock().getType() == Material.SOIL)
			{
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setDamage(0);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(EntityDamageByEntityEvent event)
	{
		if(data.isGame1() && !data.isGame2() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
		else if(data.isGame2() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME) && event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			Player aplayer = (Player)event.getDamager();
			PlayerData pd = data.getPlayerData(player.getUniqueId());
			PlayerData apd = data.getPlayerData(aplayer.getUniqueId());
			if(pd != null && apd != null && (!pd.isLive() || !apd.isLive() || !pd.isCooldown() || !apd.isCooldown()))
			{
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame1() && player.getWorld().getName().equals(Main.WORLD_NAME) && pd != null)
		{
			if(data.getMinigame() == MiniGame.경마)
			{
				return;
			}
			int yFrom = event.getFrom().getBlockY();
			int yTo = event.getTo().getBlockY();
			if(yFrom != yTo && yTo < 0)
			{
				if(!data.isGame2())
				{
					data.teleportPlayer(player);
				}
				else
				{
					if(pd.isLive() && !data.getMapData().isTopScore())
					{
						data.diePlayer(player.getUniqueId());
					}
					else
					{
						data.teleportPlayer(player);
						if(pd.isCooldown())
						{
							new Cooldown(10, player, true);
						}
					}
				}
			}
		}
	}
}
