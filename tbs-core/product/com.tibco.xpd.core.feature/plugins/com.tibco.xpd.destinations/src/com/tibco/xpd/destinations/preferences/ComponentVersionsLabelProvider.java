/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import org.eclipse.jface.viewers.LabelProvider;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.internal.Messages;

/**
 * @author NWilson
 * 
 */
public class ComponentVersionsLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {
        String text = ""; //$NON-NLS-1$
        if (element instanceof String) {
            String validationDestinationId = (String) element;
            boolean isDisabled =
                    GlobalDestinationUtil
                            .isDisabledComponentVersion(validationDestinationId);
            if (isDisabled) {
                text =
                        Messages.DestinationComponentsViewer_notApplicableVerisonLabel;
            } else {
                String name =
                        GlobalDestinationUtil
                                .getValidationDestinationName(validationDestinationId);
                if (name != null) {
                    text = name;
                }
            }
        }
        return text;
    }

}
