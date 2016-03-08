package com.linmalu.minigames.data;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.game.MiniGameEvent;
import com.linmalu.minigames.game.MiniGameUtil;

public enum MiniGame
{
	달리기, 모루피하기, 등반, 눈치, 땅파기, 꼬리피하기, 땅따먹기, 총싸움, 폭탄피하기, 양털찾기, 카트타기, 신호등블록, 경마, 블록부수기;

	private MiniGameUtil handle;

	public MiniGameUtil getHandle()
	{
		return handle;
	}
	private void InitializationField()
	{
		handle = getLinmaluClsss(MiniGameUtil.class, getMiniGames());
		Main.getMain().registerEvents(getLinmaluClsss(MiniGameEvent.class, getMiniGames()));
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
	private MiniGame getMiniGames()
	{
		return valueOf(toString());
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
		for(MiniGame mg : values())
		{
			mg.InitializationField();
		}
	}
}
