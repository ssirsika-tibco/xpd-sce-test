/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.destinations.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * Helper for the
 * <code>com.tibco.xpd.destinations.ui.complexDataTypeBinding</code> extension
 * point.
 * <p>
 * This extension point provides the binding of complex data types contributed
 * via the <code>com.tibco.xpd.resources.ui.complexDataType</code> extension
 * point to particular Process Destination Environments.
 * </p>
 * 
 * @author aallway
 * 
 */
public class ComplexDataTypeBindingHelper {

    /** Complex Data Type Binding extension point Id. */
    private static final String EXTPOINT_ID = "complexDataTypeBinding"; //$NON-NLS-1$

    /** Main destination element. */
    private static final String DESTINATION_EL = "destination"; //$NON-NLS-1$

    /** Destination environment id. */
    private static final String DESTINATION_ID_ATTR = "destinationId"; //$NON-NLS-1$

    /** Complex data type binding element. */
    private static final String COMPLEXDATATYPE_EL = "complexDataTypeBinding"; //$NON-NLS-1$

    /** Complex data type id. */
    private static final String COMPLEXDATATYPE_ID_ATTR = "complexDataTypeId"; //$NON-NLS-1$ 

    /**
     * Get a list of the complex data type ids for the selected destination
     * environments.
     * 
     * @param destEnvIds
     *            List of process destination environment id's.
     * 
     * @return Set of complex data type id's that are supported by given
     *         destination environments.
     */
    public static Set<String> getTypesForDestinations(
            Collection<String> destEnvIds) {
        Set<String> complexTypeIds = new HashSet<String>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DestinationsUIPlugIn.PLUGIN_ID,
                                EXTPOINT_ID);

        if (point != null) {
            //
            // Go thru each extender of this extension point.
            //
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {

                for (IExtension ext : extensions) {
                    loadSingleExtension(ext, destEnvIds, complexTypeIds);
                }
            }
        }

        return complexTypeIds;
    }

    /**
     * Load the bindings defined in a single contribution.
     * 
     * @param ext
     * @param destEnvIds
     * @param complexTypeIds
     */
    private static void loadSingleExtension(IExtension ext,
            Collection<String> destEnvIds, Set<String> complexTypeIds) {

        IConfigurationElement destEl =
                ExtPointUtil.getConfigElement(ext, DESTINATION_EL, true);
        if (destEl != null) {

            // Check if this binding is for an active dest env.
            String destId =
                    ExtPointUtil.getConfigAttribute(destEl,
                            DESTINATION_ID_ATTR,
                            true);
            if (destId != null && destEnvIds.contains(destId)) {

                // Get the bindings...
                Collection<IConfigurationElement> bindingElements =
                        ExtPointUtil.getConfigElements(destEl,
                                COMPLEXDATATYPE_EL,
                                true);

                for (Iterator iter = bindingElements.iterator(); iter.hasNext();) {
                    IConfigurationElement bindEl =
                            (IConfigurationElement) iter.next();

                    String complexTypeId =
                            ExtPointUtil.getConfigAttribute(bindEl,
                                    COMPLEXDATATYPE_ID_ATTR,
                                    true);
                    if (complexTypeId != null && complexTypeId.length() > 0) {
                        complexTypeIds.add(complexTypeId);
                    }
                }
            }

        }

        return;
    }

}
