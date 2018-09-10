package model.AI;

import java.util.ArrayList;
import java.util.Map;

import model.Character;
import model.NPC;
import model.Skill;
import model.Skills.*;

public class Core
{
	public static Map.Entry<Skill, ArrayList<Object>> decideAction(NPC npc, ArrayList<Character> fighters)
	{
		
		
		ArrayList<Object> target = new ArrayList<Object>();
		target.add("self");
		return Map.entry(new Wait(), target);
	}
}
