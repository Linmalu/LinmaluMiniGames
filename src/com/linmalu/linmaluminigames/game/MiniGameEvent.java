package com.linmalu.linmaluminigames.game;

import org.bukkit.event.Listener;

import com.linmalu.linmaluminigames.Main;
import com.linmalu.linmaluminigames.data.GameData;
import com.linmalu.linmaluminigames.data.MiniGames;

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
