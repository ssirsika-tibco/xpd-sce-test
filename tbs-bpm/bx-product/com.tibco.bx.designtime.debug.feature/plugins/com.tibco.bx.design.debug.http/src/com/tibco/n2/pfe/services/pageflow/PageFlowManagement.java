/**
 * PageFlowService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflow;

import com.tibco.bx.debug.operation.ILauncherEventHandler;


public interface PageFlowManagement extends java.rmi.Remote {

    /**
     * List deployed pageflows.
     */
    public com.tibco.n2.pfe.services.pageflowType.ListPageFlowsResponse listPageFlows(com.tibco.n2.pfe.services.pageflowType.ListingRequest parameters) throws java.rmi.RemoteException, com.tibco.n2.pfe.services.pageflowType.InternalServiceFaultException, com.tibco.n2.pfe.services.pageflowType.InvalidArgumentFault, com.tibco.n2.pfe.services.pageflowType.PageFlowExecutionFault;

    /**
     * Start an instance of a deployed pageflow.
     */
    public com.tibco.n2.pfe.services.pageflowType.PageResponse startPageFlow(com.tibco.n2.pfe.services.pageflowType.PFETemplate pageFlowDefinition, com.tibco.n2.pfe.services.pageflowType.DataPayload formalParams, com.tibco.n2.pfe.services.pageflowType.PayloadModeType responsePayloadMode, java.lang.Long cacheTimeout, ILauncherEventHandler handler) throws java.rmi.RemoteException, com.tibco.n2.pfe.services.pageflowType.InternalServiceFaultException, com.tibco.n2.pfe.services.pageflowType.InvalidArgumentFault, com.tibco.n2.pfe.services.pageflowType.PageFlowExecutionFault;
    
}
