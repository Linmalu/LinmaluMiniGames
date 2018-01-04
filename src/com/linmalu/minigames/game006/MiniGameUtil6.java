package com.linmalu.minigames.game006;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;
import com.linmalu.minigames.types.ConfigType;

//땅따먹기
public class MiniGameUtil6 extends MiniGameUtil
{
	public MiniGameUtil6(MiniGame minigame)
	{
		super(minigame);
		setConfigData(ConfigType.MAP_DEFAULT_SIZE, 10);
		setConfigData(ConfigType.MAP_PLAYER_SIZE, 2);
		setConfigData(ConfigType.TIME_DEFAULT, 180);
		setConfigData(ConfigType.TIME_PLAYER, 0);
		setConfigData(ConfigType.SCORE_DEFAULT, 100);
		setConfigData(ConfigType.SCORE_PLAYER, 10);
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
