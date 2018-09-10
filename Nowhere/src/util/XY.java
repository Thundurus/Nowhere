package util;

public class XY
{
	public int x = 0, y = 0;
	public XY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public XY translate(XY xy)
	{
		return new XY (xy.x - x + 2, xy.y - y + 2);		
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(((XY) obj).x == this.x && ((XY) obj).y == this.y)
			return true;
		return false;
	}
	//Completely secure and cromulent hashing algorithm.
	@Override
	public int hashCode()
	{
		return ((x + 1) * 49 + 8 * y - 9) - 46;
	}
	@Override
	public String toString()
	{
		return ("[" + x + ", " + y + "]");
	}
}
