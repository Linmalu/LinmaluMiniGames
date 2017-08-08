package com.linmalu.minigames.data;

import java.io.File;
import java.util.List;

import com.linmalu.library.api.LinmaluConfig;
import com.linmalu.minigames.Main;

public class LanguageData
{
	private static final LinmaluConfig config = new LinmaluConfig(new File(Main.getMain().getDataFolder(), "ko_kr.lang"));

	public static String getLanguage(String msg)
	{
		return config.getString(msg, "");
	}
	public static List<String> getLanguages(String msg)
	{
		return config.getStringList(msg);
	}
}
