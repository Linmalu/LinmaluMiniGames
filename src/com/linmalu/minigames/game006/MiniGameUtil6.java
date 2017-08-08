package com.linmalu.minigames.game006;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

//땅따먹기
public class MiniGameUtil6 extends MiniGameUtil
{
	public MiniGameUtil6(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.MAP_DEFAULT_SIZE, 10);
		configs.put(ConfigData.MAP_PLAYER_SIZE, 2);
		configs.put(ConfigData.TIME_DEFAULT, 180);
		configs.put(ConfigData.TIME_PLAYER, 0);
		configs.put(ConfigData.SCORE_DEFAULT, 100);
		configs.put(ConfigData.SCORE_PLAYER, 10);
		see = true;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		if(y == MAP_DEFAULT_HEIGHT)
		{
			return new MaterialData(Material.SNOW_BLOCK);
		}
		return new MaterialData(Material.AIR);
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			int number = data.getPlayerData(player.getUniqueId()).getNumber();
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
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "땅");
			item.setItemMeta(im);
			item.setDurability((short)(number % 16));
			for(int i = 0; i < 9; i++)
			{
				player.getInventory().setItem(i, item);
			}
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		if(timer.getTime() % 10 == 0)
		{
			for(Player player : data.getPlayers())
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false), true);
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
