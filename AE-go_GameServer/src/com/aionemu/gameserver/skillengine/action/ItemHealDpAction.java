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
package com.aionemu.gameserver.skillengine.action;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;


/**
 * @author ATracer
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemHealDpAction")
public class ItemHealDpAction
extends Action
{

	private static final Logger log = Logger.getLogger(ItemHealDpAction.class);
	
	@XmlAttribute(required = true)
	protected int value;

	@XmlAttribute
	protected int delta;

	/**
	 * Gets the value of the value property.
	 * 
	 */
	public int getValue() 
	{
		return value;
	}

	@Override
	public void act(Skill skill)
	{
		int valueWithDelta = value + delta * skill.getSkillLevel();

		List<Creature> effectedList = skill.getEffectedList();
		for(Creature effected : effectedList)
		{
			if(effected instanceof Player)
			{
				((Player)effected).getCommonData().addDp(valueWithDelta);
			}
			else
			{
				log.error("Using DP restore skill on non-player target " + skill.getSkillTemplate().getSkillId());
			}
		}
	}
}