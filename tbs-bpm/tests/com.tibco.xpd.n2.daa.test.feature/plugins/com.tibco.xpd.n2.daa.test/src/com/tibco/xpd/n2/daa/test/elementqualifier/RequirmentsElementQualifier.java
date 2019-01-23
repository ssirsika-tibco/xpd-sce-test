/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.elementqualifier;

import java.util.ArrayList;
import java.util.List;

import org.custommonkey.xmlunit.ElementNameQualifier;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.tibco.xpd.n2.daa.test.util.XmlUtil;

/**
 * @author kupadhya
 * 
 */
public class RequirmentsElementQualifier extends ElementNameQualifier {

    private static final String INCLUDED_RESOURCE = "includedResource"; //$NON-NLS-1$

    private static final String REQUIRES_CAPABILITY = "requiresCapability"; //$NON-NLS-1$

    @Override
    public boolean qualifyForComparison(Element control, Element test) {
        boolean bool = super.qualifyForComparison(control, test);
        if (bool) {
            bool = checkRCAndIRElements(control, test);
        }
        return bool;
    }

    private boolean checkRCAndIRElements(Element control, Element test) {
        boolean toReturn = true;
        NodeList controlChildren = control.getChildNodes();
        NodeList testChildren = test.getChildNodes();
        int controlLength = controlChildren.getLength();
        if (countNodesWithoutConsecutiveTextNodes(controlChildren) != countNodesWithoutConsecutiveTextNodes(testChildren)) {
            toReturn = false;
            return toReturn;
        }
        List<Node> controlRCNodes = new ArrayList<Node>();
        List<Node> testRCNodes = new ArrayList<Node>();
        List<Node> controlIRNodes = new ArrayList<Node>();
        List<Node> testIRNodes = new ArrayList<Node>();
        for (int index = 0; index < controlLength; index++) {
            Node controlItem = controlChildren.item(index);
            Node testItem = testChildren.item(index);
            if (controlItem.getNodeType() != testItem.getNodeType()) {
                toReturn = false;
                break;
            }
            if (Node.ELEMENT_NODE == controlItem.getNodeType()) {
                String controlItemName = controlItem.getLocalName();
                String testItemName = testItem.getLocalName();
                if (RequirmentsElementQualifier.REQUIRES_CAPABILITY
                        .equals(controlItemName)
                        && RequirmentsElementQualifier.REQUIRES_CAPABILITY
                                .equals(testItemName)) {
                    controlRCNodes.add(controlItem);
                    testRCNodes.add(testItem);
                } else if (RequirmentsElementQualifier.INCLUDED_RESOURCE
                        .equals(controlItemName)
                        && RequirmentsElementQualifier.INCLUDED_RESOURCE
                                .equals(testItemName)) {
                    controlIRNodes.add(controlItem);
                    testIRNodes.add(testItem);
                }
            }
        }
        toReturn = compareNodes(controlRCNodes, testRCNodes);
        if (toReturn) {
            toReturn = compareNodes(controlIRNodes, testIRNodes);
        }
        return toReturn;
    }

    /**
     * For every node in the control nodes list, try to find the matching test
     * node, if not found then return false
     * 
     * @param controlNodes
     * @param testNodes
     * @return
     */
    private boolean compareNodes(List<Node> controlNodes, List<Node> testNodes) {
        boolean toReturn = true;
        int controlSize = controlNodes.size();
        int testSize = testNodes.size();
        for (int index = 0; index < controlSize; index++) {
            Node controlNode = controlNodes.get(index);
            boolean testNodeMatched = false;
            for (int testIndex = 0; testIndex < testSize; testIndex++) {
                Node testNode = testNodes.get(testIndex);
                boolean compareNodeValueAndAttributes =
                        compareNodeValueAndAttributes(controlNode, testNode);
                if (compareNodeValueAndAttributes) {
                    testNodeMatched = true;
                    break;
                }
            }
            if (!testNodeMatched) {
                toReturn = false;
                break;
            }
        }
        return toReturn;
    }

    /**
     * compare values of all attributes & value of the element (if any)
     * 
     * @param controlNode
     * @param testNode
     * @return
     */
    private boolean compareNodeValueAndAttributes(Node controlNode,
            Node testNode) {
        boolean toReturn = true;
        NamedNodeMap controlAttr = controlNode.getAttributes();
        NamedNodeMap testAttr = testNode.getAttributes();
        Text cNodeValue = XmlUtil.extractText(controlNode);
        Text tNodeValue = XmlUtil.extractText(testNode);
        if (cNodeValue != null && tNodeValue != null) {
            if (!cNodeValue.getNodeValue().equals(tNodeValue.getNodeValue())) {
                toReturn = false;
            }
        }
        // if node value do not match then return false
        if (!toReturn) {
            return toReturn;
        }
        for (int index = 0; index < controlAttr.getLength(); index++) {
            Node cAttribute = controlAttr.item(index);
            Node tAttribute = testAttr.getNamedItem(cAttribute.getNodeName());
            if (!cAttribute.getNodeValue().equals(tAttribute.getNodeValue())) {
                toReturn = false;
                break;
            }
        }
        return toReturn;
    }

    /**
     * Calculates the number of Nodes that are either not Text nodes or are Text
     * nodes whose previous sibling isn't a Text node as well. I.e. consecutive
     * Text nodes are counted as a single node.
     */
    private static int countNodesWithoutConsecutiveTextNodes(NodeList l) {
        int count = 0;
        boolean lastNodeWasText = false;
        final int length = l.getLength();
        for (int i = 0; i < length; i++) {
            Node n = l.item(i);
            if (!lastNodeWasText || n.getNodeType() != Node.TEXT_NODE) {
                count++;
            }
            lastNodeWasText = n.getNodeType() == Node.TEXT_NODE;
        }
        return count;
    }
}
