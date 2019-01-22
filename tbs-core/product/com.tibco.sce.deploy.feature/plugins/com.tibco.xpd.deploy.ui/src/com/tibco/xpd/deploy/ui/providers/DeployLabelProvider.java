/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.providers;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerElementState;
import com.tibco.xpd.deploy.ServerState;
import com.tibco.xpd.deploy.provider.DeployModelEditPlugin;
import com.tibco.xpd.deploy.provider.ServerItemProvider;
import com.tibco.xpd.deploy.ui.DeployUIActivator;

/**
 * Provides labels for deployment model objects.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployLabelProvider extends AdapterFactoryLabelProvider implements
        ICommonLabelProvider {

    public DeployLabelProvider() {
        super(DeployUIActivator.getServerManager().getAdapterFactory());

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.ICommonLabelProvider#init(org.eclipse.ui.navigator.ICommonContentExtensionSite)
     */
    public void init(ICommonContentExtensionSite aConfig) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.IMementoAware#restoreState(org.eclipse.ui.IMemento)
     */
    public void restoreState(IMemento aMemento) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.IMementoAware#saveState(org.eclipse.ui.IMemento)
     */
    public void saveState(IMemento aMemento) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.IDescriptionProvider#getDescription(java.lang.Object)
     */
    public String getDescription(Object object) {
        if (object instanceof Server) {
            Server server = (Server) object;
            ServerState serverState = server.getServerState();
            switch (serverState.getValue()) {
            case ServerState.CONNECTED:
            case ServerState.DISCONNECTED:
                String state = DeployModelEditPlugin.INSTANCE
                        .getString("_UI_ServerState_" + serverState.getName() //$NON-NLS-1$
                                + "_literal"); //$NON-NLS-1$
                StringBuilder sb = new StringBuilder(super.getText(object));
                sb.append(' ').append('(').append(state).append(')');
                return sb.toString();
            default:
                return super.getText(object);
            }
        } else if (object instanceof ServerElement) {
            ServerElement element = (ServerElement) object;
            ServerElementState serverElementState = element
                    .getServerElementState();
            if (serverElementState != null) {
                String state = serverElementState.getName();
                if (state != null) {
                    StringBuilder sb = new StringBuilder(super.getText(object));
                    sb.append(' ').append('(').append(state).append(')');
                    return sb.toString();
                }
            }
        }
        return getText(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object object) {
        if (object instanceof Server) {
            Server server = (Server) object;
            switch (server.getServerState().getValue()) {
            case ServerState.CONNECTED: {
                ServerItemProvider itemProvider = (ServerItemProvider) getAdapterFactory()
                        .adapt(server, IItemLabelProvider.class);
                Object imageObject = itemProvider.getResourceLocator()
                        .getImage("full/obj16/Server_connected"); //$NON-NLS-1$
                return ExtendedImageRegistry.getInstance()
                        .getImage(imageObject);
            }
            case ServerState.DISCONNECTED: {
                ServerItemProvider itemProvider = (ServerItemProvider) getAdapterFactory()
                        .adapt(server, IItemLabelProvider.class);
                Object imageObject = itemProvider.getResourceLocator()
                        .getImage("full/obj16/Server_disconnected"); //$NON-NLS-1$
                return ExtendedImageRegistry.getInstance()
                        .getImage(imageObject);
            }
            default:
                return super.getImage(object);
            }
        }
        return super.getImage(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object object) {
        return super.getText(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification) {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
            public void run() {
                fireLabelProviderChanged();
            }
        });
    }
}
