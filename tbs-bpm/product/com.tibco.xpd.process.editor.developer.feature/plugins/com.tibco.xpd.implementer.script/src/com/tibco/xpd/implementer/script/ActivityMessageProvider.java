/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * A task or an event that can be implemented with a message should provide an
 * adapter to this interface. This provides a standard access mechanism to
 * message implementation, parameter mapping and web service operations. The
 * input and output message details should be returned by the appropriate
 * methods. ( e.g. A message result from an end event will have an output
 * message and a null input message, a message trigger for a start event will
 * have an input message and null output message.) Note that this adapter does
 * not extend the EMF adapter interface because it it intended to be used in XPD
 * property sections, which handle their own notifications.
 * 
 * This is extension of {@link BaseActivityMessageProvider} intended to handle
 * activities used for web service interactions.
 */
public interface ActivityMessageProvider extends BaseActivityMessageProvider {

    /**
     * Get Web Service details.
     * 
     * @param act
     *            The activity to adapt.
     * @return Web Service from the activity (may be null).
     */
    WebServiceOperation getWebServiceOperation(Activity act);

    /**
     * Gets the PortTypeOperation from the other elements container
     * 
     * @param act
     * @return PortType from the activity (may be null).
     */
    PortTypeOperation getPortTypeOperation(Activity act);

    /**
     * Command to set web service details on the activity.
     * 
     * @param ed
     *            Editing domain.
     * @param act
     *            Activity to amend.
     * @param endpoint
     *            The new endpoitn value.
     * @param key
     *            Key to the details in the Wsdl cache.
     * @return A command to set the value.
     */
    Command getAssignWebServiceCommand(EditingDomain ed, Process process,
            Activity act, String endpoint, boolean isRemote, WsdlServiceKey key);

    /**
     * Command to set web service details on the activity.
     * 
     * @param objectsToCreate
     * 
     * @param act
     *            Activity to amend.
     * @param endpoint
     *            The new endpoitn value.
     * @param key
     *            Key to the details in the Wsdl cache.
     * @return A command to set the value.
     */
    void assignWebService(List<Object> objectsToCreate, Process process,
            Activity act, String endpoint, boolean isRemote, WsdlServiceKey key);

    /**
     * Command to clear web service details on the activity.
     * 
     * @param ed
     *            Editing domain.
     * @param act
     *            Activity to amend.
     * @return A command to set the value.
     */
    Command getClearWebServiceCommand(EditingDomain ed, Activity act);

    /**
     * Command to set web service details on the activity.
     * 
     * @param ed
     *            Editing domain.
     * @param act
     *            Activity to amend.
     * @param endpoint
     *            The new endpoitn value.
     * @return A command to set the value.
     */
    Command getAssignWebServiceEndpointCommand(EditingDomain ed, Activity act,
            String endpoint);

    /**
     * Command to set web service details on the activity.
     * 
     * @param ed
     *            Editing domain.
     * @param act
     *            Activity to amend.
     * @param endpoint
     *            The new endpoitn value.
     * @return A command to set the value.
     */
    Command getAssignWebServiceIsRemoteCommand(EditingDomain ed, Activity act,
            boolean isRemote);

    /**
     * Most ActivityMessageProvider's are for activities that REALLY are web
     * service activities.
     * <p>
     * Other message providers are for activities that are only INDIRECTLY
     * ActivityMessageProviders that don't invoke/receive/reply operations but
     * are related enough to need an activity message provider in order to do
     * some form of mapping to / from parts of a web-service (such ascatch fault
     * message eror event).
     * 
     * @return true if the activity is really directly a web service activity
     *         rather than indirectly.
     */
    boolean isActualWebServiceActivity();

}
