/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.core.xmlunit.xsd;

import java.io.IOException;
import java.io.Reader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.xmlunit.TypeReferenceNodeValueComparator;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;

/**
 * XML Differencer for XSD differencing...
 * <p>
 * Unless overridden...
 * <li>Ignores xsd:documentation nodes</li>
 * <li>Ignores Id attributes</li>
 * <li>Allow @base, @type and @ref values to be in different namespaces</li>.
 * <li>Allows xsd:element xsd:complexType and xsd:simpleType to appear in any
 * order.</li>
 * 
 * <p>
 * And to allow imports
 * 
 * @author aallway
 * @since 15 Apr 2011
 */
public class XsdDiff extends XmlDiff {

    /**
     * @see com.tibco.xpd.core.xmlunit.XmlDiff#init()
     * 
     */
    @Override
    protected void init() {
        super.init();

        if (ignoreDocumentation()) {
            this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    new String[] { "xsd:documentation" }, false)));
        }

        if (ignoreIdAttributes()) {
            this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath("@id")));

        }

        if (allowForDifferentTypeRefNSPrefixes()) {
            /*
             * for objects that reference namespaced entities allow the
             * namespace prefix to be different AS LONG as they are for same
             * namespace URI
             */
            TypeReferenceNodeValueComparator typeRefComparator =
                    new TypeReferenceNodeValueComparator();

            this.addCustomComparison(new XmlDiffNodePath("@ref"),
                    typeRefComparator);
            this.addCustomComparison(new XmlDiffNodePath("@type"),
                    typeRefComparator);
            this.addCustomComparison(new XmlDiffNodePath("@base"),
                    typeRefComparator);
        }

        if (ignoreElementAndTypeOrder()) {
            this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("xsd:element"),
                    new XsdNameOrRefQualifier()));

            this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("xsd:complexType"),
                    new XsdNameOrRefQualifier()));

            this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("xsd:simpleType"),
                    new XsdNameOrRefQualifier()));

        }

        if (ignoreImportOrder()) {
            this.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("xsd:import"), new XmlDiffNodePath(
                            "@namespace")));
        }

    }

    /**
     * 
     * @return <code>true</code> if all documentation elements should be ignored
     *         else <code>false</code> (The default implementation =
     *         <code>true</code>)
     */
    protected boolean ignoreDocumentation() {
        return true;
    }

    /**
     * 
     * @return <code>true</code> to allow the namespace prefixes in the
     *         <b>values</b> of certain attributes that reference namespaced
     *         types to be different <b>provided</b> thatthat are equivalent
     *         namespaces else <code>false</code> if namespace prefix must be
     *         the same (The default implementation = <code>true</code>)
     */
    protected boolean allowForDifferentTypeRefNSPrefixes() {
        return true;
    }

    /**
     * @return <code>true</code> to ignore the order in which schema elements,
     *         complexTypes and simpleTypes appear in the list of elements else
     *         <code>false</code> (The default implementation =
     *         <code>true</code>)
     */
    protected boolean ignoreElementAndTypeOrder() {
        return true;
    }

    /**
     * @return <code>true</code> to ignore the order in which schema elements,
     *         complexTypes and simpleTypes appear in the list of elements else
     *         <code>false</code> (The default implementation =
     *         <code>true</code>)
     */
    protected boolean ignoreImportOrder() {
        return true;
    }

    /**
     * @return <code>true</code> to ignore differences in the value of id
     *         attributes else <code>false</code> (The default implementation =
     *         <code>true</code>)
     */
    protected boolean ignoreIdAttributes() {
        return true;
    }

    /**
     * @param controlDoc
     * @param testDoc
     */
    public XsdDiff(Document controlDoc, Document testDoc) {
        super(controlDoc, testDoc);
    }

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public XsdDiff(InputSource control, InputSource test) throws SAXException,
            IOException {
        super(control, test);
    }

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public XsdDiff(Reader control, Reader test) throws SAXException,
            IOException {
        super(control, test);
    }

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public XsdDiff(String control, String test) throws SAXException,
            IOException {
        super(control, test);
    }

}
