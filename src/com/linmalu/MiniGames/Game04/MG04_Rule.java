package com.linmalu.MiniGames.Game04;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG04_Rule
{
	public MG04_Rule()
	{
		String[] msgs = {
				" = = = = = [ 땅 파 기 게 임 ] = = = = =",
				"땅파기 게임은 블록을 부셔서 다른 플레이어를 떨어트리는 게임입니다.",
				"서로 공격할 수 있습니다.",
				"블록을 부시면 일정 확률로 아이템이 나옵니다.",
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
