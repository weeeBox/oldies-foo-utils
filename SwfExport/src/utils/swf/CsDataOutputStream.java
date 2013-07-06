package utils.swf;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;

public class CsDataOutputStream extends OutputStream implements DataOutput
{
	private OutputStream out;
	private byte[] writeBuffer;

	public CsDataOutputStream(OutputStream out)
	{
		this.out = out;
		writeBuffer = new byte[8];
	}

	@Override
	public void write(int b) throws IOException
	{
		out.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		out.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		out.write(b, off, len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException
	{
		out.write(v ? 1 : 0);
	}

	@Override
	public void writeByte(int v) throws IOException
	{
		out.write(v & 0xff);
	}

	@Override
	public void writeShort(int v) throws IOException
	{
		writeBuffer[1] = (byte) (v >>> 8);
		writeBuffer[0] = (byte) (v >>> 0);
		out.write(writeBuffer, 0, 2);
	}

	@Override
	public void writeChar(int v) throws IOException
	{
		out.write(v & 0xff);
	}

	@Override
	public void writeInt(int v) throws IOException
	{
		writeBuffer[3] = (byte) (v >>> 24);
		writeBuffer[2] = (byte) (v >>> 16);
		writeBuffer[1] = (byte) (v >>> 8);
		writeBuffer[0] = (byte) (v >>> 0);
		out.write(writeBuffer, 0, 4);
	}

	@Override
	public void writeLong(long v) throws IOException
	{
		writeBuffer[7] = (byte) (v >>> 56);
		writeBuffer[6] = (byte) (v >>> 48);
		writeBuffer[5] = (byte) (v >>> 40);
		writeBuffer[4] = (byte) (v >>> 32);
		writeBuffer[3] = (byte) (v >>> 24);
		writeBuffer[2] = (byte) (v >>> 16);
		writeBuffer[1] = (byte) (v >>> 8);
		writeBuffer[0] = (byte) (v >>> 0);
		out.write(writeBuffer, 0, 8);
	}

	@Override
	public void writeFloat(float v) throws IOException
	{
		writeInt(Float.floatToIntBits(v));
	}

	@Override
	public void writeDouble(double v) throws IOException
	{
		writeLong(Double.doubleToLongBits(v));
	}

	@Override
	public void writeBytes(String s) throws IOException
	{
		int len = s.length();
		for (int i = 0; i < len; i++)
		{
			out.write((byte) s.charAt(i));
		}
	}

	@Override
	public void writeChars(String s) throws IOException
	{
		int len = s.length();
		for (int i = 0; i < len; i++)
		{
			int v = s.charAt(i);
			writeChar(v);
		}
	}

	@Override
	public void writeUTF(String str) throws IOException
	{
		write(str.length());

		int strlen = str.length();
		int utflen = 0;
		int c, count = 0;

		/* use charAt instead of copying String to char array */
		for (int i = 0; i < strlen; i++)
		{
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F))
			{
				utflen++;
			}
			else if (c > 0x07FF)
			{
				utflen += 3;
			}
			else
			{
				utflen += 2;
			}
		}

		if (utflen > 65535)
			throw new UTFDataFormatException("encoded string too long: " + utflen + " bytes");

		byte[] bytearr = null;
		bytearr = new byte[utflen];

		int i = 0;
		for (i = 0; i < strlen; i++)
		{
			c = str.charAt(i);
			if (!((c >= 0x0001) && (c <= 0x007F)))
				break;
			bytearr[count++] = (byte) c;
		}

		for (; i < strlen; i++)
		{
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F))
			{
				bytearr[count++] = (byte) c;

			}
			else if (c > 0x07FF)
			{
				bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
				bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
				bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
			}
			else
			{
				bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
				bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
			}
		}
		out.write(bytearr, 0, utflen);
	}
}
