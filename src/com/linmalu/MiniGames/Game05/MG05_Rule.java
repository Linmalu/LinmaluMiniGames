package com.linmalu.MiniGames.Game05;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG05_Rule
{
	public MG05_Rule()
	{
		String[] msgs = {
				" = = = = = [ 꼬 리 피 하 기 게 임 ] = = = = =",
				"꼬리피하기 게임은 꼬리에 부딪치지 않고 피하는 게임입니다.",
				"플레이어는 자동으로 앞으로 움직입니다.",
				"첫 번째 꼬리에 닿아도 죽지 않습니다.",
				"부딪치면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
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
