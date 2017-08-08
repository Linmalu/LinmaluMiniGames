package com.linmalu.minigames.game013;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluPlayer;
import com.linmalu.minigames.data.ConfigData;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.ItemData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameUtil;

//블록부수기
public class MiniGameUtil13 extends MiniGameUtil
{
	public MiniGameUtil13(MiniGame minigame)
	{
		super(minigame);
		configs.put(ConfigData.TIME_DEFAULT, 180);
		configs.put(ConfigData.TIME_PLAYER, 0);
		configs.put(ConfigData.SCORE_DEFAULT, 50);
		configs.put(ConfigData.SCORE_PLAYER, 0);
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		return new MaterialData(Material.AIR);
	}
	@Override
	public void moveWorld(Player player)
	{
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		player.teleport(new Location(data.getMapData().getWorld(), (4 * pd.getNumber()) + 2, MAP_DEFAULT_HEIGHT + 1, 2));
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
			ItemData.setItemStack(player, ItemData.삽, ItemData.도끼, ItemData.곡괭이, ItemData.삽, ItemData.도끼, ItemData.곡괭이, ItemData.삽, ItemData.도끼, ItemData.곡괭이);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
		for(Player player : data.getLivePlayers())
		{
			if(MiniGameBlockItem13.isItemData(player.getTargetBlock((Set<Material>)null, 10).getType(), ItemData.getItemData(player.getInventory().getItemInMainHand())))
			{
				player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
			}
			else
			{
				LinmaluPlayer.addPotionEffect(player, new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 4, false, false));
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
