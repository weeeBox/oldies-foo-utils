package utils.swf;

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

public abstract class AnimationRes
{
	private int characterId;
	
	private int type;
	
	private String name;

	public AnimationRes(int type, int characterId)
	{
		this.type = type;
		this.characterId = characterId;
	}

	public int getCharacterId()
	{
		return characterId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void write(OutputBitStream out) throws IOException
	{
		out.writeUI8(type);
		out.writeUI16(characterId);
	}
}
