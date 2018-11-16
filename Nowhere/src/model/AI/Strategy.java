package model.AI;

public class Strategy
{
	public String type;
	public double attack;
	public double buff;
	public double counter;
	public double defend;
	public double restore;
	public double status;
	public double transform;
	
	public Strategy(String type)
	{
		this.type = type;
		attack = 1.0;
		buff = 1.0;
		counter = 1.0;
		defend = 1.0;
		restore = 1.0;
		status = 1.0;
		transform = 1.0;
	}
	
	public Strategy(StrategyArchetype strat)
	{
		if(strat == StrategyArchetype.OFFENSIVE)
		{
			type = "Offensive";
			attack = 1.5;
			buff = 1.5;
			counter = 1.0;
			defend = 0.8;
			restore = 1.0;
			status = 0.8;
			transform = 1.5;
		}
		if(strat == StrategyArchetype.BALANCED)
		{
			type = "Balanced";
			attack = 1.0;
			buff = 1.0;
			counter = 1.0;
			defend = 1.0;
			restore = 1.0;
			status = 1.0;
			transform = 1.0;
		}
		if(strat == StrategyArchetype.PUNISHER)
		{
			type = "Punisher";
			attack = 1.0;
			buff = 1.0;
			counter = 2.0;
			defend = 1.0;
			restore = 1.0;
			status = 0.8;
			transform = 1.5;
		}
		if(strat == StrategyArchetype.STALL)
		{
			type = "Stall";
			attack = 0.8;
			buff = 1.0;
			counter = 0.8;
			defend = 1.0;
			restore = 1.5;
			status = 1.5;
			transform = 1.0;
		}
	}
	
	public void bias(ActionScore score)
	{
		score.attack *= attack;
		score.buff *= buff;
		score.counter *= counter;
		score.defend *= defend;
		score.restore *= restore;
		score.status *= status;
		score.transform *= transform;
	}
}
