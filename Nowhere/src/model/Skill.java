package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

import model.SkillFlags;

public abstract class Skill
{
	//TODO: Maybe fix the visibility of these and add get/set methods as necessary.
	public String name,
	pName,
	description;
	
	/**
	 * This hashmap is used to determine when each effect is activated, and uses the following structure:
	 * <p><code>[First__Effect][isBeforeProcess]</code>
	 * <p><code>[Second_Effect][isBeforeProcess]</code>
	 * <p>where the effects are the executeOnSelf and executeOnTarget methods (ordered as desired) and the boolean is whether or not the effect triggers before the other effects of the skill are processed.
	 * <p>Either of these can be left out (but not null) when the relevant method does not do anything.
	 */
	public LinkedHashMap<Consumer<Character>, Boolean> effectOrder = new LinkedHashMap<Consumer<Character>, Boolean>();
	
	public int priority = 0, multiplier = 1;
	
	public double baseCost = 0, accuracyModifier = 1.0, skillModifier = 1.0;
	
	public SkillArchetype baseArchetype;
	
	public ArrayList<SkillFlags> flags = new ArrayList<SkillFlags>();
	
	//Not sure if this is really relevant.
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
	
	/**
	 * Returns the string representation of the target types supported by this {@link Skill}..
	 * Possible targets include:
	 * <p>"none" (the target is area in the fight, generally used by terrain-altering skills
	 * <p>"one" (any one target)
	 * <p>"self" (only the user)
	 * <p>"not self" (any one target besides the user)
	 * <p>"enemies" (all fighters whose {@link Character#fightingStats fightingStats}[4] != user.fightingStats[4])
	 * <p>"allies" (all fighters whose {@link Character#fightingStats fightingStats}[4] == user.fightingStats[4])
	 * <p>"else" (all active fighters other than the user)
	 * <p>"all" (all active fighters in the combat instance)
	 * 
	 * @return
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
	public abstract Skill clone();
	
	
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
