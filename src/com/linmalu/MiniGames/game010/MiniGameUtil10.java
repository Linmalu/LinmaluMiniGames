package com.linmalu.minigames.game010;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGames;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil10 extends MiniGameUtil
{
	public MiniGameUtil10(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 카 트 타 기 게 임 ] = = = = =",
				"카트타기 게임은 제한시간 안에 카트를 타는 게임입니다.",
				"카트는 살아있는 사람 수보다 적게 나옵니다.",
				"제한시간이 지나면 카트에 타지 않은 사람은 탈락합니다.",
				"최종 1인이 남을 때까지 게임이 진행됩니다."
		});
	}
	@SuppressWarnings("deprecation")
	@Override
	public void createGameMap()
	{
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
						block.setType(Material.GOLD_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 15;
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 20;
		time = 10 * 20;
		cooldown = 5 * 20;
		topScore = false;
		score = 0;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
