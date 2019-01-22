package com.tibco.xpd.bom.resources.ui.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class RefactorToBusinessDataProjectAction extends AbstractHandler {

    private static final String BUSINESS_DATA_ASSET_ID =
            "com.tibco.xpd.asset.businessdata.bom"; //$NON-NLS-1$

    /**
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     * 
     * @param event
     * @return
     * @throws ExecutionException
     */
    @Override
    public Object execute(ExecutionEvent event)
            throws org.eclipse.core.commands.ExecutionException {

        ISelection selection = HandlerUtil.getCurrentSelection(event);

        if (selection instanceof IStructuredSelection
                && !((IStructuredSelection) selection).isEmpty()) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.getFirstElement() instanceof IProject) {

                IProject selectedProject = (IProject) sel.getFirstElement();

                if (selectedProject != null && selectedProject.isAccessible()) {
                    final Shell shell =
                            XpdResourcesPlugin.getStandardDisplay()
                                    .getActiveShell();

                    if (PlatformUI.getWorkbench().saveAllEditors(true)) {
                        WizardDialog dlg =
                                new WizardDialog(shell, new RefactorWizard(
                                        selectedProject));
                        dlg.open();
                    }
                }
            }
        }
        return true;
    }

    private class RefactorWizard extends Wizard {

        private final IProject project;

        /**
         * 
         */
        public RefactorWizard(IProject project) {
            this.project = project;
            setNeedsProgressMonitor(true);
            setWindowTitle(Messages.RefactorToBusinessDataProjectAction_convertToBusinessDataProjectWizard_title);
        }

        /**
         * @see org.eclipse.jface.wizard.Wizard#addPages()
         * 
         */
        @Override
        public void addPages() {
            addPage(new RefactorPage(project));
        }

        /**
         * @see org.eclipse.jface.wizard.Wizard#performFinish()
         * 
         * @return
         */
        @Override
        public boolean performFinish() {

            try {
                final List<WorkingCopy> wcToSave = new ArrayList<WorkingCopy>();
                getContainer().run(true, true, new IRunnableWithProgress() {

                    @Override
                    public void run(final IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        final List<IFile> bomFilesToConvert =
                                getBOMFilesToConvert(project);

                        monitor.beginTask(Messages.RefactorToBusinessDataProjectAction_progressMonitor_label,
                                bomFilesToConvert.size() + 1);

                        // Convert the project
                        try {
                            convertProjectToBusinessDataProject(project);
                            monitor.worked(1);

                            // Convert each BOM file
                            if (!bomFilesToConvert.isEmpty()) {
                                final GlobalDataProfileManager profileManager =
                                        GlobalDataProfileManager.getInstance();
                                UnprotectedWriteOperation op =
                                        new UnprotectedWriteOperation(
                                                XpdResourcesPlugin.getDefault()
                                                        .getEditingDomain()) {

                                            @Override
                                            protected Object doExecute() {
                                                for (IFile file : bomFilesToConvert) {
                                                    monitor.subTask(String
                                                            .format(Messages.RefactorToBusinessDataProjectAction_progressMonitor_label2,
                                                                    file.getProjectRelativePath()
                                                                            .toString()));

                                                    WorkingCopy wc =
                                                            WorkingCopyUtil
                                                                    .getWorkingCopy(file);
                                                    boolean doSave =
                                                            !wc.isWorkingCopyDirty();
                                                    if (wc instanceof BOMWorkingCopy) {
                                                        Model model =
                                                                (Model) wc
                                                                        .getRootElement();

                                                        profileManager
                                                                .applyGlobalDataProfile(model);
                                                        if (doSave) {
                                                            wcToSave.add(wc);
                                                        }
                                                    }

                                                    monitor.worked(1);
                                                }

                                                return null;
                                            }
                                        };

                                op.execute();

                                for (WorkingCopy wc : wcToSave) {
                                    try {
                                        wc.save();
                                    } catch (IOException e) {
                                        // Do nothing
                                    }
                                }
                            }
                        } catch (CoreException e1) {
                            throw new InvocationTargetException(e1);
                        } finally {
                            monitor.done();
                        }

                    }
                });
            } catch (InvocationTargetException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                e.getMessage()));
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        /**
         * Convert the project to a Business Data project.
         * 
         * @param project
         * @return
         * @throws CoreException
         */
        private boolean convertProjectToBusinessDataProject(IProject project)
                throws CoreException {
            // Add the Business Data asset to this project
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                config.addAssetTask(BUSINESS_DATA_ASSET_ID);
                ProjectAssetElement asset =
                        ProjectAssetManager.getProjectAssetMenager()
                                .getAssetById(BUSINESS_DATA_ASSET_ID);
                if (asset != null) {
                    IAssetConfigurator configurator = asset.getConfigurator();
                    if (configurator != null) {
                        configurator.configure(project,
                                asset.getConfiguration());
                    }
                }

                return true;
            }

            return false;
        }

        private List<IFile> getBOMFilesToConvert(IProject project) {
            List<IFile> bomFilesToConvert = new ArrayList<IFile>();
            List<SpecialFolder> allSpecialFoldersOfKind =
                    SpecialFolderUtil.getAllSpecialFoldersOfKind(project,
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

            if (allSpecialFoldersOfKind != null) {
                for (SpecialFolder sf : allSpecialFoldersOfKind) {
                    if (sf.getGenerated() == null) {
                        Collection<IResource> resourcesInContainer =
                                SpecialFolderUtil
                                        .getAllDeepResourcesInContainer(sf
                                                .getFolder(),
                                                BOMResourcesPlugin.BOM_FILE_EXTENSION,
                                                true);
                        if (resourcesInContainer != null) {
                            for (IResource res : resourcesInContainer) {
                                if (res instanceof IFile) {
                                    bomFilesToConvert.add((IFile) res);
                                }
                            }
                        }
                    }
                }
            }

            return bomFilesToConvert;
        }
    }

    private class RefactorPage extends WizardPage {

        private final IProject project;

        /**
         * @param pageName
         */
        protected RefactorPage(IProject project) {
            super("refactorPage"); //$NON-NLS-1$
            this.project = project;
            setTitle(Messages.RefactorPage__convertToBusinessDataDialog_title);
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        public void createControl(Composite parent) {

            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout());
            Label lbl = new Label(root, SWT.WRAP);
            if (canRefactorToBusinessDataProject(project)) {

                lbl.setText(String
                        .format(Messages.RefactorPage__convertToBusinessDataDialog_label,
                                project.getName()));
                /* Show a warning message */
                CLabel lbl2 = new CLabel(root, SWT.WRAP);
                GridData gd = new GridData(GridData.FILL_HORIZONTAL);
                gd.widthHint = 160;
                gd.horizontalIndent = 30;
                lbl2.setLayoutData(gd);

                lbl2.setImage(Activator.getDefault().getImageRegistry()
                        .get(BOMImages.WARNING_LARGE));

                lbl2.setText(Messages.RefactorToBusinessDataProjectAction_convertToBusinessData_warning_message1);

                Label lbl3 = new Label(root, SWT.WRAP);
                gd = new GridData();
                gd.verticalIndent = 5;
                lbl3.setLayoutData(gd);
                lbl3.setText(Messages.RefactorToBusinessDataProjectAction_convertToBusinessData_warning_message2);
            } else {
                setPageComplete(false);
                setErrorMessage(Messages.RefactorToBusinessDataProjectAction_convertToBusinessDataDialogError_title);
                lbl.setText(String
                        .format(Messages.RefactorToBusinessDataProjectAction_convertToBusinessDataDialog_desc2,
                                project.getName()));
            }
            setControl(root);

        }
    }

    /**
     * 
     * @param project
     * @return true if it has only one asset and hence can be refactored, false
     *         otherwise
     */
    private boolean canRefactorToBusinessDataProject(IProject project) {
        boolean canRefactor = true;

        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            EList<AssetType> assetTypes = config.getAssetTypes();
            if (assetTypes.size() > 1) {
                canRefactor = false;
            }
        }
        return canRefactor;
    }

}
