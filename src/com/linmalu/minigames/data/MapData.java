package com.linmalu.minigames.data;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

public class MapData
{
	private World world;
	private int x1, z1, x2, z2, mapHeight, time, cooldown, score;
	private boolean topScore, see;

	public MapData(World world, int x1, int z1, int x2, int z2, int mapHeight, int time, int cooldown, boolean topScore, int score, boolean see)
	{
		this.world = world;
		if(x1 < x2)
		{
			this.x1 = x1;
			this.x2 = x2;
		}
		else
		{
			this.x1 = x2;
			this.x2 = x1;
		}
		if(z1 < z2)
		{
			this.z1 = z1;
			this.z2 = z2;
		}
		else
		{
			this.z1 = z2;
			this.z2 = z1;
		}
		this.mapHeight = mapHeight;
		this.time = time * 10;
		this.cooldown = cooldown * 10;
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
	public int getZ1()
	{
		return z1;
	}
	public int getX2()
	{
		return x2;
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
	public Location getRandomLocation()
	{
		return getRandomLocation(0, 0);
	}
	public Location getRandomLocation(Location loc)
	{
		return getRandomLocation(loc.getYaw(), loc.getPitch());
	}
	public Location getRandomLocation(float yaw, float pitch)
	{
		Random ran = new Random();
		double x = ran.nextInt(x2 - x1 + 1) + x1 + 0.5D;
		double z = ran.nextInt(z2 - z1 + 1) + z1 + 0.5D;
		return new Location(world, x, mapHeight + 1, z, yaw, pitch);
	}
	public Location getRandomBlock()
	{
		Random ran = new Random();
		double x = ran.nextInt(x2 - x1 + 1) + x1 + 0.5D;
		double z = ran.nextInt(z2 - z1 + 1) + z1 + 0.5D;
		return new Location(world, x, mapHeight, z);
	}
}
