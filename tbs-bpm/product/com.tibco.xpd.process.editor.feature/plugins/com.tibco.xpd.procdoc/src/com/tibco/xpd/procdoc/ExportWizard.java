/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.procdoc;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.TransformerConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.importexport.export.wizards.ExportXPDLWizard;
import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformProgressMonitorSupport;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ImportExportCachedInfo;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * 
 * Process Package Documentation export wizard.
 * 
 * @author njpatel
 * 
 * @deprecated see {@link com.tibco.xpd.ui.documentation.DocExportWizard}.
 */
public class ExportWizard extends ExportXPDLWizard implements
        ITransformationStylesheetsProvider2, ITransformProgressMonitorSupport {

    private static final String XSLT = "/xslts/xpdl2html.xsl"; //$NON-NLS-1$

    private URL[] xslts;

    private ProcDocOptionsPage optionsPage;

    private static ImportExportCachedInfo cacheImportExportInfo = null;

    private boolean cacheTransformer = true;

    /**
     * Procedure documentation export wizard
     */
    public ExportWizard() {
        super();
        setWindowTitle(Messages.ExportWizard_title);
        setWindowMessage(Messages.ExportWizard_message);
        setOutputFileExt("html"); //$NON-NLS-1$

        // Set worspace export folder
        setWorkspaceExportFolder(Messages.ExportWizard_exportFolder_message);

        // SID - set the validation provider filter to switch off validation
        // against any dest env type...
        // (Empty list = "no validations" whereas default null = all
        // validations).
        setValidationProviderFilter(new ArrayList<String>());

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getXslts()
     */
    public URL[] getXslts() {
        if (xslts == null) {
            xslts = new URL[] { getClass().getResource(XSLT) };
        }

        return xslts;
    }

    public Map<String, String> getXsltParameters() {
        String imageFolder =
                getOSOutputFolder() + "/" + ImageCreator.IMAGES_FOLDER; //$NON-NLS-1$

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("imagesFolder", imageFolder); //$NON-NLS-1$

        if (svgImageRequired()) {
            map.put("imageFileExtension", ".svg"); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            map.put("imageFileExtension", ImageCreator.getImageFileExt()); //$NON-NLS-1$
        }

        IFile inFile = getCurrentInputFile();
        if (inFile != null) {
            long stamp = inFile.getLocalTimeStamp();
            Date dateObj = new Date(stamp);

            String modDate =
                    LocaleUtils.getLocalisedDateTime(LocaleUtils
                            .getISO8601Date(dateObj),
                            DateFormat.FULL,
                            DateFormat.MEDIUM);
            map.put("modifiedDate", modDate); //$NON-NLS-1$

            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(inFile);
            if (wc != null && wc.getRootElement() instanceof Package) {
                Package pkg = (Package) wc.getRootElement();
                if (pkg.getPackageHeader() != null) {
                    String pkgCreatedDate = pkg.getPackageHeader().getCreated();

                    String createdDate =
                            LocaleUtils.getLocalisedDateTime(pkgCreatedDate,
                                    DateFormat.FULL,
                                    DateFormat.MEDIUM);

                    map.put("createdDate", modDate); //$NON-NLS-1$
                }
            }

        }

        Collection<ProcDocOption> options = optionsPage.getProcDocOptions();
        for (ProcDocOption option : options) {
            map.put(option.getId(), option.isOn() ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        return map;
    }

    @Override
    protected ImportExportTransformer getImportExportTransformer()
            throws TransformerConfigurationException, IOException, SAXException {
        if (cacheTransformer) {
            if (cacheImportExportInfo == null) {
                ImportExportTransformer transformer =
                        new ImportExportTransformer(this);

                cacheImportExportInfo = transformer.getImportExportCachedInfo();

                return transformer;

            } else {

                return new ImportExportTransformer(this, cacheImportExportInfo);
            }
        }

        return super.getImportExportTransformer();
    }

    @Override
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        // Make sure we save the options preferences if user said so.
        optionsPage.saveOptionsToPreferences();

        IStructuredSelection files = getSelectedFiles();
        for (Iterator iterator = files.iterator(); iterator.hasNext();) {
            Object o = iterator.next();

            if (o instanceof IFile) {
                IFile file = (IFile) o;

                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault().getWorkingCopy(file);
                if (wc != null) {
                    if (wc.getRootElement() instanceof Package) {
                        String id = ((Package) wc.getRootElement()).getId();

                        if (Xpdl2WorkingCopyImpl.locatePackages(id).size() > 1) {
                            final String msg =
                                    String
                                            .format(Messages.ExportWizard_DuplicateIds_longdesc,
                                                    file.getName());
                            Display.getDefault().syncExec(new Runnable() {
                                public void run() {
                                    MessageDialog
                                            .openError(Display.getDefault()
                                                    .getActiveShell(),
                                                    Messages.ExportWizard_CantExport_title,
                                                    msg);
                                }
                            });
                            return;
                        }
                    }
                }
            }
        }

        Thread current = Thread.currentThread();
        ClassLoader oldLoader = current.getContextClassLoader();
        try {
            current.setContextClassLoader(getClass().getClassLoader());

            // Register sub-tasks - image creator and resource copier
            if (imageRequired()) {
                // Don't bother creating image if that option has been turned
                // off.
                ImageCreator imageCreator =
                        new ImageCreator(this, svgImageRequired());
                registerSubTask(imageCreator);
            }

            ResourceCopier resourceCopier =
                    new ResourceCopier(svgImageRequired());
            registerSubTask(resourceCopier);

            super.performOperation(monitor);

        } finally {
            current.setContextClassLoader(oldLoader);
        }

    }

    private boolean imageRequired() {
        ProcDocOption option =
                optionsPage.getOption(ProcDocOption.SHOW_PROCESS_IMAGES);
        if (option != null) {
            return option.isOn();
        }

        return true;
    }

    private boolean svgImageRequired() {
        ProcDocOption option =
                optionsPage.getOption(ProcDocOption.USE_SVG_IMAGES);
        if (option != null) {
            return option.isOn();
        }

        return false;
    }

    @Override
    public void addPages() {
        // Add standard export pages.
        super.addPages();

        // Add Options page.
        optionsPage = new ProcDocOptionsPage();

        addPage(optionsPage);

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider2#getImportXsltURL(java.lang.String)
     */
    public URL getImportXsltURL(String href) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider2#getMessagePropertiesURL(java.net.URL)
     */
    public URL getMessagePropertiesURL(URL xsltURL) {
        return null;
    }

    public int getMonitorSubTaskCount() {
        int subTaskCount = 0;

        IFile inFile = getCurrentInputFile();
        if (inFile != null) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(inFile);

            if (wc != null && wc.getRootElement() instanceof Package) {
                Package pkg = (Package) wc.getRootElement();

                // The export xslt can advance prog meter once per process /
                // interface.
                subTaskCount = pkg.getProcesses().size();
                ProcessInterfaces procIfcs =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);
                if (procIfcs != null) {
                    subTaskCount += procIfcs.getProcessInterface().size();
                }
            }
        }

        return subTaskCount;
    }

    public String getSubTaskLeader() {
        IFile inFile = getCurrentInputFile();
        if (inFile != null) {
            return inFile.getName()
                    + ": " + Messages.ExportWizard_ExportConvertingProgressLeader_label; //$NON-NLS-1$
        }
        return null;
    }

}
