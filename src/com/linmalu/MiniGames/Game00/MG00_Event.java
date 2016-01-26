package com.linmalu.MiniGames.Game00;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.Cooldown;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG00_Event implements Listener
{
	private GameData data = Main.getMain().getGameData();
	private MiniGames minigame = MiniGames.�޸���;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive())
		{
			int xFrom = event.getFrom().getBlockX();
			int xTo = event.getTo().getBlockX();
			int yFrom = event.getFrom().getBlockY();
			int yTo = event.getTo().getBlockY();
			int zFrom = event.getFrom().getBlockZ();
			int zTo = event.getTo().getBlockZ();
			int mapHeight = data.getMapData().getMapHeight();
			if(!player.hasPotionEffect(PotionEffectType.INVISIBILITY) && yTo == mapHeight + 1 && (xFrom != xTo || yFrom != yTo || zFrom != zTo))
			{
				Block block = player.getWorld().getBlockAt(xTo, mapHeight, zTo);
				if(block.getType() == Material.STAINED_GLASS && block.getData() == 0)
				{
					new MG00_FallingBlock(block);
				}
			}
		}
	}
	@EventHandler
	public void Event(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(data.isGame2() && data.getMinigame() == minigame && player.getWorld().getName().equals(Main.world) && pd != null && pd.isLive() && pd.isSkill() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			if(data.useItem(player, false))
			{
				new Cooldown(20, player, false);
			}
		}
	}
}
