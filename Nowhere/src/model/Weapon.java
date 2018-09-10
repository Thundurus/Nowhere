package model;

public class Weapon extends Item
{
	protected double physicalAttackModifier = 0, magicAttackModifier = 0;
	public String attackVerb = "";
	public String attackVerbs = "";
	
	@Override
	public void onEquip()
	{
	}
	@Override
	public void onRemove()
	{
	}

}
