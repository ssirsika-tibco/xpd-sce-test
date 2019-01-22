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
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * Helpful utilities for information about wsdl fault caught by catch error
 * event.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchWsdlErrorEventUtil extends
        com.tibco.xpd.processeditor.xpdl2.util.CatchWsdlErrorEventUtil {

    /**
     * @param catchErrorEvent
     * 
     * @return List WsdlPartPath's for the oepration fault message caught by the
     *         given catch error event.
     */
    public static List<WsdlPartPath> getCaughtWsdlFaultParams(
            Activity catchErrorEvent) {
        List<WsdlPartPath> wsdlFaultPartPaths = new ArrayList<WsdlPartPath>();

        Fault fault =
                CatchWsdlErrorEventUtil.getCaughtWsdlFault(catchErrorEvent);
        if (fault instanceof org.eclipse.wst.wsdl.Fault) {
            javax.wsdl.Message faultMsg = fault.getMessage();

            if (faultMsg != null) {
                List parts = faultMsg.getOrderedParts(null);

                if (parts != null) {
                    ActivityMessageProvider messageAdapter =
                            getMessageAdapter(catchErrorEvent);
                    if (messageAdapter != null) {
                        WebServiceOperation webSvcOp =
                                messageAdapter
                                        .getWebServiceOperation(catchErrorEvent);

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
     * @param catchErrorEvent
     * 
     * @return the Wsdl Fault that is caught by the given catch error event.
     */
    public static org.eclipse.wst.wsdl.Fault getCaughtWsdlFault(
            Activity catchErrorEvent) {
        org.eclipse.wst.wsdl.Fault wsdlFault = null;

        //
        // Get the error code (which will be the WSDL fault name)
        //
        ResultError resError = getCatchEventResultError(catchErrorEvent);
        if (resError != null) {
            String faultName = resError.getErrorCode();

            if (faultName != null && faultName.length() > 0) {
                Activity throwActivity =
                        getWsdlFaultThrowingActivity(catchErrorEvent);

                if (throwActivity != null) {
                    Operation wsdlOperation =
                            Xpdl2WsdlUtil.getOperation(throwActivity);

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

    /*
     * methods getWsdlFaultThrowingActivity(),
     * isWsdlFaultThrowingActivityType(), isCatchWsdlFaultErrorEvent() Moved to
     * com.tibco.xpd.processeditor.xpdl2.util.CatchWsdlErrorEventUtil
     */

    /*
     * XPD-6974: ResultError getCatchEventResultError(Activity catchErrorEvent)
     * moved to
     * com.tibco.xpd.processeditor.xpdl2.util.CatchWsdlErrorEventUtil.java
     */

    /**
     * 
     * @param catchErrorEvent
     * @return {@link ActivityMessageProvider} instance for the given catch
     *         error event
     */
    private static ActivityMessageProvider getMessageAdapter(
            Activity catchErrorEvent) {
        return ActivityMessageProviderFactory.INSTANCE
                .getMessageProvider(catchErrorEvent);
    }

}
