package com.linmalu.minigames.game008;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGames;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil8 extends MiniGameUtil
{
	public MiniGameUtil8(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 폭 탄 피 하 기 게 임 ] = = = = =",
				"폭탄피하기 게임은 폭탄을 피해서 도망가는 게임입니다.",
				"폭탄이 된 사람에게 맞으면 폭탄을 넘겨받게 됩니다.",
				"제한시간이 지나면 폭탄이 터지며 탈락합니다.",
				"1명이 남을 때까지 게임이 진행됩니다."
		});
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap() {
		MapData md = Main.getMain().getGameData().getMapData();
		for(int y = 10; y < md.getMapHeight(); y++)
		{
			for(int x = md.getX1(); x <= md.getX2(); x++)
			{
				for(int z = md.getZ1(); z <= md.getZ2(); z++)
				{
					if(y == 10 || (x == md.getX1() || x == md.getX2() || z == md.getZ1() || z == md.getZ2()))
					{
						Block block = md.getWorld().getBlockAt(x, y, z);
						block.setType(Material.REDSTONE_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 20;
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 20;
		time = 10 * 20;
		cooldown = 3 * 20;
		topScore = false;
		score = 0;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		new MiniGameBombChange8();
	}
	@Override
	public void addRandomItem(Player player)
	{
		GameItem.setItemStack(player, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄, GameItem.폭탄);
		player.getInventory().setHelmet(GameItem.폭탄.getItemStack());
	}
}
