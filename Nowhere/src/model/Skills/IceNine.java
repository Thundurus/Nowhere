package model.Skills;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import model.ActParser;
import model.Character;
import model.Master;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;
import model.Type;
import view.TextManager;
import view.TextStyle;

public class IceNine extends Skill implements Counter
{
	public IceNine()
	{
		name = "Ice Nine";
		pName = "Ice Nine";
		description = "A devastating counter-attack that works on water and liquid moves.";
		priority = 3;
		baseArchetype = SkillArchetype.COUNTER;
		baseCost = 20;
		type = Type.ICE;
		flags.add(SkillFlags.SPELL);
		flags.add(SkillFlags.MAGICAL);
		flags.add(SkillFlags.FREEZING);
		flags.add(SkillFlags.COUNTER);
	}
	
	@Override
	public void counter(Character counter, Character target, Skill skill)
	{
		ActParser.parse(counter, new IceNineAttack(skill), new ArrayList<Character>(List.of(target)));
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
	public IceNine clone()
	{
		IceNine clone = new IceNine();
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
	
	@Override
	public String preparation(Character character)
	{
		if(character.equals(Master.getPlayerCharacter()))
			return "You summon a thin barrier of icy mist.";
		else
			return character.getName() + " summons a thin barrier of icy mist.";
	}

	@Override
	public Function<Skill, Boolean> counterable()
	{
		Function<Skill, Boolean> counterable = (Skill skill) -> {
			TextManager.appendText(("Determining if " + name + " counters " + skill.name + "."), TextStyle.DEBUG);
			ArrayList<SkillFlags> sflags = skill.flags;
			//Should it also be ignoring the "Buff" flag? It would be pretty funny but whether or not it is desirable behavior remains to be seen.
			if(!sflags.contains(SkillFlags.UNCOUNTERABLE) && (sflags.contains(SkillFlags.HYDRAULIC) || sflags.contains(SkillFlags.HYDRIC)))
				return true;
			return false;
		};
		return counterable;
	}
	
	
}
