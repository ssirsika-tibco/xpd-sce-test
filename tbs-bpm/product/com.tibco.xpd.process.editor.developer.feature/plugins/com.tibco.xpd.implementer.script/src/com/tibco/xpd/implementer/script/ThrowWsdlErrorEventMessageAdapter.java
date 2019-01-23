/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;

/**
 * Activity message provider for a Throw Error End Event that is set to throw
 * error for a fault message - the fault message MAY be auto-generated or MAY be
 * selected from a user-defined WSDL operation fault message.
 * 
 * @author aallway
 * @since 3.3
 */
public class ThrowWsdlErrorEventMessageAdapter implements
        ActivityMessageProvider {

    @Override
    public void assignWebService(List<Object> objectsToCreate, Process process,
            Activity act, String endpoint, boolean isRemote, WsdlServiceKey key) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".assignWebService():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Throw Fault Message Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getAssignWebServiceCommand(EditingDomain ed,
            Process process, Activity act, String endpoint, boolean isRemote,
            WsdlServiceKey key) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getAssignWebServiceCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Throw Fault Message Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getAssignWebServiceEndpointCommand(EditingDomain ed,
            Activity act, String endpoint) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getAssignWebServiceEndpointCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Throw Fault Message Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getAssignWebServiceIsRemoteCommand(EditingDomain ed,
            Activity act, boolean isRemote) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getAssignWebServiceIsRemoteCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Throw Fault Message Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getClearWebServiceCommand(EditingDomain ed, Activity act) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getClearWebServiceCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Throw Fault Message Error Event"); //$NON-NLS-1$
    }

    @Override
    public Set<ProcessRelevantData> getDataReferences(Activity activity,
            Set<ProcessRelevantData> dataSet) {
        // Only local data refs would be handled by standard ref checker
        // enyway.,
        return Collections.emptySet();
    }

    @Override
    public Command getDeleteDataReferencesCommand(EditingDomain editingDomain,
            Activity activity, ProcessRelevantData data) {
        // Only local data refs would be handled by standard ref checker
        // anyway.,
        return null;
    }

    @Override
    public ImplementationType getImplementation(Activity act) {
        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(act)) {
            return ImplementationType.WEB_SERVICE_LITERAL;
        }
        return null;
    }

    @Override
    public EObject getMessageContainer(Activity act) {
        return EventObjectUtil.getResultError(act);
    }

    @Override
    public Message getMessageIn(Activity act) {
        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(act)) {
            return ThrowErrorEventUtil.getThrowErrorFaultMessage(act);
        }

        return null;
    }

    @Override
    public Message getMessageOut(Activity act) {
        return null;
    }

    @Override
    public PortTypeOperation getPortTypeOperation(Activity act) {
        //
        // Delegate the 'operation' to that of the activity whose wsdl operation
        // fault we are throwing.
        //
        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(act)) {
            Activity requestActivity =
                    ThrowErrorEventUtil.getRequestActivity(act);
            if (requestActivity != null) {
                ActivityMessageProvider messageProvider =
                        ActivityMessageProviderFactory.INSTANCE
                                .getMessageProvider(requestActivity);
                if (messageProvider != null) {
                    return messageProvider
                            .getPortTypeOperation(requestActivity);
                }
            }
        }

        return null;
    }

    @Override
    public WebServiceOperation getWebServiceOperation(Activity act) {
        //
        // Delegate the 'operation' to that of the activity whose wsdl operation
        // fault we are throwing.
        //
        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(act)) {
            Activity requestActivity =
                    ThrowErrorEventUtil.getRequestActivity(act);
            if (requestActivity != null) {
                ActivityMessageProvider messageProvider =
                        ActivityMessageProviderFactory.INSTANCE
                                .getMessageProvider(requestActivity);
                if (messageProvider != null) {
                    return messageProvider
                            .getWebServiceOperation(requestActivity);
                }
            }
        }

        return null;
    }

    @Override
    public boolean hasMappingIn(Activity act) {
        return true;
    }

    @Override
    public boolean hasMappingOut(Activity act) {
        return false;
    }

    @Override
    public Command getSetImplementationCommand(EditingDomain ed, Activity act,
            ImplementationType newImplType) {
        throw new RuntimeException(
                this.getClass().getName()
                        + ".getSetImplementationCommand():" //$NON-NLS-1$
                        + "It is not appropriate to use this method for Throw Fault Message Error Event"); //$NON-NLS-1$
    }

    @Override
    public Command getSwapDataIdReferencesCommand(EditingDomain editingDomain,
            Activity activity, Map<String, String> idMap) {
        // Only local data refs would be handled by standard ref checker
        // anyway.,
        return null;
    }

    @Override
    public Command getSwapDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        // Only local data refs would be handled by standard ref checker
        // anyway.,
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.implementer.script.ActivityMessageProvider#
     * isActualWebServiceActivity()
     */
    @Override
    public boolean isActualWebServiceActivity() {
        return false;
    }

}
