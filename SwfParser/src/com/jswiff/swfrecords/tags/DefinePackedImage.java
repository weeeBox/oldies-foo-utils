package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class DefinePackedImage extends DefinitionTag
{
	private int imageId;	

	public DefinePackedImage()
	{
		code = TagConstants.DEFINE_PACKED_IMAGE;
	}
	
	public DefinePackedImage(int characterId, int imageId)
	{
		this();
		this.characterId = characterId;
		this.imageId = imageId;
	}
	
	@Override
	protected void writeData(OutputBitStream outStream) throws IOException
	{
		outStream.writeUI16(characterId);
		outStream.writeUI16(imageId);
	}

	@Override
	void setData(byte[] data) throws IOException
	{
		InputBitStream is = new InputBitStream(data);
		characterId = is.readUI16();
		imageId = is.readUI16();
	}
}
