/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.IntermediateEvent;

/**
 * Throw Catch Filter to determine whether the event is a Catch or Throw.
 * Effective on {@link IntermediateEvent}
 * 
 * @author rsomayaj
 * 
 */
public interface CatchThrowFilter {

    /**
     * 
     * @param catchThrow
     * @return
     */
    IFilter hasCatchThrowType(CatchThrow catchThrow);
}
