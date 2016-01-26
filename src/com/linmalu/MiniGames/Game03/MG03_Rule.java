package com.linmalu.MiniGames.Game03;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG03_Rule
{
	public MG03_Rule()
	{
		String[] msgs = {
				" = = = = = [ 눈 치 게 임 ] = = = = =",
				"눈치 게임은 가위바위보 게임입니다.",
				"주먹 -> 가위 -> 보 -> 주먹 순서입니다.",
				"가위바위보 변경은 3초에 한 번씩 변경할 수 있습니다.",
				"공격을 하면 가위바위보를 하게 되어 승패가 결정됩니다.",
				"눈치게임에 졌을 경우 10초 동안 게임에 참여할 수 없습니다.",
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
