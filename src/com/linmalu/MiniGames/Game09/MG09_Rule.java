package com.linmalu.MiniGames.Game09;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG09_Rule
{
	public MG09_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� ã �� �� �� ] = = = = =",
				"����ã�� ������ ���ѽð� �ȿ� ������ ã�� �����Դϴ�.",
				"���ѽð��� �Ǹ� �������� ���� ������ ��� ������ϴ�.",
				"�������� Ż���� �Ǹ�, 1���� ���� ������ ������ ����˴ϴ�."
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
