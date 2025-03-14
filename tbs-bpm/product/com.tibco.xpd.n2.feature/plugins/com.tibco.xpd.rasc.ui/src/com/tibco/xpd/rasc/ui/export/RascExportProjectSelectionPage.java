/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Initial project and export location selection page for the RASC export
 * wizard.
 *
 * @author nwilson
 * @since 5 Mar 2019
 */
public class RascExportProjectSelectionPage
        extends AbstractInputOutputSelectionWizardPage {

    /**
     * <code>true</code> if we're generating for production, <code>false</code>
     * if we're generating test artifacts.
     */
    private boolean isGeneratingForProduction;

    /**
     * Constructor.
     * 
     * @param selection
     *            The initial project selection.
     * @param isGeneratingForProduction
     */
    public RascExportProjectSelectionPage(IStructuredSelection selection, boolean isGeneratingForProduction) {
        super(selection, true);
        this.isGeneratingForProduction = isGeneratingForProduction;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#createExtraTreeControlButtons(org.eclipse.swt.widgets.Composite)
     *
     * @param cmpTreeSelect
     */
    @Override
    protected void createExtraTreeControlButtons(Composite cmpTreeSelect) {
        final Button selectRequired = new Button(cmpTreeSelect, SWT.PUSH);
        selectRequired.setText(
                Messages.RascExportProjectSelectionPage_SelectRequired);
        selectRequired.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Set<IProject> selected = new HashSet<>(getSelectedProjects());
                try {
                    Set<IProject> toCheck = ProjectUtil2
                            .getReferencedProjectsHierarchies(selected, true);
                    updateSelectedObjects(toCheck, true);
                } catch (CyclicDependencyException e1) {
                    setPageComplete(false);
                    setMessage(
                            Messages.RascExportProjectSelectionPage_CyclicError,
                            ERROR);
                }
            }
        });
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#validatePageCompletion()
     */
    @Override
    protected void validatePageCompletion() {
        List<Object> selected = getSelectedObjects();
        if (selected != null && !selected.isEmpty()) {
            /*
             * ACE-2849: Saket: Production artifacts cannot be generated for
             * draft projects.
             */
            boolean isPageComplete = true;
            for (Object eachSelectedObject : selected) {
                if (eachSelectedObject instanceof IProject) {
                    try {
                        boolean isLocked =
                                (new GovernanceStateService()).isLockedForProduction((IProject) eachSelectedObject);
                        if (!isLocked && isGeneratingForProduction) {
                            isPageComplete= false;
                            break;
                        }
                    } catch (CoreException e) {
                        RascUiActivator.getLogger().error(e);
                    }
                }
            }
            if (isPageComplete) {
                setPageComplete(true);
                setMessage(null);
            } else {
                setPageComplete(false);
                setMessage(Messages.RascExportProjectSelectionPage_DraftProjectForProduction, ERROR);
            }
        } else {
            setPageComplete(false);
            setMessage(
                    Messages.RascExportProjectSelectionPage_NoProjectSelectedError,
                    ERROR);
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#createViewerFilters()
     *
     * @return
     */
    @Override
    protected ViewerFilter[] createViewerFilters() {
        return new ViewerFilter[] {
                // XPD projects content only.
                new XpdNatureProjectsOnly(),

                // Only projects.
                new ViewerFilter() {
                    @Override
                    public boolean select(Viewer viewer, Object parentElement, Object element) {
                        if (element instanceof IWorkspaceRoot || element instanceof IProject) {
                            if (element instanceof IProject) {
                                IProject project = (IProject) element;
                                /*
                                 * Need to show only open projects.
                                 */
                                return project.isOpen();
                            } else {
                                return true;
                            }
                        }
                        return false;
                    }
                } };
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#getWorkspaceExportFolder()
     */
    @Override
    protected String getWorkspaceExportFolder() {
        return com.tibco.xpd.rasc.core.Messages.RascController_default_deploy_folder; // $NON-NLS-1$
    }

    /**
     * @return The list of selected projects.
     */
    public List<IProject> getSelectedProjects() {
        List<IProject> projects = new ArrayList<>();
        for (Object item : getSelectedObjects()) {
            if (item instanceof IProject) {
                projects.add((IProject) item);
            }
        }
        return projects;
    }

}
