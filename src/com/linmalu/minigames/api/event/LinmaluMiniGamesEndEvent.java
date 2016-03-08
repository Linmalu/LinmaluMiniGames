package com.linmalu.minigames.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.linmalu.minigames.data.MiniGame;

public class LinmaluMiniGamesEndEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private Player winner;
	private MiniGame minigame;

	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public LinmaluMiniGamesEndEvent(Player winner, MiniGame minigame)
	{
		this.winner = winner;
		this.minigame = minigame;
	}
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	public Player getWinner()
	{
		return winner;
	}
	public MiniGame getMiniGame()
	{
		return minigame;
	}
}
