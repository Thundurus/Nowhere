package model.AI;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import model.Character;
import model.CharacterSnapshot;
import model.Skill;
import model.Skills.*;
import util.TurnData;

public class BattleData
{
	public String instance;
	public ArrayList<Character> fighters = new ArrayList<Character>();
	public HashSet<Skill> usedSkills = new HashSet<Skill>();
	public ArrayDeque<ArrayList<TurnData>> turns = new ArrayDeque<ArrayList<TurnData>>();
	public ArrayDeque<ArrayList<CharacterSnapshot>> statuses = new ArrayDeque<ArrayList<CharacterSnapshot>>();
	
	public BattleData(String instance)
	{
		this.instance = instance;
		turns.push(new ArrayList<TurnData>());
		statuses.push(new ArrayList<CharacterSnapshot>());
	}
	
	public BattleData(BattleData battleData)
	{
		this.fighters.addAll(battleData.fighters);
		this.usedSkills.addAll(battleData.usedSkills);
		this.turns.addAll(battleData.turns);
		this.statuses.addAll(battleData.statuses);
	}
	
	//So that we are not later confused: This causes the addAction method to become focused on the next ArrayDeque<TurnData>. It is not related to the individual actions that characterize a turn. addAction, however, is. These methods have an egregious naming convention (closer to a naming ritual, really).
	public void nextTurn()
	{
		turns.push(new ArrayList<TurnData>());		
		statuses.push(new ArrayList<CharacterSnapshot>());
	}
	public void addAction(TurnData action)
	{
		turns.peek().add(action);
	}
	public void addStatus(CharacterSnapshot status)
	{
		statuses.peek().add(status);
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
		BattleData copy = new BattleData(this);
		StringBuilder battle = new StringBuilder();
		int i = 0;
		while(!copy.turns.isEmpty())
		{
			battle.append("Turn " + (i + 1) + ":");
			ArrayList<TurnData> turn = copy.turns.pollLast();
//			ArrayList<CharacterSnapshot> status = copy.statuses.pollLast();
			for(int j = 0; j < turn.size(); j++)
			{
				battle.append("\n" + turn.get(j).getData());
				//battle.append("\n" + status.get(j).getData());
			}
			battle.append("\n\n");
			i++;
		}
		return battle.toString();
	}
}
