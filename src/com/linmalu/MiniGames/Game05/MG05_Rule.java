package com.linmalu.MiniGames.Game05;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG05_Rule
{
	public MG05_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� �� �� �� ] = = = = =",
				"�������ϱ� ������ ������ �ε�ġ�� �ʰ� ���ϴ� �����Դϴ�.",
				"�÷��̾�� �ڵ����� ������ �����Դϴ�.",
				"ù ��° ������ ��Ƶ� ���� �ʽ��ϴ�.",
				"�ε�ġ�� Ż���� �Ǹ�, 1���� ���� ������ ������ ����˴ϴ�."
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
