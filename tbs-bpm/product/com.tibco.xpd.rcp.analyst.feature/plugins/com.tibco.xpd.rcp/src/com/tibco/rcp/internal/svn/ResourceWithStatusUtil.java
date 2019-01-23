/*******************************************************************************
 * Copyright (c) 2005, 2006 Subclipse project and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Subclipse project committers - initial API and implementation
 ******************************************************************************/
package com.tibco.rcp.internal.svn;

import org.eclipse.core.resources.IResource;
import org.eclipse.team.core.TeamException;
import org.tigris.subversion.subclipse.core.ISVNLocalResource;
import org.tigris.subversion.subclipse.core.resources.LocalResourceStatus;
import org.tigris.subversion.subclipse.core.resources.SVNWorkspaceRoot;
import org.tigris.subversion.svnclientadapter.SVNStatusKind;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * copy of org.tigris.subversion.subclipse.ui.dialogs.ResourceWithStatusUtil as
 * getStatus method leverages on localised values
 * 
 * 
 * @author kupadhya
 * @since 24 Jan 2013
 */
public final class ResourceWithStatusUtil {
    private ResourceWithStatusUtil() {
    }

    public static SVNStatusKind getStatusKind(IResource resource) {
        ISVNLocalResource svnResource =
                SVNWorkspaceRoot.getSVNResourceFor(resource);
        SVNStatusKind statusKind = null;
        try {
            LocalResourceStatus status = svnResource.getStatus();
            if (status.isTextConflicted())
                statusKind = SVNStatusKind.CONFLICTED;
            else if (status.isAdded())
                statusKind = SVNStatusKind.ADDED;
            else if (status.isDeleted())
                statusKind = SVNStatusKind.DELETED;
            else if (status.isMissing())
                statusKind = SVNStatusKind.MISSING;
            else if (status.isReplaced())
                statusKind = SVNStatusKind.REPLACED;
            else if (status.isTextModified())
                statusKind = SVNStatusKind.MODIFIED;
            else if (!status.isManaged())
                statusKind = SVNStatusKind.UNVERSIONED;
        } catch (TeamException e) {
        }
        return statusKind;
    }

    public static String getStatus(IResource resource) {
        ISVNLocalResource svnResource =
                SVNWorkspaceRoot.getSVNResourceFor(resource);
        String result = null;
        try {
            LocalResourceStatus status = svnResource.getStatus();
            if (status.isTextConflicted())
                result = Messages.SVNCommitDialog_conflicted;
            else if (status.isAdded())
                result = Messages.SVNCommitDialog_added;
            else if (status.isDeleted())
                result = Messages.SVNCommitDialog_deleted;
            else if (status.isMissing())
                result = Messages.SVNCommitDialog_missing;
            else if (status.isReplaced())
                result = Messages.SVNCommitDialog_replaced;
            else if (status.isTextModified())
                result = Messages.SVNCommitDialog_modified;
            else if (!status.isManaged())
                result = Messages.SVNCommitDialog_unversioned;
            else
                result = ""; //$NON-NLS-1$
        } catch (TeamException e) {
            result = ""; //$NON-NLS-1$
        }
        return result;
    }

    public static String getPropertyStatus(IResource resource) {
        ISVNLocalResource svnResource =
                SVNWorkspaceRoot.getSVNResourceFor(resource);
        String result = null;
        try {
            LocalResourceStatus status = svnResource.getStatus();
            if (status.isPropConflicted())
                result = Messages.SVNCommitDialog_conflicted;
            else if ((svnResource.getStatus() != null)
                    && (svnResource.getStatus().getPropStatus() != null)
                    && (svnResource.getStatus().getPropStatus()
                            .equals(SVNStatusKind.MODIFIED)))
                result = Messages.SVNCommitDialog_modified;
            else
                result = ""; //$NON-NLS-1$
        } catch (TeamException e) {
            result = ""; //$NON-NLS-1$
        }
        return result;
    }
}
