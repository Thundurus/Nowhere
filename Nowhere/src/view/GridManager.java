package view;

import java.util.HashSet;
import java.util.Set;

import controller.GUIHook;
import model.Direction;
import model.Master;
import model.Node;
import util.XY;

public final class GridManager
{
	public static void redrawGrid(model.Character observer, Node[][] nodeArray)
	{
		int x = observer.getLocation().getXY().x;
		int y = observer.getLocation().getXY().y;
		
		Set<XY> nodes = new HashSet<XY>();
		Set<XY> traversable = new HashSet<XY>();
		
		try
		{
			if(Master.getGrid().length > 0)
			{
				nodes.add(new XY(2, 2));
			}
		}
		catch (Exception e)
		{
		}
		
		//Somehow, recursion might be ideal here. Screw recursion though; we're doing this the hard, pointless way.
		if(observer.getLocation().has(Direction.N))
		{
			traversable.add(new XY(2, 1));
		}
		if(observer.getLocation().has(Direction.E))
		{
			traversable.add(new XY(3, 2));
		}
		if(observer.getLocation().has(Direction.S))
		{
			traversable.add(new XY(2, 3));
		}
		if(observer.getLocation().has(Direction.W))
		{
			traversable.add(new XY(1, 2));
		}
		
		for(int i = y-2; i < y+3; i++)
		{
			for(int j = x-2; j < x+3; j++)
			{
				if(nodeArray[j][i].wasVisited())
				{
					nodes.add(observer.getLocation().getXY().translate(new XY(j, i)));
				}
			}
		}
		
		wipe();
		for(XY i : nodes)
		{
			GridStylizer.setGeneric(false, i.x, i.y);
		}		
		for(XY i : traversable)
		{
			GridStylizer.setGeneric(true, i.x, i.y);
		}
	}
	
	public static void wipe()
	{
		for(int i = 0; i < GUIHook.grid.length; i++)
		{
			for(int j = 0; j < GUIHook.grid[i].length; j++)
			{
				GridStylizer.setEmpty(i, j);
			}
		}
	}
}
