package utils.psd;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class PsdInputStream extends InputStream
{

	private int pos;
	private final InputStream in;

	public PsdInputStream(InputStream in)
	{
		this.in = in;
		pos = 0;
	}

	@Override
	public int available() throws IOException
	{
		return in.available();
	}

	@Override
	public void close() throws IOException
	{
		in.close();
	}

	@Override
	public synchronized void mark(int readlimit)
	{
		in.mark(readlimit);
	}

	@Override
	public synchronized void reset() throws IOException
	{
		in.reset();
	}

	@Override
	public boolean markSupported()
	{
		return in.markSupported();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		int res = in.read(b, off, len);
		if (res != -1)
		{
			pos += res;
		}
		return res;
	}

	@Override
	public int read(byte[] b) throws IOException
	{
		int res = in.read(b);
		if (res != -1)
		{
			pos += res;
		}
		return res;
	}

	@Override
	public int read() throws IOException
	{
		int res = in.read();
		if (res != -1)
		{
			pos++;
		}
		return res;
	}

	@Override
	public long skip(long n) throws IOException
	{
		long skip = in.skip(n);
		pos += skip;
		return skip;
	}

	public String readString(int len) throws IOException
	{
		// read string of specified length
		byte[] bytes = new byte[len];
		read(bytes);
		return new String(bytes, "ISO-8859-1");
	}

	public int readBytes(byte[] bytes, int n) throws IOException
	{
		// read multiple bytes from input
		if (bytes == null)
			return 0;
		int r = 0;
		r = read(bytes, 0, n);
		if (r < n)
		{
			throw new IOException("format error. readed=" + r + " needed=" + n);
		}
		return r;
	}

	public byte readByte() throws IOException
	{
		int ch = read();
		if (ch < 0)
		{
			throw new EOFException();
		}
		return (byte) (ch);
	}

	public short readInt16() throws IOException
	{
		int ch1 = read();
		int ch2 = read();
		if ((ch1 | ch2) < 0)
		{
			throw new EOFException();
		}
		return (short) ((ch1 << 8) + (ch2 << 0));
	}
	
	public int readUInt16() throws IOException
	{
		return ((int) readInt16()) & 0xffff;
	}

	public int readInt32() throws IOException
	{
		int ch1 = read();
		int ch2 = read();
		int ch3 = read();
		int ch4 = read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
		{
			throw new EOFException();
		}
		return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}

	public boolean readBoolean() throws IOException
	{
		int ch = read();
		if (ch < 0)
		{
			throw new EOFException();
		}
		return (ch != 0);
	}

	public final long readLong() throws IOException
	{
		int c1 = read();
		int c2 = read();
		int c3 = read();
		int c4 = read();
		int c5 = read();
		int c6 = read();
		int c7 = read();
		int c8 = read();
		return (((long) c1 << 56) + ((long) (c2 & 255) << 48) + ((long) (c3 & 255) << 40) + ((long) (c4 & 255) << 32) + ((long) (c5 & 255) << 24) + ((c6 & 255) << 16) + ((c7 & 255) << 8) + ((c8 & 255) << 0));
	}

	public final float readFixedFloat() throws IOException
	{
		float intVal = readUInt16();
		float val = readUInt16();
		return (intVal + val / 0xffff) / 0xff;
	}
	
	public final float readFloat() throws IOException
	{
		return Float.intBitsToFloat(readInt32());
	}
	
	public final double readDouble() throws IOException
	{
		return Double.longBitsToDouble(readLong());
	}

	public int skipBytes(int n) throws IOException
	{
		int total = 0;
		int cur = 0;
		while ((total < n) && ((cur = (int) skip(n - total)) > 0))
		{
			total += cur;
		}
		return total;
	}

	public int getPos()
	{
		return pos;
	}

}
