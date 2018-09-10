package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import controller.GUIHook;
import controller.SceneMapping;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.AI.BattleData;
import util.TurnData;
import view.GridManager;
import view.TextStyle;
import view.TextManager;

public class Master
{
	private static ArrayList<Combat> combatContainers = new ArrayList<Combat>();
	private static ArrayList<Character> characters = new ArrayList<Character>();
	private static ArrayList<NPC> npcs = new ArrayList<NPC>();
	private static ArrayList<ArrayList<Button>> buttons = new ArrayList<ArrayList<Button>>();
	private static ArrayList<ArrayList<Label>> labels = new ArrayList<ArrayList<Label>>();
	private static int index = 0;
	private static Node baseNode = new Node();
	private static Node[][] grid;
	private static Character playerCharacter;
	public static LinkedHashMap<String, BattleData> battleData = new LinkedHashMap<String, BattleData>();

	public static Character newPlayer(String name)
	{
		int i = characters.size();
		characters.add(new Character(name, i));
		characters.get(i).debugInfo();
		return characters.get(i);
	}
	public static void setPlayerCharacter(Character character)
	{
		playerCharacter = character;
	}
	public static Character getPlayerCharacter()
	{
		return playerCharacter;
	}
	public static NPC newNPC(String name) 
	{
		int i = npcs.size();
		npcs.add(new NPC(name, i));
		npcs.get(i).debugInfo();
		return npcs.get(i);
	}
	
	//NPCs will likely be both generated and disposed of at a much greater rate, so there must be some way to ensure that references to them do not change before the rest of the system is able to react.
	public static NPC getNPCByID(int characterID)
	{
		return npcs.parallelStream().filter(c -> c.getID() == characterID).findFirst().get();
	}
	
	public static Character getCharacterByID(int characterID)
	{
		//There should be no duplicate character IDs, but if that rule changes, this will need to change as well.
		//TODO: Refactor program to accept Optional<Character> here. A null Character should be fine too.
		return characters.parallelStream().filter(c -> c.getID() == characterID).findFirst().get();
	}

	public static Boolean move(Character character, Direction direction)
	{
		if(character.getLocation().has(direction) && !character.isFighting())
		{
			//TODO: If direction is obstructed, handle obstruction.
			character.getLocation().getLinks().get(direction).addCharacter(character);
			character.getLocation().setWasVisited(true);
			
			//If we decide to let NPCs roam and initiate combat with other NPCs, this will require amending.
			//See if there are any hostile NPCs in the Node.
			if(!character.isNPC() && character.getLocation().containsNPCs())
			{
				List<NPC> someNPCs = character.getLocation().getNPCs();
				for(NPC i : someNPCs)
				{
					i.debugInfo();
				}
				List<NPC> hostileNPCs = character.getLocation().getNPCs().stream().filter(e -> e.isHostile()).collect(Collectors.toList());
				if (hostileNPCs.size() > 0)
				{
					setOnMoveFighters(character);
				}
			}
			
			return true;
		}
		else if(character.isFighting())
		{
			//TODO: Handle escape attempts...
			TextManager.appendText("You can't run away! ", TextStyle.REGULAR);
		}
		return false;
	}

	public static Node getBaseNode()
	{
		return baseNode;
	}
	public static Node[][] getGrid()
	{
		return grid;
	}
	public static void setGrid(Node[][] grid)
	{
		Master.grid = grid;
	}
	public static void redrawGrid(Character character)
	{
		GridManager.redrawGrid(character, grid);
	}
	
	
	//TODO: Remove Player Character hack
	public static void setOnMoveFighters(Character character)
	{
		//TODO: Enemies in the same faction should attack all players that they can, leaving the most empty slots in each encounter.
		ArrayList<Character> battlers = character.getLocation().getPotentialFighters();
		int i = ((battlers.size()) > Combat.MAX_SIZE) ? Combat.MAX_SIZE : battlers.size();
		Character[] fighters = new Character[i];
		for(int j = 0; j < fighters.length; j++)
		{
			fighters[j] = battlers.get(j);
		}
		if(battlers.size() > (Combat.MAX_SIZE - 1))
		{
			boolean present = false;
			for(Character c : fighters)
			{
				if(c.getID() == battlers.get(battlers.size() - 1).getID())
				{
					present = true;
				}
			}
			if(!present)
			{
				fighters[Combat.MAX_SIZE - 1] = battlers.get(battlers.size() - 1);
			}
		}
		int[] sides = new int[fighters.length];
		for(int j = 0; j < fighters.length; j++)
		{
			if(fighters[j].isNPC())
			{
				sides[j] = 1;
			}
			else
			{
				sides[j] = 0;
			}
		}
		startCombat(fighters, sides);
	}
	public static void startCombat(Character[] fighters, int[] sides)
	{
		String instance = UUID.randomUUID().toString();
		battleData.put(instance, new BattleData(instance));
		//No one who is already fighting may be added to another fight.
		if(Arrays.asList(fighters).parallelStream().noneMatch(a -> a.isFighting()))
		{
			List<Combat> potential = new ArrayList<Combat>(combatContainers.parallelStream().filter(c -> !c.isActive()).collect(Collectors.toList()));
			if(potential.size() > 0)
			{
				Combat thisCombat = potential.get(0);
				//It should already have been cleared.
				thisCombat.clear();
				thisCombat.startCombat(fighters, sides);
				thisCombat.setInstance(instance);
			}
			
			//If none are free, create another combat container and put the fighters in there. Combat containers are reset upon the conclusion of battles, but not removed.
			//I know this sounds like a TERRIBLE idea, but I think that having the combat instances in memory will prove to be a fast solution. We will have to see how it does.
			else
			{
				int i = combatContainers.size();
				combatContainers.add(new Combat(i, instance));
				combatContainers.get(i).startCombat(fighters, sides);
			}
			playerCharacter.setTarget(playerCharacter.getEnemies().get().stream().findFirst().get());
			setAttackSkills();
		}
	}
	public static void setAttackSkills()
	{
		//TODO: Fix the horror that is the current targeting system.
		//TODO: Remove Player Character hack
		ArrayList<ArrayList<Object>> targetses = new ArrayList<ArrayList<Object>>();
		ArrayList<Skill> skills = new ArrayList<Skill>(playerCharacter.getSkills().stream().filter(e -> e.isAvailable(playerCharacter)).collect(Collectors.toList()));
		for(int i = 0; i < skills.size(); i++)
		{
			ArrayList<Object> targets = new ArrayList<Object>();
			if(skills.get(i).getPossibleTargets() != "one" && skills.get(i).getPossibleTargets() != "not self")
			{
				targets.add(skills.get(i).getPossibleTargets());
				targetses.add(targets);
			}
			else
			{
				targets.add(playerCharacter.getTarget().get());
				targetses.add(targets);
			}
		}
		GUIHook.setAttackButtons(targetses, skills);
	}
	public static void intrude(int combatID, Character intruder, int side)
	{
		combatContainers.get(combatID).addIntruder(intruder, side);
	}
	public static Combat getCombatInstance(int combatID)
	{
		return combatContainers.get(combatID);
	}
	public static void nextTurn(String instance)
	{
		battleData.get(instance).nextTurn();
	}
	public static void addAction(String instance, TurnData action)
	{
		battleData.get(instance).addAction(action);
	}
	public static void printBattleData(String instance)
	{
		System.out.println(battleData.get(instance).toString());
	}
	
	
 	public static void setSceneButtons(int buttonList)
	{
		if(buttonList < buttons.size())
		{
			GUIHook.setButtons(buttons.get(buttonList), labels.get(buttonList), buttonList);
		}
	}
	public static void setButtons(ArrayList<Button> setA, ArrayList<Label> setB, int i)
	{
		buttons.get(i).clear();
		buttons.get(i).addAll(setA);
		labels.get(i).clear();
		labels.get(i).addAll(setB);
		assert labels.size() == buttons.size();
	}
	public static ArrayList<Button> getButtons(int i)
	{
		return buttons.get(i);
	}
	
	public static int getButtonLists()
	{
		return buttons.size();
	}
	
	public static ArrayList<Button> getLastButtons()
	{
		return buttons.get(buttons.size() - 1);
	}	
	public static ArrayList<Label> getLabels(int i)
	{
		return labels.get(i);
	}
	public static ArrayList<Label> getLastLabels()
	{
		return labels.get(labels.size() - 1);
	}
	public static void addButtons()
	{
		Master.buttons.add(new ArrayList<Button>());
		Master.labels.add(new ArrayList<Label>());
	}
	public static void removeButtons()
	{
		if(buttons.size() >= 1)
		{
			buttons.remove(buttons.size() - 1);
			labels.remove(labels.size() - 1);
		}
		if(buttons.size() == 0)
		{
			TextManager.appendText(("ROKAAY. AH'M RELOADING."), TextStyle.DEBUG);
			buttons.add(new ArrayList<Button>());
			labels.add(new ArrayList<Label>());
		}
		assert labels.size() == buttons.size();
	}
	public static int getIndex()
	{
		return index;
	}
	public static void setIndex(int index)
	{
		Master.index = index;
	}
	public static void resetButtons()
	{
		//We will do it this way unless/until we want to add actions besides those given by the Node.
		LinkedHashMap<String, Supplier<Void>> nodeActions = getPlayerCharacter().getLocation().getActions();
		ArrayList<String> buttonLabels = new ArrayList<String>(nodeActions.keySet().stream().collect(Collectors.toList()));
		ArrayList<Button> actions = GUIHook.resetButtons(buttonLabels);
		ArrayDeque<Supplier<Void>> functions = new ArrayDeque<Supplier<Void>>();
		for(int i = 0; i > buttonLabels.size(); i++)
		{
			functions.push(nodeActions.get(buttonLabels.get(i)));
		}
		for(int i = 0; i < buttonLabels.size(); i++)
		{
			actions.get(i).setOnAction(e ->
			{
				functions.pollLast().get();
			});
		}
	}
	
	
	public static javafx.scene.layout.VBox createStatDisplay(Character character)
	{
		return GUIHook.createStatDisplay(character);
	}
	public static void changeStatDisplay(Character character, String stat, double value, double percentage)
	{
		if(character.statDisplay.getParent() == null)
			displayStats(character);
		GUIHook.changeStatDisplay(stat, value, percentage, character);
	}
	public static void displayStats(Character character)
	{
		if((character.statDisplay.getParent() != null))
			return;
		if(!character.equals(getPlayerCharacter()) && character.getSide() != getPlayerCharacter().getID())
			GUIHook.showStatDisplay(character.statDisplay, (javafx.scene.layout.AnchorPane) GUIHook.scenes.get(SceneMapping.AP5));
		else
		{
			GUIHook.showStatDisplay(character.statDisplay, (javafx.scene.layout.AnchorPane) GUIHook.scenes.get(SceneMapping.AP2));
		}
	}
	public static void hideStatDisplay(Character character)
	{
		if(!(character.statDisplay.getParent() != null))
			return;
		GUIHook.hideStatDisplay(character.statDisplay, (javafx.scene.layout.AnchorPane) character.statDisplay.getParent());
	}
}
