/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.providers;

import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Item provider for UML NamedElement.
 * 
 * @author Jan Arciuchiewicz
 * @since 3.3
 */
public abstract class NamedElementItemProvider extends AbstractItemProvider {

    /**
     * Constructs ItemProvider and passing the adapter factory to superclass.
     * 
     * @param adapterFactory
     *            the context adapter factory.
     */
    public NamedElementItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * Displays label instead of name if provided.
     */
    @Override
    public String getText(Object object) {
        NamedElement ne = (NamedElement) object;
        return getLabel(ne);
    }

    /**
     * Retrieves a label for this named element.
     */
    private static String getLabel(NamedElement ne) {
        boolean isSolutionDesignEnabled = true;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            IActivityManager activityManager =
                    PlatformUI.getWorkbench().getActivitySupport()
                            .getActivityManager();
            Set<?> enabledActivityIds = activityManager.getEnabledActivityIds();
            isSolutionDesignEnabled =
                    enabledActivityIds
                            .contains("com.tibco.xpd.om.solutiondesign");
        }
        String text = PrimitivesUtil.getDisplayLabel(ne);
        if (isSolutionDesignEnabled) {
            return text == null ? "" : text + " (" + ne.getName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        } else {
            return text == null ? "" : text; //$NON-NLS-1$ 
        }

    }

}
