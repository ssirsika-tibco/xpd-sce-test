/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.xpdl2.provider.Xpdl2EditPlugin;

/**
 * Extension point manager for the
 * {@link FixReassignedIdReferencesExtensionPoint} extension.
 * 
 * @author kthombar
 * @since Apr 15, 2015
 */
public class FixReassignedIdReferencesExtensionPointManager {

    /**
     * The extension point.
     */
    private static final String FIX_ID_REFERENCES_EXTENSIONPOINT =
            "fixReassignedIdReferencesContribution"; //$NON-NLS-1$

    private List<FixReassignedIdReferencesExtensionPoint> fixReassignedIdReferencesExtension =
            new ArrayList<FixReassignedIdReferencesExtensionPoint>();

    /**
     * 
     */
    public FixReassignedIdReferencesExtensionPointManager() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2EditPlugin.PLUGIN_ID,
                                FIX_ID_REFERENCES_EXTENSIONPOINT);

        if (point != null) {
            // Get all extensions of the signal type extension point
            IExtension[] exts = point.getExtensions();

            if (exts != null) {
                for (IExtension eachExtension : exts) {
                    IConfigurationElement[] elements =
                            eachExtension.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {

                            this.fixReassignedIdReferencesExtension
                                    .add(new FixReassignedIdReferencesExtensionPoint(
                                            elem));

                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @return list of {@link FixReassignedIdReferencesExtensionPoint}
     *         extensions
     */
    public List<FixReassignedIdReferencesExtensionPoint> getFixReassignedIdReferencesExtension() {
        return Collections.unmodifiableList(fixReassignedIdReferencesExtension);
    }
}
