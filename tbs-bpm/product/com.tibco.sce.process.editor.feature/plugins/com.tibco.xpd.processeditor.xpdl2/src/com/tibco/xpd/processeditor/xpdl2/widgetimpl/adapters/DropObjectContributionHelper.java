/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IDropObjectContribution;
import com.tibco.xpd.resources.logger.Logger;

/**
 * @author aallway
 * 
 */
public class DropObjectContributionHelper {

    private static final String EXTPOINT_ID = "dropObjectContribution"; //$NON-NLS-1$

    private static final String ATT_CLASS = "class"; //$NON-NLS-1$
    
    private static List<IDropObjectContribution> dropObjectContributions = null;

    public static Collection<IDropObjectContribution> getDropObjectContributions() {

        if (dropObjectContributions == null) {
            dropObjectContributions = new ArrayList<IDropObjectContribution>();

            IConfigurationElement[] configElements =
                    Platform
                            .getExtensionRegistry()
                            .getConfigurationElementsFor(Xpdl2ProcessEditorPlugin.ID,
                                    EXTPOINT_ID);
            if (configElements != null) {
                for (int i = 0; i < configElements.length; i++) {
                    try {
                        IDropObjectContribution clazz = getDropObjectConbtributionClass(configElements[i]);
                        dropObjectContributions.add(clazz);
                    } catch (Exception e) {
                        Logger logger = Xpdl2ProcessEditorPlugin.getDefault().getLogger();
                        logger.error(e);                    }
                }
            }
        }

        return dropObjectContributions;
    }

    private static IDropObjectContribution getDropObjectConbtributionClass(IConfigurationElement configElement)
            throws IllegalStateException, CoreException {

        if (configElement != null) {
            Object clazz = configElement.createExecutableExtension(ATT_CLASS);

            if (clazz instanceof IDropObjectContribution) {
                return (IDropObjectContribution) clazz;
            } else {
                throw new IllegalStateException(
                        "dropObjectContribution.class does not implement: " //$NON-NLS-1$
                                + IDropObjectContribution.class
                                        .getCanonicalName());
            }

        }
        return null;
    }
}
