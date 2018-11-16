package model.Skills;

import java.util.ArrayList;

import model.Character;
import model.Damage;
import model.DamageArray;
import model.DamageFlags;
import model.Skill;
import model.SkillArchetype;
import model.SkillFlags;
import model.Type;

public class IceNineAttack extends Skill implements Attack
{
	public Skill trigger;
	
	public IceNineAttack(Skill trigger)
	{
		this.trigger = trigger;
		name = "Ice Nine 2";
		pName = "Ice Nine - Crystal Lances";
		baseArchetype = SkillArchetype.ATTACK;
		multiplier = 9;
		flags.add(SkillFlags.DISJOINTED);
		flags.add(SkillFlags.SPELL);
		flags.add(SkillFlags.PENETRATING);
		flags.add(SkillFlags.UNABSORBABLE);
		flags.add(SkillFlags.MULTIHIT);
	}

	@Override
	public void executeOnSelf(Character user)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeOnTarget(Character target)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public DamageArray baseDamage(Character user)
	{
		calculateSkillModifier(user);
		return new DamageArray(new Damage(flags, damageFlags(), Type.ICE), skillModifier * 10);
	}

	@Override
	public ArrayList<DamageFlags> damageFlags()
	{
		ArrayList<DamageFlags> damageFlags = new ArrayList<DamageFlags>();
		damageFlags.add(DamageFlags.MAGICAL);
		return damageFlags;
	}

	@Override
	public IceNineAttack clone()
	{
		IceNineAttack clone = new IceNineAttack(trigger);
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
		clone.trigger = this.trigger;
		return clone;
	}

}
