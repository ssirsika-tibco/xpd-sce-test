/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.transform.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.implementer.script.XmlException;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.transform.document.TransformDirection;
import com.tibco.xpd.script.transform.document.TransformDocumentProvider;
import com.tibco.xpd.script.transform.properties.XSLTTransformEditorSection;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.TransformScript;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author mtorres
 */
public class TransformUtil {

    public static final String TRANSFORM_SCRIPTGRAMMAR = "Transform"; //$NON-NLS-1$

    private static TransformDocumentProvider cachedTransformDocumentProvider =
            null;

    /**
     * @param item
     *            The item to get the name for.
     * @return The name.
     */
    public static String getMappingName(Object item) {
        String name = null;
        if (item instanceof NamedElement) {
            name = ((NamedElement) item).getName();
        } else if (item instanceof ConceptPath) {
            name = ((ConceptPath) item).getPath();
        }
        return name;
    }

    public static List<DataMapping> getDataMappings(Activity activity) {
        if (activity != null && activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            if (task != null && task.getTaskScript() != null) {
                return Xpdl2ModelUtil.getScriptTaskDataMappings(task
                        .getTaskScript());
            }
        }
        return Collections.emptyList();
    }

    public static String getXmlDocStr(String rootName,
            final Classifier classifier, XSDSchema schema) {
        StringBuffer xmlDoc = new StringBuffer();
        if (schema != null && classifier != null) {
            List<XSDTypeDefinition> typeDefinitions =
                    schema.getTypeDefinitions();
            if (typeDefinitions != null && !typeDefinitions.isEmpty()) {
                for (XSDTypeDefinition typeDefinition : typeDefinitions) {
                    if (typeDefinition != null
                            && typeDefinition.getName() != null
                            && typeDefinition.getName()
                                    .equals(classifier.getName())) {
                        try {
                            String doc =
                                    WsdlUtil.generateXml(rootName,
                                            typeDefinition,
                                            1,
                                            false);
                            if (doc != null) {
                                xmlDoc.append(doc);
                            }
                        } catch (XmlException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        return xmlDoc.toString();
    }

    public static TransformScript getTransformScript(Activity activity) {
        if (activity != null) {
            TaskScript taskScript = TransformUtil.getTaskScript(activity);
            if (taskScript != null) {
                return Xpdl2ModelUtil.getTransformScript(taskScript);
            }
        }
        return null;
    }

    /**
     * @param activity
     *            The activity to get the message for.
     * @return The taskScript.
     */
    public static TaskScript getTaskScript(Activity activity) {
        TaskScript taskScript = null;
        if (activity != null && activity.getImplementation() instanceof Task) {
            taskScript = ((Task) activity.getImplementation()).getTaskScript();
        }
        return taskScript;
    }

    public static boolean isTransformTask(Activity activity) {
        if (TransformUtil.getTransformScript(activity) != null) {
            return true;
        }
        return false;
    }

    public static String getInputTransformXMLStr(Activity act,
            boolean prettyPrint) {
        if (act != null) {
            return TransformUtil.getTransformXMLStr(TransformDirection.INPUT,
                    act,
                    prettyPrint);
        }
        return null;
    }

    public static Document getInputTransformXML(Activity act) {
        if (act != null) {
            return TransformUtil.getTransformXML(TransformDirection.INPUT, act);
        }
        return null;
    }

    public static String getOutputTransformXMLStr(Activity act,
            boolean prettyPrint) {
        if (act != null) {
            return TransformUtil.getTransformXMLStr(TransformDirection.OUTPUT,
                    act,
                    prettyPrint);
        }
        return null;
    }

    public static Document getOutputTransformXML(Activity act) {
        if (act != null) {
            return TransformUtil
                    .getTransformXML(TransformDirection.OUTPUT, act);
        }
        return null;
    }

    public static String getTransformXMLStr(
            TransformDirection transformDirection, Activity act,
            boolean prettyPrint) {
        if (transformDirection != null && act != null) {
            Document document =
                    TransformUtil.getTransformXML(transformDirection, act);
            if (document != null) {
                try {
                    return TransformUtil.toXmlString(document, prettyPrint);
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Document getTransformXML(
            TransformDirection transformDirection, Activity act) {
        if (transformDirection != null && act != null) {
            TransformDocumentProvider provider =
                    TransformUtil
                            .getCachedTransformDocumentProvider(transformDirection);
            return provider.getDocument(act);
        }
        return null;
    }

    public static String toXmlString(Document doc, boolean prettyPrint)
            throws TransformerConfigurationException, TransformerException,
            IOException {
        String result = null;
        if (prettyPrint) {
            OutputFormat format = new OutputFormat(doc);
            format.setIndenting(true);
            format.setIndent(2);
            StringWriter writer = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(writer, format);
            try {
                serializer.serialize(doc);
                result = writer.getBuffer().toString();
            } catch (IOException e) {
                throw e;
            }
        } else {
            TransformerFactory factory = TransformerFactory.newInstance();
            try {
                Transformer transformer = factory.newTransformer();
                StringWriter writer = new StringWriter();
                Source source = new DOMSource(doc);
                Result target = new StreamResult(writer);
                transformer.transform(source, target);
                result = writer.getBuffer().toString();
            } catch (TransformerConfigurationException e) {
                throw e;
            } catch (TransformerException e) {
                throw e;
            }
        }
        return result;
    }

    public static TransformDocumentProvider getCachedTransformDocumentProvider(
            TransformDirection transformDirection) {
        if (transformDirection != null) {
            if (cachedTransformDocumentProvider == null) {
                cachedTransformDocumentProvider =
                        new TransformDocumentProvider(transformDirection);
            } else {
                cachedTransformDocumentProvider
                        .setTransformDirection(transformDirection);
            }
            return cachedTransformDocumentProvider;
        }
        return null;
    }

    public static TransformDocumentProvider getNotCachedTransformDocumentProvider(
            TransformDirection transformDirection) {
        if (transformDirection != null) {
            return new TransformDocumentProvider(transformDirection);
        }
        return null;
    }

    public static boolean isTransformXsltUrl(Activity activity) {
        String activityGrammar =
                ScriptGrammarFactory.getScriptGrammar(activity,
                        DirectionType.IN_LITERAL);
        if (XSLTTransformEditorSection.SCRIPT_GRAMMAR.equals(activityGrammar)) {
            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    if (DirectionType.IN_LITERAL.equals(information
                            .getDirection())) {
                        Expression expression = information.getExpression();
                        if (expression != null) {
                            if (information.isReference()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String getXmlNodeXPath(Node element) {
        StringBuffer xpath = new StringBuffer();
        if (element != null) {
            if (element instanceof Attr
                    && ((Attr) element).getOwnerElement() != null
                    && !(((Attr) element).getOwnerElement().getParentNode() instanceof Document)) {
                xpath.append(getXmlNodeXPath(((Attr) element).getOwnerElement()));
            } else if (element.getParentNode() != null
                    && !(element.getParentNode() instanceof Document)) {
                xpath.append(getXmlNodeXPath(element.getParentNode()));
            }
            xpath.append("/");//$NON-NLS-1$
            if (element instanceof Attr) {
                xpath.append("@");//$NON-NLS-1$
            }
            xpath.append(element.getNodeName());
            if (isNodeMultiple(element)) {
                xpath.append("[").append(getNodeIndex(element)).append("]");//$NON-NLS-1$//$NON-NLS-2$
            }
        }
        return xpath.toString();
    }

    private static boolean isNodeMultiple(Node element) {
        if (element != null && element.getParentNode() != null) {
            String elementName = element.getNodeName();
            Node parentNode = element.getParentNode();
            NodeList childNodes = parentNode.getChildNodes();
            if (childNodes != null) {
                int elementNumber = 0;
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);
                    if (childNode != null && childNode.getNodeName() != null
                            && childNode.getNodeName().equals(elementName)) {
                        elementNumber++;
                    }
                }
                if (elementNumber > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getNodeIndex(Node element) {
        int nodeIndex = 1;
        if (element != null && element.getParentNode() != null) {
            String elementName = element.getNodeName();
            Node parentNode = element.getParentNode();
            Node firstChild = parentNode.getFirstChild();
            if (firstChild != null) {
                if (firstChild.equals(element)) {
                    return nodeIndex;
                } else {
                    String childName = firstChild.getNodeName();
                    if (childName != null && childName.equals(elementName)) {
                        nodeIndex++;
                    }
                    Node nextSibling = firstChild.getNextSibling();
                    while (nextSibling != null) {
                        if (nextSibling.equals(element)) {
                            return nodeIndex;
                        } else {
                            String nextSiblingName = nextSibling.getNodeName();
                            if (nextSiblingName != null
                                    && nextSiblingName.equals(elementName)) {
                                nodeIndex++;
                            }
                        }
                        nextSibling = nextSibling.getNextSibling();
                    }
                }
            }
        }
        return nodeIndex;
    }

}
