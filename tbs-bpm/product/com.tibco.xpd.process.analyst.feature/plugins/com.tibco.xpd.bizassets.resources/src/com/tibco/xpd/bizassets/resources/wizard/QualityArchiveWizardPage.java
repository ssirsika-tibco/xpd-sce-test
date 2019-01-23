/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.bizassets.resources.internal.Messages;
import com.tibco.xpd.bizassets.resources.quality.ProjectNatureFilter;
import com.tibco.xpd.bizassets.resources.quality.QualityArchiveNature;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.resources.FolderSelectionDialog;
import com.tibco.xpd.ui.resources.SubtreeViewerFilter;
import com.tibco.xpd.ui.resources.TypedViewerFilter;

/**
 * @author nwilson
 */
public class QualityArchiveWizardPage extends AbstractXpdWizardPage implements
        IAssetWizardPage {

    private Text project;

    private IProject selected;

    private QualityProcessAssetConfiguration configuration;

    /**
     * @param pageName
     */
    public QualityArchiveWizardPage() {
        super(Messages.QualityArchiveWizardPage_QualityArchivePageName);
        setTitle(Messages.QualityArchiveWizardPage_QualityArchiveTitle);
        setMessage(Messages.QualityArchiveWizardPage_QualityArchiveMessage);
    }

    /**
     * @param config
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage#init(java.lang.Object)
     */
    @Override
    public void init(Object config) {
        if (config instanceof QualityProcessAssetConfiguration) {
            configuration = (QualityProcessAssetConfiguration) config;
        }
        updateConfiguration();
    }

    /**
     * @param parent
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(final Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(3, false);
        composite.setLayout(layout);

        if (QualityArchiveUtil.getQualityArchiveProjects().size() == 0) {
            Label noProjectsLabel = new Label(composite, SWT.WRAP);
            noProjectsLabel
                    .setText(Messages.QualityArchiveWizardPage_NoQualityProjectMessage);
            GridData gd1 = new GridData(SWT.LEFT, SWT.TOP, true, true);
            gd1.horizontalSpan = 3;
            gd1.widthHint = 300;
            noProjectsLabel.setLayoutData(gd1);
        } else {

            Label projectLabel = new Label(composite, SWT.NONE);
            projectLabel
                    .setText(Messages.QualityArchiveWizardPage_QualityArchiveProjectLabel);
            GridData gd1 = new GridData(SWT.LEFT, SWT.CENTER, false, false);
            projectLabel.setLayoutData(gd1);

            project = new Text(composite, SWT.BORDER);
            GridData gd2 = new GridData(SWT.FILL, SWT.CENTER, true, false);
            project.setLayoutData(gd2);
            project.setEditable(false);
            IProject active =
                    QualityArchiveUtil.getActiveQualityArchiveProject();
            if (active != null) {
                project.setText(active.getName());
            }
            selected = active;

            Button browse = new Button(composite, SWT.PUSH);
            browse.setText(Messages.QualityArchiveWizardPage_Browse);
            GridData gd3 = new GridData(SWT.FILL, SWT.CENTER, false, false);
            browse.setLayoutData(gd3);

            browse.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    IWorkspaceRoot workspaceRoot =
                            ResourcesPlugin.getWorkspace().getRoot();
                    Class<?>[] acceptedClasses = new Class[] { IProject.class };
                    Object[] rejectedElements = new Object[0];
                    ViewerFilter typedFilter =
                            new TypedViewerFilter(acceptedClasses,
                                    rejectedElements);
                    ViewerFilter subtreeFilter =
                            new SubtreeViewerFilter(workspaceRoot);
                    Set<String> types = new HashSet<String>();
                    types.add(QualityArchiveNature.NATURE_ID);
                    ViewerFilter special = new ProjectNatureFilter(types);
                    ILabelProvider labelProvider = new WorkbenchLabelProvider();
                    ITreeContentProvider contentProvider =
                            new WorkbenchContentProvider();
                    FolderSelectionDialog dialog =
                            new FolderSelectionDialog(parent.getShell(),
                                    labelProvider, contentProvider);
                    dialog.setTitle(Messages.QualityArchiveWizardPage_SelectProjectTitle);
                    dialog.setMessage(Messages.QualityArchiveWizardPage_SelectProjectMessage);
                    dialog.addFilter(typedFilter);
                    dialog.addFilter(subtreeFilter);
                    dialog.addFilter(special);
                    dialog.setInput(workspaceRoot);
                    String projectName = project.getText();
                    if (projectName != null && projectName.length() != 0) {
                        IProject current =
                                workspaceRoot.getProject(projectName);
                        dialog.setInitialSelection(current);
                    }
                    if (dialog.open() == Window.OK) {
                        IProject result = (IProject) dialog.getFirstResult();
                        project.setText(result.getName());
                        selected = result;
                    }
                }

            });
        }
        setControl(composite);
    }

    /**
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage#updateConfiguration()
     */
    @Override
    public void updateConfiguration() {
        if (configuration != null) {
            configuration.setQualityProject(selected);
        }
    }

    /**
     * @return
     */
    public IProject getQualityProject() {
        return selected;
    }
}
