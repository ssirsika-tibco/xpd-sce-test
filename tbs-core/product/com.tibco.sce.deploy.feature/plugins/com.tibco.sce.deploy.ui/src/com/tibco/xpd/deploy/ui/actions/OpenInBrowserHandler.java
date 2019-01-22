/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.DeployUIActivator;

/**
 * Invokes open in browser action for selected element. Subclasses should
 * implement {@link #getURL(Object)} method to provide URL to be opened in
 * browser.
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class OpenInBrowserHandler extends AbstractHandler {

    /**
     * Get URL for a selected element.
     * 
     * @param element
     *            the selected element.
     * @return URL for a selected element to open in a browser or 'null' to
     *         ignore.
     */
    protected abstract URL getURL(Object element);

    protected String getServerParamValue(Server server, String key) {
        if (server.getServerConfig() != null) {
            ConfigParameter cp =
                    server.getServerConfig().getConfigParameter(key);
            if (cp != null) {
                return cp.getValue();
            }
        }
        return null;
    }

    protected boolean isServerOfType(Server server, String serverType) {
        if (server.getServerType() != null) {
            return server.getServerType().getId().equals(serverType);
        }
        return false;
    }

    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection sel = HandlerUtil.getCurrentSelection(event);
        if (sel instanceof IStructuredSelection
                && !((IStructuredSelection) sel).isEmpty()) {
            Iterator<?> iterator = ((IStructuredSelection) sel).iterator();
            while (iterator.hasNext()) {
                Object element = iterator.next();
                URL url = getURL(element);
                if (url != null) {
                    String browserId = url.toString();
                    try {
                        PlatformUI.getWorkbench().getBrowserSupport()
                                .createBrowser(browserId).openURL(url);
                    } catch (PartInitException e) {
                        DeployUIActivator.getDefault().getLogger().error(e);
                        return Status.CANCEL_STATUS;
                    }
                }
            }
        }
        return Status.OK_STATUS;
    }
}
