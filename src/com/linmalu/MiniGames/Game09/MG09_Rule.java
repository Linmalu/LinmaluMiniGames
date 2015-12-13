package com.linmalu.MiniGames.Game09;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG09_Rule
{
	public MG09_Rule()
	{
		String[] msgs = {
				" = = = = = [ 양 털 찾 기 게 임 ] = = = = =",
				"양털찾기 게임은 제한시간 안에 양털을 찾는 게임입니다.",
				"제한시간이 되면 지정되지 않은 양털은 모두 사라집니다.",
				"떨어지면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
		};
		for(Player player : Main.getMain().getGameData().getPlayers())
		{
			for(String msg : msgs)
			{
				player.sendMessage(ChatColor.GREEN + msg);
			}
		}
	}
}
