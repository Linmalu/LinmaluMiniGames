package com.linmalu.minigames;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.linmalu.minigames.data.DeleteWorld;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;

public final class Main extends JavaPlugin
{
	public static final String WORLD_NAME = "LMG";
	public static final String RESOURCEPACK_MINIGAMES = "https://www.dropbox.com/s/7v4z2zo3dp5w3o3/MiniGames.zip?dl=1";
	//public static final String RESOURCEPACK_MINIGAMES = "http://minecraft.linmalu.com/MiniGames/MiniGames.zip";
	public static final String RESOURCEPACK_DEFAULT = "https://www.dropbox.com/s/kp5sgglxtcz1avx/Default.zip?dl=1";
	//public static final String RESOURCEPACK_DEFAULT = "http://www.linmalu.com/plugin/MiniGames/Default.zip";
	private static Main main;
	private GameData gamedata;

	public void onEnable()
	{
		main = this;
		gamedata = new GameData();
		getCommand(getDescription().getName()).setExecutor(new Main_Command());
		getServer().getPluginManager().registerEvents(new Main_Event(), this);
		MiniGame.initialize();
		new DeleteWorld();
		getLogger().info("제작 : 린마루");
	}
	public void onDisable()
	{
		getLogger().info("제작 : 린마루");
	}
	public static Main getMain()
	{
		return main;
	}
	public GameData getGameData()
	{
		return gamedata;
	}
	public String getTitle()
	{
		return ChatColor.AQUA + "[" + getDescription().getDescription() + "] ";
	}
}
