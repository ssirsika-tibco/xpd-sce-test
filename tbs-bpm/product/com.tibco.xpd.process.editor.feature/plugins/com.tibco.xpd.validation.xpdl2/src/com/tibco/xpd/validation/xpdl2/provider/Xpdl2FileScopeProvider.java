/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.xpdl2.Package;

/**
 * @author wzurek
 * 
 */
public class Xpdl2FileScopeProvider implements IScopeProvider {

    public static final String XPDL2_DESTINATION_ID =
            "com.tibco.xpd.validation.xpdl2.file"; //$NON-NLS-1$

    /*
     * @see
     * com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com
     * .tibco.xpd.validation.destinations.Destination, java.lang.String,
     * com.tibco.xpd.validation.provider.IValidationItem)
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        boolean doIt = false;

        EObject processPackage = item.getWorkingCopy().getRootElement();

        if (processPackage instanceof Package) {
            if (XPDL2_DESTINATION_ID.equals(destination.getId())) {
                // XPDL2 destination env is not user selectable so always run.
                doIt = true;
            } else {
                // Otherwise, only run if destination is selected.
                EObject root = processPackage;
                if (root instanceof Package) {
                    Package pkg = (Package) root;

                    DestinationPreferences preferences =
                            DestinationsActivator.getDefault()
                                    .getDestinationPreferences();
                    Set<String> globalNames =
                            preferences
                                    .getGlobalDestinationsForValidationDestination(destination
                                            .getId());
                    for (String name : globalNames) {

                        if (DestinationUtil.isGlobalDestinationEnabled(pkg,
                                name)) {
                            doIt = true;
                        }

                    }
                }

            }
        }
        if (doIt && processPackage instanceof Package) {
            /*
             * Sid XPD-2516: Check contributions to processEditorConfiguration
             * /ValidationDestinationExclusions extension point before finally
             * commiting items to the list for this validation-dest and
             * provider.
             */
            if (!ProcessEditorConfigurationUtil
                    .isExcludedValidationProvider(processPackage,
                            destination.getId(),
                            providerId)) {

                return Collections.singleton(processPackage);
            }
        }

        return Collections.emptyList();

    }
}
