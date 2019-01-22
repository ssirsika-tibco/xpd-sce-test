/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wsdlgen.transform.template;

import static com.tibco.xpd.wsdl.Activator.TIBEX;
import static com.tibco.xpd.wsdl.Activator.TIBEX_URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.util.XSDConstants;
import org.openarchitectureware.xtend.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.xsdindexing.XsdUIUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wsdlgen.WsdlGenBuilderTransformer;
import com.tibco.xpd.wsdlgen.WsdlgenPlugin;
import com.tibco.xpd.wsdlgen.transform.template.BasicTypesHelper.BasicTypeEnum;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Java helper class for common methods used in the transformation process.
 * 
 * @author rsomayaj
 * @author glewis
 * 
 */
public class TransformHelper {

    /**
     * 
     */
    private static final String DOCUMENTATION_URL_ATTRIBUTE =
            "DocumentationURL"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String DOCUMENTATION_ATTRIBUTE = "Documentation"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String DOCUMENTATION_ELEMENT = "DocumentationElement"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String WSDL_PREFIX = "wsdl"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String ID_ATTRIBUTE = "Id"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    /**
     * 
     */
    private static final String OUTPUT_INDICATOR = "_OUTPUT"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String INPUT_INDICATOR = "_INPUT"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String UNDERSCORE_DELIMITER = "_"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String COLON_DELIMITER = ":"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String CLASS_NAME = "ClassName"; //$NON-NLS-1$

    public static final String NS = "ns";//$NON-NLS-1$

    public static final String XSD = "xsd";//$NON-NLS-1$

    public static final String WSDL = WSDL_PREFIX;//$NON-NLS-1$

    private final static String UNNAMED_OPERATION = "UnNamedOperation_"; //$NON-NLS-1$

    /**
     * @param temp
     * @return
     */
    public static String traceMe(String temp) {
        WsdlgenPlugin.getDefault().getLogger().debug(temp);
        System.out.println(temp);
        return EMPTY_STRING;
    }

    /**
     * @param namespace
     * @param localPart
     * @return
     */
    private static QName createQName_pvt(String namespace, String localPart) {
        return new QName(namespace, localPart);
    }

    /**
     * @param obj
     * @return
     */
    public static String getClassName(Object obj) {
        String str = CLASS_NAME + obj.getClass();
        System.out.println(str);
        return str;
    }

    /**
     * @param obj
     * @return
     */
    public static PortType createPortType(Object obj) {
        PortType portType = WSDLFactory.eINSTANCE.createPortType();
        if (obj instanceof QName) {
            portType.setQName((QName) obj);
        }
        return portType;
    }

    /**
     * @param definitionObj
     * @param wsdlElementObj
     * @param elementName
     * @param attrib
     * @param val
     */
    public static void createExtensibilityElement(Object definitionObj,
            Object wsdlElementObj, String elementName, String attrib, String val) {
        if (definitionObj instanceof Definition) {
            if (wsdlElementObj instanceof PortType) {
                PortType portType = (PortType) wsdlElementObj;
                portType.getElement().setAttribute(TIBEX + COLON_DELIMITER
                        + elementName,
                        val);

            } else if (wsdlElementObj instanceof Fault) {
                Fault fault = (Fault) wsdlElementObj;
                fault.getElement().setAttribute(TIBEX + COLON_DELIMITER
                        + elementName,
                        val);
            } else if (wsdlElementObj instanceof Operation) {
                Operation operation = (Operation) wsdlElementObj;
                org.eclipse.wst.wsdl.Input input = (Input) operation.getInput();
                org.eclipse.wst.wsdl.Output output =
                        (Output) operation.getOutput();
                if (input != null) {
                    input.getElement().setAttribute(TIBEX + COLON_DELIMITER
                            + elementName,
                            val);
                }
                if (output != null) {
                    output.getElement().setAttribute(TIBEX + COLON_DELIMITER
                            + elementName,
                            val);
                }
            }
        }
    }

    /**
     * @param portTypeObj
     * @param actOrMethodObj
     * @return
     */
    public static Operation createOperation(Object portTypeObj,
            Object actOrMethodObj) {
        if (portTypeObj instanceof PortType
                && actOrMethodObj instanceof NamedElement) {
            NamedElement namedElement = (NamedElement) actOrMethodObj;
            PortType portType = (PortType) portTypeObj;
            Operation operation = WSDLFactory.eINSTANCE.createOperation();
            setOperationName(portTypeObj, operation, namedElement);
            portType.getEOperations().add(operation);
            return operation;
        }
        return null;
    }

    /**
     * @param definitionObj
     * @param operationObj
     * @param procIfcName
     * @return
     */
    public static Input createInput(Object definitionObj, Object operationObj,
            String procIfcName) {
        if (definitionObj instanceof Definition) {
            Definition definition = (Definition) definitionObj;
            Input input = WSDLFactory.eINSTANCE.createInput();
            if (operationObj instanceof Operation) {
                Operation operation = (Operation) operationObj;
                Message inputMessage = WSDLFactory.eINSTANCE.createMessage();
                /*
                 * XPD-5911: if a process interface name has leading digit(s)
                 * then prefix with underscore
                 */
                inputMessage.setQName(createQName_pvt(definition
                        .getTargetNamespace(),
                        _prefixWithUnderscoreIfStartsWithDigit(procIfcName)
                                + UNDERSCORE_DELIMITER + operation.getName()
                                + INPUT_INDICATOR));
                definition.getEMessages().add(inputMessage);
                input.setEMessage(inputMessage);
                operation.setEInput(input);
            }
            return input;
        }
        return null;

    }

    /**
     * @param definitionObj
     * @param operationObj
     * @param formalParametersContainer
     * @param actOrMethod
     * @return
     */
    public static Output createOutput(Object definitionObj,
            Object operationObj, Object formalParametersContainer,
            Object actOrMethod) {
        if (definitionObj instanceof Definition) {
            Definition definition = (Definition) definitionObj;
            if (shouldCreateOutput(formalParametersContainer, actOrMethod)) {
                Output output = WSDLFactory.eINSTANCE.createOutput();
                if (operationObj instanceof Operation) {
                    Operation operation = (Operation) operationObj;
                    operation.setEOutput(output);
                    Message outputMessage =
                            WSDLFactory.eINSTANCE.createMessage();
                    /*
                     * XPD-5911: if a formal parameters container name has
                     * leading digit(s) then prefix with underscore
                     */
                    String fpContainerName =
                            ((NamedElement) formalParametersContainer)
                                    .getName();

                    outputMessage
                            .setQName(createQName_pvt(definition
                                    .getTargetNamespace(),
                                    _prefixWithUnderscoreIfStartsWithDigit(fpContainerName)
                                            + UNDERSCORE_DELIMITER
                                            + operation.getName()
                                            + OUTPUT_INDICATOR));
                    definition.getEMessages().add(outputMessage);
                    output.setEMessage(outputMessage);
                }
                return output;
            }
        }
        return null;
    }

    /**
     * @param formalParametersContainer
     * @param actOrMethod
     * @return
     */
    @SuppressWarnings("unchecked")
    private static boolean shouldCreateOutput(Object formalParametersContainer,
            Object actOrMethod) {
        if (actOrMethod instanceof Activity) {
            if (!Xpdl2ModelUtil.isProcessAPIActivity((Activity) actOrMethod)) {
                // This scenario is for Service tasks output generation - always
                // create output for Service tasks.
                return true;
            }
        }
        if (formalParametersContainer instanceof FormalParametersContainer) {
            if (formalParametersContainer instanceof Process
                    && actOrMethod instanceof Activity) {
                Activity activity = (Activity) actOrMethod;
                Process process = (Process) formalParametersContainer;
                List<FormalParameter> formalParameters =
                        process.getFormalParameters();

                if (formalParameters.isEmpty()) {
                    List<Activity> replyActivities =
                            ReplyActivityUtil.getReplyActivities(activity);
                    if (replyActivities.isEmpty()) {
                        return false;
                    }
                } else {
                    List<FormalParameter> outParams =
                            new ArrayList<FormalParameter>();
                    outParams.addAll(EMFSearchUtil
                            .findManyInList(formalParameters,
                                    Xpdl2Package.eINSTANCE
                                            .getFormalParameter_Mode(),
                                    ModeType.INOUT_LITERAL));
                    outParams.addAll(EMFSearchUtil
                            .findManyInList(formalParameters,
                                    Xpdl2Package.eINSTANCE
                                            .getFormalParameter_Mode(),
                                    ModeType.OUT_LITERAL));
                    if (outParams.isEmpty()) {
                        List<Activity> replyActivities =
                                ReplyActivityUtil.getReplyActivities(activity);
                        if (replyActivities.isEmpty()) {
                            return false;
                        }
                    }
                }
            } else if (formalParametersContainer instanceof ProcessInterface) {
                if (((ProcessInterface) formalParametersContainer)
                        .getFormalParameters().isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Get the binding style. If the bindingType has a value then this value
     * will be used, otherwise the style will be determined from the
     * process/process interface.
     * 
     * @param definition
     * @param procOrProcIfc
     * @param bindingType
     * @return
     */
    private static boolean isBindingStyleDocLiteral(Definition definition,
            Object procOrProcIfc, String bindingType) {
        boolean isDocLiteral = false;

        if (bindingType != null) {
            if (bindingType.equals(SoapBindingStyle.DOCUMENT_LITERAL
                    .getLiteral())) {
                isDocLiteral = true;
            }
        } else if (procOrProcIfc instanceof Process) {
            SoapBindingStyle wsdlBindingType =
                    Xpdl2ModelUtil.getWsdlBindingStyle((Process) procOrProcIfc);
            if (SoapBindingStyle.DOCUMENT_LITERAL.equals(wsdlBindingType)) {
                isDocLiteral = true;
            }
        } else if (procOrProcIfc instanceof ProcessInterface) {
            SoapBindingStyle wsdlBindingType =
                    Xpdl2ModelUtil
                            .getWsdlBindingStyle((ProcessInterface) procOrProcIfc);
            if (SoapBindingStyle.DOCUMENT_LITERAL.equals(wsdlBindingType)) {
                isDocLiteral = true;
            }
        } else {
            throw new IllegalArgumentException(
                    "Expecting argument to be either process or process interface"); //$NON-NLS-1$
        }
        return isDocLiteral;
    }

    /**
     * Create the "ReplyImmediately" part for message start event that has
     * "reply immediately" set.
     * 
     * @param definitionObj
     * @param msgObj
     * @param procOrProcIfc
     * @param bindingType
     * @return
     */
    public static Part createReplyImmediatelyPart(Object definitionObj,
            Object operationObj, Object procOrProcIfc, String bindingType) {

        if (definitionObj instanceof Definition
                && operationObj instanceof Operation
                && procOrProcIfc instanceof NamedElement) {
            Definition definition = (Definition) definitionObj;
            Operation operation = (Operation) operationObj;

            /*
             * Create output message.
             */
            Output output = WSDLFactory.eINSTANCE.createOutput();
            operation.setEOutput(output);

            Message outputMessage = WSDLFactory.eINSTANCE.createMessage();
            /*
             * XPD-5911: if a process interface name has leading digit(s) then
             * prefix with underscore
             */
            String procOrProcIfcName = ((NamedElement) procOrProcIfc).getName();
            outputMessage.setQName(createQName_pvt(definition
                    .getTargetNamespace(),
                    _prefixWithUnderscoreIfStartsWithDigit(procOrProcIfcName)
                            + UNDERSCORE_DELIMITER + operation.getName()
                            + OUTPUT_INDICATOR));
            definition.getEMessages().add(outputMessage);
            output.setEMessage(outputMessage);

            XSDSchema schema = getWSDLTypesSchema(definition);

            if (schema != null) {
                Part part = null;
                /*
                 * This is what defines whether the part should be created as
                 * elements or types.
                 */
                boolean isDocLiteral =
                        isBindingStyleDocLiteral(definition,
                                procOrProcIfc,
                                bindingType);

                part = WSDLFactory.eINSTANCE.createPart();

                // Get the "xsd:string" basic type
                XSDSimpleTypeDefinition strType =
                        schema.resolveSimpleTypeDefinition(ExternalTypesHelper.SCHEMA_FOR_SCHEMA_URI_2001,
                                "string"); //$NON-NLS-1$
                if (isDocLiteral) {
                    XSDElementDeclaration elem =
                            getElement(schema,
                                    StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME);

                    // If the processId element does not exist then create it
                    if (elem == null) {
                        elem =
                                XSDFactory.eINSTANCE
                                        .createXSDElementDeclaration();
                        elem.setName(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME);
                        elem.setTypeDefinition(strType);
                        schema.getContents().add(elem);
                    }

                    part.setName(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME);
                    part.setElementDeclaration(elem);

                } else /* RPC literal */{
                    part.setName(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_PART_NAME);
                    part.setTypeDefinition(strType);
                }
                outputMessage.getEParts().add(part);
                return part;
            }
        }

        return null;
    }

    /**
     * Get the element with the given name from the schema.
     * 
     * @param schema
     * @param elementName
     * @return element if found, <code>null</code> otherwise.
     */
    private static XSDElementDeclaration getElement(XSDSchema schema,
            String elementName) {

        for (XSDElementDeclaration elem : schema.getElementDeclarations()) {
            if (elementName.equals(elem.getName())) {
                return elem;
            }
        }
        return null;
    }

    /**
     * @param definitionObj
     * @param msgObj
     * @param name
     * @param modeObj
     * @param dataTypeObj
     * @return
     */
    public static Part createPart(Object definitionObj, Object msgObj,
            String name, Object modeObj, Object dataTypeObj,
            Object procOrProcIfc, String bindingType) {
        if (definitionObj instanceof Definition) {
            Definition definition = (Definition) definitionObj;
            Part part = null;
            /*
             * This is what defines whether the part should be created as
             * elements or types. If the binding style is passed to the method -
             * use that, else if it is null query the process or process
             * interface for the wsdl gen binding style.
             */
            boolean isDocLiteral =
                    isBindingStyleDocLiteral(definition,
                            procOrProcIfc,
                            bindingType);

            if (msgObj instanceof Message) {
                Message message = (Message) msgObj;
                IFile file = WorkingCopyUtil.getFile(message);
                IProject project = null;
                if (file != null) {
                    project = file.getProject();
                }
                if (dataTypeObj instanceof BasicType) {
                    part =
                            handleXPDLBasicTypes(name,
                                    dataTypeObj,
                                    definition,
                                    message,
                                    isDocLiteral);
                } else if (dataTypeObj instanceof ExternalReference) {
                    part =
                            handleExternalReferences(procOrProcIfc,
                                    name,
                                    dataTypeObj,
                                    definition,
                                    message,
                                    project,
                                    isDocLiteral);
                }
            }
            return part;
        }
        return null;

    }

    /**
     * @param procOrProcIfc
     * @param name
     * @param dataTypeObj
     * @param definition
     * @param message
     * @param project
     * @param procOrProcIfc
     * @return
     */
    private static Part handleExternalReferences(Object procOrProcIfc,
            String name, Object dataTypeObj, Definition definition,
            Message message, IProject project, boolean isDocLiteral) {

        /*
         * XPD-4449: this boolean is to determine if for any reason schema
         * information (in the indexer) is not available for wsdl parts then the
         * wsdl must not get generated and a ParseException thrown
         */
        boolean errorFlag = false;
        ExternalReference ref = (ExternalReference) dataTypeObj;
        Part part = WSDLFactory.eINSTANCE.createPart();
        part.setName(name);

        EObject container = ref.eContainer();
        if (container instanceof ProcessRelevantData) {
            createDescription(definition, part, container);

        }
        message.getEParts().add(part);
        String xref = ref.getXref();
        String bomLocation = ref.getLocation();

        /**
         * Find the WSDL binding type
         */

        // Search for the schema in the local project first
        IndexerItem indexedItem =
                getXsdlElementFromIndexer(xref,
                        project,
                        isDocLiteral,
                        bomLocation);

        if (indexedItem == null) {
            // Search through all referenced projects
            Set<IProject> referencedProjectsHierarchy = new HashSet<IProject>();
            referencedProjectsHierarchy =
                    ProjectUtil.getReferencedProjectsHierarchy(project,
                            referencedProjectsHierarchy);

            for (IProject pr : referencedProjectsHierarchy) {
                indexedItem =
                        getXsdlElementFromIndexer(xref,
                                pr,
                                isDocLiteral,
                                bomLocation);
                if (indexedItem != null) {
                    break;
                }
            }
        }

        if (indexedItem != null) {
            if (isDocLiteral) {
                XSDElementDeclaration elementDeclaration =
                        ExternalTypesHelper
                                .getElementDeclarationInWsdlDefinition(definition,
                                        indexedItem);

                if (elementDeclaration != null) {
                    part.setElementDeclaration(elementDeclaration);
                } else {
                    errorFlag = true;
                }
            } else {
                XSDTypeDefinition typeDef =
                        ExternalTypesHelper
                                .getComplexTypeInWsdlDefinition(definition,
                                        indexedItem);
                if (typeDef != null) {
                    part.setTypeDefinition(typeDef);
                } else {
                    errorFlag = true;
                }
            }

        } else {
            errorFlag = true;
        }

        if (errorFlag) {
            /*
             * XPD-4449: if a bom file has any problem markers on it, xsd will
             * not get generated for it (in .bom2xsd folder). and if a parameter
             * is referring to a bom type in such bom file with problem markers,
             * wsdl gets generated incorrectly without defining element/type
             * attributes for the message parts. so we must not generate the
             * wsdl if there are any problems in the bom files referred by the
             * parts
             */
            /*
             * XPD-5121: report a more meaningful error. An exception with a
             * stack trace for an expected error is confusing.
             */
            throw new ParseException(new SchemaNotFoundError(procOrProcIfc,
                    name, xref, bomLocation));
        }

        return part;
    }

    /**
     * Get the xsd element from the indexer that is related to the information
     * given.
     * 
     * @param xref
     *            external reference id
     * @param project
     *            project in which the resource belongs
     * @param isDocLiteral
     * @param location
     *            location of the external (bom) file
     * @return
     */
    private static IndexerItem getXsdlElementFromIndexer(String xref,
            IProject project, boolean isDocLiteral, String location) {

        if (location != null) {
            IFile bomFile =
                    SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                            location);

            if (bomFile != null) {
                IndexerItem indexedItem =
                        XsdUIUtil.queryXsdElement(xref, bomFile, isDocLiteral);

                if (indexedItem != null) {
                    return indexedItem;
                }
            }
        } else {
            // No location provided so just find an XSD element with the given
            // data
            return XsdUIUtil.queryXsdElement(xref, project, isDocLiteral);
        }

        return null;
    }

    /**
     * @param name
     * @param dataTypeObj
     * @param definition
     * @param message
     * @param isDocLiteral
     * @return
     */
    private static Part handleXPDLBasicTypes(String name, Object dataTypeObj,
            Definition definition, Message message, boolean isDocLiteral) {
        Part part = WSDLFactory.eINSTANCE.createPart();
        part.setName(name);
        message.getEParts().add(part);
        XSDSchema schema = getWSDLTypesSchema(definition);
        if (schema != null) {
            BasicType basicType = (BasicType) dataTypeObj;
            EObject container = basicType.eContainer();

            if (container instanceof ProcessRelevantData) {
                createDescription(definition, part, container);
            }
            if (container instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) container;
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(formalParameter,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InitialValues());
                // if the formal parameter has initial
                setPartTypeDef(definition,
                        part,
                        schema,
                        basicType,
                        formalParameter,
                        otherElement,
                        isDocLiteral);
            } else if (container instanceof DataField) {
                DataField dataField = (DataField) container;
                Collection<String> initialValues = Collections.EMPTY_LIST;
                setPartTypeDef(definition,
                        part,
                        schema,
                        basicType,
                        dataField,
                        initialValues,
                        isDocLiteral);
            }
        }
        return part;
    }

    /**
     * Get the WSDL types schema from the Definition.
     * 
     * @param definition
     * @return
     */
    private static XSDSchema getWSDLTypesSchema(Definition definition) {
        if (definition != null && definition.getTargetNamespace() != null
                && definition.getETypes() != null
                && definition.getETypes().getSchemas() != null) {
            for (Object next : definition.getETypes().getSchemas()) {
                if (next instanceof XSDSchema) {
                    if (definition.getTargetNamespace()
                            .equals(((XSDSchema) next).getTargetNamespace())) {
                        return (XSDSchema) next;
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param definition
     * @param part
     * @param schema
     * @param basicType
     * @param prd
     * @param initialValues
     * @param otherElement
     */
    private static void setPartTypeDef(Definition definition, Part part,
            XSDSchema schema, BasicType basicType, ProcessRelevantData prd,
            Object otherElement, boolean isDocLiteralRequired) {
        InitialValues initialValues = null;
        String paramsContainerName = null;
        if (prd.eContainer() instanceof FormalParametersContainer) {
            paramsContainerName = ((NamedElement) prd.eContainer()).getName();
        } else {
            paramsContainerName = EMPTY_STRING;
        }
        if (otherElement instanceof InitialValues) {
            initialValues = (InitialValues) otherElement;
        }

        XSDTypeDefinition typeDef =
                BasicTypesHelper.checkExistingDataTypes(schema,
                        paramsContainerName,
                        prd.getName(),
                        basicType,
                        prd.isIsArray(),
                        initialValues);

        if (null != initialValues
                && !(BasicTypeType.BOOLEAN_LITERAL.equals(basicType.getType()))) {
            // if the basic type is not null
            // Check here for data types with allowed values provided

            List<String> enumValues = initialValues.getValue();
            if (!(enumValues.isEmpty())) {

                String typeDefName =
                        BasicTypeEnum.getType(basicType).getFormattedName()
                                + UNDERSCORE_DELIMITER + paramsContainerName
                                + UNDERSCORE_DELIMITER + prd.getName();
                EObject enumTypeDefName =
                        EMFSearchUtil.findInList(schema.getTypeDefinitions(),
                                XSDPackage.eINSTANCE
                                        .getXSDNamedComponent_Name(),
                                typeDefName);
                if (null != enumTypeDefName
                        && enumTypeDefName instanceof XSDTypeDefinition) {
                    typeDef = (XSDTypeDefinition) enumTypeDefName;
                    BasicTypesHelper.updateTypeDefWithEnumValues(typeDef,
                            basicType,
                            initialValues);
                } else {
                    typeDef = null;
                }
            }
        }
        if (typeDef == null) {
            typeDef =
                    BasicTypesHelper.createNewTypeDefinition(schema,
                            paramsContainerName,
                            prd.getName(),
                            basicType,
                            definition.getTargetNamespace(),
                            prd.isIsArray(),
                            initialValues);
        }
        if (isDocLiteralRequired) {
            EObject prdContainer = prd.eContainer();
            if (prdContainer instanceof NamedElement) {
                NamedElement containerNamedElement =
                        (NamedElement) prdContainer;

                EObject elInSchema =
                        EMFSearchUtil.findInList(schema
                                .getElementDeclarations(),
                                XSDPackage.eINSTANCE
                                        .getXSDNamedComponent_Name(),
                                containerNamedElement.getName() + "_" //$NON-NLS-1$
                                        + prd.getName());

                if (elInSchema instanceof XSDElementDeclaration) {
                    XSDElementDeclaration existingElement =
                            (XSDElementDeclaration) elInSchema;

                    if (existingElement.getTypeDefinition() != null) {
                        if (!(existingElement.getTypeDefinition().getName()
                                .equals(typeDef.getName()))) {
                            existingElement.setTypeDefinition(typeDef);
                        }
                    }
                    part.setElementDeclaration(existingElement);

                } else {
                    XSDElementDeclaration elDeclaration =
                            XSDFactory.eINSTANCE.createXSDElementDeclaration();
                    elDeclaration.setName(containerNamedElement.getName() + "_" //$NON-NLS-1$
                            + prd.getName());
                    elDeclaration.setTypeDefinition(typeDef);
                    schema.getContents().add(elDeclaration);
                    part.setElementDeclaration(elDeclaration);
                }
            }
        } else {

            part.setTypeDefinition(typeDef);
        }
    }

    /**
     * @param element
     * @return
     */
    public static String getElementTagName(Object element) {
        if (element instanceof Element) {
            return ((Element) element).getTagName();
        }
        return null;
    }

    /**
     * @param element
     * @return
     */
    public static String getAttribId(Object element) {
        if (element instanceof Element) {
            return ((Element) element).getAttribute(ID_ATTRIBUTE);
        }
        return null;
    }

    /**
     * @param portTypeObj
     * @param qNameObj
     */
    public static void setPortTypeQName(Object portTypeObj, Object qNameObj) {
        PortType portType = null;
        if (portTypeObj instanceof PortType) {
            portType = (PortType) portTypeObj;

        }
        QName qName = null;
        if (qNameObj instanceof QName) {
            qName = (QName) qNameObj;
        }
        if (portType != null && qName != null) {
            portType.setQName(qName);
        }
    }

    public static boolean isPageFlowProcess(Object processObj) {
        if (processObj instanceof com.tibco.xpd.xpdl2.Process) {

            com.tibco.xpd.xpdl2.Process process =
                    (com.tibco.xpd.xpdl2.Process) processObj;

            return Xpdl2ModelUtil.isPageflow(process);
        }
        return false;
    }

    /**
     * @param definitionObj
     *            WSDL definition
     * @param schemaList
     *            list of schemas. NOTE: This should be in dependency order, eg.
     *            if schemaA depends on schemaB then schemaB should be before
     *            schemaA in the list.
     */
    public static void addSchemaToTypes(Object definitionObj, List schemaList) {
        if (definitionObj instanceof Definition) {
            Definition definition = (Definition) definitionObj;
            ExternalTypesHelper.addSchemasToWsdlETypes(definition, schemaList);
        }
    }

    public static Types createTypes(Object definitionObj) {
        if (definitionObj instanceof Definition) {
            Definition definition = (Definition) definitionObj;
            return ExternalTypesHelper.createWsdlETypes(definition);
        }
        return null;
    }

    /**
     * Updates the schemas
     * 
     * @param definitionObj
     * @param bomObj
     */
    public static void updateParts(Object definitionObj, Object bomObj) {
        if (definitionObj instanceof Definition && bomObj instanceof Model) {
            /*
             * if (definition != null && xsdSchemaObj instanceof XSDSchema) {
             * XSDSchema schema = (XSDSchema) xsdSchemaObj;
             * ExternalTypesHelper.updateSchemaInDefinition(definition, schema);
             * }
             */
            ExternalTypesHelper
                    .updateSchemaInDefinition((Definition) definitionObj,
                            (Model) bomObj);
        }
    }

    /**
     * Remove any inlined schemas that are no longer used by the xpdl package.
     * 
     * @param definitionObj
     * @param xpdlObj
     */
    public static void cleanupInlinedSchemas(Object definitionObj,
            Object xpdlObj) {
        if (definitionObj instanceof Definition && xpdlObj instanceof Package) {
            ExternalTypesHelper
                    .cleanupInlinedSchemas((Definition) definitionObj,
                            (Package) xpdlObj);
        }
    }

    /**
     * @param activityObj
     * @return
     */
    public static boolean isImplementedActivity(Object activityObj) {
        if (activityObj instanceof Activity) {
            return ProcessInterfaceUtil
                    .isEventImplemented((Activity) activityObj);
        }
        return false;
    }

    /**
     * ABPM-911: A Message Start Event can be in a xpdl2:Process or
     * Embedded/Event Sub proc.
     * 
     * @param activity
     * @return eContainer (xpdl2:Process or ActivitySet) of the activity
     */
    public static EObject _getActivityContainer(Object activity) {

        if (activity instanceof Activity) {

            EObject eContainer = ((Activity) activity).eContainer();
            if (eContainer instanceof Process
                    || eContainer instanceof ActivitySet) {

                return eContainer;
            }
        }
        return null;
    }

    /**
     * @param activityObj
     * @return
     */
    public static InterfaceMethod getImplementedMethod(Object activityObj) {
        if (activityObj instanceof Activity) {
            InterfaceMethod implementedMethod =
                    ProcessInterfaceUtil
                            .getImplementedMethod((Activity) activityObj);
            return implementedMethod;
        }
        return null;

    }

    /**
     * @param processObj
     * @return
     */
    public static ProcessInterface getImplementedProcesInterface(
            Object processObj) {
        if (processObj instanceof com.tibco.xpd.xpdl2.Process) {
            com.tibco.xpd.xpdl2.Process process =
                    (com.tibco.xpd.xpdl2.Process) processObj;
            return ProcessInterfaceUtil.getImplementedProcessInterface(process);

        }
        return null;

    }

    /**
     * @param qName
     * @return
     */
    public static Message createFaultMsg(Object qName) {
        if (qName instanceof QName) {
            QName name = (QName) qName;
            Message message = WSDLFactory.eINSTANCE.createMessage();
            message.setQName(name);
            return message;
        }
        return null;
    }

    /***
     * 
     * XPD-4183: To avoid duplicate key error - check if the wsdl definition
     * already has the given fault message.
     * 
     * @param definitionObj
     * @param faultMsgObj
     * @return true if the wsdl definition already has the given fault message;
     *         false otherwise
     */
    public static boolean _isFaultMsgAlreadyExists(Object definitionObj,
            Object faultMsgObj) {

        if (definitionObj instanceof Definition
                && faultMsgObj instanceof Message) {

            Message faultMsg = (Message) faultMsgObj;
            Definition definition = (Definition) definitionObj;
            EList<?> eMessages = definition.getEMessages();

            for (Object object : eMessages) {
                if (object instanceof Message) {
                    Message message = (Message) object;
                    if (message.getQName().equals(faultMsg.getQName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return
     */
    public static boolean doesHaveReplyActivities(Object activity) {
        if (activity instanceof Activity) {
            Activity act = (Activity) activity;
            List<Activity> replyActivities =
                    ReplyActivityUtil.getReplyActivities(act);
            if (!replyActivities.isEmpty()) {
                return true;
            }

        }
        return false;
    }

    /**
     * @return
     */
    public static Fault createFault() {
        return WSDLFactory.eINSTANCE.createFault();
    }

    /**
     * @param processObj
     * @return
     */
    public static boolean doesProcessContainReqdExtAttr(Object processObj) {
        if (processObj instanceof Process) {
            return WsdlGenBuilderTransformer
                    .doesContainRequiredExtendedAttribute((Process) processObj);
        }
        return false;
    }

    /**
     * Check if the extended attribute is set to indicate that port type should
     * be generated for process interfaces.
     * 
     * @param processInterface
     * @return
     */
    public static boolean isWsdlGenEnabledForProcessInterface(
            Object processInterface) {
        if (processInterface instanceof ProcessInterface) {
            return WsdlGenBuilderTransformer
                    .isWsdlGenEnabledForProcessInterface((ProcessInterface) processInterface);
        }
        return false;
    }

    /**
     * @param extensibleElement
     * @param elementName
     * @return
     */
    public static String getProcessInfoIdentifier(Object extensibleElement,
            Object elementName) {
        if (extensibleElement instanceof WSDLElement) {
            WSDLElement wsdlElement = (WSDLElement) extensibleElement;
            String attribute = null;
            if (wsdlElement instanceof Operation) {
                Operation operation = (Operation) wsdlElement;
                if (operation.getInput() != null) {
                    attribute =
                            operation
                                    .getEInput()
                                    .getElement()
                                    .getAttribute(TIBEX + COLON_DELIMITER
                                            + elementName);
                } else {
                    attribute =
                            operation
                                    .getEOutput()
                                    .getElement()
                                    .getAttribute(TIBEX + COLON_DELIMITER
                                            + elementName);
                }

            } else {
                attribute =
                        wsdlElement.getElement().getAttribute(TIBEX
                                + COLON_DELIMITER + elementName);
            }
            return attribute;
        }
        return null;
    }

    /**
     * @param definitionObj
     * @param namespaces
     */
    public static void removeSchemas(Object definitionObj, List namespaces) {
        Definition definition = null;
        if (definitionObj instanceof Definition) {
            definition = (Definition) definitionObj;
        }
        if (definition != null) {
            ExternalTypesHelper.removeSchemasFromWsdlDefnTypes(definition,
                    namespaces);
        }
    }

    /**
     * @param portTypeObj
     * @param operationObj
     * @param namedElementObj
     */
    public static void setOperationName(Object portTypeObj,
            Object operationObj, Object namedElementObj) {
        if (operationObj instanceof Operation) {
            String portTypeOperationName =
                    getPortTypeOperationName(namedElementObj);
            Operation operation = (Operation) operationObj;
            String portTypeName;
            if (portTypeObj instanceof PortType) {
                portTypeName =
                        ((PortType) portTypeObj).getQName().getLocalPart();
            } else {
                portTypeName = EMPTY_STRING;
            }
            if (portTypeOperationName != null) {

                /*
                 * XPD-5911: if a port type operation name has leading digit(s)
                 * then prefix with underscore
                 */

                operation
                        .setName(_prefixWithUnderscoreIfStartsWithDigit(portTypeOperationName));

                // get the input -
                Input input = operation.getEInput();
                if (null != input) {
                    // get the message -
                    Message inputMessage = input.getEMessage();
                    // change the qname of the message
                    if (null != inputMessage) {

                        /*
                         * XPD-5911: if a port type name has leading digit(s)
                         * then prefix with underscore
                         */

                        QName inputMsgQName =
                                createQName_pvt(operation
                                        .getEnclosingDefinition()
                                        .getTargetNamespace(),
                                        _prefixWithUnderscoreIfStartsWithDigit(portTypeName)
                                                + UNDERSCORE_DELIMITER
                                                + operation.getName()
                                                + INPUT_INDICATOR);
                        inputMessage.setQName(inputMsgQName);
                        input.setEMessage(inputMessage);
                    }
                }

                // get the output
                Output output = operation.getEOutput();
                // get the message -
                if (null != output) {
                    Message outputMsg = output.getEMessage();
                    // change the qname of the message
                    if (null != outputMsg) {
                        /*
                         * XPD-5911: if a port type name has leading digit(s)
                         * then prefix with underscore
                         */
                        QName outputMsgQName =
                                createQName_pvt(operation
                                        .getEnclosingDefinition()
                                        .getTargetNamespace(),
                                        _prefixWithUnderscoreIfStartsWithDigit(portTypeName)
                                                + UNDERSCORE_DELIMITER
                                                + operation.getName()
                                                + OUTPUT_INDICATOR);
                        outputMsg.setQName(outputMsgQName);
                        output.setEMessage(outputMsg);
                    }
                }

            } else {
                String opName = getUniqueOpName(portTypeObj);
                /*
                 * XPD-5911: if a operation name has leading digit(s) then
                 * prefix with underscore
                 */
                operation
                        .setName(_prefixWithUnderscoreIfStartsWithDigit(opName));
            }
        }
    }

    /**
     * @param operationObj
     * @return
     */
    private static String getUniqueOpName(Object portTypeObj) {
        if (portTypeObj instanceof PortType) {
            PortType portType = (PortType) portTypeObj;
            List operations = portType.getEOperations();
            int index = 0;
            for (Object op : operations) {
                if ((UNNAMED_OPERATION + index).equals(((Operation) op)
                        .getName())) {
                    index++;
                }
            }
            return UNNAMED_OPERATION + index;
        }
        return UNNAMED_OPERATION;
    }

    /**
     * @param namedElementObj
     * @return
     */
    private static String getPortTypeOperationName(Object namedElementObj) {
        if (namedElementObj instanceof Activity) {
            Activity activity = (Activity) namedElementObj;
            TaskType taskType = TaskObjectUtil.getTaskType(activity);
            if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                TaskReceive taskReceive =
                        ((Task) activity.getImplementation()).getTaskReceive();
                Object ptoObject =
                        Xpdl2ModelUtil.getOtherElement(taskReceive,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
                if (ptoObject instanceof PortTypeOperation) {
                    PortTypeOperation portTypeOperation =
                            (PortTypeOperation) ptoObject;
                    return portTypeOperation.getOperationName();
                }

            } else {
                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(activity);
                if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                        .equals(eventTriggerType)) {
                    TriggerResultMessage triggerResultMessage =
                            EventObjectUtil.getTriggerResultMessage(activity);
                    if (triggerResultMessage != null) {
                        Object ptoObject =
                                Xpdl2ModelUtil
                                        .getOtherElement(triggerResultMessage,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_PortTypeOperation());
                        if (ptoObject instanceof PortTypeOperation) {
                            PortTypeOperation portTypeOperation =
                                    (PortTypeOperation) ptoObject;
                            return portTypeOperation.getOperationName();
                        }
                    }
                } else {
                    return ((NamedElement) namedElementObj).getName();
                }
            }
        } else if (namedElementObj instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) namedElementObj;
            TriggerResultMessage triggerResultMessage =
                    interfaceMethod.getTriggerResultMessage();
            if (triggerResultMessage != null) {
                Object ptoObject =
                        Xpdl2ModelUtil.getOtherElement(triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
                if (ptoObject instanceof PortTypeOperation) {
                    PortTypeOperation portTypeOperation =
                            (PortTypeOperation) ptoObject;
                    return portTypeOperation.getOperationName();
                }
            } else {
                return ((NamedElement) namedElementObj).getName();
            }

        }
        return null;
    }

    /**
     * @param obj
     * @return
     */
    public static boolean isWebService(Object obj) {
        if (obj instanceof OtherAttributesContainer) {
            return isWSImplementationType((OtherAttributesContainer) obj);
        }
        return false;
    }

    /**
     * @param taskReceive
     */
    private static boolean isWSImplementationType(
            OtherAttributesContainer otherAttribContainer) {
        Object implementationTypeAttribute =
                Xpdl2ModelUtil.getOtherAttribute(otherAttribContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        if (implementationTypeAttribute != null
                && TaskImplementationTypeDefinitions.WEB_SERVICE
                        .equals(implementationTypeAttribute)) {
            return true;
        }
        return false;
    }

    /**
     * @param activityObj
     * @return
     */
    public static boolean isGeneratedRequestActivity(Object activityObj) {
        if (activityObj instanceof Activity) {
            Activity activity = (Activity) activityObj;
            return Xpdl2ModelUtil.isGeneratedRequestActivity(activity);
        }
        return false;
    }

    /**
     * @param processObj
     * @return
     */
    public static boolean isDestinationFavouredForProcess(Object processObj) {
        if (processObj instanceof Process) {
            Process process = (Process) processObj;
            return ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessDestinations(process);
        }
        return false;
    }

    /**
     * @param processIfcObj
     * @return
     */
    public static boolean isDestinationFavouredForProcessInterface(
            Object processIfcObj) {
        if (processIfcObj instanceof ProcessInterface) {
            ProcessInterface processIfc = (ProcessInterface) processIfcObj;
            return ProcessDestinationUtil
                    .shouldGenerateWSDLForProcessInterfaceDestinations(processIfc);
        }
        return false;
    }

    /**
     * @param name
     * @return
     */
    public static String getInternalName(String name) {
        return NameUtil.getInternalName(name, true);
    }

    /**
     * Get the throw error events configured for the given message activity. If
     * this is a service task then the attached error events will be returned.
     * 
     * @param activityObj
     * @return
     */
    public static List<Activity> getErrorEventsForActivity(Object activityObj) {
        List<Activity> errorEvents = new ArrayList<Activity>();
        if (activityObj instanceof Activity) {
            Activity activity = (Activity) activityObj;
            Task task =
                    (Task) (activity.getImplementation() instanceof Task ? activity
                            .getImplementation() : null);

            /*
             * If this is a Send task then that cannot have any error events
             * associated with it.
             */
            if (task != null && task.getTaskSend() != null) {
                return Collections.emptyList();
            }

            Set<Activity> allEvents = new HashSet<Activity>();

            // Only service task can have error events on its boundary
            if (task != null && task.getTaskService() != null) {
                allEvents.addAll(Xpdl2ModelUtil.getAttachedEvents(activity));
            } else {
                allEvents.addAll(ThrowErrorEventUtil
                        .getThrowErrorEvents(activity));
            }

            for (Activity eventAct : allEvents) {
                Event event = eventAct.getEvent();
                if (event != null) {
                    EventTriggerType triggerType =
                            EventObjectUtil.getEventTriggerType(eventAct);

                    if (triggerType == EventTriggerType.EVENT_ERROR_LITERAL) {
                        errorEvents.add(eventAct);
                    }
                }
            }
        }
        return errorEvents;
    }

    /**
     * @param definitionObj
     * @param xpdlPackageModel
     */
    public static void updateDefinitionName(Object definitionObj,
            Object xpdlPackageModel) {
        if (definitionObj instanceof Definition
                && xpdlPackageModel instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package xpdlPacakge =
                    (com.tibco.xpd.xpdl2.Package) xpdlPackageModel;

            Definition definition = (Definition) definitionObj;
            String derivedWsdlNamespace =
                    Xpdl2ModelUtil.getDerivedWsdlNamespace(xpdlPacakge);
            String xpdlPkgName = xpdlPacakge.getName();
            /*
             * XPD-5911: if a xpdl package name has leading digit(s) then prefix
             * with underscore
             */
            QName qName =
                    createQName_pvt(derivedWsdlNamespace,
                            _prefixWithUnderscoreIfStartsWithDigit(xpdlPkgName));

            definition.setQName(qName);

            /* XPD-397: Have to reset namespaces in case package id has changed. */
            updateNamespaces(definition, xpdlPacakge);
        }
    }

    /**
     * Add or update the name spaces to the wsdl:definition and the inline
     * schema of the same namespace.
     * 
     * @param definition
     * @param pkg
     */
    public static void updateNamespaces(Definition definition, Package pkg) {
        String pckgName = pkg.getName();
        /*
         * XPD-5911: if a process package name has leading digit(s) then prefix
         * with underscore
         */
        updateNamespaces(definition,
                Xpdl2ModelUtil.getDerivedWsdlNamespace(pkg),
                _prefixWithUnderscoreIfStartsWithDigit(pckgName));
    }

    /**
     * Add or update the name spaces to the wsdl:definition and the inline
     * schema of the same namespace.
     * 
     * @param definition
     * @param namespace
     *            namespace to set
     * @param qNameLocalPart
     *            the local part to set for the {@link Definition} QName. If
     *            this is <code>null</code> then the current local part will be
     *            used.
     */
    public static void updateNamespaces(Definition definition,
            String namespace, String qNameLocalPart) {

        String currentNamespace = definition.getTargetNamespace();

        /*
         * Before changing the wsd:definition namespace change any schema with
         * the existing namespace to the new one first.
         */
        if (currentNamespace != null && currentNamespace.length() > 0
                && !currentNamespace.equals(namespace)) {
            if (definition.getETypes() != null) {
                Types types = definition.getETypes();

                List<?> schemas = types.getSchemas();
                if (schemas != null) {
                    for (Object s : schemas) {
                        if (s instanceof XSDSchema) {
                            XSDSchema schema = (XSDSchema) s;

                            if (currentNamespace.equals(schema
                                    .getTargetNamespace())) {
                                schema.setTargetNamespace(namespace);
                                schema.getQNamePrefixToNamespaceMap().put(null,
                                        namespace);
                            }
                        }
                    }
                }
            }
        }

        definition.setTargetNamespace(namespace);
        definition.addNamespace(XSD, XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);
        definition.addNamespace(TIBEX, TIBEX_URI);
        definition.addNamespace(WSDL, WSDLConstants.WSDL_NAMESPACE_URI);

        /*
         * Reset the namespace before adding it. If this is not done then this
         * will add a new namespace prefix because prefix NS is already in use
         * and has a difference namespace URI then the one being set. So to
         * replace the URI first unset the namespace.
         */
        definition.addNamespace(NS, null);
        definition.addNamespace(NS, namespace);

        if (qNameLocalPart == null && definition.getQName() != null) {
            qNameLocalPart = definition.getQName().getLocalPart();
        }

        if (qNameLocalPart != null) {
            definition.setQName(new QName(namespace, qNameLocalPart));
        }

    }

    /**
     * @param messageObject
     * @param qNameObject
     */
    public static void updateFaultMessageQName(Object messageObject,
            Object qNameObject) {
        if (messageObject instanceof Message && qNameObject instanceof QName) {
            ((Message) messageObject).setQName((QName) qNameObject);
        }
    }

    /**
     * Creates the documentation object.
     * 
     * @param wsdlObject
     * @param procObj
     */
    public static void createDescription(Object definitionObject,
            Object wsdlObject, Object procObj) {
        if (wsdlObject instanceof WSDLElement) {
            WSDLElement wsdlElement = (WSDLElement) wsdlObject;
            Definition definition = (Definition) definitionObject;

            Description description = null;
            if (procObj instanceof Process) {
                Process process = (Process) procObj;
                description = process.getProcessHeader().getDescription();
            } else if (procObj instanceof DescribedElement) {
                description = ((DescribedElement) procObj).getDescription();
            }
            if (description != null) {
                Document document = definition.getDocument();

                Element documentationElement =
                        document.createElementNS(WSDLConstants.WSDL_NAMESPACE_URI,
                                WSDL_PREFIX
                                        + COLON_DELIMITER
                                        + WSDLConstants.DOCUMENTATION_ELEMENT_TAG);

                Element docChild =
                        document.createElement(DOCUMENTATION_ELEMENT);
                docChild.setAttribute(DOCUMENTATION_ATTRIBUTE,
                        description.getValue());
                documentationElement.appendChild(docChild);
                Object docUrl =
                        Xpdl2ModelUtil.getOtherAttribute(description,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DocumentationURL());
                if (docUrl != null) {
                    docChild.setAttribute("DocumentationUrl", (String) docUrl); //$NON-NLS-1$
                }
                wsdlElement.setDocumentationElement(documentationElement);
                wsdlElement.updateElement(true);
            }
        }
    }

    /**
     * @param wsdlObject
     * @param procObj
     */
    public static void updatePortTypeDescription(Object wsdlObject,
            Object procObj) {
        if (wsdlObject instanceof PortType) {
            PortType portType = (PortType) wsdlObject;
            String localName = portType.getQName().getLocalPart();
            Definition definition = portType.getEnclosingDefinition();
            Element documentationElement = portType.getDocumentationElement();
            if (documentationElement == null) {
                createDescription(definition, portType, procObj);
            } else {
                Description description = null;
                if (procObj instanceof Process) {
                    Process process = (Process) procObj;
                    description = process.getProcessHeader().getDescription();
                } else if (procObj instanceof DescribedElement) {
                    description = ((DescribedElement) procObj).getDescription();
                }
                if (description != null) {
                    Node item = documentationElement.getFirstChild();
                    while ((!(DOCUMENTATION_ELEMENT.equals(item.getLocalName())))) {
                        item = item.getNextSibling();
                        if (DOCUMENTATION_ELEMENT.equals(item.getLocalName())) {
                            break;
                        }
                    }

                    if (DOCUMENTATION_ELEMENT.equals(item.getLocalName())) {
                        NamedNodeMap attributes = item.getAttributes();
                        Node descItem =
                                attributes
                                        .getNamedItem(DOCUMENTATION_ATTRIBUTE);
                        if (descItem != null) {
                            descItem.setNodeValue(description.getValue());
                        }
                        Node descURLItem =
                                attributes
                                        .getNamedItem(DOCUMENTATION_URL_ATTRIBUTE);
                        if (descURLItem != null) {
                            Object docUrl =
                                    Xpdl2ModelUtil
                                            .getOtherAttribute(description,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_DocumentationURL());
                            if (docUrl != null) {
                                descURLItem.setNodeValue((String) docUrl);
                            }
                        }

                    }
                    portType.setQName(new QName(
                            definition.getTargetNamespace(), localName));
                    portType.updateElement(true);
                }
            }
        }
    }

    /**
     * @param activityObject
     * @return
     */
    public static boolean isSendOneWayActivity(Object activityObject) {
        if (activityObject instanceof Activity) {
            Activity activity = (Activity) activityObject;
            if (activity.getEvent() != null) {
                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(activity);
                if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                        .equals(eventTriggerType)) {
                    String requestActivityIdForReplyActivity =
                            ReplyActivityUtil
                                    .getRequestActivityIdForReplyActivity(activity);

                    if (requestActivityIdForReplyActivity != null) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                TaskType taskType = TaskObjectUtil.getTaskType(activity);
                if (TaskType.SEND_LITERAL.equals(taskType)) {
                    String requestActivityIdForReplyActivity =
                            ReplyActivityUtil
                                    .getRequestActivityIdForReplyActivity(activity);

                    if (requestActivityIdForReplyActivity != null) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * get the binding type literal for a given process or process interface.
     * The WSDL parts are created based on this.
     * 
     * @param procOrProcIfc
     * @return
     */
    public static String getBindingType(Object procOrProcIfc) {
        SoapBindingStyle wsdlBindingType = SoapBindingStyle.RPC_LITERAL;
        if (procOrProcIfc instanceof Process) {
            wsdlBindingType =
                    Xpdl2ModelUtil.getWsdlBindingStyle((Process) procOrProcIfc);
        } else if (procOrProcIfc instanceof ProcessInterface) {
            wsdlBindingType =
                    Xpdl2ModelUtil
                            .getWsdlBindingStyle((ProcessInterface) procOrProcIfc);
        }
        return wsdlBindingType.getLiteral();
    }

    public static boolean isImplicitAssociationDisabled(Object activityOrMethod) {
        if (activityOrMethod instanceof Activity) {
            return ProcessInterfaceUtil
                    .isImplicitAssociationDisabled((Activity) activityOrMethod);
        } else if (activityOrMethod instanceof AssociatedParametersContainer) {
            return ProcessInterfaceUtil
                    .isImplicitAssociationDisabled((AssociatedParametersContainer) activityOrMethod);
        }
        return false;
    }

    /**
     * Check if the Start Message Event has the Reply Immediately flag set.
     * 
     * @param activity
     * @return
     */
    public static boolean isStartMessageWithReplyImmediate(Object activity) {
        if (activity instanceof Activity) {
            TriggerResultMessage message =
                    EventObjectUtil
                            .getTriggerResultMessage((Activity) activity);
            if (message != null) {
                return Xpdl2ModelUtil.getOtherAttributeAsBoolean(message,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ReplyImmediately());
            }
        }

        return false;
    }

    public static List<FormalParameter> getAllFormalParameters(
            Object formalParametersContainer) {
        List<FormalParameter> params = new ArrayList<FormalParameter>();

        if (formalParametersContainer instanceof Process) {
            params.addAll(ProcessInterfaceUtil
                    .getAllFormalParameters((Process) formalParametersContainer));

        } else if (formalParametersContainer instanceof ProcessInterface) {
            params.addAll(((FormalParametersContainer) formalParametersContainer)
                    .getFormalParameters());
        }

        return params;
    }

    /**
     * Get all the DataFields that are in scope of the given Activity.
     * 
     * @param activity
     * @return
     */
    public static List<DataField> getDataFieldsInScope(Object activity) {
        List<DataField> dataFields = new ArrayList<DataField>();

        if (activity instanceof Activity) {
            List<ProcessRelevantData> allData =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity((Activity) activity);

            for (ProcessRelevantData data : allData) {
                if (data instanceof DataField) {
                    dataFields.add((DataField) data);
                }
            }
        }

        return dataFields;
    }

    /**
     * If the name begins with numerics then prefix the name with underscore,
     * else return the name as is
     * 
     * @param name
     * 
     * @return name or name-prefixed-with underscore if it had leading digits.
     */
    public static String _prefixWithUnderscoreIfStartsWithDigit(String name) {
        if (null != name && name.length() > 0) {
            char charAt = name.charAt(0);

            /* if the name starts with number, then prefix with underscore */
            if (Character.isDigit(charAt)) {

                String newName = "_" + name; //$NON-NLS-1$
                return newName;
            }
        }

        return name;
    }
}