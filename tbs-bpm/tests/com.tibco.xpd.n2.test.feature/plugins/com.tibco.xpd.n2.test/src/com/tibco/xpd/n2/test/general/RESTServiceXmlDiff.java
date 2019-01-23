/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.test.general;

import java.io.IOException;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.NodeDetail;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Used during testing to compare files containing REST services
 * 
 * @author agondal
 * @since 9 Jan 2013
 */
public class RESTServiceXmlDiff extends XpdlDiff {

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public RESTServiceXmlDiff(InputSource control, InputSource test)
            throws SAXException, IOException {
        super(control, test);

        // Only check for difference inside the REST services

        RESTServiceDifferenceListener restDiffListener =
                new RESTServiceDifferenceListener(getDifferenceListener());

        overrideDifferenceListener(restDiffListener);

    }

    private static class RESTServiceDifferenceListener implements
            DifferenceListener {

        private DifferenceListener delegateListener;

        /**
         * @param delegateListener
         */
        public RESTServiceDifferenceListener(DifferenceListener delegateListener) {
            super();
            this.delegateListener = delegateListener;
        }

        /**
         * @param difference
         * @return
         * @see org.custommonkey.xmlunit.DifferenceListener#differenceFound(org.custommonkey.xmlunit.Difference)
         */
        @Override
        public int differenceFound(Difference difference) {

            NodeDetail controlNodeDetail = difference.getControlNodeDetail();

            // only consider REST service related difference

            if (controlNodeDetail.getNode() != null) {

                if (controlNodeDetail.getXpathLocation()
                        .contains("/RESTServices")) { //$NON-NLS-1$

                    return delegateListener.differenceFound(difference);

                }
            }

            return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
        }

        /**
         * @param control
         * @param test
         * @see org.custommonkey.xmlunit.DifferenceListener#skippedComparison(org.w3c.dom.Node,
         *      org.w3c.dom.Node)
         */
        @Override
        public void skippedComparison(Node control, Node test) {
            delegateListener.skippedComparison(control, test);
        }

    }

}
