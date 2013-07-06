package utils.psd.struct.res;

import java.io.IOException;

import utils.psd.PsdInputStream;

public abstract class PsdResBlock
{
	private int id;
	private String name;
	private int size;

	public PsdResBlock(int id, String name, int size)
	{
		this.id = id;
		this.name = name;
		this.size = size;
	}
	
	public abstract void read(PsdInputStream stream) throws IOException;

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getSize()
	{
		return size;
	}
}
