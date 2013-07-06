package com.jswiff.swfrecords.tags.interfaces;

import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.ShapeWithStyle;

public interface IDefineShape 
{

	/**
	 * Sets the bounding box of the shape, i.e. the rectangle that completely
	 * encloses it.
	 *
	 * @param shapeBounds shape's bounds
	 */
	public void setShapeBounds(Rect shapeBounds); 

	/**
	 * Returns the bounding box of the shape, i.e. the rectangle that
	 * completely encloses it.
	 *
	 * @return shape's bounds
	 */
	public Rect getShapeBounds(); 

	/**
	 * Sets the shape's primitives and styles (i.e. lines and curves) in a
	 * <code>ShapeWithStyle</code> instance.
	 *
	 * @param shapes shape's primitives and styles
	 */
	public void setShapes(ShapeWithStyle shapes); 

	/**
	 * Returns the shape's primitives and styles (i.e. lines and curves) in a
	 * <code>ShapeWithStyle</code> instance.
	 *
	 * @return shape's primitives and styles
	 */
	public ShapeWithStyle getShapes(); 

}