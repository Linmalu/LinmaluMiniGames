package com.linmalu.minigames.game;

import org.bukkit.event.Listener;

import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGames;

public abstract class MiniGameEvent implements Listener
{
	protected final MiniGames minigame;
	protected final GameData data;

	public MiniGameEvent(MiniGames minigame)
	{
		this.minigame = minigame;
		this.data = Main.getMain().getGameData();
	}
}
