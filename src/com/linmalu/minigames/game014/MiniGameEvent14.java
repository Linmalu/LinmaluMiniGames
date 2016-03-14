package com.linmalu.minigames.game014;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.packetwrapper.WrapperPlayServerNamedSoundEffect;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.linmalu.minigames.data.Cooldown;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.data.PlayerData;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent14 extends MiniGameEvent
{
	public MiniGameEvent14(MiniGame minigame)
	{
		super(minigame);
		registerPacketEvent(PacketType.Play.Server.NAMED_SOUND_EFFECT, PacketType.Play.Server.SPAWN_ENTITY_LIVING);
	}
	@EventHandler
	public void Event(EntityDamageByEntityEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()) && event.getDamager() instanceof Player)
		{
			Player player1 = (Player)event.getDamager();
			PlayerData pd1 = data.getPlayerData(player1.getUniqueId());
			if(event.getEntity() instanceof Player)
			{
				Player player2 = (Player) event.getEntity();
				PlayerData pd2 = data.getPlayerData(player2.getUniqueId());
				if(pd1 != null && pd2 != null && pd1.isLive() && pd2.isLive())
				{
					pd1.addScore();
					new Cooldown(0, player2, true);
					data.teleportPlayer(player2);
				}
			}
			else if(pd1 != null && pd1.isLive())
			{
				player1.getWorld().createExplosion(event.getEntity().getLocation(), 4F, false);
			}
		}
	}
	@Override
	protected void onServerPacket(PacketEvent event)
	{
		if(checkEvent(event.getPlayer().getWorld()))
		{
			if(event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT)
			{
				if(new WrapperPlayServerNamedSoundEffect(event.getPacket()).getSoundName().contains("sheep"))
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
					packet.setX(entity.getX());
					packet.setY(entity.getY());
					packet.setZ(entity.getZ());
					packet.setPitch(entity.getHeadPitch());
					packet.setPlayerUUID(event.getPlayer().getUniqueId());
					packet.setMetadata(WrappedDataWatcher.getEntityWatcher(event.getPlayer()));
					packet.sendPacket(event.getPlayer());
					event.setCancelled(true);
				}
			}
		}
	}
}