package com.linmalu.MiniGames.Game10;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG10_Rule
{
	public MG10_Rule()
	{
		String[] msgs = {
				" = = = = = [ ī Ʈ Ÿ �� �� �� ] = = = = =",
				"īƮŸ�� ������ ���ѽð� �ȿ� īƮ�� Ÿ�� �����Դϴ�.",
				"īƮ�� ����ִ� ��� ������ ���� ���ɴϴ�.",
				"���ѽð��� ������ īƮ�� Ÿ�� ���� ����� Ż���մϴ�.",
				"���� 1���� ���� ������ ������ ����˴ϴ�."
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
