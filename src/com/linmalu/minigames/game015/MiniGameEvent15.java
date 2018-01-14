package com.linmalu.minigames.game015;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import com.linmalu.minigames.data.Cooldown;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent15 extends MiniGameEvent
{
	public MiniGameEvent15(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(BlockBreakEvent event)
	{
		if(checkEvent(event.getPlayer().getWorld()))
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
		if(checkEvent(event.getEntity().getWorld()))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(EntityDamageByEntityEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()) && event.getEntity() instanceof Player && event.getDamager() instanceof Arrow)
		{
			Player player2 = (Player)event.getEntity();
			Arrow arrow = (Arrow)event.getDamager();
			PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
			if(arrow.getShooter() instanceof Player)
			{
				Player player1 = (Player)arrow.getShooter();
				PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
				if(pd1 != null && pd2 != null && pd2.isLive() && pd2.isCooldown())
				{
					for(Player player : data.getPlayers())
					{
						player.sendMessage(ChatColor.GOLD + pd1.getName() + ChatColor.YELLOW + "님이 " + ChatColor.GOLD + pd2.getName() + ChatColor.YELLOW + "님을 맞추었습니다.");
					}
					pd1.addScore();
					new Cooldown(0, player2, true);
					minigame.getInstance().teleport(player2);
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void Event(ProjectileLaunchEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()))
		{
			if(event.getEntityType() == EntityType.PLAYER)
			{
				Player player = ((Player)event.getEntity().getShooter());
				PlayerData pd = data.getPlayerData(player.getUniqueId());
				if(pd != null && pd.isLive() && pd.isSkill() && pd.isCooldown())
				{
					new MiniGameShoot15(player);
				}
			}
		}
	}
}
