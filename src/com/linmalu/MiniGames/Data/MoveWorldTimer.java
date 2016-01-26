package com.linmalu.MiniGames.Data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.linmalu.LinmaluLibrary.API.LinmaluBossbar;
import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Game00.MG00_Rule;
import com.linmalu.MiniGames.Game01.MG01_Rule;
import com.linmalu.MiniGames.Game02.MG02_Rule;
import com.linmalu.MiniGames.Game03.MG03_Rule;
import com.linmalu.MiniGames.Game04.MG04_Rule;
import com.linmalu.MiniGames.Game05.MG05_Rule;
import com.linmalu.MiniGames.Game06.MG06_Rule;
import com.linmalu.MiniGames.Game07.MG07_Rule;
import com.linmalu.MiniGames.Game08.MG08_Rule;
import com.linmalu.MiniGames.Game09.MG09_Rule;
import com.linmalu.MiniGames.Game10.MG10_Rule;
import com.linmalu.MiniGames.Game11.MG11_Rule;
import com.linmalu.MiniGames.Game12.MG12_Rule;

public class MoveWorldTimer implements Runnable
{
	private int taskId;
	private GameData data = Main.getMain().getGameData();
	private int time = 10 * 20;

	public MoveWorldTimer()
	{
		gameRule();
		data.setGamePlayer();
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), this, 0L, 1L);
	}
	public void run()
	{
		if(data.isGame1() && time >= 0)
		{
			for(Player player : data.getPlayers())
			{
				player.setLevel(time / 20);
				player.setExp(time % 20 * 0.05F);
				LinmaluBossbar.setMessage(player, ChatColor.GREEN + data.getMinigame().toString() + "게임 시작 " + ChatColor.YELLOW + player.getLevel() + ChatColor.GREEN + "초전", player.getExp() * 100);
			}
			time--;
		}
		else
		{
			Bukkit.getScheduler().cancelTask(taskId);
			if(data.isGame1())
			{
				new GameTimer();
			}
		}
	}
	private void gameRule()
	{
		switch(data.getMinigame())
		{
		case 달리기:
			new MG00_Rule();
			break;
		case 모루피하기:
			new MG01_Rule();
			break;
		case 등반:
			new MG02_Rule();
			break;
		case 눈치:
			new MG03_Rule();
			break;
		case 땅파기:
			new MG04_Rule();
			break;
		case 꼬리피하기:
			new MG05_Rule();
			break;
		case 땅따먹기:
			new MG06_Rule();
			break;
		case 총싸움:
			new MG07_Rule();
			break;
		case 폭탄피하기:
			new MG08_Rule();
			break;
		case 양털찾기:
			new MG09_Rule();
			break;
		case 카트타기:
			new MG10_Rule();
			break;
		case 신호등블록:
			new MG11_Rule();
			break;
		case 경마:
			new MG12_Rule();
			break;
		}
	}
}
