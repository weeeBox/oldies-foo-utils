package utils.swf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jswiff.listeners.SWFListener;
import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.SymbolClass;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.SymbolClass.SymbolClassEntry;

public class TagPrinter extends SWFListener
{
	@Override
	public void processTag(Tag tag, long streamOffset)
	{
		System.out.println(tag);
		if (tag instanceof DefineSprite)
		{
			DefineSprite defineSprite = (DefineSprite) tag;
			List<Tag> controlTags = defineSprite.getControlTags();
			for (Tag t : controlTags)
			{
				System.out.println("\t" + t);
			}
		}
		else if (tag instanceof SymbolClass)
		{
			SymbolClass symbol = (SymbolClass) tag;
			Map<String, SymbolClassEntry> pairs = symbol.getPairs();
			Set<Entry<String, SymbolClassEntry>> entrySet = pairs.entrySet();
			for (Entry<String, SymbolClassEntry> e : entrySet)
			{
				SymbolClassEntry se = e.getValue();
				System.out.println("\t" + e.getKey() + ":" + se.getName() + "=" + se.getTag());
			}
		}
	}
}
