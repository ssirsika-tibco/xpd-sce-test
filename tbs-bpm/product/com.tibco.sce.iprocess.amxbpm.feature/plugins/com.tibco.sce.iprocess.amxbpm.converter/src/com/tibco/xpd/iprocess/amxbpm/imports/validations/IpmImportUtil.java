/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.imports.validations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.WSDLElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.customer.api.iprocess.internal.ConversionUtility;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.processeditor.xpdl2.preCommit.AddPortTypeCommand;
import com.tibco.xpd.processeditor.xpdl2.preCommit.OperationNameUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.EndPointTypeType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * [Copied from IPM com.tibco.xpd.ipm plugin]. Contains the Utilities for the
 * IPM XPDL Import.
 * 
 * @author aprasad
 * 
 */
public class IpmImportUtil {

    public static final String DOT = "."; //$NON-NLS-1$

    /**
     * Separator used in status messages.
     */
    private static final String NAME_SEPARATOR = ", "; //$NON-NLS-1$

    public static final String DISP_MSG_INDENTATION = "\t"; //$NON-NLS-1$

    // TODO change it to ID

    public final static String IPROCESS_DESTINATION =
            ProcessDestinationUtil.IPROCESS_GLOBAL_DESTINATION; //$NON-NLS-1$

    /**
     * WSDL Special folder kind.
     */
    public static final String WSDL_SPECIALFOLDER_KIND =
            Activator.WSDL_SPECIALFOLDER_KIND;

    public static Node unescape(String content) {

        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (content != null && content.length() != 0) {
                InputSource is = new InputSource(new StringReader(content));
                doc = builder.parse(is);
            }

            /*
             * XPD-6211 - Alisha/Sid - Make sure we always return a doc root
             * node, even if it is empty because input is "".
             */

            /**
             * XPD-6169 Sid - return from java:getSequenceFlowResultActivities()
             * above had to change to Document.documentElement() now we have
             * moved to Java 1.7. Otherwise you get exception: Run-time internal
             * error in 'Don't know how to convert node type 9 I think that the
             * GregorSamsa stuff in java1.7 no longer recognises Document as a
             * valid w3c dom node type
             * 
             * So need an extra root element to return instead of whole Doc
             * itself.
             * 
             * In order to do that we must create a separate document, add a
             * root element, create a clone of the documentElement of the
             * original document, adopt it into the seondDoc so we can add it to
             * secondDoc/root and then return that.
             * 
             * We jump thru these hoops rather then just returning
             * doc.getDocumentElement() in order to make the return node
             * structure appear to be the same as it always was to the much more
             * XSLT expressions (then we don't have to change and test all of
             * those)>
             */
            Document secondDoc = factory.newDocumentBuilder().newDocument();

            Element root = secondDoc.createElement("root"); //$NON-NLS-1$
            secondDoc.appendChild(root);

            if (doc != null && doc.getDocumentElement() != null) {
                Node docEl = doc.getDocumentElement().cloneNode(true);

                Node adopted = secondDoc.adoptNode(docEl);
                root.appendChild(adopted);
            }

            return secondDoc.getDocumentElement();

        } catch (Exception e) {
            // Log error and carry on
            Logger log = IProcessAMXBPMConverterPlugin.getDefault().getLogger();
            log.error(e);
        }

        if (doc != null) {
            return doc.getDocumentElement();
        }

        return null;
    }

    public static String getOperationName(String projectName,
            String selectedProcess) {
        String name = "_" + selectedProcess; //$NON-NLS-1$
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element instanceof BindingOperation) {
            int dot = selectedProcess.indexOf('.');
            if (dot != -1) {
                name = name.substring(0, dot + 1);
            }
            name = name.replaceAll("/", "_sol_"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return name;
    }

    public static String getPortName(String projectName, String selectedProcess) {
        String name = ""; //$NON-NLS-1$
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element instanceof BindingOperation) {
            Definition definition = element.getEnclosingDefinition();
            WSDLElement container = element.getContainer();
            if (container instanceof Binding) {
                Binding binding = (Binding) container;
                Collection<?> services = definition.getServices().values();
                serviceLoop: for (Object next : services) {
                    if (next instanceof Service) {
                        Service service = (Service) next;
                        Collection<?> ports = service.getPorts().values();
                        for (Object nextPort : ports) {
                            if (nextPort instanceof Port) {
                                Port port = (Port) nextPort;
                                if (port.getBinding().equals(binding)) {
                                    name = port.getName();
                                    break serviceLoop;
                                }
                            }
                        }
                    }
                }
            }
        }
        return name;
    }

    public static String getServiceName(String projectName,
            String selectedProcess) {
        String name = ""; //$NON-NLS-1$
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element instanceof BindingOperation) {
            Definition definition = element.getEnclosingDefinition();
            WSDLElement container = element.getContainer();
            if (container instanceof Binding) {
                Binding binding = (Binding) container;
                Collection<?> services = definition.getServices().values();
                serviceLoop: for (Object next : services) {
                    if (next instanceof Service) {
                        Service service = (Service) next;
                        Collection<?> ports = service.getPorts().values();
                        for (Object nextPort : ports) {
                            if (nextPort instanceof Port) {
                                Port port = (Port) nextPort;
                                if (port.getBinding().equals(binding)) {
                                    name = service.getQName().getLocalPart();
                                    break serviceLoop;
                                }
                            }
                        }
                    }
                }
            }
        }
        return name;
    }

    public static String getPortTypeName(String projectName,
            String selectedProcess) {
        String name = ""; //$NON-NLS-1$
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element instanceof BindingOperation) {
            BindingOperation op = (BindingOperation) element;
            element = op.getEOperation();
        }
        if (element instanceof Operation) {
            WSDLElement container = element.getContainer();
            PortType type = (PortType) container;
            name = type.getQName().getLocalPart();
        }
        return name;
    }

    public static String getPortOperationName(String selectedProcess) {
        String name = "_" + selectedProcess; //$NON-NLS-1$
        int dot = selectedProcess.indexOf('.');
        if (dot != -1) {
            name = name.substring(0, dot + 1);
        }
        name = name.replaceAll("/", "_sol_"); //$NON-NLS-1$ //$NON-NLS-2$
        return name;
    }

    /**
     * NOTE: THIS ONLY WORKS FOR BW Tasks
     * 
     * @param projectName
     * @param selectedProcess
     * @return
     */
    public static String getWsdlLocation(String projectName,
            String selectedProcess) {
        String location = ""; //$NON-NLS-1$
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element != null) {
            IPath path =
                    SpecialFolderUtil.getSpecialFolderRelativePath(element);
            if (path != null) {
                location = path.toString();
            }
        }
        return location;
    }

    /**
     * @param projectName
     * @param operationName
     * @param nameSpace
     * 
     * @return the location of the WSDL file fort the given operation of the
     *         given namespace for a Web Service Task.
     */
    public static String getWSWsdlLocation(String projectName,
            String operationName, String nameSpace) {
        WSDLElement operation = null;

        if (projectName != null && operationName != null && nameSpace != null) {
            IProject project = getProject(projectName);

            if (project != null) {
                Collection<IndexerItem> items =
                        WsdlIndexerUtil
                                .getIndexedItems(operationName,
                                        null,
                                        null,
                                        Collections
                                                .singletonMap(WsdlIndexerAttributes.TARGET_NAMESPACE,
                                                        nameSpace),
                                        true,
                                        true);
                if (items != null && !items.isEmpty()) {
                    Set<IProject> refProjects =
                            ProjectUtil.getReferencedProjectsHierarchy(project,
                                    null);
                    IndexerItem opItem = getOperationItem(project, items);
                    if (opItem == null) {
                        // Search in referenced projects
                        for (IProject ref : refProjects) {
                            opItem = getOperationItem(ref, items);
                            if (opItem != null) {
                                // Found
                                break;
                            }
                        }
                    }

                    if (opItem != null) {
                        EObject eo = WsdlIndexerUtil.resolve(opItem);
                        if (eo instanceof WSDLElement) {
                            operation = (WSDLElement) eo;

                            IPath path =
                                    SpecialFolderUtil
                                            .getSpecialFolderRelativePath(operation);
                            if (path != null) {
                                return path.toString();
                            }
                        }
                    }
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    public static void createWsdlFile(String projectName, String fileName,
            String content) {
        if (projectName != null && fileName != null && content != null
                && content.length() != 0) {
            IProject project = getProject(projectName);
            if (project != null) {
                SpecialFolder specialFolder =
                        SpecialFolderUtil.getSpecialFolderOfKind(project,
                                WSDL_SPECIALFOLDER_KIND);
                if (specialFolder == null) {
                    specialFolder =
                            SpecialFolderUtil
                                    .getCreateSpecialFolderOfKind(project,
                                            WSDL_SPECIALFOLDER_KIND,
                                            "Service Descriptors"); //$NON-NLS-1$
                    if (specialFolder != null) {
                        try {
                            specialFolder.getFolder().setDerived(false);
                        } catch (CoreException e) {
                            IProcessAMXBPMConverterPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            "Failed to create service descriptors special folder for imported wsdl"); //$NON-NLS-1$
                            e.printStackTrace();
                        }
                    }
                }

                if (specialFolder != null) {
                    if (fileName.length() == 0) {
                        fileName =
                                getUniqueWsdlFileName(specialFolder,
                                        projectName);
                    } else {
                        int lastSeparator =
                                Math.max(fileName.lastIndexOf('/'),
                                        fileName.lastIndexOf('\\'));
                        if (lastSeparator != -1) {
                            fileName = fileName.substring(lastSeparator + 1);
                        }
                        int lastDot = fileName.lastIndexOf('.');
                        if (lastDot == 0) {
                            fileName =
                                    getUniqueWsdlFileName(specialFolder,
                                            projectName);
                        } else if (lastDot == -1
                                || !".wsdl".equals(fileName.substring(lastDot))) { //$NON-NLS-1$
                            fileName = fileName += ".wsdl"; //$NON-NLS-1$
                        }
                    }
                    if (fileName != null) {
                        String encoding = getEncoding(content);
                        IFolder folder = specialFolder.getFolder();
                        IFile file = folder.getFile(fileName);
                        if (!file.exists()) {
                            byte[] contentArray;
                            try {
                                contentArray = content.getBytes(encoding);
                            } catch (UnsupportedEncodingException e) {
                                // Try UTF-8 encoding.
                                try {
                                    contentArray = content.getBytes("UTF-8"); //$NON-NLS-1$
                                } catch (UnsupportedEncodingException e1) {
                                    // Fall back on default encoding.
                                    contentArray = content.getBytes();
                                }
                            }
                            InputStream source =
                                    new ByteArrayInputStream(contentArray);
                            try {
                                file.create(source, true, null);
                                // Make sure it's indexed by accessing working
                                // copy.
                                XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(file);
                            } catch (CoreException e) {
                                Logger log =
                                        IProcessAMXBPMConverterPlugin
                                                .getDefault().getLogger();
                                log.error(e);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param xpath
     * @return
     */
    public static String xPathToWsdlPath(String projectName,
            String selectedProcess, String xpath, String direction) {
        String wsdlPath = ""; //$NON-NLS-1$
        WebServiceOperation wso =
                getWebServiceOperation(projectName, selectedProcess);
        Message message =
                "IN".equals(direction) ? getInputMessage(projectName, //$NON-NLS-1$
                        selectedProcess) : getOutputMessage(projectName,
                        selectedProcess);
        if (message != null) {
            if (xpath.startsWith("/")) { //$NON-NLS-1$
                xpath = xpath.substring(1);
            }
            int c = xpath.indexOf(':');
            if (c != -1 && c < xpath.indexOf('/')) {
                xpath = xpath.substring(c + 1);
            }
            IWsdlPath path =
                    WsdlUtil.resolveXPathParameter(wso, message, xpath, true);
            if (path != null) {
                wsdlPath = path.getPath();
            }
        }
        return wsdlPath;
    }

    private static WebServiceOperation getWebServiceOperation(
            String projectName, String selectedProcess) {
        WebServiceOperation wso = null;
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element instanceof BindingOperation) {
            wso = Xpdl2Factory.eINSTANCE.createWebServiceOperation();
            wso.setOperationName(getOperationName(projectName, selectedProcess));
            com.tibco.xpd.xpdl2.Service service =
                    Xpdl2Factory.eINSTANCE.createService();
            wso.setService(service);
            service.setPortName(getPortName(projectName, selectedProcess));
            service.setServiceName(getServiceName(projectName, selectedProcess));
        }
        return wso;
    }

    private static Message getInputMessage(String projectName,
            String selectedProcess) {
        Message message = null;
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element instanceof BindingOperation) {
            BindingOperation op = (BindingOperation) element;
            element = op.getEOperation();
        }
        if (element instanceof Operation) {
            Operation operation = (Operation) element;
            Input input = operation.getInput();
            if (input != null) {
                message = input.getMessage();
            }
        }
        return message;
    }

    private static Message getOutputMessage(String projectName,
            String selectedProcess) {
        Message message = null;
        WSDLElement element =
                getOperation(getProject(projectName), selectedProcess);
        if (element instanceof BindingOperation) {
            BindingOperation op = (BindingOperation) element;
            element = op.getEOperation();
        }
        if (element instanceof Operation) {
            Operation operation = (Operation) element;
            Output output = operation.getOutput();
            if (output != null) {
                message = output.getMessage();
            }
        }
        return message;
    }

    /**
     * @param content
     *            The XML file contents
     * @return The character encoding.
     */
    private static String getEncoding(String content) {
        String encoding = "UTF-8"; //$NON-NLS-1$
        int i = content.indexOf('>');
        if (i != -1) {
            String toCheck = content.substring(0, i);
            int key = toCheck.indexOf("encoding="); //$NON-NLS-1$
            if (key != -1) {
                toCheck = toCheck.substring(key + 9);
                int start =
                        Math.max(toCheck.indexOf('\"'), toCheck.indexOf('\''));
                if (start != -1) {
                    toCheck = toCheck.substring(start + 1);
                    int end =
                            Math.max(toCheck.indexOf('\"'),
                                    toCheck.indexOf('\''));
                    if (end != 0) {
                        encoding = toCheck.substring(0, end);
                    }
                }
            }
        }
        return encoding;
    }

    private static String getUniqueWsdlFileName(SpecialFolder specialFolder,
            String projectName) {
        String fileName = null;
        if (specialFolder != null) {
            IFolder folder = specialFolder.getFolder();
            String baseName = "BwWsdl"; //$NON-NLS-1$
            fileName = baseName + ".wsdl"; //$NON-NLS-1$
            int index = 0;
            while (folder.getFile(fileName).exists()) {
                index++;
                fileName = baseName + index + ".wsdl"; //$NON-NLS-1$
            }
        }
        return fileName;
    }

    private static IProject getProject(String projectName) {
        return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    }

    /**
     * NOTE: THIS ONLY WORKS FOR BW Tasks
     * 
     * @param project
     * @param selectedProcess
     * @return
     */
    public static WSDLElement getOperation(IProject project,
            String selectedProcess) {
        WSDLElement operation = null;
        if (project != null && selectedProcess != null) {
            String operationName = getPortOperationName(selectedProcess);
            Collection<IndexerItem> items =
                    WsdlIndexerUtil.getIndexedItems(operationName,
                            null,
                            null,
                            null,
                            true,
                            true);
            if (items != null && !items.isEmpty()) {
                Set<IProject> refProjects =
                        ProjectUtil.getReferencedProjectsHierarchy(project,
                                null);
                IndexerItem opItem = getOperationItem(project, items);
                if (opItem == null) {
                    // Search in referenced projects
                    for (IProject ref : refProjects) {
                        opItem = getOperationItem(ref, items);
                        if (opItem != null) {
                            // Found
                            break;
                        }
                    }
                }

                if (opItem != null) {
                    EObject eo = WsdlIndexerUtil.resolve(opItem);
                    if (eo instanceof WSDLElement) {
                        operation = (WSDLElement) eo;
                    }
                }
            }
        }
        return operation;

    }

    /**
     * Get the Operation indexer item from the collection that belongs to the
     * given project. This will return a bound operation if found, if not then
     * port type operations will be searched.
     * 
     * @param project
     * @param opItems
     * @return
     */
    private static IndexerItem getOperationItem(IProject project,
            Collection<IndexerItem> opItems) {
        IndexerItem opItem = null;
        if (project != null && opItems != null) {
            if (opItems != null) {
                // Find the Operation with the given name in this project
                for (IndexerItem item : opItems) {
                    WsdlElementType type = WsdlElementType.getType(item);
                    if (WsdlElementType.SERVICE_OPERATION == type) {
                        // Make sure that this is from the current project
                        if (project.getName()
                                .equals(WsdlIndexerUtil.getProjectName(item))) {
                            opItem = item;
                            break;
                        }
                    } else if (WsdlElementType.PORTTYPE_OPERATION == type) {
                        /*
                         * If this is a port type operation then record and keep
                         * searching in case there is a bound operation with
                         * this name (as this takes precedence) Make sure that
                         * this is from the current project
                         */
                        if (project.getName()
                                .equals(WsdlIndexerUtil.getProjectName(item))) {
                            opItem = item;
                        }
                    }
                }
            }

        }
        return opItem;
    }

    public static String toUpper(String str) {
        return str.toUpperCase();
    }

    /**
     * @return Unique identifier generated by EMF because the ones generated by
     *         XSLT aren't as unique as expected.
     */
    public static String generateUUId() {
        return EcoreUtil.generateUUID();
    }

    /**
     * Get the EAI database definition item from the given first line.
     * <p>
     * Copes with the 2 different formats employed by iProcess : the newer EAIDB
     * (which has stored proc owner in separate ^ field in the ^ separated line
     * : and the older (EAIORA etc) that has the owner (if defined) inside the
     * stored procedure field ("." separated) on that line.
     * 
     * @param eaiDbFirstLine
     * @param itemToGet
     *            "Server" | "Database" | "Owner" | "StoredProc"
     * 
     * @return the value for the requested field.
     */
    public static String getEAIDBDefinitionItem(String eaiDbFirstLine,
            String itemToGet) {
        if (eaiDbFirstLine != null) {
            String[] fields = eaiDbFirstLine.split("\\^"); //$NON-NLS-1$
            if (fields != null) {
                if (fields.length == 4) {
                    /*
                     * Old format: "Server^Database^[owner.]StoredProc^NumParams
                     */
                    if ("Server".equalsIgnoreCase(itemToGet)) { //$NON-NLS-1$
                        return fields[0];
                    } else if ("Database".equalsIgnoreCase(itemToGet)) { //$NON-NLS-1$
                        return fields[1];
                    } else if ("Owner".equalsIgnoreCase(itemToGet)) { //$NON-NLS-1$
                        String[] ownerAndProc = fields[2].split("\\."); //$NON-NLS-1$
                        if (ownerAndProc.length == 2) {
                            return ownerAndProc[0];
                        }
                        return ""; //$NON-NLS-1$
                    } else if ("StoredProc".equals(itemToGet)) { //$NON-NLS-1$
                        String[] ownerAndProc = fields[2].split("\\."); //$NON-NLS-1$
                        if (ownerAndProc.length == 2) {
                            return ownerAndProc[1];
                        }
                        return ownerAndProc[0];
                    }

                } else if (fields.length > 4) {
                    /*
                     * New format: "Server^Database^Owner^StoredProc^NumParams
                     */
                    if ("Server".equalsIgnoreCase(itemToGet)) { //$NON-NLS-1$
                        return fields[0];
                    } else if ("Database".equalsIgnoreCase(itemToGet)) { //$NON-NLS-1$
                        return fields[1];
                    } else if ("Owner".equalsIgnoreCase(itemToGet)) { //$NON-NLS-1$
                        return fields[2];
                    } else if ("StoredProc".equals(itemToGet)) { //$NON-NLS-1$
                        return fields[3];
                    }
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Checks if given eObject has BPM destination enabled.
     * 
     * @param processOrInterface
     * @return true if given eObject has BPM destination enabled
     */
    public static boolean containsBPMDestination(EObject processOrInterface) {
        /*
         * Implementation moved to CustomerAPI feature under ConversionUtility
         * class to be shared.
         */
        return ConversionUtility.containsBPMDestination(processOrInterface);
    }

    // Moved from IProcessXpdlvalidator

    /**
     * Checks whether the BPM process with given procName exists in the
     * workspace. Since the indexer can't check whether procName is a PROCESS OR
     * PROCESSINTERFACE in one go, had to call this twice.
     * 
     * @param procName
     *            , name of process/process interface to check for.
     * @param procOrProcIfc
     *            , check for process/ OR interface valid values 'PROCESS' or
     *            'PROCESSINTERFACE'
     * @return true, if a BPM destination process with given name exists in the
     *         project.
     */
    public static EObject getBPMDestinationProcessOrIFCInWorkspace(
            String procName, String procOrProcIfc) {
        /*
         * Implementation moved to CustomerAPI feature under ConversionUtility
         * class to be shared.
         */
        return ConversionUtility
                .getBPMDestinationProcessOrIFCInWorkspace(procName,
                        procOrProcIfc);
    }

    /**
     * XPD-6426: Gets the BPM destination enabled {@link Process} or
     * {@link ProcessInterface} present in the workspace, if no {@link Process}
     * or {@link ProcessInterface} is present with the passed id then returns
     * <code>null</code>
     * 
     * @param id
     *            the id of {@link Process} or {@link ProcessInterface} which is
     *            to be fetched in workspace.
     * @return the BPM destination enabled {@link Process} or
     *         {@link ProcessInterface} present in the workspace, if no
     *         {@link Process} or {@link ProcessInterface} is present with the
     *         passed id then returns <code>null</code>
     */
    public static EObject getBpmProcOrIfcInWorkspaceById(String id) {

        List<EObject> processOrInterfaceInWorkspace = new ArrayList<EObject>();

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, id);
        /*
         * First search if we find any Processes in the workspace with the
         * passed id
         */
        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessResourceItemType.PROCESS.toString(), null,
                        additionalAttributes);
        processOrInterfaceInWorkspace =
                ProcessUIUtil
                        .getIndexedElements(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                criteria);

        if (processOrInterfaceInWorkspace == null
                || processOrInterfaceInWorkspace.isEmpty()) {
            /*
             * If there are no processes in the workspace with the passed id
             * then check for process interfaces.
             */
            criteria =
                    new IndexerItemImpl(
                            null,
                            ProcessResourceItemType.PROCESSINTERFACE.toString(),
                            null, additionalAttributes);
            processOrInterfaceInWorkspace =
                    ProcessUIUtil
                            .getIndexedElements(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                    criteria);
        }

        for (EObject procOrIfc : processOrInterfaceInWorkspace) {
            if (ConversionUtility.containsBPMDestination(procOrIfc)) {
                /*
                 * If any of the process or process interface has BPM
                 * destination enabled then return immediately.
                 */
                return procOrIfc;
            }
        }

        return null;
    }

    /**
     * XPD-6375: Updates the references of the activities which are inherited
     * from process interface.
     * <p>
     * Checks if an activity is inherited from Process interface, if yes then
     * finds the same activity in process interface by name, gets its id and
     * sets it as the implements id.
     * 
     * @param process
     *            the process which implements the passed process interface. The
     *            implements id of all the activities of this process will be
     *            fixed.
     * @param processInterface
     *            the process interface that the passed process implements
     */
    public static void updateImplementedActivityRefernces(Process process,
            EObject processInterface) {

        if (processInterface instanceof ProcessInterface) {

            ProcessInterface ifc = (ProcessInterface) processInterface;

            /*
             * Collect all start and intermediate events which are present in
             * the process interface.
             */
            List<NamedElement> allProcessInterfaceMethods =
                    new ArrayList<NamedElement>();
            allProcessInterfaceMethods.addAll(ifc.getStartMethods());
            allProcessInterfaceMethods.addAll(ifc.getIntermediateMethods());

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity activity : allActivitiesInProc) {
                /*
                 * check if the activity implements an activity from the process
                 * interface
                 */
                Object obj =
                        Xpdl2ModelUtil.getOtherAttribute(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Implements());

                if (obj != null) {
                    /*
                     * Find the implemented process interface activity by name
                     */
                    EObject findInList =
                            EMFSearchUtil
                                    .findInList(allProcessInterfaceMethods,
                                            Xpdl2Package.eINSTANCE
                                                    .getNamedElement_Name(),
                                            activity.getName());

                    if (findInList != null) {
                        /*
                         * set the process activity implements id = process
                         * interface activity id
                         */
                        Xpdl2ModelUtil.setOtherAttribute(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Implements(),
                                ((NamedElement) findInList).getId());
                    }
                }
            }
        }
    }

    /**
     * Appends the value to the master string with 4 values in a row, inserting
     * a line break after every 4 values.
     * 
     * @param counter
     *            , counter to keep track of values in the current line.
     * @param masterString
     *            , Main stting to which the value is to be appended.
     * @param valueToAppend
     *            , value to be appended to the main string.
     * @return reset value of the counter , either a value < 4 for current line,
     *         or 0 for new line.
     */
    public static int appendValue(int counter, StringBuffer masterString,
            String valueToAppend) {

        if (counter == 0) {
            // Indent message
            masterString.append(DISP_MSG_INDENTATION);
            masterString.append(valueToAppend);
        } else {
            masterString.append(NAME_SEPARATOR + valueToAppend);
        }
        counter++;
        // reset counter and add line separator
        if (counter == 4) {
            counter = 0;
            masterString
                    .append(IProcessAMXBPMConverterPlugin.SYSTEM_LINE_SEPARATOR);
        }
        return counter;
    }

    /**
     * Associates the given FIX-ME Artifact with the activity and applies FIX-ME
     * specific Node Graphic info.
     * 
     * @param newActivity
     * @param fixMeArtifact
     */
    public static void associateFixMe(Activity activity,
            com.tibco.xpd.xpdl2.Package currPackage, Artifact fixMeArtifact) {
        /*
         * Associate annotation to the activity
         */
        Association newAssociation1 =
                Xpdl2Factory.eINSTANCE.createAssociation();

        newAssociation1.setSource(fixMeArtifact.getId());
        newAssociation1.setTarget(activity.getId());

        ConnectorGraphicsInfo cGInfo =
                Xpdl2Factory.eINSTANCE.createConnectorGraphicsInfo();

        cGInfo.setBorderColor(ProcessWidgetColors.getInstance()
                .getGraphicalNodeColor(null, ProcessWidgetColors.NOTE_LINE)
                .toString());

        cGInfo.setToolId(Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + DOT
                + Xpdl2ModelUtil.CONNECTION_INFO_IDSUFFIX);

        newAssociation1.getConnectorGraphicsInfos().add(cGInfo);

        // add Association to the Package
        currPackage.getAssociations().add(newAssociation1);

        /*
         * XPD-6450: Saket: Highlighting the activities associated to the
         * annotation artifact by making them red.
         */
        Xpdl2ModelUtil.getNodeGraphicsInfo(activity).setFillColor("255,0,0"); //$NON-NLS-1$

    }

    /**
     * Add a <code>FIX-ME</code>> {@link Annotation} stating that the customer
     * should decide how to trigger the continuation of the flow from this event
     * location.
     * 
     * @param activity
     * @param newActivity
     */
    public static void addFixMeToActivity(Activity activity, String fixMeText) {

        /*
         * Create a new text annotation on the transition.
         */
        com.tibco.xpd.xpdl2.Artifact newArtifact =
                Xpdl2Factory.eINSTANCE.createArtifact();

        newArtifact.setArtifactType(ArtifactType.ANNOTATION_LITERAL);

        newArtifact.setTextAnnotation(String.format(fixMeText,
                activity.getName()));

        NodeGraphicsInfo nGInfo =
                EcoreUtil.copy(Xpdl2ModelUtil.getNodeGraphicsInfo(activity));

        if (null != nGInfo) {

            addNodeGraphicsInfoToFixMeArtifact(newArtifact,
                    nGInfo.getLaneId(),
                    nGInfo.getCoordinates().getXCoordinate()
                            + nGInfo.getWidth(),
                    nGInfo.getCoordinates().getYCoordinate()
                            - nGInfo.getHeight());

        }

        /*
         * Fetch the package of the current process in order to add annotation
         * artifact and its associations.
         */
        com.tibco.xpd.xpdl2.Package currentPackage =
                activity.getProcess().getPackage();

        /*
         * Add the artifact to the package.
         */
        currentPackage.getArtifacts().add(newArtifact);

        associateFixMe(activity, currentPackage, newArtifact);
    }

    /**
     * Adds node graphics information for the specified activity (passed as a
     * node) according to the specified co-ordinates and lane ID.
     * 
     * @param node
     * @param laneId
     * @param xCoOrd
     * @param yCoOrd
     */
    public static void addNodeGraphicsInfoToFixMeArtifact(GraphicalNode node,
            String laneId, double xCoOrd, double yCoOrd) {

        NodeGraphicsInfo nGInfo =
                Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo();

        Coordinates coordinates =
                Xpdl2Factory.eINSTANCE.createCoordinates(xCoOrd, yCoOrd);

        nGInfo.setLaneId(laneId);
        nGInfo.setCoordinates(coordinates);
        nGInfo.setHeight(49);
        nGInfo.setWidth(273);

        nGInfo.setBorderColor(ProcessWidgetColors.getInstance()
                .getGraphicalNodeColor(null, ProcessWidgetColors.NOTE_LINE)
                .toString());

        node.getNodeGraphicsInfos().add(nGInfo);
    }

    /**
     * Creates the WebServiceOperation element for the given business process
     * 
     * @param process
     *            , {@link Process} to create {@link WebServiceOperation} for.
     * @param taskOrEventActivity
     * 
     * @return webServiceOp the new created {@link WebServiceOperation}.
     */
    public static WebServiceOperation createWebServiceOperation(
            com.tibco.xpd.xpdl2.Process process, Activity taskOrEventActivity) {

        WebServiceOperation webServiceOp =
                Xpdl2Factory.eINSTANCE.createWebServiceOperation();

        webServiceOp
                .getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_SecurityProfile(),
                        ""); //$NON-NLS-1$

        webServiceOp.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Transport(),
                        AddPortTypeCommand.SERVICE_VIRTUALIZATION_URL);

        webServiceOp.getOtherAttributes()
                .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(), ""); //$NON-NLS-1$

        com.tibco.xpd.xpdl2.Service service =
                Xpdl2Factory.eINSTANCE.createService();

        Xpdl2ModelUtil.setOtherAttribute(service,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsRemote(),
                true);

        EndPoint endPoint = Xpdl2Factory.eINSTANCE.createEndPoint();
        endPoint.setEndPointType(EndPointTypeType.WSDL_LITERAL);

        /*
         * Note: As the process is not yet associated with an xpdl/project it is
         * not possible to create correct wsdl file ref. The wsdl file name is
         * derived from the xpdl file name, xpdl file in this case is saved as
         * processname.xpdl.Given the fact that the if an xpdl with same name
         * already exist it might also get saved as processName_counter.xpdl. At
         * this point ,we will base the wsdl name on xpdl processname.xpdl, and
         * let it be fixed at file save stage if required.
         */
        ExternalReference wsdlRef =
                Xpdl2Factory.eINSTANCE.createExternalReference();

        wsdlRef.setLocation(Xpdl2ModelUtil.getWsdlFileName(String
                .format("%1s.xpdl", process.getName()))); //$NON-NLS-1$

        endPoint.setExternalReference(wsdlRef);

        service.setEndPoint(endPoint);
        webServiceOp.setService(service);

        /*
         * Assign Default Participant Check whether there is a participant
         * already - create if not.
         */
        Participant endPointPartic =
                Xpdl2ModelUtil.getProcessApiActivityParticipant(process);

        if (endPointPartic == null) {

            endPointPartic =
                    AddPortTypeCommand.createNewApiEndpointParticipant(process);
            /* Add to parent Process Package */
            if (!process.getPackage().getParticipants()
                    .contains(endPointPartic)) {
                process.getPackage().getParticipants().add(endPointPartic);
            }

            Xpdl2ModelUtil.setOtherAttribute(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ApiEndPointParticipant(),
                    endPointPartic.getId());
        }

        /*
         * Set participant to activity performer.
         */
        List<EObject> perfs = new ArrayList<EObject>();
        perfs.add(endPointPartic);

        Performers perfsSeqParent = Xpdl2Factory.eINSTANCE.createPerformers();

        taskOrEventActivity.setPerformers(perfsSeqParent);

        Performer newPerf = Xpdl2Factory.eINSTANCE.createPerformer();
        newPerf.setValue(endPointPartic.getId());

        perfsSeqParent.getPerformers().add(newPerf);

        /*
         * Set Participant in endpoint alias
         */
        Xpdl2ModelUtil.setOtherAttribute(webServiceOp,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                endPointPartic.getId());
        return webServiceOp;
    }

    /**
     * Creates the PortTypeOperation element for the given activity configured
     * to generate its own web-service operation.
     * 
     * @param process
     *            parent {@link Process}.
     * @param activity
     *            , {@link Activity} to create {@link PortTypeOperation} for.
     * 
     * @return portTypeOp, new {@link PortTypeOperation} for the given
     *         {@link Activity}.
     */
    public static PortTypeOperation createPortTypeOperation(
            com.tibco.xpd.xpdl2.Process process, Activity activity) {

        PortTypeOperation portTypeOp =
                XpdExtensionFactory.eINSTANCE.createPortTypeOperation();

        /*
         * XPD-6503: Saket: If a process name has leading digit(s) then prefix
         * with underscore
         */
        String processName = process.getName();
        if (Character.isDigit(processName.charAt(0))) {

            processName = "_" + processName; //$NON-NLS-1$

        }

        portTypeOp.setPortTypeName(processName);

        String activityName = activity.getName();

        if (activityName != null && activityName.trim().length() > 0) {
            portTypeOp.setOperationName(activityName);
        } else {
            activityName = OperationNameUtil.getSubstitutedName(activity);
            portTypeOp.setOperationName(activityName);
        }

        ExternalReference wsdlRef =
                Xpdl2Factory.eINSTANCE.createExternalReference();

        wsdlRef.setLocation(Xpdl2ModelUtil.getWsdlFileName(String
                .format("%1s.xpdl", process.getName())));

        portTypeOp.setExternalReference(wsdlRef);

        return portTypeOp;
    }

    /**
     * Checks if the {@link FormalParameter} has unresolved reference.
     * 
     * @param param
     *            {@link FormalParameter} to check.
     * @return true if given {@link FormalParameter} has unresolved reference.
     */
    public static boolean isParamUnresolvedExternalReference(
            FormalParameter param) {

        DataType dataType = param.getDataType();

        if (dataType instanceof ExternalReference) {

            ExternalReference externalReference = (ExternalReference) dataType;

            return ProcessUIUtil
                    .isUnresolvedExternalReference(externalReference);
        }
        return false;
    }

    /**
     * Creates Mapping for the {@link FormalParameter}.
     * 
     * @param namedElement
     *            NamedElement to create Mapping for.
     * @param List
     *            of {@link FormalParameter} to create mapping.
     * 
     */
    public static List<DataMapping> createMappingsForFormalParams(
            NamedElement namedElement, List<FormalParameter> formalParams) {

        List<DataMapping> dataMappings = new ArrayList<DataMapping>();
        for (FormalParameter param : formalParams) {

            if (isParamUnresolvedExternalReference(param)) {
                continue;
            }

            DataMapping dataMapping =
                    IpmImportUtil.createInMapping(param.getName(),
                            param.getMode());

            if (dataMapping != null) {
                dataMappings.add(dataMapping);
            }
        }

        return dataMappings;
    }

    /**
     * Create IN Direction Mapping for the {@link FormalParameter} .
     * 
     * @param formalParam
     *            {@link FormalParameter} to create {@link DataMapping} for.
     * @param modeType
     *            FormalParameter Association ModeType.
     * @return {@link DataMapping} created for the {@link FormalParameter}.
     */
    public static DataMapping createInMapping(String formalParamName,
            ModeType modeType) {

        if (ModeType.IN_LITERAL.equals(modeType)
                || ModeType.INOUT_LITERAL.equals(modeType)) {

            DataMapping dataMapping =
                    Xpdl2Factory.eINSTANCE.createDataMapping();

            String formalString =
                    NameUtil.getInternalName(formalParamName, false);

            dataMapping.setFormal(formalString);
            Expression expression =
                    Xpdl2ModelUtil.createExpression(formalParamName);
            expression.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
            dataMapping.setActual(expression);
            dataMapping.setDirection(DirectionType.OUT_LITERAL);
            return dataMapping;
        }

        return null;
    }

    /**
     * Creates mapping for the Intermediate Message Event Handler.
     * 
     * @param activity
     *            , Intermediate Message Event Handler {@link Activity}.
     * @param process
     *            , parent {@link Process}.
     */
    public static List<DataMapping> createMappingsForActivity(
            Activity activity, Process process) {

        List<DataMapping> dataMappings = Collections.EMPTY_LIST;

        if (activity != null) {
            /*
             * No explicitly associated data, handle implicit association...
             */
            List<AssociatedParameter> associatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(activity);
            if (!(associatedParameters.isEmpty())) {
                dataMappings = new ArrayList<DataMapping>();

                for (AssociatedParameter associatedParameter : associatedParameters) {

                    ModeType modeType =
                            ProcessInterfaceUtil
                                    .getAssocParamModeType(associatedParameter);

                    DataMapping dataMapping =
                            createInMapping(associatedParameter.getFormalParam(),
                                    modeType);
                    if (dataMapping != null) {
                        dataMappings.add(dataMapping);
                    }
                }

            } else {
                List<FormalParameter> formalParams = Collections.EMPTY_LIST;

                /*
                 * Sid XPD-2087: Only return all data implicitly if implicit
                 * association has not been disabled.
                 */
                if (!ProcessInterfaceUtil
                        .isImplicitAssociationDisabled(activity)) {
                    formalParams =
                            ProcessInterfaceUtil
                                    .getAllFormalParameters(process);
                }

                dataMappings =
                        createMappingsForFormalParams(activity, formalParams);
            }
        }
        return dataMappings;
    }

    /**
     * Adds Web Service Operation to the Intermediate Message event handler
     * Activity.
     * 
     * @param taskOrEventActivity
     *            , Intermediate Message Event Handler Activity
     * @param process
     *            Parent {@link Process}.
     */
    public static void addWebServiceOperationSetToGenerateWSDL(
            Activity taskOrEventActivity, Process process) {

        OtherElementsContainer otherElementsContainer = null;

        OtherAttributesContainer otherAttribContainer = null;

        com.tibco.xpd.xpdl2.Message message = null;

        /* Web Service operation */
        WebServiceOperation webServiceOperation =
                createWebServiceOperation(process, taskOrEventActivity);

        /* Port Type operation */
        PortTypeOperation portTypeOp =
                createPortTypeOperation(process, taskOrEventActivity);

        Event event = taskOrEventActivity.getEvent();

        Implementation impl = taskOrEventActivity.getImplementation();

        if (event != null) {

            TriggerResultMessage trm = null;
            /* Set Implementation */
            if (event instanceof StartEvent) {

                ((StartEvent) event)
                        .setImplementation(ImplementationType.WEB_SERVICE_LITERAL);

                trm = ((StartEvent) event).getTriggerResultMessage();

            } else if (event instanceof IntermediateEvent) {

                ((IntermediateEvent) event)
                        .setImplementation(ImplementationType.WEB_SERVICE_LITERAL);

                trm = ((IntermediateEvent) event).getTriggerResultMessage();

            }

            if (trm != null) {

                otherAttribContainer = trm;
                otherElementsContainer = trm;
                trm.setWebServiceOperation(webServiceOperation);
                message = trm.getMessage();

            }
        } else if (impl != null) {

            Task task = (Task) impl;
            /* Handle receive task */
            TaskReceive taskReceive = task.getTaskReceive();

            if (taskReceive != null) {
                taskReceive
                        .setImplementation(ImplementationType.WEB_SERVICE_LITERAL);

                otherAttribContainer = taskReceive;
                otherElementsContainer = taskReceive;
                taskReceive.setWebServiceOperation(webServiceOperation);
                message = taskReceive.getMessage();

            }

        }

        if (otherElementsContainer != null && otherAttribContainer != null) {

            Xpdl2ModelUtil.setOtherElement(otherElementsContainer,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_PortTypeOperation(),
                    portTypeOp);

            /* Set generated */
            Xpdl2ModelUtil.setOtherAttribute(otherAttribContainer,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Generated(),
                    Boolean.TRUE);

            Xpdl2ModelUtil.setOtherAttribute(otherAttribContainer,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementationType(),
                    TaskImplementationTypeDefinitions.WEB_SERVICE);

            /* Update Mappings */
            if (message != null) {

                List<DataMapping> dataMappings =
                        createMappingsForActivity(taskOrEventActivity, process);

                if (dataMappings != null) {
                    message.eSet(Xpdl2Package.eINSTANCE
                            .getMessage_DataMappings(), dataMappings);
                }
            }
        }

    }
}
