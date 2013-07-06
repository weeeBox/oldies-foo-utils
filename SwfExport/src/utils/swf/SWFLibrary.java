package utils.swf;

import java.util.ArrayList;
import java.util.List;

import com.jswiff.swfrecords.tags.DefinitionTag;

public class SWFLibrary
{
	private List<DefinitionTag> tags;
	
	public SWFLibrary()
	{
		tags = new ArrayList<DefinitionTag>();
	}
	
	public void addTag(DefinitionTag tag)
	{
		if (getTag(tag.getCharacterId()) != null)
			throw new IllegalArgumentException("Tag with character id " + tag.getCharacterId() + " already in library");
		
		tags.add(tag);
	}
	
	public boolean containsTag(int characterId)
	{
		return getTag(characterId) != null;
	}
	
	public DefinitionTag getTag(int characterId)
	{
		for (DefinitionTag tag : tags)
		{
			if (tag.getCharacterId() == characterId)
			{
				return tag;
			}
		}
		return null;
	}

	public List<DefinitionTag> getTags()
	{
		return tags;
	}
	
	public void clear()
	{
		tags.clear();
	}

	public int size()
	{
		return tags.size();
	}
}
