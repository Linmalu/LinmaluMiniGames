package com.linmalu.MiniGames.Game07;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG07_Rule
{
	public MG07_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� �� ] = = = = =",
				"�ѽο� ������ ���� ��� �����Դϴ�.",
				"���� �� Ŭ������ �� �� �ֽ��ϴ�.",
				"���� ��Ÿ���� 1���Դϴ�.",
				"�ѿ� ���� ��� 10�� ���� ���ӿ� ������ �� �����ϴ�.",
				"��ǥ ������ ���� �����ϴ� �÷��̾ �¸��մϴ�."
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
