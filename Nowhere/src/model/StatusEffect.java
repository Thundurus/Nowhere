package model;

public class StatusEffect
{
	protected String name;
	protected boolean visible = true;
	
	//Not sure if I want to support this, or instead operate exclusively through StatusEffect child classes. Or enums, maybe.
	public StatusEffect()
	{
		
	}
	
	public StatusEffect(String name, boolean visible)
	{
		this.name = name;
		this.visible = visible;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((StatusEffect) obj).name)
			return true;
		return false;
	}
	//Completely secure and cromulent hashing algorithm #2.
	@Override
	public int hashCode()
	{
		int i = 0, j = 0;
		for(char c : name.toCharArray())
		{
			j += (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 2);
			i++;
		}
		return j;
	}
}
