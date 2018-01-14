package com.linmalu.minigames.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.linmalu.library.api.LinmaluConfig;
import com.linmalu.library.api.LinmaluTellraw;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.api.event.LinmaluMiniGamesEndEvent;
import com.linmalu.minigames.types.ConfigType;

public class GameData
{
	private final LinmaluConfig config = new LinmaluConfig(new File(Main.getMain().getDataFolder(), "config.yml"));
	private final BossBar bar = Bukkit.createBossBar(Main.getMain().getTitle(), BarColor.WHITE, BarStyle.SOLID);
	private boolean game1 = false;
	private boolean game2 = false;
	private MiniGame minigame;
	private Scoreboard scoreboard;
	private MapData mapData;
	private Map<UUID, PlayerData> players = new HashMap<>();
	private List<Entity> entitys = new ArrayList<>();
	private Map<UUID, List<Entity>> playerEntitys = new HashMap<>();
	private UUID targetPlayer;
	private int targetNumber;

	public void reload()
	{
		config.reload();
		Arrays.asList(MiniGame.values()).forEach(mg -> mg.getInstance().reload());
		config.clear();
		config.set(ConfigType.VERSION.getName(), Main.getMain().getDescription().getVersion());
		config.set(ConfigType.LANGUAGE.getName(), "KO_KR");
		if(!config.isBoolean(ConfigType.RESOURCE_PACK.getName()))
		{
			config.set(ConfigType.RESOURCE_PACK.getName(), true);
		}
		Arrays.asList(MiniGame.values()).forEach(mg -> mg.getInstance().save());
	}
	public void GameStart(CommandSender sender, MiniGame minigame)
	{
		GameStart(sender, minigame, null, 0, 0, 0, 0);
	}
	public void GameStart(CommandSender sender, MiniGame minigame, World world, int x1, int z1, int x2, int z2)
	{
		// TODO 디버깅
		// if(Bukkit.getOnlinePlayers().size() < 2)
		if(Bukkit.getOnlinePlayers().size() < (Main.debug ? 1 : 2))
		{
			sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "최소인원 2명이 되지 않습니다.");
			return;
		}
		else if(minigame == MiniGame.땅따먹기 && Bukkit.getOnlinePlayers().size() > 48)
		{
			sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "최대인원 48명이 넘습니다.");
			return;
		}
		else if(minigame == MiniGame.경마)
		{
			minigame = MiniGame.달리기;
		}
		game1 = true;
		game2 = false;
		this.minigame = minigame;
		bar.setVisible(true);
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + " = = = = = [ Linmalu MiniGames ] = = = = =");
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임 : " + ChatColor.YELLOW + minigame.toString());
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "서버버전 : " + ChatColor.YELLOW + Bukkit.getBukkitVersion());
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "미니게임버전 : " + ChatColor.YELLOW + Main.getMain().getDescription().getVersion());
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "제작자 : " + ChatColor.AQUA + "린마루(Linmalu)" + ChatColor.WHITE + " - http://blog.linmalu.com");
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "서버리소스팩이 켜져있다면 미니게임용 리소스팩이 적용됩니다.");
		int number = 0;
		for(Player player : Bukkit.getOnlinePlayers())
		{
			if((sender instanceof Player && ((Player)sender).getWorld() == player.getWorld()) || sender instanceof ConsoleCommandSender)
			{
				LinmaluTellraw.sendCmd(player, "/linmaluminigames 취소", ChatColor.GOLD + "미니게임에 참가를 원하지 않을 경우 클릭하세요.");
				LinmaluTellraw.sendCmd(player, "/linmaluminigames 관전", ChatColor.GOLD + "미니게임을 구경만 원할 경우 클릭하세요.");
				players.put(player.getUniqueId(), new PlayerData(player, number++));
			}
		}
		new GameTimer();
		minigame.getInstance().CreateWrold(world, x1, z1, x2, z2);
	}
	public void GameStop()
	{
		game1 = false;
		game2 = false;
		bar.removeAll();
		bar.setVisible(false);
		for(Entity e : entitys)
		{
			e.eject();
			e.remove();
		}
		for(List<Entity> list : playerEntitys.values())
		{
			list.forEach(Entity::remove);
			list.clear();
		}
		for(Player player : Bukkit.getOnlinePlayers())
		{
			PlayerData pd = PlayerData.getPlayerData(player.getUniqueId());
			if(pd != null)
			{
				pd.resetPlayer();
			}
			if(player.getWorld().getName().equals(Main.WORLD_NAME))
			{
				player.kickPlayer("맵삭제를 위해 강퇴됩니다.");
			}
		}
		if(mapData != null)
		{
			Bukkit.unloadWorld(mapData.getWorld(), false);
		}
		mapData = null;
		players.clear();
		entitys.clear();
		playerEntitys.clear();
		game1 = false;
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "게임이 종료되었습니다.");
		new DeleteWorld();
	}
	public void cancelPlayer(Player player)
	{
		if(game1 && !game2 && !player.getWorld().getName().equals(Main.WORLD_NAME) && players.containsKey(player.getUniqueId()))
		{
			players.remove(player.getUniqueId());
			player.sendTitle(ChatColor.GREEN + "미니게임천국", ChatColor.GOLD + "게임참가를 취소했습니다.", 0, 40, 20);
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
			if(mapData.getScore() <= 0)
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
						Bukkit.getPluginManager().callEvent(new LinmaluMiniGamesEndEvent(p, minigame));
						Bukkit.broadcastMessage(ChatColor.GREEN + "= = = = = [ 미 니 게 임 천 국 ] = = = = =");
						for(int i = 0; i < 6; i++)
						{
							Bukkit.broadcastMessage(ChatColor.YELLOW + p.getName() + ChatColor.GREEN + "님이 " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "게임의 우승자입니다.");
						}
						Bukkit.getOnlinePlayers().forEach(p1 -> p1.sendTitle(ChatColor.YELLOW + "우승자 : " + ChatColor.GOLD + p.getName(), ChatColor.GREEN + minigame.toString() + "게임", 20, 100, 20));
						break;
					}
				}
				GameStop();
			}
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
			live.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
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
		if(mapData.getScore() > 0)
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
			minigame.getInstance().teleport(player);
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
		// TODO 디버깅
		// if(players.size() < 2)
		if(players.size() < (Main.debug ? 1 : 2))
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
	public LinmaluConfig getConfig()
	{
		return config;
	}
	public BossBar getBossbar()
	{
		return bar;
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
		return config.getBoolean(ConfigType.RESOURCE_PACK.getName(), true);
	}
	public void setResourcePack(boolean resourcePack)
	{
		config.set(ConfigType.RESOURCE_PACK.getName(), resourcePack);
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
	public List<Player> getPlayers()
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
	public List<Player> getLivePlayers()
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
	public List<Entity> getEntitys()
	{
		return entitys;
	}
	public Map<UUID, List<Entity>> getPlayerEntitys()
	{
		return playerEntitys;
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
