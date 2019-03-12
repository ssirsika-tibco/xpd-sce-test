package com.tibco.xpd.rasc.ui.export;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rasc.ui.Messages;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;

/**
 * Wizard for exporting projects as RASC files.
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class RascExportWizard extends Wizard implements IExportWizard {

    /**
     * The currently selected projects.
     */
    private IStructuredSelection selection;

    /**
     * The project selection page.
     */
    private RascExportProjectSelectionPage exportPage;

    /**
     * Constructor.
     */
    public RascExportWizard() {
        setWindowTitle(Messages.RascExportWizard_Title);
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        setDefaultPageImageDescriptor(
                RascUiActivator.getDefault().getImageRegistry().getDescriptor(
                        RascUiActivator.RASC_EXPORT_WIZARD_IMAGE));
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        exportPage = new RascExportProjectSelectionPage(selection);
        exportPage.setTitle(getWindowTitle());
        exportPage.setDescription(Messages.RascExportWizard_Description);
        addPage(exportPage);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        List<IProject> projects = exportPage.getSelectedProjects();
        ExportProgressMonitorDialog progress =
                new ExportProgressMonitorDialog(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell());
        progress.open();
        boolean isProjectRelative = DestinationLocationType.PROJECT
                .equals(exportPage.getLocationType());
        IRunnableWithProgress op = new RascExportOperation(progress, projects,
                exportPage.getLocationPath(), isProjectRelative);
        progress.run(op);
        return true;
    }

}
