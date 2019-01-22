/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db.impl.derby;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Properties;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.impl.drda.NetworkServerControlImpl;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.XpdConfigOption;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.db.IResourceDb;
import com.tibco.xpd.resources.internal.db.ResourceDbException;

/**
 * Implementation of the Derby database interface that uses the database as a
 * server. This will be used in the RCP application of Studio to allow multiple
 * clients to connect to the database.
 * 
 * @author njpatel
 * 
 * @deprecated NO LONGER USED AS STUDIO_ANALYSTS NOW USES DEDICATED WORKSPACES
 *             FOR EACH INSTANCE.
 * 
 */
@Deprecated
public class ResourceDbDerbyRCP extends ResourceDbDerby {

    /**
     * The port number to use to connect to the Derby database for the workspace
     */
    private static final QualifiedName DB_WORKSPACE_PORT = new QualifiedName(
            XpdResourcesPlugin.ID_PLUGIN, "db_port"); //$NON-NLS-1$

    private static final int DEFAULT_PORTNUMBER =
            NetworkServerControl.DEFAULT_PORTNUMBER;

    private int port;

    private boolean clearWorkspacePortNo;

    @Override
    protected void preStartup() throws CoreException {
        connectToServer();
    }

    @Override
    protected Properties loadDBProperties() throws ResourceDbException {
        InputStream dbPropInputStream = null;
        
        //select config properties for specific db mode.
        String configProperties = "Configuration_server.properties";  //$NON-NLS-1$
        
        // select in-memory mode for derby if memory option is set.
        if(XpdConfigOption.IN_MEMORY_INDEX_DB.isOn()){
        	configProperties = "Configuration_InMemory_server.properties";   //$NON-NLS-1$
        }
        dbPropInputStream =
                ResourceDbDerbyRCP.class
                        .getResourceAsStream(configProperties); //$NON-NLS-1$
        Properties prop = new Properties();
        try {
            prop.load(dbPropInputStream);

            String user = (String) prop.get("user_notrans"); //$NON-NLS-1$
            String password = (String) prop.get("password_notrans"); //$NON-NLS-1$
            String driverName = (String) prop.get("derby.driver_notrans"); //$NON-NLS-1$
            String tableName = (String) prop.get("db.table_notrans"); //$NON-NLS-1$
            String schema = (String) prop.get("db.schema_notrans"); //$NON-NLS-1$

            prop.clear();

            prop.put("user", user); //$NON-NLS-1$
            prop.put("password", password); //$NON-NLS-1$
            prop.put("derby.driver", driverName); //$NON-NLS-1$
            prop.put("db.table", tableName); //$NON-NLS-1$
            prop.put("db.schema", schema); //$NON-NLS-1$

        } catch (IOException ex) {
            XpdResourcesPlugin.getDefault().getLogger().error(ex);
            throw new ResourceDbException(ex);
        }
        return prop;
    }

    @Override
    public String getDatabaseUrl() {
        return String.format("jdbc:derby://localhost:%d/%s", //$NON-NLS-1$
                port,
                IResourceDb.RESOURCE_DB_NAME);
    }

    @Override
    protected boolean reconnect() throws Exception {
        /*
         * Connection dropped - this means that the instances of the application
         * that had initially started the Derby server has been terminated. So,
         * try to re-start the server and re-connect.
         */
        // Check if this port is still free
        port = getFreePort(port > 0 ? port : DEFAULT_PORTNUMBER);
        NetworkServerControlImpl server = getServerControl(port);
        server.start(null);

        // Persist the port number with the workspace
        updatePortNumber(ResourcesPlugin.getWorkspace().getRoot(), port);
        // Make sure the workspace port number is cleared when this instance of
        // application stops (as the network server will also be terminated)
        clearWorkspacePortNo = true;

        return true;
    }

    /**
     * @throws CoreException
     */
    private void connectToServer() throws CoreException {
        // If workspace has a port assigned then get that and try to use it
        final IWorkspaceRoot workspaceRoot =
                ResourcesPlugin.getWorkspace().getRoot();

        try {
            Integer portNo = getPortNumber(workspaceRoot);
            if (portNo != null) {
                NetworkServerControlImpl server = getServerControl(portNo);
                /*
                 * Expecting server to be running as workspace has got a port
                 * assigned already. If the server is not running then we need
                 * to start it and update the workspace port
                 */
                if (!server.isServerStarted()) {
                    // Clear the existing port number so a new one can be found
                    updatePortNumber(workspaceRoot, null);
                    connectToServer();
                }
            } else {
                // Find the next available port
                portNo = getFreePort(DEFAULT_PORTNUMBER);
                NetworkServerControlImpl server = getServerControl(portNo);
                // Start the server
                server.start(null);
                clearWorkspacePortNo = true;
                // Update the workspace with the new port number
                updatePortNumber(workspaceRoot, portNo);
            }

            this.port = portNo;
        } catch (Exception e) {
            workspaceRoot.setPersistentProperty(DB_WORKSPACE_PORT, null);
            throw new CoreException(new Status(IStatus.ERROR,
                    XpdResourcesPlugin.ID_PLUGIN,
                    "Failed to connect to database server.", e));
        }
    }

    @Override
    public void close() throws ResourceDbException {
        super.close();
        if (clearWorkspacePortNo) {
            try {
                updatePortNumber(ResourcesPlugin.getWorkspace().getRoot(), null);
            } catch (CoreException e) {
                // Do nothing
            }
        }
    }

    /**
     * Get the port number associated with the given workspace.
     * 
     * @param workspaceRoot
     * @return port number or <code>null</code> if no port number is assigned.
     * @throws CoreException
     */
    private Integer getPortNumber(IWorkspaceRoot workspaceRoot)
            throws CoreException {
        String value = workspaceRoot.getPersistentProperty(DB_WORKSPACE_PORT);
        return value != null ? Integer.parseInt(value) : null;
    }

    /**
     * Update the workspace with the assigned port number.
     * 
     * @param workspaceRoot
     * @param portNumber
     *            port number or <code>null</code> to clear the value.
     * @throws CoreException
     */
    private void updatePortNumber(IWorkspaceRoot workspaceRoot,
            Integer portNumber) throws CoreException {
        workspaceRoot.setPersistentProperty(DB_WORKSPACE_PORT,
                portNumber != null ? portNumber.toString() : null);

    }

    /**
     * Get the server control to start the database server (if required).
     * 
     * @param port
     *            port number to use
     * @return
     * @throws Exception
     */
    private NetworkServerControlImpl getServerControl(int port)
            throws Exception {
        return new NetworkServerControlImpl(InetAddress.getByName("localhost"),
                port);
    }

    /**
     * Find a free port starting with the port value given.
     * 
     * @param startingAt
     *            port number to start searching from.
     * @return free port number.
     */
    private int getFreePort(int startingAt) {
        int port = startingAt;

        if (!isPortAvailable(port)) {
            // Find the next available port
            boolean isFree = false;
            while (!isFree) {
                isFree = isPortAvailable(++port);
            }
        }
        return port;
    }

    /**
     * Check if the given port number is free (uses {@link ServerSocket} to
     * connect to).
     * 
     * @param port
     * @return
     */
    private boolean isPortAvailable(int port) {
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {
            // Port not available
            return false;
        }
        return true;
    }

}
