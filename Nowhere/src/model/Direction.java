package model;

import java.util.HashMap;

public enum Direction
{
	N, NE, E, SE, S, SW, W, NW;
	
	public static HashMap<String, Direction> mappings = new HashMap<String, Direction>();
	
	public static void mapDirections()
	{
		mappings.put("North", N);
		mappings.put("Northeast", NE);
		mappings.put("East", E);
		mappings.put("Southeast", SE);
		mappings.put("South", S);
		mappings.put("Southwest", SW);
		mappings.put("West", W);
		mappings.put("Northwest", NW);
	}
	
	public static Direction reverseDirection(Direction direction)
	{
		if(direction == N)
		{
			return S;
		}
		if(direction == NE)
		{
			return SW;
		}
		if(direction == E)
		{
			return W;
		}
		if(direction == SE)
		{
			return NW;
		}
		if(direction == S)
		{
			return N;
		}
		if(direction == SW)
		{
			return NE;
		}
		if(direction == W)
		{
			return E;
		}
		if(direction == NW)
		{
			return SE;
		}
		return null;
	}
	
	@Override
	public String toString()
	{
		if(this == N)
		{
			return "north";
		}
		if(this == NE)
		{
			return "northeast";
		}
		if(this == E)
		{
			return "east";
		}
		if(this == SE)
		{
			return "southeast";
		}
		if(this == S)
		{
			return "south";
		}
		if(this == SW)
		{
			return "southwest";
		}
		if(this == W)
		{
			return "west";
		}
		if(this == NW)
		{
			return "northwest";
		}
		return null;
	}
	
	public String toString(Boolean capitalize)
	{
		if(this == N)
		{
			if(capitalize)
			return "North";
			return "north";
		}
		if(this == NE)
		{
			if(capitalize)
			return "Northeast";
			return "northeast";
		}
		if(this == E)
		{
			if(capitalize)
			return "East";
			return "east";
		}
		if(this == SE)
		{
			if(capitalize)
			return "Southeast";
			return "southeast";
		}
		if(this == S)
		{
			if(capitalize)
			return "South";
			return "south";
		}
		if(this == SW)
		{
			if(capitalize)
			return "Southwest";
			return "southwest";
		}
		if(this == W)
		{
			if(capitalize)
			return "West";
			return "west";
		}
		if(this == NW)
		{
			if(capitalize)
			return "Northwest";
			return "northwest";
		}
		return null;
	}
}
