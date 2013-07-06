package utils.swf;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

public class StreamTest
{
	public static void main(String[] args) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(new File("d:/file.bin"));
		CsDataOutputStream out = new CsDataOutputStream(fos);
		
//		out.writeInt(Integer.MAX_VALUE);
//		out.writeInt(Integer.MIN_VALUE);
//		out.writeShort(Short.MAX_VALUE);
//		out.writeShort(Short.MIN_VALUE);
//		out.writeByte(Byte.MAX_VALUE);
//		out.writeByte(Byte.MIN_VALUE);
//		out.writeBoolean(true);
//		out.writeBoolean(false);
//		out.writeChar('A');
//		byte[] buffer = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
//		out.write(buffer);
		out.writeFloat(10.156f);
		out.writeDouble(10.156);
		
		fos.close();
	}
}
