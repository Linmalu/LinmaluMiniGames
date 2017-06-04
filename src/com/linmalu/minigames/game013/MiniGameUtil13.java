package com.linmalu.minigames.game013;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.minigames.data.GameTimer;
import com.linmalu.minigames.data.MapData;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameUtil;

public class MiniGameUtil13 extends MiniGameUtil
{
	private final BlockItem[] blockItems = {new BlockItem(Material.DIRT, GameItem.삽), new BlockItem(Material.SAND, GameItem.삽), new BlockItem(Material.GRAVEL, GameItem.삽), new BlockItem(Material.WOOD, GameItem.도끼), new BlockItem(Material.LOG, GameItem.도끼), new BlockItem(Material.LOG_2, GameItem.도끼), new BlockItem(Material.STONE, GameItem.곡괭이), new BlockItem(Material.BRICK, GameItem.곡괭이), new BlockItem(Material.MOSSY_COBBLESTONE, GameItem.곡괭이)};

	public MiniGameUtil13(MiniGame minigame)
	{
		super(minigame, new String[]{" = = = = = [ 블 록 부 수 기 게 임 ] = = = = =", "블록부수기 게임은 불록을 빨리 부수는 게임입니다.", "블록을 먼저 부수는 플레이어가 승리합니다."});
		mapHeight = 50;
	}
	@Override
	public MaterialData getChunkData(int y)
	{
		return new MaterialData(Material.AIR);
		// Random random = new Random(seed);
		// for(int y = MAP_DEFAULT_HEIGHT; y <= mapHeight; y++)
		// {
		// int ran = random.nextInt(blockItems.length);
		// for(int x = 0; x < 16; x++)
		// {
		// for(int z = 0; z < 16; z++)
		// {
		// if(y != MAP_DEFAULT_HEIGHT && y != mapHeight && Math.abs(chunkX * 16 + x) % 5 == 2 && Math.abs(chunkZ * 16 + z) % 5 == 2)
		// {
		// if(y < mapHeight - 2)
		// {
		// cd.setBlock(x, y, z, blockItems[ran].getMaterial());
		// }
		// }
		// else
		// {
		// cd.setBlock(x, y, z, Material.BARRIER);
		// }
		// }
		// }
		// }
		// return cd;
	}
	@Override
	public void moveWorld(Player player)
	{
		PlayerData pd = data.getPlayerData(player.getUniqueId());
		int size = (int)Math.ceil(Math.sqrt(data.getPlayerAllCount()));
		int number = pd.getNumber();
		player.teleport(new Location(data.getMapData().getWorld(), number % size * 5 + 2.5, mapHeight - 2, number / size * 5 + 2.5));
	}
	@Override
	public MapData getMapData(World world)
	{
		topScore = true;
		return new MapData(world, x1, z1, x2, z2, mapHeight >= 0 ? mapHeight : MAP_DEFAULT_HEIGHT, time, cooldown, topScore, mapHeight - MAP_DEFAULT_HEIGHT - 3, see);
	}
	@Override
	public void addRandomItem(Player player)
	{
	}
	// @Override
	// public void reload() throws IOException
	// {
	// mapHeight = config.getInt(getConfigPath(MAP_HEIGHT), 50) + MAP_DEFAULT_HEIGHT;
	// time = (timeDefault + (Main.getMain().getGameData().getPlayerAllCount() * timePlayer));
	// topScore = true;
	// score = mapHeight - 13;
	// config.remove(minigame.toString());
	// config.set(getConfigPath(MAP_HEIGHT), mapHeight - MAP_DEFAULT_HEIGHT);
	// }
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
		for(Player player : data.getLivePlayers())
		{
			Location loc = player.getLocation();
			loc.setY(mapHeight - data.getPlayerData(player.getUniqueId()).getScore() - 3);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 4, false, false), true);
			for(BlockItem blockItem : blockItems)
			{
				if(loc.getBlock().getType() == blockItem.getMaterial() && GameItem.getGameItem(player.getInventory().getItemInMainHand()) == blockItem.getGameItem())
				{
					player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
					continue;
				}
			}
		}
	}
	@Override
	public void endTimer()
	{
	}

	private class BlockItem
	{
		private Material material;
		private GameItem gameItem;

		private BlockItem(Material material, GameItem gameItem)
		{
			this.material = material;
			this.gameItem = gameItem;
		}
		private Material getMaterial()
		{
			return material;
		}
		private GameItem getGameItem()
		{
			return gameItem;
		}
	}
}
