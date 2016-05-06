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
public class TestAnsiTermctlCharacterGraphics extends TestAnsiTermctlBase {

    public static void main(String[] args) throws Exception {
        TestAnsiTermctlBase test = new TestAnsiTermctlCharacterGraphics();
        test.init();
        test.run();
        test.end();
    }

    public void run() throws Exception {

        TerminalController terminal = getTerminal();
        GraphicCharacterTerminal graphics
            = (GraphicCharacterTerminal) terminal;
        
        graphics.setGraphicMode(true);
        
        String message = "Hello Graphics!";
        int lineLength = message.length() + 2;
        
        // draw a box with text in it
        
        terminal.setCursorPosition(new ConstantPoint(2, 2));
        graphics
            .putGraphicCharacter(GraphicCharacter.UPPER_LEFT_CORNER);
        for (int i = 0; i< lineLength; i++) {
            terminal
                .putGraphicCharacter(GraphicCharacter.MIDDLE_LINE);
        }
        graphics
            .putGraphicCharacter(GraphicCharacter.UPPER_RIGHT_CORNER);
            
        terminal.setCursorPosition(new ConstantPoint(2, 3));
        graphics
            .putGraphicCharacter(GraphicCharacter.VERTICAL_LINE);
        terminal.setForegroundColor(CharacterColor.BLACK);
        terminal.setBackgroundColor(CharacterColor.YELLOW);
        graphics.putGraphicCharacter(GraphicCharacter.BLANK);
        
        graphics.setGraphicMode(false);
        terminal.putCharacters(message);
        graphics.setGraphicMode(true);
        
        graphics.putGraphicCharacter(GraphicCharacter.BLANK);
        terminal.clearCharacterColors();
        graphics
            .putGraphicCharacter(GraphicCharacter.VERTICAL_LINE);
            
        terminal.setCursorPosition(new ConstantPoint(2, 4));
        graphics
            .putGraphicCharacter(GraphicCharacter.LOWER_LEFT_CORNER);
        for (int i = 0; i< lineLength; i++) {
            terminal
                .putGraphicCharacter(GraphicCharacter.MIDDLE_LINE);
        }
        graphics
            .putGraphicCharacter(GraphicCharacter.LOWER_RIGHT_CORNER);
        
        // draw a grid
        
        terminal.setForegroundColor(CharacterColor.MAGENTA);
        terminal.setBackgroundColor(CharacterColor.BLUE);
        
        terminal.setCursorPosition(new ConstantPoint(2, 6));
        graphics
            .putGraphicCharacter(GraphicCharacter.UPPER_LEFT_CORNER);
        graphics
            .putGraphicCharacter(GraphicCharacter.TOP_T);
        graphics
            .putGraphicCharacter(GraphicCharacter.UPPER_RIGHT_CORNER);
            
        terminal.setCursorPosition(new ConstantPoint(2, 7));
        graphics
            .putGraphicCharacter(GraphicCharacter.LEFT_T);
        graphics
            .putGraphicCharacter(GraphicCharacter.CROSSING_LINES);
        graphics
            .putGraphicCharacter(GraphicCharacter.RIGHT_T);
            
        terminal.setCursorPosition(new ConstantPoint(2, 8));
        graphics
            .putGraphicCharacter(GraphicCharacter.LOWER_LEFT_CORNER);
        graphics
            .putGraphicCharacter(GraphicCharacter.BOTTOM_T);
        graphics
            .putGraphicCharacter(GraphicCharacter.LOWER_RIGHT_CORNER);
            
        terminal.clearCharacterColors();

        // move the cursor and reset the graphics mode
        
        terminal.setCursorPosition(new ConstantPoint(0, 10));
        graphics.setGraphicMode(false);
    }
}
