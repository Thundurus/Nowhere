package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class Effect
{
	public String name;
	public HashMap<String, Integer> effects; //Integer Stat modifiers
	public HashMap<String, Double> multipliers = new HashMap<String, Double>(); //Stat multipliers
	public ArrayList<StatusEffect> statusEffects = new ArrayList<StatusEffect>(); //Status Effects
	public double remainingTime;
	public Supplier<Void> effect;
	public boolean persistent = false;
	
	public Effect(String name, double remainingTime)
	{
		this.name = name;
		this.remainingTime = remainingTime;
	}
	
	public Effect(String name, HashMap<String, Integer> effects, double remainingTime)
	{
		this.name = name;
		this.effects = effects;
		this.remainingTime = remainingTime;
	}
	
	public Effect(String name, HashMap<String, Integer> effects, ArrayList<StatusEffect> statusEffects, double remainingTime)
	{
		this.name = name;
		this.effects = effects;
		this.remainingTime = remainingTime;
		this.statusEffects.addAll(statusEffects);
	}
	
	public void applyEffect()
	{
		if (effect != null)
			effect.get();
	}
	
	public void setEffect(Supplier<Void> effect)
	{
		this.effect = effect;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((Effect) obj).name)
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
