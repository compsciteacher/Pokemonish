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
 * TODO
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2004 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class TestAnsiTermctlCornersAndEdges extends TestAnsiTermctlBase {

    public static void main(String[] args) throws Exception {
        TestAnsiTermctlBase test = new TestAnsiTermctlCornersAndEdges();
        test.init();
        test.run();
        test.end();
    }

    public void run() throws Exception {

        org.jchassis.termctl.TerminalController terminal = getTerminal();

        Dimension screenSize = terminal.getScreenSize();
        int maxX = screenSize.getWidth() - 1;
        int maxY = screenSize.getHeight() - 1;

        // mark the corners with "1", "2". "3" and "4" starting from upper
        // left and progressing clockwise around the edges of the screen

        terminal.setCursorPosition(new ConstantPoint(0, 0));
        terminal.putCharacters("1");

        terminal.setCursorPosition(new ConstantPoint(maxX, 0));
        terminal.putCharacters("2");

        terminal.setCursorPosition(new ConstantPoint(maxX, maxY));
        terminal.putCharacters("3");

        terminal.setCursorPosition(new ConstantPoint(0, maxY));
        terminal.putCharacters("4");

        // test that wrap is off

        terminal.setCursorPosition(new ConstantPoint(0, 8));
        for (int i = 0; i < 2*maxX; i++) {
            terminal.putCharacters("-");
        }
        terminal.putCharacters(">");
    }

    public void end() throws Exception {
        Thread.currentThread().sleep(8*1000);
    }
}
