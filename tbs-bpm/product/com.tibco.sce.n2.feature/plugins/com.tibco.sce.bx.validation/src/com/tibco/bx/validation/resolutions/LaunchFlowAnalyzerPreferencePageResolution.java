/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.n2.resources.ui.FlowAnalyzerPreferenceContributor;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick fix that launches flow analyzer preferences page (actally it;s the
 * proecss editor preference page which we contribute
 * 
 * @author aallway
 * @since 4 Jul 2014
 */
public class LaunchFlowAnalyzerPreferencePageResolution implements IResolution {

    public LaunchFlowAnalyzerPreferencePageResolution() {
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    public void run(IMarker marker) throws ResolutionException {
        if (Display.getCurrent() != null) {
            IPreferencePage page = new FlowAnalyzerPreferenceContributor();
            PreferenceManager mgr = new PreferenceManager();
            IPreferenceNode node =
                    new PreferenceNode(
                            FlowAnalyzerPreferenceContributor.class.getName(),
                            page);

            mgr.addToRoot(node);
            PreferenceDialog dialog =
                    new PreferenceDialog(Display.getCurrent().getActiveShell(),
                            mgr);

            dialog.create();
            dialog.setMessage(page.getTitle());
            dialog.open();
        }
    }

}
