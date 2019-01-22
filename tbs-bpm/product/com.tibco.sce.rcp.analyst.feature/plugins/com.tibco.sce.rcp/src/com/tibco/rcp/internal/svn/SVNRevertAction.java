/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.tigris.subversion.subclipse.core.ISVNLocalResource;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.core.commands.GetStatusCommand;
import org.tigris.subversion.subclipse.core.resources.SVNWorkspaceRoot;
import org.tigris.subversion.subclipse.core.util.File2Resource;
import org.tigris.subversion.subclipse.core.util.Util;
import org.tigris.subversion.subclipse.ui.actions.RevertAction;
import org.tigris.subversion.subclipse.ui.operations.RevertOperation;
import org.tigris.subversion.svnclientadapter.ISVNStatus;
import org.tigris.subversion.svnclientadapter.utils.SVNStatusUtils;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * copy of org.tigris.subversion.subclipse.ui.actions.RevertAction with minor
 * modifications
 * 
 * @author kupadhya
 * @since 13 Dec 2012
 */
public class SVNRevertAction extends RevertAction {

    private String url;

    private IResource[] resourcesToRevert;

    private HashMap statusMap;

    private IResource[] selectedResources;

    private boolean canRunAsJob = true;

    @Override
    protected void execute(final IAction action)
            throws InvocationTargetException, InterruptedException {
        statusMap = new HashMap();
        final IResource[] resources = getSelectedResources();
        try {
            IResource[] modifiedResources =
                    getModifiedResources(resources, new NullProgressMonitor());
            if (modifiedResources.length < 1) {
                MessageDialog.openInformation(getShell(),
                        Messages.SVNRevertAction_Revert,
                        Messages.SVNRevertAction_NoLocalChangesToRevert);
            } else {
                boolean revertChanges =
                        MessageDialog
                                .openQuestion(getShell(),
                                        Messages.SVNRevertAction_Revert,
                                        Messages.SVNRevertAction_AllLocalChangesWillBeLost);
                if (revertChanges) {
                    RevertOperation revertOperation =
                            new RevertOperation(getTargetPart(), resources);
                    revertOperation.setRecurse(true);
                    revertOperation.setResourcesToRevert(modifiedResources);
                    revertOperation.setCanRunAsJob(canRunAsJob);
                    revertOperation.run();
                }
            }
        } catch (SVNException e) {
            throw new InvocationTargetException(e);
        }
    }

    /**
     * get the modified resources in resources parameter
     */
    @Override
    protected IResource[] getModifiedResources(IResource[] resources,
            IProgressMonitor iProgressMonitor) throws SVNException {
        // if only one resource selected, get url. Revert dialog displays this.
        if (resources.length == 1) {
            ISVNLocalResource svnResource =
                    SVNWorkspaceRoot.getSVNResourceFor(resources[0]);
            url = svnResource.getStatus().getUrlString();
            if ((url == null) || (resources[0].getType() == IResource.FILE))
                url = Util.getParentUrl(svnResource);
        }
        ArrayList conflictFiles = new ArrayList();
        final List modified = new ArrayList();
        for (int i = 0; i < resources.length; i++) {
            IResource resource = resources[i];
            ISVNLocalResource svnResource =
                    SVNWorkspaceRoot.getSVNResourceFor(resource);
            // get adds, deletes, updates and property updates.
            GetStatusCommand command =
                    new GetStatusCommand(svnResource, true, false);
            command.run(iProgressMonitor);
            ISVNStatus[] statuses = command.getStatuses();
            for (int j = 0; j < statuses.length; j++) {
                if (SVNStatusUtils.isReadyForRevert(statuses[j])
                        || !SVNStatusUtils.isManaged(statuses[j])) {
                    IResource currentResource =
                            SVNWorkspaceRoot.getResourceFor(resource,
                                    statuses[j]);
                    if (currentResource != null) {
                        ISVNLocalResource localResource =
                                SVNWorkspaceRoot
                                        .getSVNResourceFor(currentResource);
                        if (!localResource.isIgnored()) {
                            if (SVNStatusUtils.isManaged(statuses[j])
                                    || !Util.isSpecialEclipseFile(currentResource)) {
                                modified.add(currentResource);
                                if (currentResource instanceof IContainer)
                                    statusMap.put(currentResource,
                                            statuses[j].getPropStatus());
                                else {
                                    statusMap.put(currentResource,
                                            statuses[j].getTextStatus());
                                    if (SVNStatusUtils
                                            .isTextConflicted(statuses[j])) {
                                        IFile conflictNewFile =
                                                (IFile) File2Resource
                                                        .getResource(statuses[j]
                                                                .getConflictNew());
                                        if (conflictNewFile != null)
                                            conflictFiles.add(conflictNewFile);
                                        IFile conflictOldFile =
                                                (IFile) File2Resource
                                                        .getResource(statuses[j]
                                                                .getConflictOld());
                                        if (conflictOldFile != null)
                                            conflictFiles.add(conflictOldFile);
                                        IFile conflictWorkingFile =
                                                (IFile) File2Resource
                                                        .getResource(statuses[j]
                                                                .getConflictWorking());
                                        if (conflictWorkingFile != null)
                                            conflictFiles
                                                    .add(conflictWorkingFile);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Iterator iter = conflictFiles.iterator();
        while (iter.hasNext()) {
            IFile conflictFile = (IFile) iter.next();
            modified.remove(conflictFile);
            statusMap.remove(conflictFile);
        }
        return (IResource[]) modified.toArray(new IResource[modified.size()]);
    }

}
