package com.linmalu.MiniGames.Game06;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.PlayerData;

public class MG06_Block
{
	@SuppressWarnings("deprecation")
	public MG06_Block(GameData data, PlayerData pd, Block block)
	{
		ItemStack item = data.getItems().get(pd.getNumber());
		if(!block.isEmpty() && (item.getType() != block.getType() || item.getDurability() != block.getData()))
		{
			for(Player player : data.getPlayers())
			{
				PlayerData tpd = data.getPlayerData(player.getUniqueId());
				if(tpd != pd)
				{
					ItemStack titem = data.getItems().get(tpd.getNumber());
					if(titem.getType() == block.getType() && titem.getDurability() == block.getData())
					{
						tpd.subScore();
						break;
					}
				}
			}
			pd.addScore();
			block.setType(item.getType());
			block.setData((byte) item.getDurability());
		}
	}
}
