package com.linmalu.linmaluminigames.game003;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.MapData;
import com.linmalu.linmaluminigames.data.MiniGames;
import com.linmalu.linmaluminigames.game.MiniGameUtil;

public class MiniGameUtil3 extends MiniGameUtil
{
	public MiniGameUtil3(MiniGames minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 눈 치 게 임 ] = = = = =",
				"눈치 게임은 가위바위보 게임입니다.",
				"주먹 -> 가위 -> 보 -> 주먹 순서입니다.",
				"가위바위보 변경은 3초에 한 번씩 변경할 수 있습니다.",
				"공격을 하면 가위바위보를 하게 되어 승패가 결정됩니다.",
				"눈치게임에 졌을 경우 10초 동안 게임에 참여할 수 없습니다.",
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
						block.setType(Material.HAY_BLOCK);
						block.setData((byte)0);
					}
				}
			}
		}
	}
	@Override
	public MapData getMapData(World world)
	{
		size = 5 + Main.getMain().getGameData().getPlayerAllCount();
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 15;
		time = 3 * 60 * 20;;
		cooldown = 0;
		topScore = true;
		score = 0;
		see = true;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void initializeMiniGame()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.주먹, GameItem.가위, GameItem.보, GameItem.주먹, GameItem.가위, GameItem.보, GameItem.주먹, GameItem.가위, GameItem.보);
		}
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
}
