package utils.psd.struct;

import java.io.IOException;

import utils.psd.PsdInputStream;

public class PsdHeader
{
	private static final String SIGNATURE = "8BPS";
	private static final int VERSION = 1;
	
	private int numChannels;
	private int width;
	private int height;
	private int bitsPerChannel;
	private PsdColorMode colorMode;
	
	public PsdHeader(PsdInputStream stream) throws IOException
	{
		checkSignature(stream);
		checkVersion(stream);
		
		stream.skip(6); // reserved
		numChannels = stream.readInt16();
		height = stream.readInt32();
		width = stream.readInt32();
		bitsPerChannel = stream.readInt16();
		readColorMode(stream);
	}
	
	private void checkSignature(PsdInputStream stream) throws IOException
	{
		if (!PsdHelper.checkSignature(stream, SIGNATURE))
		{
			throw new IOException("Header signature failed");
		}
	}
	
	private void checkVersion(PsdInputStream stream) throws IOException
	{
		int version = stream.readInt16();
		if (version != VERSION)
		{
			throw new IOException("Bad PSD version: " + version);
		}
	}
	
	private void readColorMode(PsdInputStream stream) throws IOException
	{
		int colorModeOrdinal = stream.readInt16();
		PsdColorMode[] colorModes = PsdColorMode.values();
		if (colorModeOrdinal < 0 || colorModeOrdinal >= colorModes.length)
		{
			throw new IOException("Bad color model: " + colorModeOrdinal);
		}
		colorMode = colorModes[colorModeOrdinal];
	}

	public int getNumChannels()
	{
		return numChannels;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getBitsPerChannel()
	{
		return bitsPerChannel;
	}

	public PsdColorMode getColorMode()
	{
		return colorMode;
	}
}
