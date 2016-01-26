package com.linmalu.MiniGames.Game02;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG02_Rule
{
	public MG02_Rule()
	{
		String[] msgs = {
				" = = = = = [ 등 반 게 임 ] = = = = =",
				"등반 게임은 쌓이는 블록을 올라가는 게임입니다.",
				"서로 공격할 수 있습니다.",
				"블록은 부실 수 있으며 부실 경우 아이템이 나옵니다.",
				"목표 위치까지 먼저 올라가는 플레이어가 승리하게 됩니다."
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
