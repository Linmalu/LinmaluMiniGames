package com.linmalu.MiniGames.Game01;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.MiniGames.Main;

public class MG01_Rule
{
	public MG01_Rule()
	{
		String[] msgs = {
				" = = = = = [ �� �� �� �� �� �� �� ] = = = = =",
				"������ϱ� ������ �ϴÿ��� �������� ��縦 ���ϴ� �����Դϴ�.",
				"�ð��� �������� �������� ����� �þ�ϴ�.",
				"��� ������ Ż���� �Ǹ�, 1���� ���� ������ ������ ����˴ϴ�."
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
