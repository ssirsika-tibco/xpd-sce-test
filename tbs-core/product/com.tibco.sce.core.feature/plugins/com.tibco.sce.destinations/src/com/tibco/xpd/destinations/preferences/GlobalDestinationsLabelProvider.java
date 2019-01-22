/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import org.eclipse.jface.viewers.LabelProvider;

import com.tibco.xpd.destinations.GlobalDestinationUtil;

/**
 * @author NWilson
 * 
 */
public class GlobalDestinationsLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {
        String text = ""; //$NON-NLS-1$
        if (element instanceof String) {
            String globalId = (String) element;
            text = GlobalDestinationUtil.getGlobalDestinationName(globalId);
        }
        return text;
    }

}
