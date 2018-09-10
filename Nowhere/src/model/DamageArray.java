package model;

import java.util.HashMap;
import java.util.Map;

public class DamageArray
{
	private HashMap<Damage, Double> damage = new HashMap<Damage, Double>();;
	
	public DamageArray()
	{
	}
	
	public DamageArray(Damage type, double dmg)
	{
		addDamage(type, dmg);
	}
	
	public DamageArray(DamageArray damageArray)
	{
		for(Map.Entry<Damage, Double> i : damageArray.damage.entrySet())
		{
			this.damage.put(i.getKey(), i.getValue());
		}
	}
	
	public double addDamage(Damage type, double dmg)
	{
		damage.put(type, dmg);
		return dmg;
	}
	
	/*
	/* Old, array-based method.
	 * So basically, never use extended damage types without an extended Damage object.
	public void addDamage(String type, double dmg)
	{
		switch(type)
		{
			case "physical": damage[0] += dmg;
			break;
			case "magical": damage[1] += dmg;
			break;
			case "psychic": damage[2] += dmg;
			break;
			case "pWill": damage[3] += dmg;
			break;
			case "vWill": damage[4] += dmg;
			break;
			case "mWill": damage[5] += dmg;
			break;
			case "psyWill": damage[6] += dmg;
			break;
			case "elec": damage[7] += dmg;
			break;
			case "mElec": damage[8] += dmg;
			break;
			case "fire": damage[9] += dmg;
			break;
			case "mFire": damage[10] += dmg;
			break;
			case "cold": damage[11] += dmg;
			break;
			case "mCold": damage[12] += dmg;
			break;
			case "truePhysical": damage[13] += dmg;
			break;
			case "trueWill": damage[14] += dmg;
		}
	}
	
	public double get(String type)
	{
		switch(type)
		{
			case "physical": return damage[0];
			
			case "magical": return damage[1];
			
			case "psychic": return damage[2];
			
			case "pWill": return damage[3];
			
			case "vWill": return damage[4];
			
			case "mWill": return damage[5];
			
			case "psyWill": return damage[6];
			
			case "elec": return damage[7];
			
			case "mElec": return damage[8];
			
			case "fire": return damage[9];
			
			case "mFire": return damage[10];
			
			case "cold": return damage[11];
			
			case "mCold": return damage[12];
			
			case "truePhysical": return damage[13];
			
			case "trueWill": return damage[14];
			
			default: return 0;
		}
	}
	*/
	
	public double get(Damage type)
	{
		return damage.get(type);
	}
	
	public HashMap<Damage, Double> getDamage()
	{
		return damage;
	}
}
