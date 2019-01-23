/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.tigris.subversion.subclipse.core.SVNException;
import org.tigris.subversion.subclipse.ui.IHelpContextIds;
import org.tigris.subversion.subclipse.ui.settings.CommentProperties;
import org.tigris.subversion.subclipse.ui.wizards.SVNWizardPage;
import org.tigris.subversion.subclipse.ui.wizards.sharing.ISVNRepositoryLocationProvider;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * copy of
 * org.tigris.subversion.subclipse.ui.wizards.sharing.SharingWizardFinishPage
 * with minor modifications.
 * 
 * @author kupadhya
 * @since 22 Jan 2013
 */
public class SVNSharingWizardFinishPage extends SVNWizardPage {
    private ISVNRepositoryLocationProvider repositoryLocationProvider;

    private SVNCommitCommentArea commitCommentArea;

    private CommentProperties commentProperties;

    public SVNSharingWizardFinishPage(String pageName, String title,
            ImageDescriptor titleImage,
            ISVNRepositoryLocationProvider repositoryLocationProvider) {
        super(pageName, title, titleImage);
        this.repositoryLocationProvider = repositoryLocationProvider;
    }

    /*
     * @see IDialogPage#createControl(Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite composite = createComposite(parent, 1);
        // set F1 help
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(composite, IHelpContextIds.SHARING_FINISH_PAGE);
        Label label = new Label(composite, SWT.LEFT | SWT.WRAP);
        label.setText(Messages.SVNSharingWizardFinishPage_message);
        GridData data = new GridData();
        data.widthHint = 350;
        label.setLayoutData(data);
        IProject project = repositoryLocationProvider.getProject();
        try {
            commentProperties = CommentProperties.getCommentProperties(project);
        } catch (SVNException e) {
        }
        commitCommentArea =
                new SVNCommitCommentArea(null, null, commentProperties);
        commitCommentArea
                .setProposedComment(Messages.SVNSharingWizard_initialImport);
        if ((commentProperties != null)
                && (commentProperties.getMinimumLogMessageSize() != 0)) {
            ModifyListener modifyListener = new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    setPageComplete(commitCommentArea.getCommentLength() >= commentProperties
                            .getMinimumLogMessageSize());
                }
            };
            commitCommentArea.setModifyListener(modifyListener);
        }
        commitCommentArea.createArea(composite);
        setControl(composite);
    }

    public String getComment() {
        return commitCommentArea.getComment();
    }
}
