package com.linmalu.MiniGames.Game04;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG04_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.¶¥ÆÄ±â;

	@EventHandler
	public void Event(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive())
		{
			event.setCancelled(false);
			int ran = new Random().nextInt(100);
			if(ran == 0)
			{
				player.getInventory().addItem(data.getItems().get(0));
			}
			else if(ran == 1)
			{
				player.getInventory().addItem(data.getItems().get(1));
			}
			else if(ran == 2)
			{
				player.getInventory().addItem(data.getItems().get(2));
			}
			else if(ran == 3)
			{
				player.getInventory().addItem(data.getItems().get(3));
			}
			else if(ran == 4)
			{
				player.getInventory().addItem(data.getItems().get(4));
			}
			else if(ran == 5)
			{
				player.getInventory().addItem(data.getItems().get(5));
			}
			else
			{
				for(ItemStack item : player.getInventory().getContents())
				{
					if(item != null && item.getType() == data.getItems().get(6).getType() && item.getAmount() < 100)
					{
						item.setAmount(item.getAmount() + 1);
						return;
					}
				}
				player.getInventory().addItem(data.getItems().get(6));
			}
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