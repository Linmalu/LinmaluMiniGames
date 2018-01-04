package com.linmalu.minigames.data;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.linmalu.library.api.LinmaluMath;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.game000.MiniGameFallingBlock0;
import com.linmalu.minigames.game007.MiniGameShoot7;

public enum ItemData
{
	공기(Material.AIR),
	속도(Material.IRON_INGOT),
	점프(Material.GOLD_INGOT),
	투명(Material.DIAMOND),
	중력(Material.EMERALD),
	모루(Material.ANVIL),
	양털가위(Material.SHEARS),
	눈덩이(Material.SNOW_BALL),
	이동(Material.NETHER_STAR),
	주먹(Material.GOLD_SPADE),
	가위(Material.GOLD_PICKAXE),
	보(Material.GOLD_AXE),
	느림(Material.STRING),
	어둠(Material.INK_SACK),
	삽(Material.DIAMOND_SPADE),
	총(Material.GOLD_HOE),
	폭탄(Material.TNT),
	도끼(Material.DIAMOND_AXE),
	곡괭이(Material.DIAMOND_PICKAXE);

	private final ItemStack item;

	private ItemData(Material material)
	{
		item = new ItemStack(material);
		if(material != Material.AIR)
		{
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + toString());
			im.setUnbreakable(true);
			item.setItemMeta(im);
		}
	}
	public ItemStack getItemStack()
	{
		return item;
	}
	public boolean equalsItemStack(ItemStack item)
	{
		return item != null && this.item.getType() == item.getType() && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && this.item.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName());
	}
	public static ItemData getItemData(ItemStack item)
	{
		for(ItemData ItemData : values())
		{
			if(ItemData.equalsItemStack(item))
			{
				return ItemData;
			}
		}
		return 공기;
	}
	public static void addItemStack(Player player, ItemData ... ItemDatas)
	{
		for(ItemData ItemData : ItemDatas)
		{
			for(ItemStack item : player.getInventory().getContents())
			{
				if(item != null && item.getType() == ItemData.getItemStack().getType() && item.getAmount() < 100)
				{
					item.setAmount(item.getAmount() + 1);
					return;
				}
			}
			player.getInventory().addItem(ItemData.getItemStack());
		}
	}
	public static void setItemStack(Player player, ItemData ... ItemDatas)
	{
		for(int i = 0; i < ItemDatas.length; i++)
		{
			player.getInventory().setItem(i, ItemDatas[i].getItemStack());
		}
	}
	public static void useItem(Player player, boolean remove, int cooldown)
	{
		GameData data = Main.getMain().getGameData();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item != null && item.getType() != Material.AIR && item.hasItemMeta())
		{
			ItemData ItemData = getItemData(item);
			boolean message = true;
			switch(ItemData)
			{
				case 공기:
					break;
				case 속도:
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2, false, false), true);
					break;
				case 점프:
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 3, false, false), true);
					break;
				case 투명:
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false), true);
					break;
				case 중력:
					Location loc = player.getLocation();
					int size = 8;
					for(int x = loc.getBlockX() - size; x <= loc.getBlockX() + size; x++)
					{
						for(int z = loc.getBlockZ() - size; z <= loc.getBlockZ() + size; z++)
						{
							Block block = player.getWorld().getBlockAt(x, data.getMapData().getMapHeight(), z);
							if(block.getType() == Material.STAINED_GLASS && loc.distance(block.getLocation()) <= size)
							{
								new MiniGameFallingBlock0(block);
							}
						}
					}
					break;
				case 이동:
					data.getMinigame().getInstance().teleport(player);
					break;
				case 느림:
					for(Player p : data.getLivePlayers())
					{
						if(!player.getUniqueId().equals(p.getUniqueId()) && LinmaluMath.distance(p.getLocation(), player.getLocation()) < 15)
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, false, false), true);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 1, false, false), true);
							p.sendMessage(ItemData.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템 효과에 걸렸습니다.");
						}
					}
					break;
				case 어둠:
					for(Player p : data.getLivePlayers())
					{
						if(!player.getUniqueId().equals(p.getUniqueId()) && LinmaluMath.distance(p.getLocation(), player.getLocation()) < 15)
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, false, false), true);
							p.sendMessage(ItemData.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템 효과에 걸렸습니다.");
						}
					}
					break;
				case 총:
					new MiniGameShoot7(player);
					message = false;
					break;
				default:
					return;
			}
			if(remove)
			{
				item.setAmount(item.getAmount() - 1);
			}
			if(cooldown > 0)
			{
				new Cooldown(cooldown, player, false);
			}
			if(message)
			{
				player.sendMessage(ItemData.getItemStack().getItemMeta().getDisplayName() + ChatColor.YELLOW + " 아이템을 사용했습니다.");
			}
		}
	}
}
