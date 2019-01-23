/*
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xsd.ui.internal;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.xsd.ui.internal.messages"; //$NON-NLS-1$
    public static String Inst2XsdWrapper_XmlParsingFailedMessage;
    public static String NewXsdWizard_CreatingFileMessage;
    public static String NewXsdWizard_ErrorTitle;
    public static String NewXsdWizard_FolderDoesNotExistMessage;
    public static String NewXsdWizard_OpeningFileShortDesc;
    public static String NewXsdWizard_XmlSchemaTitle;
    public static String NewXsdWizardPage_BrowseButton;
    public static String NewXsdWizardPage_FileRequiredMessage;
    public static String NewXsdWizardPage_FolderLabel;
    public static String NewXsdWizardPage_FolderMustExistMessage;
    public static String NewXsdWizardPage_FolderRequiredMessage;
    public static String NewXsdWizardPage_NewSchemaFileName;
    public static String NewXsdWizardPage_ProjectMustBeWritableMessage;
    public static String NewXsdWizardPage_SelectFolderMessage;
    public static String NewXsdWizardPage_SelectXmlDocumentFileMessage;
    public static String NewXsdWizardPage_WrongFileExtensionMessage;
    public static String NewXsdWizardPage_XmlDocumentFileLabel;
    public static String NewXsdWizardPage_XmlSchemaFromXmlDocShortDesc;
    public static String NewXsdWizardPage_XmlSchemaFromXmlDocTitle;
    public static String NewXsdWizardPage_XsdFileLabel;
    public static String DescriptorXMLOverJMSWizard_Title;   
    public static String DescriptorXMLOverJMSWizard_WizardFinish_label;
    public static String DescriptorXMLOverJMSWizard_WizardFinish_message;
    public static String DescriptorXMLOverJMSWizard_WizardFinish_error;
    public static String DocumentSelectionPage_Title;    
    public static String DocumentSelectionPage_Description;
    public static String DocumentSelectionPage_failedToLoadIncomingSchema_error_message;
    public static String DocumentSelectionPage_failedToLoadOutgoingSchema_error_message;
    public static String DocumentSelectionPage_failedToLoadXSD_error_shortdesc;    
    public static String DocumentSelectionPage_XMLFilePicker_title;    
    public static String DocumentSelectionPage_XMLFileSelection_shortdesc;    
    public static String DocumentSelectionPage_XMLFileSelectionError_message;    
    public static String DocumentSelectionPage_XSDFilePicker_title;    
    public static String DocumentSelectionPage_XSDFileSelection_shortdesc;    
    public static String DocumentSelectionPage_XSDFileSelectionError_message;
    public static String DocumentSelectionPage_XSDResourceLoad_diagnosticError_message;    
    public static String DocumentSelectionPage_Incoming_label;
    public static String DocumentSelectionPage_incomingDocDoesNotExist_error_message;
    public static String DocumentSelectionPage_loadingFile_error_message;
    public static String DocumentSelectionPage_loadingFile_shortdesc;
    public static String DocumentSelectionPage_Outgoing_label;
    public static String DocumentSelectionPage_outgoingDocDoesNotExist_error_message;
    public static String DocumentSelectionPage_selectOneDocument_error_shortdesc2;
    public static String DocumentMappingPage_Title;    
    public static String DocumentMappingPage_Description;   
    public static String DocumentMappingPage_Column_Name_label;
    public static String DocumentMappingPage_Column_Type_label;
    public static String DocumentMappingPage_Incoming_label;
    public static String DocumentMappingPage_Outgoing_label;
    public static String WsdlConfigurationPage_InvalidServerNamespaceMessage;
    public static String WsdlConfigurationPage_InvalidTypeNamespaceMessage;
    public static String WsdlConfigurationPage_NamespaceIdenticalMessage;
    public static String WsdlConfigurationPage_Title;    
    public static String WsdlConfigurationPage_Description;    
    public static String WsdlConfigurationPage_OperationLabel;
    public static String WsdlConfigurationPage_PortTypeLabel;
    public static String WsdlConfigurationPage_NameSpaceLabel;
    public static String WsdlConfigurationPage_ServiceNSLabel;
    public static String WsdlConfigurationPage_OutputFolderLabel;           
    public static String WsdlConfigurationPage_BrowseLabel;
    public static String WsdlConfigurationPage_destinationGroup_title;
    public static String WsdlConfigurationPage_detailsGroup_title;
    public static String WsdlConfigurationPage_OutputFolderPicker_title;
    public static String WsdlConfigurationPage_OutputFolderPicker_shortdesc;
    public static String WsdlConfigurationPage_OutputFolderPickerError_message;
    public static String WsdlConfigurationPage_selectServicesFolder_message;    
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
