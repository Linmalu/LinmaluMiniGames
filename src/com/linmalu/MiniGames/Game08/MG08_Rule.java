package com.linmalu.MiniGames.Game08;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG08_Rule
{
	public MG08_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� ź �� �� �� �� �� ] = = = = =",
				"��ź���ϱ� ������ ��ź�� ���ؼ� �������� �����Դϴ�.",
				"��ź�� �� ������� ������ ��ź�� �Ѱܹް� �˴ϴ�.",
				"���ѽð��� ������ ��ź�� ������ Ż���մϴ�.",
				"1���� ���� ������ ������ ����˴ϴ�."
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
