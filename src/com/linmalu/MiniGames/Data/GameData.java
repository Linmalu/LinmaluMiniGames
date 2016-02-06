package com.linmalu.minigames.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.linmalu.library.api.LinmaluActionbar;
import com.linmalu.library.api.LinmaluTellraw;
import com.linmalu.library.api.LinmaluTitle;
import com.linmalu.minigames.Main;

public class GameData
{
	private boolean game1 = false;
	private boolean game2 = false;
	private boolean resourcePack = true;
	private MiniGame minigame;
	private Scoreboard scoreboard;
	private MapData mapData;
	private HashMap<UUID, PlayerData> restorePlayers = new HashMap<>();
	private HashMap<UUID, PlayerData> players = new HashMap<>();
	private ArrayList<Entity> entitys = new ArrayList<>();
	private UUID targetPlayer;
	private int targetNumber;

	public void GameStart(MiniGame minigame, World world)
	{
		game1 = true;
		game2 = false;
		this.minigame = minigame;
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Bukkit.broadcastMessage(ChatColor.GREEN + " = = = = = [ Linmalu MiniGames ] = = = = =");
		Bukkit.broadcastMessage(ChatColor.GREEN + "미니게임버전 : " + ChatColor.YELLOW + Main.getMain().getDescription().getVersion());
		Bukkit.broadcastMessage(ChatColor.YELLOW + "제작자 : " + ChatColor.AQUA + "린마루(Linmalu)" + ChatColor.WHITE + " - http://blog.linmalu.com");
		Bukkit.broadcastMessage(ChatColor.YELLOW + "서버리소스팩이 켜져있다면 미니게임용 리소스팩이 적용됩니다.");
		int number = 0;
		for(Player player : Bukkit.getOnlinePlayers())
		{
			if(player.getWorld() == world)
			{
				LinmaluTellraw.sendCmd(player, "/linmaluminigames 취소", ChatColor.GOLD + "미니게임에 참가를 원하지 않을 경우 클릭하세요.");
				LinmaluTellraw.sendCmd(player, "/linmaluminigames 관전", ChatColor.GOLD + "미니게임을 구경만 원할 경우 클릭하세요.");
				players.put(player.getUniqueId(), new PlayerData(player, number++));
				LinmaluTitle.sendMessage(player, ChatColor.GREEN + "미니게임천국", ChatColor.GOLD + minigame.toString() + "게임", 20, 200, 20);
				LinmaluActionbar.sendMessage(player, ChatColor.YELLOW + "게임맵으로 이동까지 " + ChatColor.GOLD + "10" + ChatColor.YELLOW + "초전");
			}
		}
		new CreateWorldTimer();
	}
	public void GameStop()
	{
		game1 = false;
		game2 = false;
		for(Entity en : entitys)
		{
			en.eject();
			en.remove();
		}
		for(UUID uuid : players.keySet())
		{
			players.get(uuid).resetPlayer();
		}
		for(Player player : Bukkit.getOnlinePlayers())
		{
			if(player.getWorld().getName().equals(Main.WORLD_NAME))
			{
				player.kickPlayer("맵삭제를 위해 강퇴됩니다.");
				restorePlayers.put(player.getUniqueId(), players.get(player.getUniqueId()));
			}
		}
		if(mapData != null)
		{
			Bukkit.unloadWorld(mapData.getWorld(), false);
		}
		mapData = null;
		players.clear();
		entitys.clear();
		game1 = false;
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "게임이 종료되었습니다.");
		new DeleteWorld();
	}
	public void cancelPlayer(Player player)
	{
		if(game1 && !game2 && !player.getWorld().getName().equals(Main.WORLD_NAME) && players.containsKey(player.getUniqueId()))
		{
			players.remove(player.getUniqueId());
			LinmaluTitle.sendMessage(player, ChatColor.GREEN + "미니게임천국", ChatColor.GOLD + "게임참가를 취소했습니다.", 0, 40, 20);
			player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "게임 참가를 취소했습니다.");
		}
	}
	public void onlookerPlayer(Player player)
	{
		PlayerData pd = getPlayerData(player.getUniqueId());
		if(game1 && !game2 && pd != null && !pd.isObserver())
		{
			players.get(player.getUniqueId()).setObserver();
			player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "게임관전을 선택했습니다.");
		}
	}
	public Location teleportPlayer(Player player)
	{
		Location loc = mapData.getRandomEntityLocation(player.getLocation());
		for(int y = mapData.getWorld().getMaxHeight(); y >= 0; y--)
		{
			if(!mapData.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).isEmpty())
			{
				if(y == 0)
				{
					y = 50;
				}
				else
				{
					y += 1;
				}
				loc.setY(y);
				break;
			}
		}
		player.leaveVehicle();
		player.setFallDistance(0);
		player.teleport(loc);
		return loc;
	}
	public void diePlayer(UUID uuid)
	{
		PlayerData pd = getPlayerData(uuid);
		if(game2 && pd != null && pd.isLive())
		{
			pd.setLive(false);
			int playerCount = getPlayerLiveCount();
			for(Player player : getPlayers())
			{
				player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + pd.getName() + ChatColor.GREEN + "님이 탈락했습니다.");
				player.sendMessage(ChatColor.GREEN + "남은인원수 : " + ChatColor.YELLOW + playerCount + "명");
			}
			Team team = scoreboard.getTeam("탈락자");
			if(team == null)
			{
				team = scoreboard.registerNewTeam("탈락자");
			}
			team.addEntry(pd.getName());
			if(!mapData.isTopScore())
			{
				getScore(ChatColor.YELLOW + "남은인원수").setScore(playerCount);
			}
			scoreboard.resetScores(ChatColor.GOLD + pd.getName());
			Player player = Bukkit.getPlayer(uuid);
			if(player != null)
			{
				player.damage(0);
				player.setGameMode(GameMode.SPECTATOR);
				new PlayFirework(player.getLocation());
			}
			if(playerCount < 2 && game2)
			{
				for(Player p : getPlayers())
				{
					if(getPlayerData(p.getUniqueId()).isLive())
					{
						String playerName = p.getName();
						Bukkit.broadcastMessage(ChatColor.GREEN + "= = = = = [ 미 니 게 임 천 국 ] = = = = =");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "님이 " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "게임의 우승자입니다.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "님이 " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "게임의 우승자입니다.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "님이 " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "게임의 우승자입니다.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "님이 " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "게임의 우승자입니다.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "님이 " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "게임의 우승자입니다.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "님이 " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "게임의 우승자입니다.");
						LinmaluTitle.sendMessage(ChatColor.YELLOW + "우승자 : " + ChatColor.GOLD + playerName, ChatColor.GREEN + minigame.toString() + "게임", 20, 100, 20);
						break;
					}
				}
				GameStop();
			}
		}
	}
	public void restorePlayer(Player player)
	{
		PlayerData pd = restorePlayers.get(player.getUniqueId());
		if(pd != null)
		{
			pd.resetPlayer();
			restorePlayers.remove(player.getUniqueId());
		}
	}
	public void setGamePlayer()
	{
		Team live = scoreboard.getTeam("생존자");
		if(live == null)
		{
			live = scoreboard.registerNewTeam("생존자");
		}
		live.setPrefix(ChatColor.WHITE.toString());
		live.setCanSeeFriendlyInvisibles(mapData.isSee());
		if(!mapData.isSee())
		{
			live.setNameTagVisibility(NameTagVisibility.NEVER);
		}
		Team die = scoreboard.getTeam("탈락자");
		if(die == null)
		{
			die = scoreboard.registerNewTeam("탈락자");
		}
		die.setPrefix(ChatColor.GRAY.toString());
		Objective ob = scoreboard.getObjective("미니게임");
		if(ob == null)
		{
			ob = scoreboard.registerNewObjective("미니게임", "");
		}
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		setObjectiveDisplayName("");
		if(mapData.isTopScore())
		{
			ob.getScore(ChatColor.YELLOW + "목표점수").setScore(mapData.getScore());
		}
		else
		{
			ob.getScore(ChatColor.YELLOW + "남은인원수").setScore(getPlayerLiveCount());
		}
		for(PlayerData pd : players.values())
		{
			ob.getScore(ChatColor.GOLD + pd.getName()).setScore(0);
		}
		for(Player player : getPlayers())
		{
			PlayerData pd = getPlayerData(player.getUniqueId());
			pd.setPlayer();
			if(pd.isLive())
			{
				live.addEntry(player.getName());
			}
			else
			{
				die.addEntry(player.getName());
			}
			minigame.getHandle().moveWorld(player);
			setScoreboard(player);
			if(!isResourcePack())
			{
				player.setResourcePack(Main.RESOURCEPACK_MINIGAMES);
			}
		}
	}
	public void setGameItem()
	{
		game2 = true;
		if(players.size() < 2)
		{
			Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "최소인원 2명이 되지 않습니다.");
			GameStop();
			return;
		}
		for(UUID uuid : players.keySet())
		{
			Player player = Bukkit.getPlayer(uuid);
			if(player == null || player.isDead() || !player.getWorld().getName().equals(Main.WORLD_NAME) || getPlayerData(uuid).isObserver())
			{
				diePlayer(uuid);
			}
		}
	}
	public boolean isGame1()
	{
		return game1;
	}
	public boolean isGame2()
	{
		return game2;
	}
	public boolean isResourcePack()
	{
		return resourcePack;
	}
	public void setResourcePack(boolean resourcePack)
	{
		this.resourcePack = resourcePack;
	}
	public MiniGame getMinigame()
	{
		return minigame;
	}
	public void setScoreboard(Player player)
	{
		player.setScoreboard(scoreboard);
	}
	public void setObjectiveDisplayName(String name)
	{
		Objective ob = scoreboard.getObjective("미니게임");
		if(ob == null)
		{
			ob = scoreboard.registerNewObjective("미니게임", "");
		}
		ob.setDisplayName(ChatColor.GREEN + minigame.toString() + name);

	}
	public Score getScore(String name)
	{
		Objective ob = scoreboard.getObjective("미니게임");
		if(ob == null)
		{
			ob = scoreboard.registerNewObjective("미니게임", "");
		}
		return ob.getScore(name);
	}
	public MapData getMapData()
	{
		return mapData;
	}
	public void setMapData(MapData mapData)
	{
		this.mapData = mapData;
	}
	public ArrayList<Player> getPlayers()
	{
		ArrayList<Player> list = new ArrayList<>();
		for(UUID uuid : players.keySet())
		{
			Player player = Bukkit.getPlayer(uuid);
			if(player != null)
			{
				list.add(player);
			}
		}
		return list;
	}
	public ArrayList<Player> getLivePlayers()
	{
		ArrayList<Player> list = new ArrayList<>();
		for(UUID uuid : players.keySet())
		{
			PlayerData pd = players.get(uuid);
			if(pd.isLive())
			{
				Player player = Bukkit.getPlayer(uuid);
				if(player != null)
				{
					list.add(player);
				}
			}
		}
		return list;
	}
	public PlayerData getPlayerData(UUID uuid)
	{
		return players.get(uuid);
	}
	public int getPlayerAllCount()
	{
		return players.size();
	}
	public int getPlayerLiveCount()
	{
		int count = 0;
		for(PlayerData pd : players.values())
		{
			if(pd.isLive())
			{
				count++;
			}
		}
		return count;
	}
	public void addEntity(Entity entity)
	{
		entitys.add(entity);
	}
	public ArrayList<Entity> getEntitys()
	{
		return entitys;
	}
	public UUID getTargetPlayer()
	{
		return targetPlayer;
	}
	public void setTargetPlayer(UUID targetPlayer)
	{
		this.targetPlayer = targetPlayer;
	}
	public int getTargetNumber()
	{
		return targetNumber;
	}
	public void setTargetNumber(int targetNumber)
	{
		this.targetNumber = targetNumber;
	}
}
