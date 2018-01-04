package com.linmalu.minigames;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.linmalu.library.api.LinmaluMain;
import com.linmalu.minigames.data.DeleteWorld;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;

public class Main extends LinmaluMain
{
	public static final String WORLD_NAME = "LMG";
	public static final String RESOURCEPACK_MINIGAMES = "https://www.dropbox.com/s/7v4z2zo3dp5w3o3/MiniGames.zip?dl=1";
	public static final String RESOURCEPACK_DEFAULT = "https://www.dropbox.com/s/kp5sgglxtcz1avx/Default.zip?dl=1";

	public static Main getMain()
	{
		return (Main)LinmaluMain.getMain();
	}

	private GameData gamedata;

	@Override
	public void onEnable()
	{
		super.onEnable();
		if(!getDataFolder().exists())
		{
			getDataFolder().mkdirs();
		}
		for(String name : new String[]{"world0", "world1", "ko_kr.lang"})
		{
			try(InputStream in = MiniGame.class.getResourceAsStream("/res/" + name))
			{
				Files.copy(in, new File(Main.getMain().getDataFolder(), name).toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			catch(Exception e)
			{
			}
		}
		gamedata = new GameData();
		registerCommand(new Main_Command());
		registerEvents(new Main_Event());
		getGameData().reload();
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
