/***************************************************************************

    Copyright (C) 2004-2005 Sam Stainsby. All rights reserved.

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
 * Copyright &copy; 2004-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class TestAnsiTermctlCharacterColors extends TestAnsiTermctlBase {

    public static void main(String[] args) throws Exception {
        TestAnsiTermctlBase test = new TestAnsiTermctlCharacterColors();
        test.init();
        test.run();
        test.end();
    }

    public void run() throws Exception {

        TerminalController terminal = getTerminal();

        ColoredCharacterTerminal colors = (ColoredCharacterTerminal) terminal;
        StyledCharacterTerminal styles = (StyledCharacterTerminal) terminal;

        int row = 0;
        Point rowStart;

        // loop over the eight foreground colours
        for (int fg = 0; fg < 8; fg++) {

            CharacterColor foregroundColor = getColorByIndex(fg);

            // try normal, then bold (bright)
            for (int i = 0; i < 2; i++) {

                styles.setBold(i == 1);

                // loop over the eight background colours
                for (int bg = 0; bg < 8; bg++) {

                    CharacterColor backgroundColor = getColorByIndex(bg);

                    colors.setForegroundColor(foregroundColor);
                    colors.setBackgroundColor(backgroundColor);

                    terminal.putCharacter('*');
                }
            }

            row++;
            rowStart = new ConstantPoint(0, row);
            terminal.setCursorPosition(rowStart);
        }

        // one line in default colours

        rowStart = new ConstantPoint(0, row);
        terminal.setCursorPosition(rowStart);
        colors.clearCharacterColors();
        styles.clearCharacterStyles();
        terminal.putCharacters("DEFAULT COLOURS");
        row++;
        rowStart = new ConstantPoint(0, row);
        terminal.setCursorPosition(rowStart);
    }

    private CharacterColor getColorByIndex(int index) {

        switch (index) {

        case 0:
            return CharacterColor.BLACK;
        case 1:
            return CharacterColor.RED;
        case 2:
            return CharacterColor.GREEN;
        case 3:
            return CharacterColor.YELLOW;
        case 4:
            return CharacterColor.BLUE;
        case 5:
            return CharacterColor.MAGENTA;
        case 6:
            return CharacterColor.CYAN;
        case 7:
            return CharacterColor.WHITE;
        }

        throw new IllegalArgumentException("unknown color index: " + index);
    }

    private String makeString(boolean value) {
        return (value ? "true " : "false");
    }
}
