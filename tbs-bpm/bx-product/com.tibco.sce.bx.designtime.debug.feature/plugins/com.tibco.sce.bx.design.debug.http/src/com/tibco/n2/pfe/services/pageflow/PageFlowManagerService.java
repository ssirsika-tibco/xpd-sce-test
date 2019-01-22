/**
 * PageFlowService_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflow;

public interface PageFlowManagerService extends javax.xml.rpc.Service {

	public java.lang.String getPageFlowPortAddress();

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort() throws javax.xml.rpc.ServiceException;

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;

	public java.lang.String getPageFlowPort12Address();

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort12() throws javax.xml.rpc.ServiceException;

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;

}
