package com.tibco.xpd.sce.rasc.ui.export;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class RascExportWizard extends Wizard implements IExportWizard {

    private IStructuredSelection selection;

    private RascExportProjectSelectionPage exportPage;

    public RascExportWizard() {
        setWindowTitle("Export RASC");
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     *
     */
    @Override
    public void addPages() {
        exportPage = new RascExportProjectSelectionPage(selection);
        exportPage.setTitle(getWindowTitle());
        exportPage.setDescription("Desc");
        addPage(exportPage);
    }

    @Override
    public boolean performFinish() {
        return false;
    }

}
