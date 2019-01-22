/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.imports.validations;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.customer.api.iprocess.internal.ConversionUtility;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * Validator for Import xpdl files.Uses {@link IProcessXpdlValidator} to
 * validate individual Xpdl files.Additionally validates following 1) Process
 * with same name should have same GUID [Error]. 2) Process with same name
 * should have same ID. [Error].
 * 
 * Collects List of Warnings and errors, which can be obtained by using
 * getErrors() method, after the validation is run.
 * 
 * @author aprasad
 * @since 15-Apr-2014
 */
public class IProcessImportValidator implements IBpmConversionValidator {

    private Collection<File> files;

    private IProject targetProject;

    /**
     * Collection of process Name for all processes in Workspace
     */
    private Collection<String> allBPMProcessInWorkspace;

    /**
     * Collection process Name for all process Interfaces in Workspace
     */
    private Collection<String> allBPMProcessIfcInWorkspace;

    public IProcessImportValidator(IProject targetProject,
            Collection<File> iProcessXPDLFiles) {
        this.files = iProcessXPDLFiles;
        this.targetProject = targetProject;

        allBPMProcessInWorkspace = new LinkedList<String>();
        allBPMProcessIfcInWorkspace = new LinkedList<String>();
        readAllBPMProcessAndIfcInWorkspace();
    }

    /**
     * Runs the validations on the XPDL File being imported.Calls on individual
     * methods defined for each validation, which adds appropriate
     * errors/warnings to the errors list.
     * 
     * The method adds all of the calculated errors into the <code>errors</code>
     * array list which is then checked for size.
     * 
     * @param monitor
     *            the {@link IProgressMonitor} to track progress
     * @return the Errors/Warnings generated during validation.
     * @throws OperationCanceledException
     *             If the {@link IProgressMonitor} was cancelled by the user.
     */

    @Override
    public List<ImportValidationError> validate(IProgressMonitor monitor)
            throws OperationCanceledException {

        List<ImportValidationError> errors =
                new LinkedList<ImportValidationError>();
        /*
         * validate multiple processes with same name should have the same
         * ID/GUID id, and there are processes not already in workspace , to be
         * imported
         */

        validateProcessesToImportAndDuplicates(monitor, errors);
        return errors;
    }

    /**
     * This method validates the duplicate processes included in import to have
     * same GUID, and also checks if there are processes not already in
     * workspace to be imported. Raises Validation Error when all the processes
     * being imported already exist in the workspace, and duplicate[based on
     * process name] processes being imported have different GUID.
     * 
     * Both the validations are combined in this method to avoid iterating on
     * the imported xpdls twice.
     * 
     * @param monitor
     * @param errors
     */
    private void validateProcessesToImportAndDuplicates(
            IProgressMonitor monitor, List<ImportValidationError> errors)
            throws OperationCanceledException {

        /*
         * collect all visited process being imported
         * <processName,Map<String,String>>, Collects Map containing process id
         * and originating file name against the process name.
         */
        Map<String, Map<String, String>> visitedProcessDetailsMap =
                new HashMap<String, Map<String, String>>();
        /*
         * Duplicate processes name with different GUID, collects message string
         * in format processName(originatingXPDLName) in the Map<GUID, message
         * String>
         */
        Map<String, String> nonMatchingDuplicateProcesses =
                new HashMap<String, String>();

        Exception exception = null;
        Collection<String> processesToImport = new LinkedList<String>();
        monitor.beginTask(Messages.IProcessImportValidator_Msg_Status_Validating,
                files.size());

        int invalidFilesCount = 0;

        for (File file : files) {
            int processesToWriteForThisFile = 0;

            monitor.setTaskName(String.format("%1s %2s", //$NON-NLS-1$
                    Messages.IProcessImportValidator_Msg_Status_Validating,
                    file.getName()));

            // Validate Individual file
            IProcessXpdlValidator validator =
                    new IProcessXpdlValidator(targetProject, file,
                            allBPMProcessInWorkspace,
                            allBPMProcessIfcInWorkspace);

            validator.validate(new SubProgressMonitor(monitor, 1));

            Collection<ImportValidationError> xpdlValidationErrors =
                    validator.getErrors();

            /*
             * XPD-6287: Do not validate this file further when this is Invalid
             * file
             */
            if (xpdlValidationErrors != null
                    && xpdlValidationErrors.size() == 1) {

                ImportValidationError error =
                        xpdlValidationErrors.iterator().next();

                if (error.getSeverity() == IStatus.ERROR
                        && Messages.IProcessXpdlValidator_NotIProcessResourceMessage
                                .equals(error.getMessage())) {
                    // collect error and do not validate further

                    invalidFilesCount++;
                    errors.addAll(xpdlValidationErrors);
                    continue;
                }

            }

            monitor.setTaskName(String.format("%1s %2s : %3s", //$NON-NLS-1$
                    Messages.IProcessImportValidator_Msg_Status_Validating,
                    file.getName(),
                    Messages.IProcessImportValidator_ValidatingDupProcGUIDMsg));

            /*
             * XPD-6154 : Check if the given XPDL contains at least one Process
             * for iProcess to be converted.
             */
            Map<String, ProcInFile> allProcessInFile =
                    validator.getAllProcessInFile();

            if (allProcessInFile != null && !allProcessInFile.isEmpty()) {
                Collection<String> remProcessesInFileToImport =
                        new ArrayList<String>(allProcessInFile.keySet());

                /* remove all already in workspace */
                remProcessesInFileToImport.removeAll(allBPMProcessInWorkspace);
                remProcessesInFileToImport
                        .removeAll(allBPMProcessIfcInWorkspace);

                if (!remProcessesInFileToImport.isEmpty()) {
                    processesToImport.addAll(remProcessesInFileToImport);
                }
            }

            /* get all process nodes */
            for (String procName : allProcessInFile.keySet()) {

                if (procName != null) {

                    String procesGUID = null;
                    /* get GUID */

                    ProcInFile procInFile = allProcessInFile.get(procName);
                    procesGUID = procInFile.getGuid();

                    /*
                     * Get details of process with same name already visited
                     */
                    Map<String, String> existingProcessDetails =
                            visitedProcessDetailsMap.get(procName);
                    /*
                     * if there exists another process with same name, validate
                     * to have same GUID
                     */
                    if (existingProcessDetails != null) {

                        String existingDuplicateID =
                                existingProcessDetails.get("GUID"); //$NON-NLS-1$

                        // Collect NON Matching Duplicates
                        if (!procesGUID.equals(existingDuplicateID)) {
                            /* Collect current Process */
                            if (!nonMatchingDuplicateProcesses.keySet()
                                    .contains(procesGUID)) {
                                nonMatchingDuplicateProcesses.put(procesGUID,
                                        String.format(" %1s:%2s", procName, //$NON-NLS-1$
                                                file.getName()));

                            }
                            /*
                             * Collect already visited conflicting process, if
                             * not already done
                             */
                            if (!nonMatchingDuplicateProcesses.keySet()
                                    .contains(existingDuplicateID)) {

                                nonMatchingDuplicateProcesses
                                        .put(existingDuplicateID,
                                                String.format(" %1s:%2s", procName,//$NON-NLS-1$ 
                                                        existingProcessDetails
                                                                .get("FILENAME"))); //$NON-NLS-1$

                            }

                        } else {
                            processesToWriteForThisFile++;
                        }
                    } else {
                        processesToWriteForThisFile++;
                        /* Collect Visited Process Details */
                        Map<String, String> procDetails =
                                new HashMap<String, String>();
                        procDetails.put("GUID", procesGUID); //$NON-NLS-1$
                        procDetails.put("FILENAME", //$NON-NLS-1$
                                file.getName());

                        visitedProcessDetailsMap.put(procName, procDetails);

                    }

                }
            }

            /*
             * When there are processes to be written/imported from this,
             * include the validation errors, otherwise no need to show
             * validation problems if all processes already exist in workspace
             */
            if (processesToWriteForThisFile > 0) {
                /* Collect Validation Errors */
                errors.addAll(xpdlValidationErrors);
            }

            if (exception != null) {

                IProcessAMXBPMConverterPlugin
                        .getDefault()
                        .getLogger()
                        .log(new Status(
                                IStatus.ERROR,
                                IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                                String.format("%1s %2s", //$NON-NLS-1$
                                        Messages.IProcessXpdlValidator_MsgErrorValidatingXPDL,
                                        file.getName()), exception));
            }

        }

        /*
         * add error for non matching Processes, XPD-6342: Process name should
         * be separated in the nonMatching GUID error message
         */
        if (!nonMatchingDuplicateProcesses.isEmpty()) {
            for (String procDetails : nonMatchingDuplicateProcesses.values()) {

                /*
                 * XPD-6342: Saket: Dividing the error message amongst each
                 * defective pair of processes so as to make sure that message
                 * doesn't get truncated.
                 */

                String[] procDetailValues = procDetails.split(":"); //$NON-NLS-1$

                /* TO Make sure it has file name and process name both in */
                if (procDetailValues.length > 0) {
                    String msg =
                            Messages.IProcessImportValidator_ErrorMsgDuplicateProcessNonMatchingGUID
                                    + procDetailValues[0];

                    /* Use file name when available */
                    errors.add(new ImportValidationError(
                            (procDetailValues.length > 1) ? procDetailValues[1]
                                    : null, msg, IStatus.ERROR));
                }
            }

        }

        /*
         * Raise error when there are no processes to import [all processes
         * already exist in workspace], don't add when all files being imported
         * are invalid, in which case anyways there won;t be any processes to
         * import.
         */
        if (processesToImport.isEmpty() && (invalidFilesCount != files.size())) {

            errors.add(new ImportValidationError(
                    "", //$NON-NLS-1$
                    Messages.IProcessXpdlValidator_ErrorMsgAllProcessOrInterfaceAlreadyExistInWorkspace,
                    IStatus.ERROR));
        }
        // Finally Finish monitor
        monitor.done();
    }

    /**
     * Reads and caches list of name for all the Process/Process Interface set
     * for BPM destination, existing in the workspace.
     */
    private void readAllBPMProcessAndIfcInWorkspace() {

        /* cache list of process names */
        allBPMProcessInWorkspace
                .addAll(getAllProcOrInterfaceForBPMInWorkspace(ProcessResourceItemType.PROCESS
                        .name()));

        /* cache list of process Interface names */
        allBPMProcessIfcInWorkspace
                .addAll(getAllProcOrInterfaceForBPMInWorkspace(ProcessResourceItemType.PROCESSINTERFACE
                        .name()));

    }

    /**
     * Returns list of names for Process(s)/Process Interface(s) for BPM
     * destination , existing in the workspace.
     * 
     * @param procOrIfc
     * @return Collection of name of Process(s)/Process Interface(s).
     */
    private Collection<String> getAllProcOrInterfaceForBPMInWorkspace(
            String procOrIfc) {

        List<EObject> allProcessIndexedElements =
                ProcessUIUtil
                        .getAllProcessIndexedElements(ProcessResourceItemType.PROCESSINTERFACE);

        Collection<String> allProcessOrInterfaceInWorkspace =
                new LinkedList<String>();

        for (EObject processIndexedElement : allProcessIndexedElements) {

            if (processIndexedElement != null) {

                IFile file = WorkingCopyUtil.getFile(processIndexedElement);

                if (file != null) {

                    IContainer processPackageFolder = file.getParent();

                    if (processPackageFolder != null) {
                        /*
                         * Proceed only if the Process or Interface is not in
                         * the hidden special folder or the folder name does not
                         * start with '.'(dot).
                         */
                        if (!processPackageFolder.isHidden()
                                && !processPackageFolder.getName()
                                        .startsWith(".")) { //$NON-NLS-1$

                            /*
                             * Check for existing process with BPM destination ,
                             * duplicate only if this is a BPM destination
                             * process/interface
                             */
                            if (ConversionUtility
                                    .containsBPMDestination(processIndexedElement) == true) {

                                allProcessOrInterfaceInWorkspace
                                        .add(((NamedElement) processIndexedElement)
                                                .getName());
                            }
                        }
                    }
                }
            }
        }

        return allProcessOrInterfaceInWorkspace;
    }
}
