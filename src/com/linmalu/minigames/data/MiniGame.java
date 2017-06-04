package com.linmalu.minigames.data;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.game.MiniGameEvent;
import com.linmalu.minigames.game.MiniGameUtil;

public enum MiniGame
{
	달리기, 모루피하기, 등반, 눈치, 땅파기, 꼬리피하기, 땅따먹기, 총싸움, 폭탄피하기, 양털찾기, 카트타기, 신호등블록, 경마, 블록부수기, 진짜를찾아라;

	private MiniGameUtil instance;

	public MiniGameUtil getInstance()
	{
		return instance;
	}
	private void InitializationField()
	{
		instance = getLinmaluClsss(MiniGameUtil.class, getMiniGame());
		Main.getMain().registerEvents(getLinmaluClsss(MiniGameEvent.class, getMiniGame()));
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
	private MiniGame getMiniGame()
	{
		return valueOf(toString());
	}

	private static boolean initialize = false;

	public static void initialize()
	{
		if(!initialize)
		{
			initialize = true;
			for(MiniGame mg : values())
			{
				mg.InitializationField();
			}
			MiniGameUtil.reloadConfig();
		}
	}
}
