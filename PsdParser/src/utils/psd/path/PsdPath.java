package utils.psd.path;

import java.util.ArrayList;
import java.util.List;



public class PsdPath
{
	private boolean closed;
	
	private List<BezierKnot> knots;

	public PsdPath(int numKnots, boolean closed)
	{
		this.closed = closed;
		knots = new ArrayList<BezierKnot>(numKnots);
	}

	public void addKnot(BezierKnot knot)
	{
		knots.add(knot);
	}
	
	public List<BezierKnot> getKnots()
	{
		return knots;
	}
	
	public int knotsCount()
	{
		return knots.size();
	}

	public boolean isClosed()
	{
		return closed;
	}
}
