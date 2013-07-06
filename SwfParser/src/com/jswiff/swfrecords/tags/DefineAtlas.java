package com.jswiff.swfrecords.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

class ImageInfo
{
	private int x;
	private int y;
	private int widht;
	private int height;

	public ImageInfo(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.widht = width;
		this.height = height;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return widht;
	}

	public int getHeight()
	{
		return height;
	}
}

public class DefineAtlas extends Tag
{
	private String texture;
	
	private List<ImageInfo> images;

	public DefineAtlas()
	{
		code = TagConstants.DEFINE_ATLAS;		
	}
	
	public DefineAtlas(String texture)
	{
		code = TagConstants.DEFINE_ATLAS;
		images = new ArrayList<ImageInfo>();
		this.texture = texture;
	}
	
	public void add(int x, int y, int width, int height)
	{
		images.add(new ImageInfo(x, y, width, height));
	}

	public String getTexture()
	{
		return texture;
	}

	public List<ImageInfo> getImages()
	{
		return images;
	}
	
	@Override
	protected void writeData(OutputBitStream outStream) throws IOException
	{
		outStream.writeString(texture);
		outStream.writeUI16(images.size());
		for (ImageInfo img : images)
		{
			outStream.writeUI16(img.getX());
			outStream.writeUI16(img.getY());
			outStream.writeUI16(img.getWidth());
			outStream.writeUI16(img.getHeight());
		}
	}

	@Override
	void setData(byte[] data) throws IOException
	{
		InputBitStream stream = new InputBitStream(data);
		texture = stream.readString();
		int imagesCount = stream.readUI16();
		images = new ArrayList<ImageInfo>();
		for (int imageIndex = 0; imageIndex < imagesCount; imageIndex++)
		{
			int x = stream.readUI16();
			int y = stream.readUI16();
			int width = stream.readUI16();
			int height = stream.readUI16();
			
			images.add(new ImageInfo(x, y, width, height));
		}
		stream.close();
	}
}
