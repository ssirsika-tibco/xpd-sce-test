/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

class ActionCommandWrapper extends CommandWrapper implements
        CommandActionDelegate {

    private final Object image;

    /**
     * @param command
     */
    public ActionCommandWrapper(Command command, Object image) {
        super(command);
        this.image = image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.command.CommandActionDelegate#getImage()
     */
    public Object getImage() {
        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.command.CommandActionDelegate#getText()
     */
    public String getText() {
        return command.getLabel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.command.CommandActionDelegate#getToolTipText()
     */
    public String getToolTipText() {
        return command.getDescription();
    }

    /**
     * Gets image for EObject by delegating request to object's
     * IItemLabelProvider adapter.
     * 
     * @param eObject
     *            the EObject.
     * @return the image for object provided by object's adapter.
     */
    public static Object getImageForEObject(AdapterFactory adapterFactory,
            EObject eObject) {
        Adapter adapter = adapterFactory.adapt(eObject,
                IItemLabelProvider.class);
        if (adapter instanceof IItemLabelProvider) {
            return ((IItemLabelProvider) adapter).getImage(eObject);
        }
        return null;
    }

}