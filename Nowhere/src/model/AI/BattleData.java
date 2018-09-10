package model.AI;

import java.util.ArrayList;
import java.util.HashSet;

import model.Character;
import model.Skill;
import model.Skills.*;
import util.TurnData;

public class BattleData
{
	public String instance;
	public ArrayList<Character> fighters = new ArrayList<Character>();
	public HashSet<Skill> usedSkills = new HashSet<Skill>();
	public ArrayList<ArrayList<TurnData>> turns = new ArrayList<ArrayList<TurnData>>(); 
	public int turnIndex = 0;
	
	public BattleData(String instance)
	{
		this.instance = instance;
		turns.add(new ArrayList<TurnData>());
	}
	
	//So that we are not later confused: This causes the addAction method to become focused on the next ArrayList<TurnData>. It is not related to the individual actions that characterize a turn. addAction, however, is. These methods have an egregious naming convention (closer to a naming ritual, really).
	public void nextTurn()
	{
		turnIndex++;
		turns.add(new ArrayList<TurnData>());		
	}
	public void addAction(TurnData action)
	{
		turns.get(turnIndex).add(action);
	}
	
	
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(instance == ((BattleData) obj).instance)
			return true;
		return false;
	}
	//Completely secure and cromulent hashing algorithm #236!
	@Override
	public int hashCode()
	{
		int i = 0, j = 0;
		for(char c : instance.toCharArray())
		{
			j += (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 1.001);
			i++;
		}
		return j;
	}
	@Override
	public String toString()
	{
		StringBuilder battle = new StringBuilder();
		for(int i = 0; i < turns.size(); i++)
		{
			battle.append("Turn " + (i + 1) + ":");
			for(int j = 0; j < turns.get(i).size(); j++)
			{
				battle.append("\n" + turns.get(i).get(j).getData());
			}
			battle.append("\n\n");
		}
		return battle.toString();
	}
}
