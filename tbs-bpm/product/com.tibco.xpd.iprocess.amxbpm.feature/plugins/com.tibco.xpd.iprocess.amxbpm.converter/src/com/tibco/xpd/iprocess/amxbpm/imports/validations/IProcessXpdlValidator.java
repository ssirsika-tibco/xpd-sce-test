/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.imports.validations;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.wsdl.WSDLElement;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * [Copied from IPM com.tibco.xpd.ipm plugin.] This validation is invoked from
 * the iProcess to AMX BPM Import XPDL wizard. The validation is responsible to
 * check whether the resources imported have their references in-tact, and would
 * not be broken on import.
 * 
 * Most importantly with EAI Web Services/BW tasks if the referenced WSDL is
 * missing, this may cause the XPDL to look broken.
 * 
 * Alternatively, if you're importing process templates, sub-processes, main
 * processes, it may be that that the
 * 
 * Checks to be implemented for Import and Convert iProcess to AMX BPM XPDL
 * includes 1) Check the given XPDL is a valid iProcess XPDL.- Error 2) A
 * referenced Subprocess is not imported as part of the same XPDL.- Warning
 * [EXISTS] 3) If all processes being imported already exists in the workspace,
 * raise error message
 * "There are no new processes to import (all processes in source package already exist in workspace)"
 * 
 * 
 * @author aprasad
 * 
 */

public class IProcessXpdlValidator {

    /**
     * Name of the iProcess Modeler Suite , which the IProcess XPDLs are
     * expected to originate from.
     */
    private static final String TIBCO_PROCESS_SUITE = "Tibco-Process-Suite"; //$NON-NLS-1$

    private File file;

    private IProject targetProject;

    private Collection<ImportValidationError> errors =
            new LinkedList<ImportValidationError>();

    public static final int TOTAL_MAJOR_VALIDATIONS = 6;

    /**
     * Contains ID,Node map of all WorkFlowProcess in the file.
     */
    private Map<String, ProcInFile> allProcessInFile;

    /**
     * Collection of process Name for all processes in Workspace
     */
    private Collection<String> allProcessInWorkspace;

    /**
     * Collection process Name for all process Interfaces in Workspace
     */
    private Collection<String> allProcessIfcInWorkspace;

    /**
     * @param targetProject
     * @param file
     * @param allProcessIfcInWorkspace
     * @param allProcessInWorkspace
     */
    public IProcessXpdlValidator(IProject targetProject, File file,
            Collection<String> allProcessInWorkspace,
            Collection<String> allProcessIfcInWorkspace) {
        this.file = file;
        this.targetProject = targetProject;

        allProcessInFile = new HashMap<String, ProcInFile>();
        this.allProcessInWorkspace = allProcessInWorkspace;
        this.allProcessIfcInWorkspace = allProcessIfcInWorkspace;

    }

    /**
     * Runs the validations on the XPDL File being imported.Calls on individual
     * methods defined for each validation, which adds appropriate
     * errors/warnings to the errors list.
     * 
     * The method adds all of the calculated errors into the <code>errors</code>
     * array list which is then checked for size.
     * 
     * @param monitor
     * @throws OperationCanceledException
     *             If the {@link IProgressMonitor} was cancelled by the user.
     */

    public void validate(IProgressMonitor monitor)
            throws OperationCanceledException {

        errors = new LinkedList<ImportValidationError>();
        Exception exception = null;
        String valMsgFormat = "%1s %2s : %3s"; //$NON-NLS-1$

        try {
            monitor.beginTask(Messages.IProcessImportValidator_Msg_Status_Validating,
                    TOTAL_MAJOR_VALIDATIONS);

            monitor.setTaskName(String.format(valMsgFormat,
                    Messages.IProcessImportValidator_Msg_Status_Validating,
                    file.getName(),
                    Messages.IProcessXpdlValidator_StartingValMsg));

            DocumentBuilderFactory xpdlFactory =
                    DocumentBuilderFactory.newInstance();

            xpdlFactory.setNamespaceAware(true);
            DocumentBuilder builder = xpdlFactory.newDocumentBuilder();
            Document xpdlDoc = builder.parse(file);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            NamespaceContext context = new IProcessXpdlNamespaceContext();
            xpath.setNamespaceContext(context);

            allProcessInFile.clear();
            readAllProcessInFile(xpath, xpdlDoc);

            /*
             * XPD-5994: Check if this is a valid iProcess XPDL, add error
             * otherwise.
             */
            /* Major validation 1 */

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            String msg =
                    String.format(valMsgFormat,
                            Messages.IProcessImportValidator_Msg_Status_Validating,
                            file.getName(),
                            Messages.IProcessXpdlValidator_CheckValidIPMFileMsg);

            monitor.setTaskName(msg);
            boolean validXPDL = validateIProcessXPDL(xpath, xpdlDoc);

            if (!validXPDL) {
                /*
                 * No Need to validate further if this is not a n IProcess XPDL
                 * originated from the Tibco IProcess Modeler.
                 */
                monitor.worked(TOTAL_MAJOR_VALIDATIONS);
                monitor.done();
                return;

            }
            monitor.worked(1);

            /* Major validation 2 */
            /*
             * XPD-6154: Fix for additional defect - check for processes to
             * import is moved to IProcessImportValidator, as it should not be
             * checked in isolation for each file.
             */

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            /**
             * Checks the Web service/BW correctness mentioned above.
             */
            msg =
                    String.format(valMsgFormat,
                            Messages.IProcessImportValidator_Msg_Status_Validating,
                            file.getName(),
                            Messages.IProcessXpdlValidator_ValidatingWSDLMsg);

            monitor.setTaskName(msg);

            validateWSDLCorrectness(builder, xpdlDoc, xpath);
            monitor.worked(1);

            /* Major validation 3 */
            // For sub-flow references

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            msg =
                    String.format(valMsgFormat,
                            Messages.IProcessImportValidator_Msg_Status_Validating,
                            file.getName(),
                            Messages.IProcessXpdlValidator_ValidatingSubProcRefMsg);
            monitor.setTaskName(msg);

            validateSubProcReferences(xpdlDoc, xpath, monitor);
            monitor.worked(1);

            /* Major Validation 4 */
            // For DynamicSubProcObject references or GraftStep references

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            msg =
                    String.format(valMsgFormat,
                            Messages.IProcessImportValidator_Msg_Status_Validating,
                            file.getName(),
                            Messages.IProcessXpdlValidator_ValidatingDynSubProcMsg);
            monitor.setTaskName(msg);

            validateDynamicSubProcORGraftStepReferences(xpdlDoc, xpath);
            monitor.worked(1);

            /* Major Validation 5 */
            // For process implementing process-interface references

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            msg =
                    String.format(valMsgFormat,
                            Messages.IProcessImportValidator_Msg_Status_Validating,
                            file.getName(),
                            Messages.IProcessXpdlValidator_ValidatingProcTemOrIntrfMsg);
            monitor.setTaskName(msg);

            validateProcInterfaceReference(xpdlDoc, xpath);
            monitor.worked(1);

            /* Major Validation 6 */
            /* SIA-73: Warn the user of Composite data types. */

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            msg =
                    String.format(valMsgFormat,
                            Messages.IProcessImportValidator_Msg_Status_Validating,
                            file.getName(),
                            Messages.IProcessXpdlValidator_CheckingCompositeTypeMsg);
            monitor.setTaskName(msg);

            raiseWarningsForCompositeTypeParameter(xpdlDoc, xpath);
            monitor.worked(1);

        } catch (SAXException e) {
            exception = e;
        } catch (ParserConfigurationException e) {
            exception = e;
        } catch (IOException e) {
            exception = e;
        } catch (XPathExpressionException e) {
            exception = e;
        } finally {
            monitor.done();
        }

        if (exception != null) {

            ImportValidationError importValidationError =
                    new ImportValidationError(
                            file.getName(),
                            Messages.IProcessXpdlValidator_MsgErrorValidatingXPDL,
                            IStatus.ERROR);

            if (!errors.contains(importValidationError)) {

                errors.add(importValidationError);

            }

            IProcessAMXBPMConverterPlugin
                    .getDefault()
                    .getLogger()
                    .log(new Status(
                            IStatus.ERROR,
                            IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                            Messages.IProcessXpdlValidator_MsgErrorValidatingXPDL,
                            exception));
        }
    }

    /**
     * Raises warnings for the Composite Data Types parameters as they are not
     * supported.
     * 
     * @param xpdlDoc
     *            , XPDL Document.
     * @param xpath
     *            , XPath configured for the IProcessXPDL Namespace Context.
     * @throws XPathExpressionException
     */

    private void raiseWarningsForCompositeTypeParameter(Document xpdlDoc,
            XPath xpath) throws XPathExpressionException {

        Object compositeFieldTypes =
                xpath.evaluate("/xpdl:Package//sw:FieldType[text() = 'COMPOSITE']", //$NON-NLS-1$
                        xpdlDoc,
                        XPathConstants.NODESET);

        if (compositeFieldTypes instanceof NodeList) {

            if (((NodeList) compositeFieldTypes).getLength() > 0) {

                ImportValidationError importValidationError =
                        new ImportValidationError(
                                file.getName(),
                                Messages.IProcessXpdlValidator_CompositeFieldsNotSupported_message,
                                IStatus.WARNING);
                if (!errors.contains(importValidationError)) {

                    errors.add(importValidationError);

                }

            }
        }
    }

    /**
     * Validates availability of the referenced Process Interface, raises a
     * warning if it could not be located in the same doc/XPDL.
     * 
     * @param xpdlDoc
     *            , XPDL Document.
     * @param xpath
     *            , XPath configured for the IProcessXPDL Namespace Context.
     * @throws XPathExpressionException
     */

    private void validateProcInterfaceReference(Document xpdlDoc, XPath xpath)
            throws XPathExpressionException {

        Object workflowProcImplementingTemplates =
                xpath.evaluate("/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ProcProperties']/sw:ProcProperties/sw:SubProcParams[@TemplateName!='']]", //$NON-NLS-1$
                        xpdlDoc,
                        XPathConstants.NODESET);

        if (workflowProcImplementingTemplates instanceof NodeList) {

            NodeList workflows = (NodeList) workflowProcImplementingTemplates;

            for (int iWorkflow = 0; iWorkflow < workflows.getLength(); iWorkflow++) {
                Node workflow = workflows.item(iWorkflow);

                String templateName =
                        xpath.evaluate("xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ProcProperties']/sw:ProcProperties/sw:SubProcParams/@TemplateName", //$NON-NLS-1$
                                workflow);

                if (templateName != null) {

                    addWarningIfProcOrProcIfcOfNameNotFound(templateName);
                }
            }
        }
    }

    /**
     * Validate the references to Dynamic SubProcess and raise warnings for the
     * same.
     * 
     * @param xpdlDoc
     *            , XPDL Document.
     * @param xpath
     *            , XPath configured for the IProcessXPDL Namespace Context.
     * @throws XPathExpressionException
     */

    private void validateDynamicSubProcORGraftStepReferences(Document xpdlDoc,
            XPath xpath) throws XPathExpressionException {

        Object activitiesRefDynSubProcObject =
                xpath.evaluate("/xpdl:Package/xpdl:WorkflowProcesses//xpdl:Activities/xpdl:Activity[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ObjType' and @Value='DynamicSubprocObject']]", //$NON-NLS-1$
                        xpdlDoc,
                        XPathConstants.NODESET);

        if (activitiesRefDynSubProcObject instanceof NodeList) {

            NodeList activitiesRefDynSubProcs =
                    (NodeList) activitiesRefDynSubProcObject;

            for (int iActivity = 0; iActivity < activitiesRefDynSubProcs
                    .getLength(); iActivity++) {

                Node activityNode = activitiesRefDynSubProcs.item(iActivity);

                String templateName =
                        xpath.evaluate("xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='Template']/sw:Template/@Name", //$NON-NLS-1$
                                activityNode);

                if (templateName != null) {

                    addWarningIfProcOrProcIfcOfNameNotFound(templateName);
                }

            }

        }
    }

    /**
     * Validates the references to the SubProcesses and raises warning for the
     * ones which can't be resolved.
     * 
     * @param xpdlDoc
     *            , XPDL Document.
     * @param xpath
     *            , XPath configured for the IProcessXPDL Namespace Context.
     * @param monitor
     * @throws XPathExpressionException
     */
    private void validateSubProcReferences(Document xpdlDoc, XPath xpath,
            IProgressMonitor monitor) throws XPathExpressionException,
            OperationCanceledException {

        Object subFlows =
                xpath.evaluate("/xpdl:Package/xpdl:WorkflowProcesses//xpdl:Activities/xpdl:Activity/xpdl:Implementation/xpdl:SubFlow", //$NON-NLS-1$
                        xpdlDoc,
                        XPathConstants.NODESET);

        if (subFlows instanceof NodeList) {

            // For each sub-flow
            NodeList nodes = (NodeList) subFlows;

            for (int i = 0; i < nodes.getLength(); i++) {

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }

                Node item = nodes.item(i);
                NamedNodeMap attributes = item.getAttributes();
                Node idItem = attributes.getNamedItem("Id"); //$NON-NLS-1$

                // Find the sub-flow id
                if (idItem != null) {

                    String subFlowId = idItem.getNodeValue();

                    if (subFlowId != null) {

                        addWarningIfProcOrProcIfcOfNameNotFound(subFlowId);

                    }

                }

            }
        }
    }

    /**
     * Adds Warning to the erroers list , for the given process not found in the
     * workspace .
     * 
     * @param xpdlDoc
     *            , XPDL Document.
     * @param xpath
     *            , XPath configured for the IProcessXPDL Namespace Context.
     * @param procId
     * @return
     * @throws XPathExpressionException
     */

    private void addWarningIfProcOrProcIfcOfNameNotFound(String procId)
            throws XPathExpressionException {

        /*
         * Raise warning if not found in either file or workspace
         */
        if (!allProcessInFile.containsKey(procId)
                && !allProcessInWorkspace.contains(procId)
                && !allProcessIfcInWorkspace.contains(procId)) {

            String warningMessage =
                    String.format(Messages.IProcessXpdlValidator_ImportOrderIncorrectWarning_shortdesc,
                            procId);

            ImportValidationError importValidationError =
                    new ImportValidationError(file.getName(), warningMessage,
                            IStatus.WARNING);

            if (!errors.contains(importValidationError)) {

                errors.add(importValidationError);

            }
        }

    }

    /**
     * This validation ensures that for EAI Web Services or EAI BW Steps, if the
     * WSDL is not embedded within the iProcess XPDL, it checks whether the WSDL
     * exists in the workspace, and if it doesn't marks an error saying that the
     * impor t cannot be completed.
     * 
     * @param builder
     * @param xpdlDoc
     * @param xpath
     * @return
     * @throws XPathExpressionException
     * @throws SAXException
     * @throws IOException
     */
    private void validateWSDLCorrectness(DocumentBuilder builder,
            Document xpdlDoc, XPath xpath) throws XPathExpressionException,
            SAXException, IOException {

        Object result =
                xpath.evaluate("/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:Applications/xpdl:Application[@Name = 'EAI_BW']/xpdl:ExtendedAttributes[xpdl:ExtendedAttribute[@Name = 'ExtAttr' and sw:ExtAttr[@Name = 'EAIRunType']/sw:EAIRunType = 'EAIJAVA']]/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIData']/sw:EAIData/sw:EAIRunData/sw:EAITypeData/sw:EAIText", //$NON-NLS-1$
                        xpdlDoc,
                        XPathConstants.NODESET);

        if (result instanceof NodeList) {

            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {

                Node item = nodes.item(i);
                String content = item.getTextContent();

                Document eaiDoc =
                        builder.parse(new InputSource(new StringReader(content)));

                result =
                        xpath.evaluate("eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWBusinessWorksPanel']/eaijava:Payload", //$NON-NLS-1$
                                eaiDoc,
                                XPathConstants.NODE);

                if (result instanceof Node) {

                    String payload = ((Node) result).getTextContent();

                    Document payloadDoc =
                            builder.parse(new InputSource(new StringReader(
                                    payload)));

                    boolean hasEmbeddedWsdl = false;

                    result =
                            xpath.evaluate("eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:File_Contents", //$NON-NLS-1$
                                    payloadDoc,
                                    XPathConstants.NODE);

                    if (result instanceof Node) {

                        String contents = ((Node) result).getTextContent();

                        if (contents != null && contents.length() != 0) {
                            hasEmbeddedWsdl = true;
                        }
                    }

                    if (!hasEmbeddedWsdl) {

                        result =
                                xpath.evaluate("eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:Selected_Process", //$NON-NLS-1$
                                        payloadDoc,
                                        XPathConstants.NODE);

                        if (result instanceof Node) {

                            String selected = ((Node) result).getTextContent();

                            WSDLElement element =
                                    IpmImportUtil.getOperation(targetProject,
                                            selected);

                            if (element == null) {

                                ImportValidationError importValidationError =
                                        new ImportValidationError(
                                                file.getName(),
                                                String.format(Messages.IProcessXpdlValidator_OperationUnavailableErrorMessage,
                                                        IpmImportUtil
                                                                .getPortOperationName(selected)),
                                                IStatus.ERROR);

                                if (!errors.contains(importValidationError)) {

                                    errors.add(importValidationError);

                                }

                            }
                        }
                    } else {
                        // Check if there is a services special folder
                        // present in the target project
                        SpecialFolder sf =
                                SpecialFolderUtil
                                        .getSpecialFolderOfKind(targetProject,
                                                IpmImportUtil.WSDL_SPECIALFOLDER_KIND);

                        if (sf == null) {

                            ImportValidationError importValidationError =
                                    new ImportValidationError(
                                            file.getName(),
                                            Messages.IProcessXpdlValidator_noWsdlSpecialFolder_error_message,
                                            IStatus.ERROR);

                            if (!errors.contains(importValidationError)) {

                                errors.add(importValidationError);

                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * @return Collection of errors consolidated by validating the given file.
     */
    public Collection<ImportValidationError> getErrors() {
        return errors;
    }

    /*
     * Additional Validations Added in XPD-5994, as Part of Import IProcess and
     * Convert to AMX BPM XPDL .
     */
    /**
     * Checks if the given XPDL is a valid iProcess.Does a check on the Extended
     * Attribute 'MadeBy' to contain value 'Tibco Process Suite', adds an error
     * if this Resource does not contain this extended attribute entry, hence
     * does not seem to have originated from iProcess Modeler.
     * 
     * @param xpath
     *            , XPath configured for the IProcessXPDL Namespace Context.
     * @param xpdlDoc
     *            , XPDL Document.
     * 
     */
    private boolean validateIProcessXPDL(XPath xpath, Document xpdlDoc) {
        /*
         * Look for the Extended attribute "MadeBY" and check for iProcess
         * Suite.
         */
        try {

            String madeByVal =
                    xpath.evaluate("/xpdl:Package/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name = 'MadeBy']/@Value", //$NON-NLS-1$
                            xpdlDoc);

            if (!(TIBCO_PROCESS_SUITE.equals(madeByVal))) {

                // Resource does not seem to have originated from iProcess
                // Modeler.
                errors.add(new ImportValidationError(
                        file.getName(),
                        Messages.IProcessXpdlValidator_NotIProcessResourceMessage,
                        IStatus.ERROR));
                return false;

            }

        } catch (XPathExpressionException e) {

            errors.add(new ImportValidationError(file.getName(),
                    Messages.IProcessXpdlValidator_NotIProcessResourceMessage,
                    IStatus.ERROR));
            return false;

        }
        return true;
    }

    /**
     * Reads and caches details [name,GUID, Node] of all Process in the XPDL
     * file being validated.
     * 
     * @param xpdlDoc
     */
    private void readAllProcessInFile(XPath xpath, Document xpdlDoc) {

        String queryXPath =
                "/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess"; //$NON-NLS-1$

        Object procInPkg;
        try {
            /* Read all Process Nodes */
            procInPkg =
                    xpath.evaluate(queryXPath, xpdlDoc, XPathConstants.NODESET);

            if (procInPkg instanceof NodeList) {

                NodeList subProcs = (NodeList) procInPkg;
                if (subProcs != null) {

                    for (int i = 0; i < subProcs.getLength(); i++) {

                        Node processNode = subProcs.item(i);
                        /* Read Process 'Name' Attribute value */
                        NamedNodeMap attributes = processNode.getAttributes();
                        Node nameNode = attributes.getNamedItem("Name"); //$NON-NLS-1$
                        Node guidNode = null;

                        /* Start Read GUID for the process */
                        /* Read ExtendedAttributes Node */
                        Node extendedAttributesNode =
                                getChildNodeOfTypeWithNameAttribute("ExtendedAttributes", //$NON-NLS-1$
                                        null,
                                        processNode);

                        if (extendedAttributesNode != null) {
                            /* Read ExtendedAttribute 'ExtAttr' */
                            Node extendedAttributeNode =
                                    getChildNodeOfTypeWithNameAttribute("ExtendedAttribute", //$NON-NLS-1$
                                            "ExtAttr", //$NON-NLS-1$
                                            extendedAttributesNode);

                            if (extendedAttributeNode != null) {
                                /*
                                 * Read ProcProperties for ExtendedAttribute
                                 * 'ExtAttr'
                                 */
                                Node extAttrNode =
                                        getChildNodeOfTypeWithNameAttribute("ExtAttr", //$NON-NLS-1$
                                                "ProcProperties", //$NON-NLS-1$
                                                extendedAttributeNode);

                                if (extAttrNode != null) {

                                    Node procPropertiesNode =
                                            getChildNodeOfTypeWithNameAttribute("ProcProperties", //$NON-NLS-1$
                                                    null,
                                                    extAttrNode);

                                    if (procPropertiesNode != null) {
                                        /* Read ProcProperties 'ProcedureGUID' */
                                        Node procedureGUIDNode =
                                                getChildNodeOfTypeWithNameAttribute("ProcedureGUID", //$NON-NLS-1$
                                                        null,
                                                        procPropertiesNode);

                                        if (procedureGUIDNode != null) {
                                            guidNode = procedureGUIDNode;
                                        }
                                    }
                                }
                            }
                        }

                        String guid =
                                (guidNode != null) ? guidNode.getTextContent()
                                        : ""; //$NON-NLS-1$
                        /* End Read GUID for the process */

                        /* Cache the Process details */
                        allProcessInFile.put(nameNode.getNodeValue(),
                                new ProcInFile(nameNode.getNodeValue(), guid,
                                        processNode));

                    }
                }
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * For the given Node, this method searches and returns the child Node of
     * the given type [<Node tag>] , and if the nameAttribute is provided, with
     * the given name. To search with only Node type pass nameAttribute as Null.
     * 
     * @param nodeType
     * @param nameAttribute
     *            , pass null to search for only NodeType.
     * @param node
     *            , Node to search child of.
     * @return, Child Node of given Type and with name attribute, if applicable.
     *          returns null when not found.
     */
    private Node getChildNodeOfTypeWithNameAttribute(String nodeType,
            String nameAttribute, Node node) {

        NodeList children = node.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {

            Node child = children.item(i);
            /* Check Node Type */
            if (nodeType.equals(child.getNodeName())) {
                /* Check for node name if applicable */
                if (nameAttribute != null) {

                    NamedNodeMap attributes = child.getAttributes();
                    Node nameNode = attributes.getNamedItem("Name"); //$NON-NLS-1$

                    if (nameAttribute.equals(nameNode.getNodeValue())) {
                        /* return only if the name also matches */
                        return child;
                    }

                } else {
                    return child;
                }
            }
        }
        return null;

    }

    /**
     * Returns, Collection of all process in the xpdl file, mapped with their
     * name.
     * 
     * @return the allProcessInFile
     */
    public Map<String, ProcInFile> getAllProcessInFile() {
        return allProcessInFile;
    }

}

/**
 * 
 * Class to represent each process node in the IPM xpdl file being imported.
 * 
 * @author aprasad
 * @since 05-Aug-2014
 */
class ProcInFile {

    /**
     * Name of the process.
     */
    private String name;

    /**
     * GUID of the process.
     */
    private String guid;

    /**
     * Node representing the process in the xpdl file.
     */
    private Node node;

    ProcInFile(String name, String guid, Node node) {

        this.name = name;
        this.guid = guid;
        this.node = node;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @return the node
     */
    public Node getNode() {
        return node;
    }
}