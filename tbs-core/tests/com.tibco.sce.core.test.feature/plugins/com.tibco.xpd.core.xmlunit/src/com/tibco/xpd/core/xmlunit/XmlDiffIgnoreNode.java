package com.tibco.xpd.core.xmlunit;

import org.w3c.dom.Node;

/**
 * IgnoreNode
 * <p>
 * Configuration of a node to ignore. The node is specified as a NodePath which
 * can either be a simple element/attribute name, a relative path or an absolute
 * path.
 * <p>
 * For instance:
 * <li>"@Id" - Will ignore all differences in all XML attributes called Id
 * regardless of the parent element.</li>
 * 
 * @author aallway
 * @since 3.3 (15 Sep 2009)
 */
public class XmlDiffIgnoreNode {

    private XmlDiffNodePath path;

    public XmlDiffIgnoreNode(XmlDiffNodePath path) {
        this.path = path;
    }

    /**
     * @return the path to ignore
     */
    public XmlDiffNodePath getPath() {
        return path;
    }

    public boolean matches(Node node) {
        return path.matches(node);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof XmlDiffIgnoreNode) {
            return path.equals(((XmlDiffIgnoreNode) obj).path);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ignore Node: " + path;
    }
}
