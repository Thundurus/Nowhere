package model.Skills;

import java.util.ArrayList;

public interface Attack
{
	public model.DamageArray baseDamage(model.Character user);
	public ArrayList<model.DamageFlags> damageFlags();
}
