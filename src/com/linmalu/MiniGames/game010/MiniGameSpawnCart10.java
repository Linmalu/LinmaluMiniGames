package com.linmalu.minigames.game010;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MapData;

public class MiniGameSpawnCart10
{
	public MiniGameSpawnCart10()
	{
		GameData data = Main.getMain().getGameData();
		MapData md = data.getMapData();
		for(int i = 0; i < data.getPlayerLiveCount() - 1; i++)
		{
			Random ran = new Random();
			int x = ran.nextInt(md.getX2() - md.getX1() -3) + md.getX1() + 2;
			int z = ran.nextInt(md.getZ2() - md.getZ1() -3) + md.getZ1() + 2;
			Location loc = new Location(md.getWorld(), x, md.getMapHeight(), z);
			data.addEntity(md.getWorld().spawnEntity(loc, EntityType.MINECART));
		}
	}
}
