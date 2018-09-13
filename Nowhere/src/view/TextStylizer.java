package view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.text.Text;

public class TextStylizer
{
	public static Text styleText(String txt, TextStyle style)
	{
		Text text = new Text(txt);
		switch(style)
		{
			case REGULAR:	text.setId("regular");
							break;
			case DAMAGE:	text.setId("damage");
							break;
			case DEBUG:		text.setText("\nDEBUG: " + txt);
							text.setId("debug");
							break;
			case ERROR:		text.setText("\nERROR: " + txt);
							text.setId("error");
							break;
		}
		return text;
	}
	
	public static List<Text> styleText(LinkedHashMap<String, TextStyle> text)
	{
		List<Text> styledText = new ArrayList<Text>();
		for(Map.Entry<String, TextStyle> i : text.entrySet())
		{
			styledText.add(styleText(i.getKey(), i.getValue()));
		}
		return styledText;
	}
	
	public static void appendText(List<Text> text)
	{
		
	}
}
