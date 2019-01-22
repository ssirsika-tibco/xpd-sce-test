/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.server.jms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import javax.management.remote.JMXConnector;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.bw.eai.BWLink;
import com.tibco.xpd.bw.eai.BWLinkException;
import com.tibco.xpd.bw.eai.BWMessages;
import com.tibco.xpd.bw.eai.JmsConstants;
import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerState;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.DeploymentException;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus;
import com.tibco.xpd.deploy.model.extension.WorkspaceModulesSupport;
import com.tibco.xpd.deploy.ui.components.PasswordDialog;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * The implementation of server connection for JMS server.
 * 
 * @author glewis
 * 
 */
public class JmsConnection implements Connection, WorkspaceModulesSupport {
    private static final int DEFAULT_PORT = 7222;

    private static final String PACKAGE_CHARACTER_ENCODING = "UTF-8"; //$NON-NLS-1$

    private final ServerConfig config;

    private final String encoding;

    private final Server server;

    private JMXConnector jmxConnector;

    private String username;

    private String password;

    private boolean connectedFlag;

    private final Logger log;

    /**
     * The constructor.
     * 
     * @param server
     *            the server for with connection is created.
     */
    public JmsConnection(Server server) {
        this.server = server;
        this.config = server.getServerConfig();
        encoding = PACKAGE_CHARACTER_ENCODING;
        log = Activator.getLogger();
        log
                .debug(String
                        .format("Connection for server: %s was created.", server.getName())); //$NON-NLS-1$
    }

    /**
     * Gets integer port from a string port
     * 
     * @param port
     * @return
     */
    public int getPort(String port) {
        int portnumber = DEFAULT_PORT;
        try {
            portnumber = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            // Ignore
        }
        return portnumber;
    }

    @SuppressWarnings("unchecked")
    public void connect() throws ConnectionException {
        String host =
                JmsConstants.getConfigParamValue(config, JmsConstants.HOST);
        String port =
                JmsConstants.getConfigParamValue(config, JmsConstants.PORT);
        String targetQueueName =
                JmsConstants.getConfigParamValue(config,
                        JmsConstants.TARGET_QUEUE_NAME);
        String jndi =
                JmsConstants
                        .getConfigParamValue(config, JmsConstants.JNDI_NAME);
        username =
                JmsConstants.getConfigParamValue(config, JmsConstants.USERNAME);
        password =
                JmsConstants.getConfigParamValue(config, JmsConstants.PASSWORD);

        if (username != null && username.length() != 0) {
            if (password == null) {
                Display.getDefault().syncExec(new Runnable() {

                    public void run() {
                        Shell shell = Display.getDefault().getActiveShell();
                        PasswordDialog dialog = new PasswordDialog(shell);
                        dialog.setBlockOnOpen(true);
                        if (dialog.open() == PasswordDialog.OK) {
                            password = dialog.getPassword();
                        }
                    }

                });
                if (password == null) {
                    return;
                }
            } else {
                try {
                    password = EncryptionUtil.decrypt(password);
                } catch (IllegalArgumentException e) {
                    throw new ConnectionException(
                            Messages.JMSConnection_ConnectionFailedOutOfDatePasswordMessage,
                            e);
                }
            }
        } else {
            password = ""; //$NON-NLS-1$
        }

        String wsdl;
        try {
            wsdl =
                    BWLink.getWsdl(host,
                            getPort(port),
                            jndi,
                            targetQueueName,
                            username,
                            password);
        } catch (BWLinkException e) {
            disconnect();
            throw new ConnectionException(
                    BWMessages.WsdlLiveLink53ImportWizard_GetWsdlError);
        }
        if (wsdl == null) {
            disconnect();
            throw new ConnectionException(
                    BWMessages.WsdlLiveLink53ImportWizard_GetWsdlError);
        }
        connectedFlag = true;
        server.getServerElements().clear();
        server.setServerState(ServerState.CONNECTED_LITERAL);
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.Connection#disconnect()
     * 
     * @throws ConnectionException
     */
    public void disconnect() throws ConnectionException {
        connectedFlag = false;
        server.getServerElements().clear();
        server.setServerState(ServerState.DISCONNECTED_LITERAL);
        log.debug("Disconnected."); //$NON-NLS-1$        
    }

    /**
     * @see com.tibco.xpd.deploy.model.extension.Connection#isConnected()
     * 
     * @return
     * @throws ConnectionException
     */
    public boolean isConnected() throws ConnectionException {
        if (connectedFlag) {
            refreshServerContent();
        }
        return connectedFlag;
    }

    /**
     * @return true if server is connected.
     */
    private boolean isJmsConnected() {
        if (jmxConnector != null) {
            try {
                log.debug("Checking connection ..."); //$NON-NLS-1$
                jmxConnector.getMBeanServerConnection();
                log.debug("... connection OK."); //$NON-NLS-1$
            } catch (IOException e) {
                log.debug(e, e.getMessage());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private String readStringFromURL(URL filePath, String charSetEncoding)
            throws IOException {
        StringBuffer sb = new StringBuffer();

        InputStreamReader inStream;

        if (charSetEncoding != null && charSetEncoding.length() > 0) {
            inStream =
                    new InputStreamReader(filePath.openStream(), Charset
                            .forName(charSetEncoding));

        } else {
            inStream = new InputStreamReader(filePath.openStream());
        }

        BufferedReader r = new BufferedReader(inStream);
        String s = ""; //$NON-NLS-1$
        while ((s = r.readLine()) != null) {
            sb.append(s).append("\r\n"); //$NON-NLS-1$
        }
        r.close();
        return sb.toString();
    }

    public Object performServerElementOperation(ServerElement serverElement,
            Operation operation) throws DeploymentException {
        return null;
    }

    @SuppressWarnings("unchecked")
    public Object getAdapter(Class adapter) {
        return null;
    }

    private WorkspaceModule getWorkspaceModule(String path) {
        for (Iterator<?> iter = server.getWorkspaceModules().iterator(); iter
                .hasNext();) {
            WorkspaceModule module = (WorkspaceModule) iter.next();
            if (path.equals(module.getWorkspaceSrcPath())) {
                return module;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void cleanWorkspaceModules() {
        for (WorkspaceModule module : (List<WorkspaceModule>) server
                .getWorkspaceModules()) {
            IResource source =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .findMember(new Path(module.getWorkspaceSrcPath()));
            if (source == null) {
                server.getWorkspaceModules().remove(module);
            }
        }
    }

    public Server getServer() {
        return server;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.model.extension.Connection#validateModule(java.lang
     * .String)
     */
    public boolean validateModule(String url) throws ConnectionException {
        return false;
    }

    public DeploymentStatus deployModule(String url) throws DeploymentException {
        return null;
    }

    public void refreshServerContent() throws ConnectionException {
    }

    public void cleanWorkspaceModules(IResourceDelta delta) {
    }

    public List<WorkspaceModule> getAffectedWorkspaceModules(
            IResourceDelta delta) {
        return null;
    }

    public URL getWorkspaceModuleDeploymentURL(WorkspaceModule module) {
        return null;
    }

}
