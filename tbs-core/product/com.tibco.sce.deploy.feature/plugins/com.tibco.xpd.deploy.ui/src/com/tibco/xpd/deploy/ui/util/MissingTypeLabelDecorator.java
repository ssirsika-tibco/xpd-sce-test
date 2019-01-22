/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Decorates RegistrySearch elements to indicate search failure.
 * <p>
 * <i>Created: 12-01-2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class MissingTypeLabelDecorator implements ILightweightLabelDecorator {

    /** Decorator ID */
    public static final String MISSING_TYPE_DECORATOR_ID = "com.tibco.xpd.deploy.ui.MissingTypeDecorator"; //$NON-NLS-1$

    /** Error overlay image */
    public static final String ERROR_OVERLAY = "icons/overlay/error.gif"; //$NON-NLS-1$
    /** Warning overlay image */
    public static final String WARNING_OVERLAY = "icons/overlay/warning.gif"; //$NON-NLS-1$

    /*
     * @see
     * org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang
     * .Object, org.eclipse.jface.viewers.IDecoration)
     */
    public void decorate(Object element, IDecoration decoration) {
        if (element instanceof Server) {
            Server server = ((Server) element);
            synchronized (server) {
                if (server.getServerType() != null
                        && !server.getServerType().isValid()) {
                    addErrorOverlay(decoration);
                    StringBuilder missingTypeMessage = new StringBuilder();
                    missingTypeMessage.append(' ');
                    missingTypeMessage.append('(');
                    missingTypeMessage
                            .append(Messages.MissingTypeLabelDecorator_missingServerType_label);
                    missingTypeMessage.append(')');
                    decoration.addSuffix(missingTypeMessage.toString());
                } else if (server.getRepository() != null
                        && server.getRepository().getRepositoryType() != null
                        && !server.getRepository().getRepositoryType()
                                .isValid()) {
                    addErrorOverlay(decoration);
                    StringBuilder missingTypeMessage = new StringBuilder();
                    missingTypeMessage.append(' ');
                    missingTypeMessage.append('(');
                    missingTypeMessage.append(Messages.MissingTypeLabelDecorator_missingRepositoryType_label);
                    missingTypeMessage.append(')');
                    decoration.addSuffix(missingTypeMessage.toString());
                }
            }
        }
        if (element instanceof ServerGroup) {
            ServerGroup serverGroup = ((ServerGroup) element);
            synchronized (serverGroup) {
                if (serverGroup.getServerGroupType() != null
                        && !serverGroup.getServerGroupType().isValid()) {
                    addErrorOverlay(decoration);
                    StringBuilder missingTypeMessage = new StringBuilder();
                    missingTypeMessage.append(' ');
                    missingTypeMessage.append('(');
                    missingTypeMessage
                            .append(Messages.MissingTypeLabelDecorator_missingGroupType_label);
                    missingTypeMessage.append(')');
                    decoration.addSuffix(missingTypeMessage.toString());
                }
            }
        }
    }

    /**
     * @param decoration
     */
    private void addErrorOverlay(IDecoration decoration) {
        ImageDescriptor errorOverlay = DeployUIActivator
                .getImageDescriptor(ERROR_OVERLAY);
        decoration.addOverlay(errorOverlay, IDecoration.BOTTOM_LEFT);
    }

    /*
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
     * jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
    }

    /*
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
    }

    /*
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
     * .Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        return true;
    }

    /*
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
     * .jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {
    }

}
