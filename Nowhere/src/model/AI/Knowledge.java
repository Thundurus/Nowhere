package model.AI;

import java.util.HashMap;

import model.Character;
import model.Skill;
import model.Type;
import util.IntRange;

public class Knowledge
{
	public Character subject;
	//TODO: Initialize maps with known limits
	public HashMap<Skill, Integer> skills = new HashMap<Skill, Integer>();
	public HashMap<Type, Integer> types = new HashMap<Type, Integer>();
	public HashMap<String, IntRange> stats = new HashMap<String, IntRange>();
	public HashMap<String, Integer> perks = new HashMap<String, Integer>();
	
	public HashMap<Type, String> effectiveness = new HashMap<Type, String>(); 
	
	public Knowledge(Character subject)
	{
		this.subject = subject;
		stats.put("strength", new IntRange("Strength"));
		stats.put("arcane", new IntRange("Arcane"));
		stats.put("intelligence", new IntRange("Intelligence"));
		stats.put("endurance", new IntRange("Endurance"));
		stats.put("resilience", new IntRange("Resilience"));
		stats.put("willpower", new IntRange("Willpower"));
		stats.put("speed", new IntRange("Speed"));
		stats.put("skill", new IntRange("Skill"));
		stats.put("aura", new IntRange("Aura"));
	}
	
	public void addSkill(Skill skill, int probability)
	{
		skills.put(skill.clone(), probability);
	}
	public void addType(Type type, int probability)
	{
		types.put(type, probability);
	}
	public void addStat (String stat, IntRange range)
	{
		stats.put(stat, range);
	}
	public void addPerk (String perk, int probability)
	{
		perks.put(perk, probability);
	}
	public void addEffectiveness(Type type, String effect)
	{
		if(!effect.equalsIgnoreCase("immune") && !effect.equalsIgnoreCase("ineffective") && !effect.equalsIgnoreCase("normal") && !effect.equalsIgnoreCase("supereffective"))
			effectiveness.put(type, effect);
		else
			throw new IllegalArgumentException("The only valid arguments for the \"effect\" parameter are \"immune\", \"ineffective\", \"normal\", or \"supereffective\".");
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(((Knowledge) obj).subject.getName().equals(this.subject.getName()))
			return true;
		return false;
	}
	//Completely secure and cromulent hashing algorithm #19.
	@Override
	public int hashCode()
	{
		int i = 0, j = 0;
		for(char c : subject.getName().toCharArray())
		{
			j -= (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 2);
			i++;
		}
		return j;
	}
}
