/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.xmlunit.xsd;

import java.io.IOException;
import java.io.Reader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.xmlunit.TypeReferenceNodeValueComparator;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;

/**
 * 
 * 
 * @author bharge
 * @since 13 Jun 2014
 */
public class WsdlDiff extends XsdDiff {

    /**
     * @param controlDoc
     * @param testDoc
     */
    public WsdlDiff(Document controlDoc, Document testDoc) {
        super(controlDoc, testDoc);
    }

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public WsdlDiff(InputSource control, InputSource test) throws SAXException,
            IOException {
        super(control, test);
    }

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public WsdlDiff(Reader control, Reader test) throws SAXException,
            IOException {
        super(control, test);
    }

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public WsdlDiff(String control, String test) throws SAXException,
            IOException {
        super(control, test);
    }

    /**
     * @see com.tibco.xpd.core.xmlunit.xsd.XsdDiff#init()
     * 
     */
    @Override
    protected void init() {
        super.init();

        /* Qualify comparisons of message elements */
        XmlDiffSequenceQualifier sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("message"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of port type element */
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("portType"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        // startingVariables/variables elements
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        "/definitions/types/schema/simpleType"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of fault element */
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("fault"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of operation element */
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("operation"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of operation element */
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("service"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of binding element */
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("binding"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of partner link element */
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                        "partnerLinkType"), new XmlDiffNodePath("@name")); //$NON-NLS-1$ //$NON-NLS-2$
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of part element */
        // RS - Parts should be in the mentioned sequence.
        // sequenceQualifier =
        // new XmlDiffSequenceQualifier(new XmlDiffNodePath("part"),
        // new XmlDiffNodePath("@name"));
        // addSequenceElementQualifier(sequenceQualifier);
        addSequenceElementQualifier(sequenceQualifier);
        /* Qualify comparisons of XSD Schema element */
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("schema"), //$NON-NLS-1$
                        new XmlDiffNodePath("@targetNamespace")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);

        // XSD elements sequenced and compared by name.
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("element"), //$NON-NLS-1$
                        new XmlDiffNodePath("@name")); //$NON-NLS-1$
        addSequenceElementQualifier(sequenceQualifier);
        // Ignoring the type for elements contained in the XSD Complex type
        String[] arr = new String[] { "element", "@type" }; //$NON-NLS-1$ //$NON-NLS-2$
        addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(arr, false)));

        String[] arr1 = new String[] { "extension", "@base" }; //$NON-NLS-1$ //$NON-NLS-2$
        addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(arr1, false)));

        // Allow for the fact that soem attr values (liek input message type
        // reference can have differening ns prefixes within the actual
        // attr value
        TypeReferenceNodeValueComparator typeRefComparator =
                new TypeReferenceNodeValueComparator();

        this.addCustomComparison(new XmlDiffNodePath("@message"),
                typeRefComparator);

    }
}
