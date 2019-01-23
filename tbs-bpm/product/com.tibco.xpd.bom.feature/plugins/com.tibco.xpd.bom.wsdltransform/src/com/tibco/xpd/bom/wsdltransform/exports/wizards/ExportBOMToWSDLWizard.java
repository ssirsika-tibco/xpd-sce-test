/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.exports.wizards;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.wsdltransform.Activator;
import com.tibco.xpd.bom.wsdltransform.exports.BOM2WSDLTransformer;
import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.exports.wizards.BuildPage;
import com.tibco.xpd.bom.xsdtransform.exports.wizards.ExportBOMUtil;
import com.tibco.xpd.ui.importexport.exportwizard.FileExportWizard;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export wizard to generate a WSDL from a BOM file(s).
 * 
 * @author njpatel
 * 
 */
public class ExportBOMToWSDLWizard extends FileExportWizard {

    private static final String IMG_LOC = "icons/BOMExport.png"; //$NON-NLS-1$

    private BOM2WSDLTransformer transformer;

    /**
     * Export wizard to generate WSDLs from BOM files.
     */
    public ExportBOMToWSDLWizard() {
        setWindowTitle(Messages.ExportToWSDLWizard_WindowTitle);
        setWindowMessage(Messages.ExportBOMToWSDLWizard_shortdesc);
        setDefaultPageImageDescriptor(Activator
                .imageDescriptorFromPlugin(Activator.PLUGIN_ID, IMG_LOC));
        setWorkspaceExportFolder(Activator.EXPORT_FOLDER);
        transformer = new BOM2WSDLTransformer();
        setSelectionValidator(new WSDLExportSelectionValidator());
    }

    @Override
    public void addPages() {
        if (!ExportBOMUtil.isAutoBuildEnabled()) {
            addPage(new BuildPage("build")); //$NON-NLS-1$
        }
        super.addPages();
    }

    @Override
    public String getExportFileExt() {
        return Activator.WSDL_FILE_EXT;
    }

    @Override
    protected ViewerFilter[] getFilters() {
        return new ViewerFilter[] {
                new XpdNatureProjectsOnly(false),
                new SpecialFoldersOnlyFilter(
                        Collections
                                .singleton(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)),
                new NoFileContentFilter() };
    }

    @Override
    protected void processFile(IFile inputFile, IOutputFile<?> outputFile,
            IProgressMonitor monitor) throws CoreException {
        // Remove file name to give the path to the export folder
        IPath exportPath = outputFile.getPath().removeLastSegments(1);
        List<IStatus> status =
                transformer.transform(inputFile, exportPath, monitor);
        // Report the errors
        if (!status.isEmpty()) {
            // If there are errors in the status then throw exception
            for (IStatus st : status) {
                if (st.getSeverity() == IStatus.ERROR) {
                    throw new CoreException(
                            new MultiStatus(
                                    Activator.PLUGIN_ID,
                                    0,
                                    status.toArray(new IStatus[status.size()]),
                                    Messages.ExportBOMToWSDLWizard_generatorErrors_shortdesc,
                                    null));
                }
            }
        }
    }

}
