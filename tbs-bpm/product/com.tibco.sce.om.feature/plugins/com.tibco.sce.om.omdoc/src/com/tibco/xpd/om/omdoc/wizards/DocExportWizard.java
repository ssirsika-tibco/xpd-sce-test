/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IExportWizard;

import com.tibco.xpd.om.omdoc.Activator;
import com.tibco.xpd.om.omdoc.engine.DocGenType;
import com.tibco.xpd.om.omdoc.internal.Messages;
import com.tibco.xpd.om.omdoc.internal.navigator.actions.ExportDocAction;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export BOM wizard to convert an BOM file to XSD model.
 * 
 * @author glewis
 * 
 * @deprecated see {@link com.tibco.xpd.ui.documentation.DocExportWizard}.
 */
public class DocExportWizard extends AbstractDocExportWizard implements
        IExportWizard {

    private static final String IMG_LOC = "icons/obj16/OrganizationModelFile.png"; //$NON-NLS-1$

    private static final String BROWSEDESTFOLDERKEY = "DESTBROWSEFOLDER"; //$NON-NLS-1$

    protected IStructuredSelection selection;

    protected ExportDocAction action;

    protected IPath selectedExportPath = null;

    /**
     * Import UML Wizard.
     */
    public DocExportWizard() {
        setNeedsProgressMonitor(true);
        setWindowTitle(Messages.DocExportWizard_title);
        setDefaultPageImageDescriptor(Activator.imageDescriptorFromPlugin(
                Activator.PLUGIN_ID, IMG_LOC));
        setWorkspaceExportFolder(Messages.DocExportWizard_exportFolderName); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
    public boolean canFinish() {
        boolean bRet = true;

        if (bRet
                && (getSelectedFiles() == null || getSelectedFiles().size() != 1)) {
            bRet = false;
        }

        return bRet;
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                synchronized (DocExportWizard.class) {
                    if (action == null) {
                        action = new ExportDocAction(DocGenType.HTML_OUTPUTTYPE
                                .toLowerCase(), DocGenType.OUTPUT_HTML);
                    }
                }

                selection = getSelectedFiles();
                action.selectionChanged(selection);

                // determine if user selects manual location or the default
                // workspace export location
                String path = getPreference(BROWSEDESTFOLDERKEY);
                ExportDestination dest = getExportDestinationSelection();

                // set the output location accordingly
                if (dest.equals(ExportDestination.PROJECT)) {
                    IProject project = null;

                    final IFile firstModelFile = (IFile) selection
                            .getFirstElement();
                    project = firstModelFile.getProject();

                    selectedExportPath = project.getFullPath().append(
                            getWorkspaceExportFolder()).addTrailingSeparator();
                    IPath relativeRoot = project.getProject().getFullPath()
                            .append(getWorkspaceExportFolder());
                    action.setOutputPath(relativeRoot);
                } else {
                    String rootPath = path + File.separator;
                    selectedExportPath = new Path("" + rootPath); //$NON-NLS-1$
                    action
                            .setOutputPath(/* path.replace(File.separator, "/") */selectedExportPath); //$NON-NLS-1$
                }

                String outputType = getOutputType();
                int outputSelIdx = getOutputSelIdx();

                if (action.isEnabled()) {
                    action.setOutputType(outputType.toLowerCase());
                    action.setOutputSelIdx(outputSelIdx);
                    action.run();
                }
            }
        };
        try {
            getContainer().run(false, true, op);
        } catch (InterruptedException e) {
            return;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof CoreException) {
                ErrorDialog
                        .openError(
                                getContainer().getShell(),
                                Messages.DocExportWizard_ExportBOMWizard_errorExportingDoc,
                                null, ((CoreException) e.getTargetException())
                                        .getStatus());
            } else {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(
                                e.getTargetException(),
                                Messages.DocExportWizard_ExportBOMWizard_errorExportingDoc);
            }
        }
    }

    public IPath getSelectedExportPath() {
        return selectedExportPath;
    }

    @Override
    public String getExportFileExt() {
        return "html"; //$NON-NLS-1$
    }

    @Override
    protected ViewerFilter[] getFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];

        // Only include XPD projects
        filters[0] = new XpdNatureProjectsOnly();

        // Only include concept special folders
        filters[1] = new SpecialFoldersOnlyFilter(Collections
                .singleton("om" /* OM_SPECIAL_FOLDER_KIND */)); //$NON-NLS-1$

        // Only include files with the right file extension
        filters[2] = new FileExtensionInclusionFilter(Collections
                .singleton("om"/* BOMResourcesPlugin.BOM_FILE_EXTENSION */)); //$NON-NLS-1$

        // Don't drill down into the files
        filters[3] = new NoFileContentFilter();

        return filters;
    }
}
