/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.qa.test.nimbus;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;

/**
 * 
 * 
 * @author aallway
 * @since 12 Nov 2012
 */
public class NimbusImportXpdlDiff extends XmlDiff {

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public NimbusImportXpdlDiff(InputSource control, InputSource test)
            throws SAXException, IOException {
        super(control, test);

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "/Package/@Id"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "/Package/PackageHeader/Created"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Association/Object/@Id"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Association/@Id"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Association/@Source"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Association/@Target"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Activity/@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Artifact/@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "FormalParameter/@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "DataField/@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Transition/@Id"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Transition/@From"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Transition/@To"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "MessageIn/@Id"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "MessageOut/@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "/Package/ExternalPackages/ExternalPackage/@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "Participant/@Id"))); //$NON-NLS-1$

        this.addIgnoreNode(new XmlDiffIgnoreNode(
                new XmlDiffNodePath(
                        "Activity/Performers/Performer/" + XmlDiffNodePath.TEXT_CONTENT))); //$NON-NLS-1$

        /* Some trivial unimportant stuff that may change from time to time. */
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "NodeGraphicsInfo/@Height"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "NodeGraphicsInfo/@Width"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "NodeGraphicsInfo/Coordinates/@XCoordinate"))); //$NON-NLS-1$
        this.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                "NodeGraphicsInfo/Coordinates/@YCoordinate"))); //$NON-NLS-1$

    }

}
