package com.linmalu.minigames.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.Main;

public class Cooldown implements Runnable
{
	private final GameData data = Main.getMain().getGameData();
	private final int taskId;
	private int time;
	private final Player player;
	private final boolean invisivilty;
	private final PlayerData pd;
	private final ItemStack[] items;

	public Cooldown(int time, Player player, boolean invisivilty)
	{
		this.time = time * 20;
		this.player = player;
		this.invisivilty = invisivilty;
		pd = data.getPlayerData(player.getUniqueId());
		items = player.getInventory().getContents();
		if(invisivilty)
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, false, false), true);
			player.damage(0);
			new PlayFirework(player.getLocation());
			player.getInventory().clear();
			pd.setCooldown(false);
		}
		else
		{
			pd.setSkill(false);
		}
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public void run()
	{
		if(data.isGame2() && pd.isLive() && time >= 0 && (invisivilty || (pd.isCooldown() && !invisivilty)))
		{
			if(invisivilty && time % 20 == 0)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, false, false), true);
			}
			player.setLevel(time / 20);
			player.setExp(time % 20 * 0.05F);
			time--;
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
			if(pd.isLive())
			{
				if(invisivilty)
				{
					player.removePotionEffect(PotionEffectType.INVISIBILITY);
					player.getInventory().clear();
					player.getInventory().setContents(items);
					pd.setCooldown(true);
				}
				else
				{
					pd.setSkill(true);
				}
			}
		}
	}
}
