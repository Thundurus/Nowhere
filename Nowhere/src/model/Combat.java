package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import view.TextStyle;
import view.TextManager;
import model.Skills.*;

import java.util.ArrayDeque;

public class Combat
{
	private int id;
	private boolean isActive;
	public static final int MAX_SIZE = 6; //We'll figure out this number later
	private ArrayList<Character> players = new ArrayList<Character>();
	private ArrayDeque<Character> intruders = new ArrayDeque<Character>();
	private HashMap<Character, ArrayList<Object>> actions = new HashMap<>();
	private Field field;
	private ArrayList<FieldEffect> effects = new ArrayList<FieldEffect>();
	private String instance;
	
	
	Combat(int id)
	{
		this.id = id;
		isActive = false;
	}
	
	Combat(int id, String instance)
	{
		this.id = id;
		isActive = false;
		this.instance = instance;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getInstance()
	{
		return instance;
	}
	
	public boolean setInstance(String instance)
	{
		boolean alreadySet = false;
		if(instance != null)
		{
			alreadySet = true;
		}
		this.instance = instance;
		return alreadySet;
	}
	
	public boolean isFull()
	{
		return ((players.size() + intruders.size()) >= (MAX_SIZE));
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	/**
	 * Adds a group of characters to this {@link Combat} instance while setting their states to reflect this. This method does <strong>not</strong> initialize the {@link BattleData} instance or clear existing characters from this {@link Combat} instance. 
	 * 
	 * @param fighters {@link Character}s to insert
	 * @param sides the alignment of <tt>fighters</tt>. Elements in the array must correspond to the {@link Character} in <tt>fighters</tt> with the same index. 
	 * @see #clear() clear
	 * @see #setInstance(String) setInstance
	 */
	public void startCombat(Character[] fighters, int[] sides)
	{
		TextManager.appendText(("Entering combat. "), TextStyle.DEBUG);
		isActive = true;
		add(fighters, sides);
		for(int i = 0; i < fighters.length; i++)
		{
			Master.displayStats(fighters[i]);
			TextManager.appendText(("Character \"" + fighters[i].getName() + "\" has joined the fight."), TextStyle.DEBUG);
		}
	}
	
	public void add(Character[] fighters, int[] sides)
	{
		if(!isFull() && ((fighters.length + players.size() + intruders.size()) <= MAX_SIZE))
		{
			for (int i = 0; i < fighters.length; i++)
			{
				players.add(fighters[i]);
				players.get(players.size() - 1).setFightingStatus(true, true, players.size(), id, sides[i]);
			}
		}
	}
	
	public void add(Character fighter, int side)
	{
		if(!isFull())
		{
			fighter.setFightingStatus(true, true, players.size() + 1, id, side);
			players.add(fighter);
		}
	}
	
	public void addIntruder(Character intruder, int side)
	{
		intruders.push(intruder);
		intruders.peek().setFightingStatus(true, true, 0, id, side);
	}
	
	public ArrayList<Character> getFighters()
	{
		return players;
	}
	
	public void remove(Character fighter)
	{
		fighter.setFightingStatus(false, true, 0, 0, 0);
		fighter.statValidator();
		players.remove(fighter);
		
		for (int i = 0; i < players.size();i++)
		{
			players.get(i).setFightingStatus(-1, -1, i, -1, -1);
		}
	}
	
	public ArrayList<Character> getCharacters()
	{
		return players;
	}
	
	
	/**
	 * Sets a {@link Character} to an inactive state without removing them from this {@link Combat} instance, generally as a result of being defeated.
	 * 
	 * @param fighter {@link Character} to set inactive.
	 */
	public void down(Character fighter)
	{
		fighter.setFightingStatus(-1, 0, -1, -1, -1);
	}
	
	/**
	 * Removes all {@link Character} instances (including those who were in the process of joining) from this {@link Combat} instance and updates their states to reflect that.
	 * <p>It currently also hides the display of their status windows.   
	 */
	public void clear()
	{
		TextManager.appendText(("Resetting combat instance #" + id + "."), TextStyle.DEBUG);
		int size = players.size();
		for (int i = 0; i < size; i++)
		{
			//The last section of the Combat.remove() method makes it undesirable to reuse here.
			players.get(i).setFightingStatus(false, false, 0, 0, 0);
			//TODO: Decide what should happen to the displays after combat.
			Master.hideStatDisplay(players.get(i));
		}
		players.clear();
		
		while(!intruders.isEmpty())
		{
			intruders.pop().setFightingStatus(false, false, 0, 0, 0);
		}
		actions.clear();
		Master.resetButtons();
		Master.printBattleData(instance);
		instance = null;
		isActive = false;
	}
	
	/**
	 * Sets the action to be executed by a character when <code>processActions()</code> is next run. The <tt>targets</tt> parameter
	 *  
	 * @param fighter	{@link Character} to perform the action
	 * @param skill		{@link Skill} to be used on the targets
	 * @param targets	Contains either the Character(s) that are targeted by the action, or a simplified String (e.g. "self", "enemies", "all").
	 */
	public void chooseAction(Character fighter, Skill skill, ArrayList<Object> targets)
	{
		//Not sure if we should hijack their choice here, or restrict their options just before they act.
		//The latter approach allows for players that become recapacitated before their turn to use their turn, and will be the preferred approach as of now.
//		if(fighter.is(new Incapacitated()))
//		{
//			ArrayList<Object> combined = new ArrayList<Object>(List.of(new Nothing(), "self"));
//			actions.put(fighter, combined);
//			fighter.setQueuedSkill(new Nothing());
//			return;
//		}
		ArrayList<Object> combined = new ArrayList<Object>();
		combined.add(skill);
		combined.addAll(targets);
		if(players.contains(fighter))
		{
			actions.put(fighter, combined);
			fighter.setQueuedSkill(skill);
			TextManager.appendText(("Player \"" + fighter.name + "\" will use skill \"" + skill.pName + "\" on " + targets.get(0) + "."), TextStyle.DEBUG);
		}
	}

	/**
	 * Calls the respective <code>decrementEffects()</code> on all {@link Character} currently in battle and decrements the duration of any active {@link FieldEffect}.
	 * 
	 * <p>This is expected to only be used in processActions, but a player or NPC-initiated event that causes this is conceivable.
	 */
	public void decrementEffects()
	{
		players.forEach(e -> e.decrementEffects());
		effects.forEach(e -> e.duration--);
		ArrayList<FieldEffect> copy = new ArrayList<FieldEffect>(effects);
		for(FieldEffect e : copy)
		{
			if(e.duration <= 0)
			{
				effects.remove(e);
			}
		}
	}
	public void addFieldEffect(FieldEffect fe)
	{
		effects.add(fe);
	}
	/**
	 * Executes the {@link Character#queuedSkill} for all active {@link Character} instances in this component and ends the battle if applicable.
	 * 
	 * A list is populated with the active characters in this Combat, sorted by speed and priority. 
	 * <p>The fastest character <tt>X</tt> who has not already acted makes their move.
	 * <p>If <tt>X</tt>'s move has a special interaction with someone else's (Y) move, both moves execute simultaneously. <tt>Y</tt>'s turn is removed from the list.
	 * <p><tt>X</tt>'s turn is removed from the list.
	 * Characters who are knocked unconscious as a result of the previous turns have their turns removed from the list.
	 * This repeats until list is empty.
	 * Pending status effects that activate at the end of the turn are resolved (e.g. poison damage).
	 * Pending intruders are added to the battle.
	 * 
	 * @see #decrementEffects()
	 */	
	public void processActions()
	{
		ArrayList<Character> conscious = new ArrayList<Character>(players.stream().filter(c -> !c.isIncapacitated()).collect(Collectors.toList()));
		List<Character> order = conscious;
		//Priority will separate the users into tiers which the speed stat cannot surpass, assuming that speed cannot reach such values.
		Collections.sort(order, Comparator.comparing(c -> (c.getStat("eSpeed").intValue() + (c.getQueuedSkill().getPriority() * 10000))));	
		Collections.reverse(order);
		
		//TODO: Determine and handle move interactions.
		while(!order.isEmpty())
		{
			//Check again for anyone who has become incapacitated during this turn.
			if(order.get(0).isIncapacitated())
			{
				//We could do this a bit differently.
				order.remove(0);
				continue;
			}
			ArrayList<Character> targets = new ArrayList<Character>();
		
			//The first "Character" object can actually be a String defining who the targets are.
			if(actions.get(order.get(0)).get(1).getClass() == String.class)
			{
				if(((String) actions.get(order.get(0)).get(1)).equalsIgnoreCase("self"))
				{
					targets.add(order.get(0));
				}
				else if(((String) actions.get(order.get(0)).get(1)).equalsIgnoreCase("enemies"))
				{
					targets.addAll(conscious.stream().filter(c -> c.getSide() != order.get(0).getSide()).collect(Collectors.toList()));
				}
				else if(((String) actions.get(order.get(0)).get(1)).equalsIgnoreCase("allies"))
				{
					targets.addAll(conscious.stream().filter(c -> c.getSide() == order.get(0).getSide()).collect(Collectors.toList()));
				}
				else if(((String) actions.get(order.get(0)).get(1)).equalsIgnoreCase("all"))
				{
					targets.addAll(conscious);
				}
			}
			
			//If not, just add the characters.
			else
			{
				for(int i = 1; i < actions.get(order.get(0)).size(); i++)
				{
					targets.add((Character) actions.get(order.get(0)).get(i));
				}
			}
			ActParser.parse(order.get(0), (Skill) actions.get(order.get(0)).get(0), targets);
			actions.remove(order.get(0));
			order.remove(0);
		}
		
		//Combat ends if only one fighter remains, or if all remaining fighters are allies.
		//The logic will require amending if it becomes possible for all fighters to be simultaneously knocked out.
		
		ArrayList<Character> stillConscious = new ArrayList<Character>(players.stream().filter(c -> c.getFightingStatus()[1] == 1).collect(Collectors.toList()));
		
		if (stillConscious.size() == 1 || stillConscious.stream().allMatch(c -> c.getSide() == stillConscious.get(0).getSide()))
		{
			players.forEach(c -> 
			{
				Master.addStatus(getInstance(), new CharacterSnapshot(c));
			});
			
			if(stillConscious.size() == 1)
				TextManager.appendText(("Only one fighter is still conscious."), TextStyle.DEBUG);
			if(stillConscious.stream().allMatch(c -> c.getSide() == stillConscious.get(0).getSide()))
				TextManager.appendText(("All remaining fighters are allied."), TextStyle.DEBUG);
			//TODO: Victory, defeat
			clear();
			TextManager.appendText(("Battle ending."), TextStyle.DEBUG);
			//TODO: Set player keys
			//What are player keys?
			return;
		}
		
		//TODO: Existing effects on characters that trigger at the end of a turn
		//Field effects do whatever it is that they do.
		players.forEach(i ->
		{
			i.getEffects().forEach(j -> j.applyEffect());
			effects.forEach(j -> j.applyEffect(i));
		});
		
		decrementEffects();
		
		//TODO: Edit the structure to allow for arbitrary delays in the number of turns it takes for intruders to join.
		//Add intruders at the end of the current turn.
		while(!intruders.isEmpty())
		{
			int i = intruders.peek().getSide();
			add(intruders.pop(), i);
		}
		
		//Reset action map and add intruders to it.
		for(Character fighter : players)
		{
			//Extremely rudimentary method for determining consciousness.
			if(fighter.getStat("hp").doubleValue() <= 0)
			{
				fighter.setFightingStatus(-1, 0, -1, -1, -1);
			}
			else
			{
				chooseAction(fighter, new Wait(), new ArrayList<Object>(Arrays.asList(fighter)));
			}
			Master.addStatus(getInstance(), new CharacterSnapshot(fighter));
		}
		
		Master.nextTurn(instance);
	}
}
