package com.linmalu.minigames.game014;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.packetwrapper.WrapperPlayServerNamedSoundEffect;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.WrappedDataWatcherObject;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.linmalu.minigames.data.Cooldown;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent14 extends MiniGameEvent
{
	public MiniGameEvent14(MiniGame minigame)
	{
		super(minigame);
		registerPacketEvent(PacketType.Play.Server.NAMED_SOUND_EFFECT, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.ENTITY_METADATA);
	}
	@EventHandler
	public void Event(EntityDamageByEntityEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()) && event.getDamager() instanceof Player)
		{
			Player player1 = (Player)event.getDamager();
			PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
			if(pd1 != null && pd1.isLive() && pd1.isCooldown())
			{
				if(event.getEntity() instanceof Player)
				{
					Player player2 = (Player)event.getEntity();
					PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
					if(pd2 != null && pd2.isLive())
					{
						pd1.addScore();
						new Cooldown(0, player2, true);
						minigame.getInstance().teleport(player2);
					}
				}
				else if(event.getEntity() instanceof Sheep)
				{
					event.getEntity().setTicksLived(1);
					new Cooldown(3, player1, true);
					player1.getWorld().createExplosion(event.getEntity().getLocation(), 4F, false);
				}
			}
		}
	}
	@EventHandler
	public void Event(EntityDamageByBlockEvent event)
	{
		Entity entity = event.getEntity();
		if(checkEvent(entity.getWorld()) && entity.getType() == EntityType.SHEEP && entity.getTicksLived() > 100)
		{
			entity.setTicksLived(1);
		}
	}
	@Override
	protected void onServerPacket(PacketEvent event)
	{
		if(checkEvent(event.getPlayer().getWorld()))
		{
			if(event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT)
			{
				if(new WrapperPlayServerNamedSoundEffect(event.getPacket()).getSoundEffect().toString().contains("SHEEP"))
				{
					event.setCancelled(true);
				}
			}
			else if(event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING)
			{
				WrapperPlayServerSpawnEntityLiving entity = new WrapperPlayServerSpawnEntityLiving(event.getPacket());
				if(entity.getType() == EntityType.SHEEP)
				{
					WrapperPlayServerNamedEntitySpawn packet = new WrapperPlayServerNamedEntitySpawn();
					packet.setEntityID(entity.getEntityID());
					packet.setPlayerUUID(event.getPlayer().getUniqueId());
					packet.setX(entity.getX());
					packet.setY(entity.getY());
					packet.setZ(entity.getZ());
					packet.setPitch(entity.getHeadPitch());
					packet.setMetadata(WrappedDataWatcher.getEntityWatcher(event.getPlayer()));
					packet.sendPacket(event.getPlayer());
					event.setCancelled(true);
				}
			}
			else if(event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA)
			{
				WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(event.getPacket());
				boolean check = true;
				for(WrappedWatchableObject data : packet.getMetadata())
				{
					if(data.getIndex() == 13)
					{
						check = false;
						data.setValue((byte)127);
					}
				}
				if(check)
				{
					packet.getMetadata().add(new WrappedWatchableObject(new WrappedDataWatcherObject(13, Registry.get(Byte.class)), (byte)127));
				}
			}
		}
	}
}
