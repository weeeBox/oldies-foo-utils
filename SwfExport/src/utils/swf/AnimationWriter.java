package utils.swf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import utils.pack.Atlas;
import utils.pack.AtlasImage;
import utils.pack.AtlasPacker;
import utils.pack.FileUtils;
import utils.pack.Packable;

import com.jswiff.SWFDocument;
import com.jswiff.SWFWriter;

public class AnimationWriter
{
	public void write(SwfAnimation animation, File file) throws IOException
	{
		String name = FileUtils.getFilenameNoExt(file);
		File textureFile = new File(file.getParent(), "tex_" + name + ".png");
		
		FileOutputStream fos = new FileOutputStream(file);
		pack(animation, textureFile);
		write(animation, fos);
		fos.close();
	}

	private void pack(SwfAnimation animation, File output) throws IOException
	{
		List<AtlasImage> packables = animation.getPackables();
		
		AtlasPacker packer = new AtlasPacker();
		packer.doPacking(packables, AtlasPacker.FAST);
		
		Atlas atlas = new Atlas();
		for (Packable packable : packables)
		{
			atlas.add((AtlasImage) packable);
		}
		BufferedImage texture = atlas.createImage();
		ImageIO.write(texture, "png", output);
	}

	private void write(SwfAnimation animation, OutputStream stream) throws IOException
	{
		SWFDocument doc = animation.createDocument();
		SWFWriter writer = new SWFWriter(doc, stream);
		writer.write();
	}
}
