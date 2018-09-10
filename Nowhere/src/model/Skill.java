package model;

import java.util.ArrayList;
import model.SkillFlags;

public abstract class Skill
{
	//TODO: Maybe fix the visibility of these and add get/set methods as necessary.
	public String name,
	pName,
	description;
	
	public int priority = 0, multiplier = 1;
	
	public double baseCost = 0, accuracyModifier = 1.0, skillModifier = 1.0;
	
	public SkillArchetype baseArchetype;
	
	public ArrayList<SkillFlags> flags = new ArrayList<SkillFlags>();
	
	public Type type = Type.NORMAL;
	
	//Might just leave this stuff public instead..
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getpName()
	{
		return pName;
	}
	public void setpName(String pName)
	{
		this.pName = pName;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public SkillArchetype getBaseArchetype()
	{
		return baseArchetype;
	}
	public void setBaseArchetype(SkillArchetype baseArchetype)
	{
		this.baseArchetype = baseArchetype;
	}
	public int getPriority()
	{
		return priority;
	}
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	public double getBaseCost()
	{
		return baseCost;
	}
	public void setBaseCost(double baseCost)
	{
		this.baseCost = baseCost;
	}
	public double getAccuracyModifier()
	{
		return accuracyModifier;
	}
	public void setAccuracyModifier(double accuracyModifier)
	{
		this.accuracyModifier = accuracyModifier;
	}
	public double getSkillModifier()
	{
		return skillModifier;
	}
	public void setSkillModifier(double skillModifier)
	{
		this.skillModifier = skillModifier;
	}
	public ArrayList<SkillFlags> getFlags()
	{
		return flags;
	}
	public void setFlags(ArrayList<SkillFlags> flags)
	{
		this.flags = flags;
	}
	public Type getType()
	{
		return type;
	}
	public void setType(Type type)
	{
		this.type = type;
	}

	/**
	 * Sets the skillModifier based on however it is calculated.
	 * The effect of skillModifier is skill-specific, but in general can be thought to increase the effectiveness of the skill.
	 * @param character: The user of the skill.
	 */
	public void calculateSkillModifier(model.Character user)
	{
		
	}
	
	public boolean conflictsWith(Skill otherSkill)
	{
		return false;
	}
	
	/*
	 * Returns the string representation of the target types it supports.
	 * Possible targets include:
	 * "none" (the target is area in the fight, generally used by terrain-altering skills
	 * "one" (any one target)
	 * "self" (only the user)
	 * "not self" (any one target besides the user)
	 * "enemies" (all fighters whose fightingStats[4] != user.fightingStats[4])
	 * "allies" (all fighters whose fightingStats[4] == user.fightingStats[4])
	 * "else" (all active fighters other than the user)
	 * "all" (all active fighters in the combat instance)
	 */
	public String getPossibleTargets()
	{
		return "not self";
	}
	
	public void execute(Character user, Character target)
	{
		executeOnSelf(user);
		executeOnTarget(target);
	}
	
	public boolean isAvailable(Character user)
	{
		return true;
	}
	
	//Any special effects that the skill will inflict on its user or target(s).
	public abstract void executeOnSelf(Character user);
	public abstract void executeOnTarget(Character target);
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((Skill) obj).name)
			return true;
		return false;
	}
	//Completely secure and cromulent hashing algorithm #2.
	@Override
	public int hashCode()
	{
		int i = 0, j = 0;
		for(char c : name.toCharArray())
		{
			j += (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 2);
			i++;
		}
		return j;
	}
}
