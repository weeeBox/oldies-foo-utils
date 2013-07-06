package com.jswiff.swfrecords.tags.interfaces;

public interface IPlaceObject {

	/**
	 * Sets the ID of the character to be placed to the display list.
	 *
	 * @param characterId ID of character to be placed
	 */
	public abstract void setCharacterId(int characterId);

	/**
	 * Returns the ID of the character to be placed to the display list.
	 *
	 * @return character ID
	 */
	public abstract int getCharacterId();

}