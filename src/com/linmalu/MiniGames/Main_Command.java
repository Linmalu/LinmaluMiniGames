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
					list.add("����");
					list.add("����");
					list.add("stop");
					list.add("���ҽ�������");
					list.add("���ҽ������");
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
					if(args[0].equals("����") || args[0].equalsIgnoreCase("stop"))
					{
						if(!data.isGame1())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "������ ���۵��� �ʾҽ��ϴ�.");
						}
						else if(data.isGame1() && !data.isGame2())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "������ ������ ������ �� �����ϴ�.");
						}
						else
						{
							data.GameStop();
						}
						return true;
					}
					else if(args[0].equals("����"))
					{
						setGame(player, data, MiniGames.values()[new Random().nextInt(MiniGames.values().length)]);
						return true;
					}
					else if(args[0].equals("���ҽ�������"))
					{
						if(data.isGame1())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "���� �߿��� ����� �� �����ϴ�.");
						}
						else
						{
							data.setResourcePack(true);
							for(Player p : Bukkit.getOnlinePlayers())
							{
								p.setResourcePack(Main.resourcePackMiniGames);
							}
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "���ݺ��� �̴ϰ���õ�� ���ҽ����� ����˴ϴ�.");
						}
						return true;
					}
					else if(args[0].equals("���ҽ������"))
					{
						if(data.isGame1())
						{
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "���� �߿��� ����� �� �����ϴ�.");
						}
						else
						{
							data.setResourcePack(false);
							for(Player p : Bukkit.getOnlinePlayers())
							{
								p.setResourcePack(Main.resourcePackDefault);
							}
							sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "���ݺ��� �̴ϰ���õ�� ���ҽ����� ��ҵ˴ϴ�.");
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
				if(args[0].equals("���"))
				{
					data.cancelPlayer(player);
					return true;
				}
				else if(args[0].equals("����"))
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
				LinmaluTellraw.sendCmdChat(player, "/" + label + " ", ChatColor.GOLD + "/" + label + " <�̴ϰ���>" + ChatColor.GRAY + " : ������ ���� ����");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " ����", ChatColor.GOLD + "/" + label + " ����" + ChatColor.GRAY + " : ���� ���� ����");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " ����", ChatColor.GOLD + "/" + label + " ���� // stop" + ChatColor.GRAY + " : �̴ϰ��� ������");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " ���ҽ�������", ChatColor.GOLD + "/" + label + " ���ҽ�������" + ChatColor.GRAY + " : �̴ϰ���õ�� ���ҽ��� ����");
				LinmaluTellraw.sendCmdChat(player, "/" + label + " ���ҽ������", ChatColor.GOLD + "/" + label + " ���ҽ������" + ChatColor.GRAY + " : �̴ϰ���õ�� ���ҽ��� ���");
				new LinmaluTellraw(ChatColor.GREEN + "�̴ϰ������� : " + sb.toString()).changeCmdChat().sendMessage(player);
				sender.sendMessage(ChatColor.GOLD + "������ : " + ChatColor.AQUA + "������" + ChatColor.WHITE + " - http://blog.linmalu.com");
				sender.sendMessage(ChatColor.GOLD + "ī�� : " + ChatColor.WHITE + "http://cafe.naver.com/craftproducer");
				new LinmaluCheckVersion(Main.getMain(), player, Main.getMain().getTitle() + ChatColor.GREEN + "�ֽŹ����� �����մϴ�.");
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "������ �����ϴ�.");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "������ �����ϴ�.");
		}
		return true;
	}
	private void setGame(Player player, GameData data, MiniGames minigame)
	{
		if(data.isGame1())
		{
			player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "�̹� ������ �������Դϴ�.");
		}
		else
		{
			if(Bukkit.getOnlinePlayers().size() < 2)
			{
				player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "�ּ��ο� 2���� ���� �ʽ��ϴ�.");
			}
			else if(minigame == MiniGames.�����Ա� && Bukkit.getOnlinePlayers().size() > 16)
			{
				player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "�ִ��ο� 16���� �ѽ��ϴ�.");
			}
			else
			{
				data.GameStart(minigame, player.getWorld());
			}
		}
	}
}
