package com.linmalu.MiniGames.Game00;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG00_Rule
{
	public MG00_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� �� ] = = = = =",
				"�޸��� ������ �ڽ��� �Ʒ��� �ִ� ����� �ð��� ������ �������ϴ�.",
				"���� ������ �� �ֽ��ϴ�.",
				"�������� �� Ŭ�� �� ��ų�� ���Ǹ� ��Ÿ���� 30���Դϴ�.",
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
