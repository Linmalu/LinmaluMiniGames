package com.linmalu.minigames.game003;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil3 extends MiniGameUtil
{
	public MiniGameUtil3(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 눈 치 게 임 ] = = = = =",
				"눈치 게임은 가위바위보 게임입니다.",
				"주먹 -> 가위 -> 보 -> 주먹 순서입니다.",
				"가위바위보 변경은 3초에 한 번씩 변경할 수 있습니다.",
				"공격을 하면 가위바위보를 하게 되어 승패가 결정됩니다.",
				"눈치게임에 졌을 경우 10초 동안 게임에 참여할 수 없습니다.",
				"제한시간 안에 점수가 높은 플레이어가 승리합니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 15;
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
						block.setType(Material.HAY_BLOCK);
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
			config.set(MAP_DEFAULT, 5);
			config.set(MAP_PLAYER, 1);
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
			GameItem.setItemStack(player, GameItem.주먹, GameItem.가위, GameItem.보, GameItem.주먹, GameItem.가위, GameItem.보, GameItem.주먹, GameItem.가위, GameItem.보);
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
	}
	@Override
	public void endTimer()
	{
	}
}
