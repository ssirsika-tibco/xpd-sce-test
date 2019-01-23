/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.wsdl;

import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;

/**
 * Wsdl Service key that contains the information of a {@link Port} (binding)
 * {@link Operation} or a {@link PortType} <code>Operation</code>.
 * 
 */
public class WsdlServiceKey {

    /** The service name. */
    private String service;

    /** The port name. */
    private String port;

    /** The operation name. */
    private String operation;

    /** The selected port type name. */
    private String portType;

    /** The selected port type operation name. */
    private String portTypeOperation;

    /** The port transport URI */
    private String transportURI;

    /** Special folder relative path to the wsdl file */
    private String filePath;

    /**
     * This constructor has an overloaded one to include port type and port
     * operations and this should be called if you are using Web Service
     * interfaces only.
     * 
     * @param service
     *            The service name.
     * @param port
     *            The port name.
     * @param operation
     *            The operation name.
     * @param portType
     *            The port type name.
     * @param portTypeOperation
     *            The port type operation.
     * @param filePath
     *            Services special folder relative path to the file that
     *            contains this Operation.
     */
    public WsdlServiceKey(String service, String port, String operation,
            String portType, String portTypeOperation, String filePath) {
        this(portType, portTypeOperation, filePath);
        this.service = service;
        this.port = port;
        this.operation = operation;
    }

    /**
     * Key for a PortType operation.
     * 
     * @param portType
     * @param portTypeOperation
     *            * @param filePath Services special folder relative path to the
     *            file that contains this Operation.
     */
    public WsdlServiceKey(String portType, String portTypeOperation,
            String filePath) {
        this.portType = portType;
        this.portTypeOperation = portTypeOperation;
        this.filePath = filePath;
    }

    /**
     * @return The service key string.
     */
    public String getServiceKey() {
        if (service != null && port != null && operation != null) {
            return service + "." + port + "." + operation; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return portType + "." + portTypeOperation; //$NON-NLS-1$
    }

    /**
     * @return The operation name.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @return The port name.
     */
    public String getPort() {
        return port;
    }

    /**
     * @return The port operation name.
     */
    public String getPortTypeOperation() {
        return portTypeOperation;
    }

    /**
     * @return The port type name.
     */
    public String getPortType() {
        return portType;
    }

    /**
     * @return The service name.
     */
    public String getService() {
        return service;
    }

    /**
     * Get the Services special folder relative path of the file that contains
     * this Operation.
     * 
     * @return
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @return
     */
    public String getTransportURI() {
        return transportURI;
    }

    /**
     * @param transportURI
     */
    public void setTransportURI(String transportURI) {
        this.transportURI = transportURI;
    }

}
