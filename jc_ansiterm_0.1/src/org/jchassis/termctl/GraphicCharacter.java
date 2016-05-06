/***************************************************************************

    Copyright (C) 2003-2005 Sam Stainsby. All rights reserved.

    This file is part of the JChassis Project.

    JChassis is free software; you can redistribute it and/or
    modify it under the terms of version 2.1 of the GNU Lesser
    General Public License as published by the Free Software Foundation.

    JChassis is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to:

        Free Software Foundation, Inc.,
        59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

    At the time of writing, the license can also be found on the
    world-wide web at:

        http://www.fsf.org/licenses/lgpl.txt

    JChassis project management can be contacted via email sent to
    sjstainsby@yahoo.com.au.

***************************************************************************/

package org.jchassis.termctl;


/**
 * Represents graphics characters used in character terminals.
 * The main use of these characters is for drawing boxes and grids.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */public class GraphicCharacter {
    
    /** a blank space */
    public static final GraphicCharacter BLANK
        = new GraphicCharacter("BLANK");

    /** the lower, right corner of a line box */
    public static final GraphicCharacter LOWER_RIGHT_CORNER
        = new GraphicCharacter("LOWER_RIGHT_CORNER");
    
    /** the upper, right corner of a line box */
    public static final GraphicCharacter UPPER_RIGHT_CORNER
        = new GraphicCharacter("UPPER_RIGHT_CORNER");
    
    /** the upper, left corner of a line box */
    public static final GraphicCharacter UPPER_LEFT_CORNER
        = new GraphicCharacter("UPPER_LEFT_CORNER");
    
    /** the lower, left corner of a line box */
    public static final GraphicCharacter LOWER_LEFT_CORNER
        = new GraphicCharacter("LOWER_LEFT_CORNER");
    
    /** two perpendicular lines (used for drawing grids) */
    public static final GraphicCharacter CROSSING_LINES
        = new GraphicCharacter("CROSSING_LINES");

    /** horizontal, middle line (used for drawing grids and boxes) */
    public static final GraphicCharacter MIDDLE_LINE
        = new GraphicCharacter("MIDDLE_LINE");
    
    /** a T-shape, rotated 90 degrees counter-clockwise (used for drawing 
        grids) */
    public static final GraphicCharacter LEFT_T
        = new GraphicCharacter("LEFT_T");
    
    /** a T-shape, rotated 90 degrees clockwise (used for drawing grids) */
    public static final GraphicCharacter RIGHT_T
        = new GraphicCharacter("RIGHT_T");
    
    /** a upside-down T-shape (used for drawing grids) */
    public static final GraphicCharacter BOTTOM_T
        = new GraphicCharacter("BOTTOM_T");
    
    /** a T-shape (used for drawing grids) */
    public static final GraphicCharacter TOP_T
        = new GraphicCharacter("TOP_T");
    
    /** vertical, centered line (used for drawing grids and boxes) */
    public static final GraphicCharacter VERTICAL_LINE
        = new GraphicCharacter("VERTICAL_LINE");
    
    private final String name;
        
    private GraphicCharacter(String name) {
        this.name = name;
    }
    
    /**
     * Gets the name of this graphic sharacter.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }
}
