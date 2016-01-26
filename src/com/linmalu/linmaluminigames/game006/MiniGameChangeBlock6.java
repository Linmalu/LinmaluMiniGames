package com.linmalu.linmaluminigames.game006;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.linmalu.linmaluminigames.data.GameData;
import com.linmalu.linmaluminigames.data.PlayerData;

public class MiniGameChangeBlock6
{
	@SuppressWarnings("deprecation")
	public MiniGameChangeBlock6(GameData data, PlayerData pd, Block block)
	{
		ItemStack item = getItemstack(pd.getNumber());
		if(!block.isEmpty() && (item.getType() != block.getType() || item.getDurability() != block.getData()))
		{
			for(Player player : data.getPlayers())
			{
				PlayerData tpd = data.getPlayerData(player.getUniqueId());
				if(tpd != pd)
				{
					ItemStack titem = getItemstack(tpd.getNumber());
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
	private ItemStack getItemstack(int number)
	{
		ItemStack item;
		if(number < 16)
		{
			item = new ItemStack(Material.WOOL);
		}
		else if(number < 32)
		{
			item = new ItemStack(Material.STAINED_GLASS);
		}
		else
		{
			item = new ItemStack(Material.STAINED_CLAY); 
		}
		item.setDurability((short)(number % 16));
		return item;
	}
}
