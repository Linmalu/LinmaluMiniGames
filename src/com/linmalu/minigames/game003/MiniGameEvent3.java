package com.linmalu.minigames.game003;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

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
		if(checkEvent(event.getEntity().getWorld()) && event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player player1 = (Player)event.getDamager();
			Player player2 = (Player)event.getEntity();
			PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
			PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
			if(pd1 != null && pd2 != null && pd1.isLive() && pd2.isLive() && pd1.isCooldown() && pd2.isCooldown())
			{
				Material item1 = player1.getInventory().getItemInMainHand().getType();
				Material item2 = player2.getInventory().getItemInMainHand().getType();
				Material m1 = Material.GOLD_SPADE;
				Material m2 = Material.GOLD_PICKAXE;
				Material m3 = Material.GOLD_AXE;
				if((item1 == m1 && item2 == m2) || (item1 == m2 && item2 == m3) || (item1 == m3 && item2 == m1) || (item2 != m1 && item2 != m2 && item2 != m3))
				{
					addScore(pd1, player2);
				}
				else if(item1 == item2)
				{
				}
				else
				{
					addScore(pd2, player1);
				}
			}
		}
	}
	private void addScore(PlayerData pd, Player player)
	{
		pd.addScore();
		new Cooldown(10, player, true);
		minigame.getInstance().teleport(player);
		for(Player p : data.getPlayers())
		{
			p.sendMessage(ChatColor.GOLD + pd.getName() + ChatColor.YELLOW + "님이 " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + "님을 이겼습니다.");
		}
	}
	@EventHandler
	public void Event(PlayerItemHeldEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isCooldown())
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
