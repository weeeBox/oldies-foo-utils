package utils.psd.struct;

import java.io.IOException;

import utils.psd.PsdInputStream;

public class PsdColorModeSection
{
	private byte[] colorData;

	public PsdColorModeSection(PsdInputStream stream) throws IOException
	{
		int length = stream.readInt32();
		colorData = new byte[length];
		stream.read(colorData);
	}

	public byte[] getData()
	{
		return colorData;
	}
}
