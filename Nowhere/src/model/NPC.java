package model;

import java.util.HashMap;

import model.AI.Knowledge;
import model.AI.Strategy;
import view.TextManager;
import view.TextStyle;

public class NPC extends Character
{
	public Strategy strategy;
	protected HashMap<String, Knowledge> knowledge = new HashMap<String, Knowledge>();
	public Faction faction;	
	public int hostility;
	
	public NPC(String name, int characterID)
	{
		super(name, characterID);
		hostility = 0;
	}
	
	public Knowledge accessKnowledge(Character subject)
	{
		String characterName = subject.getName();
		if(!knowledge.containsKey(characterName))
		{
			knowledge.put(characterName, new Knowledge(subject));
		}
		return knowledge.get(characterName);
	}
	
	public void see(Character user, Skill skill)
	{
		if(user != this)
			accessKnowledge(user).addSkill(skill, 100);
	}
	
	
	
	@Override
	public boolean isNPC()
	{
		return true;
	}
	
	@Override
	public void debugInfo()
	{
		if(location != null)
		{
			TextManager.appendText(("NPC: " + name + " with ID #" + id + " is currently located in " + location.getLocation()), TextStyle.DEBUG);
		}
		else
		{
			TextManager.appendText(("NPC: " + name + " with ID #" + id + " is currently nowhere."), TextStyle.DEBUG);
		}
	}
	
	//TODO: A stopgap measure for testing, as hostility is neither a binary attribute nor a value which is equivalent when queried by different sources.
	public boolean isHostile()
	{
		return true;
	}
}
