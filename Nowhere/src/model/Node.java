package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import util.XY;
import view.TextManager;
import view.TextStyle;

public class Node
{
	private String location;
	private HashMap<Direction, Node> links = new HashMap<Direction, Node>();
	private HashMap<Direction, String> obstructions = new HashMap<Direction, String>();
	private ArrayList<Character> characters = new ArrayList<Character>();
	private Boolean wasVisited = false;
	private XY xy = new XY(0,0);
	private LinkedHashMap<String, Supplier<Void>> actions = new LinkedHashMap<String, Supplier<Void>>();

	public static Node nullNode()
	{
		Node blank = new Node();
		blank.setLocation("Null");
		return blank;
	}
	
	public Node()
	{
		location = "Nowhere?";
	}
	
	public Node(String location)
	{
		this.location = location;
	}
	
	public Node(String location, HashMap<Direction, Node> links)
	{
		this.location = location;
		this.links = links;
	}
	
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public HashMap<Direction, Node> getLinks()
	{
		return links;
	}

	//Currently, a hypothetical NodeA can have a link to NodeB without NodeB necessarily having a complementary link to NodeA, but this will not be the default behavior. Instead, this will require either for the complementary link to be removed afterwards, or a new method which does this.
	public void setLinks(HashMap<Direction, Node> links)
	{
		this.links = links;
	}
	
	public void addLink(Direction direction, Node link)
	{
		links.put(direction, link);
		if(!link.getLinks().containsValue(this))
		{
			link.addLink(Direction.reverseDirection(direction), this);
		}
	}
	
	public void addLinks(HashMap<Direction, Node> links)
	{
		for(Map.Entry<Direction, Node> i : links.entrySet())
		{
			addLink(i.getKey(), i.getValue());
		}
	}
	
	//Removes any (and all) links that meet the criteria from this Node only.
	public void removeLink(Node link)
	{
		for (Map.Entry<Direction, Node> i : links.entrySet())
		{
			if(i.getValue().equals(link))
			{
				links.remove(i.getKey());
			}
		}
	}

	public HashMap<Direction, String> getObstructions()
	{
		return obstructions;
	}

	public void setObstructions(HashMap<Direction, String> obstructions)
	{
		this.obstructions = obstructions;
	}

	public ArrayList<Character> getCharacters()
	{
		return characters;
	}

	//This method is of questionable value, and I do not know the use cases well enough to decide whether it should make use of addCharacter.
	public void setCharacters(ArrayList<Character> characters)
	{
		this.characters = characters;
		for(Character i : characters)
		{
			i.setLocation(this);
		}
	}
	
	public void addCharacter(Character character)
	{
		characters.add(character);
		character.getLocation().removeCharacter(character);
		character.setLocation(this);
	}
	
	public void removeCharacter(Character character)
	{
		characters.remove(character);
	}
	
	public boolean containsNPCs()
	{
		return characters.stream().anyMatch(c -> c.isNPC());
	}
	
	public ArrayList<NPC> getNPCs()
	{
		ArrayList<Character> npcs = new ArrayList<Character>(characters.stream().filter(e -> e.isNPC()).collect(Collectors.toList()));
		ArrayList<NPC> rNPCs = new ArrayList<NPC>();
		for(int i = 0; i < npcs.size();i++)
		{
			rNPCs.add((NPC) npcs.get(i));
		}
		return rNPCs;
	}
	
	public ArrayList<Character> getPotentialFighters()
	{
		//TODO: Make sure these characters actually can fight (e.g. they aren't dead/K.O.'d/otherwise incapacitated).
		ArrayList<Character> fighters = new ArrayList<Character>(characters.stream().filter(e -> !e.isNPC()).collect(Collectors.toList()));
		fighters.addAll(new ArrayList<Character>(getNPCs().stream().filter(e -> e.isHostile()).collect(Collectors.toList())));
		return fighters;
	}
	
	public boolean has(Direction direction)
	{
		if(links.containsKey(direction))
		{
			return true;
		}
		return false;
	}
	
	//This looks like it might be unnecessarily slow. Testing required.
	public boolean has(String direction)
	{
		for(String str : Direction.mappings.keySet())
		{
			if(direction.equalsIgnoreCase(str))
			{
				return has(Direction.mappings.get(str));
			}
		}
		return false;
	}
	
	public boolean wasVisited()
	{
		return wasVisited;
	}

	public void setWasVisited(Boolean wasVisited)
	{
		if(!this.wasVisited && wasVisited)
		{
			TextManager.appendText((location + " was visited for the first time."), TextStyle.DEBUG);
		}
		this.wasVisited = wasVisited;
	}
	
	public XY getXY()
	{
		return xy;
	}

	public void setXY(XY xy)
	{
		this.xy = xy;
	}
	
	public LinkedHashMap<String, Supplier<Void>> getActions()
	{
		return actions;
	}
	
	public void addAction(String string, Supplier<Void> function)
	{
		actions.put(string, function);
	}
	
	public boolean isNull()
	{
		return (location.equalsIgnoreCase("Null"));
	}
	
	public void debugInfo()
	{
		if(!location.equalsIgnoreCase("Null"))
		{
			TextManager.appendText((location + " is located at " + xy.toString() + " and has " + links.size() + " link(s). "), TextStyle.DEBUG);
			for (Map.Entry<Direction, Node> i : links.entrySet())
			{
				TextManager.appendText((i.getKey().toString(true) + " maps to " + i.getValue().getLocation() + "."), TextStyle.DEBUG);
			}
			if(!characters.isEmpty())
			{
				TextManager.appendText((location + " has " + characters.size() + " inhabitants(s). "), TextStyle.DEBUG);
				for (Character i : characters)
				{
					i.debugInfo();
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null)
		{
			if(!obj.getClass().equals(this.getClass()))
				return false;
			if(location == ((Node) obj).location)
				return true;
			return false;
		}
		return false;
	}
	//Completely secure and cromulent hashing algorithm #2.
	@Override
	public int hashCode()
	{
		int i = 0, j = 0;
		for(char c : location.toCharArray())
		{
			j += (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 2);
			i++;
		}
		return j;
	}
}