package view;

import java.util.LinkedHashMap;
import java.util.List;

import controller.GUIHook;
import controller.SceneMapping;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TextManager
{
	public static void appendText(String txt, TextStyle style)
	{
//		if(style != TextStyle.DEBUG)
		GUIHook.appendMainText(TextStylizer.styleText(txt, style));
	}
	
	public static void appendText(LinkedHashMap<String, TextStyle> text)
	{
		List<Text> styledText = TextStylizer.styleText(text);
		for(Text i : styledText)
		{
			GUIHook.appendMainText(i);
		}
	}
	
	public static void setLocation(String text)
	{
		((TextArea) GUIHook.scenes.get(SceneMapping.AP7).lookup("#location")).setText(text);
	}
}
