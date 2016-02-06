package com.linmalu.minigames.game006;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil6 extends MiniGameUtil
{
	public MiniGameUtil6(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 땅 따 먹 기 게 임 ] = = = = =",
				"땅따먹기 게임은 자신의 아래에 있는 블록이 자신의 블록으로 바뀌는 게임입니다.",
				"최대 인원은 48명입니다.",
				"서로 공격할 수 있으며 맞을 경우 10초 동안 게임에 참여할 수 없습니다.",
				"제한시간 안에 점수가 높은 플레이어가 승리합니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 10;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		cooldown = 0;
		topScore = true;
		score = 0;
		see = true;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap() {
		MapData md = Main.getMain().getGameData().getMapData();
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					Block block = md.getWorld().getBlockAt(x, y, z);
					if(y == 10 || (x == md.getX1() || x == md.getX2() || z == md.getZ1() || z == md.getZ2()))
					{
						block.setType(Material.SNOW_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	@Override
	public void reloadConfig() throws IOException
	{
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		if(!file.exists())
		{
			config.set(MAP_DEFAULT, 10);
			config.set(MAP_PLAYER, 2);
			config.set(TIME_DEFAULT, 180);
			config.set(TIME_PLAYER, 10);
		}
		mapDefault = config.getInt(MAP_DEFAULT);
		mapPlayer = config.getInt(MAP_PLAYER);
		timeDefault = config.getInt(TIME_DEFAULT);
		timePlayer = config.getInt(TIME_PLAYER);
		config.save(file);
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
		if(timer.getTime() % 20 == 0)
		{
			for(Player player : data.getPlayers())
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, true, false), true);
			}
		}
	}
	@Override
	public void endTimer()
	{
	}
}
