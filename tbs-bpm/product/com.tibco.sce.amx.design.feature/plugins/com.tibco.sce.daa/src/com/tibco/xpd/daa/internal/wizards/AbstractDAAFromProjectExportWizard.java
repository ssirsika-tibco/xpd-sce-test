/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.daa.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.daa.internal.Messages;
import com.tibco.xpd.daa.internal.util.DAAConstants;
import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage;

/**
 * Exports project(s) to DAA.
 * 
 * @author mtorres
 */
public abstract class AbstractDAAFromProjectExportWizard extends Wizard implements IExportWizard {


    /** WebDAV resources selection page. */
    private AbstractInputOutputSelectionWizardPage projectSelectionPage;
    
    private AbstractMultiProjectDAAGenerationWithProgress multiProjectDAAGenerationWithProgress;

    private static final Logger LOG = XpdResourcesPlugin.getDefault().getLogger();

    private IStructuredSelection initialSelection = StructuredSelection.EMPTY;

    /**
     * The constructor.
     */
    public AbstractDAAFromProjectExportWizard() {
        setWindowTitle(Messages.AbstractDAAFromProjectExportWizard_DaaExportWindowTitle);
        setDefaultPageImageDescriptor(DaaActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DAAConstants.IMG_DAA_EXPORT_WIZARD));
        setNeedsProgressMonitor(true);
    }
    
    protected abstract AbstractInputOutputSelectionWizardPage getProjectSelectionPage();
    
    protected abstract AbstractMultiProjectDAAGenerationWithProgress getMultiProjectDAAGenerationWithProgress();

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public final void addPages() {
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
        if (!DeployUtil.saveAllDirtyResourcesInWS()) {
            return false;
        }

        final IStatus[] statusArray = new IStatus[1];
        statusArray[0] = Status.CANCEL_STATUS;
        multiProjectDAAGenerationWithProgress
                .setSelectedProjects(getSelectedProjects());
        multiProjectDAAGenerationWithProgress
                .setDestLocationType(projectSelectionPage.getLocationType());
        multiProjectDAAGenerationWithProgress
                .setDestOutFolderPath(projectSelectionPage.getLocationPath());
        try {
            getContainer().run(true, true, multiProjectDAAGenerationWithProgress);
        } catch (InvocationTargetException e) {
            LOG.error(e);
        } catch (InterruptedException e) {
            if (multiProjectDAAGenerationWithProgress.getStatus().getSeverity() != IStatus.CANCEL) {
                LOG.error(e);
            }
        }

        IStatus opStatus = multiProjectDAAGenerationWithProgress.getStatus();
        if (opStatus == null) {
            return false;
        } else if (opStatus.getSeverity() > IStatus.OK) {
            ErrorDialog
                    .openError(getShell(),
                            Messages.AbstractDAAFromProjectExportWizard_DaaExport,
                            Messages.AbstractDAAFromProjectExportWizard_DaaError,
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
        this.projectSelectionPage = getProjectSelectionPage();
        this.multiProjectDAAGenerationWithProgress = getMultiProjectDAAGenerationWithProgress();
    }
    
    public IStructuredSelection getInitialSelection() {
        return initialSelection;
    }

}
