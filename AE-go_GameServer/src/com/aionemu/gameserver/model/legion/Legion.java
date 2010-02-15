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
package com.aionemu.gameserver.model.legion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.aionemu.gameserver.configs.LegionConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.World;

/**
 * @author Simple
 * 
 */
public class Legion
{
	private int								legionId				= 0;
	private String							legionName				= "";
	private int								legionLevel				= 1;
	private int								legionRank				= 0;
	private int								legionContribution		= 0;
	private ArrayList<Integer>				legionMembers			= new ArrayList<Integer>();
	private static final int				legionarPermission1		= 0x40;
	private int								legionarPermission2		= 0x00;
	private int								centurionPermission1	= 0x60;
	private int								centurionPermission2	= 0x00;
	private LinkedHashMap<Integer, String>	announcementList		= new LinkedHashMap<Integer, String>();

	/**
	 * Only called when a legion is created!
	 * 
	 * @param legionId
	 * @param legionName
	 */
	public Legion(int legionId, String legionName)
	{
		this.legionId = legionId;
		this.legionName = legionName;
	}

	/**
	 * Only called when a legion is loaded!
	 * 
	 * @param legionId
	 * @param legionName
	 */
	public Legion(int legionId)
	{
		this.legionId = legionId;
	}

	/**
	 * @param legionId
	 *            the legionId to set
	 */
	public void setLegionId(int legionId)
	{
		this.legionId = legionId;
	}

	/**
	 * @return the legionId
	 */
	public int getLegionId()
	{
		return legionId;
	}

	/**
	 * @param legionName
	 *            the legionName to set
	 */
	public void setLegionName(String legionName)
	{
		this.legionName = legionName;
	}

	/**
	 * @return the legionName
	 */
	public String getLegionName()
	{
		return legionName;
	}

	/**
	 * @param legionMembers
	 *            the legionMembers to set
	 */
	public void setLegionMembers(ArrayList<Integer> legionMembers)
	{
		this.legionMembers = legionMembers;
	}

	/**
	 * @return the legionMembers
	 */
	public ArrayList<Integer> getLegionMembers()
	{
		return legionMembers;
	}

	/**
	 * @return the online legionMembers
	 */
	public ArrayList<Player> getOnlineLegionMembers(World world)
	{
		ArrayList<Player> onlineLegionMembers = new ArrayList<Player>();
		for(int legionMemberObjId : legionMembers)
		{
			Player onlineLegionMember = world.findPlayer(legionMemberObjId);
			if(onlineLegionMember != null)
				onlineLegionMembers.add(onlineLegionMember);
		}
		return onlineLegionMembers;
	}

	/**
	 * @return the legionMembers
	 */
	public int getLegionMember(int playerObjId)
	{
		return legionMembers.indexOf(playerObjId);
	}

	/**
	 * Add a legionMember to the legionMembers list
	 * 
	 * @param legionMember
	 */
	public boolean addLegionMember(int playerObjId)
	{
		if(canAddMember())
		{
			legionMembers.add(playerObjId);
			return true;
		}
		return false;
	}

	/**
	 * Delete a legionMember from the legionMembers list
	 * 
	 * @param playerObjId
	 */
	public void deleteLegionMember(int playerObjId)
	{
		ArrayList<Integer> newLegionMembers = new ArrayList<Integer>();
		for(int memberObjId : legionMembers)
		{
			if(memberObjId != playerObjId)
			{
				newLegionMembers.add(memberObjId);
			}
		}
		setLegionMembers(newLegionMembers);
	}

	/**
	 * @param legionarPermission1
	 *            the legionarPermission1 to set
	 */
	public void setLegionarPermissions(int legionarPermission2, int centurionPermission1, int centurionPermission2)
	{
		this.legionarPermission2 = legionarPermission2;
		this.centurionPermission1 = centurionPermission1;
		this.centurionPermission2 = centurionPermission2;
	}

	/**
	 * @return the legionarPermission1
	 */
	public int getLegionarPermission1()
	{
		return legionarPermission1;
	}

	/**
	 * @return the legionarPermission2
	 */
	public int getLegionarPermission2()
	{
		return legionarPermission2;
	}

	/**
	 * @return the centurionPermission1
	 */
	public int getCenturionPermission1()
	{
		return centurionPermission1;
	}

	/**
	 * @return the centurionPermission2
	 */
	public int getCenturionPermission2()
	{
		return centurionPermission2;
	}

	/**
	 * @return the legionLevel
	 */
	public int getLegionLevel()
	{
		return legionLevel;
	}

	/**
	 * @param legionLevel
	 */
	public void setLegionLevel(int legionLevel)
	{
		this.legionLevel = legionLevel;
	}

	/**
	 * @param legionRank
	 *            the legionRank to set
	 */
	public void setLegionRank(int legionRank)
	{
		this.legionRank = legionRank;
	}

	/**
	 * @return the legionRank
	 */
	public int getLegionRank()
	{
		return legionRank;
	}

	/**
	 * @param legionContribution
	 *            the legionContribution to set
	 */
	public void setLegionContribution(int legionContribution)
	{
		this.legionContribution = legionContribution;
	}

	/**
	 * @return the legionContribution
	 */
	public int getLegionContribution()
	{
		return legionContribution;
	}

	public boolean hasRequiredMembers()
	{
		switch(getLegionLevel())
		{
			case 1:
				if(getLegionMembers().size() >= LegionConfig.LEGION_LEVEL2_REQUIRED_MEMBERS)
					return true;
				break;
			case 2:
				if(getLegionMembers().size() >= LegionConfig.LEGION_LEVEL3_REQUIRED_MEMBERS)
					return true;
				break;
			case 3:
				if(getLegionMembers().size() >= LegionConfig.LEGION_LEVEL4_REQUIRED_MEMBERS)
					return true;
				break;
			case 4:
				if(getLegionMembers().size() >= LegionConfig.LEGION_LEVEL5_REQUIRED_MEMBERS)
					return true;
				break;
		}
		return false;
	}
	
	public int getKinahPrice()
	{
		switch(getLegionLevel())
		{
			case 1:
				return LegionConfig.LEGION_LEVEL2_REQUIRED_KINAH;
			case 2:
				return LegionConfig.LEGION_LEVEL3_REQUIRED_KINAH;
			case 3:
				return LegionConfig.LEGION_LEVEL4_REQUIRED_KINAH;
			case 4:
				return LegionConfig.LEGION_LEVEL5_REQUIRED_KINAH;
		}
		return 0;
	}
	
	public int getContributionPrice()
	{
		switch(getLegionLevel())
		{
			case 1:
				return LegionConfig.LEGION_LEVEL2_REQUIRED_CONTRIBUTION;
			case 2:
				return LegionConfig.LEGION_LEVEL3_REQUIRED_CONTRIBUTION;
			case 3:
				return LegionConfig.LEGION_LEVEL4_REQUIRED_CONTRIBUTION;
			case 4:
				return LegionConfig.LEGION_LEVEL5_REQUIRED_CONTRIBUTION;
		}
		return 0;
	}

	private boolean canAddMember()
	{
		switch(getLegionLevel())
		{
			case 1:
				if(getLegionMembers().size() < LegionConfig.LEGION_LEVEL1_MAX_MEMBERS)
					return true;
				break;
			case 2:
				if(getLegionMembers().size() < LegionConfig.LEGION_LEVEL2_MAX_MEMBERS)
					return true;
				break;
			case 3:
				if(getLegionMembers().size() < LegionConfig.LEGION_LEVEL3_MAX_MEMBERS)
					return true;
				break;
			case 4:
				if(getLegionMembers().size() < LegionConfig.LEGION_LEVEL4_MAX_MEMBERS)
					return true;
				break;
			case 5:
				if(getLegionMembers().size() < LegionConfig.LEGION_LEVEL5_MAX_MEMBERS)
					return true;
				break;
		}
		return false;
	}

	/**
	 * @param announcementList
	 *            the announcementList to set
	 */
	/*
	 * public void addAnnouncement(int unixTime, String message) { LinkedHashMap<Integer, String> newAnnouncementList =
	 * new LinkedHashMap<Integer, String>(); for announcementList.put(unixTime, message); this.setStateChanged(true); }
	 */

	/**
	 * @param announcementList
	 *            the announcementList to set
	 */
	public void setAnnouncementList(LinkedHashMap<Integer, String> announcementList)
	{
		this.announcementList = announcementList;
	}

	/**
	 * @return the announcementList
	 */
	public LinkedHashMap<Integer, String> getAnnouncementList()
	{
		return announcementList;
	}

	/**
	 * @return the currentAnnouncement
	 */
	public Entry<Integer, String> getCurrentAnnouncement()
	{
		if(announcementList.size() > 0)
		{
			Entry<Integer, String> currentAnnouncement = null;
			for(Entry<Integer, String> unixTime : announcementList.entrySet())
			{
				if(currentAnnouncement == null)
				{
					currentAnnouncement = unixTime;
				}
				else
				{
					if(currentAnnouncement.getKey() < unixTime.getKey())
					{
						currentAnnouncement = unixTime;
					}
				}
			}
			return currentAnnouncement;
		}
		return null;
	}
}