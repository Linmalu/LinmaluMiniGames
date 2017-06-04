package com.linmalu.minigames.game005;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import com.comphenix.packetwrapper.WrapperPlayServerNamedSoundEffect;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.linmalu.minigames.data.MiniGame;
import com.linmalu.minigames.game.MiniGameEvent;

public class MiniGameEvent5 extends MiniGameEvent
{
	public MiniGameEvent5(MiniGame minigame)
	{
		super(minigame);
		registerPacketEvent(PacketType.Play.Server.NAMED_SOUND_EFFECT);
	}
	@EventHandler
	public void Event(EntityDamageEvent event)
	{
		if(checkEvent(event.getEntity().getWorld()))
		{
			event.setCancelled(true);
		}
	}
	@Override
	protected void onServerPacket(PacketEvent event)
	{
		if(checkEvent(event.getPlayer().getWorld()))
		{
			if(event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT)
			{
				if(new WrapperPlayServerNamedSoundEffect(event.getPacket()).getSoundEffect().toString().contains("sheep"))
				{
					event.setCancelled(true);
				}
			}
		}
	}
}