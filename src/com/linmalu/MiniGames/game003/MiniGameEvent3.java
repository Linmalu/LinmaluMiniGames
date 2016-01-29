package com.linmalu.minigames.game003;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.Cooldown;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent3 extends MiniGameEvent
{
	public MiniGameEvent3(MiniGame minigame)
	{
		super(minigame);
	}
	@EventHandler
	public void Event(EntityDamageByEntityEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.WORLD_NAME) && event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player player1 = (Player)event.getDamager();
			Player player2 = (Player) event.getEntity();
			PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
			PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
			if(pd1 != null && pd2 != null && pd1.isLive() && pd2.isLive() && pd1.isCooldown() && pd2.isCooldown())
			{
				Material item1 = player1.getItemInHand().getType();
				Material item2 = player2.getItemInHand().getType();
				Material m1 = Material.GOLD_SPADE;
				Material m2 = Material.GOLD_PICKAXE;
				Material m3 = Material.GOLD_AXE;
				if((item1 == m1 && item2 == m2) || (item1 == m2 && item2 == m3) || (item1 == m3 && item2 == m1) || (item2 != m1 && item2 != m2 && item2 != m3))
				{
					new Cooldown(10, player2, true);
					data.teleportPlayer(player2);
					pd1.addScore();
				}
				else if(item1 == item2)
				{
				}
				else
				{
					new Cooldown(10, player1, true);
					data.teleportPlayer(player1);
					pd2.addScore();
				}
			}
		}
	}
	@EventHandler
	public void Event(PlayerItemHeldEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.WORLD_NAME) && pd != null && pd.isCooldown())
		{
			if(pd.isSkill())
			{
				new Cooldown(3, player, false);
			}
			else
			{
				event.setCancelled(true);
			}
		}
	}
}