/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

import com.tibco.xpd.rasc.ui.RascUiActivator;

/**
 * Helper class to launch the Admin page in a new browser window.
 *
 * @author nwilson
 * @since 25 Mar 2019
 */
public class AdminLauncher {

    public void launch() throws PartInitException, MalformedURLException {
        String url = RascUiActivator.getDefault().getAdminBaseUrl();
        IWebBrowser browser = PlatformUI.getWorkbench().getBrowserSupport()
                .createBrowser("admin-ui"); //$NON-NLS-1$
        browser.openURL(new URL(url));
    }
}
