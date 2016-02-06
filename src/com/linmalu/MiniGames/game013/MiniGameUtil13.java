package com.linmalu.minigames.game013;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluYamlConfiguration;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil13 extends MiniGameUtil
{
	private final Material[] types = {Material.STONE, Material.GRASS, Material.DIRT, Material.COBBLESTONE, Material.WOOD, Material.SAND, Material.GRAVEL, 	Material.LOG, Material.SPONGE, Material.GLASS, Material.LAPIS_BLOCK, Material.SANDSTONE,
			Material.WOOL, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.BRICK, Material.BOOKSHELF,	Material.MOSSY_COBBLESTONE, Material.OBSIDIAN, Material.DIAMOND_BLOCK, Material.REDSTONE_BLOCK, Material.PACKED_ICE,
			Material.SNOW_BLOCK, Material.CLAY, Material.PUMPKIN, Material.NETHERRACK, Material.SOUL_SAND, Material.GLOWSTONE, Material.LOG_2};
	
	public MiniGameUtil13(MiniGame minigame)
	{
		super(minigame, new String[]{
				" = = = = = [ 블 록 부 수 기 게 임 ] = = = = =",
				"블록부수기 게임은 불록을 빨리 부수는 게임입니다.",
				"블록을 먼저 부수는 플레이어가 승리합니다."
		});
	}
	@Override
	public MapData getMapData(World world)
	{
		int time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer)) * 20;
		x2 = z2 = (int) (Math.ceil(Math.sqrt(data.getPlayerAllCount())) * 3);
		cooldown = 0;
		topScore = true;
		score = mapHeight - 12;
		see = false;
		return new MapData(world, x1, x2, z1, z2, mapHeight, time, cooldown, topScore, score, see);
	}
	@Override
	public void createGameMap()
	{
		MapData md = Main.getMain().getGameData().getMapData();
		int size = (int) Math.ceil(Math.sqrt(data.getPlayerAllCount())) * 3;
		for(int y = 10; y <= md.getMapHeight(); y++)
		{
			int ran = new Random().nextInt(types.length);
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					Block block = md.getWorld().getBlockAt(x, y, z);
					if(x % 3 == 1 && z % 3 == 1 && y != md.getMapHeight())
					{
						if(y != md.getMapHeight() -1 && y != md.getMapHeight() - 2)
						{
							block.setType(types[ran]);
						}
					}
					else
					{
						block.setType(Material.BARRIER);
					}
				}
			}
		}
	}
	@Override
	public void moveWorld(Player player)
	{
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		int size = (int) Math.ceil(Math.sqrt(data.getPlayerAllCount()));
		int number = pd.getNumber();
		Location loc = player.getLocation();
		loc.setWorld(data.getMapData().getWorld());
		loc.setX(number % size * 3 + 1.5);
		loc.setY(mapHeight - 2);
		loc.setZ(number / size * 3 + 1.5);
		player.teleport(loc);
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
			config.set(MAP_HEIGHT, 50);
		}
		mapHeight = config.getInt(MAP_HEIGHT) + 10;
		config.save(file);
	}
	@Override
	public void startTimer()
	{
		for(Player player : data.getLivePlayers())
		{
			GameItem.setItemStack(player, GameItem.삽, GameItem.도끼, GameItem.곡괭이, GameItem.삽, GameItem.도끼, GameItem.곡괭이, GameItem.삽, GameItem.도끼, GameItem.곡괭이);
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
