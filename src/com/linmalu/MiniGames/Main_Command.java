package com.linmalu.MiniGames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.linmalu.LinmaluLibrary.API.LinmaluCheckVersion;
import com.linmalu.LinmaluLibrary.API.LinmaluTellraw;
import com.linmalu.MiniGames.Data.GameData;
import com.linmalu.MiniGames.Data.MiniGames;

public class Main_Command implements CommandExecutor
{
	public Main_Command()
	{
		Main.getMain().getCommand(Main.getMain().getDescription().getName()).setTabCompleter(new TabCompleter()
		{			
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
			{
				if(args.length == 1 && sender.isOp())
				{
					ArrayList<String> list = new ArrayList<>();
					for(MiniGames game : MiniGames.values())
					{
						list.add(game.toString());
					}
					list.add("랜덤");
					list.add("종료");
					list.add("stop");
					list.add("리소스팩적용");
					list.add("리소스팩취소");
					return list;
				}
				return null;
			}
		});
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			GameData data = Main.getMain().getGameData();
			if(args.length == 1)
			{
				if(player.isOp())
				{
					if(args[0].equals("종료") || args[0].equalsIgnoreCase("stop"))
					{
						if(!data.isGame1())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "게임이 시작되지 않았습니다.");
						}
						else if(data.isGame1() && !data.isGame2())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "지금은 게임을 종료할 수 없습니다.");
						}
						else
						{
							data.GameStop();
						}
						return true;
					}
					else if(args[0].equals("랜덤"))
					{
						setGame(player, data, MiniGames.values()[new Random().nextInt(MiniGames.values().length)]);
						return true;
					}
					else if(args[0].equals("리소스팩적용"))
					{
						if(data.isGame1())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "게임 중에는 사용할 수 없습니다.");
						}
						else
						{
							data.setResourcePack(true);
							for(Player p : Bukkit.getOnlinePlayers())
							{
								p.setResourcePack(Main.resourcePackMiniGames);
							}
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "지금부터 미니게임천국 리소스팩이 적용됩니다.");
						}
						return true;
					}
					else if(args[0].equals("리소스팩취소"))
					{
						if(data.isGame1())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "게임 중에는 사용할 수 없습니다.");
						}
						else
						{
							data.setResourcePack(false);
							for(Player p : Bukkit.getOnlinePlayers())
							{
								p.setResourcePack(Main.resourcePackDefault);
							}
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "지금부터 미니게임천국 리소스팩이 취소됩니다.");
						}
						return true;
					}
					else
					{
						for(MiniGames game : MiniGames.values())
						{
							if(args[0].equals(game.toString()))
							{
								setGame(player, data, game);
								return true;
							}
						}
					}
				}
				if(args[0].equals("취소"))
				{
					data.cancelPlayer(player);
					return true;
				}
				else if(args[0].equals("관전"))
				{
					data.onlookerPlayer(player);
					return true;
				}
			}
			if(player.isOp())
			{
				StringBuffer sb = new StringBuffer();
				for(MiniGames mg : MiniGames.values())
				{
					sb.append("$CC:" + ChatColor.YELLOW + mg.toString() + "|/mg " + mg.toString() + "$");
					sb.append(", ");
				}
				sender.sendMessage(ChatColor.GREEN + " = = = = = [ M i n i G a m e s ] = = = = =");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " ", ChatColor.GOLD + "/" + label + " <미니게임>" + ChatColor.GRAY + " : 지정된 게임 시작");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " 랜덤", ChatColor.GOLD + "/" + label + " 랜덤" + ChatColor.GRAY + " : 랜덤 게임 시작");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " 종료", ChatColor.GOLD + "/" + label + " 종료 // stop" + ChatColor.GRAY + " : 미니게임 끝내기");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " 리소스팩적용", ChatColor.GOLD + "/" + label + " 리소스팩적용" + ChatColor.GRAY + " : 미니게임천국 리소스팩 적용");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " 리소스팩취소", ChatColor.GOLD + "/" + label + " 리소스팩취소" + ChatColor.GRAY + " : 미니게임천국 리소스팩 취소");
				new LinmaluTellraw(ChatColor.GREEN + "미니게임종류 : " + sb.toString()).changeCmdChat().sendMessage(player);
				sender.sendMessage(ChatColor.GOLD + "제작자 : " + ChatColor.AQUA + "린마루" + ChatColor.WHITE + " - http://blog.linmalu.com");
				sender.sendMessage(ChatColor.GOLD + "카페 : " + ChatColor.WHITE + "http://cafe.naver.com/craftproducer");
				new LinmaluCheckVersion(Main.getMain(), player, Main.getMain().getTitle() + ChatColor.GREEN + "최신버전이 존재합니다.");
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
		}
		return true;
	}
	private void setGame(Player player, GameData data, MiniGames minigame)
	{
		if(data.isGame1())
		{
			player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "이미 게임이 진행중입니다.");
		}
		else
		{
			if(Bukkit.getOnlinePlayers().size() < 2)
			{
				player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "최소인원 2명이 되지 않습니다.");
			}
			else if(minigame == MiniGames.땅따먹기 && Bukkit.getOnlinePlayers().size() > 16)
			{
				player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "최대인원 16명이 넘습니다.");
			}
			else
			{
				data.GameStart(minigame, player.getWorld());
			}
		}
	}
}
