/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.nimbus.importprocessmap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.runtime.IStatus;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.xpd.nimbus.XpdNimbusPlugin;
import com.tibco.xpd.nimbus.internal.Messages;

/**
 * Validate the Nimbus Control Process Map to be imported.
 * 
 * @author aallway
 * @since 16/10/2012
 */
class ImportNimbusProcessMapValidator {

    private File file;

    /**
     * @param targetProject
     * @param file
     */
    public ImportNimbusProcessMapValidator(File file) {
        this.file = file;
    }

    /**
     * Validate the given input process map file to fill in a possible list of
     * problems into statuses list
     * 
     * @return list of validation statuses in passed statuses (empty if valid)
     */
    public List<ImportNimbusProcessMapValidationStatus> validate() {

        List<ImportNimbusProcessMapValidationStatus> statuses =
                new ArrayList<ImportNimbusProcessMapValidationStatus>();

        try {
            DocumentBuilderFactory cmFactory =
                    DocumentBuilderFactory.newInstance();
            cmFactory.setNamespaceAware(true);
            DocumentBuilder builder = cmFactory.newDocumentBuilder();
            Document xpdlDoc = builder.parse(file);

            XPath xpath = createProcessMapXPathProcessor();

            validate(xpdlDoc, xpath, statuses);

        } catch (SAXException e) {
            statuses.add(new ImportNimbusProcessMapValidationStatus(
                    IStatus.ERROR,
                    Messages.ImportNimbusProcessMapValidator_NotANimbusSimplifiedExport_message,
                    file.getAbsolutePath()));
        } catch (ParserConfigurationException e) {
            statuses.add(new ImportNimbusProcessMapValidationStatus(
                    IStatus.ERROR,
                    Messages.ImportNimbusProcessMapValidator_NotANimbusSimplifiedExport_message,
                    file.getAbsolutePath()));
        } catch (IOException e) {
            statuses.add(new ImportNimbusProcessMapValidationStatus(
                    IStatus.ERROR,
                    Messages.ImportNimbusProcessMapValidator_NotANimbusSimplifiedExport_message,
                    file.getAbsolutePath()));
        }

        return statuses;
    }

    /**
     * @return xpath processor for Nimbus Process Map
     */
    private XPath createProcessMapXPathProcessor() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        NamespaceContext context = new NimbusProcessMapNamespaceContext();
        xpath.setNamespaceContext(context);
        return xpath;
    }

    /**
     * Validate the given input Process Map file to fill in a possible list of
     * problems, warnings (or failing that a single OK status) into status list
     * 
     * @param xpdlDoc
     * @param xpath
     * @param statusList
     * 
     */
    private void validate(Document xpdlDoc, XPath xpath,
            List<ImportNimbusProcessMapValidationStatus> statusList) {
        try {
            /*
             * Check it was exported as a Nimbus Process Map Simplified model.
             */
            Object exporter =
                    xpath.evaluate("/XMI/XMI.header/XMI.documentation/XMI.exporter/text()", //$NON-NLS-1$
                            xpdlDoc,
                            XPathConstants.STRING);
            if (!(exporter instanceof String)
                    || !((String) exporter).contains("Simplified XML")) { //$NON-NLS-1$
                statusList
                        .add(new ImportNimbusProcessMapValidationStatus(
                                IStatus.ERROR,
                                Messages.ImportNimbusProcessMapValidator_NotANimbusSimplifiedExport_message,
                                file.getAbsolutePath()));

            } else {
                /*
                 * Ensure at least one diagram with activities present.
                 */
                Object diagrams =
                        xpath.evaluate("/XMI/XMI.content/Diagram[count(Activity) > 0]", //$NON-NLS-1$
                                xpdlDoc,
                                XPathConstants.NODESET);

                if (!(diagrams instanceof NodeList)
                        || ((NodeList) diagrams).getLength() < 1) {
                    statusList
                            .add(new ImportNimbusProcessMapValidationStatus(
                                    IStatus.ERROR,
                                    Messages.ImportProcessMapValidator_MustHaveOneDiagram_message,
                                    file.getAbsolutePath()));
                } else {
                    /*
                     * Ensure that all diagrams are Simple type (not BPMN - as
                     * we don't support that import)
                     */
                    int numDiagrams = ((NodeList) diagrams).getLength();

                    Object simpleDiagrams =
                            xpath.evaluate("/XMI/XMI.content/Diagram[count(Activity) > 0 and DiagramType = 'Simple']", //$NON-NLS-1$
                                    xpdlDoc,
                                    XPathConstants.NODESET);

                    if (!(simpleDiagrams instanceof NodeList)
                            || ((NodeList) simpleDiagrams).getLength() != numDiagrams) {
                        statusList
                                .add(new ImportNimbusProcessMapValidationStatus(
                                        IStatus.ERROR,
                                        Messages.ImportProcessMapSimplifiedExportValidator_NonSimpleDiagrams_message,
                                        file.getAbsolutePath()));
                    } else {
                        /*
                         * Everything appears to be ok.
                         */
                        statusList
                                .add(new ImportNimbusProcessMapValidationStatus(
                                        IStatus.OK,
                                        Messages.ImportProcessMapSimplifiedExportValidator_FileIsValidExportFormat_message,
                                        file.getAbsolutePath()));
                    }

                }

            }

        } catch (XPathExpressionException e) {
            XpdNimbusPlugin.getDefault().getLogger()
                    .error(e, "Failed xpath execution on Process Map."); //$NON-NLS-1$
        }
    }

    /**
     * Xpath namespace context
     * 
     * 
     * @author aallway
     * @since 28 Oct 2011
     */
    private static class NimbusProcessMapNamespaceContext implements
            NamespaceContext {

        private Map<String, String> prefixToUri;

        public NimbusProcessMapNamespaceContext() {
            prefixToUri = new HashMap<String, String>();
            //            prefixToUri.put("c", "CONTROL"); //$NON-NLS-1$ //$NON-NLS-2$
            //            prefixToUri.put("CONTROL", "CONTROL"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        /**
         * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
         * 
         * @param prefix
         * @return
         */
        @Override
        public String getNamespaceURI(String prefix) {
            if (prefix == null) {
                prefix = "xpdl"; //$NON-NLS-1$
            }
            return prefixToUri.get(prefix);
        }

        /**
         * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
         * 
         * @param namespaceURI
         * @return
         */
        @Override
        public String getPrefix(String namespaceURI) {
            String prefix = null;
            Iterator<String> i = getPrefixes(namespaceURI);
            if (i.hasNext()) {
                prefix = i.next();
            }
            return prefix;
        }

        /**
         * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
         * 
         * @param namespaceURI
         * @return
         */
        @Override
        public Iterator<String> getPrefixes(String namespaceURI) {
            List<String> prefixes = new ArrayList<String>();
            if (namespaceURI != null) {
                for (String prefix : prefixToUri.keySet()) {
                    if (namespaceURI.equals(prefixToUri.get(prefix))) {
                        prefixes.add(prefix);
                    }
                }
            }
            return prefixes.iterator();
        }
    }

}
