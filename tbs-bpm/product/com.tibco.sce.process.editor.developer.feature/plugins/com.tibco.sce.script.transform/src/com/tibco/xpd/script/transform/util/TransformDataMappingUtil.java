/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.transform.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.MultipleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.transform.document.TransformDocumentProvider;
import com.tibco.xpd.script.transform.mapping.GenerationException;
import com.tibco.xpd.script.transform.properties.XSLTTransformEditorSection;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author mtorres
 */
public class TransformDataMappingUtil {

    public static final String XSL_URI = "http://www.w3.org/1999/XSL/Transform"; //$NON-NLS-1$

    private static final String XSLT_CHARACTER_ENCODING = "UTF-8"; //$NON-NLS-1$

    public static String XPATH_SEPARATION_CHARACTER = "/"; //$NON-NLS-1$

    public static String CONCEPTPATH_SEPARATION_CHARACTER = "."; //$NON-NLS-1$

    public static String XPATH_OPENBRACKET_CHARACTER = "["; //$NON-NLS-1$

    public static String XPATH_CLOSEDBRACKET_CHARACTER = "]"; //$NON-NLS-1$

    /**
     * @return The generated XSLT document.
     * @throws GenerationException
     *             If there was a problem generating the document.
     */
    public static Document generateInputXslt(Activity act)
            throws GenerationException {

        Document document = createDocument();

        if (document != null && act != null) {
            final Activity activity = act;

            String activityGrammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            DirectionType.IN_LITERAL);
            if (XSLTTransformEditorSection.SCRIPT_GRAMMAR
                    .equals(activityGrammar)) {
                List<?> others =
                        Xpdl2ModelUtil.getOtherElementList(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());
                for (Object other : others) {
                    if (other instanceof ScriptInformation) {
                        ScriptInformation information =
                                (ScriptInformation) other;
                        if (DirectionType.IN_LITERAL.equals(information
                                .getDirection())) {
                            Expression expression = information.getExpression();
                            if (expression != null) {
                                String script = expression.getText();
                                if (information.isReference()) {
                                    try {
                                        URL url = new URL(script);
                                        script =
                                                readXSLTFromURL(url,
                                                        XSLT_CHARACTER_ENCODING);
                                        document = createDocument(script);
                                    } catch (MalformedURLException e) {
                                        throw new GenerationException(
                                                "Cannot read XSLT from URL"
                                                        + ": " + e.getLocalizedMessage()); //$NON-NLS-1$
                                    } catch (IOException e) {
                                        throw new GenerationException(
                                                "Cannot read XSLT from URL"
                                                        + ": " + e.getLocalizedMessage()); //$NON-NLS-1$
                                    }
                                } else {
                                    document = createDocument(script);
                                }
                            }
                        }
                    }
                }
            } else {

                Element stylesheet =
                        document.createElementNS(XSL_URI, "xsl:stylesheet"); //$NON-NLS-1$
                stylesheet.setAttribute("version", "1.0"); //$NON-NLS-1$ //$NON-NLS-2$
                stylesheet
                        .setAttribute("xmlns:xsl", "http://www.w3.org/1999/XSL/Transform");//$NON-NLS-1$ //$NON-NLS-2$
                document.appendChild(stylesheet);

                Element output =
                        document.createElementNS(XSL_URI, "xsl:output"); //$NON-NLS-1$
                output.setAttribute("method", "xml"); //$NON-NLS-1$ //$NON-NLS-2$
                stylesheet.appendChild(output);

                Element template =
                        document.createElementNS(XSL_URI, "xsl:template"); //$NON-NLS-1$
                template.setAttribute("match", "/"); //$NON-NLS-1$ //$NON-NLS-2$
                stylesheet.appendChild(template);

                List<DataMapping> mappings =
                        TransformUtil.getDataMappings(activity);

                List<ProcessRelevantData> associatedProcessRelevantData =
                        ProcessInterfaceUtil
                                .getAssociatedProcessRelevantDataForActivity(activity,
                                        ModeType.OUT_LITERAL,
                                        true);
                if (associatedProcessRelevantData != null
                        && !associatedProcessRelevantData.isEmpty()) {
                    Document outputDom =
                            TransformUtil.getOutputTransformXML(act);

                    createInputXslt(document,
                            act,
                            outputDom,
                            template,
                            mappings);
                }
            }
        }

        return document;
    }

    /**
     * This method generates the input xslt
     * 
     **/
    private static void createInputXslt(Document rootDoc, Activity act,
            Document outputDom, Element stylesheet,
            List<DataMapping> dataMappings) throws GenerationException {
        if (outputDom != null && outputDom.getFirstChild() != null) {
            Node outputDomRoot = outputDom.getFirstChild();
            createInputXslt(rootDoc,
                    act,
                    outputDomRoot,
                    stylesheet,
                    dataMappings);
        }
    }

    private static void createInputXslt(Document rootDoc, Activity act,
            Node nextElement, Element stylesheet, List<DataMapping> dataMappings)
            throws GenerationException {
        if (rootDoc != null && act != null && nextElement != null
                && stylesheet != null) {
            String nodeName = nextElement.getNodeName();
            String targetXPath =
                    TransformDataMappingUtil
                            .removeRootFromNodePath(TransformUtil
                                    .getXmlNodeXPath(nextElement));
            if (isComplexToComplexMapping(targetXPath, act, dataMappings)) {
                createCopyElement(rootDoc,
                        act,
                        nextElement,
                        stylesheet,
                        dataMappings,
                        targetXPath,
                        nodeName);
            } else {
                Element template =
                        createElement(rootDoc,
                                act,
                                nextElement,
                                stylesheet,
                                dataMappings,
                                nodeName);
                // Check if it is mapped to anything
                String xpath =
                        TransformDataMappingUtil
                                .resolveSourceMappingXPath(targetXPath,
                                        act,
                                        dataMappings);
                if (xpath != null) {
                    Element value =
                            rootDoc.createElementNS(XSL_URI, "xsl:value-of"); //$NON-NLS-1$
                    value.setAttribute("select", xpath); //$NON-NLS-1$
                    template.appendChild(value);
                }
                computeChildNodes(rootDoc,
                        act,
                        nextElement,
                        stylesheet,
                        dataMappings,
                        template);
            }
        }
    }

    private static boolean isComplexToComplexMapping(String targetXPath,
            Activity act, List<DataMapping> dataMappings) {
        String targetConceptPathStr = xpathToConceptPath(targetXPath);
        ConceptPath targetConceptPath =
                ConceptUtil.resolveConceptPath(act, targetConceptPathStr);
        if (isComplex(targetConceptPath)) {
            DataMapping targetDataMapping =
                    resolveTargetDataMapping(dataMappings,
                            targetConceptPathStr,
                            act);
            if (targetDataMapping != null) {
                List<Object> sourceMappings =
                        getMappedSources(targetDataMapping, act);
                if (sourceMappings != null && sourceMappings.size() == 1) {
                    Object sourceMapping = sourceMappings.get(0);
                    if (sourceMapping instanceof ConceptPath
                            && isComplex((ConceptPath) sourceMapping)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String xpathToConceptPath(String xpath) {
        if (xpath != null) {
            String conceptPath =
                    xpath.replaceAll(XPATH_SEPARATION_CHARACTER,
                            CONCEPTPATH_SEPARATION_CHARACTER);
            return incrementPathIndexes(conceptPath,
                    -1,
                    CONCEPTPATH_SEPARATION_CHARACTER);
        }
        return xpath;
    }

    public static String conceptPathToXPath(String conceptPath) {
        if (conceptPath != null) {
            String xpath =
                    conceptPath.replaceAll("\\"
                            + CONCEPTPATH_SEPARATION_CHARACTER,
                            XPATH_SEPARATION_CHARACTER);
            return incrementPathIndexes(xpath, 1, XPATH_SEPARATION_CHARACTER);
        }
        return conceptPath;
    }

    private static boolean isComplex(ConceptPath conceptPath) {
        if (conceptPath != null
                && (conceptPath.getType() instanceof org.eclipse.uml2.uml.Class || isMemoField(conceptPath))) {
            return true;
        }
        return false;
    }

    private static boolean isMemoField(ConceptPath conceptPath) {
        if (conceptPath != null
                && conceptPath.getItem() instanceof ProcessRelevantData
                && ProcessRelevantDataUtil
                        .isMemoField((ProcessRelevantData) conceptPath
                                .getItem())) {
            return true;
        }
        return false;
    }

    private static boolean isComplexRoot(ConceptPath conceptPath) {
        if (conceptPath != null
                && conceptPath.getItem() instanceof ProcessRelevantData) {
            ProcessRelevantData prd =
                    (ProcessRelevantData) conceptPath.getItem();
            if (prd.getDataType() instanceof ExternalReference
                    || ProcessRelevantDataUtil.isMemoField(prd)) {
                return true;
            }
        }
        return false;
    }

    // private static boolean isChildOfComplex(ConceptPath conceptPath) {
    // if (conceptPath != null && conceptPath.getRoot() != null) {
    // return isComplexRoot(conceptPath.getRoot());
    // }
    // return false;
    // }

    private static void computeChildNodes(Document rootDoc, Activity act,
            Node nextElement, Element stylesheet,
            List<DataMapping> dataMappings, Element template)
            throws GenerationException {

        NodeList childNodes = nextElement.getChildNodes();
        if (childNodes != null && childNodes.getLength() > 0) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);
                if (childNode != null) {
                    createInputXslt(rootDoc,
                            act,
                            childNode,
                            template,
                            dataMappings);
                }
            }
        }
    }

    private static Element createElement(Document rootDoc, Activity act,
            Node nextElement, Element stylesheet,
            List<DataMapping> dataMappings, String nodeName)
            throws GenerationException {
        Element template = rootDoc.createElementNS(XSL_URI, "xsl:element"); //$NON-NLS-1$
        template.setAttribute("name", nodeName); //$NON-NLS-1$
        // addAttributes(act,
        // rootDoc,
        // nextElement,
        // template,
        // dataMappings);
        stylesheet.appendChild(template);
        return template;
    }

    private static Element createCopyElement(Document rootDoc, Activity act,
            Node nextElement, Element stylesheet,
            List<DataMapping> dataMappings, String targetXPath, String nodeName)
            throws GenerationException {
        Element template = rootDoc.createElementNS(XSL_URI, "xsl:element"); //$NON-NLS-1$
        template.setAttribute("name", nodeName); //$NON-NLS-1$
        String xpath =
                TransformDataMappingUtil.resolveSourceMappingXPath(targetXPath,
                        act,
                        dataMappings);
        if (xpath != null) {
            Element attCopyOf = rootDoc.createElementNS(XSL_URI, "xsl:copy-of"); //$NON-NLS-1$
            attCopyOf
                    .setAttribute("select", xpath + "/@* | " + xpath + "/node()"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            template.appendChild(attCopyOf);
        }

        stylesheet.appendChild(template);
        return template;
    }

    /**
     * This method returns the xpath of the source element to which the target
     * element is mapped to
     * 
     * @return {@link String} the xpath of the source element, <code>null</code>
     *         otherwise
     **/
    private static String resolveSourceMappingXPath(String targetXPath,
            Activity act, List<DataMapping> dataMappings)
            throws GenerationException {
        // Compare targetPath in list of mappings
        if (dataMappings != null && !dataMappings.isEmpty()
                && targetXPath != null) {
            for (DataMapping dataMapping : dataMappings) {
                if (dataMapping != null && dataMapping.getFormal() != null) {
                    ConceptPath targetConceptPath =
                            ConceptUtil.resolveConceptPath(act,
                                    dataMapping.getFormal());
                    if (targetConceptPath != null) {
                        String mappingTargetXPath =
                                getTargetXPath(targetConceptPath,
                                        dataMappings,
                                        act);
                        if (mappingTargetXPath != null) {
                            // There are no attributes in xml
                            if (mappingTargetXPath.equals(targetXPath)) {
                                String sourceXPath =
                                        TransformDataMappingUtil
                                                .getSourceXPath(dataMapping,
                                                        act);
                                if (sourceXPath != null) {
                                    return sourceXPath;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static String getTargetXPath(ConceptPath targetConceptPath,
            List<DataMapping> dataMappings, Activity act) {
        List<String> targetMappingXPaths = new ArrayList<String>();
        if (targetConceptPath != null && targetConceptPath.getPath() != null) {
            String targetXPath =
                    conceptPathToXPath(targetConceptPath.getPath());
            if (isChildrenOfArrayItemParent(targetConceptPath)) {
                // Need to remove unneeded indexes (ie: only one array element
                // mapped)
                for (DataMapping dataMapping : dataMappings) {
                    if (dataMapping != null && dataMapping.getFormal() != null) {
                        ConceptPath conceptPath =
                                ConceptUtil.resolveConceptPath(act,
                                        dataMapping.getFormal());
                        if (conceptPath != null
                                && conceptPath.getPath() != null) {
                            targetMappingXPaths
                                    .add(conceptPathToXPath(conceptPath
                                            .getPath()));
                        }
                    }
                }
                StringTokenizer st =
                        new StringTokenizer(targetXPath,
                                XPATH_SEPARATION_CHARACTER);
                if (st != null) {
                    StringBuffer sb = new StringBuffer();
                    while (st.hasMoreTokens()) {
                        String token = st.nextToken();
                        if (token != null) {
                            int pathIndex = getXPathIndex(token);
                            if (pathIndex != -1
                                    && !isNeededIndex(sb.toString() + token,
                                            targetMappingXPaths)) {
                                token = removeIndexFromPath(token);
                            }
                            sb.append(token);
                            if (st.hasMoreTokens()) {
                                sb.append(XPATH_SEPARATION_CHARACTER);
                            }
                        }
                    }
                    return sb.toString();
                }
            } else {
                return targetXPath;
            }
        }
        return null;
    }

    private static boolean isNeededIndex(String path, List<String> mappingPaths) {
        int elementHigherIndex = 1;
        if (path != null) {
            int lastIndex = path.lastIndexOf(XPATH_OPENBRACKET_CHARACTER);
            if (lastIndex != -1) {
                String pathWithoutIndex = path.substring(0, lastIndex);
                if (pathWithoutIndex != null) {
                    for (String mappingPath : mappingPaths) {
                        if (mappingPath != null
                                && mappingPath.startsWith(pathWithoutIndex)) {
                            int mappingPathLastIndex =
                                    mappingPath
                                            .indexOf(XPATH_CLOSEDBRACKET_CHARACTER,
                                                    pathWithoutIndex.length());
                            if (mappingPathLastIndex != -1
                                    && mappingPath.length() >= mappingPathLastIndex + 1) {
                                String interestingMappingPathPart =
                                        mappingPath.substring(0,
                                                mappingPathLastIndex + 1);
                                int pathIndex =
                                        getXPathIndex(interestingMappingPathPart);
                                if (pathIndex > elementHigherIndex) {
                                    elementHigherIndex = pathIndex;
                                }
                            }
                        }
                    }
                }
            }
        }
        return elementHigherIndex > 1;
    }

    private static String removeIndexFromPath(String path) {
        if (path != null && path.trim().endsWith(XPATH_CLOSEDBRACKET_CHARACTER)) {
            int endIndex = path.lastIndexOf(XPATH_OPENBRACKET_CHARACTER);
            if (endIndex != -1) {
                return path.substring(0, endIndex);
            }
        }
        return null;
    }

    private static int getXPathIndex(String xpath) {
        if (xpath != null
                && xpath.trim().endsWith(XPATH_CLOSEDBRACKET_CHARACTER)) {
            int endIndex = xpath.lastIndexOf(XPATH_CLOSEDBRACKET_CHARACTER);
            if (endIndex != -1) {
                int beginIndex = xpath.lastIndexOf(XPATH_OPENBRACKET_CHARACTER);
                if (beginIndex != -1) {
                    String arrayIndexStr =
                            xpath.substring(beginIndex + 1, endIndex);
                    if (arrayIndexStr != null) {
                        try {
                            Integer arrayIndex = Integer.valueOf(arrayIndexStr);
                            return arrayIndex.intValue();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isChildrenOfArrayItemParent(ConceptPath conceptPath) {
        if (conceptPath != null && conceptPath.isArrayItem()) {
            return true;
        }
        ConceptPath parentConceptPath = conceptPath.getParent();
        while (parentConceptPath != null) {
            if (parentConceptPath.isArrayItem()) {
                return true;
            }
            parentConceptPath = parentConceptPath.getParent();
        }
        return false;
    }

    private static DataMapping resolveTargetDataMapping(
            List<DataMapping> dataMappings, String targetConceptPathStr,
            Activity act) {
        if (dataMappings != null && !dataMappings.isEmpty()
                && targetConceptPathStr != null) {
            for (DataMapping dataMapping : dataMappings) {
                if (dataMapping != null && dataMapping.getFormal() != null) {
                    ConceptPath targetConceptPath =
                            ConceptUtil.resolveConceptPath(act,
                                    dataMapping.getFormal());
                    if (targetConceptPath != null) {
                        String targetBasePath = targetConceptPath.getBasePath();
                        if (targetBasePath != null) {
                            if (targetBasePath.equals(targetConceptPathStr)) {
                                return dataMapping;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static String getSourceXPath(DataMapping dataMapping, Activity act)
            throws GenerationException {
        List<Object> mappedSources = getMappedSources(dataMapping, act);
        List<String> sourceXPaths = new ArrayList<String>();
        if (mappedSources != null && !mappedSources.isEmpty()) {
            for (Object mappedSource : mappedSources) {
                if (mappedSource instanceof ConceptPath) {
                    ConceptPath sourceConceptPath = (ConceptPath) mappedSource;
                    sourceXPaths.add(sourcePathToXPath(sourceConceptPath
                            .getPath(), act, isComplexRoot(sourceConceptPath)));
                } else if (mappedSource instanceof DataMapping
                        && ProcessScriptUtil
                                .isAScriptMapping((DataMapping) mappedSource)) {
                    sourceXPaths.add(ProcessScriptUtil
                            .getDataMappingScript(dataMapping));
                }
            }
        }
        if (sourceXPaths != null) {
            if (sourceXPaths.size() == 1) {
                return sourceXPaths.get(0);
            } else {
                StringBuilder path = new StringBuilder("concat(");//$NON-NLS-1$
                Iterator<String> iterator = sourceXPaths.iterator();
                while (iterator.hasNext()) {
                    String sourceXPath = iterator.next();
                    path.append(sourceXPath);
                    if (iterator.hasNext()) {
                        path.append(",");//$NON-NLS-1$
                    }
                }
                path.append(")");//$NON-NLS-1$
                return path.toString();
            }
        }
        return null;
    }

    // private static void addAttributes(Activity act, Document rootDoc,
    // Node node, Element template,
    // List<DataMapping> dataMappings) throws GenerationException {
    // if (node != null && node.getAttributes() != null) {
    // NamedNodeMap nnm = node.getAttributes();
    // for (int i = 0; i < nnm.getLength(); i++) {
    // Node att = nnm.item(i);
    // if (att != null) {
    // Element attElement =
    //                            rootDoc.createElementNS(XSL_URI, "xsl:attribute"); //$NON-NLS-1$
    //                    attElement.setAttribute("name", att.getNodeName()); //$NON-NLS-1$
    // template.appendChild(attElement);
    // // Check if it is mapped to anything
    // String attTargetPath =
    // TransformDataMappingUtil
    // .removeRootFromNodePath(TransformUtil
    // .getXmlNodeXPath(att));
    // String xpath =
    // TransformDataMappingUtil
    // .resolveSourceMappingXPath(attTargetPath,
    // act,
    // dataMappings);
    // if (xpath != null) {
    // Element attValueOf =
    // rootDoc
    // .createElementNS(XSL_URI,
    //                                                "xsl:value-of"); //$NON-NLS-1$
    //                        attValueOf.setAttribute("select", xpath); //$NON-NLS-1$
    // attElement.appendChild(attValueOf);
    // }
    // }
    // }
    // }
    // }

    /**
     * Reads the xslt from a url and then returns the actual xslt contents back
     * as a string
     * 
     * @param filePath
     * @param charSetEncoding
     * @return
     * @throws IOException
     */
    private static String readXSLTFromURL(URL filePath, String charSetEncoding)
            throws IOException {
        StringBuffer sb = new StringBuffer();

        InputStreamReader inStream;

        if (charSetEncoding != null && charSetEncoding.length() > 0) {
            inStream =
                    new InputStreamReader(filePath.openStream(),
                            Charset.forName(charSetEncoding));

        } else {
            inStream = new InputStreamReader(filePath.openStream());
        }

        BufferedReader r = new BufferedReader(inStream);
        String s = ""; //$NON-NLS-1$
        while ((s = r.readLine()) != null) {
            sb.append(s).append("\r\n"); //$NON-NLS-1$
        }
        r.close();
        return sb.toString();
    }

    /**
     * Create a <code>Document</code> object.
     * 
     * @return <code>Document</code> if successful, <code>null</code> otherwise.
     * @throws GenerationException
     */
    private static Document createDocument() throws GenerationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new GenerationException(e.getMessage());
        }
        return builder.newDocument();
    }

    /**
     * Create a <code>Document</code> object.
     * 
     * @return <code>Document</code> if successful, <code>null</code> otherwise.
     * @throws GenerationException
     */
    private static Document createDocument(String script)
            throws GenerationException {
        Document document = null;
        if (script != null && script.length() != 0) {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                document =
                        builder.parse(new InputSource(new StringReader(script)));
            } catch (ParserConfigurationException e) {
                throw new GenerationException(e.getMessage());
            } catch (SAXException e) {
                throw new GenerationException(e.getMessage());
            } catch (IOException e) {
                throw new GenerationException(e.getMessage());
            }
        }
        return document;
    }

    public static String sourcePathToXPath(String sourcePath,
            Activity activity, boolean isComplexRoot)
            throws GenerationException {
        if (isComplexRoot) {
            return "";//$NON-NLS-1$
        } else if (sourcePath != null) {
            String sourceXPath = conceptPathToXPath(sourcePath);
            StringBuffer resultStrBuffer =
                    new StringBuffer("/").append(TransformDocumentProvider.INPUT_ROOT_NAME).append("/");//$NON-NLS-1$//$NON-NLS-2$
            resultStrBuffer.append(sourceXPath);
            return resultStrBuffer.toString();
        }
        return null;
    }

    private static String incrementPathIndexes(String path, int increment,
            String separator) {
        if (path != null) {
            StringBuffer sb = new StringBuffer();
            StringTokenizer st = new StringTokenizer(path, separator);
            while (st.hasMoreTokens()) {
                String nextToken = st.nextToken();
                if (nextToken != null) {
                    int pathIndex = getXPathIndex(nextToken);
                    if (pathIndex != -1) {
                        nextToken =
                                replaceIndexInPath(nextToken, pathIndex
                                        + increment);
                    }
                }
                sb.append(nextToken);
                if (st.hasMoreTokens()) {
                    sb.append(separator);
                }
            }
            return sb.toString();
        }
        return path;
    }

    private static String replaceIndexInPath(String path, int newIndex) {
        if (path != null && path.trim().endsWith(XPATH_CLOSEDBRACKET_CHARACTER)) {
            int endIndex = path.lastIndexOf(XPATH_CLOSEDBRACKET_CHARACTER);
            if (endIndex != -1) {
                int beginIndex = path.lastIndexOf(XPATH_OPENBRACKET_CHARACTER);
                if (beginIndex != -1) {
                    String pathWithoutIndex = path.substring(0, beginIndex);
                    return pathWithoutIndex + XPATH_OPENBRACKET_CHARACTER
                            + Integer.toString(newIndex)
                            + XPATH_CLOSEDBRACKET_CHARACTER;
                }
            }
        }
        return path;
    }

    public static List<Object> getMappedSources(DataMapping dataMapping,
            Activity activity) {
        List<Object> mappedSources = new ArrayList<Object>();
        if (ProcessScriptUtil.isAScriptMapping(dataMapping)) {
            mappedSources.add(dataMapping);
        } else {
            String target = DataMappingUtil.getTarget(dataMapping);
            String script = DataMappingUtil.getScript(dataMapping);
            String grammar = DataMappingUtil.getGrammar(dataMapping);
            if (target != null && script != null && grammar != null) {
                ScriptMappingCompositorFactory factory =
                        ScriptMappingCompositorFactory
                                .getCompositorFactory(grammar,
                                        dataMapping.getDirection());
                if (factory != null) {
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(activity, target, script);
                    if (compositor instanceof MultipleMappingCompositor) {
                        MultipleMappingCompositor multiple =
                                (MultipleMappingCompositor) compositor;
                        Collection<Object> paths = multiple.getTargetItems();
                        for (Object path : paths) {
                            mappedSources.addAll(multiple.getSourceItems(path));
                        }
                    } else if (compositor instanceof SingleMappingCompositor) {
                        SingleMappingCompositor single =
                                (SingleMappingCompositor) compositor;
                        mappedSources.addAll(single
                                .getSourceItems(DirectionType.IN_LITERAL
                                        .equals(dataMapping.getDirection())));
                    }
                }
            }
        }
        return mappedSources;
    }

    public static List<Object> getMappedTargets(DataMapping dataMapping,
            Activity activity) {
        List<Object> mappedTargets = new ArrayList<Object>();
        String target = DataMappingUtil.getTarget(dataMapping);
        String script = DataMappingUtil.getScript(dataMapping);
        String grammar = DataMappingUtil.getGrammar(dataMapping);
        if (target != null && script != null && grammar != null) {
            ScriptMappingCompositorFactory factory =
                    ScriptMappingCompositorFactory
                            .getCompositorFactory(grammar,
                                    dataMapping.getDirection());
            if (factory != null) {
                ScriptMappingCompositor compositor =
                        factory.getCompositor(activity, target, script);
                if (compositor instanceof MultipleMappingCompositor) {
                    MultipleMappingCompositor multiple =
                            (MultipleMappingCompositor) compositor;
                    mappedTargets.addAll(multiple.getTargetItems());
                } else if (compositor instanceof SingleMappingCompositor) {
                    SingleMappingCompositor single =
                            (SingleMappingCompositor) compositor;
                    ConceptPath targetConcept =
                            ConceptUtil.resolveConceptPath(activity,
                                    single.getTarget());
                    if (targetConcept != null) {
                        mappedTargets.add(targetConcept);
                    }
                }
            }
        }

        return mappedTargets;
    }

    public static String removeRootFromNodePath(String nodePath) {
        if (nodePath != null) {
            if (nodePath.startsWith(XPATH_SEPARATION_CHARACTER
                    + TransformDocumentProvider.INPUT_ROOT_NAME
                    + XPATH_SEPARATION_CHARACTER)) {
                return nodePath.replaceFirst(XPATH_SEPARATION_CHARACTER
                        + TransformDocumentProvider.INPUT_ROOT_NAME
                        + XPATH_SEPARATION_CHARACTER, "");//$NON-NLS-1$
            } else if (nodePath.startsWith(XPATH_SEPARATION_CHARACTER
                    + TransformDocumentProvider.OUTPUT_ROOT_NAME
                    + XPATH_SEPARATION_CHARACTER)) {
                return nodePath.replaceFirst(XPATH_SEPARATION_CHARACTER
                        + TransformDocumentProvider.OUTPUT_ROOT_NAME
                        + XPATH_SEPARATION_CHARACTER, "");//$NON-NLS-1$
            }
        }
        return nodePath;
    }

}
