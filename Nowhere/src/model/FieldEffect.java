package model;

public abstract class FieldEffect
{
	public String name, description;
	public int duration;
	
	public FieldEffect(String name, int duration)
	{
		this.name = name;
		this.duration = duration;
		description = name + " is in effect.";
	}
	
	public abstract void applyEffect(Character character);
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((FieldEffect) obj).name)
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
