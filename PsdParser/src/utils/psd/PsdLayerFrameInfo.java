package utils.psd;

class PsdLayerFrameInfo
{
	private int x;
	private int y;
	private boolean visible;

	public PsdLayerFrameInfo(int x, int y, boolean visible)
	{
		this.x = x;
		this.y = y;
		this.visible = visible;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

}
