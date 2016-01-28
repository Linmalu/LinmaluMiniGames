package com.linmalu.minigames.game;

import org.bukkit.event.Listener;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;

public abstract class MiniGameEvent implements Listener
{
	protected final MiniGame minigame;
	protected final GameData data;

	public MiniGameEvent(MiniGame minigame)
	{
		this.minigame = minigame;
		this.data = Main.getMain().getGameData();
	}
}
