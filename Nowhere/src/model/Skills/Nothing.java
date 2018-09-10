package model.Skills;

import model.Character;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;

public class Nothing extends Skill
{
	public Nothing()
	{
		name = "Nothing";
		pName = "Nothing";
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
}
