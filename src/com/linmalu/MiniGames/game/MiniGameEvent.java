package com.linmalu.minigames.game;

import org.bukkit.World;
import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.linmalu.minigames.Main;
import com.linmalu.minigames.data.GameData;
import com.linmalu.minigames.data.MiniGame;

public abstract class MiniGameEvent implements Listener
{
	protected final MiniGame minigame;
	protected final GameData data;

	public MiniGameEvent(MiniGame minigame)
	{
		this.minigame = minigame;
		this.data = Main.getMain().getGameData();
	}
	protected boolean checkEvent(World world)
	{
		return data.isGame2() && data.getMinigame() == minigame && world.getName().equals(Main.WORLD_NAME);
	}
	protected void registerPacketEvent(PacketType ... packetTypes)
	{
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getMain(), packetTypes)
		{
			@Override
			public void onPacketReceiving(PacketEvent event)
			{
				onClientPacket(event);
			}
			@Override
			public void onPacketSending(PacketEvent event)
			{
				onServerPacket(event);
			}
		});
	}
	protected void onClientPacket(PacketEvent event)
	{
	}
	protected void onServerPacket(PacketEvent event)
	{
	}
}
