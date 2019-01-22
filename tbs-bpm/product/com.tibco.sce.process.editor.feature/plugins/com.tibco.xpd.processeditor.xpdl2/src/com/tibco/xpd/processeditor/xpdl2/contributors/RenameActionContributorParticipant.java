/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.contributors;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Participates in the rename action. Contribution ensures that all API
 * activities whose WSDL operations are auto-generated, the
 * {@link WebServiceOperation} and {@link PortTypeOperation} locations are
 * updated with the WSDL file names.
 * 
 * 2. Ensures that when a Pageflow process referenced from a user task is
 * updated when .xpdl file is renamed
 * 
 * @author rsomayaj
 * 
 */
public class RenameActionContributorParticipant extends RenameParticipant {

    /**
     * 
     */
    private static final String WSDL_FILE_EXTN = ".wsdl"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String XPDL_FILE_EXTENSION = ".xpdl"; //$NON-NLS-1$

    private static final String XPDL = "xpdl"; //$NON-NLS-1$

    private IFile xpdlFile;

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    /**
     * 
     */
    public RenameActionContributorParticipant() {
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @return
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {
        return RefactoringStatus.create(Status.OK_STATUS);
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        final WorkingCopy workingCopy =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
        EObject objXpdlPackage = workingCopy.getRootElement();
        final LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();
        final EditingDomain editingDomain = workingCopy.getEditingDomain();
        // For all processes
        if (objXpdlPackage instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package xpdlPackage =
                    (com.tibco.xpd.xpdl2.Package) objXpdlPackage;
            List<Process> processes = xpdlPackage.getProcesses();
            for (Process proc : processes) {
                scanProcess(cmd, editingDomain, proc);
            }

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(xpdlPackage);
            if (processInterfaces != null) {
                List<ProcessInterface> processInterfaceList =
                        processInterfaces.getProcessInterface();
                for (ProcessInterface processInterface : processInterfaceList) {
                    scanProcessInterface(cmd, editingDomain, processInterface);
                }

            }

        }
        try {
            if (editingDomain != null && cmd.canExecute()) {
                XpdResourcesPlugin.getStandardDisplay()
                        .syncExec(new Runnable() {
                            public void run() {
                                editingDomain.getCommandStack().execute(cmd);
                                if (workingCopy.isWorkingCopyDirty()) {
                                    try {
                                        workingCopy.save();
                                    } catch (IOException e) {
                                        LOG.error(e);
                                    }
                                }
                            }
                        });
            }

        } catch (Exception ex) {
            LOG.error(ex);
        }

        return new CompositeChange(
                Messages.RenameActionContributorParticipant_ChangeOk_Label);
    }

    /**
     * @param cmd
     * @param editingDomain
     * @param processInterface
     */
    private void scanProcessInterface(LateExecuteCompoundCommand cmd,
            EditingDomain editingDomain, ProcessInterface processInterface) {
        // GO through the start methods in the process interface
        for (StartMethod startMethod : processInterface.getStartMethods()) {
            appendInterfaceSetPortTypeLocationCmd(cmd,
                    editingDomain,
                    startMethod);
        }

        for (IntermediateMethod intermediateMethod : processInterface
                .getIntermediateMethods()) {
            appendInterfaceSetPortTypeLocationCmd(cmd,
                    editingDomain,
                    intermediateMethod);
        }
    }

    /**
     * Append Set PortType Command for Process Interface events.
     * 
     * @param cmd
     * @param editingDomain
     * @param interfaceMethod
     */
    private void appendInterfaceSetPortTypeLocationCmd(
            LateExecuteCompoundCommand cmd, EditingDomain editingDomain,
            InterfaceMethod interfaceMethod) {
        if (TriggerType.MESSAGE_LITERAL.equals(interfaceMethod.getTrigger())) {
            TriggerResultMessage triggerResultMessage =
                    interfaceMethod.getTriggerResultMessage();
            Object pTOObj =
                    Xpdl2ModelUtil.getOtherElement(triggerResultMessage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
            if (pTOObj instanceof PortTypeOperation) {
                PortTypeOperation portType = (PortTypeOperation) pTOObj;
                getSetPortTypeOpLocationCmd(cmd, editingDomain, portType);
            }
        }
    }

    /**
     * Scans through the process to find activities whose
     * {@link WebServiceOperation} and {@link PortTypeOperation} details have
     * been auto-generated
     * 
     * @param cmd
     * @param proc
     */
    private void scanProcess(LateExecuteCompoundCommand cmd, EditingDomain ed,
            Process proc) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
        for (Activity act : allActivitiesInProc) {
            // If the activity is a generated request activity then modify the
            // WSDL location

            if (Xpdl2ModelUtil.isGeneratedRequestActivity(act)
                    && WebServiceOperationUtil
                            .isWebServiceImplementationType(act)) {

                /*
                 * XPD:397 - shouldn't care whether WSDL is derived. If the xpdl
                 * file name is changing then we will be ref'ing a newly created
                 * one wsdl! if (Xpdl2ModelUtil.isWsdlDerived(act)) {
                 */
                WebServiceOperation webServiceOperation =
                        WebServiceOperationUtil.getWebServiceOperation(act);
                PortTypeOperation portTypeOperation =
                        WebServiceOperationUtil.getPortTypeOperation(act);
                if (webServiceOperation != null) {
                    getSetWebServiceLocationCmd(cmd, ed, webServiceOperation);
                }
                if (portTypeOperation != null) {
                    getSetPortTypeOpLocationCmd(cmd, ed, portTypeOperation);
                }

            }

            /**
             * XPD-1038: A Pageflow process referenced from a user task is lost
             * when .xpdl file is renamed
             */
            if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(act))) {

                FormImplementation formImpl =
                        TaskObjectUtil.getUserTaskFormImplementation(act);
                if (formImpl != null
                        && FormImplementationType.PAGEFLOW.equals(formImpl
                                .getFormType())) {
                    String oldFormURI = formImpl.getFormURI();

                    if (null != oldFormURI) {
                        String newXpdlName = getArguments().getNewName();
                        Process pageflowProcess =
                                TaskObjectUtil.getUserTaskPageflowProcess(act);

                        if (null != pageflowProcess) {

                            IPath path =
                                    SpecialFolderUtil
                                            .getSpecialFolderRelativePath(pageflowProcess);
                            String newPath =
                                    path.toString().replaceFirst(path
                                            .lastSegment(),
                                            newXpdlName);

                            URI newFormUri = URI.createURI(newPath);
                            newFormUri =
                                    newFormUri.appendFragment(pageflowProcess
                                            .getId());
                            cmd
                                    .append(TaskObjectUtil
                                            .getUserTaskSetFormImplementationCommand(ed,
                                                    act,
                                                    FormImplementationType.PAGEFLOW,
                                                    newFormUri.toString()));

                            Command repairIfcCmd =
                                    PageflowUtil
                                            .getCreateUserTaskDataForPageflowCommand(ed,
                                                    act,
                                                    act.getProcess(),
                                                    pageflowProcess,
                                                    true);
                            if (repairIfcCmd != null) {
                                cmd.append(repairIfcCmd);
                            }
                        }
                    }
                }
            }
            // XPD-1038 - ends
        }
    }

    /**
     * Gets the command that sets the {@link WebServiceOperation} location.
     * 
     * @param cmd
     * @param ed
     * @param webServiceOperation
     */
    private void getSetWebServiceLocationCmd(LateExecuteCompoundCommand cmd,
            EditingDomain ed, WebServiceOperation webServiceOperation) {
        if (webServiceOperation != null) {
            Service service = webServiceOperation.getService();
            if (service != null) {
                EndPoint endPoint = service.getEndPoint();
                if (endPoint != null) {
                    ExternalReference externalReference =
                            endPoint.getExternalReference();

                    appendSetNameCommand(ed, cmd, externalReference);
                }
            }
        }
    }

    /**
     * Gets the command that sets the {@link PortTypeOperation} location
     * 
     * @param cmd
     * @param ed
     * @param portTypeOpContainer
     */
    private void getSetPortTypeOpLocationCmd(LateExecuteCompoundCommand cmd,
            EditingDomain ed, PortTypeOperation portTypeOperation) {
        ExternalReference externalReference =
                portTypeOperation.getExternalReference();
        appendSetNameCommand(ed, cmd, externalReference);
    }

    /**
     * Returns the {@link SetCommand}
     * 
     * @param ed
     * @param externalReference
     */
    private void appendSetNameCommand(EditingDomain ed,
            LateExecuteCompoundCommand cmd, ExternalReference externalReference) {
        cmd.append(SetCommand.create(ed,
                externalReference,
                Xpdl2Package.eINSTANCE.getExternalReference_Location(),
                getWSDLFileName(getArguments().getNewName())));
    }

    /**
     * Returns the corresponding WSDL name for an XPDL.
     * 
     * @param name
     * @return
     */
    private String getWSDLFileName(String xpdlFileName) {
        int indexofXPDLExtn = xpdlFileName.indexOf(XPDL_FILE_EXTENSION);
        if (indexofXPDLExtn != -1) {
            String name = xpdlFileName.substring(0, indexofXPDLExtn);
            return name + WSDL_FILE_EXTN;
        }
        return null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.RenameActionContributorParticipant_RenameParticipant_Label;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
        if (element instanceof IFile
                && XPDL.equals(((IFile) element).getFileExtension())) {
            xpdlFile = (IFile) element;
            return true;
        }
        return false;
    }

}
