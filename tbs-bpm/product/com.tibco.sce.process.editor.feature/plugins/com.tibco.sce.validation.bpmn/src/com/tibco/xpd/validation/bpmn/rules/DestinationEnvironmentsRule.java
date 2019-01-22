/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author bharge
 * 
 */
public class DestinationEnvironmentsRule extends ProcessValidationRule {
    private static final String ID = "bpmn.nonexistentDestination"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        if (XpdResourcesPlugin.isRCP()) {
            return;
        }
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        List<String> globalDestinations = preferences.getGlobalDestinations();
        EList<ExtendedAttribute> extendedAttributes =
                process.getExtendedAttributes();

        for (ExtendedAttribute extendedAttribute : extendedAttributes) {
            if (extendedAttribute.getName().equalsIgnoreCase("Destination")) { //$NON-NLS-1$
                if (!globalDestinations.contains(extendedAttribute.getValue())) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(extendedAttribute.getValue());
                    addIssue(ID, process, messages);
                }
            }
        }
    }
}
