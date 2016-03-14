package com.linmalu.minigames.game004;

import org.bukkit.Effect;
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
			minigame.getHandle().addRandomItem(player);
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
	@EventHandler
	public void Event(ProjectileHitEvent event)
	{
		Projectile entity = event.getEntity();
		if(checkEvent(event.getEntity().getWorld()) && entity.getType() == EntityType.SNOWBALL)
		{
			BlockIterator bi = new BlockIterator(entity.getWorld(), entity.getLocation().toVector(), entity.getVelocity().normalize(), 0, 2);
			while(bi.hasNext())
			{
				Block block = bi.next();
				if(block != null && block.getType() == Material.SNOW_BLOCK)
				{
					block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
					block.breakNaturally();
					break;
				}
			}
		}
	}
}