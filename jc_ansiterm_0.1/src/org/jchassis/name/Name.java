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

package org.jchassis.name;

/**
 * An immutable name representation.
 * A name has zero or more name components which are strings. Each
 * name component may contain any character except '/'.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2004 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class Name {

    private static final char NAME_COMPONENT_SEPARATOR = '/';

    private final String[] components;

    /**
     * Creates a new name with the specified name components.
     *
     * @param components the components of this name
     *
     * @throws IllegalArgumentException if a name component is null or it
     *     contains '/'
     */
    public Name(String[] components) {

        final int n = components.length;
        for (int i = 0; i < n; i++) {

            String component = components[i];

            if (component == null) {
                throw new
                    IllegalArgumentException("null component at index: " + i);

            }

            if (component.indexOf(NAME_COMPONENT_SEPARATOR) >= 0) {
                throw new
                    IllegalArgumentException("component must not contain '"
                                             + NAME_COMPONENT_SEPARATOR
                                             + "': " + component);
            }
        }

        this.components = (String[]) components.clone();
    }

    public boolean equals(Object obj) {

        boolean equals = false;

        if (obj instanceof Name) {

            Name otherName = (Name) obj;

            final int n = this.components.length;
            if (otherName.components.length == n) {

                // check equality of each component - return false of first
                // mismatch

                boolean mismatched = false;
                for (int i = 0; i < n; i++) {
                    if (!otherName.components[i].equals(this.components[i])) {
                        mismatched = true;
                        break;
                    }
                }

                if (!mismatched) {
                    equals = true;
                }
            }
        }

        return equals;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer(getClass().getName());
        buffer.append('[');
        final int n = this.components.length;
        for (int i = 0; i < n; i++) {
            buffer.append(this.components[i]);
            if (i < (n - 1)) {
                buffer.append(NAME_COMPONENT_SEPARATOR);
            }
        }
        buffer.append(']');
        return buffer.toString();
    }
}
