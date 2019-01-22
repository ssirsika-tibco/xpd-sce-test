/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.InputStream;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.NodeDetail;
import org.eclipse.core.runtime.IStatus;
import org.w3c.dom.Node;

/**
 * @author kupadhya
 * 
 */
public class XMIFileComparator extends XmlBasedComparator {

    DifferenceListener myDifferenceListener = new XMIDifferenceListener();

    @Override
    protected DifferenceListener getDifferenceListener() {
        return myDifferenceListener;
    }

    class XMIDifferenceListener implements DifferenceListener {

        public int differenceFound(Difference diff) {

            NodeDetail testNodeDetail = diff.getTestNodeDetail();
            Node node = testNodeDetail.getNode();
            if (node == null) {

                return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            }
            String localName = node.getLocalName();
            if ("installHome".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            } else if ("pluginsDirectory".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;

            } else if ("version".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            } else if ("location".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;

            } else if ("key".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            } else if ("value".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            } else if ("rangeLow".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            } else if ("rangeHigh".equals(localName)) { //$NON-NLS-1$

                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            } else {

                System.err.println("Difference reported for " + localName); //$NON-NLS-1$
            }
            return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
        }

        public void skippedComparison(Node arg0, Node arg1) {

            System.out.println("In skippedComparison method"); //$NON-NLS-1$
        }

    }

    public static void main(String args[]) {

        InputStream goldStream =
                XMIFileComparator.class.getClassLoader()
                        .getResourceAsStream("machine.gold.xmi"); //$NON-NLS-1$
        InputStream genStream =
                XMIFileComparator.class.getClassLoader()
                        .getResourceAsStream("machine.gen.xmi"); //$NON-NLS-1$
        XMIFileComparator comparator = new XMIFileComparator();
        IStatus compareStatus =
                comparator.compareContents(goldStream, genStream, null);
        if (!compareStatus.isOK()) {

            System.out.println(compareStatus.getMessage());
        } else {

            System.out.println("compare success"); //$NON-NLS-1$
        }
    }
}
