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

package org.jchassis.termctl.ansi;

import org.jchassis.termctl.Key;
import org.jchassis.termctl.Keystroke;
import org.jchassis.termctl.ModifierKey;

/**
 * A class to convert keycodes from stdin into logical keystrokes.
 * It is intended that an instance of this class is only ever accessed
 * by one thread.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
class KeycodeComposer {


    private final static int ASCII_HT = 9;
    private final static int ASCII_CR = 13;
    private final static int ASCII_ESC = 27;
    private final static int ASCII_DEL = 127;

    private boolean isEscape;
    private boolean isCsi; // Command Sequence Introducer = 'ESC' '['
    private boolean expectingTilde; // expect a '~' suffix
    private int tildePrefix; // the char in ESC [ <char> ~ sequences

    KeycodeComposer() {
        reset();
    }

    private void reset() {
        this.isEscape = false;
        this.isCsi = false;
        this.expectingTilde = false;
    }

    // returns null for partial composition
    public Keystroke composeKeycode(int keycode) {

        Keystroke keystroke = null;

        if (this.expectingTilde) {
            if (keycode == '~') {

                int digit = this.tildePrefix - '0';

                switch(digit) {
                case 1:
                    keystroke = Keystroke.HOME;
                    break;
                case 2:
                    keystroke = Keystroke.INSERT;
                    break;
                case 3:
                    keystroke = Keystroke.DELETE;
                    break;
                case 4:
                    keystroke = Keystroke.END;
                    break;
                case 5:
                    keystroke = Keystroke.PAGE_UP;
                    break;
                case 6:
                    keystroke = Keystroke.PAGE_DOWN;
                    break;
                default:
                    keystroke = Keystroke.UNKNOWN;
                }

            } else {
                keystroke = Keystroke.UNKNOWN;
            }

            reset();

        } else if (this.isCsi) { // expecting a CSI prefixed sequence

            int code = keycode - '0';
            if ((code >= 1) && (code <= 6)) {
                this.tildePrefix = keycode;
                this.expectingTilde = true;
            } else {
                switch (keycode) {
                case 'A':
                    keystroke = Keystroke.UP;
                    break;
                case 'B':
                    keystroke = Keystroke.DOWN;
                    break;
                case 'C':
                    keystroke = Keystroke.RIGHT;
                    break;
                case 'D':
                    keystroke = Keystroke.LEFT;
                    break;
                case 'Z':
                    keystroke = new Keystroke(Key.TAB, ModifierKey.SHIFT);
                    break;
                default:
                    keystroke = Keystroke.UNKNOWN;
                }

                reset();
            }
        } else if (this.isEscape) {

            // only accept '[' as the next character: this makes a CSI
            // prefixed sequence

            if (keycode == '[') {
                this.isCsi = true;
            } else {
                keystroke = Keystroke.UNKNOWN;
                reset();
            }
        } else if (keycode == ASCII_ESC) {
            this.isEscape = true;
        } else if (keycode == ASCII_HT) {
            keystroke = Keystroke.TAB;
        } else if (keycode == ASCII_CR) {
            keystroke = Keystroke.ENTER;
        } else if (keycode == ASCII_DEL) {
            keystroke = Keystroke.BACKSPACE;
        } else if ((keycode >= 1) && (keycode <= 26)) {
            char value = (char) (keycode + 64);
            keystroke =
                new Keystroke(new Key(value), ModifierKey.CONTROL);
        } else if ((keycode >= 32) && (keycode <= 126)) {
            keystroke = new Keystroke(new Key((char) keycode));
        } else {
            keystroke = Keystroke.UNKNOWN;
        }

        return keystroke;
    }
}
