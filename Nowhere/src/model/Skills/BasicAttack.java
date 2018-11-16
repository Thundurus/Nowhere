package model.Skills;

import java.util.ArrayList;

import model.Character;
import model.Damage;
import model.DamageArray;
import model.DamageFlags;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;

public class BasicAttack extends Skill implements Attack
{
	public BasicAttack()
	{
		name = "Attack";
		pName = "Attack";
		baseArchetype = SkillArchetype.ATTACK;
		description = "Attack with your equipped weapon.";
		flags.add(SkillFlags.ATTACK);
		flags.add(SkillFlags.MELEE);		
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
		return new DamageArray(new Damage(flags, damageFlags(), model.Type.NORMAL), skillModifier * 10);
	}

	@Override
	public ArrayList<DamageFlags> damageFlags()
	{
		ArrayList<DamageFlags> damageFlags = new ArrayList<DamageFlags>();
		damageFlags.add(DamageFlags.PHYSICAL);
		return damageFlags;
	}
	
	@Override
	public BasicAttack clone()
	{
		BasicAttack clone = new BasicAttack();
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
