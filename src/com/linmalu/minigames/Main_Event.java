package com.linmalu.minigames;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.linmalu.library.api.LinmaluAutoRespawn;
import com.linmalu.library.api.LinmaluVersion;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;

public class Main_Event implements Listener
{
	private final GameData data = Main.getMain().getGameData();

	@EventHandler(priority = EventPriority.LOW)
	public void Event(FoodLevelChangeEvent event)
	{
		if(data.isGame1() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(EntityExplodeEvent event)
	{
		if(data.isGame1() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.blockList().clear();
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(BlockExplodeEvent event)
	{
		if(data.isGame1() && event.getBlock().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.blockList().clear();
		}
	}
	@EventHandler(priority = EventPriority.LOW)
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
	@EventHandler(priority = EventPriority.LOW)
	public void Event(PlayerRespawnEvent event)
	{
		final Player player = event.getPlayer();
		final PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && pd != null)
		{
			event.setRespawnLocation(data.teleport(player));
		}
	}
	@EventHandler(priority = EventPriority.LOW)
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
			data.teleport(player);
			if(!data.isResourcePack())
			{
				player.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
				player.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 적용됩니다.");
			}
		}
		else if(!data.isGame1())
		{
			PlayerData pd = PlayerData.getPlayerData(player.getUniqueId());
			if(pd != null)
			{
				pd.resetPlayer();
			}
		}
		if(data.isResourcePack())
		{
			player.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
			player.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 적용됩니다.");
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if(data.isGame2() && player.getWorld().getName().equals(Main.WORLD_NAME))
		{
			data.diePlayer(player.getUniqueId());
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		if(data.isGame2() && player.getWorld().getName().equals(Main.WORLD_NAME))
		{
			LinmaluAutoRespawn.respawn(player);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(ItemSpawnEvent event)
	{
		if(data.isGame1() && event.getLocation().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(PlayerPickupItemEvent event)
	{
		if(data.isGame1() && event.getPlayer().getWorld().getName().equals(Main.WORLD_NAME))
		{
			event.getItem().remove();
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.LOW)
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
	@EventHandler(priority = EventPriority.LOW)
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
	@EventHandler(priority = EventPriority.LOW)
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
		else if(data.isGame2() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			if(event.getDamager() instanceof Player)
			{
				PlayerData pd = data.getPlayerData(event.getDamager().getUniqueId());
				if(!(pd != null && pd.isLive() && pd.isCooldown()))
				{
					event.setCancelled(true);
				}
			}
			if(event.getEntity() instanceof Player)
			{
				PlayerData pd = data.getPlayerData(event.getEntity().getUniqueId());
				if(!(pd != null && pd.isLive() && pd.isCooldown()))
				{
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void Event(ProjectileHitEvent event)
	{
		if(data.isGame2() && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME))
		{
			Entity entity = event.getHitEntity();
			if(entity != null && entity.getType() == EntityType.PLAYER)
			{
				PlayerData pd = data.getPlayerData(entity.getUniqueId());
				if(pd != null && pd.isLive())
				{
					((Player)entity).damage(1, (Entity)event.getEntity().getShooter());
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOW)
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
					data.teleport(player);
				}
				else
				{
					if(pd.isLive() && !data.getMapData().isTopScore())
					{
						data.diePlayer(player.getUniqueId());
					}
					else
					{
						data.teleport(player);
					}
				}
			}
		}
	}
}
