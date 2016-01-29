package com.linmalu.minigames.game008;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;

public class MiniGameBombChange8
{
	private GameData data = Main.getMain().getGameData();

	public MiniGameBombChange8()
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
	public MiniGameBombChange8(Player player)
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
				data.getMinigame().getHandle().addRandomItem(player);
			}
		}
	}
}
