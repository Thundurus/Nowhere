package model;

import java.util.stream.Stream;

import util.XY;

public class NodeGen
{
	public static void generateTestSite()
	{
		XY[] li = {new XY(3,4), new XY(3,3), new XY(4,3), new XY(5,3), new XY(5,2), new XY(6,3), new XY(6,2)};		
		Node[][] nodes = generate(li);
		
		Node node1 = nodes[3][4];
		Node node2 = nodes[3][3];
		Node node3 = nodes[4][3];
		Node node4 = nodes[5][3];
		Node node5 = nodes[5][2];
		Node node6 = nodes[6][3];
		Node node7 = nodes[6][2];
		
		node1.addLink(Direction.N, node2);
		node1.setLocation("Node 1");
		node2.addLink(Direction.E, node3);
		node2.setLocation("Node 2");
		node3.addLink(Direction.E, node4);
		node3.setLocation("Node 3");
		node4.addLink(Direction.N, node5);
		node4.addLink(Direction.E, node6);
		node4.setLocation("Node 4");
		node5.addLink(Direction.E, node7);
		node5.setLocation("Node 5");
		node6.addLink(Direction.N, node7);
		node6.setLocation("Node 6");
		node7.setLocation("Node 7");
		
		node1.addCharacter(Master.getCharacterByID(0));
		node1.setWasVisited(true);
		node4.addCharacter(Master.getNPCByID(0));
		
		Master.setGrid(nodes);
		Master.redrawGrid(Master.getCharacterByID(0));
	}
	
	public static Node[][] generate(XY size, XY[] coordinates)
	{
		Node[] nodeList = new Node[coordinates.length];
		for(int i = 0; i < coordinates.length;i++)
		{
			nodeList[i] = new Node();
			nodeList[i].setXY((XY) coordinates[i]);
		}
		
		Node[][] nodes = new Node[size.x][size.y];
		
		for(int i = 0; i < nodes.length; i++)
		{
			Node[] iNodes = Stream.generate(() -> Node.nullNode()).limit(size.x).toArray(Node[]::new);
			nodes[i] = iNodes;
		}
		
		for(int i = 0; i < nodeList.length; i++)
		{
//			TextManager.appendText((nodeList[i].getXY().x + ", " + nodeList[i].getXY().y), TextStyle.DEBUG);
			Node node = new Node(nodeList[i].getLocation());
			node.setXY(nodeList[i].getXY());
			nodes[nodeList[i].getXY().x][nodeList[i].getXY().y] = node;
		}
		return nodes;
	}
	
	//Produces square maps only.
	public static Node[][] generate(XY[] coordinates)
	{
		return generate(new XY(coordinates.length + 4, coordinates.length + 4), coordinates);
	}
}
