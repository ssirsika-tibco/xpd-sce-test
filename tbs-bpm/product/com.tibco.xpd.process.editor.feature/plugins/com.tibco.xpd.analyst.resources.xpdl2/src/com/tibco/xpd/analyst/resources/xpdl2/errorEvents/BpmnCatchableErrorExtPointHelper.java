/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Extension point helper for the
 * <code>com.tibco.xpd.analyst.resources.xpdl2.errorEvents.bpmnCatchableErrorProviders</code>
 * extension point.
 * 
 * @author aallway
 * @since 3.2
 */
class BpmnCatchableErrorExtPointHelper {

    public static BpmnCatchableErrorExtPointHelper INSTANCE =
            new BpmnCatchableErrorExtPointHelper();

    public BpmnCatchableErrorExtPointHelper() {
        loadContributions();
    }

    private static final String EXT_POINT_ID = "bpmnCatchableErrorProviders"; //$NON-NLS-1$

    private static final String ERROR_BROWSER_EL_NAME = "ErrorBrowser"; //$NON-NLS-1$

    private static final String CLASS_ATTR_NAME = "class"; //$NON-NLS-1$

    private List<IBpmnCatchableErrorsContributor> errorBrowserContributions =
            Collections.emptyList();

    /**
     * Get the applicable catchable error provider contributors for the given
     * error thrower.
     * 
     * @param errorThrowingActivity
     *            The activity that might throw the error.
     * 
     * @return List of applicable catchable error providers or null if none
     *         found
     */
    public Collection<IBpmnCatchableErrorsContributor> getApplicableErrorBrowserContributions(
            Activity errorThrowingActivity) {
        Collection<IBpmnCatchableErrorsContributor> applicable =
                new ArrayList<IBpmnCatchableErrorsContributor>();

        for (IBpmnCatchableErrorsContributor contribution : errorBrowserContributions) {
            if (contribution.isApplicableErrorThrower(errorThrowingActivity)) {
                applicable.add(contribution);
            }
        }

        return applicable;
    }

    private void loadContributions() {
        errorBrowserContributions =
                new ArrayList<IBpmnCatchableErrorsContributor>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                                EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] errorProviders =
                    point.getConfigurationElements();

            if (errorProviders != null) {
                for (int c = 0; c < errorProviders.length; c++) {
                    IConfigurationElement extContribution = errorProviders[c];

                    if (ERROR_BROWSER_EL_NAME.equals(extContribution.getName())) {
                        Object propClass = null;
                        try {
                            propClass =
                                    extContribution
                                            .createExecutableExtension(CLASS_ATTR_NAME);
                        } catch (CoreException ce) {
                            System.err.println(this.getClass().getName()
                                    + "CoreException: " + ce.getMessage()); //$NON-NLS-1$
                            ce.printStackTrace(System.err);
                        }

                        if (propClass instanceof IBpmnCatchableErrorsContributor) {
                            errorBrowserContributions
                                    .add((IBpmnCatchableErrorsContributor) propClass);
                        } else {
                            String contributerId =
                                    extContribution.getContributor().getName();
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(contributerId
                                            + ": " //$NON-NLS-1$
                                            + EXT_POINT_ID
                                            + ": Incorrectly defined extension - class must implement " //$NON-NLS-1$
                                            + IBpmnCatchableErrorsContributor.class
                                                    .getCanonicalName());
                        }

                    }
                }
            }
        }

        return;
    }

}
