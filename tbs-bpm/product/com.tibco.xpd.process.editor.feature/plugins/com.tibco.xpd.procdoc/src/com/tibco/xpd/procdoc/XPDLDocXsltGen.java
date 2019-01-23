/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.procdoc;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.TransformerConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.documentation.DocGenModelInfo;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;
import com.tibco.xpd.ui.documentation.engine.AbstractXSLTDocGen;
import com.tibco.xpd.ui.importexport.exportwizard.OutputFile;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ImportExportCachedInfo;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.ValidationException;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author mtorres
 */
public class XPDLDocXsltGen extends AbstractXSLTDocGen {

    private static final String XSLT = "/xslts/xpdl2html.xsl"; //$NON-NLS-1$

    private URL[] xslts;

    private static ImportExportCachedInfo cacheImportExportInfo = null;

    private boolean cacheTransformer = false;

    private IDocGenInfo currentDocGenInfo = null;

    private Map<String, IPath> processToImageMap;

    private IPath outputFilePath = null;

    /**
     * Export list of required validations providers (i.e. destination
     * environments). See
     * <code>{@link #setValidationProviderFilter(List)}</code> for more details.
     */
    private List<String> validationProviderFilter = null;

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getXslts()
     */
    @Override
    public URL[] getXslts() {
        if (xslts == null) {
            xslts = new URL[] { getClass().getResource(XSLT) };
        }

        return xslts;
    }

    @Override
    public Map<String, String> getXsltParameters() {
        IProject project = null;
        String imageFolder =
                getGeneratorOutputPath(currentDocGenInfo)
                        .append(ImageCreator.IMAGES_FOLDER).toPortableString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("imagesFolder", imageFolder); //$NON-NLS-1$

        if (svgImageRequired()) {
            map.put("imageFileExtension", ".svg"); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            map.put("imageFileExtension", ImageCreator.getImageFileExt()); //$NON-NLS-1$
        }

        IFile inFile = null;
        if (currentDocGenInfo.getSource() instanceof IFile) {
            inFile = (IFile) currentDocGenInfo.getSource();
        }

        if (inFile != null) {
            project = inFile.getProject();
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

                    map.put("createdDate", createdDate); //$NON-NLS-1$
                }
            }

        }

        List<ProcDocOption> options = new ArrayList<ProcDocOption>();
        if (project != null
                && ProcDocOption.hasProjectSpecificProcDocSettings(project)) {
            options = ProcDocOption.loadProcDocProjectOptions(project);
        } else {
            options = ProcDocOption.loadProcDocOptions();
        }
        for (ProcDocOption option : options) {
            map.put(option.getId(), option.isOn() ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        return map;
    }

    @Override
    protected ImportExportTransformer getImportExportTransformer()
            throws TransformerConfigurationException, IOException, SAXException {
        if (cacheTransformer || cacheImportExportInfo == null) {
            if (cacheImportExportInfo == null) {
                ImportExportTransformer transformer =
                        new ImportExportTransformer(this);

                cacheImportExportInfo = transformer.getImportExportCachedInfo();

                return transformer;

            }
        }

        return new ImportExportTransformer(this, cacheImportExportInfo);
    }

    private boolean imageRequired() {
        return true;
    }

    private boolean svgImageRequired() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider2#getImportXsltURL(java.lang.String)
     */
    @Override
    public URL getImportXsltURL(String href) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider2#getMessagePropertiesURL(java.net.URL)
     */
    @Override
    public URL getMessagePropertiesURL(URL xsltURL) {
        return null;
    }

    @Override
    protected String getGeneratorRootFolderName() {
        return Messages.ProcessModelFolderLabel;
    }

    @Override
    protected void runEngine(IDocGenInfo docGenInfo) {
        currentDocGenInfo = docGenInfo;
        IResource source = docGenInfo.getSource();
        if (source instanceof IFile) {
            IFile sourceFile = (IFile) source;
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(sourceFile);
            if (wc != null) {
                if (wc.getRootElement() instanceof Package) {
                    String id = ((Package) wc.getRootElement()).getId();

                    if (Xpdl2WorkingCopyImpl.locatePackages(id).size() > 1) {
                        final String msg =
                                String.format(Messages.ExportWizard_DuplicateIds_longdesc,
                                        sourceFile.getName());
                        Display.getDefault().syncExec(new Runnable() {
                            @Override
                            public void run() {
                                MessageDialog.openError(Display.getDefault()
                                        .getActiveShell(),
                                        Messages.ExportWizard_CantExport_title,
                                        msg);
                            }
                        });
                        return;
                    }
                }
            }
            Thread current = Thread.currentThread();
            ClassLoader oldLoader = current.getContextClassLoader();
            try {
                current.setContextClassLoader(getClass().getClassLoader());
                OutputFile outputFile = null;

                try {
                    outputFile = getOutputFile(sourceFile, docGenInfo);
                } catch (CoreException e1) {
                    ProcdocPlugin.getDefault().getLogger().error(e1);
                }
                if (outputFile != null) {
                    // Register sub-tasks - image creator and resource copier
                    if (imageRequired()) {
                        // Don't bother creating image if that option has been
                        // turned off.
                        ImageCreator imageCreator =
                                new ImageCreator(null, svgImageRequired());
                        try {
                            imageCreator.perform(new SubProgressMonitor(
                                    new NullProgressMonitor(), 30),
                                    sourceFile,
                                    outputFile.getFile());
                            processToImageMap =
                                    imageCreator.getProcessImagesMap();
                        } catch (CoreException e) {
                            ProcdocPlugin.getDefault().getLogger().error(e);
                        }
                    }

                    ResourceCopier resourceCopier =
                            new ResourceCopier(svgImageRequired());
                    try {
                        resourceCopier.perform(new SubProgressMonitor(
                                new NullProgressMonitor(), 30),
                                sourceFile,
                                outputFile.getFile());
                    } catch (CoreException e) {
                        ProcdocPlugin.getDefault().getLogger().error(e);
                    }
                    try {
                        performValidate(new NullProgressMonitor(), docGenInfo);

                    } catch (InterruptedException e) {
                        ProcdocPlugin.getDefault().getLogger().error(e);
                    } catch (CoreException e) {
                        ProcdocPlugin.getDefault().getLogger().error(e);
                    }

                    try {
                        performTransformation(new NullProgressMonitor(),
                                sourceFile,
                                docGenInfo);
                        if (outputFile != null && outputFile.getPath() != null) {
                            outputFilePath =
                                    new Path(outputFile.getFile()
                                            .getAbsolutePath());
                        }
                    } catch (CoreException e) {
                        ProcdocPlugin.getDefault().getLogger().error(e);
                    } catch (InterruptedException e) {
                        ProcdocPlugin.getDefault().getLogger().error(e);
                    }
                } else {
                    ProcdocPlugin.getDefault().getLogger()
                            .error(Messages.XPDLDocXsltGen_NoOutputFileFound);
                }
            } finally {
                current.setContextClassLoader(oldLoader);
            }
        }
    }

    /**
     * Returns the output file object based on the selection made in the wizard
     * with regards to the destination of the export.
     * 
     * @param inputFile
     * @return The <code>{@link OutputFile}</code> object which wraps the
     *         resource being created.
     * @throws CoreException
     */
    @Override
    public OutputFile getOutputFile(IFile inputFile, IDocGenInfo docGenInfo)
            throws CoreException {
        OutputFile outputFile = null;
        String outputFileName = getOutputFileName(inputFile.getName());

        // If the output file name is null or blank then set it to the same name
        // as the input file
        if (outputFileName == null || outputFileName.length() == 0) {
            outputFileName = inputFile.getName();
        }

        IPath generatorOutputPath = docGenInfo.getGeneratorOutputPath();
        /*
         * If export destination is the project then create the export folder in
         * the workspace if required and return an IFile
         */
        if (generatorOutputPath != null) {
            IPath path = generatorOutputPath.append(outputFileName);
            outputFile = new OutputFile(path.toFile());
        }

        return outputFile;
    }

    protected String getOutputFileName(String szInputFileName) {
        String outputFileExt = "html"; //$NON-NLS-1$;
        String outputFileName =
                szInputFileName.substring(0, szInputFileName.lastIndexOf('.'));
        outputFileName += "." + outputFileExt; //$NON-NLS-1$
        return outputFileName;
    }

    @Override
    public String getSystemId() {

        return null;
    }

    @Override
    public int getMonitorSubTaskCount() {

        return 0;
    }

    @Override
    public String getSubTaskLeader() {

        return null;
    }

    /**
     * Check for problems in the selected files before export and whether there
     * are actually any processes in the package for the intended destination
     * environment of the export.
     * 
     * @param monitor
     * @throws InterruptedException
     * @throws CoreException
     */
    private void performValidate(IProgressMonitor monitor,
            IDocGenInfo docGenInfo) throws InterruptedException, CoreException {
        /**
         * If destination environment (provider) filter has been set to 'no
         * validations required' (empty list rather than null) then there's no
         * validations to be performed.
         */
        if (validationProviderFilter == null
                || validationProviderFilter.size() == 0) {
            return;
        }

        Set<Destination> destinations = new HashSet<Destination>();

        // Get the destination env. for each provider ID provided
        for (String validationProviderId : validationProviderFilter) {
            Destination destination =
                    ValidationActivator.getDefault()
                            .getDestination(validationProviderId);

            if (destination != null) {
                destinations.add(destination);
            }
        }

        // Validate the selected resources
        if (!destinations.isEmpty()) {
            Validator validator = new Validator(destinations);

            if (validator != null) {
                /**
                 * For each selected file, run the validation; keeping track of
                 * those that have problems And keeping track of any that don't
                 * have ANY processes for given destination environment.
                 */
                List<String> pkgsWithProblems = new ArrayList<String>();
                List<String> pkgsWithNoProcesses = new ArrayList<String>();

                IResource resource = docGenInfo.getSource();

                if (resource != null) {
                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(resource);

                    if (wc != null) {
                        // Check if the processes in this package have the
                        // required destination type(s) set
                        Package pckg = (Package) wc.getRootElement();
                        if (!hasEnabledProcesses(pckg)) {
                            // Correct destination(s) not set
                            pkgsWithNoProcesses.add(resource.getName());
                        } else {
                            // Validate the processes
                            Collection<IIssue> issues;
                            try {
                                issues = validator.validate(resource);
                            } catch (ValidationException e) {
                                // Throw core exception
                                throw new CoreException(new Status(
                                        IStatus.ERROR,
                                        ImportExportPlugin.PLUGIN_ID,
                                        IStatus.OK, e.getLocalizedMessage(), e));
                            }

                            if (issues != null && !issues.isEmpty()) {
                                // There are problems with the package.
                                // Check for severe errors
                                for (IIssue issue : issues) {
                                    int severity = issue.getSeverity();// issueInfo
                                    // .
                                    // getSeverity
                                    // (
                                    // );
                                    if (severity == IMarker.SEVERITY_ERROR) {
                                        // This package has severe error so
                                        // add to warning message
                                        pkgsWithProblems
                                                .add(resource.getName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                /**
                 * Finally, if any of the packages have error level problems
                 * then inform the user then throw InterruptedException
                 */
                if (pkgsWithProblems.size() > 0
                        || pkgsWithNoProcesses.size() > 0) {
                    String szProblemList = ""; //$NON-NLS-1$

                    // Create the problems list
                    if (pkgsWithProblems.size() > 0) {
                        szProblemList +=
                                "The following packages have problems related to this export-type that must be resolved prior to export...\n";//$NON-NLS-1$

                        for (Iterator<String> it = pkgsWithProblems.iterator(); it
                                .hasNext();) {
                            String pkgName = it.next();

                            szProblemList += "\t- " + pkgName + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
                        }

                        szProblemList += "\n"; //$NON-NLS-1$
                    }

                    if (pkgsWithNoProcesses.size() > 0) {
                        szProblemList +=
                                "The following packages have no processes with destination environment set for this export-type...\n";//$NON-NLS-1$

                        for (Iterator<String> it =
                                pkgsWithNoProcesses.iterator(); it.hasNext();) {
                            String pkgName = it.next();

                            szProblemList += "\t- " + pkgName + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
                        }
                    }

                    final Display display =
                            XpdResourcesPlugin.getStandardDisplay();

                    final String szMsg = szProblemList;

                    display.syncExec(new Runnable() {
                        @Override
                        public void run() {
                            MessageDialog.openError(display.getActiveShell(),
                                    "Cannot Export",//$NON-NLS-1$
                                    szMsg);
                        }
                    });

                    // And throw the exception after user has ok'd
                    throw new InterruptedException();
                }
            }
        }
    }

    private boolean hasEnabledProcesses(Package pckg) {
        Set<String> names = new HashSet<String>();
        for (Process process : pckg.getProcesses()) {
            names.addAll(DestinationUtil.getEnabledGlobalDestinations(process));
        }
        ProcessInterfaces processInterfaces =
                (ProcessInterfaces) pckg
                        .getOtherElement(XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessInterfaces().getName());
        if (processInterfaces != null) {
            for (ProcessInterface pi : processInterfaces.getProcessInterface()) {
                names.addAll(DestinationUtil.getEnabledGlobalDestinations(pi));
            }
        }
        DestinationPreferences preferences =
                DestinationsActivator.getDefault().getDestinationPreferences();
        for (String name : names) {
            for (String componentId : preferences.getEnabledComponents(name)) {
                String validationDestinationId =
                        preferences.getValidationDestinationId(name,
                                componentId);
                if (validationProviderFilter.contains(validationDestinationId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected List<IDocGenModelInfo> createDocGenModelInfo(IResource resource) {
        List<IDocGenModelInfo> docGenModelInfo =
                new ArrayList<IDocGenModelInfo>();

        if (resource != null) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
            if (wc != null && wc.getRootElement() instanceof Package) {
                Package pckg = (Package) wc.getRootElement();
                String packageLabel = Xpdl2ModelUtil.getDisplayNameOrName(pckg);
                String changeDate = getChangeDate(resource);
                String author = null;
                String notes = null;
                if (pckg.getRedefinableHeader() != null) {
                    if (pckg.getRedefinableHeader().getAuthor() != null) {
                        author = pckg.getRedefinableHeader().getAuthor();
                    }
                }

                EList<Process> processes = pckg.getProcesses();

                if (processes != null && !processes.isEmpty()) {

                    /*
                     * populate the process doc info
                     */
                    populateProcessDocInfo(processes,
                            packageLabel,
                            changeDate,
                            author,
                            notes,
                            docGenModelInfo);

                }
                /*
                 * XPD-7192: Adding process interface documentation to main
                 * index.html documentation page.
                 */
                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pckg);

                if (processInterfaces != null) {

                    EList<ProcessInterface> processInterface =
                            processInterfaces.getProcessInterface();

                    if (processInterface != null && !processInterface.isEmpty()) {
                        /*
                         * populate the process interface doc info
                         */
                        populateProcessInterfaceDocInfo(processInterface,
                                packageLabel,
                                changeDate,
                                author,
                                notes,
                                docGenModelInfo);
                    }
                }
            }
        }
        return docGenModelInfo;
    }

    /**
     * Populates the passed 'docGenModelInfo' with the process interface related
     * doc info.
     * 
     * @param processInterface
     *            the interface
     * @param packageLabel
     *            the package lable
     * @param changeDate
     *            last change date
     * @param author
     *            the author
     * @param notes
     *            additional notes
     * @param docGenModelInfo
     *            the doc gen model info to pupulate
     */
    @SuppressWarnings("deprecation")
    private void populateProcessInterfaceDocInfo(
            EList<ProcessInterface> processInterface, String packageLabel,
            String changeDate, String author, String notes,
            List<IDocGenModelInfo> docGenModelInfo) {

        for (ProcessInterface eachInterface : processInterface) {

            DocGenModelInfo docGenXPDLModelInfo = new DocGenModelInfo();
            String description = null;

            if (eachInterface.getDescription() != null) {
                description = eachInterface.getDescription().getValue();
            }
            /*
             * get the image to display based on the type of interface it is
             */
            String imagePathForProcessType =
                    getImagePathForInterfaceType(eachInterface);

            String linkToInterface = getProcessLink(eachInterface.getId());

            populateDocGenModelInfo(docGenXPDLModelInfo,
                    null,
                    Xpdl2ModelUtil.getDisplayNameOrName(eachInterface),
                    packageLabel,
                    description,
                    changeDate,
                    author,
                    notes,
                    linkToInterface,
                    null,
                    null,
                    imagePathForProcessType,
                    Messages.XPDLDocXsltGen_ProcessInterfaceModelContainer_title,
                    "150", /* priority 150 *///$NON-NLS-1$
                    getGeneratorRootFolderName()
                            + "/" + ImageCreator.IMAGES_FOLDER //$NON-NLS-1$
                            + "/" //$NON-NLS-1$
                            + ResourceCopier.PROCESS_INTERFACE_ICON);

            docGenModelInfo.add(docGenXPDLModelInfo);
        }
    }

    /**
     * 
     * @param procInterface
     *            the interface
     * @return the image path based upon the type of interface that is passed to
     *         this method.
     */
    private String getImagePathForInterfaceType(ProcessInterface procInterface) {
        String interfaceImagePath;

        if (Xpdl2ModelUtil.isServiceProcessInterface(procInterface)) {
            /*
             * if its a service process interface then return the path of
             * service process interface icon
             */
            interfaceImagePath =
                    getGeneratorRootFolderName()
                            + "/" + ImageCreator.IMAGES_FOLDER //$NON-NLS-1$
                            + "/" //$NON-NLS-1$
                            + ResourceCopier.SERVICE_PROCESS_INTERFACE_ICON;
        } else {
            /*
             * by default return the path of the standard process interface
             * icon.
             */
            interfaceImagePath =
                    getGeneratorRootFolderName()
                            + "/" + ImageCreator.IMAGES_FOLDER //$NON-NLS-1$
                            + "/" //$NON-NLS-1$
                            + ResourceCopier.PROCESS_INTERFACE_ICON;
        }

        return interfaceImagePath;
    }

    /**
     * Populates the passed 'docGenModelInfo' with the process related doc info.
     * 
     * @param processes
     *            the processes whose doc is to be generated
     * @param packageLabel
     *            the package label
     * @param changeDate
     *            last change date
     * @param author
     *            the author
     * @param notes
     *            additional notes
     * @param docGenModelInfo
     *            the doc gen model info to populate
     */
    @SuppressWarnings("deprecation")
    private void populateProcessDocInfo(EList<Process> processes,
            String packageLabel, String changeDate, String author,
            String notes, List<IDocGenModelInfo> docGenModelInfo) {
        for (Process process : processes) {
            if (process != null) {
                DocGenModelInfo docGenXPDLModelInfo = new DocGenModelInfo();
                String description = null;
                if (process.getProcessHeader() != null
                        && process.getProcessHeader().getDescription() != null) {
                    description =
                            process.getProcessHeader().getDescription()
                                    .getValue();
                }
                String processImage = getImagePathForProcessId(process.getId());

                String imagesFolderPath =
                        new Path(processImage).removeLastSegments(1).toString();
                /*
                 * SCF-420 : pass the correct icon(based on the type of
                 * process), so that the process can be distinctly identified.
                 */
                String imagePathForProcessType =
                        getImagePathForProcessType(process, imagesFolderPath);

                String processLink = getProcessLink(process.getId());
                /*
                 * SCF-420: We no longer pass the xpdlcontainer and xpdl title
                 * to generate the Process index.html documentation as it was
                 * very specific to Process doc export. Now we have changed the
                 * index.xml(in the .documentationOutput folder) file that gets
                 * generated to have more generalised elements like
                 * 'modelconatiner' which specifies the title of the table and
                 * the priority in which the table should be displayed; which
                 * has the child element 'model' that decides the contents of
                 * the table.
                 */
                populateDocGenModelInfo(docGenXPDLModelInfo,
                        processImage,
                        Xpdl2ModelUtil.getDisplayNameOrName(process),
                        packageLabel,
                        description,
                        changeDate,
                        author,
                        notes,
                        processLink,
                        null,
                        null,
                        imagePathForProcessType,
                        Messages.XPDLDocXsltGen_ProcessModelContainer_title,
                        "100", /* priority 100 *///$NON-NLS-1$
                        imagesFolderPath
                                + "/" + ResourceCopier.BIZ_PROCESS_ICON); //$NON-NLS-1$
                docGenModelInfo.add(docGenXPDLModelInfo);
            }
        }

    }

    /**
     * 
     * @param process
     *            the process
     * @param imagesFolderPath
     *            the path of the Images folder
     * @return the Correct Image path based on the type of process (i.e. Biz
     *         process, service process, case service, biz service, pageflow)
     */
    private String getImagePathForProcessType(Process process,
            String imagesFolderPath) {

        String imagePathForProcessType;

        if (Xpdl2ModelUtil.isBusinessProcess(process)) {

            imagePathForProcessType =
                    imagesFolderPath + "/" + ResourceCopier.BIZ_PROCESS_ICON; //$NON-NLS-1$

        } else if (Xpdl2ModelUtil.isCaseService(process)) {

            imagePathForProcessType =
                    imagesFolderPath + "/" + ResourceCopier.CASE_SERVICE_ICON; //$NON-NLS-1$

        } else if (Xpdl2ModelUtil.isServiceProcess(process)) {

            imagePathForProcessType =
                    imagesFolderPath
                            + "/" + ResourceCopier.SERVICE_PROCESS_ICON; //$NON-NLS-1$

        } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {

            imagePathForProcessType =
                    imagesFolderPath + "/" + ResourceCopier.BIZ_SERVICE_ICON; //$NON-NLS-1$

        } else {
            /*
             * by default return pageflow icon.
             */
            imagePathForProcessType =
                    imagesFolderPath + "/" + ResourceCopier.PAGEFLOW_ICON; //$NON-NLS-1$
        }

        return imagePathForProcessType;
    }

    private String getImagePathForProcessId(String processId) {
        if (processToImageMap != null && processId != null) {
            IPath imagePath = processToImageMap.get(processId);
            if (imagePath != null && currentDocGenInfo != null) {
                IPath baseOutputPath = getBaseOutputPath(currentDocGenInfo);
                String absoluteImageStrPath = imagePath.toPortableString();
                if (baseOutputPath != null) {
                    String absoluteBaseOutputStrPath =
                            baseOutputPath.addTrailingSeparator()
                                    .toPortableString();
                    return makePathRelative(absoluteBaseOutputStrPath,
                            absoluteImageStrPath);
                }
            }
        }
        return null;
    }

    private String getProcessLink(String processId) {
        if (processId != null && outputFilePath != null) {
            IPath baseOutputPath = getBaseOutputPath(currentDocGenInfo);
            if (baseOutputPath != null) {
                String absoluteBaseOutputStrPath =
                        baseOutputPath.addTrailingSeparator()
                                .toPortableString();
                String processLink =
                        makePathRelative(absoluteBaseOutputStrPath,
                                outputFilePath.toPortableString());
                if (processLink != null) {
                    return processLink + "#" + processId;//$NON-NLS-1$
                }
            }
        }
        return null;
    }

    private String getChangeDate(IResource resource) {
        long stamp = resource.getLocalTimeStamp();
        Date dateObj = new Date(stamp);

        return LocaleUtils.getISO8601DateTime(dateObj);
    }

}
