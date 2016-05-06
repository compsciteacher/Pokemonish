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

/**
 * A source of events.
 * Listeners can be added more than once, causing a new &quot;entry&quot;
 * for each addition. If a listener has multiple entries, it will be
 * notified multiple times for a single event (N times if there are
 * N entries). Each call to removeEventListener removes a single entry
 * &mdash; the last entry added.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public interface EventSource {

    /**
     * Adds a listener entry to this event source.
     * All events will be propagated to the listener.
     *
     * @param listener the listener to add and entry for
     */
    void addEventListener(EventListener listener);

    /**
     * Adds a listener entry to this event source.
     * Only events with the specified event role will be propagated to the
     * listener.
     *
     * @param eventRole allow events with this role only
     * @param listener the listener to add and entry for
     */
    void addEventListener(EventRole eventRole, EventListener listener);

    /**
     * Adds a listener entry to this event source.
     * Only events allowed by the specified filter will be propagated to the
     * listener.
     *
     * @param filter a filter for the events
     * @param listener the listener to add and entry for
     */
    void addEventListener(EventFilter filter, EventListener listener);

    /**
     * Removes the specified listener entry.
     * If the same listener instance has been added more than once, only
     * the last entry is removed. This includes removing the filter that
     * is associated with that entry, if a filter was specified when
     *
     * @param listener the listener whose entry will removed
     *
     * the entry was added by the addEventListener method.
     */
    boolean removeEventListener(EventListener listener);
}
