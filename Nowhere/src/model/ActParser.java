package model;

import java.util.List;

import view.TextManager;
import view.TextStyle;

public class ActParser
{
	public static void parse(Character chara, Skill action, List<Character> targets)
	{		
		switch(action.baseArchetype)
		{			
			case ATTACK: attack(chara, targets, action);
			break;
			
			case SPELL: spell(chara, targets, action);
			break;
			
			//TODO: Wait effects.
			case WAIT:	if(action.name.equalsIgnoreCase("Nothing"))
						{
							TextManager.appendText(("\n" + chara + " did nothing."), TextStyle.REGULAR);
						}
						else
						{
							TextManager.appendText(("\n" + chara + " waited."), TextStyle.REGULAR);
						}
			break;
			
			default:
			break;
		}
	}
	
	public static void attack(Character attacker, List<Character> target, Skill skill)
	{
		target.forEach(e -> e.react(attacker, skill));
	}
	
	public static void spell(Character attacker, List<Character> target, Skill skill)
	{
		target.forEach(e -> e.react(attacker, skill));
	}
}
