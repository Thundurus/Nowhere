package model;

import java.util.ArrayList;

public class Damage
{
	public ArrayList<SkillFlags> skillFlags = new ArrayList<SkillFlags>();
	public ArrayList<DamageFlags> damageFlags = new ArrayList<DamageFlags>();
	public Type type = Type.NORMAL;
	
	public Damage()
	{
		
	}
	
	public Damage(ArrayList<DamageFlags> damageFlags, Type type)
	{
		this.damageFlags = damageFlags;
		this.type = type;
	}
	
	public Damage(ArrayList<SkillFlags> skillFlags, ArrayList<DamageFlags> damageFlags, Type type)
	{
		this.skillFlags = skillFlags;
		this.damageFlags = damageFlags;
		this.type = type;
	}
	
	public Damage(Damage damage)
	{
		this.skillFlags = damage.skillFlags;
		this.damageFlags = damage.damageFlags;
		this.type = damage.type;
	}
}
