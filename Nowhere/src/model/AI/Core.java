package model.AI;

import java.util.ArrayList;
import java.util.Map;

import model.Character;
import model.NPC;
import model.Skill;
import model.SkillFlags;
import model.Skills.*;

public class Core
{
	public static Map.Entry<Skill, ArrayList<Object>> decideAction(NPC npc)
	{
		
		
		ArrayList<Object> target = new ArrayList<Object>();
		target.add("self");
		return Map.entry(new IceNine(), target);
	}
	
	public static void score(NPC npc)
	{
		
	}
}
