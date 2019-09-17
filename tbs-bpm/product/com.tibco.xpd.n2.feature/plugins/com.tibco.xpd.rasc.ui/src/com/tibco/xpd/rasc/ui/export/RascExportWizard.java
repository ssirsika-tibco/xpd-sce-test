package com.tibco.xpd.rasc.ui.export;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.rasc.core.RascActivator;
import com.tibco.xpd.rasc.core.RascController;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;
import com.tibco.xpd.ui.importexport.exportwizard.pages.DestinationLocationType;

/**
 * Wizard for exporting projects as RASC files.
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class RascExportWizard extends Wizard
        implements IExportWizard, ExportCompleteListener {

    /**
     * The currently selected projects.
     */
    private IStructuredSelection selection;

    /**
     * The project selection page.
     */
    private RascExportProjectSelectionPage exportPage;

    /**
     * The export status page.
     */
    private RascExportStatusPage statusPage;

    /**
     * Cache of WizardDialog buttons by ID.
     */
    private Map<Integer, Button> buttonMap;

    /**
     * True if the export is currently running.
     */
    private boolean exportRunning = false;

    /**
     * True if the export is for production, false if it's for drafts.
     */
    private boolean isGeneratingForProduction = false;

    /**
     * Constructor.
     */
    public RascExportWizard(boolean isGeneratingForProduction) {
        this.isGeneratingForProduction = isGeneratingForProduction;
        if (this.isGeneratingForProduction) {
            setWindowTitle(Messages.RascGenerateProductionRascWizard_Title);
        } else {
            setWindowTitle(Messages.RascGenerateDraftRascWizard_Title);
        }
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
        exportPage = new RascExportProjectSelectionPage(selection, this.isGeneratingForProduction);
        exportPage.setTitle(getWindowTitle());
        if (this.isGeneratingForProduction) {
            exportPage.setDescription(Messages.RascGenerateProductionRascWizard_Description);
        } else {
            exportPage.setDescription(Messages.RascGenerateDraftRascWizard_Description);
        }

        statusPage = new RascExportStatusPage();
        statusPage.setTitle(getWindowTitle());
        if (this.isGeneratingForProduction) {
            statusPage.setDescription(Messages.RascGenerateProductionRascWizard_Description);
        } else {
            statusPage.setDescription(Messages.RascGenerateDraftRascWizard_Description);
        }
        statusPage.setExportCompleteListener(this);

        addPage(exportPage);
        addPage(statusPage);

    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#setContainer(org.eclipse.jface.wizard.IWizardContainer)
     *
     * @param wizardContainer
     */
    @Override
    public void setContainer(IWizardContainer wizardContainer) {
        super.setContainer(wizardContainer);
        WizardDialog wizardDialog = getWizardDialog();
        if (wizardDialog != null) {
            wizardDialog.addPageChangedListener(new IPageChangedListener() {

                @Override
                public void pageChanged(PageChangedEvent event) {
                    Object page = event.getSelectedPage();
                    updateWizardDialog(page);
                }
            });
        }
    }

    private void updateWizardDialog(Object page) {
        if (buttonMap == null) {
            buttonMap = getButtonMap();
        }
        if (buttonMap != null) {
            Button finish = buttonMap.get(IDialogConstants.FINISH_ID);
            Button cancel = buttonMap.get(IDialogConstants.CANCEL_ID);
            Button next = buttonMap.get(IDialogConstants.NEXT_ID);
            Button previous = buttonMap.get(IDialogConstants.BACK_ID);
            previous.setVisible(false);
            next.setVisible(false);
            if (page == exportPage) {
                finish.setText(Messages.RascExportWizard_ExportButton);
            } else if (page == statusPage) {
                exportRunning = true;
                statusPage.setPageComplete(false);
                finish.setText(Messages.RascExportWizard_LaunchButton);
                cancel.setText(Messages.RascExportWizard_CloseButton);
                cancel.setEnabled(false);
                export();
            }
        }
    }

    private Map<Integer, Button> getButtonMap() {
        Map<Integer, Button> buttons = null;
        WizardDialog dialog = getWizardDialog();
        Control buttonBar = dialog.buttonBar;
        if (buttonBar != null && buttonBar instanceof Composite) {
            buttons = new HashMap<>();
            Composite compositeBar = (Composite) buttonBar;
            addChildButtons(buttons, compositeBar);
        }
        return buttons;
    }

    /**
     * @param buttonMap
     * @param compositeBar
     */
    private void addChildButtons(Map<Integer, Button> buttonMap,
            Composite compositeBar) {
        Control[] children = compositeBar.getChildren();
        for (Control child : children) {
            if (child instanceof Button) {
                Button button = (Button) child;
                Object data = button.getData();
                if (data instanceof Integer) {
                    Integer id = (Integer) data;
                    buttonMap.put(id, button);
                }
            } else if (child instanceof Composite) {
                addChildButtons(buttonMap, (Composite) child);
            }
        }
    }

    private WizardDialog getWizardDialog() {
        WizardDialog dialog = null;
        IWizardContainer container = getContainer();
        if (container instanceof WizardDialog) {
            dialog = (WizardDialog) container;
        }
        return dialog;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        IWizardContainer container = getContainer();
        IWizardPage page = container.getCurrentPage();
        if (page == exportPage) {
            container.showPage(statusPage);
            return false;
        } else {
            try {
                launch();
            } catch (PartInitException | MalformedURLException e) {
                RascUiActivator.getLogger().error(e);
                statusPage.setErrorMessage(
                        Messages.RascExportWizard_AdminPageError);
                statusPage.setPageComplete(false);
            }
            return true;
        }
    }

    /**
     * Run the export operation.
     */
    private void export() {
        List<IProject> projects = exportPage.getSelectedProjects();
        boolean isProjectRelative = DestinationLocationType.PROJECT
                .equals(exportPage.getLocationType());
        RascController controller =
                RascActivator.getDefault().getRascController();
        IRunnableWithProgress op = new RascExportOperation(getShell(),
                controller, statusPage, projects, exportPage.getLocationPath(),
                isProjectRelative);
        statusPage.run(op);
    }

    /**
     * Launch the admin UI in a new browser window.
     * 
     * @throws PartInitException
     *             If the browser could not be opened.
     * @throws MalformedURLException
     *             If the URL is invalid (should never happen)
     */
    private void launch() throws PartInitException, MalformedURLException {
        boolean hide = RascUiActivator.getDefault().getHideAdminUrlDialog();
        AdminLauncher launcher = new AdminLauncher();
        String url = RascUiActivator.getDefault().getAdminBaseUrl();
        if (!hide || url == null || url.length() == 0) {
            int result = new AdminUrlPropertyDialog(getShell()).open();
            if (result == Dialog.OK) {
                launcher.launch();
            }
        } else {
            launcher.launch();
        }
    }

    /**
     * @see com.tibco.xpd.rasc.ui.export.ExportCompleteListener#exportComplete()
     *
     */
    @Override
    public void exportComplete() {
        exportRunning = false;
        Button cancel = buttonMap.get(IDialogConstants.CANCEL_ID);
        Display display = cancel.getDisplay();
        if (display != null && !display.isDisposed()) {
            display.asyncExec(() -> cancel.setEnabled(true));
        }
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performCancel()
     */
    @Override
    public boolean performCancel() {
        if (exportRunning) {
            return false;
        }
        return super.performCancel();
    }

}
