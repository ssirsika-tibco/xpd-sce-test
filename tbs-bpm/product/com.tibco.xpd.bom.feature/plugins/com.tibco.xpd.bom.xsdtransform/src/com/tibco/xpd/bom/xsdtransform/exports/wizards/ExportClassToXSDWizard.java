/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.IExportDataProvider;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export wizard to generate XSDs from BOM {@link Class Classes}.
 * 
 * @author njpatel
 */
public class ExportClassToXSDWizard extends Wizard implements
        IExportDataProvider {

    private static final String EXPORT_FOLDER =
            "/Exports/" + Activator.EXPORT_FOLDER; //$NON-NLS-1$ 

    private ClassToXSDExportPage page;

    private final List<Class> initialSelection;

    /**
     * Export wizard to generate XSDs from selected BOM {@link Class Classes}.
     * 
     * @param selection
     *            initial selection to set in the wizard.
     */
    public ExportClassToXSDWizard(List<Class> selection) {
        this.initialSelection = selection;
        setNeedsProgressMonitor(true);
        setHelpAvailable(false);
        setWindowTitle(Messages.ExportToXSDWizard_WindowTitle);
    }

    @Override
    public void addPages() {
        if (!ExportBOMUtil.isAutoBuildEnabled()) {
            addPage(new BuildPage("build")); //$NON-NLS-1$
        }
        page =
                new ClassToXSDExportPage(new StructuredSelection(
                        initialSelection), getInput(), getFilters(), this);
        page.setTitle(getWindowTitle());
        page.setDescription(Messages.ExportClassToXSDWizard_shortdesc);
        page.setSelectionValidator(new XSDExportSelectionValidator());
        addPage(page);
    }

    /**
     * Get the input for the selection page.
     * 
     * @return workspace root
     */
    private Object getInput() {
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    /**
     * Get the filters to set in the <code>Class</code> selection tree.
     * 
     * @return array of filters
     */
    private ViewerFilter[] getFilters() {
        List<ViewerFilter> filters = new ArrayList<ViewerFilter>();

        filters.add(new XpdNatureProjectsOnly(false));
        filters.add(new SpecialFoldersOnlyFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)));
        filters.add(new FileExtensionInclusionFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_FILE_EXTENSION)));
        filters.add(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof EObject) {
                    EObject eo = (EObject) element;
                    EPackage pkg = eo.eClass().getEPackage();
                    if (pkg == UMLPackage.eINSTANCE) {
                        // UML element - only include models, packages and
                        // classes
                        return eo instanceof Class || eo instanceof Model
                                || eo instanceof Package;
                    }
                }
                return true;
            }
        });

        return filters.toArray(new ViewerFilter[filters.size()]);
    }

    @Override
    public boolean performFinish() {
        boolean ret = true;

        if (PlatformUI.getWorkbench().saveAllEditors(true)) {
            final IStructuredSelection selection = page.getSelectedElements();
            // Perform operation in a workspace modify operation as the
            // workspace may be affected
            WorkspaceModifyOperation operation =
                    new WorkspaceModifyOperation() {
                        @Override
                        protected void execute(IProgressMonitor monitor)
                                throws CoreException,
                                InvocationTargetException, InterruptedException {
                            performOperation(selection, monitor);
                        }
                    };

            try {
                getContainer().run(true, true, operation);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();

                if (cause != null) {
                    ErrorDialog
                            .openError(getShell(),
                                    Messages.ExportClassToXSDWizard_export_errorDialog_title,
                                    Messages.ExportClassToXSDWizard_export_errorDialog_shortdesc,
                                    cause instanceof CoreException ? ((CoreException) cause)
                                            .getStatus() : new Status(
                                            IStatus.ERROR, Activator.PLUGIN_ID,
                                            IStatus.OK, cause
                                                    .getLocalizedMessage(),
                                            cause));
                }
            } catch (InterruptedException e) {
                // Process interrupted so don't close the wizard
                ret = false;
            }
        } else {
            return false;
        }

        return ret;
    }

    /**
     * Perform the export operation
     * 
     * @param selection
     *            Classes selected for export
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    protected void performOperation(IStructuredSelection selection,
            IProgressMonitor monitor) throws CoreException {
        if (selection != null && !selection.isEmpty()) {
            boolean preserveSchema = page.doPreserveSchema();
            switch (page.getDestinationSelection()) {
            case PATH:
                exportClassToFileSystem(selection, preserveSchema, monitor);
                break;
            case PROJECT:
                exportClassToWorkspace(selection, preserveSchema, monitor);
                break;
            }
        }
    }

    /**
     * User selected to export to the file system so generate WSDLs at given
     * location.
     * 
     * @param selection
     *            Classes to export
     * @param preserveSchema
     *            <code>true</code> to preserve the schema
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    private void exportClassToFileSystem(IStructuredSelection selection,
            boolean preserveSchema, IProgressMonitor monitor)
            throws CoreException {
        IPath path = page.getDestinationPath();
        if (path != null) {
            OverwriteStatus overwriteStatus = OverwriteStatus.FILE;
            monitor.beginTask(Messages.ExportClassToXSDWizard_exportingClass_progress_title,
                    selection.size());
            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {

                if (overwriteStatus != OverwriteStatus.ALL_FILES) {
                    // Reset status
                    overwriteStatus = OverwriteStatus.FILE;
                }
                Object next = iter.next();
                if (next instanceof Class) {
                    Class cls = (Class) next;
                    monitor.subTask("'" + cls.getLabel() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
                    IPath destPath =
                            path.append(cls.getName())
                                    .addFileExtension(Activator.XSD_FILE_EXT);
                    File destFile = destPath.toFile();
                    if (overwriteStatus != OverwriteStatus.ALL_FILES
                            && destFile.exists()) {
                        overwriteStatus = canOverwrite(destPath);
                        if (overwriteStatus.isCancelled()) {
                            throw new OperationCanceledException();
                        }
                    }

                    if (overwriteStatus.canOverwrite()) {
                        // Generate WSDL
                        process(preserveSchema,
                                cls,
                                URI.createFileURI(destPath.toString()));
                    }
                    monitor.worked(1);
                }
            }
        }
        monitor.done();
    }

    /**
     * User selected to export to the Exports folder in the workspace so
     * generate WSDLs in the relevant project's export folder.
     * 
     * @param selection
     *            Classes to export
     * @param preserveSchema
     *            <code>true</code> to preserve schema
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    private void exportClassToWorkspace(IStructuredSelection selection,
            boolean preserveSchema, IProgressMonitor monitor)
            throws CoreException {
        OverwriteStatus overwriteStatus = OverwriteStatus.FILE;

        monitor.beginTask(Messages.ExportClassToXSDWizard_exportingClass_progress_title,
                selection.size());
        for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
            if (overwriteStatus != OverwriteStatus.ALL_FILES) {
                // Reset status
                overwriteStatus = OverwriteStatus.FILE;
            }
            Object next = iter.next();
            if (next instanceof Class) {
                Class cls = (Class) next;
                monitor.subTask("'" + cls.getLabel() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
                IPath path = getProjectExportPath(cls);

                IFile destFile =
                        ResourcesPlugin.getWorkspace().getRoot().getFile(path);

                if (overwriteStatus != OverwriteStatus.ALL_FILES
                        && destFile != null && destFile.exists()) {
                    overwriteStatus = canOverwrite(path);
                    if (overwriteStatus.isCancelled()) {
                        throw new OperationCanceledException();
                    }
                }

                if (overwriteStatus.canOverwrite()) {
                    process(preserveSchema,
                            cls,
                            URI.createPlatformResourceURI(path.toString(), true));
                }
                monitor.worked(1);
            }
        }
        monitor.done();
    }

    /**
     * Generate a XSD in the given destination URI for the given class.
     * 
     * @param preserveSchema
     *            <code>true</code> to preserve the schema
     * @param cls
     *            Class to generate XSD for
     * @param destUri
     *            location of XSD file
     * @throws CoreException
     */
    private void process(boolean preserveSchema, Class cls, URI destUri)
            throws CoreException {
        List<IStatus> result = new ArrayList<IStatus>();
        try {
            XSDUtil.incrementalTransformBOMToXSD(cls,
                    destUri.toString(),
                    preserveSchema,
                    result,
                    true);
            /*
             * if (preserveSchema) { XSDUtil.incrementalTransformBOMToXSD(cls,
             * destUri .toFileString(), preserveSchema, issues);
             * transformer.incrementalPreserveTransform(cls, destUri, issues); }
             * else { transformer.incrementalTransform(cls, destUri, issues); }
             */
        } catch (Exception e) {
            if (e instanceof CoreException) {
                throw (CoreException) e;
            } else {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                Messages.ExportClassToXSDWizard_generatorErrors_shortdesc,
                                e));
            }
        }

        // If there are errors in the result then throw exception
        List<IStatus> errors = new ArrayList<IStatus>();
        for (IStatus res : result) {
            if (res.getSeverity() == IStatus.ERROR) {
                errors.add(res);
            }
        }
        if (!errors.isEmpty()) {
            throw new CoreException(new MultiStatus(Activator.PLUGIN_ID,
                    IStatus.ERROR, errors.toArray(new IStatus[errors.size()]),
                    Messages.ExportClassToXSDWizard_generatorErrors_shortdesc,
                    null));
        }
    }

    /**
     * Check with user if the file at the given path can be overwritten.
     * 
     * @param filePath
     * @return
     */
    private OverwriteStatus canOverwrite(final IPath filePath) {
        final OverwriteStatus status[] =
                new OverwriteStatus[] { OverwriteStatus.FILE };

        getShell().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                OverwriteFileMessageDialog dlg =
                        new OverwriteFileMessageDialog(
                                getShell(),
                                Messages.ExportClassToXSDWizard_fileExists_errorDialog_title,
                                String.format(Messages.ExportClassToXSDWizard_fileExists_errorDialog_shortdesc,
                                        filePath.toString()));
                status[0] = dlg.getOverwriteStatus(dlg.open());
            }
        });
        return status[0];
    }

    /**
     * If exporting to Project exports folder then work out the path the export
     * path for the given class.
     * 
     * @param cl
     * @return
     */
    private IPath getProjectExportPath(Class cl) {
        IFile file = WorkingCopyUtil.getFile(cl);
        if (file != null) {
            IPath location = file.getProject().getFullPath();
            return location.append(getWorkspaceExportFolder())
                    .append(cl.getName())
                    .addFileExtension(Activator.XSD_FILE_EXT);
        }
        return null;
    }

    /**
     * Page to select classes to export and destination.
     * 
     * @author njpatel
     * 
     */
    private class ClassToXSDExportPage extends ExportWizardPage {

        private boolean preserveSchema = false;

        /**
         * Main export wizard page.
         * 
         * @param selection
         * @param input
         * @param filters
         * @param provider
         */
        public ClassToXSDExportPage(IStructuredSelection selection,
                Object input, ViewerFilter[] filters,
                IExportDataProvider provider) {
            super(selection, input, new ViewerSorter(), filters, provider);
        }

        /**
         * Check if the schema should be preserved in the exported WSDL.
         * 
         * @return
         */
        public boolean doPreserveSchema() {
            return preserveSchema;
        }

        @Override
        protected Control createUserControl(Composite container) {
            Composite root = new Composite(container, SWT.NONE);
            root.setLayout(new GridLayout());

            final Button btn = new Button(root, SWT.CHECK);
            btn.setText(Messages.ExportClassToXSDWizard_preserveSchemas_button);
            btn.setSelection(preserveSchema);
            btn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    preserveSchema = btn.getSelection();
                }
            });

            return root;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.
     * IExportDataProvider#getPreference(java.lang.String)
     */
    @Override
    public String getPreference(String key) {
        String prefValue = null;

        // Make sure we have the key, otherwise return null
        if (key != null && key != "") { //$NON-NLS-1$
            IPreferenceStore pStore =
                    Activator.getDefault().getPreferenceStore();
            prefValue = pStore.getString(getClass().getName() + key);
        }

        return prefValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.
     * IExportDataProvider#getWorkspaceExportFolder()
     */
    @Override
    public String getWorkspaceExportFolder() {
        return EXPORT_FOLDER;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.
     * IExportDataProvider#setPreference(java.lang.String, java.lang.String)
     */
    @Override
    public void setPreference(String key, String value) {
        if (key != null && key != "" && value != null) { //$NON-NLS-1$
            IPreferenceStore pStore =
                    Activator.getDefault().getPreferenceStore();
            pStore.setValue(getClass().getName() + key, value);
        }
    }

}
