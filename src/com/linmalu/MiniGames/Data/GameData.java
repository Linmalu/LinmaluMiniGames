package com.linmalu.MiniGames.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.linmalu.LinmaluLibrary.API.LinmaluActionbar;
import com.linmalu.LinmaluLibrary.API.LinmaluTellraw;
import com.linmalu.LinmaluLibrary.API.LinmaluTitle;
import com.linmalu.MiniGames.Main;
import com.linmalu.MiniGames.Game00.MG00_FallingBlock;
import com.linmalu.MiniGames.Game00.MG00_NoMoving;
import com.linmalu.MiniGames.Game01.MG01_Falling;
import com.linmalu.MiniGames.Game02.MG02_Falling;
import com.linmalu.MiniGames.Game05.MG05_Moving;
import com.linmalu.MiniGames.Game07.MG07_Shoot;
import com.linmalu.MiniGames.Game08.MG08_Change;
import com.linmalu.MiniGames.Game11.MG11_Block;

public class GameData
{
	private boolean game1 = false;
	private boolean game2 = false;
	private boolean resourcePack = true;
	private MiniGames minigame;
	private Scoreboard scoreboard;
	private MapData mapData;
	private HashMap<UUID, PlayerData> restorePlayers = new HashMap<>();
	private HashMap<UUID, PlayerData> players = new HashMap<>();
	private ArrayList<ItemStack> items = new ArrayList<>();
	private ArrayList<Entity> entitys = new ArrayList<>();
	private UUID targetPlayer;
	private int targetNumber;

	public GameData()
	{
		reloadConfig();
	}
	public void reloadConfig()
	{
		File folder = Main.getMain().getDataFolder();
		if(!folder.exists())
		{
			folder.mkdir();
		}
//		for(File file : folder.listFiles())
//		{
//			String name = file.getName();
//			int i = name.lastIndexOf(".yml");
//			if(i != -1)
//			{
//				new DigitalClock(file, name.substring(0, i));
//			}
//		}
	}
	public void GameStart(MiniGames minigame, World world)
	{
		game1 = true;
		game2 = false;
		this.minigame = minigame;
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Bukkit.broadcastMessage(ChatColor.GREEN + " = = = = = [ M i n i G a m e s ] = = = = =");
		Bukkit.broadcastMessage(ChatColor.GREEN + "�̴ϰ��ӹ��� : " + ChatColor.YELLOW + Main.getMain().getDescription().getVersion());
		Bukkit.broadcastMessage(ChatColor.GREEN + "������ : " + ChatColor.YELLOW + "������");
		Bukkit.broadcastMessage(ChatColor.GREEN + "��α� : " + ChatColor.YELLOW + "http://blog.linmalu.com");
		Bukkit.broadcastMessage(ChatColor.YELLOW + "�������ҽ����� �����ִٸ� �̴ϰ��ӿ� ���ҽ����� ����˴ϴ�.");
		for(Player player : Bukkit.getOnlinePlayers())
		{
			if(player.getWorld() == world)
			{
				LinmaluTellraw.sendCmd(player, "/minigames ���", ChatColor.GOLD + "�̴ϰ��ӿ� ������ ������ ���� ��� Ŭ���ϼ���.");
				LinmaluTellraw.sendCmd(player, "/minigames ����", ChatColor.GOLD + "�̴ϰ����� ���游 ���� ��� Ŭ���ϼ���.");
				players.put(player.getUniqueId(), new PlayerData(player));
				LinmaluTitle.setMessage(player, ChatColor.GREEN + "�̴ϰ���õ��", ChatColor.GOLD + minigame.toString() + "����", 20	, 200, 20);
				LinmaluActionbar.setMessage(player, ChatColor.YELLOW + "���Ӹ����� �̵����� " + ChatColor.GOLD + "10" + ChatColor.YELLOW + "����");
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
			if(player.getWorld().getName().equals(Main.world))
			{
				player.kickPlayer("�ʻ����� ���� ����˴ϴ�.");
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
		items.clear();
		game1 = false;
		Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GREEN + "������ ����Ǿ����ϴ�.");
		new DeleteWorld();
	}
	public void cancelPlayer(Player player)
	{
		if(game1 && !game2 && !player.getWorld().getName().equals(Main.world) && players.containsKey(player.getUniqueId()))
		{
			players.remove(player.getUniqueId());
			LinmaluTitle.setMessage(player, ChatColor.GREEN + "�̴ϰ���õ��", ChatColor.GOLD + "���������� ����߽��ϴ�.", 0, 40, 20);
			player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "���� ������ ����߽��ϴ�.");
		}
	}
	public void onlookerPlayer(Player player)
	{
		PlayerData pd = getPlayerData(player.getUniqueId());
		if(game1 && !game2 && pd != null && !pd.isObserver())
		{
			players.get(player.getUniqueId()).setObserver();
			player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "���Ӱ����� �����߽��ϴ�.");
		}
	}
	public Location teleportPlayer(Player player)
	{
		int x, y, z;
		Random ran = new Random();
		x = ran.nextInt(mapData.getX2() - mapData.getX1() -3) + mapData.getX1() + 2;
		z = ran.nextInt(mapData.getZ2() - mapData.getZ1() -3) + mapData.getZ1() + 2;
		for(y = mapData.getWorld().getMaxHeight(); y > 0; y--)
		{
			if(!mapData.getWorld().getBlockAt(x, y, z).isEmpty())
			{
				break;
			}
		}
		if(y == 0)
		{
			y = 50;
		}
		else
		{
			y += 1;
		}
		Location loc = new Location(mapData.getWorld(), x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());
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
				player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + pd.getName() + ChatColor.GREEN + "���� Ż���߽��ϴ�.");
				player.sendMessage(ChatColor.GREEN + "�����ο��� : " + ChatColor.YELLOW + playerCount + "��");
			}
			Team team = scoreboard.getTeam("Ż����");
			if(team == null)
			{
				team = scoreboard.registerNewTeam("Ż����");
			}
			team.addEntry(pd.getName());
			if(!mapData.isTopScore())
			{
				getScore(ChatColor.GREEN + "�����ο���").setScore(playerCount);
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
						Bukkit.broadcastMessage(ChatColor.GREEN + "= = = = = [ �� �� �� �� õ �� ] = = = = =");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "���� " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "������ ������Դϴ�.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "���� " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "������ ������Դϴ�.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "���� " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "������ ������Դϴ�.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "���� " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "������ ������Դϴ�.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "���� " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "������ ������Դϴ�.");
						Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.GREEN + "���� " + ChatColor.GOLD + minigame.toString() + ChatColor.GREEN + "������ ������Դϴ�.");
						LinmaluTitle.setMessage(ChatColor.YELLOW + "����� : " + ChatColor.GOLD + playerName, ChatColor.GOLD + minigame.toString() + "����", 20, 100, 20);
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
		Team live = scoreboard.getTeam("������");
		if(live == null)
		{
			live = scoreboard.registerNewTeam("������");
		}
		live.setPrefix(ChatColor.WHITE.toString());
		live.setCanSeeFriendlyInvisibles(mapData.isSee());
		Team die = scoreboard.getTeam("Ż����");
		if(die == null)
		{
			die = scoreboard.registerNewTeam("Ż����");
		}
		die.setPrefix(ChatColor.GRAY.toString());
		Objective ob = scoreboard.getObjective("�̴ϰ���");
		if(ob == null)
		{
			ob = scoreboard.registerNewObjective("�̴ϰ���", "");
		}
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		setObjectiveDisplayName("");
		if(mapData.isTopScore())
		{
			ob.getScore(ChatColor.GREEN + "��ǥ����").setScore(mapData.getScore());
		}
		else
		{
			ob.getScore(ChatColor.GREEN + "�����ο���").setScore(getPlayerLiveCount());
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
			setScoreboard(player);
			teleportPlayer(player);
			if(!isResourcePack())
			{
				player.setResourcePack(Main.resourcePackMiniGames);
			}
		}
	}
	@SuppressWarnings("deprecation")
	public boolean useItem(Player player, boolean remove)
	{
		ItemStack item = player.getItemInHand();
		if(item != null && item.getType() != Material.AIR && item.hasItemMeta())
		{
			ItemMeta im = item.getItemMeta();
			if(im.hasDisplayName())
			{
				String name = im.getDisplayName();
				if(item.getType() == Material.IRON_INGOT && name.equals(ChatColor.GREEN + "�ӵ�"))
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, true, false), true);
				}
				else if(item.getType() == Material.GOLD_INGOT && name.equals(ChatColor.GREEN + "����"))
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 2, true, false), true);
				}
				else if(item.getType() == Material.DIAMOND && name.equals(ChatColor.GREEN + "����"))
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, true, false), true);
				}
				else if(item.getType() == Material.EMERALD && name.equals(ChatColor.GREEN + "�߷�"))
				{
					Location loc = player.getLocation();
					int size = 8;
					for(int x = loc.getBlockX() - size; x <= loc.getBlockX() + size; x++)
					{
						for(int z = loc.getBlockZ() - size; z <= loc.getBlockZ() + size; z++)
						{
							Block block = player.getWorld().getBlockAt(x, getMapData().getMapHeight(), z);
							if(block.getType() == Material.STAINED_GLASS && block.getData() == 0 && loc.distance(block.getLocation()) <= size)
							{
								new MG00_FallingBlock(block);
							}
						}
					}
				}
				else if(item.getType() == Material.STRING && name.equals(ChatColor.GREEN + "����"))
				{
					for(Player p : getPlayers())
					{
						if(getPlayerData(p.getUniqueId()).isLive() && !player.getUniqueId().equals(p.getUniqueId()))
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, true, false), true);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 1, true, false), true);
							p.sendMessage(name + ChatColor.YELLOW + " ������ ȿ���� �ɷȽ��ϴ�.");
						}
					}
				}
				else if(item.getType() == Material.INK_SACK && name.equals(ChatColor.GREEN + "���"))
				{
					for(Player p : getPlayers())
					{
						if(getPlayerData(p.getUniqueId()).isLive() && !player.getUniqueId().equals(p.getUniqueId()))
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, true, false), true);
							p.sendMessage(name + ChatColor.YELLOW + " ������ ȿ���� �ɷȽ��ϴ�.");
						}
					}
				}
				else if(item.getType() == Material.NETHER_STAR && name.equals(ChatColor.GREEN + "�̵�"))
				{
					teleportPlayer(player);
				}
				else if(item.getType() == Material.GOLD_HOE && name.equals(ChatColor.GREEN + "��"))
				{
					new MG07_Shoot(player);
					return true;
				}
				else
				{
					return false;
				}
				if(remove)
				{
					if(item.getAmount() > 1)
					{
						item.setAmount(item.getAmount() -1);
					}
					else
					{
						player.getInventory().remove(item);
					}
				}
				player.sendMessage(name + ChatColor.YELLOW + " �������� ����߽��ϴ�.");
			}
		}
		return true;
	}
	public void setGameItem()
	{
		game2 = true;
		if(players.size() < 2)
		{
			Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "�ּ��ο� 2���� ���� �ʽ��ϴ�.");
			GameStop();
			return;
		}
		for(UUID uuid : players.keySet())
		{
			Player player = Bukkit.getPlayer(uuid);
			if(player == null || player.isDead() || !player.getWorld().getName().equals(Main.world) || getPlayerData(uuid).isObserver())
			{
				diePlayer(uuid);
			}
		}
		int number = 0;
		switch(minigame)
		{
		case �޸���:
			for(Player player : getPlayers())
			{
				if(getPlayerData(player.getUniqueId()).isLive())
				{
					new MG00_NoMoving(player);
					for(ItemStack item : items)
					{
						player.getInventory().addItem(item);
					}
				}
			}
			break;
		case ������ϱ�:
			new MG01_Falling();
			for(Player player : getPlayers())
			{
				if(getPlayerData(player.getUniqueId()).isLive())
				{
					ItemStack item = items.get(number);
					for(int i = 0; i < 9; i++)
					{
						player.getInventory().setItem(i, item);
					}
				}
			}
			break;
		case ���:
			new MG02_Falling();
			break;
		case ��ġ:
			for(Player player : getPlayers())
			{
				if(getPlayerData(player.getUniqueId()).isLive())
				{
					for(ItemStack item : items)
					{
						player.getInventory().addItem(item);
					}
				}
			}
			break;
		case ���ı�:
			for(Player player : getPlayers())
			{
				if(getPlayerData(player.getUniqueId()).isLive())
				{
					ItemStack item = new ItemStack(Material.DIAMOND_SPADE);
					ItemMeta im = item.getItemMeta();
					im.spigot().setUnbreakable(true);
					item.setItemMeta(im);
					player.getInventory().addItem(item);
				}
			}
			break;
		case �������ϱ�:
			for(Player player : getPlayers())
			{
				if(getPlayerData(player.getUniqueId()).isLive())
				{
					number %= 16;
					ItemStack item = new ItemStack(Material.WOOL, 64, (short)number);
					for(int i = 0; i < 9; i++)
					{
						player.getInventory().setItem(i, item);
					}
					new MG05_Moving(player, DyeColor.values()[number]);
					number++;
				}
			}
			break;
		case �����Ա�:
			for(Player player : getPlayers())
			{
				PlayerData pd = getPlayerData(player.getUniqueId()); 
				if(pd.isLive())
				{
					pd.setNumber(number);
					ItemStack item = items.get(number);
					for(int i = 0; i < 9; i++)
					{
						player.getInventory().addItem(item);
					}
				}
				number++;
			}
			break;
		case �ѽο�:
			for(Player player : getPlayers())
			{
				if(getPlayerData(player.getUniqueId()).isLive())
				{
					for(ItemStack item : items)
					{
						player.getInventory().addItem(item);
					}
				}
			}
			break;
		case ��ź���ϱ�:
			new MG08_Change();
			break;
		case ����ã��:
			break;
		case īƮŸ��:
			break;
		case ��ȣ����:
			new MG11_Block();
			break;
		case �渶:
			for(Player player : getPlayers())
			{
				PlayerData pd = getPlayerData(player.getUniqueId());
				if(pd.isLive())
				{
					pd.setNumber(0);
				}
				number++;
			}
			for(int x = 2; x <= 12; x++)
			{
				new Location(mapData.getWorld(), x, 22, 13).getBlock().setType(Material.AIR);
			}
			break;
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
	public MiniGames getMinigame()
	{
		return minigame;
	}
	public void setScoreboard(Player player)
	{
		player.setScoreboard(scoreboard);
	}
	public void setObjectiveDisplayName(String name)
	{
		Objective ob = scoreboard.getObjective("�̴ϰ���");
		if(ob == null)
		{
			ob = scoreboard.registerNewObjective("�̴ϰ���", "");
		}
		ob.setDisplayName(ChatColor.YELLOW + minigame.toString() + name);
		
	}
	public Score getScore(String name)
	{
		Objective ob = scoreboard.getObjective("�̴ϰ���");
		if(ob == null)
		{
			ob = scoreboard.registerNewObjective("�̴ϰ���", "");
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
	public ArrayList<ItemStack> getItems()
	{
		return items;
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
