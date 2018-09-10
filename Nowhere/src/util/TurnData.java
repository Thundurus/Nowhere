package util;

import java.util.ArrayList;

import model.Character;
import model.Skill;
import model.SkillFlags;

public class TurnData
{
	public Character user;
	public ArrayList<Character> targets = new ArrayList<Character>();
	public Skill action;
	public double damage;
	public double[] damageArray;
	
	//Stats
	public ArrayList<Double> targetHealth = new ArrayList<Double>();
	
	public TurnData(Character user, ArrayList<Character> targets, Skill action)
	{
		this.user = user;
		this.targets = targets;
		this.action = action;
		targets.stream().forEach(c -> 
		{
			targetHealth.add(c.getStat("hp").doubleValue());
		});
	}
	
	public TurnData(Character user, ArrayList<Character> targets, Skill action, double damage)
	{
		this.user = user;
		this.targets = targets;
		this.action = action;
		this.damage = damage;
		targets.stream().forEach(c -> 
		{
			targetHealth.add(c.getStat("hp").doubleValue());
		});
	}
	
	public TurnData(Character user, ArrayList<Character> targets, Skill action, double[] damageArray)
	{
		this.user = user;
		this.targets = targets;
		this.action = action;
		this.damageArray = damageArray;
		targets.stream().forEach(c -> 
		{
			targetHealth.add(c.getStat("hp").doubleValue());
		});
	}
	
	public String getData()
	{
		return this.toString();
	}
	
	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder(user.getName() + " used skill " + action.getpName() + " on ");
		if(targets.size() == 1)
		{
			result.append("target " + targets.get(0).getName());
			if(action.getFlags().contains(SkillFlags.ATTACK))
			{
				if(Double.valueOf(damage) != null)
				{
					if(damage != 0)
						result.append(", dealing " + damage + " damage, reducing their HP to " + targetHealth.get(0));
					else
						result.append(", dealing no damage");
				}
			}
			result.append(".");
			return result.toString();
		}
		//Currently unused.
		else
		{
			result.append("targets.");
			if(damageArray != null)
			{
				for(int i = 0; i < targets.size(); i++)
				{
					result.append("\n" + targets.get(i).getName() + " was dealt " + damageArray[i] + " damage, reducing their HP to " + targets.get(i).getStat("hp") + ".");
				}
			}
			return result.toString();
		}
		
	}
}
