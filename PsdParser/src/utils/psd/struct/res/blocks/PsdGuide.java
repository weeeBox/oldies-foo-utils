package utils.psd.struct.res.blocks;

public class PsdGuide
{
	private float location;
	private boolean vertical;
	
	public PsdGuide(int location, boolean vertical)
	{
		this.location = location / 32.0f;
		this.vertical = vertical;
	}
	
	public float getLocation()
	{
		return location;
	}
	
	public boolean isVertical()
	{
		return vertical;
	}
	
	public boolean isHorizontal()
	{
		return !vertical;
	}
	
	@Override
	public String toString()
	{
		return getClass().getName() + ": location=" + location + " " + (vertical ? "vertical" : "horizontal");
	}
}
