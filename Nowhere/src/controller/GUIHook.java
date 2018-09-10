package controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import model.Combat;
import model.Direction;
import model.Master;
import model.Skill;
import model.SkillFlags;
import model.Skills.*;
import model.Type;
import model.AI.Core;
import view.TextStyle;
import view.TextManager;

public class GUIHook implements Initializable
{
	public static int defaultChildren = 1; //The amount of children a StackPane on the map grid can have which, if exceeded, causes the program to assume it is in use by a button.
	public static ArrayDeque<Supplier<Void>> todo = new ArrayDeque<Supplier<Void>>();
	public static LinkedHashMap<ButtonMapping, Pane> mapping = new LinkedHashMap<ButtonMapping, Pane>();
	public static LinkedHashMap<ButtonMapping, Pane> leftright = new LinkedHashMap<ButtonMapping, Pane>();
	public static LinkedHashMap<SceneMapping, Pane> scenes = new LinkedHashMap<SceneMapping, Pane>();
	public static boolean locked = false; //Determines whether statDisplays may be added or removed.
	public static Pane[][] grid;
    
	@FXML
	private AnchorPane root;
	
    @FXML
    private TextFlow mainText;
	
	@FXML
    private AnchorPane ap1;

    @FXML
    private AnchorPane ap2;

    @FXML
    private AnchorPane ap3;
    
    @FXML
    private AnchorPane ap4;

    @FXML
    private AnchorPane ap5;

    @FXML
    private AnchorPane ap6;

    @FXML
    private AnchorPane ap7;
	
	@FXML
    private StackPane r1c1;

    @FXML
    private TextArea r1c1_t;

    @FXML
    private StackPane r1c2;

    @FXML
    private TextArea r1c2_t;

    @FXML
    private StackPane r1c3;

    @FXML
    private TextArea r1c3_t;

    @FXML
    private StackPane r1c4;

    @FXML
    private TextArea r1c4_t;

    @FXML
    private StackPane r1c5;

    @FXML
    private TextArea r1c5_t;

    @FXML
    private StackPane r2c1;

    @FXML
    private TextArea r2c1_t;

    @FXML
    private StackPane r2c2;

    @FXML
    private TextArea r2c2_t;

    @FXML
    private StackPane r2c3;

    @FXML
    private TextArea r2c3_t;

    @FXML
    private StackPane r2c4;

    @FXML
    private TextArea r2c4_t;

    @FXML
    private StackPane r2c5;

    @FXML
    private TextArea r2c5_t;

    @FXML
    private StackPane r3c1;

    @FXML
    private StackPane r3c2;

    @FXML
    private StackPane r3c3;

    @FXML
    private StackPane r3c4;

    @FXML
    private StackPane r3c5;
    
    @FXML
    private StackPane left;
    
    @FXML
    private StackPane right;
    
    @FXML
    private StackPane mg11;

    @FXML
    private StackPane mg12;

    @FXML
    private StackPane mg13;

    @FXML
    private StackPane mg14;

    @FXML
    private StackPane mg15;

    @FXML
    private StackPane mg21;

    @FXML
    private StackPane mg22;

    @FXML
    private StackPane mg23;

    @FXML
    private StackPane mg24;

    @FXML
    private StackPane mg25;

    @FXML
    private StackPane mg31;

    @FXML
    private StackPane mg32;

    @FXML
    private StackPane mg33;

    @FXML
    private StackPane mg34;

    @FXML
    private StackPane mg35;

    @FXML
    private StackPane mg41;

    @FXML
    private StackPane mg42;

    @FXML
    private StackPane mg43;

    @FXML
    private StackPane mg44;

    @FXML
    private StackPane mg45;

    @FXML
    private StackPane mg51;

    @FXML
    private StackPane mg52;

    @FXML
    private StackPane mg53;

    @FXML
    private StackPane mg54;

    @FXML
    private StackPane mg55;

    
    @Override
	public void initialize(URL url, ResourceBundle rsrcs)
	{
		assert root != null;
		assert mainText != null;
    	
		assert r1c1 != null;
		assert r1c2 != null;
		assert r1c3 != null;
		assert r1c4 != null;
		assert r1c5 != null;
		assert r1c1 != null;
		assert r2c2 != null;
		assert r2c3 != null;
		assert r2c4 != null;
		assert r2c5 != null;
		assert r3c1 != null;
		assert r3c2 != null;
		assert r3c3 != null;
		assert r3c4 != null;
		assert r3c5 != null;
		assert left != null;
		assert right != null;
		
		assert r1c1_t != null;
		assert r1c2_t != null;
		assert r1c3_t != null;
		assert r1c4_t != null;
		assert r1c5_t != null;
		assert r2c1_t != null;
		assert r2c2_t != null;
		assert r2c3_t != null;
		assert r2c4_t != null;
		assert r2c5_t != null;
		
		assert ap1 != null;
		assert ap2 != null;
		assert ap3 != null;
		assert ap4 != null;
		assert ap5 != null;
		assert ap6 != null;
		assert ap7 != null;
		
		mapping.put(ButtonMapping.R1C1, r1c1);
		mapping.put(ButtonMapping.R1C2, r1c2);
		mapping.put(ButtonMapping.R1C3, r1c3);
		mapping.put(ButtonMapping.R1C4, r1c4);
		mapping.put(ButtonMapping.R1C5, r1c5);
		mapping.put(ButtonMapping.R2C1, r2c1);
		mapping.put(ButtonMapping.R2C2, r2c2);
		mapping.put(ButtonMapping.R2C3, r2c3);
		mapping.put(ButtonMapping.R2C4, r2c4);
		mapping.put(ButtonMapping.R2C5, r2c5);
		mapping.put(ButtonMapping.R3C1, r3c1);
		mapping.put(ButtonMapping.R3C2, r3c2);
		mapping.put(ButtonMapping.R3C3, r3c3);
		mapping.put(ButtonMapping.R3C4, r3c4);
		mapping.put(ButtonMapping.R3C5, r3c5);
		leftright.put(ButtonMapping.LEFT, left);
		leftright.put(ButtonMapping.RIGHT, right);
		scenes.put(SceneMapping.ROOT, root);
		scenes.put(SceneMapping.MAIN, mainText);
		scenes.put(SceneMapping.AP1, ap1);
		scenes.put(SceneMapping.AP2, ap2);
		scenes.put(SceneMapping.AP3, ap3);
		scenes.put(SceneMapping.AP4, ap4);
		scenes.put(SceneMapping.AP5, ap5);
		scenes.put(SceneMapping.AP6, ap6);
		scenes.put(SceneMapping.AP7, ap7);
		
		Pane[][] theGrid = {{mg11, mg21, mg31, mg41, mg51},
							{mg12, mg22, mg32, mg42, mg52},
							{mg13, mg23, mg33, mg43, mg53},
							{mg14, mg24, mg34, mg44, mg54},
							{mg15, mg25, mg35, mg45, mg55}};
		grid = theGrid;
//		for(int i = 0; i <  grid.length;i++)
//		{
//			for(int j = 0; j <  grid[i].length;j++)
//			{
//				TextManager.appendText(grid[i][j], TextStyle.DEBUG);		
//			}
//		}
		
		root.getStylesheets().add("controller/stylesheet.css");
		
		Master.addButtons();
		Direction.mapDirections();
		
		root.addEventFilter(KeyEvent.KEY_PRESSED, e ->
		{
			if(e.getCode() == KeyCode.DIGIT1)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R1C1).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
					//Exception handling? Ha!
				}
			}
			if(e.getCode() == KeyCode.DIGIT2)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R1C2).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT3)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R1C3).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT4)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R1C4).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT5)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R1C5).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT6)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R2C1).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT7)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R2C2).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT8)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R2C3).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT9)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R2C4).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.DIGIT0)
			{
				try
				{
					((Button) mapping.get(ButtonMapping.R2C5).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.Q)
			{
				try
				{
					((Button) leftright.get(ButtonMapping.LEFT).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.E)
			{
				try
				{
					((Button) leftright.get(ButtonMapping.RIGHT).lookup(".button")).fire();
				}
				catch (Exception ex)
				{
				}
			}
			if(e.getCode() == KeyCode.W)
			{
				moveNorth();
			}
			if(e.getCode() == KeyCode.A)
			{
				moveWest();
			}
			if(e.getCode() == KeyCode.S)
			{
				moveSouth();
			}
			if(e.getCode() == KeyCode.D)
			{
				moveEast();
			}
		});
		
		clearButtons();
		
		Button start = genSet("Start");
		start.setOnAction(e ->
		{
//			ArrayList<String> longList = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51"));
//			ArrayList<Button> buttonList = resetButtons(longList);
//			buttonList.get(0).setOnAction(f ->
//			{
//				model.NodeGen.generateTestSite();
//			});
//			buttonList.get(1).setOnAction(f ->
//			{
//				ObservableList<javafx.scene.Node> vbox = scenes.get(SceneMapping.AP2).getChildren();
//				for(javafx.scene.Node i : vbox)
//	    		{
//	    			i.setVisible(false);
//	    			
//	    		}
//			});
//			buttonList.get(2).setOnAction(f ->
//			{
//				ObservableList<javafx.scene.Node> vbox = scenes.get(SceneMapping.AP2).getChildren();
//				for(javafx.scene.Node i : vbox)
//	    		{
//	    			i.setVisible(true);
//	    		}
//			});
//			buttonList.get(3).setOnAction(f ->
//			{
//				showStatDisplay(Master.getPlayerCharacter().getStatDisplay(), (AnchorPane) Master.getPlayerCharacter().getStatDisplay().getParent());
//			});
//			buttonList.get(4).setOnAction(f ->
//			{
//				hideStatDisplay(Master.getPlayerCharacter().getStatDisplay(), (AnchorPane) Master.getPlayerCharacter().getStatDisplay().getParent());
//			});
//			/*
//			buttonList.get(3).setOnAction(f ->
//			{
//				changeStatDisplay("hp", 50, 20.0);
//			});
//			buttonList.get(4).setOnAction(f ->
//			{
//				changeStatDisplay("hp", 100, 40.0);
//			});
//			buttonList.get(5).setOnAction(f ->
//			{
//				changeStatDisplay("hp", 150, 60.0);
//			});
//			buttonList.get(6).setOnAction(f ->
//			{
//				changeStatDisplay("hp", 200, 80.0);
//			});
//			buttonList.get(7).setOnAction(f ->
//			{
//				changeStatDisplay("hp", 250, 100.0);
//			});
//			buttonList.get(10).setOnAction(f ->
//			{
//				changeStatDisplay("mp", 50, 20.0);
//			});
//			buttonList.get(11).setOnAction(f ->
//			{
//				changeStatDisplay("mp", 100, 40.0);
//			});
//			buttonList.get(12).setOnAction(f ->
//			{
//				changeStatDisplay("mp", 150, 60.0);
//			});
//			buttonList.get(13).setOnAction(f ->
//			{
//				changeStatDisplay("mp", 200, 80.0);
//			});
//			buttonList.get(14).setOnAction(f ->
//			{
//				changeStatDisplay("mp", 250, 100.0);
//			});
//			buttonList.get(15).setOnAction(f ->
//			{
//				changeStatDisplay("sp", 50, 20.0);
//			});
//			buttonList.get(16).setOnAction(f ->
//			{
//				changeStatDisplay("sp", 100, 40.0);
//			});
//			buttonList.get(17).setOnAction(f ->
//			{
//				changeStatDisplay("sp", 150, 60.0);
//			});
//			buttonList.get(18).setOnAction(f ->
//			{
//				changeStatDisplay("sp", 200, 80.0);
//			});
//			buttonList.get(19).setOnAction(f ->
//			{
//				changeStatDisplay("sp", 250, 100.0);
//			});
//			*/
			
			model.NodeGen.generateTestSite();
			clearButtons();
		});
		Master.setPlayerCharacter(Master.newPlayer("THE MAN"));
		Master.getPlayerCharacter().changeStat("willpower", 50);
		Master.getPlayerCharacter().restoreStat("mp", 1000, true);
		Master.getPlayerCharacter().changeStat("endurance", 50);
		Master.getPlayerCharacter().restoreStat("sp", 1000, true);
//		System.out.println(Master.getPlayerCharacter().getStat("mp").intValue());
		Master.getPlayerCharacter().addSkill(new AvidiaHolyWater());
		Master.getPlayerCharacter().addSkill(new WaterDragon());
		Master.getPlayerCharacter().addSkill(new SummonedGatling());
		Master.getPlayerCharacter().addSkill(new WaterSphere());
		Master.newNPC("THE ADVERSARY");
		Master.getNPCByID(0).modStat("speed", 100);
		Master.getNPCByID(0).addType(Type.GHOST);
		scenes.get(SceneMapping.AP2).getChildren().clear();
	}
    
    
    /**
     * @param text: The label text.
     * @param position: Position on the virtual keyboard.
     */
    public static Button genButton(String text, ButtonMapping position)
    {
    	Button button = new Button("");
    	button.maxHeightProperty().bind(((StackPane) mapping.get(position)).heightProperty());
    	button.maxWidthProperty().bind(((StackPane) mapping.get(position)).widthProperty());
    	Label label = new Label(text);
    	label.setMouseTransparent(true);
    	Master.getLastButtons().add(button);
    	Master.getLastLabels().add(label);
    	
    	return button;
    }
    public static Button genButton(String text)
    {
    	Button button = new Button("");
		if(Master.getLastButtons().size() > 14)
		{
			Master.addButtons();
		}
    	
    	Boolean used = true;
    	int i = 0;
    	while(used)
    	{
    		if(mapping.get(mapping.keySet().toArray()[i]).getChildren().size() <= defaultChildren)
    		{
    			ButtonMapping position = (ButtonMapping) mapping.keySet().toArray()[i];
    			button = genButton(text, position);
    			used = false;
    		}
    		i++;
    	}
    	    	
    	return button;
    }
    //I need separate methods for _setting_ buttons from the master button lists, and _resetting_ the afore-mentioned lists, and the method names are going to be confusing no matter what.
    public static void setButton(Button button, Label label)
    {
    	
    	Boolean used = true;
    	int i = 0;
    	while(used)
    	{
    		if(mapping.get(mapping.keySet().toArray()[i]).getChildren().size() <= defaultChildren)
    		{
    			used = false;
    			ButtonMapping position = (ButtonMapping) mapping.keySet().toArray()[i];
    			mapping.get(position).getChildren().remove(mapping.get(position).lookup(".button"));
    			mapping.get(position).getChildren().remove(mapping.get(position).lookup(".label"));		
    	    	mapping.get(position).getChildren().add(button);
    	    	mapping.get(position).getChildren().add(label);
    	    	try
				{
					mapping.get(position).lookup(".text-area").toFront();
				}
    	    	catch (Exception e)
				{
				}
    		}
    		else
    		{
        		i++;
    		}
    	}
    }
    /**
     * @param buttons: LinkedHashMap containing the String:ButtonMapping pairs for button setting. May ONLY be used to set the visible buttons.
     * @return
     */
    public static void setButtons(ArrayList<Button> buttons, ArrayList<Label> labels, int index)
    {
    	unsetButtons();
    	for(int i = 0; i < buttons.size(); i++)
    	{
    		setButton(buttons.get(i), labels.get(i));
    	}
    	Master.setIndex(index);
    	if(Master.getButtonLists() > 1)
    	{
    		if(index > 0)
    		{
    			Button leftButton = new Button("");
    	    	leftButton.maxHeightProperty().bind(((StackPane) leftright.get(ButtonMapping.LEFT)).heightProperty());
    	    	leftButton.maxWidthProperty().bind(((StackPane) leftright.get(ButtonMapping.LEFT)).widthProperty());
    	    	leftButton.setAlignment(Pos.BASELINE_CENTER);
    	    	leftButton.setTextAlignment(TextAlignment.CENTER);
    	    	Label leftLabel = new Label("< ");
    	    	leftLabel.setMouseTransparent(true);
    	    	leftLabel.maxHeightProperty().bind(((StackPane) leftright.get(ButtonMapping.LEFT)).heightProperty());
    	    	leftLabel.maxWidthProperty().bind(((StackPane) leftright.get(ButtonMapping.LEFT)).widthProperty().divide(1.8));
    	    	
    	    	leftright.get(ButtonMapping.LEFT).getChildren().remove(leftright.get(ButtonMapping.LEFT).lookup(".button"));
    	    	leftright.get(ButtonMapping.LEFT).getChildren().remove(leftright.get(ButtonMapping.LEFT).lookup(".label"));		
    	    	leftright.get(ButtonMapping.LEFT).getChildren().add(leftButton);
    	    	leftright.get(ButtonMapping.LEFT).getChildren().add(leftLabel);
    	    	try
    			{
    	    		leftright.get(ButtonMapping.LEFT).lookup(".text-area").toFront();
    			}
    	    	catch (Exception e)
    			{
    			}
    	    	
    	    	leftButton.setOnAction(e ->
    	    	{
    	    		Master.setSceneButtons(index - 1);
    	    	});
    		}
    		if(index < Master.getButtonLists() - 1)
    		{
    			Button rightButton = new Button("");
    	    	rightButton.maxHeightProperty().bind(((StackPane) leftright.get(ButtonMapping.RIGHT)).heightProperty());
    	    	rightButton.maxWidthProperty().bind(((StackPane) leftright.get(ButtonMapping.RIGHT)).widthProperty());
    	    	rightButton.setAlignment(Pos.BASELINE_CENTER);
    	    	rightButton.setTextAlignment(TextAlignment.CENTER);
    	    	Label rightLabel = new Label("> ");
    	    	rightLabel.setMouseTransparent(true);
    	    	rightLabel.maxHeightProperty().bind(((StackPane) leftright.get(ButtonMapping.RIGHT)).heightProperty());
    	    	rightLabel.maxWidthProperty().bind(((StackPane) leftright.get(ButtonMapping.RIGHT)).widthProperty().divide(1.8));
    	    	
    	    	leftright.get(ButtonMapping.RIGHT).getChildren().remove(leftright.get(ButtonMapping.RIGHT).lookup(".button"));
    	    	leftright.get(ButtonMapping.RIGHT).getChildren().remove(leftright.get(ButtonMapping.RIGHT).lookup(".label"));		
    	    	leftright.get(ButtonMapping.RIGHT).getChildren().add(rightButton);
    	    	leftright.get(ButtonMapping.RIGHT).getChildren().add(rightLabel);
    	    	try
    			{
    	    		leftright.get(ButtonMapping.RIGHT).lookup(".text-area").toFront();
    			}
    	    	catch (Exception e)
    			{
    			}
    	    	
    	    	rightButton.setOnAction(e ->
    	    	{
    	    		Master.setSceneButtons(index + 1);
    	    	});
    		}
    	}
    }
    public static Button genSet(String text)
    {
    	Button button = genButton(text);
    	Label label = Master.getLastLabels().get(Master.getLastLabels().size() - 1);
    	setButton(button, label);
    	return button;
    }
    public static ArrayList<Button> resetButtons(ArrayList<String> buttons)
    {
    	clearButtons();
    	ArrayList<Button> buttonList = new ArrayList<Button>();
    	for(String i : buttons)
    	{
    		buttonList.add(genButton(i));
    	}
    	Master.setSceneButtons(0);
    	return buttonList;
    }
    /**
     * Clears visible buttons. 
     */
    public static void unsetButtons()
    {
    	for(Map.Entry<ButtonMapping, Pane> i : mapping.entrySet())
    	{
    		try
    		
			{
				i.getValue().getChildren().remove(i.getValue().lookup(".button"));
				i.getValue().getChildren().remove(i.getValue().lookup(".label"));
			}
    		catch (Exception e)
			{
			}
    	}
    	for(Map.Entry<ButtonMapping, Pane> i : leftright.entrySet())
    	{
    		try
			{
				i.getValue().getChildren().remove(i.getValue().lookup(".button"));
				i.getValue().getChildren().remove(i.getValue().lookup(".label"));
			}
    		catch (Exception e)
			{
			}
    	}
    }
    /**
     * Clears visible buttons, deletes all mappings from the master button lists.
     */
    public static void clearButtons()
    {
    	unsetButtons();
    	for (int i = Master.getButtonLists(); i > 0; i--)
    	{
    		Master.removeButtons();
    	}
    }

    
    public static void moveNorth()
    {
    	if(Master.move(Master.getCharacterByID(0), Direction.N))
		{
			Master.redrawGrid(Master.getCharacterByID(0));
			TextManager.setLocation(Master.getCharacterByID(0).getLocation().getLocation());
		}
    }
    public static void moveWest()
    {
    	if(Master.move(Master.getCharacterByID(0), Direction.W))
		{
			Master.redrawGrid(Master.getCharacterByID(0));
			TextManager.setLocation(Master.getCharacterByID(0).getLocation().getLocation());
		}
    }
    public static void moveEast()
    {
    	if(Master.move(Master.getCharacterByID(0), Direction.E))
		{
			Master.redrawGrid(Master.getCharacterByID(0));
			TextManager.setLocation(Master.getCharacterByID(0).getLocation().getLocation());
		}
    }
    public static void moveSouth()
    {
    	if(Master.move(Master.getCharacterByID(0), Direction.S))
		{
			Master.redrawGrid(Master.getCharacterByID(0));
			TextManager.setLocation(Master.getCharacterByID(0).getLocation().getLocation());
		}
    }
 
    
    public static void setAttackButton(Button button, model.Character attacker, ArrayList<Object> targets, Skill skill)
    {
    	Combat instance = Master.getCombatInstance(attacker.getCombatInstance());
    	Tooltip tooltip = new Tooltip(skill.getpName());
    	if(skill.baseCost > 0)
    	tooltip.setText(skill.getpName() + "\nCost: " + skill.baseCost);
    	tooltip.setShowDelay(new Duration(350));
    	button.setTooltip(tooltip);
    	button.setOnAction(e ->
    	{
    		//TODO: This may require amending if/when the cost of skills can be modified by perks. 
    		//Spells cost MP, all other skills cost SP
    		if((!skill.getFlags().contains(SkillFlags.SPELL) && !(attacker.getStat("sp").doubleValue() >= skill.baseCost)) || (skill.getFlags().contains(SkillFlags.SPELL) && !(attacker.getStat("mp").doubleValue() >= skill.baseCost)))
    		{
    			//TODO: "You cannot do this."
    			return;
    		}
    		instance.chooseAction(attacker, skill, targets);
    		//TODO: processActions() is to be run automatically after an arbitrary amount of time, not initiated by users.
    		Map.Entry<Skill, ArrayList<Object>> solution = Core.decideAction(Master.getNPCByID(0), instance.getCharacters());
    		instance.chooseAction(Master.getNPCByID(0), solution.getKey(), solution.getValue());
    		instance.processActions();
    	});
    	TextManager.appendText(("Button \"" + skill.name + "\" set."), TextStyle.DEBUG);
    }
    public static ArrayList<Button> setAttackButtons(ArrayList<ArrayList<Object>> targetses, ArrayList<Skill> skills)
    {
    	ArrayList<String> str = new ArrayList<String>();
    	for(int i = 0; i < skills.size(); i++)
    	{
    		str.add(skills.get(i).name);
    	}
    	ArrayList<Button> buttons = resetButtons(str);
    	for(int i = 0; i < buttons.size(); i++)
    	{
    		setAttackButton(buttons.get(i), Master.getPlayerCharacter(), targetses.get(i), skills.get(i));
    	}
    	return buttons;
    }
    
    
    public static void setMainText(ArrayList<Text> text)
    {
    	clearMainText();
    	scenes.get(SceneMapping.MAIN).getChildren().addAll(text);
    }
    public static void appendMainText(Text text)
    {
    	scenes.get(SceneMapping.MAIN).getChildren().add(text);
    }
    public static void clearMainText()
    {
    	scenes.get(SceneMapping.MAIN).getChildren().removeAll(scenes.get(SceneMapping.MAIN).getChildren());
    }
    
    
    //Under no circumstances should conflicting animations be allowed to play.
    public static void changeStatDisplay(String stat, double value, double percentage, model.Character character)
    {
    	//TODO: Restrict changeStatDisplay from acting on the same GUI element concurrently while continuing to allow unrelated GUI elements to be modified simultaneously. 
//    	if(locked)
//    	{
//			System.out.println("locked (change)");
//    		todo.push(() -> 
//			{
//				System.out.println("change event");
//				changeStatDisplay(stat, value, percentage, character);
//				return null;
//			});
//    		System.out.println(todo);
//    		return;
//    	}
    	VBox stats = character.getStatDisplay();
    	LinkedHashMap<String, Double> statDisplay = character.getDisplayed();
    	LinkedHashMap<String, javafx.scene.Node> nodes = new LinkedHashMap<String, javafx.scene.Node>();
		for(javafx.scene.Node i : stats.getChildren())
		{
			if(i.getClass().equals(javafx.scene.layout.HBox.class))
			{
				ObservableList<javafx.scene.Node> hboxes = ((Pane) i).getChildren();
				String id = "x";
				for(javafx.scene.Node j : hboxes)
				{
					
					if(j.getClass().equals(AnchorPane.class))
					{			
						ObservableList<javafx.scene.Node> anchorPanes = ((Pane) j).getChildren();
						javafx.scene.Node ap = anchorPanes.stream().findFirst().get();
						
						Pattern numbers = Pattern.compile("[0-9]*");
						Matcher m = numbers.matcher(java.lang.Character.toString(((Label) ap.lookup(".label")).getText().charAt(0)));
						
						//We have the number label.
						if(m.matches())
						{
							nodes.put(id + "number", j.lookup(".label"));
						}
						//We have the text label. The design of the VBox should encourage this to happen first.
						else
						{
							id = ((Label) ap.lookup(".label")).getText();
							nodes.put(id + "label", ap.lookup(".label"));
						}
					}
					if(j.getClass().equals(StackPane.class))
					{
						ObservableList<javafx.scene.Node> stackPanes = ((Pane) j).getChildren();
						for(javafx.scene.Node k : stackPanes)
						{
							if(k.isDisabled())
							{
								nodes.put(id + "barHolder", k);
							}
							else
							{
								nodes.put(id + "bar", k);
							}
						}
					}
				}
			}
		}
		
		ArrayDeque<String> statVX = new ArrayDeque<String>();		
		if(stat.equalsIgnoreCase("hp") && percentage != statDisplay.get("hp"))
		{
			statVX.push(new String("hp"));
			statVX.push(new String("HP"));
		}
		else if(stat.equalsIgnoreCase("mp"))
		{
			statVX.push(new String("mp"));
			statVX.push(new String("MP"));
		}
		else if(stat.equalsIgnoreCase("sp"))
		{
			statVX.push(new String("sp"));
			statVX.push(new String("SP"));
		}
		else
		{
			return;
		}
		
		final String statV2 = statVX.pop();
		final String statV1 = statVX.pop();
		
		Paint currentFill = ((Label) nodes.get(statV2 + "number")).textFillProperty().get();
		
		javafx.scene.effect.Effect effect = ((Label) nodes.get(statV2 + "number")).getEffect();
		
		//Stat bar resizing
		ScaleTransition scale = new ScaleTransition(Duration.millis(2000), nodes.get(statV2 + "bar"));
  		scale.setFromX(statDisplay.get(statV1) * 0.01);
		scale.setToX(percentage * 0.01);
		
  		TranslateTransition slide = new TranslateTransition(Duration.millis(2000), nodes.get(statV2 + "bar"));
  		slide.setByX(-1 * (((Math.abs(percentage - statDisplay.get(statV1))) * .01) / 2) * ((javafx.scene.layout.Region) nodes.get(statV2 + "barHolder")).widthProperty().doubleValue());
  		if(percentage > statDisplay.get(statV1))
  		{
  			slide.setByX(slide.getByX() * -1);
  		}
  		
  		//Stat number value changes
  		double difference = Double.parseDouble(((Label) nodes.get(statV2 + "number")).getText()) - value;
  		ArrayDeque<BigDecimal> values = new ArrayDeque<BigDecimal>();
  		for(int i = 0; i < 40; i++)
  		{
  			//We keep 1 decimal place of precision in the displayed values.
  			int j = Double.toString((value + (difference/40 * i))).split("\\.")[0].length();
  			values.push(new BigDecimal((value + (difference/40 * i)), new MathContext(j + 1)));
  		}
  		Timeline renumberer = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>()
  		{
			@Override
			public void handle(ActionEvent event)
			{
				//TODO: Maybe gradually change the color from red back to its original color.
				((Label) nodes.get(statV2 + "number")).setText(Double.toString(values.pop().doubleValue()));
			}      			
  		}));
  		renumberer.setCycleCount(40);
  		
  		
		ParallelTransition animation = new ParallelTransition();
		animation.getChildren().addAll(scale, slide, renumberer);
		//"Finally" set the correct values, as the animation is not necessarily accurate.
		animation.setOnFinished(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				((Label) nodes.get(statV2 + "number")).setText(Double.toString(value));
				((Label) nodes.get(statV2 + "number")).textFillProperty().set(currentFill);
				((Label) nodes.get(statV2 + "number")).setEffect(effect);
				locked = false;
				while(!todo.isEmpty())
				{
					todo.pollLast().get();
				}
			}
		});
		
		//TODO: Figure out a way to set the text effect for $STATnumber to nothing.
		((Label) nodes.get(statV2 + "number")).textFillProperty().set(Color.web("#f50043"));
		locked = true;
		animation.play();
		
		statDisplay.put(statV1, percentage);
    }
    //This is intended to be a temporary measure until a more screen-resolution agnostic solution is implemented.
    public static void createStatDisplay(model.Character character, AnchorPane parent)
    {
    	VBox vbox = createStatDisplay(character);
    	parent.getChildren().add(vbox);													AnchorPane.setLeftAnchor(vbox, (double) 0); AnchorPane.setTopAnchor(vbox, (double) 0); AnchorPane.setRightAnchor(vbox, (double) 0); AnchorPane.setBottomAnchor(vbox, (double) 0);
    }
    public static VBox createStatDisplay(model.Character character)
    {
    	VBox vbox = new VBox();															vbox.setPrefHeight(305.0); vbox.setPrefWidth(240.0); vbox.setSpacing(5.0);
	    	AnchorPane top = new AnchorPane();											top.minHeightProperty().bind(vbox.heightProperty().divide(3)); top.setPrefHeight(200.0); top.setPrefWidth(200.0);
	    	Region region1 = new Region();												region1.setLayoutX(15.0); region1.setLayoutY(225.0); region1.setMinHeight(5.0); region1.setPrefHeight(200.0); region1.setPrefWidth(200.0); region1.setStyle("-fx-background-color: #2D2D2D;");
	    	HBox hpBox = new HBox();													hpBox.setMinHeight(30.0); hpBox.setPrefHeight(30.0); hpBox.setPrefWidth(230.0);
	    		AnchorPane hpAnchor1 = new AnchorPane();								hpAnchor1.setPrefHeight(200.0); hpAnchor1.setPrefWidth(200.0);
	    			Label hpLabel = new Label("HP");									hpLabel.setAlignment(Pos.CENTER); hpLabel.setContentDisplay(ContentDisplay.CENTER); hpLabel.setLayoutY(8); hpLabel.setPrefHeight(30.0); hpLabel.setPrefWidth(35.0); hpLabel.setTextFill(Color.web("#ca002e"));
	    		StackPane hpStack = new StackPane();									hpStack.setMinWidth(160.0); hpStack.setPrefHeight(150.0); hpStack.setPrefWidth(200.0); hpStack.setPadding(new Insets(5.0));
	    			Region hpBarHolder = new Region();									hpBarHolder.setDisable(true); hpBarHolder.setPrefHeight(200.0); hpBarHolder.setPrefWidth(200.0); hpBarHolder.setStyle("-fx-background-color: #2C2C2C; -fx-background-radius: 5;"); 
	    			Region hpBar = new Region();										hpBar.setLayoutX(15.0); hpBar.setLayoutY(15.0); hpBar.setPrefHeight(200.0); hpBar.setPrefWidth(200.0); hpBar.setStyle("-fx-background-color: #CA002E; -fx-background-radius: 5;");
	    		AnchorPane hpAnchor2 = new AnchorPane();								hpAnchor2.setPrefHeight(200.0); hpAnchor2.setPrefWidth(200.0);
	    			Label hpValue = new Label(character.getStat("hp").toString());		hpValue.setAlignment(Pos.CENTER); hpValue.setContentDisplay(ContentDisplay.CENTER); hpValue.setPrefHeight(30.0); hpValue.setPrefWidth(35.0); hpValue.setTextFill(Color.web("#dadada"));
	    	HBox mpBox = new HBox();													mpBox.setMinHeight(30.0); mpBox.setPrefHeight(30.0); mpBox.setPrefWidth(230.0);
		    	AnchorPane mpAnchor1 = new AnchorPane();								mpAnchor1.setPrefHeight(200.0); mpAnchor1.setPrefWidth(200.0);
					Label mpLabel = new Label("MP");									mpLabel.setAlignment(Pos.CENTER); mpLabel.setContentDisplay(ContentDisplay.CENTER); mpLabel.setLayoutY(8); mpLabel.setPrefHeight(30.0); mpLabel.setPrefWidth(35.0); mpLabel.setTextFill(Color.web("#ca002e"));
				StackPane mpStack = new StackPane();									mpStack.setMinWidth(160.0); mpStack.setPrefHeight(150.0); mpStack.setPrefWidth(200.0); mpStack.setPadding(new Insets(5.0));
					Region mpBarHolder = new Region();									mpBarHolder.setDisable(true); mpBarHolder.setPrefHeight(200.0); mpBarHolder.setPrefWidth(200.0); mpBarHolder.setStyle("-fx-background-color: #2C2C2C; -fx-background-radius: 5;"); 
					Region mpBar = new Region();										mpBar.setLayoutX(15.0); mpBar.setLayoutY(15.0); mpBar.setPrefHeight(200.0); mpBar.setPrefWidth(200.0); mpBar.setStyle("-fx-background-color: #003295; -fx-background-radius: 5;");
				AnchorPane mpAnchor2 = new AnchorPane();								mpAnchor2.setPrefHeight(200.0); mpAnchor2.setPrefWidth(200.0);
					Label mpValue = new Label(character.getStat("mp").toString());		mpValue.setAlignment(Pos.CENTER); mpValue.setContentDisplay(ContentDisplay.CENTER); mpValue.setPrefHeight(30.0); mpValue.setPrefWidth(35.0); mpValue.setTextFill(Color.web("#dadada"));
	    	HBox spBox = new HBox();													spBox.setMinHeight(30.0); spBox.setPrefHeight(30.0); spBox.setPrefWidth(230.0);
		    	AnchorPane spAnchor1 = new AnchorPane();								spAnchor1.setPrefHeight(200.0); spAnchor1.setPrefWidth(200.0);
					Label spLabel = new Label("SP");									spLabel.setAlignment(Pos.CENTER); spLabel.setContentDisplay(ContentDisplay.CENTER); spLabel.setLayoutY(8); spLabel.setPrefHeight(30.0); spLabel.setPrefWidth(35.0); spLabel.setTextFill(Color.web("#ca002e"));
				StackPane spStack = new StackPane();									spStack.setMinWidth(160.0); spStack.setPrefHeight(150.0); spStack.setPrefWidth(200.0); spStack.setPadding(new Insets(5.0));
					Region spBarHolder = new Region();									spBarHolder.setDisable(true); spBarHolder.setPrefHeight(200.0); spBarHolder.setPrefWidth(200.0); spBarHolder.setStyle("-fx-background-color: #2C2C2C; -fx-background-radius: 5;"); 
					Region spBar = new Region();										spBar.setLayoutX(15.0); spBar.setLayoutY(15.0); spBar.setPrefHeight(200.0); spBar.setPrefWidth(200.0); spBar.setStyle("-fx-background-color: #3D8A00; -fx-background-radius: 5;");
				AnchorPane spAnchor2 = new AnchorPane();								spAnchor2.setPrefHeight(200.0); spAnchor2.setPrefWidth(200.0);
					Label spValue = new Label(character.getStat("sp").toString());		spValue.setAlignment(Pos.CENTER); spValue.setContentDisplay(ContentDisplay.CENTER); spValue.setPrefHeight(30.0); spValue.setPrefWidth(35.0); spValue.setTextFill(Color.web("#dadada"));
	    	Region region2 = new Region();												region2.setLayoutX(15.0); region2.setLayoutY(225.0); region2.setMinHeight(5.0); region2.setPrefHeight(200.0); region2.setPrefWidth(200.0); region2.setStyle("-fx-background-color: #2D2D2D;");
	    	AnchorPane bottom = new AnchorPane();										bottom.minHeightProperty().bind(vbox.heightProperty().divide(3)); bottom.setPrefHeight(200.0); bottom.setPrefWidth(200.0);
    	
    	hpAnchor1.getChildren().add(hpLabel); 											AnchorPane.setLeftAnchor(hpLabel, (double) 0); AnchorPane.setTopAnchor(hpLabel, (double) 0); AnchorPane.setRightAnchor(hpLabel, (double) 0); AnchorPane.setBottomAnchor(hpLabel, (double) 0);
    	hpStack.getChildren().addAll(hpBarHolder, hpBar);
    	hpAnchor2.getChildren().add(hpValue); 											AnchorPane.setLeftAnchor(hpValue, (double) 0); AnchorPane.setTopAnchor(hpValue, (double) 0); AnchorPane.setRightAnchor(hpValue, (double) 0); AnchorPane.setBottomAnchor(hpValue, (double) 0);
    	hpBox.getChildren().addAll(hpAnchor1, hpStack, hpAnchor2);
    	mpAnchor1.getChildren().add(mpLabel); 											AnchorPane.setLeftAnchor(mpLabel, (double) 0); AnchorPane.setTopAnchor(mpLabel, (double) 0); AnchorPane.setRightAnchor(mpLabel, (double) 0); AnchorPane.setBottomAnchor(mpLabel, (double) 0);
    	mpStack.getChildren().addAll(mpBarHolder, mpBar);
    	mpAnchor2.getChildren().add(mpValue); 											AnchorPane.setLeftAnchor(mpValue, (double) 0); AnchorPane.setTopAnchor(mpValue, (double) 0); AnchorPane.setRightAnchor(mpValue, (double) 0); AnchorPane.setBottomAnchor(mpValue, (double) 0);
    	mpBox.getChildren().addAll(mpAnchor1, mpStack, mpAnchor2);
    	spAnchor1.getChildren().add(spLabel); 											AnchorPane.setLeftAnchor(spLabel, (double) 0); AnchorPane.setTopAnchor(spLabel, (double) 0); AnchorPane.setRightAnchor(spLabel, (double) 0); AnchorPane.setBottomAnchor(spLabel, (double) 0);
    	spStack.getChildren().addAll(spBarHolder, spBar);
    	spAnchor2.getChildren().add(spValue); 											AnchorPane.setLeftAnchor(spValue, (double) 0); AnchorPane.setTopAnchor(spValue, (double) 0); AnchorPane.setRightAnchor(spValue, (double) 0); AnchorPane.setBottomAnchor(spValue, (double) 0);
    	spBox.getChildren().addAll(spAnchor1, spStack, spAnchor2);
    	vbox.getChildren().addAll(top, region1, hpBox, mpBox, spBox, region2, bottom);
    	return vbox;
    }
    public static void showStatDisplay(VBox statDisplay, AnchorPane parent)
    {
    	if(!locked)
    	{
    		parent.getChildren().add(statDisplay);
	    	AnchorPane.setLeftAnchor(statDisplay, (double) 0);
	    	AnchorPane.setTopAnchor(statDisplay, (double) 0);
	    	AnchorPane.setRightAnchor(statDisplay, (double) 0);
	    	AnchorPane.setBottomAnchor(statDisplay, (double) 0);
    	}
    	else
    	{
    		todo.push(() -> 
    		{
    			showStatDisplay(statDisplay, parent);
    			return null;
    		});
    	}
    }
    public static void hideStatDisplay(VBox statDisplay, AnchorPane parent)
    {
    	if(!locked)
    	{
    		parent.getChildren().remove(statDisplay);
    	}
    	else
    	{
    		todo.push(() -> 
    		{
    			hideStatDisplay(statDisplay, parent);
    			return null;
    		});
    	}
    }
}
