package view;

import controller.GUIHook;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import util.XY;

public final class GridStylizer
{
	public static void setGeneric(Boolean canTravel, int x, int y)
	{
		Pane pane = GUIHook.grid[x][y];
		if(canTravel)
		{
			pane.setStyle("-fx-background-color: linear-gradient(from 10% 10% to 26% 26%, repeat,  #393939 50%, #505050  50%); -fx-background-radius: 7; -fx-border-color: #C45; -fx-border-radius: 7; -fx-border-width: 3.5; -fx-background-insets: 1;");
			pane.setCursor(Cursor.HAND);
			pane.setOnMouseClicked(e ->
			{
				if(y == 1)
				{
					GUIHook.moveNorth();
				}
				else if(x == 1)
				{
					GUIHook.moveWest();
				}
				else if(y == 3)
				{
					GUIHook.moveSouth();
				}
				else if(x == 3)
				{
					GUIHook.moveEast();
				}
			});
		}
		else
			pane.setStyle("-fx-background-color: linear-gradient(from 10% 10% to 26% 26%, repeat,  #393939 50%, #505050  50%); -fx-background-radius: 7; -fx-background-insets: 1;");
	}
	
	public static void setGeneric(Boolean canTravel, XY xy)
	{
		setGeneric(canTravel, xy.x, xy.y);		
	}
	
	public static void setEmpty(int x, int y)
	{
		Pane pane = GUIHook.grid[x][y];
		pane.setStyle("");
		pane.setCursor(Cursor.DEFAULT);
		pane.setOnMouseClicked(e ->
		{

		});
	}
}
