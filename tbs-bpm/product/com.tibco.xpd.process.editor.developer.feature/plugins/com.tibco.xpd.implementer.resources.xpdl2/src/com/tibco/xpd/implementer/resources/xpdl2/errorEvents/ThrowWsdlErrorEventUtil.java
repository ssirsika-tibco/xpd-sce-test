/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.wsdl.Fault;
import javax.wsdl.Operation;

import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.WsdlPartPath;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;

/**
 * Helpful utilities for information about wsdl fault thrown by throw fault
 * message end error event.
 * 
 * @author aallway
 * @since 3.3
 */
public class ThrowWsdlErrorEventUtil {

    /**
     * @param throwErrorEvent
     * 
     * @return List WsdlPartPath's for the oepration fault message thrown by the
     *         given throw error event.
     */
    public static List<WsdlPartPath> getWsdlFaultParams(Activity throwErrorEvent) {
        List<WsdlPartPath> wsdlFaultPartPaths = new ArrayList<WsdlPartPath>();

        Fault fault = ThrowWsdlErrorEventUtil.getWsdlFault(throwErrorEvent);
        if (fault instanceof org.eclipse.wst.wsdl.Fault) {
            javax.wsdl.Message faultMsg = fault.getMessage();

            if (faultMsg != null) {
                List parts = faultMsg.getOrderedParts(null);

                if (parts != null) {
                    ActivityMessageProvider messageAdapter =
                            ActivityMessageProviderFactory.INSTANCE
                                    .getMessageProvider(throwErrorEvent);

                    if (messageAdapter != null) {
                        WebServiceOperation webSvcOp =
                                messageAdapter
                                        .getWebServiceOperation(throwErrorEvent);

                        for (Object part : parts) {
                            if (part instanceof Part) {
                                WsdlPartPath path =
                                        new WsdlPartPath(
                                                webSvcOp,
                                                (org.eclipse.wst.wsdl.Fault) fault,
                                                (Part) part);
                                wsdlFaultPartPaths.add(path);
                            }
                        }
                    }
                }
            }
        }
        return wsdlFaultPartPaths;
    }

    /**
     * @param throwErrorEvent
     * 
     * @return the Wsdl Fault that is thrown by the given catch error event.
     */
    public static org.eclipse.wst.wsdl.Fault getWsdlFault(
            Activity throwErrorEvent) {
        org.eclipse.wst.wsdl.Fault wsdlFault = null;

        //
        // Get the error code (which will be the WSDL fault name)
        //
        Message faultMessage =
                ThrowErrorEventUtil.getThrowErrorFaultMessage(throwErrorEvent);

        if (faultMessage != null) {
            String faultName = faultMessage.getFaultName();

            if (faultName != null && faultName.length() > 0) {
                Activity requestActivity =
                        ThrowErrorEventUtil.getRequestActivity(throwErrorEvent);

                if (requestActivity != null) {
                    Operation wsdlOperation =
                            Xpdl2WsdlUtil.getOperation(requestActivity);

                    if (wsdlOperation != null
                            && wsdlOperation instanceof org.eclipse.wst.wsdl.Operation) {
                        Map faults = wsdlOperation.getFaults();

                        if (faults != null && faults.size() > 0) {
                            for (Iterator iterator =
                                    faults.entrySet().iterator(); iterator
                                    .hasNext();) {
                                Entry entry = (Entry) iterator.next();

                                if (entry.getValue() instanceof Fault
                                        && entry.getValue() instanceof org.eclipse.wst.wsdl.Fault) {
                                    org.eclipse.wst.wsdl.Fault fault =
                                            (org.eclipse.wst.wsdl.Fault) entry
                                                    .getValue();

                                    if (faultName.equals(fault.getName())) {
                                        wsdlFault = fault;
                                        break;
                                    }
                                }
                            } // next fault
                        }
                    }
                }
            }
        }

        return wsdlFault;
    }

}
