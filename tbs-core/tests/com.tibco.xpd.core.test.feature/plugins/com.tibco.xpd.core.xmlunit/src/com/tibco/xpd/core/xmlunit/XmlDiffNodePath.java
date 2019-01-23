package com.tibco.xpd.core.xmlunit;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * NodePath
 * <p>
 * Simple path handling for XmlDiff comparisons, paths may be absolute (start
 * with "/"), simple name (prefixed with "@" to signify an attribute rather than
 * xml element, or relative ("grandparent/parent/name").
 * <p>
 * Elements can be Namespace prefixed - if they are then the prefix will be
 * taken into account when matches(Node node) is called - otherwise matching
 * will be based on node's local name only.
 * 
 * @author aallway
 * @since 3.3 (15 Sep 2009)
 */
public class XmlDiffNodePath {
    public static String SEPARATOR = "/"; //$NON-NLS-1$

    public static String ATTRIBUTE_PREFIX = "@"; //$NON-NLS-1$

    public static String NS_PREFIX = ":";

    /**
     * When the path refers to the text content of an element, use this as the
     * name.
     */
    public static String TEXT_CONTENT = "!!text!!"; //$NON-NLS-1$

    public static String CDATA_CONTENT = "!!cdata!!"; //$NON-NLS-1$

    private String path;

    public XmlDiffNodePath(String[] segments, boolean isAbsolute) {
        path = ""; //$NON-NLS-1$

        for (String seg : segments) {
            if (path.length() > 0 || (path.length() == 0 && isAbsolute)) {
                path += SEPARATOR;
            }
            path += seg;
        }
    }

    public XmlDiffNodePath(String path) {
        this.path = path;
    }

    /**
     * @return The name of the attribute or element.
     */
    public String getNodeName() {
        String name = ""; //$NON-NLS-1$

        int idx = path.lastIndexOf(SEPARATOR);
        if (idx >= 0) {
            name = path.substring(idx + 1);
        } else {
            name = path;
        }

        if (name.startsWith(ATTRIBUTE_PREFIX)) {
            name = name.substring(1);
        }

        return name;
    }

    /**
     * @return The attribute/element name without NS prefix if one was defined.
     */
    public String getLocalNodeName() {
        String myName = getNodeName();

        int idx = myName.indexOf(NS_PREFIX);

        if (idx >= 0) {
            return myName.substring(idx + 1);
        }

        return myName; // $NON-NLS-1$
    }

    /**
     * @return namespace prefix if defined or ""
     */
    public String getNsPrefix() {
        String myName = getNodeName();
        if (myName != null) {
            int idx = myName.indexOf(NS_PREFIX);

            if (idx >= 0) {
                return myName.substring(0, idx);
            }
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * @return Parent node path of this path or null if no parent
     */
    public XmlDiffNodePath getParentNodePath() {
        int idx = path.lastIndexOf(SEPARATOR);
        if (idx > 0) { // >0 to ignore root "/"
            String newPath = path.substring(0, idx);
            if (newPath.length() != 0) {
                return new XmlDiffNodePath(newPath);
            }
        }

        return null;
    }

    /**
     * @return true if path represents an attribute.
     */
    public boolean isAttributeNode() {
        String[] segs = path.split(SEPARATOR);
        if (segs != null && segs.length >= 1) {
            if (segs[segs.length - 1].startsWith(ATTRIBUTE_PREFIX)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return true if the path is absolute (starts from root of document).
     */
    public boolean isAbsoluteNodePath() {
        return path.startsWith(SEPARATOR);
    }

    /**
     * @return true of the path represents the text content of an element.
     */
    public boolean isTextNode() {
        if (TEXT_CONTENT.equals(getNodeName())) {
            return true;
        }

        return false;
    }

    /**
     * @return true of the path represents the CDATA content of an element.
     */
    public boolean isCdataNode() {
        if (CDATA_CONTENT.equals(getNodeName())) {
            return true;
        }

        return false;
    }

    /**
     * @return Each segment of path as a separate NodePath.
     */
    public XmlDiffNodePath[] getPathSegments() {
        String[] segs = path.split(SEPARATOR);

        XmlDiffNodePath[] paths = new XmlDiffNodePath[segs.length];

        for (int i = 0; i < segs.length; i++) {
            paths[i] = new XmlDiffNodePath(segs[i]);
        }

        return paths;
    }

    /**
     * @param node
     * @return true if the given DOM Node is located in a place that matches the
     *         given path.
     */
    public boolean matches(Node node) {
        boolean matches = false;

        if (isAttributeNode() == (node instanceof Attr)) {

            String localNodeName = getLocalNodeName();

            /*
             * If the (path specifies the text content oif and element AND the
             * node is a test node) OR (if the non-ns-prefixed names match).
             */
            if ((TEXT_CONTENT.equals(localNodeName)
                    && node.getNodeType() == node.TEXT_NODE)
                    || (CDATA_CONTENT.equals(localNodeName)
                            && node.getNodeType() == node.CDATA_SECTION_NODE)
                    || localNodeName.equals(node.getLocalName())) {
                /*
                 * If prefix is specified in our path then must match the prefix
                 * in the node otherwise we'll just be simple and match on
                 */
                String nsPrefix = getNsPrefix();

                if (nsPrefix != null && nsPrefix.length() > 0) {
                    /* When prefix defined must match that given. */
                    if (nsPrefix.equals(node.getPrefix())) {
                        matches = true;
                    }

                } else {
                    /* Match on local name only. */
                    matches = true;
                }

                /*
                 * If the end-node of path matches, get the parent and check
                 * that that matches.
                 */
                if (matches) {
                    matches = false;

                    Node parentNode = getParentElement(node);

                    XmlDiffNodePath parentPath = getParentNodePath();

                    if (parentPath != null) {
                        if (parentNode != null) {
                            /* Matches if parent matches. */
                            matches = parentPath.matches(parentNode);
                        } else {
                            /* Path has parent but node does not! */
                        }

                    } else if (isAbsoluteNodePath()) {
                        /*
                         * If path parent is null because this is root element -
                         * make sure that the node is a root element too.
                         */
                        if (node.getParentNode() instanceof Document) {
                            matches = true;
                        }

                    } else {
                        /*
                         * Path has no parent but is not absolute, therefore if
                         * we matched so far then it matches.
                         */
                        matches = true;
                    }
                }
            }
        }

        return matches;
    }

    /**
     * @param node
     * @return Helper that gets parent element regardless of whether given node
     *         is an element or attribute.
     */
    public static Node getParentElement(Node node) {
        Node parentNode = null;

        if (node instanceof Attr) {
            parentNode = ((Attr) node).getOwnerElement();
        } else {
            parentNode = node.getParentNode();
        }
        return parentNode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof XmlDiffNodePath) {
            return path.equals(((XmlDiffNodePath) obj).path);
        } else if (obj instanceof Node) {
            return this.matches((Node) obj);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getPath();
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

}