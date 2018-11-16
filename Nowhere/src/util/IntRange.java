package util;

public class IntRange
{
	public String name;
	public int max = 0;
	public int min = 0;
	
	public IntRange(String name)
	{
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
//		if(((IntRange) obj).name.equals(this.name) && ((IntRange) obj).max == this.max && ((IntRange) obj).min == this.min)
		if(((IntRange) obj).name.equals(this.name))
			return true;
		return false;
	}
	//Completely secure and cromulent hashing algorithm.
	@Override
	public int hashCode()
	{
		return ((max + 1) * 49 + 8 * min - 9) - 46;
	}
	@Override
	public String toString()
	{
		return (name + ": " + min + "~" + max);
	}
}
