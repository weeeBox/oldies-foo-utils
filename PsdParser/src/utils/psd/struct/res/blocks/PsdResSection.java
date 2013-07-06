package utils.psd.struct.res.blocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.psd.struct.PsdResId;
import utils.psd.struct.res.PsdResBlock;
import utils.psd.struct.res.PsdResSectionBase;

public class PsdResSection extends PsdResSectionBase
{
	@Override
	public PsdResBlock blockFor(int id, String name, int size) throws IOException
	{
		switch (id)
		{
			case PsdResId.WORK_PATH:
				return new PathBlock(id, name, size);
				
			case PsdResId.GRUID_AND_GUIDES:
				return new GridGuidesBlock(id, name, size);
		}
		
		if (id >= PsdResId.PATH_INFO_FIRST && id <= PsdResId.PATH_INFO_LAST)
			return new PathBlock(id, name, size);
		
		return super.blockFor(id, name, size);
	}

	public List<PsdGuide> getGuides()
	{
		List<PsdGuide> guides = new ArrayList<PsdGuide>();
		
		List<PsdResBlock> blocks = getBlocks();
		for (PsdResBlock block : blocks)
		{
			if (block.getId() == PsdResId.GRUID_AND_GUIDES)
			{
				GridGuidesBlock guidesBlock = (GridGuidesBlock) block;
				return guidesBlock.getGuides();
			}
		}
		return guides;
	}
}
