package com.linmalu.linmaluminigames.game006;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MapData;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.game.MiniGameUtil;

public class MiniGameUtil6 extends MiniGameUtil
{
	public MiniGameUtil6(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 땅 따 먹 기 게 임 ] = = = = =",
				"땅따먹기 게임은 자신의 아래에 있는 블록이 자신의 블록으로 바뀌는 게임입니다.",
				"최대 인원은 16명입니다.",
				"1분이 지날 때마다 이동속도가 증가합니다.",
				"서로 공격할 수 있으며 맞을 경우 10초 동안 게임에 참여할 수 없습니다.",
				"목표 점수에 먼저 도달하는 플레이어가 승리합니다."
		});
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
	public MapData getMapData(World world)
	{
		size = 10 + (Main.getMain().getGameData().getPlayerAllCount() * 2);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 10;
		time = 0;
		cooldown = 0;
		topScore = true;
		score = 200 + (Main.getMain().getGameData().getPlayerAllCount() * 10);
		see = true;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		int number = 0;
		for(Player player : data.getLivePlayers())
		{
			data.getPlayerData(player.getUniqueId()).setNumber(number);
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
	public void addRandomItem(Player player)
	{
	}
}
