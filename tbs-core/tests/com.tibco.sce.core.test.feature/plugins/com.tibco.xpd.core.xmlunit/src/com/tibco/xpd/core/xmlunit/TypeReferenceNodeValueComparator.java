package com.tibco.xpd.core.xmlunit;

import java.util.Comparator;

import org.w3c.dom.Node;

/**
 * Compares that the text value of a node treated as a reference to some entity
 * in either the same namespace as the node or some prefix-specified namespace
 * matches in the given nodes contents.
 * <p>
 * i.e. passed a control node attribute="ns1:XXX" and and testNode
 * attribute="anotherns:XXX" then this comparator will return equal IF XXX is
 * the same AND ns1 / anotherns have the same namespace URI.
 * <p>
 * Nominally For use with
 * {@link XmlDiff#addCustomComparison(XmlDiffNodePath, Comparator)}
 * 
 * @author aallway
 * @since 14 Apr 2011
 */
public class TypeReferenceNodeValueComparator implements Comparator<Node> {

    public static final int NAMESPACE_NOTPRESENT = -1;

    public static final int NAMESPACE_DIFFERENT = -999;

    public static final int TYPENAME_DIFFERENT = 999;

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * 
     * @param controlNode
     * @param testNode
     * @return 0 if the nodes reference the same type (etc) - uses the namespace
     *         prefix to look up the actual then compares the type names without
     *         prefix.
     */
    public int compare(Node controlMode, Node testNode) {
        String controlNamespace = getNamespace(controlMode);
        String testNamespace = getNamespace(testNode);

        if (controlNamespace == null || controlNamespace.length() == 0
                || testNamespace == null || testNamespace.length() == 0) {
            return NAMESPACE_NOTPRESENT;
        }

        if (!controlNamespace.equals(testNamespace)) {
            return NAMESPACE_DIFFERENT;
        }

        String controlTypeName = getTypeName(controlMode);
        String testTypeName = getTypeName(testNode);

        if (!controlTypeName.equals(testTypeName)) {
            return TYPENAME_DIFFERENT;
        }

        return 0;
    }

    private String getNamespace(Node n) {
        String namespace = null;

        String value = n.getNodeValue();
        if (value != null && value.length() > 0) {
            String prefix = null;

            int i = value.indexOf(":");
            if (i >= 0) {
                prefix = value.substring(0, i);
            }

            if (prefix == null || prefix.length() == 0) {
                namespace = n.getOwnerDocument().lookupNamespaceURI(null);
            } else {
                namespace = n.getOwnerDocument().lookupNamespaceURI(prefix);
            }
        }

        return namespace;
    }

    private String getTypeName(Node n) {
        String typeName = null;

        String value = n.getNodeValue();
        if (value != null && value.length() > 0) {

            int i = value.indexOf(":");
            if (i >= 0) {
                if ((i + 1) < value.length()) {
                    typeName = value.substring(i + 1);
                }
            } else {
                typeName = value;
            }

        }

        return typeName == null ? "" : typeName; //$NON-NLS-1$
    }

}