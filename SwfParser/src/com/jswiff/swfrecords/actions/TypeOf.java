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
 * This action returns the type of a specified item as a string. The possible
 * types are:
 * 
 * <ul>
 * <li>
 * "number"
 * </li>
 * <li>
 * "boolean"
 * </li>
 * <li>
 * "string"
 * </li>
 * <li>
 * "function"
 * </li>
 * <li>
 * "object"
 * </li>
 * <li>
 * "movieclip"
 * </li>
 * <li>
 * "null"
 * </li>
 * <li>
 * "undefined"
 * </li>
 * </ul>
 * </p>
 * 
 * <p>
 * Performed stack operations:<br><code>pop item<br> push type</code>
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>typeof()</code>
 * </p>
 *
 * @since SWF 5
 */
public final class TypeOf extends Action {
  /**
   * Creates a new TypeOf action.
   */
  public TypeOf() {
    code = ActionConstants.TYPE_OF;
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"TypeOf"</code>
   */
  public String toString() {
    return "TypeOf";
  }
}
