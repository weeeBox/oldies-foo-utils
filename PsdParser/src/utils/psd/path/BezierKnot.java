package utils.psd.path;

import utis.psd.stuff.Point;

public class BezierKnot
{
	private Point control1;
	private Point anchor;
	private Point control2;
	private boolean linked;

	public BezierKnot(Point control1, Point anchor, Point control2, boolean linked)
	{
		this.control1 = control1;
		this.anchor = anchor;
		this.control2 = control2;
		this.linked = linked;
	}

	public Point getControl1()
	{
		return control1;
	}

	public Point getAnchor()
	{
		return anchor;
	}

	public Point getControl2()
	{
		return control2;
	}

	public boolean isLinked()
	{
		return linked;
	}
}
