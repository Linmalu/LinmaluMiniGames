package com.linmalu.MiniGames.Game08;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Data.GameData;

public class MG08_Change
{
	private GameData data = Main.getMain().getGameData();

	public MG08_Change()
	{
		ArrayList<UUID> list = new ArrayList<>();
		for(Player player : data.getPlayers())
		{
			if(data.getPlayerData(player.getUniqueId()).isLive())
			{
				list.add(player.getUniqueId());
			}
		}
		if(list.size() != 0)
		{
			changePlayer(list.get(new Random().nextInt(list.size())));
		}
	}
	public MG08_Change(Player player)
	{
		changePlayer(player.getUniqueId());
	}
	private void changePlayer(UUID uuid)
	{
		data.setTargetPlayer(uuid);
		for(Player player : data.getPlayers())
		{
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			if(player.getUniqueId().equals(uuid))
			{
				for(ItemStack item : data.getItems())
				{
					player.getInventory().addItem(item);
					player.getInventory().setHelmet(item);
				}
			}
		}
	}
}
