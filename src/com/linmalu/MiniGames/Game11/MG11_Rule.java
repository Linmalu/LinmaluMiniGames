package com.linmalu.MiniGames.Game11;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG11_Rule
{
	public MG11_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� ȣ �� �� �� �� �� ] = = = = =",
				"��ȣ���� ������ ����� ���� ���Ͽ� ������� �����Դϴ�.",
				"����� ������ �ʷϻ� -> ����� -> ������ -> ����� �����Դϴ�.",
				"���� ������ �� �ֽ��ϴ�.",
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
