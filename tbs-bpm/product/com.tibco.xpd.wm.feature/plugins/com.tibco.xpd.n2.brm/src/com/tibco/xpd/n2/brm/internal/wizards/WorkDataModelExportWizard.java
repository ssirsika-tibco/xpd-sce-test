/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.brm.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.brm.internal.Messages;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Exports BPM project(s)' work data model.
 * 
 * @author Jan Arciuchiewicz
 */
public class WorkDataModelExportWizard extends Wizard implements IExportWizard,
        IShellProvider {

    /** Project to export selection page. */
    private WorkDataModelProjectSelectionPage projectSelectionPage;

    private static final Logger LOG = Activator.getDefault().getLogger();

    private IStructuredSelection initialSelection = StructuredSelection.EMPTY;

    /**
     * The constructor.
     */
    public WorkDataModelExportWizard() {
        setWindowTitle(Messages.WorkDataModelExportWizard_WDMExportWizard_title);
        // TODO JA: Export wizard icon is needed.
        // setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry()
        // .getDescriptor(???));
        setNeedsProgressMonitor(true);
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public final void addPages() {
        projectSelectionPage =
                new WorkDataModelProjectSelectionPage(initialSelection) {
                    @Override
                    protected ViewerFilter[] createViewerFilters() {
                        List<ViewerFilter> filters =
                                new ArrayList<ViewerFilter>(Arrays.asList(super
                                        .createViewerFilters()));

                        filters.add(new ViewerFilter() {
                            @Override
                            public boolean select(Viewer viewer,
                                    Object parentElement, Object element) {
                                if (element instanceof IProject) {
                                    IProject p = (IProject) element;
                                    if (p.isAccessible()
                                            && ProjectUtil.isStudioProject(p)
                                            && GlobalDestinationUtil
                                                    .isGlobalDestinationEnabled(p,
                                                            N2Utils.N2_GLOBAL_DESTINATION_ID))
                                        return true;
                                }
                                return false;
                            }
                        });
                        return filters
                                .toArray(new ViewerFilter[filters.size()]);
                    }
                };
        addPage(projectSelectionPage);
    }

    protected List<IProject> getSelectedProjects() {
        List<IProject> projects = new ArrayList<IProject>();
        for (Object o : projectSelectionPage.getSelectedObjects()) {
            if (o instanceof IProject) {
                projects.add((IProject) o);
            }
        }
        return Collections.unmodifiableList(projects);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean performFinish() {
        final List<IProject> selectedProjects = getSelectedProjects();
        // if (!DeployUtil.saveAllDirtyResourcesInWS()) {
        // return false;
        // }
        if (!PlatformUI.getWorkbench()
                .saveAll(this, getContainer(), null, true)) {
            return false;
        }

        final IStatus[] statusArray = new IStatus[1];
        statusArray[0] = Status.CANCEL_STATUS;
        WDMExportOperation wdmExportOperation =
                new WDMExportOperation(selectedProjects,
                        projectSelectionPage.getLocationType(),
                        projectSelectionPage.getLocationPath(), this);
        try {
            getContainer().run(true, true, wdmExportOperation);
        } catch (InvocationTargetException e) {
            LOG.error(e);
        } catch (InterruptedException e) {
            if (wdmExportOperation.getStatus() != null
                    && wdmExportOperation.getStatus().getSeverity() != IStatus.CANCEL) {
                LOG.error(e);
            }
        }

        IStatus opStatus = wdmExportOperation.getStatus();
        if (opStatus == null) {
            return false;
        } else if (opStatus.getSeverity() > IStatus.OK) {
            ErrorDialog
                    .openError(getShell(),
                            Messages.WorkDataModelExportWizard_WDMExportProblem_title,
                            Messages.WorkDataModelExportWizard_WDMExportProblem_message,
                            opStatus,
                            IStatus.OK | IStatus.INFO | IStatus.WARNING
                                    | IStatus.ERROR);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        initialSelection = selection;
    }
}
