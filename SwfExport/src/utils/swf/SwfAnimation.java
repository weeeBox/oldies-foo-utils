package utils.swf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.pack.AtlasImage;
import utils.pack.AtlasPacker;

import com.jswiff.SWFDocument;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.tags.DefineAtlas;
import com.jswiff.swfrecords.tags.DefineBitsJPEG3;
import com.jswiff.swfrecords.tags.DefineBitsLossless2;
import com.jswiff.swfrecords.tags.DefinePackedImage;
import com.jswiff.swfrecords.tags.DefinitionTag;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagConstants;
import com.jswiff.util.ImageUtilities;

public class SwfAnimation
{
	private int framesCount;
	private int frameRate;
	
	private int width;
	private int height;

	private SWFLibrary library;
	private List<AtlasImage> packables;
	private List<Tag> tags;

	public SwfAnimation(int framesCount, int frameRate, int width, int height)
	{
		this.framesCount = framesCount;
		this.frameRate = frameRate;
		this.width = width;
		this.height = height;

		packables = new ArrayList<AtlasImage>();
		tags = new ArrayList<Tag>();
		library = new SWFLibrary();
	}
	
	public void init(List<Tag> swfTags) throws IOException
	{
		tags.clear();
		library.clear();
		
		for (Tag tag : swfTags)
		{
			if (tag instanceof DefinitionTag)
			{
				processDefinitionTag((DefinitionTag)tag);
			}
			else
			{
				processControlTag(tag);
			}
		}			
	}
	
	private void processDefinitionTag(DefinitionTag tag) throws IOException
	{
		switch (tag.getCode())
		{
			case TagConstants.DEFINE_BITS_LOSSLESS_2:
			{
				DefineBitsLossless2 lossless = (DefineBitsLossless2) tag;
				DefinePackedImage packedImage = new DefinePackedImage(lossless.getCharacterId(), packables.size());
				
				BufferedImage img = ImageUtilities.createImage(lossless.getZlibBitmapData(), lossless.getWidth(), lossless.getHeight());
				packables.add(new AtlasImage(img));
				library.addTag(packedImage);
				break;
			}
			case TagConstants.DEFINE_BITS_JPEG_3:
			{
				DefineBitsJPEG3 jpeg = (DefineBitsJPEG3) tag;
				DefinePackedImage packedImage = new DefinePackedImage(jpeg.getCharacterId(), packables.size());
				
				BufferedImage img = ImageUtilities.createImage(jpeg.getJpegData(), jpeg.getBitmapAlphaData());
				packables.add(new AtlasImage(img));
				library.addTag(packedImage);
			}
				break;
			
			case TagConstants.DEFINE_SHAPE:
			case TagConstants.DEFINE_SHAPE_2:
			case TagConstants.DEFINE_SHAPE_3:
			case TagConstants.DEFINE_SHAPE_4:
			case TagConstants.DEFINE_SPRITE:
				library.addTag(tag);
				break;
			
			case TagConstants.DEFINE_BITS_LOSSLESS:
			case TagConstants.DEFINE_BITS_JPEG_2:
				throw new Error("implement me: " + tag + " " + ((DefinitionTag)tag).getCharacterId());
			
			default:
				System.out.println("Ignored: " + tag);
				break;
		}
	}

	private void processControlTag(Tag tag)
	{
		switch (tag.getCode())
		{
			case TagConstants.PLACE_OBJECT:
			case TagConstants.PLACE_OBJECT_2:
			case TagConstants.PLACE_OBJECT_3:
			case TagConstants.REMOVE_OBJECT:
			case TagConstants.REMOVE_OBJECT_2:
			case TagConstants.SYMBOL_CLASS:
			case TagConstants.SHOW_FRAME:
				tags.add(tag);
				break;

			default:
				System.out.println("Ignored: " + tag);
				break;
		}
	}
	
	public SWFDocument createDocument()
	{
		SWFDocument doc = new SWFDocument();
		doc.setCompressed(false);
		doc.setFrameCount(framesCount);
		doc.setFrameRate(frameRate);
		doc.setFrameSize(new Rect(0, width * 20, 0, height * 20));
		
		doc.addTag(pack("test"));
		
		for (Tag tag : library.getTags())
		{
			doc.addTag(tag);
		}
		doc.addTags(tags);
		
		return doc;
	}
	
	private DefineAtlas pack(String name)
	{		
		AtlasPacker packer = new AtlasPacker();
		packer.doPacking(packables, AtlasPacker.FAST);
		
		DefineAtlas defineAtlas = new DefineAtlas("tex_" + name);
		for (AtlasImage packable : packables)
		{
			defineAtlas.add(packable.getRealX(), packable.getRealY(), packable.getRealWidth(), packable.getRealHeight());
		}
		
		return defineAtlas;
	}
	
	public List<AtlasImage> getPackables()
	{
		return packables;
	}

	public int getFramesCount()
	{
		return framesCount;
	}
	
	public int getFrameRate()
	{
		return frameRate;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
