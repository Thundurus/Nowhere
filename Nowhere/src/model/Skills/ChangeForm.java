package model.Skills;

import model.Character;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;
import model.Type;
import view.TextManager;
import view.TextStyle;

public class ChangeForm extends Skill
{
	public Character user;
	public int formOffset;
	
	public ChangeForm(Character user, int formOffset)
	{
		name = "Change Form";
		pName = "Change into " + user.getFormName(formOffset) + ".";
		description = "Change into another form.";
		priority = 4;
		baseArchetype = SkillArchetype.TRANSFORM;
		type = Type.NULL;
		this.user = user;
		this.formOffset = formOffset;
		flags.add(SkillFlags.TRANSFORM);
	}
	
	@Override
	public void executeOnSelf(Character user)
	{
		user.changeForm(formOffset);
	}

	@Override
	public void executeOnTarget(Character target)
	{
	}
	
	@Override
	public ChangeForm clone()
	{
		//This should not really be happening.
		TextManager.appendText(("Attempted to perform clone on skill \"Change Form\"."), TextStyle.ERROR);
		ChangeForm clone = new ChangeForm(user, formOffset);
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
