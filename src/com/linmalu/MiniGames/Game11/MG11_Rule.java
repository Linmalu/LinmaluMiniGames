package com.linmalu.MiniGames.Game11;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG11_Rule
{
	public MG11_Rule()
	{
		String[] msgs = {
				" = = = = = [ 신 호 등 블 럭 게 임 ] = = = = =",
				"신호등블록 게임은 블록의 색이 변하여 사라지는 게임입니다.",
				"블록의 순서는 초록색 -> 노란색 -> 빨간색 -> 사라짐 순서입니다.",
				"서로 공격할 수 있습니다.",
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
