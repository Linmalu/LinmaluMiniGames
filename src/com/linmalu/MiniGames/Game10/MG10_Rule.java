package com.linmalu.MiniGames.Game10;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG10_Rule
{
	public MG10_Rule()
	{
		String[] msgs = {
				" = = = = = [ 카 트 타 기 게 임 ] = = = = =",
				"카트타기 게임은 제한시간 안에 카트를 타는 게임입니다.",
				"카트는 살아있는 사람 수보다 적게 나옵니다.",
				"제한시간이 지나면 카트에 타지 않은 사람은 탈락합니다.",
				"최종 1인이 남을 때까지 게임이 진행됩니다."
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
