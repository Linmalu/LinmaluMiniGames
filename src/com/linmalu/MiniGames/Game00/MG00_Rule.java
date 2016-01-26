package com.linmalu.MiniGames.Game00;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG00_Rule
{
	public MG00_Rule()
	{
		String[] msgs = {
				" = = = = = [ 달 리 기 게 임 ] = = = = =",
				"달리기 게임은 자신의 아래에 있는 블록이 시간이 지나면 떨어집니다.",
				"서로 공격할 수 있습니다.",
				"아이템을 우 클릭 시 스킬이 사용되며 쿨타임은 30초입니다.",
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
