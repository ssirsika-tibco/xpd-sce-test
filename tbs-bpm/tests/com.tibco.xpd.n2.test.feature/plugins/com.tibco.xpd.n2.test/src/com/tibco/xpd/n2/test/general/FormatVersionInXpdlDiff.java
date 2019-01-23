/*
 * Copyright (c) TIBCO Software Inc 2004, 2018. All rights reserved.
 */

package com.tibco.xpd.n2.test.general;

import java.io.IOException;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;

/**
 * Extends XpdlDiff class to ignore format version in xpdl file.
 *
 * @author bharge
 * @since Oct 4, 2018
 */
public class FormatVersionInXpdlDiff extends XpdlDiff {

    /**
     * @param goldSource
     * @param testSource
     * @throws IOException
     * @throws SAXException
     */
    public FormatVersionInXpdlDiff(InputSource goldSource,
            InputSource testSource) throws SAXException, IOException {
        super(goldSource, testSource);

        final DifferenceListener defaultDiffListener =
                getDifferenceListener();

        DifferenceListener customDl = new DifferenceListener() {

            @Override
            public void skippedComparison(Node control, Node test) {
                defaultDiffListener.skippedComparison(control, test);
            }

            @Override
            public int differenceFound(Difference difference) {
                Node parentNode = XmlDiffNodePath.getParentElement(
                        difference.getTestNodeDetail().getNode());

                if (parentNode != null
                        && parentNode.getAttributes() != null) {
                    NamedNodeMap attributes = parentNode.getAttributes();

                    /*
                     * Ignore difference if the node being compared is
                     * FormatVersion!
                     */
                    if (null != attributes.getNamedItem("Name") //$NON-NLS-1$
                            && attributes.getNamedItem("Name") //$NON-NLS-1$
                                    .getTextContent()
                                    .equalsIgnoreCase("FormatVersion")) { //$NON-NLS-1$

                        return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                    }
                }

                return defaultDiffListener.differenceFound(difference);
            }
        };

        overrideDifferenceListener(customDl);
    }

}