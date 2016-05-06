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
 * A representation of a key on a keyboard.
 * These are used for contructing keystrokes.
 * Keys that represent a particular character have a 'char' value. Modifier
 * keys, (the SHIFT key, etc.) for example, don't have a value.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class Key {

    public static final Key TAB       = new Key("TAB");
    public static final Key ENTER     = new Key("ENTER");
    public static final Key BACKSPACE = new Key("BACKSPACE");
    public static final Key UP        = new Key("UP");
    public static final Key DOWN      = new Key("DOWN");
    public static final Key LEFT      = new Key("LEFT");
    public static final Key RIGHT     = new Key("RIGHT");
    public static final Key HOME      = new Key("HOME");
    public static final Key INSERT    = new Key("INSERT");
    public static final Key DELETE    = new Key("DELETE");
    public static final Key END       = new Key("END");
    public static final Key PAGE_UP   = new Key("PAGE_UP");
    public static final Key PAGE_DOWN = new Key("PAGE_DOWN");
    public static final Key UNKNOWN   = new Key("UNKNOWN");

    private final String name;
    private final char value;
    private final boolean hasValue;

    /**
     * Creates a new key.
     *
     * @param name the user-friendly name of the key
     */
    Key(String name) {
        this.name = name;
        this.value = (char) 0;
        this.hasValue = false;
    }

    /**
     * Creates a new key.
     *
     * @param value the value of the character this key represents; 0 if this
     *          key doesn't represent a character
     */
    public Key(char value) {
        this(String.valueOf(value), value, true);
    }

    private Key(String name, char value, boolean hasValue) {
        this.name = name;
        this.value = value;
        this.hasValue = hasValue;
    }

    /**
     * Gets the user-friendly name of this key.
     *
     * @return the key's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Indicates whether this key represents a character.
     *
     * @return true if the key represents a character; false otherwise
     */
    public boolean hasValue() {
        return this.hasValue;
    }

    /**
     * Gets the character that this key represents, if any.
     *
     * @return thecharacter value; or zero if this key doesn't represent 
     * a character
     */
    public char getValue() {
        return this.value;
    }

    public String toString() {
        return this.name;
    }
}
