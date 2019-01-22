/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class AddPortTypeForImplementedActivityCommand extends CompoundCommand {

    private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    private TransactionalEditingDomain editingDomain;

    private Activity activity;

    /**
     * @param domain
     */
    public AddPortTypeForImplementedActivityCommand(
            TransactionalEditingDomain ed, Activity activity) {
        this.editingDomain = ed;
        this.activity = activity;
    }

    /**
     * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
     * 
     */
    @Override
    public void execute() {
        InterfaceMethod implementedMethod =
                ProcessInterfaceUtil.getImplementedMethod(activity);

        if (implementedMethod != null) {
            copyPortTypeOperation(activity, implementedMethod);
            prepareWebServiceOperation(activity);
        }

    }

    /**
     * 
     * @param ed
     * @param activity
     * @param interfaceMethod
     * @return
     */
    private void copyPortTypeOperation(Activity activity,
            InterfaceMethod interfaceMethod) {
        // Copy the port type operation, associated parameters, data
        // mappings for the start activity
        TriggerResultMessage ifcMethodTrm =
                interfaceMethod.getTriggerResultMessage();
        if (ifcMethodTrm != null) {

            Event event = activity.getEvent();
            if (event instanceof StartEvent) {
                this.appendAndExecute(SetCommand.create(editingDomain,
                        event,
                        Xpdl2Package.eINSTANCE.getStartEvent_Implementation(),
                        ImplementationType.WEB_SERVICE_LITERAL));
            } else if (event instanceof IntermediateEvent) {
                this.appendAndExecute(SetCommand.create(editingDomain,
                        event,
                        Xpdl2Package.eINSTANCE
                                .getIntermediateEvent_Implementation(),
                        ImplementationType.WEB_SERVICE_LITERAL));
            }

            this.appendAndExecute(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            event,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType(),
                            TaskImplementationTypeDefinitions.WEB_SERVICE));

            PortTypeOperation pTO = getPortTypeOperation(ifcMethodTrm);
            if (pTO != null) {
                EObject copiedPTO = EcoreUtil.copy(pTO);
                TriggerResultMessage actTrm =
                        EventObjectUtil.getTriggerResultMessage(activity);
                if (actTrm != null) {
                    this.appendAndExecute(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    actTrm,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Generated(),
                                    Boolean.TRUE));
                    this.appendAndExecute(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    actTrm,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation(),
                                    copiedPTO));
                }
            }
        }
        return;
    }

    private PortTypeOperation getPortTypeOperation(TriggerResultMessage trm) {
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(trm,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PortTypeOperation());
        if (otherElement instanceof PortTypeOperation) {
            return (PortTypeOperation) otherElement;
        }
        return null;
    }

    /**
     * @param activity
     */
    private void prepareWebServiceOperation(Activity activity) {
        TriggerResultMessage triggerResultMessage =
                EventObjectUtil.getTriggerResultMessage(activity);

        if (triggerResultMessage != null) {
            WebServiceOperation webServiceOperation =
                    triggerResultMessage.getWebServiceOperation();
            if (webServiceOperation == null) {
                webServiceOperation =
                        ElementsFactory.createWebServiceOperation();
                this.appendAndExecute(SetCommand.create(editingDomain,
                        triggerResultMessage,
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultMessage_WebServiceOperation(),
                        webServiceOperation));
            }

            if (webServiceOperation != null) {
                this.appendAndExecute(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                webServiceOperation,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SecurityProfile(),
                                EMPTY_STRING));
                /*
                 * XPD-3669: While fixing XPD-3547 it has been decided that
                 * transport type for generated services (and abstract operations
                 * from user defined wsdls) must be service virtualisation.
                 */
                this.appendAndExecute(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                webServiceOperation,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Transport(),
                                AddPortTypeCommand.SERVICE_VIRTUALIZATION_URL));

                this.appendAndExecute(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                webServiceOperation,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Alias(),
                                EMPTY_STRING));

                Service service = webServiceOperation.getService();
                PortTypeOperation pTO =
                        getPortTypeOperation(triggerResultMessage);
                if (pTO != null) {
                    if (pTO.getExternalReference() != null) {
                        EObject copiedExternalReference =
                                EcoreUtil.copy(pTO.getExternalReference());
                        EndPoint endPoint = service.getEndPoint();
                        if (endPoint == null) {
                            endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
                            endPoint.setExternalReference((ExternalReference) copiedExternalReference);
                        }
                    }
                }

                /*
                 * Assign the endpoint to the default api activity endpoint
                 * participant for process.
                 */
                if (AddPortTypeCommand.needsEndPointParticipant(activity
                        .getProcess(), activity, webServiceOperation)) {
                    this.appendAndExecute(AddPortTypeCommand
                            .getAssignDefaultApiParticipantCommand(editingDomain,
                                    activity.getProcess(),
                                    activity,
                                    webServiceOperation));
                }

                if (service != null) {
                    this.appendAndExecute(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    service,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_IsRemote(),
                                    Boolean.TRUE));
                }
            }

        }
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     * 
     * @return
     */
    @Override
    public boolean canExecute() {
        return true;
    }
}
