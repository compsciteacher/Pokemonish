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
 * A simple event filter implementation that filters based on an
 * event's data type and role or role prefix.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class SimpleEventFilter implements EventFilter {

    // filter parameters

    private final boolean allowingEvents;
    private final EventRole allowedRole;
    private final Class allowedClass;

    private SimpleEventFilter(EventRole allowedRole, Class allowedClass,
                              boolean allowEvents) {
        this.allowedRole = allowedRole;
        this.allowedClass = allowedClass;
        this.allowingEvents = allowEvents;
    }

    /**
     * Creates a filter that only allows events that have the specified
     * class and role. This calls this(allowedRole, false, allowedClass).
     *
     * @param allowedRole the role of events to allow through; if null,
     *     events with any role are allowed
     * @param allowedClass the class of event data to allow through; subclasses
     *     are also allowed; if null, all classes are allowed
     */
    public SimpleEventFilter(EventRole allowedRole, Class allowedClass) {
        this(allowedRole, allowedClass, true);
    }

    /**
     * Creates a filter that only allows events that have the specified
     * role. This calls this(allowedRole, false).
     *
     * @param allowedRole the role of events to allow through; if null,
     *     events with any role are allowed
     */
    public SimpleEventFilter(EventRole allowedRole) {
        this(allowedRole, null);
    }

    /**
     * Creates a filter that only allows events that have the specified
     * class. This calls this(null, allowedClass).
     *
     * @param allowedClass the class of event data to allow through; subclasses
     *     are also allowed; if null, all classes are allowed
     */
    public SimpleEventFilter(Class allowedClass) {
        this(null, allowedClass);
    }

    /**
     * Creates either an &quot;identity&quot; filter or a &quot;null&quot;
     * filter &mdash; either all events or no events pass through.
     * This calls this((String) null).
     *
     * @param allowEvents if true, all events are allowed through; otherwise
     *     no events are allow through
     */
    public SimpleEventFilter(boolean allowEvents) {
        this(null, null, allowEvents);
    }

    /**
     * Creates an &quot;identity&quot; filter &mdash; all events pass through.
     * This calls this(true).
     */
    public SimpleEventFilter() {
        this(true);
    }

    /**
     * Indicated whether this filter is allowing any events through or not.
     *
     * @return true events are being allowed; otherwise no events are allowed
     */
    public boolean isAllowingEvents() {
        return this.allowingEvents;
    }

    /**
     * Gets the class of event data that is allowed through this filter.
     *
     * @return the class of events allowed; null indicates that events of
     * any class are allowed
     */
    public Class getAllowedClass() {
        return this.allowedClass;
    }

    /**
     * Gets the role of events that are allowed through this filter.
     *
     * @return the role of events allowed; null indicates that events with
     * any role are allowed
     */
    public EventRole getAllowedRole() {
        return this.allowedRole;
    }

    // javadoc inherited
    public boolean allowEvent(EventRole eventRole, Object eventData) {

        if (!this.allowingEvents) {
            // not allowing any events
            return false;
        }

        if (this.allowedClass != null) { // using a class filter
            if ((eventData == null) || !allowedClass.isInstance(eventData)) {
                // reject because class does not match allowed class
                return false;
            }
        }

        if (this.allowedRole != null) { // using a role filter
            if (!eventRole.equals(this.allowedRole)) {
                // reject because role does not equal allowed role prefix
                return false;
            }
        }

        return true;
    }
}
