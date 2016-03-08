package com.linmalu.minigames;

import java.io.IOException;
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

import com.linmalu.library.api.LinmaluTellraw;
import com.linmalu.library.api.LinmaluVersion;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;

public class Main_Command implements CommandExecutor
{
	public Main_Command()
	{
		Main.getMain().getCommand(Main.getMain().getDescription().getName()).setTabCompleter(new TabCompleter()
		{
			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
			{
				if(args.length == 1 && sender.isOp())
				{
					ArrayList<String> list = new ArrayList<>();
					for(MiniGame game : MiniGame.values())
					{
						list.add(game.toString());
					}
					list.add("랜덤");
					list.add("종료");
					list.add("stop");
					list.add("리소스팩적용");
					list.add("리소스팩취소");
					list.add("리로드");
					list.add("reload");
					return list;
				}
				return null;
			}
		});
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		GameData data = Main.getMain().getGameData();
		if(sender instanceof Player && args.length == 1)
		{
			Player player = (Player)sender;
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
		if(sender.isOp())
		{
			if(args.length == 1)
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
					setGame(sender, data, MiniGame.values()[new Random().nextInt(MiniGame.values().length)]);
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
							p.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
						}
						Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 적용됩니다.");
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
							p.setResourcePack(Main.RESOURCEPACK_DEFAULT);
						}
						Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임천국 리소스팩이 취소됩니다.");
					}
					return true;
				}
				else if(args[0].equals("리로드") || args[0].equalsIgnoreCase("reload"))
				{
					if(data.isGame1())
					{
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "게임 중에는 사용할 수 없습니다.");
					}
					else
					{
						for(MiniGame minigame : MiniGame.values())
						{
							try
							{
								minigame.getHandle().reloadConfig();
							}
							catch(IOException e)
							{
								e.printStackTrace();
							}
						}
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "설정파일을 다시 불러왔습니다.");
					}
					return true;
				}
				else
				{
					for(MiniGame game : MiniGame.values())
					{
						if(args[0].equals(game.toString()))
						{
							setGame(sender, data, game);
							return true;
						}
					}
				}
			}
			StringBuffer sb = new StringBuffer();
			for(MiniGame mg : MiniGame.values())
			{
				sb.append("$AT:" + ChatColor.YELLOW + mg.toString() + "|/mg " + mg.toString() + "|" + ChatColor.GREEN + "클릭시 채팅에 입력됩니다." + ChatColor.GRAY + " - " + ChatColor.GOLD + "/mg " + mg.toString() + "$");
				sb.append(", ");
			}
			sender.sendMessage(ChatColor.GREEN + " = = = = = [ Linmalu MiniGames ] = = = = =");
			LinmaluTellraw.sendChat(sender, "/" + label + " ", ChatColor.GOLD + "/" + label + " <미니게임>" + ChatColor.GRAY + " : 지정된 게임 시작");
			LinmaluTellraw.sendChat(sender, "/" + label + " 랜덤", ChatColor.GOLD + "/" + label + " 랜덤" + ChatColor.GRAY + " : 랜덤 게임 시작");
			LinmaluTellraw.sendChat(sender, "/" + label + " 종료", ChatColor.GOLD + "/" + label + " 종료 // stop" + ChatColor.GRAY + " : 미니게임 끝내기");
			LinmaluTellraw.sendChat(sender, "/" + label + " 리로드", ChatColor.GOLD + "/" + label + " 리로드 // reload" + ChatColor.GRAY + " : 설정파일 다시 불러오기");
			LinmaluTellraw.sendChat(sender, "/" + label + " 리소스팩적용", ChatColor.GOLD + "/" + label + " 리소스팩적용" + ChatColor.GRAY + " : 미니게임천국 리소스팩 적용");
			LinmaluTellraw.sendChat(sender, "/" + label + " 리소스팩취소", ChatColor.GOLD + "/" + label + " 리소스팩취소" + ChatColor.GRAY + " : 미니게임천국 리소스팩 취소");
			new LinmaluTellraw(ChatColor.GREEN + "미니게임종류 : " + sb.toString()).changeChatText().sendMessage(sender);
			sender.sendMessage(ChatColor.YELLOW + "제작자 : " + ChatColor.AQUA + "린마루(Linmalu)" + ChatColor.WHITE + " - http://blog.linmalu.com");
			sender.sendMessage(ChatColor.YELLOW + "카페 : " + ChatColor.WHITE + "http://cafe.naver.com/craftproducer");
			LinmaluVersion.check(Main.getMain(), sender);
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
		}
		return true;
	}
	private void setGame(CommandSender sender, GameData data, MiniGame minigame)
	{
		if(data.isGame1())
		{
			sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "이미 게임이 진행중입니다.");
		}
		else
		{
			if(Bukkit.getOnlinePlayers().size() < 2)
			{
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "최소인원 2명이 되지 않습니다.");
			}
			else if(minigame == MiniGame.땅따먹기 && Bukkit.getOnlinePlayers().size() > 48)
			{
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "최대인원 16명이 넘습니다.");
			}
			else
			{
				data.GameStart(minigame);
			}
		}
	}
}
