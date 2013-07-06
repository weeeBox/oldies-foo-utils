package utils.psd.struct.res;

import java.io.IOException;

import utils.psd.PsdInputStream;

public class UnknownResBlock extends PsdResBlock
{
	private byte[] data;
	
	public UnknownResBlock(int id, String name, int size)
	{
		super(id, name, size);
		data = new byte[size];
	}
	
	@Override
	public void read(PsdInputStream stream) throws IOException
	{
		stream.read(data);
	}

	public byte[] getData()
	{
		return data;
	}		
}
