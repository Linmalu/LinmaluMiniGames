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

public class MiniGameUtil6 extends MiniGameUtil
{
	public MiniGameUtil6(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 땅 따 먹 기 게 임 ] = = = = =", "땅따먹기 게임은 자신의 아래에 있는 블록이 자신의 블록으로 바뀌는 게임입니다.", "최대 인원은 48명입니다.", "서로 공격할 수 있으며 맞을 경우 10초 동안 게임에 참여할 수 없습니다.", "제한시간 안에 점수가 높은 플레이어가 승리합니다."});
		mapDefault = 10;
		mapPlayer = 2;
		timeDefault = 180;
		timePlayer = 0;
		scoreDefault = 100;
		scorePlayer = 10;
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
