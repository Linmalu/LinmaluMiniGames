package com.linmalu.MiniGames.Game02;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG02_Rule
{
	public MG02_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� ] = = = = =",
				"��� ������ ���̴� ����� �ö󰡴� �����Դϴ�.",
				"���� ������ �� �ֽ��ϴ�.",
				"����� �ν� �� ������ �ν� ��� �������� ���ɴϴ�.",
				"��ǥ ��ġ���� ���� �ö󰡴� �÷��̾ �¸��ϰ� �˴ϴ�."
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
