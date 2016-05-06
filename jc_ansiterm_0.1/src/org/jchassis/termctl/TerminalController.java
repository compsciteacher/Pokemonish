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


import org.jchassis.event.EventRole;
import org.jchassis.event.EventSource;
import org.jchassis.geom.int2d.*;

/**
 * An interface for controlling a text terminal.
 * A terminal should initally be in the "default" state, defined in the
 * init() method's documentation.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public interface TerminalController {

    /** the role for an event represeint the input of a single character
        through the keyboard - the event value must be an Integer,
        representing the ASCII key code */
    public static final EventRole KEYBOARD_INPUT_EVENT_ROLE
        = new EventRole(new String[] {"action", "input"}); // TODO - name?

    /**
     * Tries to initialise the terminal to its default state.
     * The default state is: the terminal screen is cleared and the cursor is
     * in the home position. Also, wrapping should be off when in the default
     * state: cursor movements should not wrap when an attempt is made to
     * move the cursor past an edge of the screen in any direction. Finally,
     * no character styles should be applied (bold, blink, etc.) and character
     * colors should be white text on a black background.
     */
    void init() throws TerminalIoException;

    /**
     * Resets the terminal.
     * This should also leave the terminal in the default state, as
     * described in the init() method documention . This method is indended
     * to take more drastic action than init, by invoking the terminal's
     * reset function if it has one, rather than just adjusting some
     * attributes.
     */
    void reset() throws TerminalIoException;

    /**
     * Moves the cursor one position in the specified direction.
     *
     * @param direction the direction to move the cursor in
     */
    void moveCursor(Direction direction) throws TerminalIoException;

    /**
     * Moves the cursor in the specified direction.
     *
     * @param direction the direction to move the cursor in
     * @param count the number of positions to move the cursor in the
     *     specified direction; a value of zero results in no action
     */
    void moveCursor(Direction direction, int count) throws TerminalIoException;

    /**
     * Puts the specified character at the current cursor position.
     * The cursor will move forward (i.e. to the right) one position.
     *
     * @param ch the character to put
     */
    void putCharacter(char ch) throws TerminalIoException;

    /**
     * Puts the specified characters at the current cursor position
     * and moving forward one position for each character.
     *
     * @param chars the characters to put
     */
    void putCharacters(String chars) throws TerminalIoException;

    /**
     * Gets the current cursor position.
     * Positions are based at (0,0), not (1,1).
     *
     * @return the cursor's position
     */
    Point getCursorPosition() throws TerminalIoException;

    /**
     * Sets the current cursor position.
     * Positions are based at (0,0), not (1,1).
     *
     * @param position the position to move the cursor to
     */
    void setCursorPosition(Point position) throws TerminalIoException;

    /**
     * Sets the cursor in the home position.
     * This is equivalent to settting the cursor position to (0,0).
     */
    void home() throws TerminalIoException;

    /**
     * Issues a warning such as an audible beep.
     */
    void beep() throws TerminalIoException;

    /**
     * Erases the terminal screen.
     */
    void eraseScreen() throws TerminalIoException;

    /**
     * Gets the size of the screen.
     *
     * @return the screen dimesions in terms of characters positions (columns
     *     x rows).
     */
    Dimension getScreenSize() throws TerminalIoException;

    /**
     * Enables or disables keyboard input.
     */
    void setInputEnabled(boolean on);

    /**
     * Gets the event source for events generated by this terminal.
     * Typically, this will just be a source of keyboard input events.
     */
    EventSource getEventSource();

}
