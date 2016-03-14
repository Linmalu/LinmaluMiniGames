package com.linmalu.minigames.data;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

public class MapData
{
	private World world;
	private int x1, x2, z1, z2, mapHeight, time, cooldown, score;
	private boolean topScore, see;

	public MapData(World world, int x1, int x2, int z1, int z2, int mapHeight, int time, int cooldown, boolean topScore, int score, boolean see)
	{
		this.world = world;
		this.x1 = x1;
		this.x2 = x2;
		this.z1 = z1;
		this.z2 = z2;
		this.mapHeight = mapHeight;
		this.time = time;
		this.cooldown = cooldown;
		this.topScore = topScore;
		this.score = score;
		this.see = see;
	}
	public World getWorld()
	{
		return world;
	}
	public int getX1()
	{
		return x1;
	}
	public int getX2()
	{
		return x2;
	}
	public int getZ1()
	{
		return z1;
	}
	public int getZ2()
	{
		return z2;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}
	public int getTime()
	{
		return time;
	}
	public void setTime(int tiem)
	{
		this.time = tiem;
	}
	public int getCooldown()
	{
		return cooldown;
	}
	public boolean isTopScore()
	{
		return topScore;
	}
	public int getScore()
	{
		return score;
	}
	public boolean isSee()
	{
		return see;
	}
	public Location getRandomLocation(int size)
	{
		return getRandomLocation(size, 0, 0);
	}
	public Location getRandomLocation(int size, Location loc)
	{
		return getRandomLocation(size, loc.getYaw(), loc.getPitch());
	}
	public Location getRandomLocation(int size, float yaw, float pitch)
	{
		Random ran = new Random();
		return new Location(world, ran.nextInt(x2 - x1 + 1 - size * 2) + x1 + 0.5 + size, mapHeight, ran.nextInt(z2 - z1 + 1 - size * 2) + z1 + 0.5 + size, yaw, pitch);
	}
}
