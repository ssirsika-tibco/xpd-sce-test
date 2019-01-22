/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.properties.visual;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;

/**
 * Allows {@link BaseGraphicalEditPart} and {@link BaseConnectionEditPart} thru,
 * but not {@link ProcessEditPart}
 * 
 * @author aallway
 * @since 15 Mar 2012
 */
public class NotProcessEditPartFilter implements IFilter {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        if (toTest instanceof ProcessEditPart
                || toTest instanceof WidgetRootEditPart) {
            return false;
        } else if (toTest instanceof BaseGraphicalEditPart) {
            return true;
        } else if (toTest instanceof BaseConnectionEditPart) {
            return true;
        }
        return false;
    }

}
