package com.linmalu.MiniGames.Game06;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG06_Rule
{
	public MG06_Rule()
	{
		String[] msgs = {
				" = = = = = [ 땅 따 먹 기 게 임 ] = = = = =",
				"땅따먹기 게임은 자신의 아래에 있는 블록이 자신의 블록으로 바뀌는 게임입니다.",
				"최대 인원은 16명입니다.",
				"1분이 지날 때마다 이동속도가 증가합니다.",
				"서로 공격할 수 있으며 맞을 경우 10초 동안 게임에 참여할 수 없습니다.",
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
