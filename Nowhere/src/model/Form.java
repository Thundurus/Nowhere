package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.Skills.BasicAttack;
import model.Skills.Wait;

public class Form
{
	protected Character character;
	protected String name;
	protected ArrayList<String> perks = new ArrayList<String>();
	protected ArrayList<Skill> skills = new ArrayList<Skill>();
	protected ArrayList<Effect> effects = new ArrayList<Effect>();
	protected ArrayList<StatusEffect> statusEffects = new ArrayList<StatusEffect>();
	protected ArrayList<CounterState> counterState = new ArrayList<CounterState>();
	/**
	 * Multipliers add their total value to the number one before calculation.
	 * <p>I.e. if the key "strength" is in the map with the value 0.32, then effective strength will be multiplied by 1.32. They apply multiplicatively, and may be negative.
	 */
	protected HashMap<String, Double> multipliers = new HashMap<String, Double>();
	protected ArrayList<Type> type = new ArrayList<Type>();
	protected int[] baseStats = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //[24]
	protected int[] tempStats = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //[24]
	
	protected int maxHP, maxMP, maxSP, level, xp, index;
	protected double hp, mp, sp;
	
	public Form(String name, Character character, int index)
	{
		this.name = name;
		this.character = character;
		this.index = index;
		level = 1;
		xp = 0;
		baseStats[0] = 3;
		baseStats[1] = 3;
		baseStats[2] = 3;
		baseStats[3] = 3;
		baseStats[4] = 3;
		baseStats[5] = 3;
		baseStats[6] = 3;
		baseStats[7] = 3;
		baseStats[8] = 3;
		baseStats[9] = 3;
		baseStats[10] = 5;
		type.add(Type.NORMAL);
		skills.add(new BasicAttack());
		skills.add(new Wait());
	}
	
	public void calculateMaxHP()
	{
		int temp = 40;
		temp += level * 5;
		temp += ((getStat("eResilience").intValue() + getStat("eEndurance").intValue() + getStat("eWillpower").intValue()) * 2);
		for (int i = 0; i < character.equipped.length; i++)
		{
			temp += character.equipped[i].healthMod;
		}
		maxHP = temp;
	}
	public void calculateMaxMP()
	{
		int temp = 10;
		temp += level * 5;
		temp += ((getStat("eWillpower").intValue()) * 5);
		for (int i = 0; i < character.equipped.length;i++)
		{
			temp += character.equipped[i].manaMod;
		}
		maxMP = temp;
	}
	public void calculateMaxSP()
	{
		int temp = 40;
		temp += level * 5;
		temp += ((getStat("eEndurance").intValue()) * 5);
		for (int i = 0; i < character.equipped.length;i++)
		{
			temp += character.equipped[i].staminaMod;
		}
		maxSP = temp;
	}
	
	public String getName()
	{
		final String copy = new String(name);
		return copy;
	}
	
	/**
	 * Returns a {@link Number} containing the value for the specified statistic. Statistics are enumerated in camel-case (e.g. <tt>"maxHP"</tt>, <tt>"physicalAttack"</tt>).
	 * <p>Prepending the character <tt>'e'</tt> to an existing statistic (e.g. <tt>"eStrength"</tt>, <tt>"ePhysicalAttack"</tt>) will return the value after modification by effects active on the {@link Character} instead of the base value. Not all statistics support this kind of modification.
	 * 
	 * @param stat {@link String} representation of a {@link Character} stat
	 * @return {@link Number} representing the specified stat
	 * @throws IllegalArgumentException when a String that does not match any of the stats is passed
	 */
	public Number getStat (String stat)
	{
		double multiplier = 1;
		switch(stat)
		{
			case "maxHP": return maxHP;
			
			case "maxMP": return maxMP;
			
			case "maxSP": return maxSP;
			
			case "hp": return hp;
				
			case "mp": return mp;
				
			case "sp": return sp;
				
			case "xp": return xp;
				
			case "strength": return baseStats[0];
			
			case "eStrength":
				multiplier = 1;
				if(multipliers.containsKey("strength"))
					multiplier += multipliers.get("strength");
				return (baseStats[0] + tempStats[0]) * multiplier;
			
			case "arcane": return baseStats[1];
			
			case "eArcane":
				multiplier = 1;
				if(multipliers.containsKey("arcane"))
					multiplier += multipliers.get("arcane");
				return (baseStats[1] + tempStats[1]) * multiplier;
			
			case "intelligence": return baseStats[2];
			
			case "eIntelligence":
				multiplier = 1;
				if(multipliers.containsKey("intelligence"))
					multiplier += multipliers.get("intelligence");
				return (baseStats[2] + tempStats[2]) * multiplier;
			
			case "endurance": return baseStats[3];
			
			case "eEndurance":
				multiplier = 1;
				if(multipliers.containsKey("endurance"))
					multiplier += multipliers.get("encurance");
				return (baseStats[3] + tempStats[3]) * multiplier;
			
			case "willpower": return baseStats[4];
			
			case "eWillpower":
				multiplier = 1;
				if(multipliers.containsKey("willpower"))
					multiplier += multipliers.get("willpower");
				return (baseStats[4] + tempStats[4]) * multiplier;
			
			case "resilience": return baseStats[5];
			
			case "eResilience":
				multiplier = 1;
				if(multipliers.containsKey("resilience"))
					multiplier += multipliers.get("resilience");
				return (baseStats[5] + tempStats[5]) * multiplier;
			
			case "speed": return baseStats[6];
			
			case "eSpeed":
				multiplier = 1;
				if(multipliers.containsKey("speed"))
					multiplier += multipliers.get("speed");
				return (baseStats[6] + tempStats[6]) * multiplier;
			
			case "skill": return baseStats[7];
			
			case "eSkill":
				multiplier = 1;
				if(multipliers.containsKey("skill"))
					multiplier += multipliers.get("skill");
				return (baseStats[7] + tempStats[7]) * multiplier;
			
			case "aura": return baseStats[8];
			
			case "eAura":
				multiplier = 1;
				if(multipliers.containsKey("aura"))
					multiplier += multipliers.get("aura");
				return (baseStats[8] + tempStats[8]) * multiplier;
			
			//Other stat
			
			case "baseDamage": return baseStats[10];
				
			case "eBaseDamage":
				multiplier = 1;
				if(multipliers.containsKey("baseDamage"))
					multiplier += multipliers.get("baseDamage");
				return (baseStats[10] + tempStats[10]) * multiplier;
				
			case "physicalAttack": return baseStats[11];
				
			case "ePhysicalAttack":
				multiplier = 1;
				if(multipliers.containsKey("physicalAttack"))
					multiplier += multipliers.get("physicalAttack");
				return (baseStats[11] + tempStats[11]) * multiplier;
				
			case "magicAttack": return baseStats[12];
				
			case "eMagicAttack":
				multiplier = 1;
				if(multipliers.containsKey("magicAttack"))
					multiplier += multipliers.get("magicAttack");
				return (baseStats[12] + tempStats[12]) * multiplier;

			case "accuracy": return baseStats[13];
				
			case "eAccuracy":
				multiplier = 1;
				if(multipliers.containsKey("accuracy"))
					multiplier += multipliers.get("accuracy");
				return (baseStats[13] + tempStats[13]) * multiplier;
				
			case "criticalChance": return baseStats[14];
				
			case "eCriticalChance":
				multiplier = 1;
				if(multipliers.containsKey("criticalChance"))
					multiplier += multipliers.get("criticalChance");
				return (baseStats[14] + tempStats[14]) * multiplier;
				
			case "criticalDamage": return baseStats[15];
				
			case "eCriticalDamage":
				multiplier = 1;
				if(multipliers.containsKey("criticalDamage"))
					multiplier += multipliers.get("criticalDamage");
				return (baseStats[15] + tempStats[15]) * multiplier;
				
			case "evasion": return baseStats[16];
				
			case "eEvasion":
				multiplier = 1;
				if(multipliers.containsKey("evasion"))
					multiplier += multipliers.get("evasion");
				return (baseStats[16] + tempStats[16]) * multiplier;
				
			case "magicDefense": return baseStats[17];
				
			case "eMagicDefense":
				multiplier = 1;
				if(multipliers.containsKey("magicDefense"))
					multiplier += multipliers.get("magicDefense");
				return (baseStats[17] + tempStats[17]) * multiplier;
				
			case "physicalDefense": return baseStats[18];
				
			case "ePhysicalDefense":
				multiplier = 1;
				if(multipliers.containsKey("physicalDefense"))
					multiplier += multipliers.get("physicalDefense");
				return (baseStats[18] + tempStats[18]) * multiplier;
				
			case "weaponHandling": return baseStats[19];
				
			case "eWeaponHandling":
				multiplier = 1;
				if(multipliers.containsKey("weaponHandling"))
					multiplier += multipliers.get("weaponHandling");
				return (baseStats[19] + tempStats[19]) * multiplier;
				
			default:
				throw new IllegalArgumentException("The stat \"" + stat + "\" does not exist.");
		}
	}
	
	//No custom equals/hashcode methods. A character can have two forms that are the same in every way.
}
