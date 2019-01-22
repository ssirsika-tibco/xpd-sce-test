/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.importexport.export.wizards;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.preferences.DestinationPreferences;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard.ExportDestination;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;
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
import com.tibco.xpd.xpdl2.edit.util.XpdlUtils;

/**
 * This abstract class extends <code>AbstractExportWizard</code> to provide a
 * wizard that exports XPDL files. This class provides the following
 * functionality:
 * <ul>
 * <li>Runs validation rules against the xpdl files selected prior to export.</li>
 * <li>Provides filters that will only allow the user to select xpdl files from
 * the process packages folders.</li>
 * </ul>
 * <p>
 * The following methods can be called to customise the XPDL export wizard:
 * </p>
 * <ul>
 * <li><code>{@link #setValidationProviderFilter(List)}</code> - Sets the
 * validation to run. See method for more details.</li>
 * <li><code>{@link #setOutputFileExt(String)}</code> - Sets the file extension
 * of the file being produced during the export. This is set to 'xpdl' by
 * default.</li>
 * </ul>
 * <p>
 * For more details on using this export wizard class see
 * <code>AbstractExportWizard</code>.
 * </p>
 * 
 * @see AbstractExportWizard
 * 
 * @author njpatel
 * 
 */
public abstract class ExportXPDLWizard extends AbstractExportWizard {

    // Output file extension
    private String outputFileExt = "xpdl"; //$NON-NLS-1$

    /**
     * Export list of required validations providers (i.e. destination
     * environments). See
     * <code>{@link #setValidationProviderFilter(List)}</code> for more details.
     */
    private List<String> validationProviderFilter = null;

    /**
     * <p>
     * Set the filter list of validation providers whose error-level problems
     * must not exist before export can complete.
     * </p>
     * <p>
     * If providerIDFilter = null then validation filter is turned OFF i.e. all
     * provider's validations are used. This is the default behaviour for
     * export.
     * </p>
     * <p>
     * Otherwise this function returns true if the provider id is in the list
     * and false if it isn't. Therefore... - if you want all problems for all
     * destination environments (providers) then pass null (don't filter). - if
     * you want to not validate against any particular destination environment
     * then pass list with no entries. - if you want to validate against one or
     * more dest env's then pass their provider id's in the list.
     * </p>
     * 
     * @param providerIDFilter
     */
    public void setValidationProviderFilter(List<String> providerIDFilter) {
        validationProviderFilter = providerIDFilter;
    }

    /**
     * Set the extension of the exported file, e.g. "xpdl". Do not include the
     * extension separator.
     * 
     * @param ext
     */
    public void setOutputFileExt(String ext) {

        ext = ext.trim();
        // Remove any leading period
        if (ext.charAt(0) == '.') {
            ext = ext.substring(1);
        }

        outputFileExt = ext;
    }

    @Override
    public String getExportFileExt() {
        return outputFileExt;
    }

    @Override
    protected ViewerFilter[] getFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];

        // Only show xpdl files
        Set<String> extensions = Collections.singleton("xpdl"); //$NON-NLS-1$
        filters[0] = new FileExtensionInclusionFilter(extensions);

        // Only show packages folders
        Set<String> projFolderKind =
                Collections
                        .singleton(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        filters[1] = new SpecialFoldersOnlyFilter(projFolderKind);

        // Only include XPD nature projects
        filters[2] = new XpdNatureProjectsOnly();

        // Don't drill down into the xpdl files
        filters[3] = new NoFileContentFilter();

        return filters;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard#
     * performOperation(org.eclipse.core.runtime.IProgressMonitor)
     */
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        // SID - Check for any validation problems for given
        // destination environment.
        performValidate(monitor);

        super.performOperation(monitor);

        //
        // Perform Post-processing on OUTPUT files.
        // Cannot do this with IFiles as the user MAY have selected a folder
        // outside of the workspace (which cannot be represented with an IFile).
        //
        // This still leaves us with the old refresh problem in that because we
        // are using java.io.FIle we are changing the file behind eclipse's
        // back.
        // Therefore we will perform a refresh on the output folder(s) at the
        // end.
        //
        IStructuredSelection selectedFiles = getSelectedFiles();

        Set<IFolder> outFolders = new HashSet<IFolder>();

        for (Iterator<?> iter = selectedFiles.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof IFile) {
                IFile inputFile = (IFile) obj;

                String outFileName = inputFile.getName();

                String path =
                        getOSOutputFolder() + File.separatorChar + outFileName;

                File tempFile = new File(path);
                performOutputModifications(tempFile);

                if (getExportDestinationSelection() == ExportDestination.PROJECT) {
                    //
                    // If outputting to folder in project (workspace) then we
                    // will have to force a refresh because we've just hacked
                    // the file as a Java IO file (which we must do because file
                    // isn't necessarily in the workspace).
                    IProject proj = inputFile.getProject();

                    IFolder outFolder =
                            proj.getFolder(getWorkspaceExportFolder());
                    if (outFolder.exists()) {
                        outFolders.add(outFolder);
                    }
                }
            }
        }

        for (IFolder outFolder : outFolders) {
            outFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
        }

    }

    protected void performOutputModifications(File outputFile) {
        String xpdlContents;
        try {
            xpdlContents =
                    XpdlUtils.getXPDLContents(outputFile, getCharsetEncoding());
            xpdlContents =
                    XpdlUtils.removeInterfaceOperationSelections(xpdlContents);
            xpdlContents = XpdlUtils.removeEmptyRunTimeAlias(xpdlContents);
            xpdlContents = XpdlUtils.changeEmptySecurityProfile(xpdlContents);
            xpdlContents = XpdlUtils.removeWSCarriageReturns(xpdlContents);
            xpdlContents = XpdlUtils.removeBWCarriageReturns(xpdlContents);
            XpdlUtils.setFileContents(outputFile,
                    xpdlContents,
                    getCharsetEncoding());
        } catch (IOException e) {
        }
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
    private void performValidate(IProgressMonitor monitor)
            throws InterruptedException, CoreException {
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

                for (Iterator<?> iter = getSelectedFiles().iterator(); iter
                        .hasNext();) {
                    IAdaptable adaptable = (IAdaptable) iter.next();
                    IResource resource =
                            (IResource) adaptable.getAdapter(IResource.class);

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
                                            IStatus.OK,
                                            e.getLocalizedMessage(), e));
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
                                            pkgsWithProblems.add(resource
                                                    .getName());
                                            break;
                                        }
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
                                Messages.ExportXPDLWizard_ProblemWithDestinationType;

                        for (Iterator<String> it = pkgsWithProblems.iterator(); it
                                .hasNext();) {
                            String pkgName = (String) it.next();

                            szProblemList += "\t- " + pkgName + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
                        }

                        szProblemList += "\n"; //$NON-NLS-1$
                    }

                    if (pkgsWithNoProcesses.size() > 0) {
                        szProblemList +=
                                Messages.ExportXPDLWizard_NoProcessesWithDestinationType;

                        for (Iterator<String> it =
                                pkgsWithNoProcesses.iterator(); it.hasNext();) {
                            String pkgName = (String) it.next();

                            szProblemList += "\t- " + pkgName + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
                        }
                    }

                    final Display display = getShell().getDisplay();

                    final String szMsg = szProblemList;

                    display.syncExec(new Runnable() {
                        public void run() {
                            MessageDialog
                                    .openError(getShell(),
                                            Messages.ExportXPDLWizard_CannotExportDialogTitle,
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
}
