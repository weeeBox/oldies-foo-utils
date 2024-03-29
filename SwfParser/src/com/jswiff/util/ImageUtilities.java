/*
 * JSwiff is an open source Java API for Macromedia Flash file generation
 * and manipulation
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jswiff.util;

import com.jswiff.io.InputBitStream;
import com.jswiff.swfrecords.AlphaBitmapData;
import com.jswiff.swfrecords.RGBA;
import com.jswiff.swfrecords.ZlibBitmapData;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 * Provides utility methods for image processing.
 * 
 * @author <a href="mailto:ralf@terdic.de">Ralf Terdic</a>
 */
public class ImageUtilities
{
	/**
	 * Returns the format of an image (as string).
	 * 
	 * @param stream
	 *            image source stream
	 * 
	 * @return format stream (e.g. "JPEG")
	 * 
	 * @throws IOException
	 *             if an I/O error occured
	 */
	public static String getFormat(InputStream stream) throws IOException
	{
		ImageInputStream iis = ImageIO.createImageInputStream(stream);
		Iterator iter = ImageIO.getImageReaders(iis);
		if (!iter.hasNext())
		{
			throw new IOException("Unsupported image format!");
		}
		ImageReader reader = (ImageReader) iter.next();
		iis.close();
		return reader.getFormatName();
	}

	/**
	 * Returns the content of an image as RGBA array.
	 * 
	 * @param image
	 *            buffered image
	 * 
	 * @return image as RGBA array
	 */
	public static RGBA[] getRGBAArray(BufferedImage image)
	{
		BufferedImage argbImage;
		if (image.getType() != BufferedImage.TYPE_INT_ARGB)
		{
			argbImage = convertToARGB(image);
		}
		else
		{
			argbImage = image;
		}
		int height = argbImage.getHeight();
		int width = argbImage.getWidth();
		RGBA[] values = new RGBA[height * width];
		int i = 0;
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int rgb = argbImage.getRGB(x, y); // 0xaarrggbb
				int a = ((rgb & 0xff000000) >> 24) & 0xff;
				int r = ((rgb & 0xff0000) >> 16) & 0xff;
				int g = ((rgb & 0xff00) >> 8) & 0xff;
				int b = rgb & 0xff;
				if (a != 255)
				{
					// thx to Tom Rathbone for this fix
					float alpha = a / 255f;
					r *= alpha;
					g *= alpha;
					b *= alpha;
				}
				values[i++] = new RGBA(r, g, b, a);
			}
		}
		return values;
	}

	/**
	 * Loads an image from a stream.
	 * 
	 * @param stream
	 *            input stream
	 * 
	 * @return the loaded image
	 * 
	 * @throws IOException
	 *             if the image could not be loaded
	 */
	public static BufferedImage loadImage(InputStream stream) throws IOException
	{
		return ImageIO.read(stream);
	}

	/**
	 * Writes an image to an output stream as a JPEG file.
	 * 
	 * @param image
	 *            image to be written
	 * @param stream
	 *            target stream
	 * 
	 * @throws IOException
	 *             if an I/O error occured
	 */
	public static void saveImageAsJPEG(BufferedImage image, OutputStream stream) throws IOException
	{
		ImageIO.write(image, "jpg", stream);
	}

	/**
	 * Writes an image to an output stream as a JPEG file. The JPEG quality can
	 * be specified in percent.
	 * 
	 * @param image
	 *            image to be written
	 * @param stream
	 *            target stream
	 * @param qualityPercent
	 *            JPEG quality in percent
	 * 
	 * @throws IOException
	 *             if an I/O error occured
	 * @throws IllegalArgumentException
	 *             if qualityPercent not between 0 and 100
	 */
	public static void saveImageAsJPEG(BufferedImage image, OutputStream stream, int qualityPercent) throws IOException
	{
		if ((qualityPercent < 0) || (qualityPercent > 100))
		{
			throw new IllegalArgumentException("Quality out of bounds!");
		}
		float quality = qualityPercent / 100f;
		ImageWriter writer = null;
		Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
		if (iter.hasNext())
		{
			writer = (ImageWriter) iter.next();
		}
		ImageOutputStream ios = ImageIO.createImageOutputStream(stream);
		writer.setOutput(ios);
		ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
		iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwparam.setCompressionQuality(quality);
		writer.write(null, new IIOImage(image, null, null), iwparam);
		ios.flush();
		writer.dispose();
		ios.close();
	}

	/**
	 * Writes an image to an output stream as a PNG file.
	 * 
	 * @param image
	 *            image to be written
	 * @param stream
	 *            target stream
	 * 
	 * @throws IOException
	 *             if an I/O error occured
	 */
	public static void saveImageAsPNG(BufferedImage image, OutputStream stream) throws IOException
	{
		ImageIO.write(image, "png", stream);
	}

	public static BufferedImage createImage(ZlibBitmapData zlibBitmapData, int width, int height) throws IOException
	{
		if (zlibBitmapData instanceof AlphaBitmapData)
		{
			AlphaBitmapData data = (AlphaBitmapData) zlibBitmapData;
			RGBA[] pixels = data.getBitmapPixelData();
			int[] argb = new int[pixels.length];
			for (int i = 0; i < pixels.length; i++)
			{
				argb[i] = pixels[i].getARGB();
			}
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			img.setRGB(0, 0, width, height, argb, 0, width);

			return img;
		}
		else
		{
			throw new IOException("Can't read zlib data" + zlibBitmapData);
		}
	}
	
	public static BufferedImage createImage(byte[] jpegData, byte[] alphaData) throws IOException
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(jpegData);
		BufferedImage image = ImageIO.read(bis);
		if (image == null)
		{
			throw new IOException("Can't read jpeg image");
		}
		
		if (alphaData != null)
		{				
			int width = image.getWidth();
			int height = image.getHeight();
			int totalPixels = width * height;
			
			InputBitStream zStream = new InputBitStream(alphaData);
			zStream.enableCompression();
			byte[] alphaBytes = zStream.readBytes(totalPixels);
			zStream.close();
			
			int[] argb = new int[totalPixels];
			image.getRGB(0, 0, width, height, argb, 0, width);
			
			for (int i = 0; i < totalPixels; ++i)
			{
				int a = alphaBytes[i] & 0xff;
				float floatAlpha = a / 255.0f;
				
				int pixel = argb[i];
				int r = (int)(((pixel >> 16) & 0xff) * floatAlpha);
				int g = (int)(((pixel >> 8) & 0xff) * floatAlpha);
				int b = (int)(((pixel >> 0) & 0xff) * floatAlpha);
				argb[i] = (a << 24) | (r << 16) | (g << 8) | b;					
			}
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, width, height, argb, 0, width);
		}
		return image;
	}

	private static BufferedImage convertToARGB(BufferedImage srcImage)
	{
		BufferedImage newImage = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics bg = newImage.getGraphics();
		bg.drawImage(srcImage, 0, 0, null);
		bg.dispose();
		return newImage;
	}
}
