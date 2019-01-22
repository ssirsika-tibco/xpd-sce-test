/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public final class WsdlPathFactory {

    private static final String URI_SEPARATOR = "/"; //$NON-NLS-1$

    /**
     * Private constructor.
     */
    private WsdlPathFactory() {
    }

    /**
     * @param xPathesqPath
     *            XPath-like string (as used for mappings on activities)
     * @param prefixNsMapping
     *            Old namespace prefixes represented as keys. New namespace
     *            prefixes represented as values.
     * @return Updated version of an XPath-like string where its namespace
     *         prefixes have been replaced with updated versions as specified in
     *         <code>prefixNsMapping</code>
     */
    public static String updateNamespacesOnXPathesqPaths(String xPathesqPath,
            Map<String, String> prefixNsMapping) {

        StringBuilder outputPath = new StringBuilder();

        String[] path = xPathesqPath.split(URI_SEPARATOR);

        for (int i = 0; i < path.length; i++) {
            String element = path[i];

            if (i > 0) {
                outputPath.append(URI_SEPARATOR);
            }

            /*
             * Certain leader parts of our mapping path can just be output.
             * Basically ALL paths should start with "wso:/part:name" or
             * "wso:/fault:name/part:name".
             * 
             * So we'll just output these elements direct when we find them at
             * start of string.
             */
            if (i == 0 && element.startsWith(WsdlPartPath.WSO_ID)) {
                outputPath.append(element);

                if ((i + 1) < path.length
                        && path[i + 1].startsWith(WsdlPartPath.FAULT_ID)) {
                    i++;
                    outputPath.append(URI_SEPARATOR);
                    outputPath.append(path[i]);
                }

                if ((i + 1) < path.length
                        && path[i + 1].startsWith(WsdlPartPath.PART_ID)) {
                    i++;
                    outputPath.append(URI_SEPARATOR);
                    outputPath.append(path[i]);
                }

            } else if (element.startsWith(XsdPath.GROUP_SEQUENCE)
                    || element.startsWith(XsdPath.GROUP_CHOICE)
                    || element.startsWith(XsdPath.XSD_ANY)) {
                /*
                 * Pass thru any of the other special representations of xsd
                 * constructs.
                 */
                outputPath.append(element);

            } else {
                /*
                 * Now replace the prefix if there is one.
                 */
                String[] qname = element.split(":"); //$NON-NLS-1$
                if (qname.length > 1) {
                    /* Switch prefix to new one. */
                    String newPrefix = prefixNsMapping.get(qname[0]);
                    if (newPrefix == null) {
                        newPrefix = qname[0] + "_unknown"; //$NON-NLS-1$
                    }

                    outputPath.append(newPrefix + ":" + qname[1]); //$NON-NLS-1$

                } else {
                    /* No prefix. */
                    outputPath.append(element);
                }
            }
        }

        return outputPath.toString();
    }

    /**
     * Constructs a WSDL path object from a path string.
     * 
     * @param wso
     *            The web service operation to create the path for.
     * @param path
     *            The path string
     * @param in
     *            true if in, false if out.
     * @return The WSDL path object.
     */
    public static IWsdlPath createWsdlPath(WebServiceOperation wso,
            String path, boolean in) {

        IWsdlPath wsdlPath = null;
        StringTokenizer tokenizer = new StringTokenizer(path, "/"); //$NON-NLS-1$
        if (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.startsWith(WsdlPartPath.WSO_ID)) {
                if (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();

                    Part part = null;
                    Fault fault = null;

                    //
                    // Allow for optional fault:faultName
                    //
                    if (token.startsWith(WsdlPartPath.FAULT_ID)) {
                        String faultName =
                                token.substring(WsdlPartPath.FAULT_ID.length());

                        token = tokenizer.nextToken();
                        if (token.startsWith(WsdlPartPath.PART_ID)) {
                            String partName =
                                    token.substring(WsdlPartPath.PART_ID
                                            .length());

                            fault = getFault(wso, partName, faultName);
                            if (fault != null) {
                                part = getPartFromFault(partName, fault);
                            }
                        }

                    } else if (token.startsWith(WsdlPartPath.PART_ID)) {
                        //
                        // Not from a operation fault message
                        String partName =
                                token.substring(WsdlPartPath.PART_ID.length());
                        part = getPart(wso, partName, in);
                    }

                    if (part != null) {
                        WsdlPartPath partPath;
                        if (fault instanceof org.eclipse.wst.wsdl.Fault) {
                            partPath =
                                    new WsdlPartPath(wso,
                                            (org.eclipse.wst.wsdl.Fault) fault,
                                            part);

                        } else {
                            partPath = new WsdlPartPath(wso, part, in, !in);
                        }

                        if (tokenizer.hasMoreTokens()) {
                            token = tokenizer.nextToken();
                            XsdPath xsdPath = getRootPath(partPath, token);
                            while (xsdPath != null && tokenizer.hasMoreTokens()) {
                                token = tokenizer.nextToken();
                                xsdPath = getPath(xsdPath, token);
                            }
                            wsdlPath = xsdPath;
                        } else {
                            wsdlPath = partPath;
                        }
                    } else {
                        EObject obj = getActivity(wso);
                        if (obj instanceof Activity) {
                            Activity activity = (Activity) obj;
                            boolean generatedRequestActivity =
                                    Xpdl2ModelUtil
                                            .isGeneratedRequestActivity(activity);
                            if (!generatedRequestActivity) {
                                if (ReplyActivityUtil.isReplyActivity(activity)) {
                                    Activity requestActivityForReplyActivity =
                                            ReplyActivityUtil
                                                    .getRequestActivityForReplyActivity(activity);
                                    if (requestActivityForReplyActivity != null) {
                                        generatedRequestActivity =
                                                Xpdl2ModelUtil
                                                        .isGeneratedRequestActivity(requestActivityForReplyActivity);
                                    }

                                }

                            }
                            if (generatedRequestActivity) {
                                String partName =
                                        token.substring(WsdlPartPath.PART_ID
                                                .length());
                                ProcessRelevantData parameter =
                                        ConceptUtil.resolveParameter(activity,
                                                partName);
                                if (parameter instanceof FormalParameter) {
                                    part =
                                            PartProxy
                                                    .getPart((FormalParameter) parameter);
                                    WsdlPartPath partPath =
                                            new WsdlPartPath(wso, part, in, !in);
                                    wsdlPath = partPath;
                                }
                            }

                        }
                    }
                }
            }
        }
        return wsdlPath;
    }

    /**
     * @param wso
     * @return
     */
    private static EObject getActivity(WebServiceOperation wso) {
        EObject containerObject = wso.eContainer();

        while (containerObject != null
                && !(containerObject instanceof Activity)) {
            containerObject = containerObject.eContainer();
        }

        return containerObject;
    }

    /**
     * @param wso
     * @param partName
     * @param faultName
     * 
     * @return the part from the message in the given WSDL operaiton fault.
     */
    public static Fault getFault(WebServiceOperation wso, String partName,
            String faultName) {
        if (wso != null && partName != null && faultName != null) {
            Operation op = getWsdlOperation(wso);

            if (op != null) {
                return op.getFault(faultName);

            }
        }
        return null;
    }

    /**
     * @param partName
     * @param fault
     */
    public static Part getPartFromFault(String partName, Fault fault) {
        if (fault != null && fault.getMessage() != null) {
            javax.wsdl.Part p = fault.getMessage().getPart(partName);
            if (p instanceof Part) {
                return (Part) p;
            }
        }
        return null;
    }

    /**
     * @param wso
     *            The web service operation.
     * @param partName
     *            The Part name.
     * @param in
     *            true if in, false if out.
     * @return The Part matching the name.
     */
    public static Part getPart(WebServiceOperation wso, String partName,
            boolean in) {
        Part part = null;

        if (wso != null && partName != null) {
            Operation op = getWsdlOperation(wso);

            Collection<?> parts = new ArrayList<Object>();
            if (op != null) {
                if (in) {
                    Input input = op.getInput();
                    if (input != null) {
                        part =
                                getWsdlPartFromMessage(input.getMessage(),
                                        partName);
                    }
                } else {
                    Output output = op.getOutput();
                    if (output != null) {
                        part =
                                getWsdlPartFromMessage(output.getMessage(),
                                        partName);
                    }
                }
            }
        }
        return part;
    }

    /**
     * @param portTypeOp
     *            The web service operation.
     * @param partName
     *            The Part name.
     * @param in
     *            true if in, false if out.
     * @return The Part matching the name.
     */
    public static Part getPart(PortTypeOperation portTypeOp, String partName,
            boolean in) {
        Part part = null;

        if (portTypeOp != null && partName != null) {
            Operation op = getPortTypeOperation(portTypeOp);

            Collection<?> parts = new ArrayList<Object>();
            if (op != null) {
                if (in) {
                    Input input = op.getInput();
                    if (input != null) {
                        part =
                                getWsdlPartFromMessage(input.getMessage(),
                                        partName);
                    }
                } else {
                    Output output = op.getOutput();
                    if (output != null) {
                        part =
                                getWsdlPartFromMessage(output.getMessage(),
                                        partName);
                    }
                }
            }
        }
        return part;
    }

    /**
     * @param message
     * @param partName
     * @return the given Wsdl Part from the given Wsdl message.
     */
    public static Part getWsdlPartFromMessage(Message message, String partName) {
        Part part = null;

        if (message != null) {

            Map<?, ?> partsMap = message.getParts();
            if (partsMap != null) {
                Collection<?> parts = partsMap.values();

                for (Object next : parts) {
                    if (next instanceof Part) {
                        Part nextPart = (Part) next;
                        if (partName.equals(nextPart.getName())) {
                            part = nextPart;
                        }
                    }
                }

                if (part == null) {
                    for (Object next : parts) {
                        if (next instanceof Part) {
                            Part nextPart = (Part) next;
                            if (nextPart.getElementName() != null) {
                                if (partName.equals(nextPart.getElementName()
                                        .getLocalPart())) {
                                    part = nextPart;
                                }
                            }
                        }
                    }
                }
            }
        }
        return part;
    }

    private static Operation getWsdlOperation(WebServiceOperation wso) {
        String operation = wso.getOperationName();
        String portType = null; // wso.getPortTypeName();
        String portOperation = null; // wso.getPortOperationName();

        EObject parent = wso.eContainer();
        if (parent instanceof OtherElementsContainer) {
            OtherElementsContainer container = (OtherElementsContainer) parent;
            PortTypeOperation portTypeOperation =
                    (PortTypeOperation) Xpdl2ModelUtil
                            .getOtherElement(container,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_PortTypeOperation());
            if (portTypeOperation != null) {
                portType = portTypeOperation.getPortTypeName();
                portOperation = portTypeOperation.getOperationName();
            }
        }

        Service svc = wso.getService();
        if (svc != null) {
            String port = wso.getService().getPortName();
            String service = wso.getService().getServiceName();
            WsdlServiceKey key =
                    new WsdlServiceKey(service, port, operation, portType,
                            portOperation, Xpdl2WsdlUtil.getLocation(wso
                                    .getService()));
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(wso);
            IResource resource = wc.getEclipseResources().get(0);
            IProject project = resource.getProject();
            Operation op =
                    WsdlIndexerUtil.getOperation(project, key, true, true);

            return op;
        }
        return null;
    }

    private static Operation getPortTypeOperation(
            PortTypeOperation portTypeOperation) {

        String portType = portTypeOperation.getPortTypeName();
        String portOperation = portTypeOperation.getOperationName();

        WsdlServiceKey key =
                new WsdlServiceKey(null, null, null, portType, portOperation,
                        Xpdl2WsdlUtil.getLocation(portTypeOperation));
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(portTypeOperation);
        IResource resource = wc.getEclipseResources().get(0);
        IProject project = resource.getProject();
        Operation op = WsdlIndexerUtil.getOperation(project, key, true, true);

        return op;
    }

    /**
     * @param partPath
     *            The WsdlPartPath to extend.
     * @param name
     *            The name of the component.
     * @return The XsdPath.
     */
    private static XsdPath getRootPath(WsdlPartPath partPath, String name) {
        XsdPath path = null;
        Part part = partPath.getPart();
        XSDTypeDefinition type = WsdlUtil.getTypeDefinition(part);
        int index = getIndex(name);
        int arrayIndex = getArrayIndex(name);
        name = stripIndex(name);
        if (type != null) {
            List<XSDComponent> components = WsdlUtil.getTypeChildren(type);
            for (XSDComponent component : components) {
                if (component instanceof XSDAttributeUse) {
                    String baseName =
                            XsdPath.getBaseComponentName(component, true);
                    if (name.equals(baseName)) {
                        path = new WsdlXsdRootPath(partPath, component);
                    }
                } else {
                    String baseName =
                            XsdPath.getBaseComponentName(component, true);
                    if (name.equals(baseName)) {
                        if (index == 0) {
                            path = new WsdlXsdRootPath(partPath, component);
                            path.setArrayIndex(arrayIndex);
                        }
                        break;
                    }
                }
                index--;
            }
        }
        return path;
    }

    /**
     * @param partPath
     *            The XsdPath to extend.
     * @param name
     *            The name of the component.
     * @return The XsdPath.
     */
    private static XsdPath getPath(XsdPath partPath, String name) {
        XsdPath path = null;
        XSDConcreteComponent parent = partPath.getComponent();
        List<XSDComponent> components = null;
        int index = getIndex(name);
        boolean takeFirst = false;
        if (index == 0) {
            takeFirst = true;
        }
        int arrayIndex = getArrayIndex(name);
        name = stripIndex(name);
        boolean isElementArray = false;
        if (parent instanceof XSDParticle) {
            XSDParticle particle = (XSDParticle) parent;
            int max = particle.getMaxOccurs();
            isElementArray = max > 1 || max == -1;
            parent = particle.getContent();
        }
        if (parent instanceof XSDElementDeclaration) {
            XSDElementDeclaration decl = (XSDElementDeclaration) parent;
            decl = decl.getResolvedElementDeclaration();
            if (isElementArray) {
                components = Collections.singletonList((XSDComponent) decl);
            } else {
                XSDTypeDefinition type = decl.getTypeDefinition();
                components = WsdlUtil.getTypeChildren(type);
            }
        } else if (parent instanceof XSDModelGroup) {
            XSDModelGroup group = (XSDModelGroup) parent;
            components = WsdlUtil.getGroupChildren(group);
        }
        if (components != null) {
            for (XSDComponent component : components) {
                if (component instanceof XSDAttributeUse) {
                    String baseName =
                            XsdPath.getBaseComponentName(component, true);
                    if (name.equals(baseName)) {
                        path = new XsdPath(partPath, component);
                    }
                    index--;
                } else {
                    String baseName =
                            XsdPath.getBaseComponentName(component, true);
                    if (name.equals(baseName)) {
                        if (index == 0 || takeFirst) {
                            path = new XsdPath(partPath, component);
                            path.setArrayIndex(arrayIndex);
                            break;
                        }
                    }
                    index--;
                }
            }
        }
        return path;
    }

    /**
     * Removes The [...] index from the name.
     * 
     * @param name
     *            The name possibly including index.
     * @return The name with no index.
     */
    private static String stripIndex(String name) {
        int b1 = name.indexOf('[');
        if (b1 != -1) {
            name = name.substring(0, b1);
        } else {
            int b2 = name.indexOf('{');
            if (b2 != -1) {
                name = name.substring(0, b2);
            }
        }
        return name;
    }

    /**
     * Extracts the index number from a name.
     * 
     * @param name
     *            The name possibly including index.
     * @return The index, or zero if there is no index.
     */
    private static int getIndex(String name) {
        int index = 0;
        int b1 = name.indexOf('[');
        int b2 = name.indexOf(']');
        if (b1 != -1 && b2 != -1 && b2 > b1) {
            String indexString = name.substring(b1 + 1, b2);
            try {
                index = Integer.parseInt(indexString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return index;
    }

    /**
     * Extracts the index number from a name.
     * 
     * @param name
     *            The name possibly including index.
     * @return The index, or zero if there is no index.
     */
    private static int getArrayIndex(String name) {
        int index = -1;
        int b1 = name.indexOf('{');
        int b2 = name.indexOf('}');
        if (b1 != -1 && b2 != -1 && b2 > b1) {
            String indexString = name.substring(b1 + 1, b2);
            try {
                index = Integer.parseInt(indexString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return index;
    }

    /**
     * @param otherElement
     * @param formal
     * @param in
     */
    public static IWsdlPath createWsdlPath(PortTypeOperation portTypeOperation,
            String path, boolean in) {
        IWsdlPath wsdlPath = null;
        StringTokenizer tokenizer = new StringTokenizer(path, "/"); //$NON-NLS-1$
        if (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.startsWith(WsdlPartPath.WSO_ID)) {
                if (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();

                    Part part = null;
                    Fault fault = null;

                    //
                    // Allow for optional fault:faultName
                    //
                    // if (token.startsWith(WsdlPartPath.FAULT_ID)) {
                    // String faultName =
                    // token.substring(WsdlPartPath.FAULT_ID.length());
                    //
                    // token = tokenizer.nextToken();
                    // if (token.startsWith(WsdlPartPath.PART_ID)) {
                    // String partName =
                    // token.substring(WsdlPartPath.PART_ID
                    // .length());

                    // fault = getFault(wso, partName, faultName);
                    // if (fault != null) {
                    // part = getPartFromFault(partName, fault);
                    // }
                    // }

                    // } else
                    if (token.startsWith(WsdlPartPath.PART_ID)) {
                        //
                        // Not from a operation fault message
                        String partName =
                                token.substring(WsdlPartPath.PART_ID.length());
                        part = getPart(portTypeOperation, partName, in);
                    }

                    if (part != null) {
                        WsdlPartPath partPath;
                        // if (fault instanceof org.eclipse.wst.wsdl.Fault) {
                        // partPath =
                        // new WsdlPartPath(wso,
                        // (org.eclipse.wst.wsdl.Fault) fault,
                        // part);
                        //
                        // } else {
                        partPath =
                                new WsdlPartPath(portTypeOperation, part, in,
                                        !in);
                        // }

                        if (tokenizer.hasMoreTokens()) {
                            token = tokenizer.nextToken();
                            XsdPath xsdPath = getRootPath(partPath, token);
                            while (xsdPath != null && tokenizer.hasMoreTokens()) {
                                token = tokenizer.nextToken();
                                xsdPath = getPath(xsdPath, token);
                            }
                            wsdlPath = xsdPath;
                        } else {
                            wsdlPath = partPath;
                        }
                    } else {
                        EObject obj =
                                Xpdl2ModelUtil
                                        .getParentActivity(portTypeOperation);
                        if (obj instanceof Activity) {
                            Activity activity = (Activity) obj;
                            boolean generatedRequestActivity =
                                    Xpdl2ModelUtil
                                            .isGeneratedRequestActivity(activity);
                            if (!generatedRequestActivity) {
                                if (ReplyActivityUtil.isReplyActivity(activity)) {
                                    Activity requestActivityForReplyActivity =
                                            ReplyActivityUtil
                                                    .getRequestActivityForReplyActivity(activity);
                                    if (requestActivityForReplyActivity != null) {
                                        generatedRequestActivity =
                                                Xpdl2ModelUtil
                                                        .isGeneratedRequestActivity(requestActivityForReplyActivity);
                                    }

                                }

                            }
                            if (generatedRequestActivity) {
                                String partName =
                                        token.substring(WsdlPartPath.PART_ID
                                                .length());
                                ProcessRelevantData parameter =
                                        ConceptUtil.resolveParameter(activity,
                                                partName);
                                if (parameter instanceof FormalParameter) {
                                    part =
                                            PartProxy
                                                    .getPart((FormalParameter) parameter);
                                    WsdlPartPath partPath =
                                            new WsdlPartPath(portTypeOperation,
                                                    part, in, !in);
                                    wsdlPath = partPath;
                                }
                            }

                        }
                    }
                }
            }
        }
        return wsdlPath;
    }

}
