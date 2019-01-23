/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.wizards.conceptdoc;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export wizard for the concept model documentation.
 * 
 * @author njpatel
 * 
 * @deprecated see {@link com.tibco.xpd.ui.documentation.DocExportWizard}.
 */
public class BOMDocumentationExportWizard extends AbstractExportWizard
        implements ITransformationStylesheetsProvider2 {

    private static final String XSLT = "/xslts/bom2html.xsl"; //$NON-NLS-1$

    private static final String MESSAGES = "$nl$/xslts/messages.properties"; //$NON-NLS-1$

    private URL[] xsltUrls;

    /**
     * Constructor
     */
    public BOMDocumentationExportWizard() {
        setNeedsProgressMonitor(true);
        setWindowTitle(Messages.BOMDocumentationExportWizard_title);
        setWindowMessage(Messages.BOMDocumentationExportWizard_message);
        setWorkspaceExportFolder(Messages.BOMDocumentationExportWizard_exportFolder_label);

        // Register subtask to copy images and css to export folder
        ResourceCopier copier = new ResourceCopier();
        registerSubTask(copier);
        ImageCreator imageCreator = new ImageCreator(this);
        registerSubTask(imageCreator);
    }

    @Override
    public String getExportFileExt() {
        return "html"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXslts()
     */
    public URL[] getXslts() {
        if (xsltUrls == null) {
            xsltUrls = new URL[] { getClass().getResource(XSLT) };
        }
        return xsltUrls;
    }

    public Map<String, String> getXsltParameters() {
        return null;
    }

    @Override
    protected ViewerFilter[] getFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];

        // Only include XPD projects
        filters[0] = new XpdNatureProjectsOnly();

        // Only include concept special folders
        filters[1] = new SpecialFoldersOnlyFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND));

        // Only include files with the right file extension
        filters[2] = new FileExtensionInclusionFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_FILE_EXTENSION));

        // Don't drill down into the files
        filters[3] = new NoFileContentFilter();

        return filters;

    }

    /*
     * (non-Javadoc) Class loader changed for access to XSLT functions.
     * 
     * @see com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard#performOperation(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {
        Thread current = Thread.currentThread();
        ClassLoader oldLoader = current.getContextClassLoader();
        try {
            current.setContextClassLoader(getClass().getClassLoader());
            super.performOperation(monitor);
        } finally {
            current.setContextClassLoader(oldLoader);
        }

    }

    public URL getImportXsltURL(String href) {
        return null;
    }

    public URL getMessagePropertiesURL(URL xsltURL) {
        return FileLocator.find(Activator.getDefault().getBundle(), new Path(
                MESSAGES), null);

    }

}
