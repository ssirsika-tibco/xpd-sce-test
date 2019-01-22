package com.tibco.xpd.bw.eai.pages;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.bw.eai.BWMessages;
import com.tibco.xpd.bw.eai.JmsConstants;
import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.registry.WsdlFileUtil;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * @author nwilson
 */
public class WsdlSelectionPage extends WizardPage {

    private static final int DEFAULT_PORT = 7222;

    private static final String DEFAULT_JNDI_NAME = "QueueConnectionFactory"; //$NON-NLS-1$

    /** Text control for the host name. */
    private Text host;

    /** Text control for the port number. */
    private Text port;

    /** Text control for the JNDI name. */
    private Text jndi;

    /** Text control for the target name. */
    private Text name;
    
    /** Text control for the user name. */
    private Text username;
    
    /** Text control for the password. */
    private Text password;

    /** Listener for text modification events. */
    private final ModifyListener modify;

    /**
     * Constructor.
     */
    public WsdlSelectionPage() {
        super(BWMessages.SelectionPage_Title);
        setTitle(BWMessages.SelectionPage_Title);
        setDescription(BWMessages.SelectionPage_Description);
        modify = new ModifyListener() {
            public void modifyText(ModifyEvent e) {                    
                updatePageComplete();
            }
        };
    }

    /**
     * @param parent
     *            The parent to add the controls to.
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(
     *      org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        control.setLayout(layout);

        Label hostLabel = new Label(control, SWT.NONE);
        hostLabel.setText(BWMessages.HostLabel);
        host = new Text(control, SWT.BORDER | SWT.FLAT);
        host.setEnabled(false);
        host.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label portLabel = new Label(control, SWT.NONE);
        portLabel.setText(BWMessages.PortLabel);
        port = new Text(control, SWT.BORDER | SWT.FLAT);
        port.setText("" + DEFAULT_PORT); //$NON-NLS-1$
        port.setEnabled(false);
        port.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label jndiLabel = new Label(control, SWT.NONE);
        jndiLabel.setText(BWMessages.JndiLabel);
        jndi = new Text(control, SWT.BORDER | SWT.FLAT);
        jndi.setText(DEFAULT_JNDI_NAME); //$NON-NLS-1$
        jndi.setEnabled(false);
        jndi.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label nameLabel = new Label(control, SWT.NONE);
        nameLabel.setText(BWMessages.NameLabel);
        name = new Text(control, SWT.BORDER | SWT.FLAT);
        name.setEnabled(false);
        name.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        Label usernameLabel = new Label(control, SWT.NONE);
        usernameLabel.setText(BWMessages.UsernameLabel);
        username = new Text(control, SWT.BORDER | SWT.FLAT);
        username.setEnabled(false);
        username.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        Label passwordLabel = new Label(control, SWT.NONE);
        passwordLabel.setText(BWMessages.PasswordLabel);
        password = new Text(control, SWT.BORDER | SWT.FLAT | SWT.PASSWORD);
        password.setEnabled(false);
        password.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        host.addModifyListener(modify);
        port.addModifyListener(modify);
        name.addModifyListener(modify);
        username.addModifyListener(modify);
        password.addModifyListener(modify);

        setControl(control);
        updatePageComplete();
    }
    
    /**
     * Refreshes the fields with the appropriate values taken from the selected Jms server
     * @param server
     */
    public void refreshServerDetails(Server server){
        ServerConfig serverConfig = server.getServerConfig();
        List<ConfigParameter> configParams = serverConfig.getConfigParameters();        
        for (ConfigParameter configParam:configParams){            
            if (configParam.getKey().equals(JmsConstants.HOST)){         
                host.setText(configParam.getValue());
            }else if (configParam.getKey().equals(JmsConstants.PORT)){
                port.setText(configParam.getValue());
            }else if (configParam.getKey().equals(JmsConstants.TARGET_QUEUE_NAME)){
                name.setText(configParam.getValue());
            }else if (configParam.getKey().equals(JmsConstants.USERNAME)){
                username.setText(configParam.getValue());
            }else if (configParam.getKey().equals(JmsConstants.JNDI_NAME)){
                jndi.setText(configParam.getValue());
            }else if (configParam.getKey().equals(JmsConstants.PASSWORD)){
                String tempPass = configParam.getValue();
                try {
                    tempPass = EncryptionUtil.decrypt(tempPass);
                } catch (IllegalArgumentException e) {                    
                }
                if (tempPass == null) {
                    tempPass = ""; //$NON-NLS-1$
                }
                password.setText(tempPass);
            }
        }
        updatePageComplete();
    }

    /**
     * @return A guess at the WSDL file name, or an empty string.
     */
    public String guessFileName() {
        String guess = name.getText();
        if (!WsdlFileUtil.hasWsdlExtension(guess)) {
            guess += ".wsdl"; //$NON-NLS-1$
        }
        return guess;
    }

    public String getHost() {
        return host.getText();
    }

    public int getPort() {
        int portnumber = DEFAULT_PORT;
        try {
            portnumber = Integer.parseInt(port.getText());
        } catch (NumberFormatException e) {
            // Ignore
        }
        return portnumber;
    }

    public String getTarget() {
        return name.getText();
    }
    
    public String getUsername() {
        return username.getText();
    }
    
    public String getPassword() {
        return password.getText();
    }

    /**
     * Sets the page complete status based on user input.
     */
    private void updatePageComplete() {
        boolean complete = false;
        if (host.getText().length() > 0) {
            if (name.getText().length() > 0) {
                try {
                    Integer.parseInt(port.getText());
                    complete = true;
                } catch (NumberFormatException e) {
                    setErrorMessage(BWMessages.WsdlLiveLinkPage_invalidPort);
                }
            }
        }
        setPageComplete(complete);
    }

    public String getJndiName() {
        return jndi.getText();
    }
}
