package com.jswiff.swfrecords.tags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class SymbolClass extends Tag 
{
	private Map<String, SymbolClassEntry> pairs;
	
	public SymbolClass() 
	{
		code = TagConstants.SYMBOL_CLASS;
		pairs = new LinkedHashMap<String, SymbolClassEntry>();
	}

	@Override
	void setData(byte[] data) throws IOException 
	{
		InputBitStream inStream = new InputBitStream(data);
		int numSymbols = inStream.readUI16();
		
		for (int symbolIndex = 0; symbolIndex < numSymbols; symbolIndex++) 
		{
			int tagId = inStream.readUI16();
			String className = inStream.readString();
			pairs.put(className, new SymbolClassEntry(tagId, className));
		}
	}

	@Override
	protected void writeData(OutputBitStream outStream) throws IOException 
	{
		int numSymbols = pairs.size();
		outStream.writeUI16(numSymbols);
		
		Set<Entry<String, SymbolClassEntry>> entrySet = pairs.entrySet();
		
		for (Entry<String, SymbolClassEntry> e : entrySet) 
		{
			String className = e.getKey();
			int tagId = e.getValue().getTag();
			
			outStream.writeUI16(tagId);
			outStream.writeString(className);
		}
	}
	
	public void addSymbolPair(int tagId, String className)
	{
		pairs.put(className, new SymbolClassEntry(tagId, className));
	}
	
	public Map<String, SymbolClassEntry> getPairs()
	{
		return pairs;
	}

	public static class SymbolClassEntry
	{
		private int tag;
		private String name;

		public SymbolClassEntry(int tag, String name) 
		{
			this.tag = tag;
			this.name = name;
		}

		public int getTag() 
		{
			return tag;
		}

		public String getName() 
		{
			return name;
		}

		@Override
		public int hashCode() 
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + name.hashCode();
			result = prime * result + tag;
			return result;
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SymbolClassEntry other = (SymbolClassEntry) obj;
			if (!name.equals(other.name))
				return false;
			if (tag != other.tag)
				return false;
			return true;
		}
	}
}
