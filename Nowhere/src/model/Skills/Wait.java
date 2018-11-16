package model.Skills;

import model.Character;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;

public class Wait extends Skill
{
	public Wait()
	{
		name = "Wait";
		pName = "Wait";
		baseArchetype = SkillArchetype.WAIT;
		description = "Do nothing.";
		flags.add(SkillFlags.WAIT);		
	}

	@Override
	public String getPossibleTargets()
	{
		return "self";
	}
	
	@Override
	public void executeOnSelf(Character user)
	{
	}

	@Override
	public void executeOnTarget(Character target)
	{
	}
	
	@Override
	public Wait clone()
	{
		Wait clone = new Wait();
		clone.name = this.name;
		clone.pName = this.pName;
		clone.description = this.description;
		clone.priority = this.priority;
		clone.multiplier = this.multiplier;
		clone.baseCost = this.baseCost;
		clone.accuracyModifier = this.accuracyModifier;
		clone.skillModifier = this.skillModifier;
		clone.baseArchetype = this.baseArchetype;
		clone.flags.addAll(this.flags);
		clone.type = this.type;
		return clone;
	}
}
