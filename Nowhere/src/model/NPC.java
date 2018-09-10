package model;

import view.TextManager;
import view.TextStyle;

public class NPC extends Character
{
	protected Faction faction;
	protected int hostility;
	//TODO: Fix magic skill number.
	//TODO: This does not make sense to put here.
	protected Skill[] knownSkills = new Skill[8];
	
	public NPC(String name, int characterID)
	{
		super(name, characterID);
		hostility = 0;
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
