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

package com.jswiff.swfrecords.actions;

/**
 * <p>
 * Performs a boolean NOT (<code>!</code>) operation.
 * </p>
 * 
 * <p>
 * Note: Before SWF 5, 1 was used instead of true and 0 instead of false.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop value</code><br>
 * <code>push result</code> (1 if value is 0, otherwise 0. SWF 5 and newer:
 * <code>true</code> if <code>value</code> is <code>true</code>, otherwise
 * <code>false</code>)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>!</code> operator
 * </p>
 *
 * @since SWF 4
 */
public final class Not extends Action {
  /**
   * Creates a new Not action.
   */
  public Not() {
    code = ActionConstants.NOT;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"Not"</code>
   */
  public String toString() {
    return "Not";
  }
}
