package utils.psd.struct.res.blocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.psd.PsdInputStream;
import utils.psd.struct.res.PsdResBlock;

public class GridGuidesBlock extends PsdResBlock
{
	private List<PsdGuide> guides;
	
	public GridGuidesBlock(int id, String name, int size)
	{
		super(id, name, size);		
	}

	@Override
	public void read(PsdInputStream stream) throws IOException
	{
		stream.skip(12);
		
		int guidesCount = stream.readInt32();
		guides = new ArrayList<PsdGuide>(guidesCount);
		for (int guideIndex = 0; guideIndex < guidesCount; guideIndex++)
		{
			int location = stream.readInt32();
			int direction = stream.readByte();
			
			guides.add(new PsdGuide(location, direction == 0 ? true : false));
		}
	}

	public List<PsdGuide> getGuides()
	{
		return guides;
	}
}
