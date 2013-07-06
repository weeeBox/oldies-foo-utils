package utils.psd;

import java.io.IOException;

public class PsdChannelInfo
{
	private int id;
	
	private int length;

	public PsdChannelInfo(PsdInputStream stream) throws IOException
	{
		id = stream.readInt16();
		length = stream.readInt32();
	}

	public PsdChannelInfo(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public int getLength()
	{
		return length;
	}
}
