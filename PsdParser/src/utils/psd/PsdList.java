package utils.psd;

import java.io.IOException;
import java.util.ArrayList;

class PsdList extends ArrayList<Object>
{
	private static final long serialVersionUID = 1L;

	public PsdList(PsdInputStream stream) throws IOException
	{
		int itemsCount = stream.readInt32();
		for (int i = 0; i < itemsCount; i++)
		{
			String type = stream.readString(4);
			if (type.equals("Objc"))
			{
				add(new PsdDescriptor(stream));
			}
			else if (type.equals("VlLs"))
			{
				add(new PsdList(stream));
			}
			else if (type.equals("doub"))
			{
				double val = stream.readDouble();
				add(val);
			}
			else if (type.equals("long"))
			{
				int val = stream.readInt32();
				add(val);
			}
			else if (type.equals("bool"))
			{
				boolean val = stream.readBoolean();
				add(val);
			}
			else
			{
				throw new IOException("UNKNOWN TYPE");
			}
		}
	}
}
