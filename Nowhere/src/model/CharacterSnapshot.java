package model;

import java.util.ArrayList;

public class CharacterSnapshot
{
	public String name;
	//TODO: Fix magic skill number.
	public Skill[] skills = new Skill[8];
	public ArrayList<Effect> effects = new ArrayList<Effect>();
	public ArrayList<StatusEffect> statusEffects = new ArrayList<StatusEffect>();
	public ArrayList<Type> type = new ArrayList<Type>();
	public int[] currentStats = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //[24]
	
	public int id, maxHP, maxMP, maxSP, level, xp;
	public double hp, mp, sp;
	
	public CharacterSnapshot(Character character, Skill[] skills)
	{
		this.name = new String(character.getName());
		this.effects = character.getEffects();
		this.statusEffects = character.getStatusEffects();
	}
}
