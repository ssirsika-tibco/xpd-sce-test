package com.tibco.xpd.bw.eai;

import org.eclipse.osgi.util.NLS;

public class BWMessages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.bw.eai.messages"; //$NON-NLS-1$

    private BWMessages() {
    }

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, BWMessages.class);
    }

    /** The dialog title identifier. */
    public static String WsdlImport_Title;

    /** The dialog title identifier. */
    public static String SelectionPage_Title;

    /** The dialog message identifier. */
    public static String SelectionPage_Description;

    /** The host input field label. */
    public static String HostLabel;

    /** The host input field label. */
    public static String PortLabel;

    /** The host input field label. */
    public static String NameLabel;
    
    /** The host input field label. */
    public static String JndiLabel;
    
    /** The username input field label. */
    public static String UsernameLabel;
    
    /** The password input field label. */
    public static String PasswordLabel;

    public static String WsdlLiveLink53ImportWizard_CreateFileError;

    public static String WsdlLiveLink53ImportWizard_ExceptionDetails_longdesc;

    public static String WsdlLiveLink53ImportWizard_GetWsdlError;

    public static String WsdlLiveLink53ImportWizard_ImportError_title;

    public static String WsdlLiveLink53ImportWizard_ImportingInProgress_message;

    public static String WsdlLiveLink53ImportWizard_ProblemDuringImport_message;
    
    /** Invalid port error message. */
    public static String WsdlLiveLinkPage_invalidPort;
    
    public static String JMSServerSelectionPage_JMSServerSelectionTitle;
    
    public static String JMSServerSelectionPage_JMSServerSelectionMessage;
    
    public static String JMSServerSelectionPage_JmsServerColumnLabel;
    
}
