/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform.document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.uml2.uml.Classifier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.transform.Bom2XmlTransformer;
import com.tibco.xpd.script.transform.util.TransformDataMappingUtil;
import com.tibco.xpd.script.transform.util.TransformUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.XmlUtil;

/**
 * @author mtorres
 */
public class TransformDocumentProvider {

    /** The transform direction. */
    private TransformDirection transformDirection;
    
    public static final String INPUT_ROOT_NAME = "__input__";//$NON-NLS-1$
    
    public static final String OUTPUT_ROOT_NAME = "__output__";//$NON-NLS-1$

    private List<IStatus> issues = new ArrayList<IStatus>();
    
    public List<IStatus> getIssues() {
        return issues;
    }

    public TransformDocumentProvider(TransformDirection transformDirection) {
        this.transformDirection = transformDirection;
    }

    public Document getDocument(Activity act){
        if (act != null) {
            Bom2XmlTransformer scriptTransformer = new Bom2XmlTransformer();
            if (transformDirection == TransformDirection.INPUT) {
                List<ProcessRelevantData> prdList =
                        ProcessInterfaceUtil
                                .getAssociatedProcessRelevantDataForActivity(act,
                                        ModeType.IN_LITERAL,
                                        true);
                if (prdList != null) {
                    return getInputDocument(prdList, scriptTransformer, act);
                }
            } else if (transformDirection == TransformDirection.OUTPUT) {
                List<ProcessRelevantData> prdList =
                        ProcessInterfaceUtil
                                .getAssociatedProcessRelevantDataForActivity(act,
                                        ModeType.OUT_LITERAL,
                                        true);
                if (prdList != null) {
                    return getOutputDocument(prdList, scriptTransformer, act);
                }
            }
        }
        return null;
    }

    private Document getInputDocument(List<ProcessRelevantData> prdList,
            Bom2XmlTransformer scriptTransformer,
            Activity act) {
        return getDocument(prdList, scriptTransformer, INPUT_ROOT_NAME, act);
    }

    private Document getOutputDocument(List<ProcessRelevantData> prdList,
            Bom2XmlTransformer scriptTransformer,
            Activity act) {
        return getDocument(prdList, scriptTransformer, OUTPUT_ROOT_NAME, act);
    }
    
    private Document getDocument(List<ProcessRelevantData> prdList,
            Bom2XmlTransformer scriptTransformer, String rootElementName,
            Activity act) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        Document xmldoc = null;
        issues = new ArrayList<IStatus>();
        try {
            List<DataMapping> dataMappings = TransformUtil.getDataMappings(act);
            DocumentBuilder builder = factory.newDocumentBuilder();
            xmldoc = builder.newDocument();
            Element root = xmldoc.createElement(rootElementName);
            xmldoc.appendChild(root);
            if (prdList != null) {
                for (ProcessRelevantData prd : prdList) {
                    if (prd != null) {
                        DataType dataType = prd.getDataType();
                        if (dataType instanceof BasicType) {
                            Element newElement =
                                    xmldoc.createElement(prd.getName());
                            root.appendChild(newElement);
                        } else if (dataType instanceof ExternalReference) {
                            Classifier classifier =
                                    ProcessUtil
                                            .resolveDataReference((ExternalReference) dataType,
                                                    WorkingCopyUtil
                                                            .getProjectFor(prd));
                            Document referenceDocument =
                                    buildXml(prd.getName(),
                                            classifier,
                                            scriptTransformer);
                            if (referenceDocument != null
                                    && referenceDocument.getFirstChild() != null) {
                                Node referenceNode =
                                        referenceDocument.getFirstChild()
                                                .cloneNode(true);
                                Node tempNode =
                                        xmldoc.importNode(referenceNode, true);
                                root.appendChild(tempNode);
                            }
                        }
                    }
                }
            }
            expandDocument(xmldoc, dataMappings, act);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xmldoc;
    }
    
    private void expandDocument(Document doc, List<DataMapping> dataMappings,
            Activity activity) {
        if (doc != null) {
            if (doc != null && doc.getFirstChild() != null) {
                Node outputDomRoot = doc.getFirstChild();
                if (outputDomRoot != null && dataMappings != null) {
                    NodeList childNodes = outputDomRoot.getChildNodes();
                    if (childNodes != null && childNodes.getLength() > 0) {
                        for (int i = 0; i < childNodes.getLength(); i++) {
                            Node childNode = childNodes.item(i);
                            if (childNode != null) {
                                // Expand children
                                expandChildNodes(doc,
                                        childNode,
                                        dataMappings,
                                        activity);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void expandChildNodes(Document doc, Node node,
            List<DataMapping> dataMappings, Activity activity) {
        if (node != null && dataMappings != null) {
            NodeList childNodes = node.getChildNodes();
            if (childNodes != null && childNodes.getLength() > 0) {
                int childNodesLength = childNodes.getLength();
                for (int i = 0; i < childNodesLength; i++) {
                    Node childNode = childNodes.item(i);
                    if (childNode != null) {
                        // Expand children
                        expandChildNodes(doc, childNode, dataMappings, activity);
                    }
                }
            }
            int numberOfCopies =
                    calculateNumberOfCopies(node, dataMappings, activity);
            for (int i = 0; i < numberOfCopies; i++) {
                Node clonedNode = node.cloneNode(true);
                node.getParentNode().appendChild(clonedNode);
            }
        }
    }
    
    private List<Object> getResolvedDataMappings(
            List<DataMapping> dataMappings, Activity activity) {
        if (dataMappings != null) {
            Set<Object> resolvedDataMappings = new HashSet<Object>();
            for (DataMapping dataMapping : dataMappings) {
                if (dataMapping != null) {
                    if (transformDirection.equals(TransformDirection.INPUT)) {
                        resolvedDataMappings.addAll(TransformDataMappingUtil
                                .getMappedSources(dataMapping, activity));
                    } else {
                        resolvedDataMappings.addAll(TransformDataMappingUtil
                                .getMappedTargets(dataMapping, activity));
                    }
                }
            }
            return new ArrayList<Object>(resolvedDataMappings);
        }
        return Collections.emptyList();
    }
    
    private int calculateNumberOfCopies(Node node,
            List<DataMapping> dataMappings, Activity activity) {
        int numberOfCopies = 0;
        String nodeXPath =
                TransformDataMappingUtil.removeRootFromNodePath(TransformUtil
                        .getXmlNodeXPath(node));
        if (nodeXPath != null && dataMappings != null) {
            List<Object> resolvedDataMappings =
                    getResolvedDataMappings(dataMappings, activity);
            if (resolvedDataMappings != null && !resolvedDataMappings.isEmpty()) {
                for (Object object : resolvedDataMappings) {
                    if (object instanceof ConceptPath) {
                        ConceptPath conceptPath = (ConceptPath) object;
                        String basePath = conceptPath.getBasePath();
                        if (basePath != null) {
                            String baseXPath =
                                    TransformDataMappingUtil
                                            .conceptPathToXPath(basePath);
                            if (baseXPath != null
                                    && baseXPath.startsWith(nodeXPath)) {
                                ConceptPath ancestor =
                                        getAncestorWithPath(conceptPath,
                                                nodeXPath);
                                if (ancestor != null && ancestor.isArrayItem()) {
                                    int indexNum = ancestor.getIndex();                                    
                                    if (indexNum > numberOfCopies) {
                                        numberOfCopies = indexNum;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return numberOfCopies;
    }
    
    private ConceptPath getAncestorWithPath(ConceptPath conceptPath,
            String xpath) {
        if (xpath != null && conceptPath != null) {
            ConceptPath parentConceptPath = conceptPath;
            while (parentConceptPath != null) {
                if (parentConceptPath.getBasePath() != null) {
                    String parentXPath =
                            TransformDataMappingUtil
                                    .conceptPathToXPath(parentConceptPath
                                            .getBasePath());
                    if (parentXPath.equals(xpath)) {
                        return parentConceptPath;
                    }
                }
                parentConceptPath = parentConceptPath.getParent();
            }
        }
        return null;
    }
    
    /**
     * TODO: Cache the transformation and refresh the cache when the bom model is 
     * modified
     **/
    private Document buildXml(String rootName, Classifier classifier,
            Bom2XmlTransformer scriptTransformer) {
        Document xmlDoc = null;
        if (classifier != null && classifier.getModel() != null) {
            // perform the transformation BOM to xml
            if (scriptTransformer != null) {
                String xmlTransform =
                        scriptTransformer.transform(rootName, classifier, issues);
                if (xmlTransform != null) {
                    try {
                        xmlDoc = XmlUtil.stringToDom(xmlTransform);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return xmlDoc;
    }

    public void setTransformDirection(TransformDirection transformDirection) {
        this.transformDirection = transformDirection;
    }
}
