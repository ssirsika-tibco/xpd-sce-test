/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Common functionality for all Message Adapters on activities. Note the odd way
 * that mappings are stored, inputs go in the data mappings as you would expect,
 * but outputs are done as assignments on the Activity (which allows the
 * multiple formal parameter outputs to be combined in an expression and
 * assigned to process data.
 * 
 * @author scrossle
 * @author rsomayaj
 * 
 */
public abstract class AbstractActivityMessageAdapter extends
        BaseAbstractActivityMessageAdapter implements ActivityMessageProvider {

    /**
     * @param ws
     *            The service to assign.
     * @param endPointLocation
     *            The endpoint.
     * @return The command to make the assignment.
     */
    protected void assignWebServiceEndpoint(Service ws, String endPointLocation) {
        EndPoint endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
        endPoint.setEndPointType(EndPointTypeType.WSDL_LITERAL);

        ExternalReference ref =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref.setLocation(endPointLocation);
        endPoint.setExternalReference(ref);

        ws.setEndPoint(endPoint);
    }

    /**
     * @param ed
     *            The editing domain.
     * @param ws
     *            The service to assign.
     * @param endPointLocation
     *            The endpoint.
     * @return The command to make the assignment.
     */
    protected Command getAssignWebServiceEndpointCommand(EditingDomain ed,
            Service ws, String endPointLocation) {
        CompoundCommand command =
                new CompoundCommand(
                        Messages.AbstractActivityMessageAdapter_AssignEndpointCommand);
        EndPoint endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
        endPoint.setEndPointType(EndPointTypeType.WSDL_LITERAL);

        ExternalReference ref =
                Xpdl2Factory.eINSTANCE.createExternalReference();
        ref.setLocation(endPointLocation);
        endPoint.setExternalReference(ref);

        command.append(SetCommand.create(ed,
                ws,
                Xpdl2Package.eINSTANCE.getService_EndPoint(),
                endPoint));

        return command;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param ws
     *            The service to assign.
     * @param isRemote
     *            Whether remote wsdl or not
     * @return The command to make the assignment.
     */
    protected Command getAssignWebServiceIsRemoteCommand(EditingDomain ed,
            Service ws, boolean isRemote) {
        CompoundCommand command =
                new CompoundCommand(
                        Messages.AbstractActivityMessageAdapter_AssignIsRemoteCommand);

        command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                ws,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsRemote(),
                isRemote));

        return command;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.script.ActivityMessageProvider#getMessageIn
     * (com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public abstract Message getMessageIn(Activity act);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.script.ActivityMessageProvider#getMessageOut
     * (com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public abstract Message getMessageOut(Activity act);

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.script.ActivityMessageProvider#
     * getWebServiceOperation(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public abstract WebServiceOperation getWebServiceOperation(Activity act);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.script.ActivityMessageProvider#getPortTypeOperation
     * (com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public abstract PortTypeOperation getPortTypeOperation(Activity act);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.script.ActivityMessageProvider#hasMappingIn
     * (com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public abstract boolean hasMappingIn(Activity act);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.script.ActivityMessageProvider#hasMappingOut
     * (com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public abstract boolean hasMappingOut(Activity act);

    @Override
    public Command getAssignWebServiceEndpointCommand(EditingDomain ed,
            Activity act, String endpoint) {
        WebServiceOperation wso = getWebServiceOperation(act);
        Service ws = null;
        if (wso != null) {
            ws = wso.getService();
        }

        return getAssignWebServiceEndpointCommand(ed, ws, endpoint);
    }

    @Override
    public Command getAssignWebServiceIsRemoteCommand(EditingDomain ed,
            Activity act, boolean isRemote) {
        WebServiceOperation wso = getWebServiceOperation(act);
        Service ws = null;
        if (wso != null) {
            ws = wso.getService();
        }

        return getAssignWebServiceIsRemoteCommand(ed, ws, isRemote);
    }

    @Override
    public Command getClearWebServiceCommand(EditingDomain ed, Activity act) {
        CompoundCommand command =
                new CompoundCommand(
                        Messages.TaskServiceMessageAdapter_ClearWebServiceCommand);
        command.append(RemoveCommand.create(ed, getWebServiceOperation(act)));

        PortTypeOperation pto = getPortTypeOperation(act);
        if (pto != null) {
            command.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                    (OtherElementsContainer) pto.eContainer(),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_PortTypeOperation(),
                    pto));
        }

        Message in = getMessageIn(act);
        Message out = getMessageOut(act);
        if (in != null) {
            EList<?> mappingsIn = in.getDataMappings();
            command.append(RemoveCommand.create(ed,
                    in,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    mappingsIn));
        }
        if (out != null) {
            EList<?> mappingsOut = out.getDataMappings();
            command.append(RemoveCommand.create(ed,
                    out,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    mappingsOut));
        }
        command.append(RemoveCommand.create(ed,
                act,
                Xpdl2Package.eINSTANCE.getActivity_Assignments(),
                act.getAssignments()));

        Command tempCommand =
                getSetGrammarCommand(act, "XPath", DirectionType.IN_LITERAL); //$NON-NLS-1$
        if (tempCommand.canExecute()) {
            command.append(tempCommand);
        }

        tempCommand =
                getSetGrammarCommand(act, "XPath", DirectionType.OUT_LITERAL); //$NON-NLS-1$
        if (tempCommand.canExecute()) {
            command.append(tempCommand);
        }
        return command;
    }

    /**
     * 
     * Adds the additional command to passec compound command.
     * 
     * @param objectsToCreate
     * 
     * @param editingDomain
     *            The editing domain.
     * @param process
     *            The process.
     * @param activity
     *            The activity.
     * @param key
     *            The service key.
     * @param cpdCmd
     *            The compound command to add to.
     */
    protected void autoAssignEndpointParticipant(List<Object> objectsToCreate,
            Process process, Activity activity, WsdlServiceKey key) {

        Participant participant =
                WSSystemParticSharedResUtil
                        .autoAssignEndpointParticipantCommand(process,
                                activity,
                                key,
                                objectsToCreate);
        if (null != participant) {
            /*
             * set the sys Partipcant in the activity performers and end point
             * name
             */
            List<EObject> perfs = new ArrayList<EObject>();
            perfs.add(participant);
            Performers performers = Xpdl2Factory.eINSTANCE.createPerformers();
            Performer newPerf = Xpdl2Factory.eINSTANCE.createPerformer();
            newPerf.setValue(participant.getId());
            performers.getPerformers().add(newPerf);
            activity.setPerformers(performers);
            WebServiceOperation webServiceOperation =
                    getWebServiceOperation(activity);
            if (webServiceOperation != null) {
                webServiceOperation
                        .getOtherAttributes()
                        .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                                participant.getId());

                /*
                 * Ensure that when end point participant is set, the IsRemote
                 * flag is also set.
                 */
                if (webServiceOperation.getService() != null) {
                    Xpdl2ModelUtil.setOtherAttribute(webServiceOperation
                            .getService(), XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_IsRemote(), true);
                }

            }
        }

        return;
    }

    /**
     * 
     * Adds the additional command to passec compound command.
     * 
     * @param editingDomain
     *            The editing domain.
     * @param process
     *            The process.
     * @param activity
     *            The activity.
     * @param key
     *            The service key.
     * @param cpdCmd
     *            The compound command to add to.
     */
    public void autoAssignEndpointParticipantCommand(
            EditingDomain editingDomain, Process process, Activity activity,
            WsdlServiceKey key, CompoundCommand cpdCmd) {

        Participant participant =
                WSSystemParticSharedResUtil
                        .autoAssignEndpointParticipantCommand(editingDomain,
                                process,
                                activity,
                                key,
                                cpdCmd);
        if (participant != null) {
            /*
             * set the sys Partipcant in the activity performers and end point
             * name
             */
            List<EObject> perfs = new ArrayList<EObject>();
            perfs.add(participant);
            Command perfCommand =
                    TaskObjectUtil.getSetPerformersCommand(editingDomain,
                            activity,
                            perfs.toArray(new EObject[0]));
            if (perfCommand != null) {
                cpdCmd.append(perfCommand);
            }

            cpdCmd.append(new SetWebServiceOperationAliasCommand(editingDomain,
                    activity, participant.getId()));
        }

        return;
    }

    protected class SetWebServiceOperationAliasCommand extends
            AbstractLateExecuteCommand {
        /** The activity. */
        private Activity activity = null;

        /** The participant id. */
        private String participantId = null;

        /**
         * Constructor.
         * 
         * @param ed
         *            The editing domain.
         * @param activity
         *            The activity.
         * @param participantId
         *            The participant id.
         */
        public SetWebServiceOperationAliasCommand(EditingDomain ed,
                Activity activity, String participantId) {
            super(ed, ""); //$NON-NLS-1$
            this.activity = activity;
            this.participantId = participantId;
        }

        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {
            WebServiceOperation webServiceOperation =
                    getWebServiceOperation(activity);
            if (webServiceOperation != null) {
                CompoundCommand cmd = new CompoundCommand();

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                webServiceOperation,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Alias(),
                                participantId));

                /*
                 * Ensure that when end point participant is set, the IsRemote
                 * flag is also set.
                 */
                if (webServiceOperation.getService() != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    webServiceOperation.getService(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_IsRemote(),
                                    true));
                }
                return cmd;
            }

            return null;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.script.ActivityMessageProvider#
     * isActualWebServiceActivity()
     */
    @Override
    public boolean isActualWebServiceActivity() {
        return true;
    }

    /**
     * Checks if there is change in Port Type and Port Type operation.
     * 
     * @param act
     * @param key
     */
    // Changes for XPD-3451, persist in/out mapping when there is no port
    // type and operation change
    protected boolean noChangeInPortTypeOperation(Activity act,
            WsdlServiceKey key) {
        PortTypeOperation portTypeOpr = getPortTypeOperation(act);
        if (portTypeOpr != null) {
            // get OLD port type and operation details
            String oldPortTypeName =
                    portTypeOpr.getPortTypeName() != null ? portTypeOpr
                            .getPortTypeName() : ""; //$NON-NLS-1$
            String oldPortTypeOperation =
                    portTypeOpr.getOperationName() != null ? portTypeOpr
                            .getOperationName() : ""; //$NON-NLS-1$
            // get NEW port type and operation details
            String newPortTypeName =
                    key.getPortType() != null ? key.getPortType() : ""; //$NON-NLS-1$
            String newPortTypeOperation =
                    key.getPortTypeOperation() != null ? key
                            .getPortTypeOperation() : ""; //$NON-NLS-1$
            // If the ports Type and PortType operation are same
            return (oldPortTypeName.equals(newPortTypeName) && oldPortTypeOperation
                    .equals(newPortTypeOperation));
        }
        return false;
    }
}
