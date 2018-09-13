package model;

import java.util.ArrayList;
import java.util.List;

import util.TurnData;
import view.TextManager;
import view.TextStyle;

public class ActParser
{
	public static void parse(Character chara, Skill action, ArrayList<Character> targets)
	{		
		switch(action.baseArchetype)
		{			
			case ATTACK: attack(chara, targets, action);
			break;
			
			case SPELL: spell(chara, targets, action);
			break;
			
			//TODO: Wait effects (e.g. any effects granted by waiting).
			case WAIT:	if(action.name.equalsIgnoreCase("Nothing"))
						{
							TextManager.appendText(("\n" + chara + " did nothing."), TextStyle.REGULAR);
						}
						else
						{
							TextManager.appendText(("\n" + chara + " waited."), TextStyle.REGULAR);
						}
						Master.addAction(Master.getCombatInstance(chara.getCombatInstance()).getInstance(), new TurnData(chara, targets, action));
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
