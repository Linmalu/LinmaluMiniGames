package com.linmalu.MiniGames.Game01;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG01_Rule
{
	public MG01_Rule()
	{
		String[] msgs = {
				" = = = = = [ 모 루 피 하 기 게 임 ] = = = = =",
				"모루피하기 게임은 하늘에서 떨어지는 모루를 피하는 게임입니다.",
				"시간이 지날수록 떨어지는 블록은 늘어납니다.",
				"모루 맞으면 탈락이 되며, 1명이 남을 때까지 게임이 진행됩니다."
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
