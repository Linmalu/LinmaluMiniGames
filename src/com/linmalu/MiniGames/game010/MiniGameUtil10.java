package com.linmalu.minigames.game010;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil10 extends MiniGameUtil
{
	public MiniGameUtil10(MiniGame minigame)
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
		int size = mapDefault + (Main.getMain().getGameData().getPlayerAllCount() * mapPlayer);
		x1 = z1 = -size;
		x2 = z2 = size;
		mapHeight = 20;
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
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
	@Override
	public void reloadConfig() throws IOException
	{
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		if(!file.exists())
		{
			config.set(MAP_DEFAULT, 5);
			config.set(MAP_PLAYER, 1);
			config.set(TIME_DEFAULT, 10);
			config.set(TIME_PLAYER, 0);
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
		MapData md = data.getMapData();
		for(int i = 0; i < data.getPlayerLiveCount() - 1; i++)
		{
			data.addEntity(md.getWorld().spawnEntity(md.getRandomEntityLocation(), EntityType.MINECART));
		}
	}
	@Override
	public void runTimer(GameTimer timer)
	{
	}
	@Override
	public void endTimer()
	{
		ArrayList<UUID> players = new ArrayList<>();
		for(Entity e : data.getEntitys())
		{
			if(!e.isEmpty())
			{
				Entity p = e.getPassenger();
				if(p.getType() == EntityType.PLAYER)
				{
					players.add(p.getUniqueId());
					e.eject();
				}
			}
			e.remove();
		}
		data.getEntitys().clear();
		for(Player player : data.getLivePlayers())
		{
			if(!players.contains(player.getUniqueId()))
			{
				data.diePlayer(player.getUniqueId());
			}
		}
	}
}
