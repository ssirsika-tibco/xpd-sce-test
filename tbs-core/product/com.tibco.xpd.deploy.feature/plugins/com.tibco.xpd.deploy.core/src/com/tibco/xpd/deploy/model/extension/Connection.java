/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import org.eclipse.core.runtime.IAdaptable;

import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerElement;

/**
 * Defines contract for the management of a connection to the server.
 * <p>
 * <i>Created: 27 Sep 2006</i>
 * 
 * @author Jan Arciuchiewicz
 */
public interface Connection extends IAdaptable {

    /**
     * Connects to server.
     */
    void connect() throws ConnectionException;

    /**
     * Disconnects from server.
     */
    void disconnect() throws ConnectionException;

    /**
     * Checks if server is connected.
     * 
     * @return true if server is connected.
     */
    boolean isConnected() throws ConnectionException;

    /**
     * This method is responsible for updating server content using connection.
     * It should update current state of server and all its ServerElement's.
     * 
     * @see ServerElement
     */
    void refreshServerContent() throws ConnectionException;

    /**
     * Performs module validation on server. Validation is performed just before
     * deployment. If this method returns false then the
     * {@link #deployModule(String)} method will not be invoked. If there is no
     * need for server module validation then method should simply return
     * <code>true</code>.
     * 
     * @param url
     *                The URL of module to validate.
     * @return <code>true</code> if the deployment should proceed ({@link #deployModule(String)}
     *         method should be invoked). If method returns <code>false</code>
     *         then deployModule will be skipped.
     * @throws ConnectionException
     *                 If validation on the server thrown an exception.
     */
    boolean validateModule(String url) throws ConnectionException;

    /**
     * Deploys module on the server. Module is represented by string containing
     * URL to the module. The form url should be supported by the server.
     * 
     * @param url
     *                string containing URL of the module to be deployed.
     * @return response from server containing description of deployment
     *         operation.
     */
    DeploymentStatus deployModule(String url) throws DeploymentException;

    /**
     * Performs operation on ServerElement.
     * 
     * @param serverElement
     *                server element on which the operation will be performed.
     * @param operation
     *                is the operation which will be invoked on the server
     *                element. The operation should be one of the operations
     *                associated with ServerElementType.
     */
    Object performServerElementOperation(ServerElement serverElement,
            Operation operation) throws DeploymentException;

    /**
     * Returns reference to the server.
     * 
     * @return associated deployment server.
     */
    Server getServer();

}
