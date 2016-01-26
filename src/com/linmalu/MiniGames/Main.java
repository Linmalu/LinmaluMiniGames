package com.linmalu.MiniGames;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.linmalu.MiniGames.Data.DeleteWorld;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Game00.MG00_Event;
import com.linmalu.MiniGames.Game01.MG01_Event;
import com.linmalu.MiniGames.Game02.MG02_Event;
import com.linmalu.MiniGames.Game03.MG03_Event;
import com.linmalu.MiniGames.Game04.MG04_Event;
import com.linmalu.MiniGames.Game05.MG05_Event;
import com.linmalu.MiniGames.Game06.MG06_Event;
import com.linmalu.MiniGames.Game07.MG07_Event;
import com.linmalu.MiniGames.Game08.MG08_Event;
import com.linmalu.MiniGames.Game09.MG09_Event;
import com.linmalu.MiniGames.Game10.MG10_Event;
import com.linmalu.MiniGames.Game12.MG12_Event;

public final class Main extends JavaPlugin
{
//	public static final String title = ChatColor.AQUA + "[미니게임천국] ";
	public static final String world = "LMG";
	public static final String resourcePackMiniGames = "https://www.dropbox.com/s/7v4z2zo3dp5w3o3/MiniGames.zip?dl=1";
//	public static final String resourcePackMiniGames = "http://minecraft.linmalu.com/MiniGames/MiniGames.zip";
	public static final String resourcePackDefault = "https://www.dropbox.com/s/kp5sgglxtcz1avx/Default.zip?dl=1";
//	public static final String resourcePackDefault = "http://www.linmalu.com/plugin/MiniGames/Default.zip";
	private static Main main;
	private GameData gamedata;

	public void onEnable()
	{
		main = this;
		gamedata = new GameData();
		getCommand(getDescription().getName()).setExecutor(new Main_Command());
		getServer().getPluginManager().registerEvents(new Main_Event(), this);
		getServer().getPluginManager().registerEvents(new MG00_Event(), this);
		getServer().getPluginManager().registerEvents(new MG01_Event(), this);
		getServer().getPluginManager().registerEvents(new MG02_Event(), this);
		getServer().getPluginManager().registerEvents(new MG03_Event(), this);
		getServer().getPluginManager().registerEvents(new MG04_Event(), this);
		getServer().getPluginManager().registerEvents(new MG05_Event(), this);
		getServer().getPluginManager().registerEvents(new MG06_Event(), this);
		getServer().getPluginManager().registerEvents(new MG07_Event(), this);
		getServer().getPluginManager().registerEvents(new MG08_Event(), this);
		getServer().getPluginManager().registerEvents(new MG09_Event(), this);
		getServer().getPluginManager().registerEvents(new MG10_Event(), this);
		getServer().getPluginManager().registerEvents(new MG12_Event(), this);
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
