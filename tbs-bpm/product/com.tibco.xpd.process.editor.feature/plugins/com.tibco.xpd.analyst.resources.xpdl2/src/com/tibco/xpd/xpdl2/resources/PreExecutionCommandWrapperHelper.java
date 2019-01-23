/**
 * PreExecutionCommandWrapperHelper.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;

/**
 * PreExecutionCommandWrapperHelper
 * 
 */
public class PreExecutionCommandWrapperHelper {

    //
    // Default static instance
    private static PreExecutionCommandWrapperHelper INSTANCE =
            new PreExecutionCommandWrapperHelper();

    /**
     * Get the Default static instance
     * 
     * @return the Default static instance
     */
    public static PreExecutionCommandWrapperHelper getDefault() {
        return INSTANCE;
    }

    //
    // The contribution loader class itself.
    private static final String PRE_EXEC_CMD_WRAPPER_EXTPOINT_ID =
            "preCommandExecutionWrapper"; //$NON-NLS-1$ 

    private static final String WRAPPER_EL = "PreExecutionCommandWrapper"; //$NON-NLS-1$ 

    private static final String CLASS_ATTR = "class"; //$NON-NLS-1$ 

    private List<IPreCommandExecutionWrapper> contributions;

    /**
     * Load the contributions.
     */
    public PreExecutionCommandWrapperHelper() {
        loadContributions();
    }

    /**
     * Load the pre execution command wrapper contributions.
     */
    private void loadContributions() {
        contributions = new ArrayList<IPreCommandExecutionWrapper>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                                PRE_EXEC_CMD_WRAPPER_EXTPOINT_ID);

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements =
                            ext.getConfigurationElements();
                    if (elements != null) {
                        for (int i = 0; i < elements.length; i++) {
                            IConfigurationElement el = elements[i];
                            if (WRAPPER_EL.equals(el.getName())) {
                                Object clazz;
                                try {
                                    clazz =
                                            el
                                                    .createExecutableExtension(CLASS_ATTR);
                                    if (clazz instanceof IPreCommandExecutionWrapper) {
                                        contributions
                                                .add((IPreCommandExecutionWrapper) clazz);
                                    }

                                } catch (CoreException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @return the contributions
     */
    public List<IPreCommandExecutionWrapper> getContributions() {
        return contributions;
    }

    
}

