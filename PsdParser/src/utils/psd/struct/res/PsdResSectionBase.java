package utils.psd.struct.res;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.psd.PsdInputStream;
import utils.psd.struct.PsdHelper;

public abstract class PsdResSectionBase
{
	private static final String SIGNATURE = "8BIM";
	
	private int dataSize;
	
	private List<PsdResBlock> blocks;
	
	public PsdResSectionBase()
	{
		blocks = new ArrayList<PsdResBlock>();
	}
	
	public void read(PsdInputStream stream) throws IOException
	{
		dataSize = stream.readInt32();
		int startPos = stream.getPos();
		readBlocks(stream);
		stream.skip(dataSize - (stream.getPos() - startPos)); // skip the rest of section
	}

	private void readBlocks(PsdInputStream stream) throws IOException
	{
		int avail = dataSize;		
		while (avail > 0)
		{
			int startPos = stream.getPos();
			readBlock(stream);
			avail -= stream.getPos() - startPos;
		}
	}

	private void readBlock(PsdInputStream stream) throws IOException
	{
		checkSignature(stream);
		int blockId = stream.readInt16();
		String blockName = readName(stream);
		int blockSize = stream.readInt32();
		int startPos = stream.getPos();
		
		if (blockSize % 2 != 0)
			blockSize++;
		
		PsdResBlock block = blockFor(blockId, blockName, blockSize);
		block.read(stream);
		blocks.add(block);
		
		stream.skip(blockSize - (stream.getPos() - startPos)); // skip the rest of block if any
	}

	public PsdResBlock blockFor(int blockId, String blockName, int blockSize) throws IOException
	{
		return new UnknownResBlock(blockId, blockName, blockSize);
	}

	private void checkSignature(PsdInputStream stream) throws IOException
	{
		if (!PsdHelper.checkSignature(stream, SIGNATURE))
		{
			throw new IOException("Image resource signature failed");
		}
	}
	
	private String readName(PsdInputStream stream) throws IOException
	{
		int sizeOfName = stream.readByte() & 0xFF;
		if ((sizeOfName & 0x01) == 0)
		{
			sizeOfName++;
		}
		return stream.readString(sizeOfName);
	}

	public int blocksCount()
	{
		return blocks.size();
	}
	
	public List<PsdResBlock> getBlocks()
	{
		return blocks;
	}

	public int getDataSize()
	{
		return dataSize;
	}
}
