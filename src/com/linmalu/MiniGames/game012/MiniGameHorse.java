package com.linmalu.minigames.game012;

import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;

public class MiniGameHorse
{
	private final GameData data = Main.getMain().getGameData();

	public MiniGameHorse()
	{
		for(Player p : data.getPlayers())
		{
			createHorse(p);
		}
	}
	public MiniGameHorse(Player player)
	{
		createHorse(player);
	}
	private void createHorse(Player player)
	{
		Horse h = data.getMapData().getWorld().spawn(player.getLocation(), Horse.class);
		h.setAge(0);
		h.setVariant(Variant.UNDEAD_HORSE);
		h.setTamed(true);
		h.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		h.setJumpStrength(0);
		h.setMaxHealth(20);
		h.setHealth(h.getMaxHealth());
		data.addEntity(h);
		h.setPassenger(player);
	}
}
