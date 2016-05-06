/***************************************************************************

    Copyright (C) 2004 Sam Stainsby. All rights reserved.

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

package org.jchassis.termctl.ansi;

import org.jchassis.geom.int2d.*;
import org.jchassis.termctl.*;


/**
 * tests the motion of the cursor under the ANSI/VT100TerminalController
 * implementation
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2004 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class TestAnsiTermctlCursorMotion extends TestAnsiTermctlBase {

    public static void main(String[] args) throws Exception {
        TestAnsiTermctlBase test = new TestAnsiTermctlCursorMotion();
        test.init();
        test.run();
        test.end();
    }

    public void run() throws Exception {

        drawSquareSpiral(8);

        getTerminal().setCursorPosition(new ConstantPoint(9, 1));

        drawSquareSpiral(8);

        getTerminal().moveCursor(Direction.UP);
        getTerminal().putCharacters("ABCDEFG");

        getTerminal().setCursorPosition(new ConstantPoint(0,10));
    }

    // Fill an 8x8 square area in a spiralling way.
    // Example:
    //
    // 00000001
    // 34444451
    // 37888951
    // 37123951
    // 37154951
    // 37100051
    // 37666661
    // 32222222
    //
    // The cursor should return to sit on the top, left corner of the
    // square, after the square has been drawn.
    public void drawSquareSpiral(int size) throws Exception {

        int length = size - 1;
        int ch = 0; // the single digit to print

        Point originalPosition = getTerminal().getCursorPosition();

        do {
            // this for loop completes one circuit of the spiral, with
            // the cursor ending up in the original position

            for (int direction = 0; direction < 4; direction++) {

                Direction d;

                if (direction == 0) {
                    d = Direction.RIGHT;
                } else if (direction == 1) {
                    d = Direction.DOWN;
                } else if (direction == 2) {
                    d = Direction.LEFT;
                } else {
                    d = Direction.UP;
                }

                for (int offset = 0; offset < length; offset++) {

                    // print character then reverse cursor back to
                    // original point

                    getTerminal().putCharacters(Integer.toString(ch));
                    getTerminal().moveCursor(Direction.LEFT);

                    // now move in the current direction of the spiral arm

                    getTerminal().moveCursor(d);

                }

                ch++;
                if (ch == 10) {
                    ch = 0;
                }
            }

            length = length - 2;

            // move the cursor to the start of a new circuit

            getTerminal().moveCursor(Direction.RIGHT);
            getTerminal().moveCursor(Direction.DOWN);

        } while (length > 0);

        getTerminal().setCursorPosition(originalPosition);
    }
}
