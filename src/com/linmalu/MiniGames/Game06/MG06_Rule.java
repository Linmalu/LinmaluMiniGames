package com.linmalu.MiniGames.Game06;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG06_Rule
{
	public MG06_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� �� �� ] = = = = =",
				"�����Ա� ������ �ڽ��� �Ʒ��� �ִ� ����� �ڽ��� ������� �ٲ�� �����Դϴ�.",
				"�ִ� �ο��� 16���Դϴ�.",
				"1���� ���� ������ �̵��ӵ��� �����մϴ�.",
				"���� ������ �� ������ ���� ��� 10�� ���� ���ӿ� ������ �� �����ϴ�.",
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
