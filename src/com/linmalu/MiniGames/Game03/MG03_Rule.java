package com.linmalu.MiniGames.Game03;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG03_Rule
{
	public MG03_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� ġ �� �� ] = = = = =",
				"��ġ ������ ���������� �����Դϴ�.",
				"�ָ� -> ���� -> �� -> �ָ� �����Դϴ�.",
				"���������� ������ 3�ʿ� �� ���� ������ �� �ֽ��ϴ�.",
				"������ �ϸ� ������������ �ϰ� �Ǿ� ���а� �����˴ϴ�.",
				"��ġ���ӿ� ���� ��� 10�� ���� ���ӿ� ������ �� �����ϴ�.",
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
