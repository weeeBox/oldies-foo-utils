package utils.pack;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Atlas 
{
	private int width;
	private int height;
	
	private List<AtlasImage> images;
	
	public Atlas() 
	{
		images = new ArrayList<AtlasImage>();
	}
	
	public void add(AtlasImage image)
	{
		int x = image.getX();
		int y = image.getY();
		int w = image.getWidth();
		int h = image.getHeight();
		if (x + w > width)
			width = x + w;
		if (y + h > height)
			height = y + h;
		
		images.add(image);
	}
	
	public BufferedImage createImage()
	{
		int realWidth = AtlasPacker.getApproximate(width);
		int realHeight = AtlasPacker.getApproximate(height);
		
		BufferedImage img = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = img.createGraphics();
		for (int i = 0; i < images.size(); ++i)
		{
			AtlasImage packed = images.get(i);
			g.drawImage(packed.getImage(), packed.getRealX(), packed.getRealY(), null);
		}
		g.dispose();
		
		return img;
	}

	public List<AtlasImage> getImages() 
	{
		return images;
	}
}
