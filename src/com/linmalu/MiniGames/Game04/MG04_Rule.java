package com.linmalu.MiniGames.Game04;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG04_Rule
{
	public MG04_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� �� ] = = = = =",
				"���ı� ������ ����� �μż� �ٸ� �÷��̾ ����Ʈ���� �����Դϴ�.",
				"���� ������ �� �ֽ��ϴ�.",
				"����� �νø� ���� Ȯ���� �������� ���ɴϴ�.",
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
