package com.linmalu.MiniGames.Game06;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.Cooldown;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MapData;
import com.linmalu.MiniGames.Data.MiniGames;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG06_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.¶¥µû¸Ô±â;

	@EventHandler
	public void Event(EntityDamageByEntityEvent event)
	{
		if(data.isGame2() && data.getMinigame() == minigame && event.getEntity().getWorld().getName().equals(Main.world) && event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player player1 = (Player)event.getDamager();
			Player player2 = (Player) event.getEntity();
			PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
			PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
			if(pd1 != null && pd2 != null && pd1.isLive() && pd2.isLive() && pd1.isCooldown() && pd2.isCooldown())
			{
				new Cooldown(10, player2, true);
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		MapData md = data.getMapData();
		if(data.isGame2() && data.getMinigame() ==  minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive() && pd.isCooldown())
		{
			int xFrom = event.getFrom().getBlockX();
			int xTo = event.getTo().getBlockX();
			int yFrom = event.getFrom().getBlockY();
			int yTo = event.getTo().getBlockY();
			int zFrom = event.getFrom().getBlockZ();
			int zTo = event.getTo().getBlockZ();
			if(yTo == md.getMapHeight() + 1 && (xFrom != xTo || yFrom != yTo || zFrom != zTo))
			{
				Block block = player.getWorld().getBlockAt(xTo, md.getMapHeight(), zTo);
				new MG06_Block(data, pd, block);
			}
		}
	}


}