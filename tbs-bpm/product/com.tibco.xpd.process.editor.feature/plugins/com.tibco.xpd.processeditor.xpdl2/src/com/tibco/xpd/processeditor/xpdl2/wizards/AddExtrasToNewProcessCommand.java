package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ParticipantUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramUtils;
import com.tibco.xpd.processwidget.adapters.CopyPasteScope;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AddNewProcessCommand
 * <p>
 * Command to help in adding a new process with extra bits for implemented
 * interface or fragment template.
 * <p>
 * The 'adding extra elements' needs process to actually be in the package (so
 * it's properly in context) BUT also need to be executed as part of the same
 * command.
 * </p>
 */
public class AddExtrasToNewProcessCommand extends LateExecuteCompoundCommand {

    private EditingDomain editingDomain;

    private WorkingCopy workingCopy;

    private Process newProcess;

    public static final String TEMPLATEDATA = "TEMPLATE_DATA"; //$NON-NLS-1$

    /**
     * @param editingDomain
     * @param workingCopy
     * @param originalCreateProcessCommand
     */
    public AddExtrasToNewProcessCommand(EditingDomain editingDomain,
            WorkingCopy workingCopy, Process newProcess,
            Command originalCreateProcessCommand) {
        super();
        this.editingDomain = editingDomain;
        this.workingCopy = workingCopy;
        this.newProcess = newProcess;
        this.append(originalCreateProcessCommand);
        this.setLabel(originalCreateProcessCommand.getLabel());
    }

    @Override
    public void execute() {
        // Execute the original command.
        super.execute();

        // Check for anything additional to do.
        if (workingCopy.getAttributes().containsKey(TEMPLATEDATA)) {

            Object templateElements =
                    workingCopy.getAttributes().get(TEMPLATEDATA);

            Command cmd =
                    addTemplateElements(editingDomain,
                            newProcess,
                            templateElements);
            if (cmd != null) {
                this.appendAndExecute(cmd);
            }

            /**
             * XPD-5834: all wizards which use this command, are removing the
             * fragment template data from wc in their respective wizard life
             * cycle, except NewProcessWizard.
             * 
             * so changed NewProcessWizard.performFinish() to remove this
             * template data in its wizard life cycle.
             * 
             * this will help in avoid getting empty process creation problems
             * when multiple case classes are selected for generating business
             * service (the problem in case of multi case class selection was -
             * only one business service was having the activities and
             * transitions and rest all were getting generated with no
             * activities/transitions because they were being removed from the
             * wc here!)
             */
            // workingCopy.getAttributes().remove(TEMPLATEDATA);
        }

    }

    /**
     * Create a command that will take the original default process created and
     * add extra bits for either a fragment template OR an interface to that
     * process.
     * 
     * @param editingDomain
     * @param process
     * @param elements
     * @return
     */
    private Command addTemplateElements(EditingDomain editingDomain,
            Process process, Object elements) {
        CompoundCommand cmd = new CompoundCommand();

        if (elements instanceof Collection
                && !((Collection) elements).isEmpty()) {
            Collection templateElements = (Collection) elements;
            CopyPasteScope scope =
                    Xpdl2ProcessDiagramUtils
                            .getCopyPasteScope(templateElements);
            EObject targetObject = null;
            Point startPoint = null;

            Pool pool = ImplementInterfaceUtil.getRelevantPool(process, cmd);
            Lane lane = null;

            lane = ImplementInterfaceUtil.getRelevantLane(pool, cmd);

            if (scope.getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
                targetObject = lane;
                startPoint = getStartingPoint(process, templateElements, cmd);
            } else if (scope.getCopyScope() == CopyPasteScope.COPY_LANES) {
                targetObject = pool;

                // The template contains lane so get rid of the default one.
                cmd.append(RemoveCommand.create(editingDomain, lane));

                startPoint = new Point(0, 0);
            } else if (scope.getCopyScope() == CopyPasteScope.COPY_POOLS) {
                targetObject = process;

                // The template contains pools so get rid of the default
                // one.
                cmd.append(RemoveCommand.create(editingDomain, pool));

                startPoint = new Point(20, 40);
            }

            if (startPoint != null) {
                //
                // Don't build the add diagram objects command now (because
                // it'll get confused about list indexes because of
                // removecommands above.
                // Instead, create a command that builds the add diagram
                // objects
                // command only on execution.
                cmd.append(new AddTemplateObjectsCommand(editingDomain,
                        process, targetObject, startPoint, templateElements));

            }

        } else if (elements instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) elements;
            ImplementedInterface implementedInterface =
                    XpdExtensionFactory.eINSTANCE.createImplementedInterface();

            WorkingCopy workingCopyOfInterface =
                    WorkingCopyUtil.getWorkingCopyFor(processInterface);
            WorkingCopy workingCopyOfContainer =
                    WorkingCopyUtil.getWorkingCopyFor(process);

            if (workingCopyOfInterface != null
                    && workingCopyOfContainer != null
                    && workingCopyOfInterface != workingCopyOfContainer) {
                IFile packageFile =
                        (IFile) workingCopyOfInterface.getEclipseResources()
                                .get(0);
                String fileNameWithoutExtension = null;
                if (packageFile != null) {
                    String fileName = packageFile.getName();
                    fileNameWithoutExtension =
                            fileName.substring(0, fileName.indexOf('.'));

                }

                implementedInterface.setPackageRef(fileNameWithoutExtension);

                ExternalPackage externalInterfacePackage =
                        ((Xpdl2WorkingCopyImpl) workingCopyOfContainer)
                                .createExternalPackage(workingCopyOfInterface);
                ((Package) workingCopyOfContainer.getRootElement())
                        .getExternalPackages().add(externalInterfacePackage);
            }
            implementedInterface
                    .setProcessInterfaceId(processInterface.getId());

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementedInterface(),
                    implementedInterface));

            Lane lane =
                    ImplementInterfaceUtil
                            .getRelevantLane(ImplementInterfaceUtil
                                    .getRelevantPool(process, cmd), cmd);

            Point initialPoint = new Point(55, 60);

            // Setting the positions on the start method while creating a
            // process from a Process Interface.
            List<StartMethod> startMethods = processInterface.getStartMethods();

            for (StartMethod startMethod : startMethods) {
                Command addImplementedStartMethodCommand =
                        ImplementInterfaceUtil
                                .getAddImplementedStartMethodCommand(editingDomain,
                                        process,
                                        startMethod,
                                        initialPoint,
                                        true);
                if (addImplementedStartMethodCommand != null) {
                    cmd.append(addImplementedStartMethodCommand);
                }
                initialPoint.y = initialPoint.y + 120;

            }
            if (startMethods.size() > 3) {
                NodeGraphicsInfo ngi = Xpdl2ModelUtil.getNodeGraphicsInfo(lane);

                ngi.setHeight(80 + startMethods.size() * 80);
            }

            initialPoint = new Point(180, 120);
            List<IntermediateMethod> intermediateMethods =
                    processInterface.getIntermediateMethods();

            for (IntermediateMethod intermediateMethod : intermediateMethods) {
                cmd.append(ImplementInterfaceUtil
                        .getAddImplementedIntermediateMethodCommand(editingDomain,
                                process,
                                intermediateMethod,
                                initialPoint,
                                true));

                initialPoint.x = initialPoint.x + 100;
                // expand width if more intermediate events.
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    private Point getStartingPoint(Process process,
            Collection templateElements, CompoundCommand cmd) {
        Point startPoint = new Point(20, 40);
        double height = 0, h = 0;
        for (Iterator iterator = templateElements.iterator(); iterator
                .hasNext();) {
            EObject object = (EObject) iterator.next();
            if (object instanceof GraphicalNode) {
                GraphicalNode graphicalNode = (GraphicalNode) object;
                NodeGraphicsInfo ngi =
                        Xpdl2ModelUtil.getNodeGraphicsInfo(graphicalNode);
                if (ngi.getCoordinates() != null) {
                    h = ngi.getCoordinates().getYCoordinate() + ngi.getHeight();
                    if (h > height)
                        height = h;
                }
            }
            // finding height
        }
        if (height > 300) {
            Pool pool = ImplementInterfaceUtil.getRelevantPool(process, cmd);

            NodeGraphicsInfo ngi =
                    Xpdl2ModelUtil
                            .getNodeGraphicsInfo((pool.getLanes().get(0)));
            if (ngi != null) {
                ngi.setHeight(height + 30);
            }

        } else if (height < 200) {
            startPoint.x = 40;
            startPoint.y = 100;
        }

        return startPoint;
    }

    /**
     * AddTemplateObjectsCommand
     * <p>
     * Command to add the fragment template elements to the given process.
     * <p>
     * The command is not built until execution time so that any previous
     * mucking about with the new process is done before we start messing with
     * it.
     * 
     */
    private class AddTemplateObjectsCommand extends CompoundCommand {

        private EditingDomain editingDomain;

        private Process process;

        private EObject targetObject = null;

        private Point startPoint = null;

        private Collection templateElements;

        /**
         * Set when the Paste operation is cancelled by user, cancelling add
         * required project references.
         */
        private boolean pasteCancelledByUser = false;

        /**
         * @param editingDomain
         * @param process
         * @param targetObject
         * @param startPoint
         * @param templateElements
         */
        public AddTemplateObjectsCommand(EditingDomain editingDomain,
                Process process, EObject targetObject, Point startPoint,
                Collection templateElements) {
            super();
            this.editingDomain = editingDomain;
            this.process = process;
            this.targetObject = targetObject;
            this.startPoint = startPoint;
            this.templateElements = templateElements;
        }

        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#getAffectedObjects()
         * 
         * @return
         */
        @Override
        public Collection<?> getAffectedObjects() {
            return pasteCancelledByUser ? Collections.EMPTY_LIST : super
                    .getAffectedObjects();
        }

        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#undo()
         * 
         */
        @Override
        public void undo() {
            if (!pasteCancelledByUser) {
                super.undo();
            }
        }

        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#redo()
         * 
         */
        @Override
        public void redo() {
            if (!pasteCancelledByUser) {
                super.redo();
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.CompoundCommand#execute()
         */
        @Override
        public void execute() {
            // Execute the commands already added to us.
            // Don't build the command until execution time.
            if (!this.appendAndExecute(Xpdl2ProcessDiagramUtils
                    .getAddDiagramObjectsCommand(editingDomain,
                            process,
                            targetObject,
                            startPoint,
                            templateElements,
                            null,
                            false,
                            true))) {
                pasteCancelledByUser = true;
            }

            if (!pasteCancelledByUser) {
                /*
                 * After adding the template objects to the process associate
                 * all the data with any user task as mandatory (otherwise the
                 * mandatory send task data may not be satisfied.
                 */

                /*
                 * Removed this from here as this is specific to some use case.
                 * Not all the templates need to do this! Specific
                 * wizard/process generation code must handle suitable to
                 * itself.
                 */

                fixFinalProcess(WorkingCopyUtil.getFile(process).getName(),
                        process);
            }
            return;
        }

        @Override
        public boolean canExecute() {
            return true;
        }

        /**
         * @param xpdlFileName
         * @param process
         */
        private void fixFinalProcess(String xpdlFileName, Process process) {
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity act : activities) {
                String wsdlFileName;
                int i = xpdlFileName.lastIndexOf("."); //$NON-NLS-1$
                if (i >= 0) {
                    wsdlFileName = xpdlFileName.substring(0, i);
                } else {
                    wsdlFileName = xpdlFileName;
                }

                wsdlFileName += ".wsdl"; //$NON-NLS-1$
                fixProcessAPIActivities(process, act, wsdlFileName);
            }
            /*
             * SID XPD-745: If there is a process api participant then fix it.
             */
            fixApiParticipantName(process);
        }

        /**
         * Change the name of the process API particiapnt for process (if there
         * is one, to correcct defaut name for it.
         * 
         * @param process
         */
        private void fixApiParticipantName(Process process) {
            Participant apiParticipant =
                    Xpdl2ModelUtil.getProcessApiActivityParticipant(process);

            /*
             * Because we created a process from scratch and simply added
             * activities to it then any api endpoint participant setting
             * required on the process may not yet be present - so let's look
             * for any api activity and use the participant that it references.
             */
            if (apiParticipant == null) {
                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {
                    if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
                        apiParticipant =
                                Xpdl2ModelUtil
                                        .getEndPointAliasParticipant(activity);
                        if (apiParticipant != null) {
                            break;
                        }
                    }
                }

                /* Add api participant attr to process */
                if (apiParticipant != null) {
                    appendAndExecute(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ApiEndPointParticipant(),
                                    apiParticipant.getId()));
                }
            }

            if (apiParticipant != null && process.getPackage() != null) {
                String particLabel =
                        ParticipantUtil
                                .getDefaultParticipantLabelForProcessApi(Xpdl2ModelUtil
                                        .getDisplayNameOrName(process),
                                        Xpdl2ModelUtil
                                                .getDisplayNameOrName(process
                                                        .getPackage()));

                String particName =
                        ParticipantUtil
                                .getDefaultParticipantNameForProcessApi(process
                                        .getName());

                appendAndExecute(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                apiParticipant,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                particLabel));

                appendAndExecute(SetCommand.create(editingDomain,
                        apiParticipant,
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        particName));
                apiParticipant.setName(particName);

                /*
                 * SDA-66: Fix the Endpoint URI to match the package and process
                 * name.
                 */
                ParticipantSharedResource participantSharedRes =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(apiParticipant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());
                if (participantSharedRes != null) {
                    EObject sharedResource =
                            participantSharedRes.getSharedResource();
                    if (sharedResource instanceof WsResource) {
                        WsInbound inbound =
                                ((WsResource) sharedResource).getInbound();
                        if (inbound != null
                                && inbound.getSoapHttpBinding().size() > 0) {
                            WsSoapHttpInboundBinding wsSoapHttpInboundBinding =
                                    inbound.getSoapHttpBinding().get(0);

                            String endpointURI =
                                    ParticipantUtil
                                            .getDefaultSharedResourceURIForProcessApi(process,
                                                    process.getName(),
                                                    process.getPackage()
                                                            .getName());

                            appendAndExecute(SetCommand
                                    .create(editingDomain,
                                            wsSoapHttpInboundBinding,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getWsSoapHttpInboundBinding_EndpointUrlPath(),
                                            endpointURI));
                        }
                    }
                }
            }

            return;
        }

        /**
         * @param process
         * @param act
         * @param string
         */
        private void fixProcessAPIActivities(Process process, Activity act,
                String wsdlFileName) {

            /*
             * SID XPD-745 - have to assume that any biz service send task in a
             * template will be a reference to a wsdl generated by the package
             * in template - as well as generated request activity or reply
             * activity.
             */
            boolean isReplyActivity = ReplyActivityUtil.isReplyActivity(act);
            boolean isRequestActivity =
                    Xpdl2ModelUtil.isGeneratedRequestActivity(act);

            if (isReplyActivity
                    || isRequestActivity
                    || WebServiceOperationUtil
                            .isInvokeBusinessProcessImplementationType(act)) {

                WebServiceOperation wso =
                        Xpdl2ModelUtil.getWebServiceOperation(act);
                PortTypeOperation pto =
                        Xpdl2ModelUtil.getPortTypeOperation(act);

                if (null != wso) {
                    Service ws = wso.getService();
                    ws.setPortName(process.getName());

                    ExternalReference wsdlLocationRef =
                            getServiceEndPointLocation(ws);
                    ExternalReference ptoLocationRef =
                            getServiceEndPointLocation(pto);

                    // replace external reference in the
                    // WebServciceOperation and PortTypeOperation
                    if (null != wsdlLocationRef
                            && null != wsdlLocationRef.getLocation()) {
                        appendAndExecute(SetCommand.create(editingDomain,
                                wsdlLocationRef,
                                Xpdl2Package.eINSTANCE
                                        .getExternalReference_Location(),
                                wsdlFileName));
                    }

                    if (null != ptoLocationRef
                            && null != ptoLocationRef.getLocation()) {
                        appendAndExecute(SetCommand.create(editingDomain,
                                ptoLocationRef,
                                Xpdl2Package.eINSTANCE
                                        .getExternalReference_Location(),
                                wsdlFileName));
                    }
                }

                /*
                 * Sid XPD-2139. Name operation after the reuqest activity
                 * (which is named after the process which is named after the
                 * package which is named after the project.
                 */
                Activity requestActivity = null;

                if (isRequestActivity) {
                    requestActivity = act;

                } else if (isReplyActivity) {
                    requestActivity =
                            ReplyActivityUtil
                                    .getRequestActivityForReplyActivity(act);
                }

                if (requestActivity != null) {
                    String requestName;

                    /*
                     * Note: Currently this code relies on the request activity
                     * being in template activity list prior to the reply
                     * activity.
                     */
                    if (isRequestActivity) {
                        String requestLabel =
                                Xpdl2ModelUtil
                                        .getDisplayNameOrName(requestActivity)
                                        + "-" + Xpdl2ModelUtil.getDisplayNameOrName(process); //$NON-NLS-1$
                        requestName =
                                NameUtil.getInternalName(requestLabel, false);

                        appendAndExecute(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        requestActivity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName(),
                                        requestLabel));

                        appendAndExecute(SetCommand.create(editingDomain,
                                requestActivity,
                                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                                requestName));

                    } else {
                        requestName = requestActivity.getName();
                    }

                    if (wso != null) {
                        appendAndExecute(SetCommand
                                .create(editingDomain,
                                        wso,
                                        Xpdl2Package.eINSTANCE
                                                .getWebServiceOperation_OperationName(),
                                        requestName));
                        if (wso.getService() != null) {
                            appendAndExecute(SetCommand.create(editingDomain,
                                    wso.getService(),
                                    Xpdl2Package.eINSTANCE
                                            .getService_PortName(),
                                    process.getName()));
                        }
                    }

                    if (pto != null) {
                        appendAndExecute(SetCommand.create(editingDomain,
                                pto,
                                XpdExtensionPackage.eINSTANCE
                                        .getPortTypeOperation_OperationName(),
                                requestName));
                        appendAndExecute(SetCommand.create(editingDomain,
                                pto,
                                XpdExtensionPackage.eINSTANCE
                                        .getPortTypeOperation_PortTypeName(),
                                process.getName()));
                    }
                }

            }

            return;
        }

        /**
         * @param ws
         * @return The external reference location from
         *         Service/EndPoint/ExternalReference.
         */
        private ExternalReference getServiceEndPointLocation(Service ws) {
            if (ws != null) {
                EndPoint endPoint = ws.getEndPoint();
                if (endPoint != null) {
                    ExternalReference externalReference =
                            endPoint.getExternalReference();
                    if (externalReference != null) {
                        return externalReference;
                    }
                }
            }

            return null;
        }

        /**
         * @param ws
         * @return The external reference location from
         *         Activity/xpdExt:PortTypeOperation/ExternalReference.
         */
        private ExternalReference getServiceEndPointLocation(
                PortTypeOperation portTypeOperation) {

            if (portTypeOperation != null) {
                ExternalReference externalReference =
                        portTypeOperation.getExternalReference();

                if (externalReference != null) {
                    return externalReference;
                }
            }

            return null;
        }

    }
}