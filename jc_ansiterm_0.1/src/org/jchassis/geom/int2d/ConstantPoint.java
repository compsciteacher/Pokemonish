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

package org.jchassis.geom.int2d;

/**
 * An immutable point.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class ConstantPoint implements Point {

    private final int x;

    private final int y;

    /**
     * Creates a new point.
     *
     * @param x the x (horizontal) component of the point
     * @param y the y (vertical) component of the point
     */
    public ConstantPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // javadoc inherited
    public int getX() {
        return this.x;
    }

    // javadoc inherited
    public int getY() {
        return this.y;
    }

    public String toString() {
        return "[" + this.x + "," + this.y + "]";
    }

    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            return ((p.getX() == this.x) && (p.getY() == this.y));
        } else {
            return false;
        }
    }
}
