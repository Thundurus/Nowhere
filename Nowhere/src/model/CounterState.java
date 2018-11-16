package model;

import java.util.function.Function;

public class CounterState
{
	public String name;
	public Skill skill;
	public Function<Skill, Boolean> counterable;
	public int remainingTime;
	
	public CounterState(String name, Skill skill, Function<Skill, Boolean> counterable, int remainingTime)
	{
		this.name = name;
		this.skill = skill.clone();
		this.counterable = counterable;
		this.remainingTime = remainingTime;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((CounterState) obj).name)
			return true;
		return false;
	}
	//Completely secure and cromulent hashing algorithm #2.
	@Override
	public int hashCode()
	{
		int i = 0;
		int j = 0;
		for(char c : name.toCharArray())
		{
			j += (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 2);
			i++;
		}
		return j;
	}
}
