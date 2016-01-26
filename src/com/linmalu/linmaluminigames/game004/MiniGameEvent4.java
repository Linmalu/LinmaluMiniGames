package com.linmalu.linmaluminigames.game004;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.data.PlayerData;
import com.linmalu.linmaluminigames.game.MiniGameEvent;

public class MiniGameEvent4 extends MiniGameEvent
{
	public MiniGameEvent4(MiniGames minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive())
		{
			event.setCancelled(false);
			minigame.getUtil().addRandomItem(player);
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
	@EventHandler
	public void Event(ProjectileHitEvent event)
	{
		Projectile entity = event.getEntity();
		if(data.isGame2() && data.getMinigame() == minigame && entity.getWorld().getName().equals(Main.world) && entity.getType() == EntityType.SNOWBALL)
		{
			BlockIterator bi = new BlockIterator(entity.getWorld(), entity.getLocation().toVector(), entity.getVelocity().normalize(), 0, 2);
			while(bi.hasNext())
			{
				Block block = bi.next();
				if(block != null && block.getType() == Material.SNOW_BLOCK)
				{
					block.breakNaturally();
					break;
				}
			}
		}
	}
}