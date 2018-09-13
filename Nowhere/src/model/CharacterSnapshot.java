package model;

import java.util.ArrayList;

public class CharacterSnapshot
{
	public String name;
	//TODO: Fix magic number for the skill array size.
	public Skill[] skills = new Skill[8];
	public ArrayList<Effect> effects = new ArrayList<Effect>();
	public ArrayList<StatusEffect> statusEffects = new ArrayList<StatusEffect>();
	public ArrayList<Type> type = new ArrayList<Type>();
	//It is not yet decided whether only the totals are needed or if the active modifications need to be available.
	public int[] currentStats = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //[24]
	
	public int id, maxHP, maxMP, maxSP, level, xp;
	public double hp, mp, sp;
	
	public CharacterSnapshot(Character character)
	{
		this.id = character.id;
		this.name = new String(character.getName());
		this.effects = character.getEffects();
		this.statusEffects = character.getStatusEffects();
		
		this.maxHP = character.currentForm.maxHP;
		this.maxMP = character.currentForm.maxMP;
		this.maxSP = character.currentForm.maxSP;
		this.level = character.currentForm.level;
		this.xp = character.currentForm.xp;
		this.hp = character.currentForm.hp;
		this.mp = character.currentForm.mp;
		this.sp = character.currentForm.sp;
		
		currentStats[0] = character.getStat("eStrength").intValue();
		currentStats[1] = character.getStat("eArcane").intValue();
		currentStats[2] = character.getStat("eIntelligence").intValue();
		currentStats[3] = character.getStat("eEndurance").intValue();
		currentStats[4] = character.getStat("eWillpower").intValue();
		currentStats[5] = character.getStat("eResilience").intValue();
		currentStats[6] = character.getStat("eSpeed").intValue();
		currentStats[7] = character.getStat("eSkill").intValue();
		currentStats[8] = character.getStat("eAura").intValue();
		
		currentStats[10] = character.getStat("eBaseDamage").intValue();
		currentStats[11] = character.getStat("ePhysicalAttack").intValue();
		currentStats[12] = character.getStat("eMagicAttack").intValue();
		currentStats[13] = character.getStat("eAccuracy").intValue();
		currentStats[14] = character.getStat("eCriticalChance").intValue();
		currentStats[15] = character.getStat("eCriticalDamage").intValue();
		currentStats[16] = character.getStat("eEvasion").intValue();
		currentStats[17] = character.getStat("eMagicDefense").intValue();
		currentStats[18] = character.getStat("ePhysicalDefense").intValue();
		currentStats[19] = character.getStat("eWeaponHandling").intValue();
	}
	
	public CharacterSnapshot(Character character, Skill[] skills)
	{
		this.id = character.id;
		this.name = new String(character.getName());
		this.effects = character.getEffects();
		this.statusEffects = character.getStatusEffects();
		
		this.maxHP = character.currentForm.maxHP;
		this.maxMP = character.currentForm.maxMP;
		this.maxSP = character.currentForm.maxSP;
		this.level = character.currentForm.level;
		this.xp = character.currentForm.xp;
		this.hp = character.currentForm.hp;
		this.mp = character.currentForm.mp;
		this.sp = character.currentForm.sp;
		
		currentStats[0] = character.getStat("eStrength").intValue();
		currentStats[1] = character.getStat("eArcane").intValue();
		currentStats[2] = character.getStat("eIntelligence").intValue();
		currentStats[3] = character.getStat("eEndurance").intValue();
		currentStats[4] = character.getStat("eWillpower").intValue();
		currentStats[5] = character.getStat("eResilience").intValue();
		currentStats[6] = character.getStat("epeed").intValue();
		currentStats[7] = character.getStat("eSkill").intValue();
		currentStats[8] = character.getStat("eAura").intValue();
		
		currentStats[10] = character.getStat("eBaseDamage").intValue();
		currentStats[11] = character.getStat("ePhysicalAttack").intValue();
		currentStats[12] = character.getStat("eMagicAttack").intValue();
		currentStats[13] = character.getStat("eAccuracy").intValue();
		currentStats[14] = character.getStat("eCriticalChance").intValue();
		currentStats[15] = character.getStat("eCriticalDamage").intValue();
		currentStats[16] = character.getStat("eEvasion").intValue();
		currentStats[17] = character.getStat("eMagicDefense").intValue();
		currentStats[18] = character.getStat("ePhysicalDefense").intValue();
		currentStats[19] = character.getStat("eWeaponHandling").intValue();
		
		for(int i = 0; i < skills.length; i++)
		{
			this.skills[i] = skills[i];
		}
	}
	
	public String getData()
	{
		return this.toString();
	}
	
	//TODO: Override toString().
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((model.CharacterSnapshot) obj).name)
			return true;
		return false;
	}
	
	//Completely secure and cromulent hashing algorithm #497!
	//Let's hope that names never get too long.
	//Or that character IDs ever get too large.
	@Override
	public int hashCode()
	{
		int i = 0, j = 0;
		for(char c : name.toCharArray())
		{
			j += (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 2);
			j += id;
			i++;
		}
		return j;
	}
}
