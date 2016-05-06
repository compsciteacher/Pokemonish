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

package org.jchassis.event;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Support for collections of listeners.
 * This class is not thread-safe.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class DefaultEventCoordinator implements EventCoordinator {

    private final Vector listeners;
    private final Vector filters;

    public DefaultEventCoordinator() {
        this.listeners = new Vector(0);
        this.filters = new Vector(0);
    }

    // javadoc inherited
    public void notifyEventListeners(EventRole eventRole, Object event) {

        final int n = this.listeners.size();
        for (int i = 0; i < n; i++) {
            EventListener listener
                = (EventListener) this.listeners.elementAt(i);
            EventFilter filter = (EventFilter) this.filters.elementAt(i);

            boolean allow = (filter == null ?
                             true : filter.allowEvent(eventRole, event));
            if (allow) {
                listener.notifyEventListener(eventRole, event);
            }
        }
    }

    // javadoc inherited
    public void addEventListener(EventListener listener) {
        addEventListener((EventFilter) null, listener);
    }

    // javadoc inherited
    public void addEventListener(EventRole eventRole, EventListener listener) {
        this.listeners.addElement(listener);
        this.filters.addElement(new SimpleEventFilter(eventRole));
    }

    // javadoc inherited
    public void addEventListener(EventFilter filter, EventListener listener) {
        this.listeners.addElement(listener);
        this.filters.addElement(filter);
    }

    // javadoc inherited
    public boolean removeEventListener(EventListener listener) {

        final int index = this.listeners.lastIndexOf(listener);
        boolean exists = (index >= 0);

        if (exists) {
            this.listeners.removeElementAt(index);
            this.filters.removeElementAt(index);
        }

        return exists;
    }
}
