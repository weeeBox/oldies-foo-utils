package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class DoABC extends Tag 
{
	private long flags;
	private String name;
	private byte[] ABCdata;

	public DoABC() 
	{
		code = TagConstants.DO_ABC;
	}
	
	@Override
	void setData(byte[] data) throws IOException 
	{
		InputBitStream inStream = new InputBitStream(data);
		flags = inStream.readUI32();
		name = inStream.readString();
		ABCdata = inStream.readBytes(inStream.available());
	}

	@Override
	protected void writeData(OutputBitStream outStream) throws IOException 
	{
		outStream.writeUI32(flags);
		outStream.writeString(name);
		outStream.writeBytes(ABCdata);
	}

	public long getFlags() 
	{
		return flags;
	}

	public String getName() 
	{
		return name;
	}

	public byte[] getABCdata() 
	{
		return ABCdata;
	}
}
