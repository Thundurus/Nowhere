package model.Skills;

import java.util.ArrayList;

import model.Character;
import model.Damage;
import model.DamageArray;
import model.DamageFlags;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;

public class AvidiaHolyWater extends Skill implements Attack, Beam
{
	public AvidiaHolyWater()
	{
		name = "Avidia Holy Water";
		pName = "Ultimate Demon Technique - Final Skill: Avidia Holy Water";
		baseArchetype = SkillArchetype.ATTACK;
		baseCost = 60;
		flags.add(SkillFlags.ATTACK);
		flags.add(SkillFlags.CHI);
		flags.add(SkillFlags.ADVANCING);
		flags.add(SkillFlags.BEAM);
	}
	
	@Override
	public void executeOnSelf(Character user)
	{
	}

	@Override
	public void executeOnTarget(Character target)
	{
		//TODO: Purification effect
	}

	@Override
	public boolean conflictsWith(Skill otherSkill)
	{
		if(otherSkill.flags.contains(SkillFlags.BEAM))
		{
			handleBeamStruggle(otherSkill);
			return true;
		}
		return false;
	}
	
	@Override
	public void handleBeamStruggle(Skill otherSkill)
	{
		System.out.println("beam struggle");
	}

	//TODO: These two methods apply to the "beam-only" version.
	@Override
	public DamageArray baseDamage(Character user)
	{
		calculateSkillModifier(user);
		return new DamageArray(new Damage(flags, damageFlags(), model.Type.AURA), skillModifier * 50);
	}

	@Override
	public ArrayList<DamageFlags> damageFlags()
	{
		ArrayList<DamageFlags> damageFlags = new ArrayList<DamageFlags>();
		damageFlags.add(DamageFlags.AURA);
		return damageFlags;
	}
}
