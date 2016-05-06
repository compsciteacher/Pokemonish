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
 * An interface to a terminal that allows the use of certain graphic 
 * characters.
 * The main use of these characters is for drawing boxes and grids.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public interface GraphicCharacterTerminal {
    
    /**
     * Switches the graphic character mode on or off.
     * Graphic characters should only be sent to the terminal when
     * the mode is on. The result of sending graphic characters to
     * a terminal when graphics mode is not on is not defined. Similarly,
     * the result of trying to send normal characters (eg. by using the
     * TerminalController putCharacters method) to a terminal that is still 
     * in graphics mode is not defined.
     *
     * @param on if true; the mode is switched on; otherwise it is
     *     switched off
     */
    void setGraphicMode(boolean on) throws TerminalIoException;
    
    /**
     * Sends a graphic character to the terminal.
     * @param graphic the graphic character to send
     */
    void putGraphicCharacter(GraphicCharacter graphic)
        throws TerminalIoException;

}
