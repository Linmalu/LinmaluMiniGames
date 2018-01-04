package com.linmalu.minigames.data;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.types.ConfigType;

public class Languages
{
	private static JsonObject json;

	private static JsonObject getJson()
	{
		if(json == null)
		{
			try
			{
				json = new JsonParser().parse(new String(Files.readAllBytes(new File(Main.getMain().getDataFolder(), "ko_kr.lang").toPath()), Charset.forName("UTF-8"))).getAsJsonObject();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return json;
	}
	public static List<String> getRules(MiniGame mg)
	{
		List<String> rules = new ArrayList<>();
		getJson().get(mg.getName()).getAsJsonObject().get("rule").getAsJsonArray().forEach(je -> rules.add(je.getAsString()));
		return rules;
	}
	public static String getConfigTypeName(ConfigType type)
	{
		return getJson().get("config").getAsJsonObject().get(type.toString().toLowerCase()).getAsString();
	}
}
