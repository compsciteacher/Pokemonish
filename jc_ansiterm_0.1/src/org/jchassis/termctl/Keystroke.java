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
 * Keystroke definitions. These are used for keystroke events.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class Keystroke {

    public static final Keystroke TAB       = new Keystroke(Key.TAB);
    public static final Keystroke ENTER     = new Keystroke(Key.ENTER);
    public static final Keystroke BACKSPACE = new Keystroke(Key.BACKSPACE);
    public static final Keystroke UP        = new Keystroke(Key.UP);
    public static final Keystroke DOWN      = new Keystroke(Key.DOWN);
    public static final Keystroke LEFT      = new Keystroke(Key.LEFT);
    public static final Keystroke RIGHT     = new Keystroke(Key.RIGHT);
    public static final Keystroke HOME      = new Keystroke(Key.HOME);
    public static final Keystroke INSERT    = new Keystroke(Key.INSERT);
    public static final Keystroke DELETE    = new Keystroke(Key.DELETE);
    public static final Keystroke END       = new Keystroke(Key.END);
    public static final Keystroke PAGE_UP   = new Keystroke(Key.PAGE_UP);
    public static final Keystroke PAGE_DOWN = new Keystroke(Key.PAGE_DOWN);
    public static final Keystroke UNKNOWN   = new Keystroke(Key.UNKNOWN);

    private final Key key;
    private final ModifierKey modifier1;
    private final ModifierKey modifier2;
    private final ModifierKey modifier3;
    private final int modifierCount;

    /**
     * Creates a single key keystroke.
     *
     * @param key the key that was pressed
     */
    public Keystroke(Key key) {
        this(key, null, null, null);
    }

    /**
     * Creates a two key keystroke.
     *
     * @param key the key that was pressed
     * @param modifier the modifier that was simultaneously pressed
     */
    public Keystroke(Key key, ModifierKey modifier) {
        this(key, modifier, null, null);
    }

    /**
     * Creates a three key keystroke.
     *
     * @param key the key that was pressed
     * @param modifier1 the modifier that was simultaneously pressed
     * @param modifier2 the modifier that was simultaneously pressed
     */
    public Keystroke(Key key, ModifierKey modifier1, ModifierKey modifier2) {
        this(key, modifier1, modifier2, null);
    }

    /**
     * Creates a four key keystroke.
     *
     * @param key the key that was pressed
     * @param modifier1 the modifier that was simultaneously pressed
     * @param modifier2 the modifier that was simultaneously pressed
     * @param modifier3 the modifier that was simultaneously pressed
     */
    public Keystroke(Key key, ModifierKey modifier1, ModifierKey modifier2,
                     ModifierKey modifier3) {
        this.key = key;
        this.modifier1 = modifier1;
        this.modifier2 = modifier2;
        this.modifier3 = modifier3;

        if (modifier1 == null) {
            this.modifierCount = 0;
        } else if (modifier2 == null) {
            this.modifierCount = 1;
        } else if (modifier3 == null) {
            this.modifierCount = 2;
        } else {
            this.modifierCount = 3;
        }
    }

    /**
     * Gets the (non-modifier) key that was pressed.
     *
     * @return the key
     */
    public Key getKey() {
        return this.key;
    }

    /**
     * Indicates whether any modifier keys were pressed.
     *
     * @return true if one or more modifier keys were pressed; false otherwise
     */
    public boolean isModified() {
        return (this.modifierCount > 0);
    }

    /**
     * Tests if a particular modifier key was pressed.
     *
     * @param modifier the modifier key to test for
     *
     * @return true if the key was pressed; false otherwise
     */
    public boolean hasModifier(ModifierKey modifier) {
        return (this.modifier1 == modifier) || (this.modifier2 == modifier)
            || (this.modifier3 == modifier);
    }

    /**
     * Gets the number of modifier keys pressed.
     *
     * @return the number of modifier keys pressed
     */
    public int getModifierCount() {
        return this.modifierCount;
    }

    /**
     * Gets the first modifier key.
     *
     * @return the modifier key; or null if no modifier was pressed
     */
    public ModifierKey getFirstModifier() {
        return this.modifier1;
    }

    /**
     * Gets the second modifier key.
     *
     * @return the modifier key; or null if one or less modifiers were pressed
     */
    public ModifierKey getSecondModifier() {
        return this.modifier2;
    }

    /**
     * Gets the third modifier key.
     *
     * @return the modifier key; or null if two or less modifiers were pressed
     */
    public ModifierKey getThirdModifier() {
        return this.modifier3;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (this.modifier1 != null) {
            buffer.append(this.modifier1.toString());
            buffer.append("-");
            if (this.modifier2 != null) {
                buffer.append(this.modifier2.toString());
                buffer.append("-");
                if (this.modifier3 != null) {
                    buffer.append(this.modifier3.toString());
                    buffer.append("-");
                }
            }
        }

        buffer.append(this.key.toString());

        return buffer.toString();
    }
}
