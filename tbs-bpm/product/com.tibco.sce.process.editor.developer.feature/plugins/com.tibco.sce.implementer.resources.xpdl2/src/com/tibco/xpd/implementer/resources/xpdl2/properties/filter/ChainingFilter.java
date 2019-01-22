package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter with ability to chain with another filter.
 *
 * @author mmaciuki
 *
 */
public interface ChainingFilter {
	ViewerFilter chain(ViewerFilter otherFilter);
}
