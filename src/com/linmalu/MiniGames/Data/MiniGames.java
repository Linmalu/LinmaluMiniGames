package com.linmalu.minigames.data;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.game.MiniGameEvent;
import com.linmalu.minigames.game.MiniGameUtil;

public enum MiniGames
{
	달리기, 모루피하기, 등반, 눈치, 땅파기, 꼬리피하기, 땅따먹기, 총싸움, 폭탄피하기, 양털찾기, 카트타기, 신호등블록, 경마;

	private MiniGameUtil util;

	public MiniGameUtil getUtil()
	{
		return util;
	}
	private void InitializationField()
	{
		util = getLinmaluClsss(MiniGameUtil.class, getMiniGames());
		Bukkit.getPluginManager().registerEvents(getLinmaluClsss(MiniGameEvent.class, getMiniGames()), Main.getMain());
	}
	private <T> T getLinmaluClsss(Class<T> cast, Object ... args)
	{
		try
		{
			Class<?> cls = Class.forName("com.linmalu.minigames.game" + String.format("%03d", ordinal()) + "." + cast.getSimpleName() + ordinal());
			if(args == null)
			{
				return cast.cast(cls.newInstance());
			}
			else
			{
				ArrayList<Class<?>> list = new ArrayList<>();
				for(Object arg : args)
				{
					list.add(arg.getClass());
				}
				Constructor<?> con = cls.getConstructor(list.toArray(new Class[list.size()]));
				return cast.cast(con.newInstance(args));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	private MiniGames getMiniGames()
	{
		return values()[ordinal()];
	}

	private static boolean initialize = false;

	public static void initialize()
	{
		if(initialize)
		{
			return;
		}
		else
		{
			initialize = true;
		}
		for(MiniGames mg : values())
		{
			mg.InitializationField();
		}
	}
}
