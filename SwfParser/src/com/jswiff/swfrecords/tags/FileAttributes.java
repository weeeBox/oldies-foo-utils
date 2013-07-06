/*
 * JSwiff is an open source Java API for Macromedia Flash file generation
 * and manipulation
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jswiff.swfrecords.tags;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * This tag is used as of SWF 8 to define SWF properties like access mode and
 * the presence of metadata. Do NOT add this tag to your
 * <code>SWFDocument</code>, use its <code>setAccessMode</code> and
 * <code>setMetadata</code> methods instead!
 *
 * @see com.jswiff.SWFDocument#setAccessMode(byte)
 * @see com.jswiff.SWFDocument#setMetadata(String)
 * @since SWF 8
 */
public class FileAttributes extends Tag 
{
  /** Reserved: must be 0 */
  private static final int FLAG_RESERVED0 = 0;
  
  /** If 1, the SWF file uses hardware acceleration to blit graphics to the screen, 
   * where such acceleration is available. If 0, the SWF file will not use hardware
   * accelerated graphics facilities. Minimum file version is 10. */
  private static final int FLAG_USE_DIRECT_BLIT = 1;
  
  /** If 1, the SWF file uses GPU compositing features when drawing graphics, where such 
   * acceleration is available. If 0, the SWF file will not use hardware accelerated 
   * graphics facilities. Minimum file version is 10. */
  private static final int FLAG_USE_USE_GPU = 2;
  
  /** If 1, the SWF file contains the Metadata tag. If 0, the SWF file does not contain the 
   * Metadata tag. */
  private static final int FLAG_HAS_METADATA = 3;
  
  /** If 1, this SWF uses ActionScript 3.0. If 0, this SWF uses ActionScript 1.0 or 2.0. 
   * Minimum file format version is 9. */
  private static final int FLAG_ACTION_SCRIPT3 = 4;

  /** Reserved: must be 0 */
  private static final int FLAG_RESERVED1 = 5;
  
  /** Reserved: must be 0 */
  private static final int FLAG_RESERVED2 = 6;
  
  /** If 1, this SWF file is given network file access when loaded locally. If 0, this SWF 
   * file is given local file access when loaded locally. */
  private static final int FLAG_USE_NETWORK = 7;

  /** Total used flags */
  private static final int FLAGS_COUNT = 8;
  
  private boolean[] flagsArray;
  
  /**
   * Creates a new FileAttributes instance.
   */
  public FileAttributes() 
  {
    code = TagConstants.FILE_ATTRIBUTES;
    flagsArray = new boolean[FLAGS_COUNT];
  }

  /**
   * Specifies whether the SWF is granted network or local access.
   *
   * @param allowNetworkAccess true for network access, false for local access
   */
  public void setAllowNetworkAccess(boolean allowNetworkAccess) 
  {
    setFlag(FLAG_USE_NETWORK, allowNetworkAccess);
  }

  /**
   * Checks whether the SWF is granted network or local access.
   *
   * @return true for network access, false for local access
   */
  public boolean isAllowNetworkAccess() 
  {
    return hasFlag(FLAG_USE_NETWORK);
  }

  /**
   * Specifies whether the SWF contains metadata (in a Metadata tag).
   *
   * @param hasMetadata true if Metadata tag contained
   */
  public void setHasMetadata(boolean hasMetadata) 
  {
    setFlag(FLAG_HAS_METADATA, hasMetadata);
  }

  /**
   * Checks whether the SWF contains metadata (in a Metadata tag).
   *
   * @return true if Metadata tag contained
   */
  public boolean hasMetadata() 
  {
    return hasFlag(FLAG_HAS_METADATA);
  }

  protected void writeData(OutputBitStream outStream) throws IOException 
  {
	int flags = 0;
	for (int flagIndex = 0; flagIndex < FLAGS_COUNT; flagIndex++) 
    {
    	if (flagsArray[flagIndex])
    	{
    		flags |= 1 << flagIndex;
    	}
	}
    outStream.writeSI32(flags);
  }

  void setData(byte[] data) throws IOException 
  {
    InputBitStream inStream = new InputBitStream(data);
    int flags               = inStream.readSI32();
    
    for (int flagIndex = 0; flagIndex < FLAGS_COUNT; flagIndex++) 
    {
    	flagsArray[flagIndex] = ((flags & (1 << flagIndex)) != 0);
	}
  }
  
  public boolean isUseDirectBlit()
  {
	  return hasFlag(FLAG_USE_DIRECT_BLIT);
  }
  
  public void setUseDirectBlit(boolean flag)
  {
	  setFlag(FLAG_USE_DIRECT_BLIT, flag);
  }
  
  public boolean isUseGPU()
  {
	  return hasFlag(FLAG_USE_USE_GPU);
  }
  
  public void setUseGPU(boolean flag)
  {
	  setFlag(FLAG_USE_USE_GPU, flag);
  }
  
  public boolean isActionScript3()
  {
	  return hasFlag(FLAG_ACTION_SCRIPT3);
  }
  
  public void setActionScript3(boolean flag)
  {
	  setFlag(FLAG_ACTION_SCRIPT3, flag);
  }
  
  private boolean hasFlag(int flagIndex)
  {
	 assert flagIndex >= 0 && flagIndex < FLAGS_COUNT : flagIndex + "<" + FLAGS_COUNT;
	 return flagsArray[flagIndex];
  }
  
  private void setFlag(int flagIndex, boolean value)
  {
	  assert flagIndex >= 0 && flagIndex < FLAGS_COUNT : flagIndex + "<" + FLAGS_COUNT;
	  flagsArray[flagIndex] = value;
  }
}
