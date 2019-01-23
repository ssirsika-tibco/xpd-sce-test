/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.util.ArrayList;
import java.util.List;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.ElementNameAndAttributeQualifier;
import org.custommonkey.xmlunit.ElementNameAndTextQualifier;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.ElementQualifier;
import org.custommonkey.xmlunit.NodeDetail;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.tibco.xpd.n2.daa.test.elementqualifier.RequirmentsElementQualifier;
import com.tibco.xpd.n2.daa.test.util.XmlUtil;

/**
 * 
 * We cannot implement our own a
 * 
 * @author kupadhya
 * 
 */
public class RequirementsFileComparator extends XmlBasedComparator {
    private static final String COMPONENT = "component"; //$NON-NLS-1$

    private static final String REQUIREMENTS = "requirements"; //$NON-NLS-1$

    DifferenceListener myDifferenceListener =
            new RequirementsDifferenceListener();

    /**
     * The attribute component=
     * "UC2ANoneStartEventWithUserTasksFive.composite#_Oh89gJ3eEd64p862VNEB_A"
     * can help to understand which 'requirements' element to match. As
     * 'Oh89gJ3eEd64p862VNEB_A' is the UUID of the component but it will be
     * different between gold & test composite file.
     * 
     * So, we need to do a recursive match of child elements to identify which 2
     * requirements element to be used for matching Using child element for
     * 'RecursiveElementNameAndTextQualifier'.
     * 
     */
    ElementQualifier myElementQualifier = new RequirementsEQ();

    // ElementQualifier myElementQualifier = new RequirementsElementQualifier();

    @Override
    protected DifferenceListener getDifferenceListener() {
        return myDifferenceListener;
    }

    @Override
    protected ElementQualifier getElementQualifier() {
        return myElementQualifier;
    }

    class RequirementsDifferenceListener implements DifferenceListener {

        List<String> xPathToIgnore = new ArrayList<String>();

        private String REGEX1 =
                "/ApplicationRequirements/requirements/@component"; //$NON-NLS-1$

        void initialiseXPathArray() {
            if (xPathToIgnore.isEmpty()) {
                xPathToIgnore.add(REGEX1);
            }
        }

        /**
         * we need
         * 
         * @see org.custommonkey.xmlunit.DifferenceListener#differenceFound(org.custommonkey.xmlunit.Difference)
         * 
         * @param diff
         * @return
         */
        public int differenceFound(Difference diff) {
            NodeDetail controlNodeDetail = diff.getControlNodeDetail();
            String controlXPath = controlNodeDetail.getXpathLocation();
            NodeDetail testNodeDetail = diff.getTestNodeDetail();
            String testXPath = testNodeDetail.getXpathLocation();
            // replacing sequencing information
            System.out.println(" Difference in 2 documents, controlXPath " //$NON-NLS-1$
                    + controlXPath + " testXPath " + testXPath); //$NON-NLS-1$
            controlXPath = controlXPath.replaceAll("\\[\\d+\\]", ""); //$NON-NLS-1$ //$NON-NLS-2$
            initialiseXPathArray();
            if (xPathToIgnore.contains(controlXPath)) {
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
            // checking contents & ensuring that the difference is not about
            // whitespace
            Node cNode = diff.getControlNodeDetail().getNode();
            Node tNode = diff.getTestNodeDetail().getNode();
            if (cNode != null && tNode != null) {
                Text cNodeText = XmlUtil.extractText(cNode);
                Text tNodeText = XmlUtil.extractText(tNode);
                boolean similar = XmlUtil.similar(cNodeText, tNodeText);
                if (similar) {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
            }
            boolean recoverable = diff.isRecoverable();
            if (recoverable) {
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
            return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            // TODO: need to strip out the index & then do a String compare with
            // a list of Strings to see whether the difference is acceptable &
            // recoverable [diff.isRecoverable()].

            // NodeDetail testNodeDetail = diff.getTestNodeDetail();
            // Node testNode = testNodeDetail.getNode();
            // String controlLN = controlNode.getLocalName();
            // if (RequirementsFileComparator.REQUIREMENTS.equals(controlLN)) {
            // Node controlNodeNI =
            // controlNode
            // .getAttributes()
            // .getNamedItem(RequirementsFileComparator.COMPONENT);
            // String controlNV = controlNodeNI.getNodeValue();
            // Node testNodeNI =
            // testNode
            // .getAttributes()
            // .getNamedItem(RequirementsFileComparator.COMPONENT);
            // String testNV = testNodeNI.getNodeValue();
            // if (!controlNV.equals(testNV)) {
            // return
            // DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            // }
            // return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            // }
            // System.err.println("In the difference found method");
            // String xpathLocation =
            // diff.getTestNodeDetail().getXpathLocation();
            // boolean contains = xPathToIgnore.contains(xpathLocation);
            // if (contains) {
            // return
            // DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            // } else {
            // return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            // }
            // boolean recoverable = diff.isRecoverable();
            // if (recoverable) {
            // initialiseXPathArray();
            // if (xPathToIgnore.contains(controlXPath)) {
            // return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            // } else {
            // System.err.println(" Difference in the document "
            // + controlXPath);
            // return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            // }
            // } else {
            // return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            // }

        }

        public void skippedComparison(Node arg0, Node arg1) {
            System.out.println("In skippedComparison method"); //$NON-NLS-1$
        }
    }

    class RequirementsEQ extends ElementNameQualifier {
        @Override
        public boolean qualifyForComparison(Element control, Element test) {
            boolean bool = super.qualifyForComparison(control, test);
            if (bool) {
                ElementQualifier elementQualifierForElementName =
                        getElementQualifierForElementName(control);
                if (elementQualifierForElementName != null) {
                    boolean qualifyForComparison =
                            elementQualifierForElementName
                                    .qualifyForComparison(control, test);
                    return qualifyForComparison;
                } else {
                    throw new IllegalStateException(
                            "No Element Qualifier configured for element " //$NON-NLS-1$
                                    + control.getLocalName());
                }
            }
            return false;
        }
    }

    private ElementQualifier getElementQualifierForElementName(Element control) {
        String localName = control.getLocalName();
        if ("requirements".equals(localName)) { //$NON-NLS-1$
            return new RequirmentsElementQualifier();
        } else if ("requiresCapability".equals(localName)) { //$NON-NLS-1$
            return new ElementNameAndAttributeQualifier(new String[] { "id", //$NON-NLS-1$
                    "version", "type" }); //$NON-NLS-1$ //$NON-NLS-2$
        } else if ("includedResource".equals(localName)) { //$NON-NLS-1$
            return new ElementNameAndTextQualifier();
        } else if ("importPackage".equals(localName)) { //$NON-NLS-1$
            return new ElementNameAndAttributeQualifier(new String[] { "name" }); //$NON-NLS-1$
        } else if ("range".equals(localName)) { //$NON-NLS-1$
            return new ElementNameAndAttributeQualifier(new String[] { "lower", //$NON-NLS-1$
                    "upper" }); //$NON-NLS-1$
        }
        return null;
    }

    /**
     * 
     * @author kupadhya
     * 
     */
    class RequirementsRecursiveEQ extends RecursiveElementNameAndTextQualifier {
        @Override
        public boolean qualifyForComparison(Element control, Element test) {
            boolean qualifyForComparison = false;
            qualifyForComparison = super.qualifyForComparison(control, test);
            return qualifyForComparison;
        }
    }

    /**
     * IMPORTANT:: This will not work
     * 
     * @author kupadhya
     * 
     */
    class RequirementsElementQualifierLocal extends
            ElementNameAndAttributeQualifier {

        private static final String DELIMITER = "#"; //$NON-NLS-1$

        @Override
        protected boolean areAttributesComparable(Element control, Element test) {
            String controlAttr =
                    control.getAttribute(RequirementsFileComparator.COMPONENT);
            String testAttr =
                    test.getAttribute(RequirementsFileComparator.COMPONENT);
            if (controlAttr != null && testAttr != null) {
                int controlIndex =
                        controlAttr
                                .indexOf(RequirementsElementQualifierLocal.DELIMITER);
                if (controlIndex != -1) {
                    String controlCN = controlAttr.substring(controlIndex);
                    int testIndex =
                            testAttr
                                    .indexOf(RequirementsElementQualifierLocal.DELIMITER);
                    if (testIndex != -1) {
                        String testCN = testAttr.substring(testIndex);
                        if (controlCN.equals(testCN)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    private String removeIndexReference(String xPath) {
        xPath.replaceAll("[\\d]", ""); //$NON-NLS-1$ //$NON-NLS-2$

        return null;
    }

}
