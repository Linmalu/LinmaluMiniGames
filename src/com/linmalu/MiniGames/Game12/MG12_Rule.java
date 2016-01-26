package com.linmalu.MiniGames.Game12;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG12_Rule
{
	public MG12_Rule()
	{
		String[] msgs = {
				" = = = = = [ 경 마 게 임 ] = = = = =",
				"경마 게임은 말을 타고 경주하는 게임입니다.",
				"점프로 가속 스킬을 쓸 수 있습니다.",
				"떨어지면 시작 지점에서 다시 시작합니다.",
				"목적지에 먼저 도착하는 플레이어가 승리합니다."
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
