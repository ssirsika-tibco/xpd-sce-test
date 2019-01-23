/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.imports.validations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.customer.api.iprocess.internal.ConversionUtility;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.edit.util.XpdlUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Validator for iProcess Studio to AMX-BPM conversion.
 * <p>
 * Performs the following validation on the source Xpdl files and the target
 * Container
 * <p>
 * 1. Check if the XPDL files are valid studio xpdl files -Error
 * <p>
 * 2. Check if each process has iProcess Destination enabled - Error
 * <p>
 * 3. Check if multiple process have same name but different Id's - Error
 * <p>
 * 4. Check if all processes to convert are already converted and available in
 * workspace - Error
 * <p>
 * 5. If target container is not in BPM project
 * <p>
 * 6. Sub proc references
 * 
 * @author kthombar
 * @since 09-Jun-2014
 */
public class IpsToBpmConversionValidator implements IBpmConversionValidator {

    private Set<IResource> allXpdlsToConvert;

    private IContainer destinationContainer;

    private final int TOTAL_MAJOR_VALIDATIONS = 6;

    private final static String IPROCESS_DESTINATION = "iProcess"; //$NON-NLS-1$

    /**
     * Validator for iProcess Studio to AMX-BPM conversion.
     * 
     * @param allXpdlsToConvert
     *            the xpdl's to convertr
     * @param destinationContainer
     *            the destination container in which the converted xpdls will be
     *            saved
     */
    public IpsToBpmConversionValidator(Set<IResource> allXpdlsToConvert,
            IContainer destinationContainer) {
        this.allXpdlsToConvert = allXpdlsToConvert;
        this.destinationContainer = destinationContainer;

    }

    /**
     * performs the validation.
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.imports.validations.IBpmConversionValidator#validate(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.swt.widgets.ProgressBar)
     * 
     * @param monitor
     * 
     * @return the Errors/Warnings generated during validation.
     * @throws OperationCanceledException
     *             If the {@link IProgressMonitor} was cancelled by the user.
     */
    @Override
    public List<ImportValidationError> validate(IProgressMonitor monitor)
            throws OperationCanceledException {

        List<ImportValidationError> errors =
                new LinkedList<ImportValidationError>();
        String valMsgFormat = "%1s %2s : %3s"; //$NON-NLS-1$

        try {

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            monitor.beginTask("", allXpdlsToConvert.size() + 2); //$NON-NLS-1$

            /*
             * Do validation on the resources
             */
            for (IResource eachXpdl : allXpdlsToConvert) {

                monitor.setTaskName(String
                        .format(valMsgFormat,
                                Messages.IProcessImportValidator_Msg_Status_Validating,
                                eachXpdl.getName(),
                                Messages.IpsToBpmConversionValidator_CheckingXpdlValidityMonitor_msg));

                /* check if the xpdl file is valid */
                checkValidStudioXpdlFile(eachXpdl, errors);

                monitor.worked(1);

            }

            if (errors != null && !errors.isEmpty()) {
                /*
                 * if the xpdl files are invalid then return immediately and do
                 * not proceed further.
                 */
                return errors;
            }

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            monitor.setTaskName(Messages.IpsToBpmConversionValidator_ValidatingTargetMonitor_msg);

            /*
             * Validate the target container(it should be in a BPM project)
             */
            validateDestinationContainer(destinationContainer,
                    errors,
                    new SubProgressMonitor(monitor, 1));

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            monitor.setTaskName(Messages.IpsToBpmConversionValidator_ValidatingProcessIfcMonitor_msg);
            /*
             * do validation on process/interfaces within resources.
             */
            validateProcessesInXpdlFiles(allXpdlsToConvert,
                    errors,
                    new SubProgressMonitor(monitor, 1));

        } finally {
            monitor.done();
        }

        return errors;

    }

    /**
     * Check if the destination container selected is inside a BPM project
     * 
     * @param destinationContainer2
     * @param errors
     * @param monitor
     */
    @SuppressWarnings("deprecation")
    private void validateDestinationContainer(IContainer destinationContainer,
            List<ImportValidationError> errors, IProgressMonitor monitor) {

        try {

            monitor.beginTask("", 1); //$NON-NLS-1$

            IProject project = destinationContainer.getProject();

            boolean isBpmlDestinationEnabled =
                    GlobalDestinationHelper.isGlobalDestinationEnabled(project,
                            N2Utils.N2_GLOBAL_DESTINATION_ID);

            if (!isBpmlDestinationEnabled) {
                errors.add(new ImportValidationError(
                        project.getName() + "/" //$NON-NLS-1$
                                + destinationContainer.getName(),
                        Messages.IpsToBpmConversionValidator_TargetFolderNotInBpmProjError_msg,
                        IStatus.ERROR));
            }
            monitor.worked(1);
        } finally {
            monitor.done();
        }
    }

    /**
     * Validates all the processes / interfaces within the xpdl files.
     * 
     * @param allXpdlsToConvert2
     * @param errors
     * @param monitor
     */
    private void validateProcessesInXpdlFiles(
            Set<IResource> allXpdlsToConvert2,
            List<ImportValidationError> errors, IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 5); //$NON-NLS-1$

            List<EObject> processAndProcessIfcInXpdl =
                    getProcessAndProcessIfcInXpdl(allXpdlsToConvert2);

            monitor.worked(1);

            checkEachProcessInXpdlIsiProcess(processAndProcessIfcInXpdl, errors);

            monitor.worked(1);

            checkProcessWithSameNameButDiffrentId(processAndProcessIfcInXpdl,
                    errors);

            monitor.worked(1);

            checkAllConvertedProcessAlreadyExistsInWorkspace(processAndProcessIfcInXpdl,
                    errors);

            monitor.worked(1);

            checkSubProcReferencesBroken(processAndProcessIfcInXpdl, errors);
            monitor.worked(1);

        } finally {
            monitor.done();
        }

    }

    /**
     * Checks if the Imported processes have their referenced process/interface
     * also imported along. Else raises warning markers.
     * 
     * @param processAndProcessIfcInXpdl
     * @param errors
     */
    private void checkSubProcReferencesBroken(
            List<EObject> processAndProcessIfcInXpdl,
            List<ImportValidationError> errors) {
        /**
         * Id of all the process process interfaces which are being converted
         */
        Set<String> allProcessIfcIds = new HashSet<String>();

        populateSetWithIds(allProcessIfcIds, processAndProcessIfcInXpdl);

        for (EObject eObject : processAndProcessIfcInXpdl) {

            if (eObject instanceof Process) {
                Process process = (Process) eObject;

                boolean hasBrokenSubProcReferences = false;

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                IFile xpdlFile = WorkingCopyUtil.getFile(eObject);

                for (Activity eachActivity : allActivitiesInProc) {

                    Implementation impl = eachActivity.getImplementation();

                    if (impl instanceof SubFlow) {
                        SubFlow subFlow = (SubFlow) impl;
                        String processId = subFlow.getProcessId();
                        if (processId == null
                                || !allProcessIfcIds.contains(processId)) {
                            hasBrokenSubProcReferences = true;
                        }
                    }
                }
                if (hasBrokenSubProcReferences) {
                    /* if there are broken sub-process references raise warnings */
                    errors.add(new ImportValidationError(
                            xpdlFile.getName(),
                            String.format(Messages.IpsToBpmConversionValidator_ProcessReferencesMissingWarning_msg,
                                    process.getName()), IStatus.WARNING));
                }

                Object otherElement =
                        Xpdl2ModelUtil
                                .getOtherElement(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementedInterface());

                if (otherElement instanceof ImplementedInterface) {
                    ImplementedInterface implementedIfc =
                            (ImplementedInterface) otherElement;
                    String processInterfaceId =
                            implementedIfc.getProcessInterfaceId();

                    if (processInterfaceId == null
                            || !allProcessIfcIds.contains(processInterfaceId)) {
                        /*
                         * If the interface that a process implemetns is not
                         * present then show warnings.
                         */
                        errors.add(new ImportValidationError(
                                xpdlFile.getName(),
                                String.format(Messages.IpsToBpmConversionValidator_ProcessRefMissingWarning_msg),
                                IStatus.WARNING));

                    }
                }
            }
        }
    }

    /**
     * Populates the passed set with the Id's of the passed EObjects
     * 
     * @param allProcessIfcIds
     * @param processAndProcessIfcInXpdl
     */
    private void populateSetWithIds(Set<String> allProcessIfcIds,
            List<EObject> processAndProcessIfcInXpdl) {

        for (EObject eObject : processAndProcessIfcInXpdl) {
            if (eObject instanceof NamedElement) {
                allProcessIfcIds.add(((NamedElement) eObject).getId());
            }
        }

    }

    /**
     * Checks if all source process/interfaces are already converted(i.e. BPM
     * destination enabled) and available in the workspace.
     * 
     * @param processAndProcessIfcInXpdl
     * @param errors
     */
    private void checkAllConvertedProcessAlreadyExistsInWorkspace(
            List<EObject> processAndProcessIfcInXpdl,
            List<ImportValidationError> errors) {

        for (EObject procOrIfc : processAndProcessIfcInXpdl) {

            if (procOrIfc instanceof Process) {

                if (!ConversionUtility.isExistingProcess((Process) procOrIfc)) {

                    return;
                }
            } else if (procOrIfc instanceof ProcessInterface) {

                if (!ConversionUtility
                        .isExistingProcessInterface((ProcessInterface) procOrIfc)) {

                    return;
                }
            }
        }

        errors.add(new ImportValidationError(
                "", //$NON-NLS-1$
                Messages.IpsToBpmConversionValidator_AllProcessExistsInWorkspaceError_msg1,
                IStatus.ERROR));
    }

    /**
     * Checks if multipli process/interface have same name then they should have
     * the same Id.
     * 
     * @param processAndProcessIfcInXpdl
     * @param errors
     */
    private void checkProcessWithSameNameButDiffrentId(
            List<EObject> processAndProcessIfcInXpdl,
            List<ImportValidationError> errors) {

        Map<String, String> processNameToIdMap = new HashMap<String, String>();

        for (EObject eObject : processAndProcessIfcInXpdl) {

            if (eObject instanceof NamedElement) {

                IFile file = WorkingCopyUtil.getFile(eObject);

                NamedElement element = (NamedElement) eObject;
                String name = element.getName();
                if (processNameToIdMap.containsKey(name)) {

                    String id = processNameToIdMap.get(name);
                    if (id != null && !id.isEmpty()) {

                        if (!id.equals(element.getId())) {
                            errors.add(new ImportValidationError(
                                    file.getName(),
                                    String.format(Messages.IpsToBpmConversionValidator_ProcessIfcWithSameNameDiffIdError_msg,
                                            name), IStatus.ERROR));
                        }
                    }
                } else {
                    processNameToIdMap.put(name, element.getId());
                }
            }
        }
    }

    /**
     * Checks if the passed List of Process / Interface has iProcess destination
     * enabled on them.
     * 
     * @param processAndProcessIfcInXpdl
     * @param errors
     */
    private void checkEachProcessInXpdlIsiProcess(
            List<EObject> processAndProcessIfcInXpdl,
            List<ImportValidationError> errors) {

        for (EObject procOrIfc : processAndProcessIfcInXpdl) {

            if (!isIProcessDestination(procOrIfc)) {

                IFile file = WorkingCopyUtil.getFile(procOrIfc);

                errors.add(new ImportValidationError(
                        file.getName(),
                        String.format(Messages.IpsToBpmConversionValidator_IprocessIfcNotIprocessError_msg,
                                ((NamedElement) procOrIfc).getName()),
                        IStatus.ERROR));

            }
        }
    }

    /**
     * check if the passed EObject has iProcess destination enabled.
     * 
     * @param iProcessDestinationNames
     *            the iProcess Destination names.
     * @param procOrIfc
     * @return <code>true</code> if the passed EObject has iProcess destination
     *         enabled.
     */
    private boolean isIProcessDestination(EObject procOrIfc) {

        if (procOrIfc instanceof ExtendedAttributesContainer) {

            Collection<ExtendedAttribute> extendedAttributes =
                    ((ExtendedAttributesContainer) procOrIfc)
                            .getExtendedAttributes();

            for (ExtendedAttribute eachExtendedAttribute : extendedAttributes) {

                if (DestinationUtil.DESTINATION_ATTRIBUTE
                        .equals(eachExtendedAttribute.getName())) {

                    String value = eachExtendedAttribute.getValue();
                    if (IPROCESS_DESTINATION.equals(value)) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param allXpdlsToConvert2
     * @return all the {@link Process}'es and {@link ProcessInterface}'es in the
     *         passed Xpdl file.
     */
    private List<EObject> getProcessAndProcessIfcInXpdl(
            Set<IResource> allXpdlsToConvert2) {

        List<EObject> processAndInterfaces = new ArrayList<EObject>();

        for (IResource iResource : allXpdlsToConvert2) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iResource);
            EObject rootElement = workingCopy.getRootElement();
            if (rootElement instanceof Package) {

                Package pkg = (Package) rootElement;

                processAndInterfaces.addAll(pkg.getProcesses());

                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);

                if (processInterfaces != null) {
                    EList<ProcessInterface> processInterface =
                            processInterfaces.getProcessInterface();

                    processAndInterfaces.addAll(processInterface);
                }
            }
        }
        return processAndInterfaces;
    }

    /**
     * checks if the passed Xpdl file is valid.
     * 
     * @param eachXpdl
     * @param errors
     */
    private void checkValidStudioXpdlFile(IResource eachXpdl,
            List<ImportValidationError> errors) {

        boolean isInvalidXpdl = false;

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(eachXpdl);

        if (workingCopy == null || workingCopy.isInvalidFile()
                || !XpdlUtils.isSupportedXPDLFile(eachXpdl)) {
            isInvalidXpdl = true;
        }

        if (isInvalidXpdl) {
            errors.add(new ImportValidationError(
                    eachXpdl.getName(),
                    Messages.IpsToBpmConversionValidator_InvalidInputXpdlError_msg,
                    IStatus.ERROR));
        }

    }

    /**
     * Sets the minimum and maximum limits of the validator Progress Bar
     * 
     * @param validationProgressBar
     */
    private void setValidatorLimits(final ProgressBar validationProgressBar) {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                /*
                 * progressBar will be null when not called from Wizard, like
                 * called from tests.
                 */
                if (validationProgressBar != null) {
                    validationProgressBar.setMinimum(0);
                    validationProgressBar.setMaximum(TOTAL_MAJOR_VALIDATIONS
                            + allXpdlsToConvert.size());
                }
            }
        });
    }

}
