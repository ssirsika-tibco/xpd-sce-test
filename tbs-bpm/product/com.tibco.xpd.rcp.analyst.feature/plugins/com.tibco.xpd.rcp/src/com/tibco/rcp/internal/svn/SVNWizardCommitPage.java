/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

/**
 * copy of org.tigris.subversion.subclipse.ui.wizards.dialogs.SvnWizardCommitPage with minor modifications
 *
 * @author kupadhya
 * @since 14 Dec 2012
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerSwitchingPane;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.team.core.synchronize.SyncInfoSet;
import org.eclipse.team.internal.core.subscribers.ChangeSet;
import org.eclipse.ui.PlatformUI;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.ui.IHelpContextIds;
import org.tigris.subversion.subclipse.ui.ISVNUIConstants;
import org.tigris.subversion.subclipse.ui.SVNUIPlugin;
import org.tigris.subversion.subclipse.ui.actions.SVNPluginAction;
import org.tigris.subversion.subclipse.ui.comments.CommitCommentArea;
import org.tigris.subversion.subclipse.ui.settings.CommentProperties;
import org.tigris.subversion.subclipse.ui.settings.ProjectProperties;
import org.tigris.subversion.subclipse.ui.util.LinkList;
import org.tigris.subversion.subclipse.ui.wizards.IClosableWizard;
import org.tigris.subversion.subclipse.ui.wizards.dialogs.SvnWizardDialog;
import org.tigris.subversion.subclipse.ui.wizards.dialogs.SvnWizardDialogPage;

import com.tibco.xpd.rcp.internal.Messages;

public class SVNWizardCommitPage extends SvnWizardDialogPage {
    public static final String COMMIT_WIZARD_DIALOG_SETTINGS = "CommitWizard"; //$NON-NLS-1$

    public static final String SHOW_COMPARE = "ShowCompare"; //$NON-NLS-1$

    private static final String H_WEIGHT_1 = "HWeight1"; //$NON-NLS-1$

    private static final String H_WEIGHT_2 = "HWeight2"; //$NON-NLS-1$

    private static final String V_WEIGHT_1 = "VWeight1"; //$NON-NLS-1$

    private static final String V_WEIGHT_2 = "VWeight2"; //$NON-NLS-1$

    private SashForm verticalSash;

    private SashForm horizontalSash;

    private boolean showCompare;

    private SVNCommitCommentArea commitCommentArea;

    private IResource[] resourcesToCommit;

    private ProjectProperties projectProperties;

    private Object[] selectedResources;

    private Text issueText;

    private String issue;

    private Button showCompareButton;

    private boolean includeUnversioned;

    private IDialogSettings settings;

    private CommentProperties commentProperties;

    private SyncInfoSet syncInfoSet;

    private String removalError;

    private boolean fromSyncView;

    private HashMap statusMap;

    private SVNResourceSelectionTree resourceSelectionTree;

    private CompareViewerSwitchingPane compareViewerPane;

    private static final int MIN_COMMENT_LENGTH = 0;

    public SVNWizardCommitPage(IResource[] resourcesToCommit, String url,
            ProjectProperties projectProperties, HashMap statusMap,
            ChangeSet changeSet, boolean fromSyncView) {
        super("CommitDialog", null); //$NON-NLS-1$ 
        setImageDescriptor(SVNUtils.getSVNWizardImageDescriptor());
        this.fromSyncView = fromSyncView;
        if (fromSyncView)
            includeUnversioned = true;
        else
            includeUnversioned =
                    SVNUIPlugin
                            .getPlugin()
                            .getPreferenceStore()
                            .getBoolean(ISVNUIConstants.PREF_SELECT_UNADDED_RESOURCES_ON_COMMIT);

        this.resourcesToCommit = resourcesToCommit;
        // this.url = url;
        this.projectProperties = projectProperties;
        this.statusMap = statusMap;
        // this.changeSet = changeSet;
        settings = SVNUIPlugin.getPlugin().getDialogSettings();
        if (changeSet == null) {
            if (url == null)
                setTitle(Messages.SVNCommitDialog_commitTo
                        + " " + Messages.SVNCommitDialog_multiple); //$NON-NLS-1$ 
            else
                setTitle(Messages.SVNCommitDialog_commitTo + " " + url); //$NON-NLS-1$        
        } else {
            setTitle(Messages.SVNCommitDialog_commitToChangeSet
                    + " " + changeSet.getName()); //$NON-NLS-1$       
        }
        if (resourcesToCommit.length > 0) {
            try {
                commentProperties =
                        CommentProperties
                                .getCommentProperties(resourcesToCommit[0]);
                commentProperties.setMinimumLogMessageSize(MIN_COMMENT_LENGTH);
            } catch (SVNException e) {
            }
        }
        commitCommentArea =
                new SVNCommitCommentArea(null, null, commentProperties);
        commitCommentArea.setShowLabel(false);
        if ((commentProperties != null)
                && (commentProperties.getMinimumLogMessageSize() != 0)) {
            ModifyListener modifyListener = new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    setPageComplete(canFinish());
                }
            };
            commitCommentArea.setModifyListener(modifyListener);
        }
    }

    @Override
    public void createControls(Composite composite) {
        horizontalSash = new SashForm(composite, SWT.HORIZONTAL);
        horizontalSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));

        verticalSash = new SashForm(horizontalSash, SWT.VERTICAL);
        // verticalSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
        // true));

        GridLayout gridLayout = new GridLayout();
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        verticalSash.setLayout(gridLayout);
        verticalSash.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite cTop = new Composite(verticalSash, SWT.NULL);
        GridLayout topLayout = new GridLayout();
        topLayout.marginHeight = 0;
        topLayout.marginWidth = 0;
        cTop.setLayout(topLayout);
        cTop.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite cBottom1 = new Composite(verticalSash, SWT.NULL);
        GridLayout bottom1Layout = new GridLayout();
        bottom1Layout.marginHeight = 0;
        bottom1Layout.marginWidth = 0;
        cBottom1.setLayout(bottom1Layout);
        cBottom1.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite cBottom2 = new Composite(cBottom1, SWT.NULL);
        GridLayout bottom2Layout = new GridLayout();
        bottom2Layout.marginHeight = 0;
        bottom2Layout.marginWidth = 0;
        cBottom2.setLayout(bottom2Layout);
        cBottom2.setLayoutData(new GridData(GridData.FILL_BOTH));

        try {
            int[] weights = new int[2];
            weights[0] = settings.getInt("CommitDialog.weights.0"); //$NON-NLS-1$
            weights[1] = settings.getInt("CommitDialog.weights.1"); //$NON-NLS-1$
            verticalSash.setWeights(weights);
        } catch (Exception e) {
            verticalSash.setWeights(new int[] { 5, 4 });
        }

        verticalSash.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent e) {
                int[] weights = verticalSash.getWeights();
                for (int i = 0; i < weights.length; i++)
                    settings.put("CommitDialog.weights." + i, weights[i]); //$NON-NLS-1$ 
            }
        });

        if (projectProperties != null) {
            if (projectProperties.getMessage() != null) {
                addBugtrackingArea(cTop);
            }
        }

        commitCommentArea.createArea(cTop);
        commitCommentArea
                .addPropertyChangeListener(new IPropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent event) {
                        if (event.getProperty() == CommitCommentArea.OK_REQUESTED
                                && canFinish()) {
                            IClosableWizard wizard =
                                    (IClosableWizard) getWizard();
                            wizard.finishAndClose();
                        }
                    }
                });

        addResourcesArea(cBottom2);

        // set F1 help
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(composite, IHelpContextIds.COMMIT_DIALOG);
        setPageComplete(canFinish());

        compareViewerPane =
                new CompareViewerSwitchingPane(horizontalSash, SWT.BORDER
                        | SWT.FLAT) {
                    @Override
                    protected Viewer getViewer(Viewer oldViewer, Object input) {
                        CompareConfiguration cc = new CompareConfiguration();
                        cc.setLeftEditable(false);
                        cc.setRightEditable(false);
                        return CompareUI.findContentViewer(oldViewer,
                                input,
                                this,
                                cc);
                    }
                };
        compareViewerPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));

        IDialogSettings section =
                settings.getSection(COMMIT_WIZARD_DIALOG_SETTINGS);
        showCompare =
                section == null ? false : section.getBoolean(SHOW_COMPARE);
        int vWeight1 = 50;
        int vWeight2 = 50;
        if (section != null) {
            try {
                vWeight1 = section.getInt(V_WEIGHT_1);
                vWeight2 = section.getInt(V_WEIGHT_2);
            } catch (NumberFormatException e) {
            }
        }

        int hWeight1 = 35;
        int hWeight2 = 65;
        if (section != null) {
            try {
                hWeight1 = section.getInt(H_WEIGHT_1);
                hWeight2 = section.getInt(H_WEIGHT_2);
            } catch (NumberFormatException e) {
            }
        }

        if (!showCompare) {
            horizontalSash.setMaximizedControl(verticalSash);
        } else {
            showCompareButton.setSelection(true);
        }

        verticalSash.setWeights(new int[] { vWeight1, vWeight2 });
        horizontalSash.setWeights(new int[] { hWeight1, hWeight2 });

    }

    public void updatePreference(boolean includeUnversioned) {
        SVNUIPlugin
                .getPlugin()
                .getPreferenceStore()
                .setValue(ISVNUIConstants.PREF_SELECT_UNADDED_RESOURCES_ON_COMMIT,
                        includeUnversioned);
    }

    private void addResourcesArea(Composite composite) {
        // get the toolbar actions from any contributing plug-in
        final SVNPluginAction[] toolbarActions =
                SVNUIPlugin.getCommitDialogToolBarActions();

        resourceSelectionTree =
                new SVNResourceSelectionTree(composite, SWT.NONE,
                        Messages.SVNGenerateSVNDiff_Changes, resourcesToCommit,
                        statusMap, null, true, null, syncInfoSet);
        MyTreeFilter fileFilter = new MyTreeFilter();
        resourceSelectionTree.getTreeViewer().addFilter(fileFilter);

        resourceSelectionTree
                .setRemoveFromViewValidator(new SVNResourceSelectionTree.IRemoveFromViewValidator() {
                    @Override
                    public boolean canRemove(ArrayList resourceList,
                            IStructuredSelection selection) {
                        return removalOk(resourceList, selection);
                    }

                    @Override
                    public String getErrorMessage() {
                        return removalError;
                    }
                });
        resourceSelectionTree.getTreeViewer()
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        selectedResources =
                                resourceSelectionTree.getSelectedResources();
                        setPageComplete(canFinish());
                        // need to update the toolbar actions too - but we use
                        // the tree viewer's selection
                        if (toolbarActions.length > 0) {
                            ISelection selection =
                                    resourceSelectionTree.getTreeViewer()
                                            .getSelection();
                            for (int i = 0; i < toolbarActions.length; i++) {
                                SVNPluginAction action = toolbarActions[i];
                                action.selectionChanged(selection);
                            }
                        }
                    }
                });
        ((CheckboxTreeViewer) resourceSelectionTree.getTreeViewer())
                .addCheckStateListener(new ICheckStateListener() {
                    @Override
                    public void checkStateChanged(CheckStateChangedEvent event) {
                        selectedResources =
                                resourceSelectionTree.getSelectedResources();
                    }
                });
        if (!includeUnversioned) {
            resourceSelectionTree.removeUnversioned();
        }
        selectedResources = resourceSelectionTree.getSelectedResources();
        setPageComplete(canFinish());
    }

    private void addBugtrackingArea(Composite composite) {
        Composite bugtrackingComposite = new Composite(composite, SWT.NULL);
        GridLayout bugtrackingLayout = new GridLayout();
        bugtrackingLayout.numColumns = 2;
        bugtrackingComposite.setLayout(bugtrackingLayout);

        Label label = new Label(bugtrackingComposite, SWT.NONE);
        label.setText(projectProperties.getLabel());
        issueText = new Text(bugtrackingComposite, SWT.BORDER);
        GridData data = new GridData();
        data.widthHint = 150;
        issueText.setLayoutData(data);
    }

    @Override
    public boolean performCancel() {
        return true;
    }

    @Override
    public boolean performFinish() {

        if (confirmUserData() == false) {
            return false;
        }

        selectedResources = resourceSelectionTree.getSelectedResources();
        int[] hWeights = horizontalSash.getWeights();
        int[] vWeights = verticalSash.getWeights();
        IDialogSettings section =
                settings.getSection(COMMIT_WIZARD_DIALOG_SETTINGS);
        if (section == null)
            section = settings.addNewSection(COMMIT_WIZARD_DIALOG_SETTINGS);
        if (showCompare) {
            section.put(H_WEIGHT_1, hWeights[0]);
            section.put(H_WEIGHT_2, hWeights[1]);
        }
        section.put(V_WEIGHT_1, vWeights[0]);
        section.put(V_WEIGHT_2, vWeights[1]);
        section.put(SHOW_COMPARE, showCompare);
        return true;
    }

    private boolean confirmUserData() {

        if (projectProperties != null) {
            int issueCount = 0;
            if (projectProperties.getMessage() != null) {

                issue = issueText.getText().trim();
                if (issue.length() > 0) {
                    String issueError = projectProperties.validateIssue(issue);
                    if (issueError != null) {
                        MessageDialog.openError(getShell(),
                                Messages.SVNCommitDialog_title,
                                issueError);
                        issueText.selectAll();
                        issueText.setFocus();
                        return false;
                    } else {
                        issueCount++;
                    }
                }
            }
            if (projectProperties.getLogregex() != null) {

                try {
                    LinkList linkList =
                            projectProperties.getLinkList(commitCommentArea
                                    .getComment());
                    String[] urls = linkList.getUrls();
                    issueCount += urls.length;

                } catch (Exception e) {
                    handle(e, null, null);
                }
            }
            if (projectProperties.isWarnIfNoIssue()) {

                if (issueCount == 0) {
                    if ((projectProperties.getMessage() != null)
                            && (projectProperties.getLogregex() == null)) {
                        if (!MessageDialog
                                .openQuestion(getShell(),
                                        Messages.SVNCommitDialog_title,
                                        String.format(Messages.SVNCommitDialog_LogRegexIsNull,
                                                projectProperties.getLabel()))) {
                            issueText.setFocus();
                            return false;
                        }
                    } else if ((projectProperties.getMessage() == null)
                            && (projectProperties.getLogregex() != null)) {
                        if (!MessageDialog.openQuestion(getShell(),
                                Messages.SVNCommitDialog_title,
                                Messages.SVNCommitDialog_NoIssueKey)) {
                            commitCommentArea.setFocus();
                            return false;
                        }
                    } else if ((projectProperties.getMessage() != null)
                            && (projectProperties.getLogregex() != null)) {
                        if (!MessageDialog
                                .openQuestion(getShell(),
                                        Messages.SVNCommitDialog_title,
                                        String.format(Messages.SVNCommitDialog_MessageDoesNotContainIssue,
                                                projectProperties.getLabel()))) {
                            commitCommentArea.setFocus();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void showComparePane(boolean showCompare) {
        this.showCompare = showCompare;
        if (showCompare) {
            horizontalSash.setMaximizedControl(null);
        } else {
            horizontalSash.setMaximizedControl(verticalSash);
        }

    }

    private boolean removalOk(ArrayList resourceList,
            IStructuredSelection selection) {
        ArrayList clonedList = (ArrayList) resourceList.clone();
        List deletedFolders = new ArrayList();
        Iterator iter = selection.iterator();
        while (iter.hasNext())
            clonedList.remove(iter.next());
        ArrayList folderPropertyChanges = new ArrayList();
        boolean folderDeletionSelected = false;
        iter = clonedList.iterator();
        while (iter.hasNext()) {
            IResource resource = (IResource) iter.next();
            if (resource instanceof IContainer) {
                if (ResourceWithStatusUtil.getStatus(resource)
                        .equals(Messages.SVNCommitDialog_deleted)) {
                    folderDeletionSelected = true;
                    deletedFolders.add(resource);
                }
                String propertyStatus =
                        ResourceWithStatusUtil.getPropertyStatus(resource);
                if (propertyStatus != null && propertyStatus.length() > 0)
                    folderPropertyChanges.add(resource);
            }
        }
        if (folderDeletionSelected) {
            iter = selection.iterator();
            while (iter.hasNext()) {
                IResource resource = (IResource) iter.next();
                Iterator iter2 = deletedFolders.iterator();
                while (iter2.hasNext()) {
                    IContainer deletedFolder = (IContainer) iter2.next();
                    if (isChild(resource, deletedFolder)) {
                        removalError = Messages.SVNCommitDialog_parentDeleted;
                        return false;
                    }
                }
            }
        }
        if (!folderDeletionSelected || folderPropertyChanges.size() == 0)
            return true;
        boolean unselectedPropChangeChildren = false;
        iter = folderPropertyChanges.iterator();
        outer: while (iter.hasNext()) {
            IContainer container = (IContainer) iter.next();
            for (int i = 0; i < resourcesToCommit.length; i++) {
                if (!clonedList.contains(resourcesToCommit[i])) {
                    if (isChild(resourcesToCommit[i], container)) {
                        unselectedPropChangeChildren = true;
                        removalError =
                                Messages.SVNCommitDialog_unselectedPropChangeChildren;
                        break outer;
                    }
                }
            }
        }
        return !unselectedPropChangeChildren;
    }

    private boolean isChild(IResource resource, IContainer folder) {
        IContainer container = resource.getParent();
        while (container != null) {
            if (container.getFullPath().toString()
                    .equals(folder.getFullPath().toString()))
                return true;
            container = container.getParent();
        }
        return false;
    }

    @Override
    public void setMessage() {
        setMessage(Messages.SVNCommitDialog_message);
    }

    private boolean canFinish() {
        selectedResources = resourceSelectionTree.getSelectedResources();
        if (selectedResources.length == 0) {
            return false;
        }
        if (commentProperties == null)
            return true;
        else
            return commitCommentArea.getCommentLength() >= commentProperties
                    .getMinimumLogMessageSize();
    }

    public String getComment() {
        String comment = null;
        if ((projectProperties != null) && (issue != null)
                && (issue.length() > 0)) {
            if (projectProperties.isAppend())
                comment =
                        commitCommentArea.getComment()
                                + "\n" + projectProperties.getResolvedMessage(issue) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
            else
                comment =
                        projectProperties.getResolvedMessage(issue)
                                + "\n" + commitCommentArea.getComment(); //$NON-NLS-1$
        } else
            comment = commitCommentArea.getComment();
        commitCommentArea.addComment(commitCommentArea.getComment());
        return comment;
    }

    public IResource[] getSelectedResources() {
        if (selectedResources != null && selectedResources.length > 0) {
            return resourcesToCommit;
        }
        return new IResource[0];
    }

    public void setComment(String proposedComment) {
        commitCommentArea.setProposedComment(proposedComment);
    }

    @Override
    public void saveSettings() {
    }

    @Override
    public String getWindowTitle() {
        return Messages.SVNCommitDialog_title;
    }

    public void setSyncInfoSet(SyncInfoSet syncInfoSet) {
        this.syncInfoSet = syncInfoSet;
    }

    @Override
    public void createButtonsForButtonBar(Composite parent,
            SvnWizardDialog wizardDialog) {
    }

    protected void handle(Exception exception, String title, String message) {
        SVNUIPlugin.openError(getShell(),
                title,
                message,
                exception,
                SVNUIPlugin.LOG_NONTEAM_EXCEPTIONS);
    }

    class MyTreeFilter extends ViewerFilter {

        /**
         * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param parentElement
         * @param element
         * @return
         */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof IFile) {
                return false;
            }
            if (parentElement instanceof IContainer
                    && element instanceof IFolder) {
                return false;
            }
            return true;
        }
    }

}
