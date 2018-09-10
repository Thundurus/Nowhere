package model.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import model.Character;
import model.Effect;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;
import model.StatusEffect;

public class WaterSphere extends Skill
{
	public WaterSphere()
	{
		name = "Water Sphere";
		pName = "Water Sphere";
		baseArchetype = SkillArchetype.SPELL;
		baseCost = 60;
		flags.add(SkillFlags.HITSCAN);
		flags.add(SkillFlags.SPELL);
		flags.add(SkillFlags.UNREFLECTABLE);
		flags.add(SkillFlags.UNBLOCKABLE);
		flags.add(SkillFlags.MAGICAL);
		flags.add(SkillFlags.HYDRIC);
	}
	
	@Override
	public String getPossibleTargets()
	{
		return "one";
	}

	@Override
	public void executeOnSelf(Character user)
	{
	}

	@Override
	public void executeOnTarget(Character target)
	{
		Effect waterSphere = new Effect("Water Sphere", new HashMap<String, Integer>(), new ArrayList<StatusEffect>(List.of(new model.StatusEffects.Incapacitated())), 3);
		Supplier<Void> effect = () -> {
			System.out.println(target.getName());
			return null;
		};
		waterSphere.setEffect(effect);
		if(!target.getEffects().contains(waterSphere))
		{
			target.addEffect(waterSphere);
		}
		else
		{
			//Do nothing?
		}
	}

}
