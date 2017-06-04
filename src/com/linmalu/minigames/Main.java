package com.linmalu.minigames;

import com.linmalu.library.api.LinmaluMain;
import com.linmalu.minigames.data.DeleteWorld;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;

public class Main extends LinmaluMain
{
	public static final String WORLD_NAME = "LMG";
	public static final String RESOURCEPACK_MINIGAMES = "https://www.dropbox.com/s/7v4z2zo3dp5w3o3/MiniGames.zip?dl=1";
	// public static final String RESOURCEPACK_MINIGAMES = "http://minecraft.linmalu.com/MiniGames/MiniGames.zip";
	public static final String RESOURCEPACK_DEFAULT = "https://www.dropbox.com/s/kp5sgglxtcz1avx/Default.zip?dl=1";
	// public static final String RESOURCEPACK_DEFAULT = "http://www.linmalu.com/plugin/MiniGames/Default.zip";

	public static Main getMain()
	{
		return (Main)LinmaluMain.getMain();
	}

	private GameData gamedata;

	@Override
	public void onEnable()
	{
		super.onEnable();
		gamedata = new GameData();
		registerCommand(new Main_Command());
		registerEvents(new Main_Event());
		MiniGame.initialize();
		new DeleteWorld();
	}
	@Override
	public void onDisable()
	{
		super.onDisable();
		if(gamedata.isGame1())
		{
			gamedata.GameStop();
		}
	}
	public GameData getGameData()
	{
		return gamedata;
	}
}
