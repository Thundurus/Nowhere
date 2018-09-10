package model.Skills;

import java.util.ArrayList;

import model.Character;
import model.Damage;
import model.DamageArray;
import model.DamageFlags;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;

public class SummonedGatling extends Skill implements Attack
{
	public SummonedGatling()
	{
		name = "S. Gatling";
		pName = "Summoned Gatling";
		baseArchetype = SkillArchetype.ATTACK;
		baseCost = 25;
		multiplier = 8;
		flags.add(SkillFlags.ATTACK);
		flags.add(SkillFlags.PROJECTILE);
		flags.add(SkillFlags.MISSILE);
		flags.add(SkillFlags.SPELL);
		flags.add(SkillFlags.MULTIHIT);
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
	public DamageArray baseDamage(Character user)
	{
		calculateSkillModifier(user);
		return new DamageArray(new Damage(flags, damageFlags(), model.Type.STEEL), skillModifier * 5);
	}

	@Override
	public ArrayList<DamageFlags> damageFlags()
	{
		ArrayList<DamageFlags> damageFlags = new ArrayList<DamageFlags>();
		damageFlags.add(DamageFlags.PHYSICAL);
		return damageFlags;
	}
}
