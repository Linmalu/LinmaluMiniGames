package com.linmalu.minigames.game000;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent0 extends MiniGameEvent
{
	public MiniGameEvent0(MiniGame minigame)
	{
		super(minigame);
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive())
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
					new MiniGameFallingBlock0(block);
				}
			}
		}
	}
	@EventHandler
	public void Event(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		if(checkEvent(player.getWorld()) && pd != null && pd.isLive() && pd.isSkill() && event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			minigame.getInstance().useItem(player, false, 20);
		}
	}
}
