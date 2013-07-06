package utils.swf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.listeners.SWFDocumentReader;
import com.jswiff.swfrecords.Rect;
import com.jswiff.util.Convert;

public class AnimationReader
{		
	public SwfAnimation read(File input) throws IOException
	{
		SWFDocument document = readDocument(input);
		int framesCount = document.getFrameCount();
		int frameRate = document.getFrameRate();
		Rect frameSize = document.getFrameSize();
		int width = (int)Convert.twips2pixels(frameSize.getXMax() - frameSize.getXMin());
		int height = (int)Convert.twips2pixels(frameSize.getYMax() - frameSize.getYMin());
		SwfAnimation animation = new SwfAnimation(framesCount, frameRate, width, height);
		animation.init(document.getTags());
		return animation;
	}
	
	private SWFDocument readDocument(File input) throws IOException
	{
		FileInputStream stream = null;
		try
		{
			stream = new FileInputStream(input);
			SWFDocumentReader docReader = new SWFDocumentReader();
			SWFReader reader = new SWFReader(stream);
			reader.addListener(new TagPrinter());
			reader.addListener(docReader);
			reader.read();
			
			SWFDocument document = docReader.getDocument();
			return document;
		}
		finally
		{
			if (stream != null)
				stream.close();
		}
	}
}
