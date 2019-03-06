package com.tibco.xpd.sce.rasc.ui.export;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.sce.rasc.ui.Messages;
import com.tibco.xpd.sce.rasc.ui.RascUiActivator;

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
        return false;
    }

}
