package com.linmalu.MiniGames.Game08;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG08_Rule
{
	public MG08_Rule()
	{
		String[] msgs = {
				" = = = = = [ 폭 탄 피 하 기 게 임 ] = = = = =",
				"폭탄피하기 게임은 폭탄을 피해서 도망가는 게임입니다.",
				"폭탄이 된 사람에게 맞으면 폭탄을 넘겨받게 됩니다.",
				"제한시간이 지나면 폭탄이 터지며 탈락합니다.",
				"1명이 남을 때까지 게임이 진행됩니다."
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
