package com.linmalu.MiniGames.Game12;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG12_Rule
{
	public MG12_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� ] = = = = =",
				"�渶 ������ ���� Ÿ�� �����ϴ� �����Դϴ�.",
				"������ ���� ��ų�� �� �� �ֽ��ϴ�.",
				"�������� ���� �������� �ٽ� �����մϴ�.",
				"�������� ���� �����ϴ� �÷��̾ �¸��մϴ�."
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
