package com.tibco.xpd.script.sourceviewer.client;

import org.eclipse.ui.IWorkbenchPartSite;

public interface ISiteProvider {
    /**
     * Provides site
     * @return
     */
    IWorkbenchPartSite getWorkbenchPartSite();
}
