package com.linmalu.minigames.game005;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.packetwrapper.WrapperPlayServerNamedSoundEffect;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent5 extends MiniGameEvent
{
	public MiniGameEvent5(MiniGame minigame)
	{
		super(minigame);
		registerPacketEvent(PacketType.Play.Server.NAMED_SOUND_EFFECT, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.ENTITY_METADATA);
	}
	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(PlayerMoveEvent event)
	{
		if(checkEvent(event.getPlayer().getWorld()))
		{
			new MiniGameMoving5(event.getPlayer(), event.getPlayer().getLocation().clone());
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
					for(Entry<UUID, List<Entity>> entry : data.getPlayerEntitys().entrySet())
					{
						for(Entity e : entry.getValue())
						{
							if(e.getEntityId() == entity.getEntityID())
							{
								WrapperPlayServerNamedEntitySpawn packet = new WrapperPlayServerNamedEntitySpawn();
								packet.setEntityID(entity.getEntityID());
								packet.setPlayerUUID(entry.getKey());
								packet.setX(entity.getX());
								packet.setY(entity.getY());
								packet.setZ(entity.getZ());
								packet.setPitch(entity.getHeadPitch());
								packet.setMetadata(WrappedDataWatcher.getEntityWatcher(Bukkit.getEntity(entry.getKey())));
								packet.sendPacket(event.getPlayer());
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
}
