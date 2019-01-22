/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author kupadhya
 * 
 */
public class XmlUtil {
    /**
     * Extract the normalized text from within an element
     * 
     * @param fromNode
     * @return extracted Text node (could be null)
     */
    public static Text extractText(Node fromNode) {
        fromNode.normalize();
        NodeList fromNodeList = fromNode.getChildNodes();
        Node currentNode;
        for (int i = 0; i < fromNodeList.getLength(); ++i) {
            currentNode = fromNodeList.item(i);
            if (currentNode.getNodeType() == Node.TEXT_NODE) {
                return (Text) currentNode;
            }
        }
        if (fromNode.getNodeType() == Node.TEXT_NODE) {
            return (Text) fromNode;
        }
        return null;
    }

    /**
     * Determine whether the text nodes contain similar values
     * 
     * @param control
     * @param test
     * @return true if text nodes are similar, false otherwise
     */
    public static boolean similar(Text control, Text test) {
        if (control == null) {
            return test == null;
        } else if (test == null) {
            return false;
        }
        return control.getNodeValue().trim().equals(test.getNodeValue().trim());
    }

}
