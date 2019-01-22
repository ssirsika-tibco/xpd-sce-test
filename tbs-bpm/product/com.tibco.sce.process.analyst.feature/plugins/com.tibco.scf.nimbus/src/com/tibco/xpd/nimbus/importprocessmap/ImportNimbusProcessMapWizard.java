/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.nimbus.importprocessmap;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.importexport.imports.wizards.ImportXPDLWizard;
import com.tibco.xpd.nimbus.XpdNimbusImages;
import com.tibco.xpd.nimbus.XpdNimbusPlugin;
import com.tibco.xpd.nimbus.internal.Messages;
import com.tibco.xpd.ui.importexport.importwizard.pages.FilteredFSElement;

/**
 * Import Nimbus Control Process Map to Business Studio XPDL.
 * 
 * @author aallway
 * @since 16 Oct 2012
 */
public class ImportNimbusProcessMapWizard extends ImportXPDLWizard {

    private static final String IMPORT_PROCESSMAP_XSLT =
            "/xslts/NimbusProcessMap_2_BSxpdl.xslt"; //$NON-NLS-1$

    private ImportNimbusProcessMapValidationPage validateImportPage;

    public ImportNimbusProcessMapWizard() {
        super();

        setWindowTitle(Messages.ImportNimbusProcessMapWizard_Import_title);
        setWindowMessage(Messages.ImportNimbusProcessMapWizard_Import_description);

        setDefaultPageImageDescriptor(XpdNimbusPlugin
                .getImageDescriptor(XpdNimbusImages.IMG_NIMBUSIMPORT_WIZARD));

        /* Register task to fix up model after xslt's create the basic stuff. */
        registerPostImportTask(new ImportNimbusPostImportSubTask());
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXsltParameters()
     * 
     * @return
     */
    @Override
    public Map<String, String> getXsltParameters() {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        super.addPages();

        validateImportPage = new ImportNimbusProcessMapValidationPage();
        addPage(validateImportPage);
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        /*
         * Reset the context class loader so that the xslt can load the util
         * classes.
         */
        Thread current = Thread.currentThread();
        ClassLoader oldLoader = current.getContextClassLoader();
        try {
            current.setContextClassLoader(getClass().getClassLoader());

            /* Do the transform. */
            super.performOperation(monitor);

        } finally {
            /*
             * Set everything back the way it was.
             */
            current.setContextClassLoader(oldLoader);
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider#getXslts()
     * 
     * @return
     */
    @Override
    public URL[] getXslts() {
        return new URL[] { XpdNimbusPlugin.class
                .getResource(IMPORT_PROCESSMAP_XSLT) };
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.IImportWizardPageProvider#getFileExtensionFilter()
     * 
     * @return
     */
    @Override
    public String[] getFileExtensionFilter() {
        return new String[] { "xml" }; //$NON-NLS-1$
    }

    /**
     * @return List of resources selected by user.
     */
    public List<FilteredFSElement> getImportResources() {
        return getSelectedResources();
    }

    /**
     * <b>Internal use only</b> Preset list of selected import resources. Only
     * used for JUnit testing.
     */
    private List<FilteredFSElement> testSelectedResources = null;

    /**
     * <b>Internal use only</b> preset the list of selected import (for use in
     * Junit testing).
     * 
     * @param testSelectedResources
     */
    public void setTestSelectedResources(
            List<FilteredFSElement> testSelectedResources) {
        this.testSelectedResources = testSelectedResources;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.AbstractImportWizard#getSelectedResources()
     * 
     * @return
     */
    @Override
    protected List<FilteredFSElement> getSelectedResources() {
        if (testSelectedResources != null) {
            return testSelectedResources;
        }

        return super.getSelectedResources();
    }

    /**
     * <b>Internal use only</b> preset import target destination container (for
     * use in Junit testing).
     */
    private IContainer testDestinationContainer = null;

    /**
     * <b>Internal use only</b> preset the import target destination container
     * (for use in Junit testing).
     * 
     * @param testDestinationContainer
     */
    public void setTestDestinationContainer(IContainer testDestinationContainer) {
        this.testDestinationContainer = testDestinationContainer;
    }

    /**
     * @see com.tibco.xpd.importexport.imports.wizards.ImportXPDLWizard#getDestinationContainer()
     * 
     * @return
     */
    @Override
    protected IContainer getDestinationContainer() {
        if (testDestinationContainer != null) {
            return testDestinationContainer;
        }
        return super.getDestinationContainer();
    }
}
