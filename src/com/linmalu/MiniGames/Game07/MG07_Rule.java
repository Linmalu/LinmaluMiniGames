package com.linmalu.MiniGames.Game07;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG07_Rule
{
	public MG07_Rule()
	{
		String[] msgs = {
				" = = = = = [ 총 싸 움 게 임 ] = = = = =",
				"총싸움 게임은 총을 쏘는 게임입니다.",
				"총을 우 클릭으로 쏠 수 있습니다.",
				"총의 쿨타임은 1초입니다.",
				"총에 맞을 경우 10초 동안 게임에 참여할 수 없습니다.",
				"목표 점수에 먼저 도달하는 플레이어가 승리합니다."
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
