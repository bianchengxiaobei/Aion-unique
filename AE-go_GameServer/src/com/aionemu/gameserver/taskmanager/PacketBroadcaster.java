/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.taskmanager;

import com.aionemu.gameserver.configs.Config;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author lord_rex and MrPoke
 * 
 */
public class PacketBroadcaster extends AbstractPeriodicTaskManager<Creature>
{
	private static final class SingletonHolder
	{
		private static final PacketBroadcaster	INSTANCE	= new PacketBroadcaster();
	}

	public static PacketBroadcaster getInstance()
	{
		return SingletonHolder.INSTANCE;
	}

	private PacketBroadcaster()
	{
		super(100);
	}

	public static enum BroadcastMode
	{
		UPDATE_PLAYER_HP_STAT {
			@Override
			public void sendPacket(Creature creature)
			{
				((Player) creature).getLifeStats().sendHpPacketUpdateImpl();
				
				if(Config.DEBUG_PACKET_BROADCASTER)
					PacketSendUtility.sendMessage(((Player) creature), "PacketBroadcast: Your HP stat is updated.");
			}
		},
		UPDATE_PLAYER_MP_STAT {
			@Override
			public void sendPacket(Creature creature)
			{
				((Player) creature).getLifeStats().sendMpPacketUpdateImpl();
				
				if(Config.DEBUG_PACKET_BROADCASTER)
					PacketSendUtility.sendMessage(((Player) creature), "PacketBroadcast: Your MP stat is updated.");
			}
		},
		UPDATE_PLAYER_EFFECT_ICONS {
			@Override
			public void sendPacket(Creature creature)
			{
				((Player) creature).getEffectController().updatePlayerEffectIconsImpl();
				
				if(Config.DEBUG_PACKET_BROADCASTER)
					PacketSendUtility.sendMessage(((Player) creature), "PacketBroadcast: Your effect icons are updated.");
			}
		},
		UPDATE_NEARBY_QUEST_LIST {
			@Override
			public void sendPacket(Creature creature)
			{
				((Player) creature).getController().updateNearbyQuestListImpl();
				
				if(Config.DEBUG_PACKET_BROADCASTER)
					PacketSendUtility.sendMessage(((Player) creature), "PacketBroadcast: Your quest list is updated.");
			}
		},
		// TODO: more packets
		;

		private final byte	MASK;

		private BroadcastMode()
		{
			MASK = (byte) (1 << ordinal());
		}

		public byte mask()
		{
			return MASK;
		}

		protected abstract void sendPacket(Creature creature);

		protected final void trySendPacket(Creature creature, byte mask)
		{
			if((mask & mask()) == mask())
			{
				sendPacket(creature);

				creature.removePacketBroadcastMask(this);
			}
		}
	}

	private static final BroadcastMode[]	VALUES	= BroadcastMode.values();

	@Override
	protected void callTask(Creature creature)
	{
		for(byte mask; (mask = creature.getPacketBroadcastMask()) != 0;)
		{
			for(BroadcastMode mode : VALUES)
			{
				mode.trySendPacket(creature, mask);
			}
		}
	}
}
