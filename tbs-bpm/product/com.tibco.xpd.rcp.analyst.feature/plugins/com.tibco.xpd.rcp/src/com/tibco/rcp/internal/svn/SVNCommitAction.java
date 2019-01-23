/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.internal.core.subscribers.ActiveChangeSet;
import org.eclipse.team.internal.core.subscribers.ChangeSet;
import org.tigris.subversion.subclipse.core.ISVNLocalResource;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.core.SVNProviderPlugin;
import org.tigris.subversion.subclipse.core.commands.GetStatusCommand;
import org.tigris.subversion.subclipse.core.resources.SVNWorkspaceRoot;
import org.tigris.subversion.subclipse.core.util.File2Resource;
import org.tigris.subversion.subclipse.core.util.Util;
import org.tigris.subversion.subclipse.ui.ISVNUIConstants;
import org.tigris.subversion.subclipse.ui.SVNUIPlugin;
import org.tigris.subversion.subclipse.ui.actions.WorkbenchWindowAction;
import org.tigris.subversion.subclipse.ui.settings.ProjectProperties;
import org.tigris.subversion.subclipse.ui.wizards.dialogs.SvnWizard;
import org.tigris.subversion.subclipse.ui.wizards.dialogs.SvnWizardDialog;
import org.tigris.subversion.svnclientadapter.ISVNStatus;
import org.tigris.subversion.svnclientadapter.utils.SVNStatusUtils;

import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;

/**
 * copy of org.tigris.subversion.subclipse.ui.actions.CommitAction with some
 * minor modifications
 * 
 * @author kupadhya
 * @since 14 Dec 2012
 */
public class SVNCommitAction extends WorkbenchWindowAction {
    protected String commitComment;

    protected IResource[] resourcesToCommit;

    protected String url;

    protected boolean hasUnaddedResources;

    protected boolean commit;

    protected IResource[] selectedResources;

    private String proposedComment;

    private boolean canRunAsJob = true;

    // private boolean sharing;

    private HashMap statusMap;

    public SVNCommitAction() {

    }

    public SVNCommitAction(String proposedComment) {
        this.proposedComment = proposedComment;
    }

    /*
     * get non added resources and prompts for resources to be added prompts for
     * comments add non added files commit selected files
     * 
     * @see SVNAction#execute(IAction)
     */
    @Override
    public void execute(IAction action) throws InvocationTargetException,
            InterruptedException {
        statusMap = new HashMap();
        final IResource[] resources = getSelectedResources();
        final List resourcesToBeAdded = new ArrayList();
        final List resourcesToBeDeleted = new ArrayList();
        if (action != null && !action.isEnabled()) {
            action.setEnabled(true);
        } else {
            run(new IRunnableWithProgress() {
                @Override
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException {
                    try {

                        List<WorkingCopy> allDirtyWorkingCopiesInAProject =
                                SVNUtils.getAllDirtyWorkingCopiesInProjectArray(selectedResources,
                                        SVNUtils.getUserEditableRCPFileExtensionList());
                        if (allDirtyWorkingCopiesInAProject.size() > 0) {
                            MessageDialog messageDialog =
                                    new MessageDialog(
                                            getShell(),
                                            Messages.SVNCommitDialog_title,
                                            null,
                                            Messages.SVNCommitAction_ShouldModelsBeSaved,
                                            MessageDialog.QUESTION,
                                            new String[] {
                                                    IDialogConstants.YES_LABEL,
                                                    IDialogConstants.NO_LABEL,
                                                    IDialogConstants.CANCEL_LABEL },
                                            0);
                            messageDialog.open();
                            if (messageDialog.getReturnCode() == 0) {
                                SVNUtils.saveAllDirtyWC(allDirtyWorkingCopiesInAProject);
                            } else if (messageDialog.getReturnCode() == 2) {
                                return;
                            }
                        }
                        // search for modified or added, non-ignored resources
                        // in the selection.
                        IResource[] modified =
                                getModifiedResources(resources, monitor);

                        // if no changes since last commit, do not show commit
                        // dialog.
                        if (modified.length == 0) {
                            MessageDialog.openInformation(getShell(),
                                    Messages.SVNCommitDialog_title,
                                    Messages.SVNCommitDialog_noChanges);
                            commit = false;
                        } else {
                            ProjectProperties projectProperties =
                                    ProjectProperties
                                            .getProjectProperties(modified[0]);
                            commit = confirmCommit(modified, projectProperties);
                        }

                        // if commit was not canceled, create a list of any
                        // unversioned resources that were selected and a list
                        // of any missing
                        // resources that were selected.
                        if (commit) {
                            for (int i = 0; i < resourcesToCommit.length; i++) {
                                IResource resource = resourcesToCommit[i];
                                ISVNLocalResource svnResource =
                                        SVNWorkspaceRoot
                                                .getSVNResourceFor(resource);
                                if (!svnResource.isManaged())
                                    resourcesToBeAdded.add(resource);
                                if (svnResource.getStatus().isMissing())
                                    resourcesToBeDeleted.add(resource);
                            }
                        }
                    } catch (TeamException e) {
                        throw new InvocationTargetException(e);
                    }
                }
            },
                    true /* cancelable */,
                    PROGRESS_BUSYCURSOR); //$NON-NLS-1$

            if (!commit) {
                return; // user canceled
            }
            SVNCommitOperation commitOperation =
                    new SVNCommitOperation(getTargetPart(), resources,
                            (IResource[]) resourcesToBeAdded
                                    .toArray(new IResource[resourcesToBeAdded
                                            .size()]),
                            (IResource[]) resourcesToBeDeleted
                                    .toArray(new IResource[resourcesToBeDeleted
                                            .size()]), resourcesToCommit,
                            commitComment, false);
            commitOperation.setCanRunAsJob(canRunAsJob);
            commitOperation.run();

        }
    }

    /**
     * get the modified and unadded resources in resources parameter
     */
    @Override
    protected IResource[] getModifiedResources(IResource[] resources,
            IProgressMonitor iProgressMonitor) throws SVNException {
        IResource[] allResources = getSelectedResources(true);
        List allSelections = new ArrayList();
        for (int i = 0; i < allResources.length; i++)
            allSelections.add(allResources[i]);
        List conflictFiles = new ArrayList();
        final List modified = new ArrayList();
        List unversionedFolders = new ArrayList();
        hasUnaddedResources = false;
        for (int i = 0; i < resources.length; i++) {
            IResource resource = resources[i];
            ISVNLocalResource svnResource =
                    SVNWorkspaceRoot.getSVNResourceFor(resource);

            // This check is for when the action is called with unmanaged
            // resources
            if (svnResource.getRepository() == null) {
                continue;
            }

            // if only one resource selected, get url. Commit dialog displays
            // this.
            if (resources.length == 1) {
                url = svnResource.getStatus().getUrlString();
                if ((url == null) || (resource.getType() == IResource.FILE))
                    url = Util.getParentUrl(svnResource);
            }

            boolean descend = true;
            if (resource instanceof IContainer) {
                outer: for (int j = 0; j < allResources.length; j++) {
                    if (allResources[j] == resource)
                        continue;

                    IContainer parent = allResources[j].getParent();
                    while (parent != null) {
                        if (parent.equals(resource)) {
                            descend = false;
                            break outer;
                        }
                        parent = parent.getParent();
                    }
                }
            }
            // get adds, deletes, updates and property updates.
            GetStatusCommand command =
                    new GetStatusCommand(svnResource, descend, false);
            command.run(iProgressMonitor);
            ISVNStatus[] statuses = command.getStatuses();
            for (int j = 0; j < statuses.length; j++) {
                if (SVNStatusUtils.isReadyForCommit(statuses[j])
                        || SVNStatusUtils.isMissing(statuses[j])) {
                    IResource currentResource =
                            SVNWorkspaceRoot.getResourceFor(resource,
                                    statuses[j]);
                    if (currentResource != null
                            && (descend == true || allSelections
                                    .contains(currentResource))) {
                        ISVNLocalResource localResource =
                                SVNWorkspaceRoot
                                        .getSVNResourceFor(currentResource);
                        if (!localResource.isIgnored()) {
                            if (!SVNStatusUtils.isManaged(statuses[j])) {
                                hasUnaddedResources = true;
                                if ((currentResource.getType() != IResource.FILE)
                                        && !isSymLink(currentResource))
                                    unversionedFolders.add(currentResource);
                                else {
                                    if (!modified.contains(currentResource)) {
                                        modified.add(currentResource);
                                        if (currentResource instanceof IContainer)
                                            statusMap
                                                    .put(currentResource,
                                                            statuses[j]
                                                                    .getPropStatus());
                                        else
                                            statusMap
                                                    .put(currentResource,
                                                            statuses[j]
                                                                    .getTextStatus());
                                    }
                                }
                            } else if (!modified.contains(currentResource)) {
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

        IResource[] unaddedResources =
                getUnaddedResources(unversionedFolders, iProgressMonitor);
        for (int i = 0; i < unaddedResources.length; i++)
            if (!modified.contains(unaddedResources[i]))
                modified.add(unaddedResources[i]);
        Iterator iter = conflictFiles.iterator();
        while (iter.hasNext()) {
            IFile conflictFile = (IFile) iter.next();
            modified.remove(conflictFile);
            statusMap.remove(conflictFile);
        }
        return (IResource[]) modified.toArray(new IResource[modified.size()]);
    }

    public int getHighestProblemSeverity(IResource[] resources) {
        int mostSeriousSeverity = -1;

        for (int i = 0; i < resources.length; i++) {
            IResource resource = resources[i];
            try {
                IMarker[] problems =
                        resource.findMarkers(IMarker.PROBLEM,
                                true,
                                IResource.DEPTH_ZERO);
                for (int j = 0; j < problems.length; j++) {
                    IMarker problem = problems[j];
                    int severity = problem.getAttribute(IMarker.SEVERITY, 0);
                    if (severity > mostSeriousSeverity) {
                        mostSeriousSeverity = severity;
                    }
                }
            } catch (CoreException e) {
            }
        }

        return mostSeriousSeverity;
    }

    /**
     * prompt commit of selected resources.
     * 
     * @throws SVNException
     */
    protected boolean confirmCommit(IResource[] modifiedResources,
            ProjectProperties projectProperties) throws SVNException {
        if (onTagPath(modifiedResources)) {
            // Warning - working copy appears to be on a tag path.
            if (!MessageDialog.openQuestion(getShell(),
                    Messages.SVNCommitDialog_title,
                    Messages.SVNCommitDialog_tag))
                return false;
        }
        int highestProblemSeverity =
                getHighestProblemSeverity(modifiedResources);
        IPreferenceStore preferenceStore =
                SVNUIPlugin.getPlugin().getPreferenceStore();
        switch (highestProblemSeverity) {
        case IMarker.SEVERITY_WARNING:
            String allowCommitsWithWarnings =
                    preferenceStore
                            .getString(ISVNUIConstants.PREF_ALLOW_COMMIT_WITH_WARNINGS);
            if (MessageDialogWithToggle.PROMPT.equals(allowCommitsWithWarnings)
                    || MessageDialogWithToggle.NEVER
                            .equals(allowCommitsWithWarnings)) {
                MessageDialogWithToggle warningDialog =
                        MessageDialogWithToggle
                                .openYesNoQuestion(shell,
                                        Messages.SVNCommitWizard_commitResources,
                                        Messages.SVNCommitWizard_warningMarkers,
                                        Messages.SVNCommitWizard_warningQuestion,
                                        false,
                                        preferenceStore,
                                        ISVNUIConstants.PREF_ALLOW_COMMIT_WITH_WARNINGS);
                if (IDialogConstants.YES_ID != warningDialog.getReturnCode()) {
                    return false;
                }
            }
            break;
        case IMarker.SEVERITY_ERROR:
            String allowCommitsWithErrors =
                    preferenceStore
                            .getString(ISVNUIConstants.PREF_ALLOW_COMMIT_WITH_ERRORS);
            if (MessageDialogWithToggle.PROMPT.equals(allowCommitsWithErrors)
                    || MessageDialogWithToggle.NEVER
                            .equals(allowCommitsWithErrors)) {
                MessageDialogWithToggle errorDialog =
                        MessageDialogWithToggle.openYesNoQuestion(shell,
                                Messages.SVNCommitWizard_commitResources,
                                Messages.SVNCommitWizard_errorMarkers,
                                Messages.SVNCommitWizard_errorQuestion,
                                false,
                                preferenceStore,
                                ISVNUIConstants.PREF_ALLOW_COMMIT_WITH_ERRORS);
                if (IDialogConstants.YES_ID != errorDialog.getReturnCode()) {
                    return false;
                }
            }
            break;
        }

        SVNWizardCommitPage commitPage =
                new SVNWizardCommitPage(modifiedResources, url,
                        projectProperties, statusMap, null, false);
        // commitPage.setSharing(sharing);

        SvnWizard wizard = new SvnWizard(commitPage);
        SvnWizardDialog dialog = new SvnWizardDialog(getShell(), wizard);
        if (proposedComment == null || proposedComment.length() == 0) {
            commitPage.setComment(getProposedComment(modifiedResources));
        } else {
            commitPage.setComment(proposedComment);
        }
        wizard.setParentDialog(dialog);
        boolean commitOK = (dialog.open() == SvnWizardDialog.OK);
        url = null;
        commitComment = commitPage.getComment();
        resourcesToCommit = commitPage.getSelectedResources();
        return commitOK;
    }

    private boolean onTagPath(IResource[] modifiedResources)
            throws SVNException {
        // Multiple resources selected.
        if (url == null) {
            IResource resource = modifiedResources[0];
            ISVNLocalResource svnResource =
                    SVNWorkspaceRoot.getSVNResourceFor(resource);
            String firstUrl = svnResource.getStatus().getUrlString();
            if ((firstUrl == null) || (resource.getType() == IResource.FILE))
                firstUrl = Util.getParentUrl(svnResource);
            if (firstUrl.indexOf("/tags/") != -1)return true; //$NON-NLS-1$
        }
        // One resource selected.
        else if (url.indexOf("/tags/") != -1)return true; //$NON-NLS-1$
        return false;
    }

    /**
     * @see org.tigris.subversion.subclipse.ui.actions.SVNAction#getErrorTitle()
     */
    @Override
    protected String getErrorTitle() {
        return Messages.SVNCommitAction_commitFailed;
    }

    /**
     * @see org.tigris.subversion.subclipse.ui.actions.WorkspaceAction#isEnabledForUnmanagedResources()
     */
    @Override
    protected boolean isEnabledForUnmanagedResources() {
        return true;
    }

    @Override
    protected boolean isEnabled() throws TeamException {

        // invoke the inherited method so that overlaps are maintained
        IResource[] resources = super.getSelectedResources();

        // disable if no resources are selected
        if (resources.length == 0)
            return false;

        // validate enabled for each resource in the selection
        for (int i = 0; i < resources.length; i++) {
            IResource resource = resources[i];

            // no SVN actions are enabled if the selection contains a linked
            // resource
            if (SVNWorkspaceRoot.isLinkedResource(resource))
                return false;

            // only enable for resources in a project shared with SVN
            if (RepositoryProvider.getProvider(resource.getProject(),
                    SVNProviderPlugin.getTypeId()) == null) {
                return false;
            }

            // ensure that resource management state matches what the action
            // requires
            ISVNLocalResource svnResource =
                    SVNWorkspaceRoot.getSVNResourceFor(resource);
            if (!isEnabledForSVNResource(svnResource)) {
                return false;
            }
        }
        return true;
    }

    /**
     * get the unadded resources in resources parameter
     */
    private IResource[] getUnaddedResources(List resources,
            IProgressMonitor iProgressMonitor) throws SVNException {
        final List unadded = new ArrayList();
        final SVNException[] exception = new SVNException[] { null };
        for (Iterator iter = resources.iterator(); iter.hasNext();) {
            IResource resource = (IResource) iter.next();
            if (resource.exists()) {
                // visit each resource deeply
                try {
                    resource.accept(new IResourceVisitor() {
                        @Override
                        public boolean visit(IResource aResource) {
                            ISVNLocalResource svnResource =
                                    SVNWorkspaceRoot
                                            .getSVNResourceFor(aResource);
                            // skip ignored resources and their children
                            try {
                                if (svnResource.isIgnored())
                                    return false;
                                // visit the children of shared resources
                                if (svnResource.isManaged())
                                    return true;
                                if ((aResource.getType() == IResource.FOLDER)
                                        && isSymLink(aResource)) // don't
                                                                 // traverse
                                                                 // into symlink
                                                                 // folders
                                    return false;
                            } catch (SVNException e) {
                                exception[0] = e;
                            }
                            // file/folder is unshared so record it
                            unadded.add(aResource);
                            return aResource.getType() == IResource.FOLDER;
                        }
                    },
                            IResource.DEPTH_INFINITE,
                            false /* include phantoms */);
                } catch (CoreException e) {
                    throw SVNException.wrapException(e);
                }
                if (exception[0] != null)
                    throw exception[0];
            }
        }
        if (unadded.size() > 0)
            hasUnaddedResources = true;
        return (IResource[]) unadded.toArray(new IResource[unadded.size()]);
    }

    protected boolean isSymLink(IResource resource) {
        File file = resource.getLocation().toFile();
        try {
            if (!file.exists())
                return true;
            else {
                String cnnpath = file.getCanonicalPath();
                String abspath = file.getAbsolutePath();
                return !abspath.equals(cnnpath);
            }
        } catch (IOException ex) {
            return true;
        }
    }

    @Override
    protected IResource[] getSelectedResources() {
        if (selectedResources == null)
            return super.getSelectedResources();
        else
            return selectedResources;
    }

    public void setSelectedResources(IResource[] selectedResources) {
        this.selectedResources = selectedResources;
    }

    /*
     * Get a proposed comment by looking at the active change sets
     */
    private String getProposedComment(IResource[] resourcesToCommit) {
        StringBuffer comment = new StringBuffer();
        ChangeSet[] sets =
                SVNProviderPlugin.getPlugin().getChangeSetManager().getSets();
        int numMatchedSets = 0;
        for (int i = 0; i < sets.length; i++) {
            ChangeSet set = sets[i];
            if (isUserSet(set) && containsOne(set, resourcesToCommit)) {
                if (numMatchedSets > 0)
                    comment.append(System.getProperty("line.separator")); //$NON-NLS-1$
                comment.append(set.getComment());
                numMatchedSets++;
            }
        }
        return comment.toString();
    }

    private boolean isUserSet(ChangeSet set) {
        if (set instanceof ActiveChangeSet) {
            ActiveChangeSet acs = (ActiveChangeSet) set;
            return acs.isUserCreated();
        }
        return false;
    }

    private boolean containsOne(ChangeSet set, IResource[] resourcesToCommit) {
        for (int j = 0; j < resourcesToCommit.length; j++) {
            IResource resource = resourcesToCommit[j];
            if (set.contains(resource)) {
                return true;
            }
            if (set instanceof ActiveChangeSet) {
                ActiveChangeSet acs = (ActiveChangeSet) set;
                if (acs.getDiffTree().members(resource).length > 0)
                    return true;
            }
        }
        return false;
    }

    public boolean hasOutgoingChanges() {
        try {
            return getModifiedResources(selectedResources,
                    new NullProgressMonitor()).length > 0;
        } catch (SVNException e) {
        }
        return false;
    }

    // public void setSharing(boolean sharing) {
    // this.sharing = sharing;
    // }

    @Override
    protected String getImageId() {
        return ISVNUIConstants.IMG_MENU_COMMIT;
    }

    public void setCanRunAsJob(boolean canRunAsJob) {
        this.canRunAsJob = canRunAsJob;
    }

}
