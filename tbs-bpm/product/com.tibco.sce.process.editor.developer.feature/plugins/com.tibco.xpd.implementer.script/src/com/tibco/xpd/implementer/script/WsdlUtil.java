/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDForm;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDModelGroupDefinition;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.XSDWildcard;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public final class WsdlUtil {

    /** Processing instruction regex. */
    private static final String PI = "<\\?.*\\?>"; //$NON-NLS-1$

    /** XPath element regex. */
    private static final String JS =
            "([a-zA-Z_]+[a-zA-Z0-9_]*\\:\\:)?[a-zA-Z_]+[a-zA_Z0-9_]*(\\[[0-9]+\\])?"; //$NON-NLS-1$

    /**
     * Private constructor.
     */
    private WsdlUtil() {
    }

    /**
     * @param group
     *            The group to get children for.
     * @return A list of child components.
     */
    public static List<XSDComponent> getGroupChildren(XSDModelGroup group) {
        List<XSDComponent> childList = new ArrayList<XSDComponent>();
        List<?> particles = group.getParticles();
        for (Object item : particles) {
            if (item instanceof XSDParticle) {
                XSDParticle particle = (XSDParticle) item;
                XSDParticleContent content = particle.getContent();
                if (content instanceof XSDElementDeclaration
                        || content instanceof XSDModelGroup
                        /* XPD-1491 allow for xsd:any etc. */
                        || content instanceof XSDWildcard) {
                    childList.add(particle);
                }
            }
        }
        return childList;
    }

    /**
     * @param type
     *            The type to get children for.
     * @return A list of child components.
     */
    public static List<XSDComponent> getTypeChildren(XSDTypeDefinition type) {
        List<XSDComponent> childList = new ArrayList<XSDComponent>();
        if (type instanceof XSDComplexTypeDefinition) {
            XSDComplexTypeDefinition complex = (XSDComplexTypeDefinition) type;
            List<?> attributes = complex.getAttributeContents();
            for (Object attribute : attributes) {
                if (attribute instanceof XSDAttributeUse) {
                    childList.add((XSDAttributeUse) attribute);
                }
            }
            XSDParticle particle = type.getComplexType();
            if (particle != null) {
                childList.add(particle);
            }
        }
        return childList;
    }

    /**
     * @param particle
     *            The particle to get children for.
     * @return A list of child components.
     */
    public static List<XSDComponent> getParticleChildren(XSDParticle particle) {
        List<XSDComponent> childList = new ArrayList<XSDComponent>();
        XSDParticleContent content = particle.getContent();
        if (content instanceof XSDElementDeclaration) {
            childList.add((XSDElementDeclaration) content);
        } else if (content instanceof XSDModelGroup) {
            childList.add((XSDModelGroup) content);
        }
        /* XPD-1491 allow for xsd:any etc. */
        else if (content instanceof XSDWildcard) {
            childList.add((XSDWildcard) content);
        }

        /* XPD-1491 allow for xsd:any etc. */

        return childList;
    }

    /**
     * @param part
     *            The part to get the type definition for.
     * @return The type definition associated with the part.
     */
    public static XSDTypeDefinition getTypeDefinition(Part part) {
        XSDTypeDefinition type;
        XSDElementDeclaration decl = part.getElementDeclaration();
        if (decl == null) {
            type = part.getTypeDefinition();
        } else {
            type = decl.getTypeDefinition();
        }
        return type;
    }

    /**
     * Resolves a parameter name to an IWsdlPath within a given operation
     * message.
     * 
     * @param wso
     *            The web service operation.
     * @param message
     *            The operation message.
     * @param parameter
     *            The xpath parameter name.
     * @return The WSDL path or null if none match.
     */
    public static IWsdlPath resolveXPathParameter(WebServiceOperation wso,
            Message message, String parameter, boolean ignoreNamespace) {
        IWsdlPath path = null;
        if (message != null && parameter != null) {
            if (parameter.startsWith("$")) { //$NON-NLS-1$
                parameter = parameter.substring(1);
            }
            String[] elements = parameter.split("/"); //$NON-NLS-1$
            path =
                    resolveParameter(wso,
                            message,
                            elements,
                            ignoreNamespace,
                            false);
        }
        return path;
    }

    /**
     * @param wso
     *            The web service operation.
     * @param message
     *            The operation message.
     * @param elements
     *            parts array.
     * @return The resolved wsdl path.
     */
    private static IWsdlPath resolveParameter(WebServiceOperation wso,
            Message message, String[] elements, boolean ignoreNamespace,
            boolean zeroBased) {
        IWsdlPath path = null;
        if (elements.length > 0) {
            int index = 0;
            for (Object next : message.getOrderedParts(null)) {
                Part part = (Part) next;
                String name;
                if (part.getElementName() == null) {
                    name = part.getName();
                } else {
                    name = part.getElementName().getLocalPart();
                }
                if (elements[index].equals(name)) {
                    WsdlPartPath partPath =
                            new WsdlPartPath(wso, part, false, false);
                    path =
                            resolveJavaScriptParameter(partPath,
                                    elements,
                                    index + 1,
                                    ignoreNamespace,
                                    zeroBased);
                }
                if (path != null) {
                    break;
                }
            }
        }
        return path;
    }

    /**
     * @param current
     *            The current WSDL path.
     * @param elements
     *            The parameter elements.
     * @param index
     *            The current index in the parameter elements.
     * @return The WSDL path matching the parameter.
     */
    private static IWsdlPath resolveJavaScriptParameter(IWsdlPath current,
            String[] elements, int index, boolean ignoreNamespace,
            boolean zeroBased) {
        IWsdlPath path = null;
        if (index >= elements.length) {
            path = current;
        } else {
            for (IWsdlPath child : current.getChildList()) {
                if (child instanceof XsdPath) {
                    XsdPath xsdPath = (XsdPath) child;
                    String childName =
                            XsdPath.getBaseComponentName(xsdPath.getComponent(),
                                    false);
                    if (childName == null) {
                        childName = ""; //$NON-NLS-1$
                    }
                    if (ignoreNamespace) {
                        int ns = childName.indexOf(":"); //$NON-NLS-1$
                        if (ns != -1) {
                            childName = childName.substring(ns + 1);
                        }
                    }
                    String childBase = getBaseName(childName);
                    String base = getBaseName(elements[index]);
                    if (ignoreNamespace) {
                        int ns = base.indexOf(":"); //$NON-NLS-1$
                        if (ns != -1) {
                            base = base.substring(ns + 1);
                        }
                    }
                    int position = getPosition(elements[index], zeroBased);
                    boolean setArrayIndex = false;
                    boolean advance = true;
                    boolean match = false;
                    XSDConcreteComponent component = xsdPath.getComponent();
                    if (component instanceof XSDParticle) {
                        XSDParticle particle = (XSDParticle) component;
                        if (particle.getContent() instanceof XSDElementDeclaration) {
                            if (xsdPath.isArray()) {
                                if (position == -1
                                        && index == (elements.length - 1)) {
                                    advance = true;
                                } else {
                                    advance = false;
                                }
                            }
                            if (childBase.equals(base)) {
                                match = true;
                            }
                        } else if (particle.getContent() instanceof XSDModelGroup) {
                            advance = false;
                            match = true;
                            if (xsdPath.isArray()) {
                                setArrayIndex = true;
                                position = 0;
                            }
                        }
                    } else if (component instanceof XSDElementDeclaration) {
                        IWsdlPath parent = xsdPath.getParent();
                        if (parent != null && parent.isArray()) {
                            setArrayIndex = true;
                        }
                        match = true;
                    } else if (component instanceof XSDAttributeUse) {
                        if (childBase.equals(base)) {
                            match = true;
                        }
                    }
                    if (position != -1 && setArrayIndex) {
                        xsdPath.setArrayIndex(position);
                    }
                    if (match) {
                        int newIndex = index + (advance ? 1 : 0);
                        path =
                                resolveJavaScriptParameter(child,
                                        elements,
                                        newIndex,
                                        ignoreNamespace,
                                        zeroBased);
                    }
                    /*
                     * } else if ("".equals(childBase)) { if (xsdPath.isArray())
                     * { xsdPath.setArrayIndex(0); } path =
                     * resolveJavaScriptParameter(child, elements, index); }
                     */
                }
                if (path != null) {
                    break;
                }
            }
        }
        return path;
    }

    /**
     * @param element
     *            The element name.
     * @return The array index position.
     */
    private static int getPosition(String element, boolean zeroBased) {
        int position = -1;
        int open = element.indexOf('[');
        int close = element.lastIndexOf(']');
        if (open != -1 && close != -1 && close > open) {
            String content = element.substring(open + 1, close);
            try {
                position = Integer.parseInt(content);
                if (!zeroBased) {
                    position--;
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        return position;
    }

    /**
     * @param element
     *            The element name.
     * @return The base name without the array index.
     */
    private static String getBaseName(String element) {
        String base = element;
        int open = element.indexOf('[');
        if (open != -1) {
            base = base.substring(0, open);
        }
        return base;
    }

    /**
     * @param parameter
     *            The parameter to check.
     * @return true if the parameter is text contained in quotes.
     */
    public static boolean isJavaScriptAppendedText(String parameter) {
        parameter = parameter.trim();
        if (parameter != null && parameter.length() > 2) {
            if (parameter.substring(0, 1).equals("\"") //$NON-NLS-1$
                    && parameter.substring(parameter.length() - 1,
                            parameter.length()).equals("\"")) { //$NON-NLS-1$
                return true;
            }
        }
        return false;
    }

    /**
     * @param message
     *            The WSDL message.
     * @param parameter
     *            The parameter to check.
     * @return true if the parameter is contained in the schema.
     */
    public static boolean isValidJavaScriptParameter(Message message,
            String parameter) {
        boolean valid = false;
        if (parameter != null) {
            String[] elements = parameter.split("\\."); //$NON-NLS-1$
            if (elements.length > 0) {
                int index = 0;
                for (Object next : message.getOrderedParts(null)) {
                    Part part = (Part) next;
                    String name;
                    if (part.getElementName() == null) {
                        name = part.getName();
                    } else {
                        name = part.getElementName().getLocalPart();
                    }
                    if (name != null && name.equals(elements[index])) {
                        valid =
                                isValidJavaScriptParameter(getTypeDefinition(part),
                                        elements,
                                        index + 1);
                    }
                    if (valid) {
                        break;
                    }
                }
            }
        }
        return valid;
    }

    /**
     * @param type
     *            The type to check.
     * @param elements
     *            The parameter elements.
     * @param index
     *            The current index in the elements array.
     * @return true if the path is valid.
     */
    private static boolean isValidJavaScriptParameter(XSDTypeDefinition type,
            String[] elements, int index) {
        boolean valid = false;
        if (index >= elements.length) {
            valid = true;
        } else {
            if (type instanceof XSDComplexTypeDefinition) {
                XSDComplexTypeDefinition complex =
                        (XSDComplexTypeDefinition) type;
                if (elements[index].startsWith("@")) { //$NON-NLS-1$
                    String attribute = elements[index].substring(1);
                    for (Object next : complex.getAttributeUses()) {
                        XSDAttributeUse use = (XSDAttributeUse) next;
                        XSDAttributeDeclaration decl =
                                use.getAttributeDeclaration();
                        if (decl != null) {
                            String name = decl.getName();
                            if (name != null && name.equals(attribute)) {
                                valid = true;
                            }
                        }
                        if (valid) {
                            break;
                        }
                    }
                } else {
                    XSDParticle particle = complex.getComplexType();
                    if (particle != null) {
                        valid =
                                isValidJavaScriptParameter(particle,
                                        elements,
                                        index,
                                        1);
                    }
                }
            }
        }
        return valid;
    }

    /**
     * @param particle
     *            The particle to check.
     * @param elements
     *            The parameter elements.
     * @param index
     *            The current index in the elements array.
     * @param maxMultiplier
     *            The multiplier for max values.
     * @return true if the path is valid.
     */
    private static boolean isValidJavaScriptParameter(XSDParticle particle,
            String[] elements, int index, int maxMultiplier) {
        boolean valid = false;
        XSDParticleContent content = particle.getContent();
        int max = particle.getMaxOccurs() * maxMultiplier;
        if (content instanceof XSDModelGroup) {
            XSDModelGroup group = (XSDModelGroup) content;
            for (Object next : group.getParticles()) {
                XSDParticle child = (XSDParticle) next;
                valid = isValidJavaScriptParameter(child, elements, index, max);
                if (valid) {
                    break;
                }
            }
        } else if (content instanceof XSDElementDeclaration) {
            String current = elements[index];
            if (Pattern.matches(JS, current)) {
                current = current.replace("::", ":"); //$NON-NLS-1$ //$NON-NLS-2$
                boolean indexValid = true;
                int open = current.indexOf('[');
                if (current.contains("[") && current.endsWith("]")) { //$NON-NLS-1$ //$NON-NLS-2$
                    String indexString =
                            current.substring(open + 1, current.length() - 1);
                    current = current.substring(0, open);
                    try {
                        int indexValue = Integer.parseInt(indexString);
                        if (indexValue < 0 || (indexValue > max && max != -1)) {
                            indexValid = false;
                        }
                    } catch (NumberFormatException e) {
                        indexValid = false;
                    }
                }
                if (indexValid) {
                    XSDElementDeclaration element =
                            (XSDElementDeclaration) content;
                    element = element.getResolvedElementDeclaration();
                    String name = element.getQName();
                    if (!name.contains(":")) { //$NON-NLS-1$
                        if (XSDForm.QUALIFIED_LITERAL.equals(element
                                .getSchema().getElementFormDefault())) {
                            String uri = element.getTargetNamespace();
                            Map<?, ?> namespaces =
                                    element.getSchema()
                                            .getQNamePrefixToNamespaceMap();
                            for (Object prefix : namespaces.keySet()) {
                                if (prefix != null) {
                                    Object value = namespaces.get(prefix);
                                    if (uri.equals(value)) {
                                        name = ((String) prefix) + ":" + name; //$NON-NLS-1$
                                    }
                                }
                            }
                        }
                    }
                    if (name != null && name.equals(current)) {
                        valid =
                                isValidJavaScriptParameter(element.getType(),
                                        elements,
                                        index + 1);
                    }
                }
            }
        }
        return valid;
    }

    /**
     * Generates dummy XML data for the given schema type.
     * 
     * @param name
     *            The root element name.
     * @param type
     *            The schema type.
     * @return The generated XML.
     * @throws XmlException
     *             If there was a problem.
     */
    public static String generateXml(String name, XSDTypeDefinition type)
            throws XmlException {
        return generateXml(name, type, 5, true);
    }

    /**
     * Generates dummy XML data for the given schema type.
     * 
     * @param name
     *            The root element name.
     * @param type
     *            The schema type.
     * @param depth
     *            The depth to generate to.
     * @return The generated XML.
     * @throws XmlException
     *             If there was a problem.
     */
    public static String generateXml(String name, XSDTypeDefinition type,
            int depth, boolean bidirectional) throws XmlException {
        String xml = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement(name);
            document.appendChild(root);
            Set<XSDElementDeclaration> used =
                    new HashSet<XSDElementDeclaration>();
            Set<XSDComplexTypeDefinition> complexTypesUsed =
                    new HashSet<XSDComplexTypeDefinition>();
            if (type instanceof XSDComplexTypeDefinition) {
                complexTypesUsed.add((XSDComplexTypeDefinition) type);
            }
            appendTypeChildren(document,
                    root,
                    type,
                    depth,
                    used,
                    complexTypesUsed,
                    bidirectional);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            Source source = new DOMSource(document);
            Result target = new StreamResult(writer);
            transformer.transform(source, target);
            writer.flush();
            xml = writer.toString();
            writer.close();
            xml = xml.replaceAll(PI, ""); //$NON-NLS-1$
        } catch (ParserConfigurationException e) {
            throw new XmlException(e.getMessage());
        } catch (TransformerConfigurationException e) {
            throw new XmlException(e.getMessage());
        } catch (TransformerException e) {
            throw new XmlException(e.getMessage());
        } catch (IOException e) {
            throw new XmlException(e.getMessage());
        }
        return xml;
    }

    /**
     * @param document
     *            The document.
     * @param element
     *            The element to append to.
     * @param type
     *            The XSD type to append from.
     * @throws XmlException
     *             If there was a problem.
     */
    private static void appendTypeChildren(Document document, Element element,
            XSDTypeDefinition type, int depth, Set<XSDElementDeclaration> used,
            Set<XSDComplexTypeDefinition> complexTypesUsed,
            boolean bidirectional) throws XmlException {
        List<XSDComponent> children = getTypeChildren(type);
        for (XSDComponent component : children) {
            if (component instanceof XSDElementDeclaration) {
                Set<XSDComplexTypeDefinition> newComplexTypesUsed =
                        new HashSet<XSDComplexTypeDefinition>(complexTypesUsed);
                XSDElementDeclaration elementDecl =
                        (XSDElementDeclaration) component;
                appendElement(document,
                        element,
                        elementDecl,
                        depth,
                        used,
                        newComplexTypesUsed,
                        bidirectional);
            } else if (component instanceof XSDAttributeUse) {
                XSDAttributeUse attributeUse = (XSDAttributeUse) component;
                XSDAttributeDeclaration attrDecl =
                        attributeUse.getAttributeDeclaration();
                String qName;
                Attr child;
                XSDAttributeDeclaration resolved =
                        attrDecl.getResolvedAttributeDeclaration();
                String namespaceURI = XsdPath.getNamespaceUri(attrDecl);
                Map<String, String> componentNamespaces =
                        WsdlUtil.getComponentNamespaces(attrDecl);
                String prefix =
                        WsdlUtil.getPrefix(componentNamespaces, namespaceURI);
                qName = resolved.getName();
                if (prefix != null) {
                    qName = prefix + ":" + qName; //$NON-NLS-1$
                }
                if (qName.contains(":")) { //$NON-NLS-1$
                    String uri = resolved.getTargetNamespace();
                    child = document.createAttributeNS(uri, qName);
                } else {
                    child = document.createAttribute(qName);
                }
                element.setAttributeNode(child);
            } else if (component instanceof XSDParticle) {
                XSDParticle particle = (XSDParticle) component;
                int min = particle.getMinOccurs();
                if (min == 0) {
                    min = 1;
                }
                XSDParticleContent content = particle.getContent();
                for (int i = 0; i < min; i++) {
                    Set<XSDComplexTypeDefinition> newComplexTypesUsed =
                            new HashSet<XSDComplexTypeDefinition>(
                                    complexTypesUsed);
                    appendParticleChildren(document,
                            element,
                            content,
                            depth,
                            used,
                            newComplexTypesUsed,
                            bidirectional);
                }
            } else {
                throw new XmlException("Type child Not found: " //$NON-NLS-1$
                        + component.getClass().getName());
            }
        }
    }

    /**
     * @param document
     *            The document.
     * @param element
     *            The element to append to.
     * @param elementDecl
     *            The element declaration.
     * @throws XmlException
     *             If there was a problem.
     */
    private static void appendElement(Document document, Element element,
            XSDElementDeclaration elementDecl, int depth,
            Set<XSDElementDeclaration> used,
            Set<XSDComplexTypeDefinition> complexTypesUsed,
            boolean bidirectional) throws XmlException {
        if (depth > 0) {
            used = new HashSet<XSDElementDeclaration>(used);
            XSDElementDeclaration resolved =
                    elementDecl.getResolvedElementDeclaration();
            XSDTypeDefinition elementType = resolved.getTypeDefinition();
            if (!bidirectional
                    && elementType instanceof XSDComplexTypeDefinition) {
                if (complexTypesUsed.contains(elementType)) {
                    return;
                } else {
                    complexTypesUsed
                            .add((XSDComplexTypeDefinition) elementType);
                }
            }
            String qName;
            Element child;
            String namespaceURI = XsdPath.getNamespaceUri(elementDecl);
            Map<String, String> componentNamespaces =
                    WsdlUtil.getComponentNamespaces(elementDecl);
            String prefix =
                    WsdlUtil.getPrefix(componentNamespaces, namespaceURI);
            qName = resolved.getName();
            if (prefix != null) {
                qName = prefix + ":" + qName; //$NON-NLS-1$
            }
            if (qName.contains(":")) { //$NON-NLS-1$   
                String uri = resolved.getTargetNamespace();
                child = document.createElementNS(uri, qName);
            } else {
                child = document.createElement(qName);
            }
            element.appendChild(child);
            if (used.contains(resolved)) {
                depth--;
            } else {
                used.add(resolved);
            }
            appendTypeChildren(document,
                    child,
                    elementType,
                    depth,
                    used,
                    complexTypesUsed,
                    bidirectional);
        }
    }

    public static String getPrefix(Map<String, String> namespaces, String uri) {
        String prefix = null;
        if (namespaces != null && uri != null) {
            Set<String> keyset = namespaces.keySet();
            for (String key : keyset) {
                if (key != null) {
                    String tempUri = namespaces.get(key);
                    if (tempUri != null && tempUri.equals(uri)) {
                        return key;
                    }
                }
            }
        }
        return prefix;
    }

    public static Map<String, String> getComponentNamespaces(
            XSDComponent xsdComponent) {
        Map<String, String> namespaces = new HashMap<String, String>();
        Map<String, String> returnNamespaces = new HashMap<String, String>();
        if (xsdComponent != null) {
            IFile resource = WorkingCopyUtil.getFile(xsdComponent);
            if (resource != null) {
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(resource);
                if (wc != null) {
                    EObject root = wc.getRootElement();
                    if (root instanceof Definition) {
                        Definition definition = (Definition) root;
                        if (definition != null
                                && definition.getNamespaces() != null) {
                            namespaces.putAll(definition.getNamespaces());
                        }
                        XSDSchema schema = xsdComponent.getSchema();
                        if (schema != null
                                && schema.getQNamePrefixToNamespaceMap() != null) {
                            Map<String, String> prefixNamespaceMap =
                                    schema.getQNamePrefixToNamespaceMap();
                            if (prefixNamespaceMap != null) {
                                Map<String, String> schemaOnlyNamespaces =
                                        WsdlUtil.getSchemaOnlyNamespaces(namespaces,
                                                prefixNamespaceMap);
                                returnNamespaces.putAll(schemaOnlyNamespaces);
                                // Add Definition namespaces
                                addNotOverwrittenNamespaces(schemaOnlyNamespaces,
                                        namespaces,
                                        returnNamespaces);
                                // Add Schema namespaces
                                addNotOverwrittenNamespaces(schemaOnlyNamespaces,
                                        prefixNamespaceMap,
                                        returnNamespaces);
                            }
                        }
                    }
                }
            }
        }
        return returnNamespaces;
    }

    public static void addNotOverwrittenNamespaces(
            Map<String, String> overwrittenNamespaces,
            Map<String, String> allNamespaces,
            Map<String, String> returnNamespaces) {
        if (returnNamespaces != null && allNamespaces != null) {
            Set<Map.Entry<String, String>> allNamespacesEntrySet = null;
            if (allNamespaces != null) {
                allNamespacesEntrySet = allNamespaces.entrySet();
            }
            if (allNamespacesEntrySet == null) {
                allNamespacesEntrySet =
                        new HashSet<Map.Entry<String, String>>();
            }
            for (Map.Entry<String, String> allNamespacesEntry : allNamespacesEntrySet) {
                String key = allNamespacesEntry.getKey();
                if (key != null) {
                    String value = allNamespacesEntry.getValue();
                    if (value != null) {
                        if (!overwrittenNamespaces.containsKey(key)
                                && !overwrittenNamespaces.containsValue(value)) {
                            returnNamespaces.put(key, value);
                        }
                    }
                }
            }
        }
    }

    public static Map<String, String> getSchemaOnlyNamespaces(
            Map<String, String> definitionNamespaces,
            Map<String, String> allNamespaces) {
        Map<String, String> schemaOnlyNamespaces =
                new HashMap<String, String>();
        Set<Map.Entry<String, String>> allNamespacesEntrySet = null;
        if (allNamespaces != null) {
            allNamespacesEntrySet = allNamespaces.entrySet();
        }
        if (allNamespacesEntrySet == null) {
            allNamespacesEntrySet = new HashSet<Map.Entry<String, String>>();
        }
        Set<Map.Entry<String, String>> definitionNamespacesEntrySet = null;
        if (definitionNamespaces != null) {
            definitionNamespacesEntrySet = definitionNamespaces.entrySet();
        }
        if (definitionNamespacesEntrySet == null) {
            definitionNamespacesEntrySet =
                    new HashSet<Map.Entry<String, String>>();
        }
        for (Map.Entry<String, String> allNamespacesEntry : allNamespacesEntrySet) {
            if (!definitionNamespacesEntrySet.contains(allNamespacesEntry)) {
                if (allNamespacesEntry.getKey() != null
                        && allNamespacesEntry.getValue() != null) {
                    schemaOnlyNamespaces.put(allNamespacesEntry.getKey(),
                            allNamespacesEntry.getValue());
                }
            }
        }
        return schemaOnlyNamespaces;
    }

    /**
     * @param document
     *            The document.
     * @param element
     *            The element to append to.
     * @param content
     *            The particle content to append from.
     * @throws XmlException
     *             If there was a problem.
     */
    private static void appendParticleChildren(Document document,
            Element element, XSDParticleContent content, int depth,
            Set<XSDElementDeclaration> used,
            Set<XSDComplexTypeDefinition> complexTypesUsed,
            boolean bidirectional) throws XmlException {
        if (content instanceof XSDModelGroup) {
            XSDModelGroup group = (XSDModelGroup) content;
            for (Object next : group.getParticles()) {
                XSDParticle particle = (XSDParticle) next;
                int min = particle.getMinOccurs();
                if (min == 0) {
                    min = 1;
                }
                XSDParticleContent childContent = particle.getContent();
                Set<XSDComplexTypeDefinition> newComplexTypesUsed =
                        new HashSet<XSDComplexTypeDefinition>(complexTypesUsed);
                for (int i = 0; i < min; i++) {
                    appendParticleChildren(document,
                            element,
                            childContent,
                            depth,
                            used,
                            newComplexTypesUsed,
                            bidirectional);
                }
            }
        } else if (content instanceof XSDElementDeclaration) {
            Set<XSDComplexTypeDefinition> newComplexTypesUsed =
                    new HashSet<XSDComplexTypeDefinition>(complexTypesUsed);
            XSDElementDeclaration elementDecl = (XSDElementDeclaration) content;
            appendElement(document,
                    element,
                    elementDecl,
                    depth,
                    used,
                    newComplexTypesUsed,
                    bidirectional);
        } else if (content instanceof XSDWildcard) {
            // TODO: Add the wildcard attributes
        } else if (content instanceof XSDModelGroupDefinition) {
            // TODO: Add the modelGroupDefinition attributes
        } else {
            throw new XmlException("Particle child Not found: " //$NON-NLS-1$
                    + content.getClass().getName());
        }
    }

    /**
     * @param parts
     *            The parts to check.
     * @return true if the parts contain one or more mapping components.
     */
    public static boolean hasComponents(Collection<javax.wsdl.Part> parts) {
        boolean hasComponents = false;
        for (javax.wsdl.Part part : parts) {
            org.eclipse.wst.wsdl.Part ePart = (org.eclipse.wst.wsdl.Part) part;
            XSDTypeDefinition definition = WsdlUtil.getTypeDefinition(ePart);
            hasComponents =
                    (definition instanceof XSDSimpleTypeDefinition)
                            || hasChildComponents(definition);
            if (hasComponents) {
                break;
            }
        }
        return hasComponents;
    }

    /**
     * @param type
     *            The type.
     * @return true if there are any child mapping components.
     */
    private static boolean hasChildComponents(XSDTypeDefinition type) {
        return hasChildComponents(WsdlUtil.getTypeChildren(type));
    }

    /**
     * @param particle
     *            The particle.
     * @return true if there are any child mapping components.
     */
    private static boolean hasChildComponents(XSDParticle particle) {
        return hasChildComponents(WsdlUtil.getParticleChildren(particle));
    }

    /**
     * @param group
     *            The group.
     * @return true if there are any child mapping components.
     */
    private static boolean hasChildComponents(XSDModelGroup group) {
        return hasChildComponents(WsdlUtil.getGroupChildren(group));
    }

    /**
     * @param children
     *            The children.
     * @return true if there are any child mapping components.
     */
    private static boolean hasChildComponents(List<XSDComponent> children) {
        boolean hasComponents = false;
        for (XSDComponent component : children) {
            if (component instanceof XSDAttributeUse) {
                hasComponents = true;
            } else if (component instanceof XSDElementDeclaration) {
                XSDElementDeclaration element =
                        (XSDElementDeclaration) component;
                XSDTypeDefinition type =
                        element.getResolvedElementDeclaration().getType();
                if (type != null) {
                    if ("anyType".equals(type.getName())) { //$NON-NLS-1$
                        hasComponents = hasChildComponents(type);
                    } else {
                        hasComponents = true;
                    }
                }
            } else if (component instanceof XSDModelGroup) {
                XSDModelGroup group = (XSDModelGroup) component;
                hasComponents = hasChildComponents(group);
            } else if (component instanceof XSDParticle) {
                XSDParticle particle = (XSDParticle) component;
                hasComponents = hasChildComponents(particle);
            }
            if (hasComponents) {
                break;
            }
        }
        return hasComponents;
    }

    /**
     * Check if the operation parts use simple data types.
     * 
     * @param project
     *            The project.
     * @param serviceKey
     *            The WSDL service key.
     * @return true if the operation contains only simple types.
     */
    public static boolean isSimpleType(IProject project,
            WsdlServiceKey serviceKey) {

        boolean isSimple = false;

        if (project != null && serviceKey != null) {

            Operation op =
                    WsdlIndexerUtil.getOperation(project,
                            serviceKey,
                            true,
                            true);
            if (op != null) {
                if (op.getInput() != null) {
                    Message message = op.getInput().getMessage();

                    if (message != null) {
                        // Check input parts
                        isSimple = isSimpleType(message);

                        // Check output parts if all
                        // input
                        // parts are of simple type
                        if (isSimple && op.getOutput() != null) {
                            message = op.getOutput().getMessage();

                            if (message != null) {
                                isSimple = isSimpleType(message);
                            }
                        }
                    }
                }
            } else {
                throw new NullPointerException(
                        String.format(Messages.WsdlUtil_OperationNotFound_message,
                                serviceKey.getServiceKey()));
            }
        }

        return isSimple;
    }

    /**
     * Check the parts in the <i>message</i> to determine if simple type mapping
     * is required. If a part type definition is an instance of
     * <code>XSDSimpleTypeDefinition</code> then this would be deemed a simple
     * type.
     * 
     * @param message
     *            The message.
     * @return true if this is considered a simple type.
     */
    private static boolean isSimpleType(Message message) {

        if (message != null) {
            Map<?, ?> parts = message.getParts();

            if (parts != null) {
                for (Object next : parts.values()) {
                    if (next instanceof Part) {
                        Part part = (Part) next;
                        XSDTypeDefinition definition = part.getTypeDefinition();

                        if (definition == null
                                || !(definition instanceof XSDSimpleTypeDefinition)) {
                            return false;
                        }
                    }
                }
            }
        } else {
            throw new NullPointerException("message is null."); //$NON-NLS-1$
        }

        return true;
    }

    /**
     * @param definition
     *            The definition to check.
     * @return true if valid.
     */
    public static boolean isRpc(Definition definition) {
        boolean isRpc = false;
        for (Object next : definition.getBindings().values()) {
            if (next instanceof Binding) {
                Binding binding = (Binding) next;
                List<?> elements = binding.getExtensibilityElements();
                for (Object element : elements) {
                    if (element instanceof SOAPBinding) {
                        SOAPBinding soapBinding = (SOAPBinding) element;
                        String style = soapBinding.getStyle();
                        if ("rpc".equals(style)) { //$NON-NLS-1$
                            isRpc = true;
                        }
                    }
                }
            }
        }
        return isRpc;
    }

    public static SoapBindingStyle getBindingStyle(Definition definition) {

        SoapBindingStyle bindingStyle = null;

        Map<?, ?> messages = definition.getMessages();

        for (Object next : messages.values()) {
            if (next instanceof Message) {
                Map<?, ?> parts = ((Message) next).getParts();

                for (Object nextPart : parts.values()) {
                    if (nextPart instanceof Part) {
                        Part part = (Part) nextPart;
                        if (part.getElementDeclaration() != null) {
                            // This is document style
                            if (bindingStyle == null) {
                                bindingStyle =
                                        SoapBindingStyle.DOCUMENT_LITERAL;
                            } else if (bindingStyle != SoapBindingStyle.DOCUMENT_LITERAL) {
                                // Cannot determine as mixture of styles
                                // used in the message part
                                return null;
                            }
                        } else if (part.getTypeDefinition() != null) {
                            // This is part style
                            if (bindingStyle == null) {
                                bindingStyle = SoapBindingStyle.RPC_LITERAL;
                            } else if (bindingStyle != SoapBindingStyle.RPC_LITERAL) {
                                // Cannot determine as mixture of styles
                                // used in the message part
                                return null;
                            }
                        } else {
                            // Part has no type or element definition so
                            // cannot determine binding style
                            return null;
                        }
                    }
                }
            }
        }

        return bindingStyle;
    }

    /**
     * @param act
     *            The activity.
     * @return true if the activity is a service task.
     */
    public static boolean isServiceTask(Activity act) {
        boolean serviceTask = false;
        Implementation impl = act.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            if (task.getTaskService() != null) {
                serviceTask = true;
            }
        }
        return serviceTask;
    }

    /**
     * @param act
     *            The end event activity.
     * @return true if this is a reply to a web service call.
     */
    public static boolean isEndReplyEvent(Activity act) {
        boolean isReply = false;
        Event event = act.getEvent();
        if (event instanceof EndEvent) {
            if (ReplyActivityUtil.isReplyActivity(act)) {
                isReply = true;
            }
        }
        return isReply;
    }

    /**
     * @param activity
     *            The activity.
     * @return The project for the activity.
     */
    private static IProject getProject(Activity activity) {
        IProject project = null;
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(activity);
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {
                project = resource.getProject();
            }
        }
        return project;
    }

    /**
     * @param activity
     *            The activity.
     * @param formal
     *            The parameter.
     * @param in
     *            true if in.
     * @return The IWsdlPath.
     */
    public static IWsdlPath resolveParameter(Activity activity, String formal,
            boolean in) {
        IWsdlPath wsdlPath = null;

        ActivityMessageProvider messageAdapter =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(activity);

        if (messageAdapter != null) {
            WebServiceOperation wso =
                    messageAdapter.getWebServiceOperation(activity);
            if (wso != null) {
                wsdlPath = WsdlPathFactory.createWsdlPath(wso, formal, in);
            }
        }
        return wsdlPath;
    }

    /**
     * @param interfaceMethod
     *            The Interface Method.
     * @param formal
     *            The parameter.
     * @param in
     *            true if in.
     * @return The IWsdlPath.
     */
    public static IWsdlPath resolveParameter(InterfaceMethod interfaceMethod,
            String formal, boolean in) {
        IWsdlPath wsdlPath = null;
        TriggerResultMessage triggerResultMessage =
                interfaceMethod.getTriggerResultMessage();
        if (triggerResultMessage != null) {
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(triggerResultMessage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
            if (otherElement instanceof PortTypeOperation) {
                wsdlPath =
                        WsdlPathFactory
                                .createWsdlPath((PortTypeOperation) otherElement,
                                        formal,
                                        in);
            }
        }

        return wsdlPath;
    }

    /**
     * @param target
     *            The traget.
     * @return
     */
    public static String wsdlPathToXPath(String target) {
        String xpath = target.replaceAll(".*part:", ""); //$NON-NLS-1$ //$NON-NLS-2$
        xpath = xpath.replaceAll("\\[[0-9]*\\]", ""); //$NON-NLS-1$ //$NON-NLS-2$
        xpath = xpath.replaceAll("group:sequence/", ""); //$NON-NLS-1$ //$NON-NLS-2$
        xpath = xpath.replaceAll("group:choice/", ""); //$NON-NLS-1$ //$NON-NLS-2$
        xpath = xpath.replace("{", "["); //$NON-NLS-1$ //$NON-NLS-2$
        xpath = xpath.replace("}", "]"); //$NON-NLS-1$ //$NON-NLS-2$
        return xpath;
    }

    /**
     * RS- There is a problem with this method. I am quite nervous to change
     * this one, so writing an overload
     * 
     * This method has confused the direction of the mapping with the
     * input/output of the WSDL
     * 
     * @see <code>com.tibco.xpd.implementer.script.WsdlUtil.getAllWsdlPaths(Activity, DirectionType, Boolean)</code>
     * 
     * @param activity
     *            The activity.
     * @param direction
     *            The mapping direction.
     * @return A set of all referenced wsdl paths.
     */
    public static Set<IWsdlPath> getAllWsdlPaths(Activity activity,
            DirectionType direction) {
        Set<IWsdlPath> paths = new HashSet<IWsdlPath>();
        List<DataMapping> mappings =
                Xpdl2ModelUtil.getDataMappings(activity, direction);
        if (DirectionType.IN_LITERAL.equals(direction)) {
            for (DataMapping mapping : mappings) {
                String target = DataMappingUtil.getTarget(mapping);
                IWsdlPath path =
                        WsdlUtil.resolveParameter(activity, target, true);
                if (path != null) {
                    paths.add(path);
                }
            }
        } else {
            for (DataMapping mapping : mappings) {
                String target = DataMappingUtil.getTarget(mapping);
                String script = DataMappingUtil.getScript(mapping);
                String grammar = DataMappingUtil.getGrammar(mapping);
                boolean isScripted = DataMappingUtil.isScripted(mapping);
                if (isScripted) {
                    IExpressionParser parser =
                            ExpressionParserFactory.getInstance()
                                    .getExpressionParser(grammar);
                    try {
                        Collection<String> params =
                                parser.getParameterNames(script, null, null);
                        ActivityMessageProvider messageAdapter =
                                ActivityMessageProviderFactory.INSTANCE
                                        .getMessageProvider(activity);
                        if (messageAdapter != null) {
                            WebServiceOperation wso =
                                    messageAdapter
                                            .getWebServiceOperation(activity);
                            javax.wsdl.Message message =
                                    Xpdl2WsdlUtil.getMessage(activity);
                            if (wso != null && message != null) {
                                for (String xpathParam : params) {
                                    IWsdlPath path =
                                            WsdlUtil.resolveXPathParameter(wso,
                                                    message,
                                                    xpathParam,
                                                    true);
                                    if (path != null) {
                                        paths.add(path);
                                    }
                                }
                            }
                        }
                    } catch (FieldParserException e) {
                        Logger logger =
                                LoggerFactory
                                        .createLogger(com.tibco.xpd.implementer.script.Activator.PLUGIN_ID);
                        logger.error(e);
                    }
                } else {
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            DirectionType.OUT_LITERAL);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(activity, target, script);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor single =
                                    (SingleMappingCompositor) compositor;
                            Collection<Object> items =
                                    single.getSourceItems(false);
                            for (Object next : items) {
                                if (next instanceof IWsdlPath) {
                                    paths.add((IWsdlPath) next);
                                }
                            }
                        }
                    }
                }
            }
        }
        return paths;
    }

    /**
     * The
     * 
     * 
     * @param activity
     *            The activity.
     * @param direction
     *            The mapping direction.
     * @return A set of all referenced wsdl paths.
     */
    public static Set<IWsdlPath> getAllWsdlPaths(Activity activity,
            DirectionType direction, Boolean wsdlIn) {
        Set<IWsdlPath> paths = new HashSet<IWsdlPath>();
        List<DataMapping> mappings =
                Xpdl2ModelUtil.getDataMappings(activity, direction);
        if (DirectionType.IN_LITERAL.equals(direction)) {
            for (DataMapping mapping : mappings) {
                String target = DataMappingUtil.getTarget(mapping);
                IWsdlPath path =
                        WsdlUtil.resolveParameter(activity, target, true);
                if (path != null) {
                    paths.add(path);
                }
            }
        } else {
            for (DataMapping mapping : mappings) {
                String target = DataMappingUtil.getTarget(mapping);
                String script = DataMappingUtil.getScript(mapping);
                String grammar = DataMappingUtil.getGrammar(mapping);
                boolean isScripted = DataMappingUtil.isScripted(mapping);
                if (isScripted) {
                    IExpressionParser parser =
                            ExpressionParserFactory.getInstance()
                                    .getExpressionParser(grammar);
                    try {
                        Collection<String> params =
                                parser.getParameterNames(script, null, null);
                        ActivityMessageProvider messageAdapter =
                                ActivityMessageProviderFactory.INSTANCE
                                        .getMessageProvider(activity);
                        if (messageAdapter != null) {
                            WebServiceOperation wso =
                                    messageAdapter
                                            .getWebServiceOperation(activity);
                            javax.wsdl.Message message =
                                    Xpdl2WsdlUtil.getMessage(activity);
                            if (wso != null && message != null) {
                                for (String xpathParam : params) {
                                    IWsdlPath path =
                                            WsdlUtil.resolveXPathParameter(wso,
                                                    message,
                                                    xpathParam,
                                                    true);
                                    if (path != null) {
                                        paths.add(path);
                                    }
                                }
                            }
                        }
                    } catch (FieldParserException e) {
                        Logger logger =
                                LoggerFactory
                                        .createLogger(com.tibco.xpd.implementer.script.Activator.PLUGIN_ID);
                        logger.error(e);
                    }
                } else {
                    ScriptMappingCompositorFactory factory =
                            ScriptMappingCompositorFactory
                                    .getCompositorFactory(grammar,
                                            DirectionType.OUT_LITERAL);
                    if (factory != null) {
                        ScriptMappingCompositor compositor =
                                factory.getCompositor(activity, target, script);
                        if (compositor instanceof SingleMappingCompositor) {
                            SingleMappingCompositor single =
                                    (SingleMappingCompositor) compositor;
                            Collection<Object> items =
                                    single.getSourceItems(wsdlIn);
                            for (Object next : items) {
                                if (next instanceof IWsdlPath) {
                                    paths.add((IWsdlPath) next);
                                }
                            }
                        }
                    }
                }
            }
        }
        return paths;
    }

    /**
     * @param item
     * @return
     */
    public static List<XsdPath> getParentGroupList(XsdPath item) {
        List<XsdPath> parentGroupList = new ArrayList<XsdPath>();
        if (item != null) {
            XsdPath parent = item.getParent();
            if (parent != null) {
                XSDConcreteComponent component = parent.getComponent();
                while (component instanceof XSDParticle) {
                    XSDParticle particle = (XSDParticle) component;
                    XSDParticleContent particleContent = particle.getContent();
                    if (particleContent instanceof XSDModelGroup) {
                        parentGroupList.add(parent);
                        parent = parent.getParent();
                        if (parent != null) {
                            component = parent.getComponent();
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return parentGroupList;
    }

    /**
     * Returns Base type definition for a given element.
     * 
     * @param elementDeclaration
     * @return
     */
    public static XSDTypeDefinition getBaseTypeDefinition(
            XSDElementDeclaration elementDeclaration) {
        XSDTypeDefinition typeDefinition =
                elementDeclaration.getTypeDefinition();
        if (typeDefinition instanceof XSDSimpleTypeDefinition) {
            return typeDefinition;
        } else if (typeDefinition instanceof XSDComplexTypeDefinition) {
            XSDTypeDefinition baseTypeDefinition =
                    ((XSDComplexTypeDefinition) typeDefinition)
                            .getBaseTypeDefinition();
            return baseTypeDefinition;
        }
        return null;
    }

}
