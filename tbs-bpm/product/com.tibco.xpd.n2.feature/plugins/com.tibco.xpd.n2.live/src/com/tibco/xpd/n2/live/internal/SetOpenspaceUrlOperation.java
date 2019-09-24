/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.live.internal;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.n2.live.OpenspaceViewHelper;

/**
 * Undoable operation for setting the openspace URL.
 * 
 * @author nwilson
 * @since 9 Sep 2014
 */
public class SetOpenspaceUrlOperation extends AbstractOperation {

    /**
     * ID of the server to set the URL for.
     */
    private String serverId;

    /**
     * The new URL to set.
     */
    private String newUrl;

    /**
     * Holder for the old URL to support undo.
     */
    private String oldUrl;

    /**
     * @param serverId
     *            The server ID.
     * @param newUrl
     *            The new URL.
     */
    public SetOpenspaceUrlOperation(String serverId, String newUrl) {
        super(Messages.SetOpenspaceUrlOperation_Label);
        this.serverId = serverId;
        this.newUrl = newUrl;
    }

    /**
     * @see org.eclipse.core.commands.operations.AbstractOperation#execute(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.runtime.IAdaptable)
     * 
     * @param monitor
     * @param info
     * @return
     * @throws ExecutionException
     */
    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        oldUrl = OpenspaceViewHelper.getOpenspaceBaseUrlFromPrefs(serverId);
        OpenspaceViewHelper.saveOpenspaceBaseUrlInPreferences(newUrl, serverId);
        return Status.OK_STATUS;
    }

    /**
     * @see org.eclipse.core.commands.operations.AbstractOperation#redo(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.runtime.IAdaptable)
     * 
     * @param monitor
     * @param info
     * @return
     * @throws ExecutionException
     */
    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        OpenspaceViewHelper.saveOpenspaceBaseUrlInPreferences(newUrl, serverId);
        return Status.OK_STATUS;
    }

    /**
     * @see org.eclipse.core.commands.operations.AbstractOperation#undo(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.runtime.IAdaptable)
     * 
     * @param monitor
     * @param info
     * @return
     * @throws ExecutionException
     */
    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        OpenspaceViewHelper.saveOpenspaceBaseUrlInPreferences(oldUrl, serverId);
        return Status.OK_STATUS;
    }

}
