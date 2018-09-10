package model;

public class Field
{
	public String name, description;
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((Field) obj).name)
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
