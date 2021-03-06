package model.Skills;

import java.util.ArrayList;

import model.Character;
import model.Damage;
import model.DamageArray;
import model.DamageFlags;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;

public class WaterDragon extends Skill implements Attack
{
	public WaterDragon()
	{
		name = "Water Dragon";
		pName = "Water Style: Explosive Bite of the Water Dragon";
		baseArchetype = SkillArchetype.ATTACK;
		baseCost = 60;
		flags.add(SkillFlags.ATTACK);
		flags.add(SkillFlags.PROJECTILE);
		flags.add(SkillFlags.SPELL);
		flags.add(SkillFlags.MAGICAL);
		flags.add(SkillFlags.HYDRIC);
		flags.add(SkillFlags.HYDRAULIC);
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
		return new DamageArray(new Damage(flags, damageFlags(), model.Type.WATER), skillModifier * 40);
	}

	@Override
	public ArrayList<DamageFlags> damageFlags()
	{
		ArrayList<DamageFlags> damageFlags = new ArrayList<DamageFlags>();
		damageFlags.add(DamageFlags.MAGICAL);
		return damageFlags;
	}
	
	@Override
	public WaterDragon clone()
	{
		WaterDragon clone = new WaterDragon();
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
