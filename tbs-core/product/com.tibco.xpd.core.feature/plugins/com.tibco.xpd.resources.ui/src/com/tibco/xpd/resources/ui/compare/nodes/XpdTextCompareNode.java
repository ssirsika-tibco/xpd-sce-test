/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes;

/**
 * Simple text node for comparison viewer
 * 
 * @author aallway
 * @since 29 Sep 2010
 */
public class XpdTextCompareNode extends XpdCompareNode {

    private String text;

    private Object rawObject;

    /**
     * @param rawObject
     *            The raw object that this text node represents.
     * @param text
     * @param parentCompareNode
     * @param contentType
     */
    public XpdTextCompareNode(String text, Object rawObject,
            Object parentCompareNode, String contentType) {
        super(parentCompareNode, contentType);
        this.text = text;
        this.rawObject = rawObject;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
     * 
     * @return
     */
    @Override
    protected Object[] createChildren() {
        return null;
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        /* Make the text content displayable on a single line for name! */
        String name = text.replaceAll("\r", "\\\\r"); //$NON-NLS-1$ //$NON-NLS-2$
        name = name.replaceAll("\n", "\\\\n"); //$NON-NLS-1$ //$NON-NLS-2$
        name = name.replaceAll("\t", "\\\\t"); //$NON-NLS-1$ //$NON-NLS-2$
        return name;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#calculateLocationInParent()
     * 
     * @return
     */
    @Override
    protected String calculateLocationInParent() {
        return "textValue";
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#getTextContent()
     * 
     * @return
     */
    @Override
    public String getTextContent() {
        return text;
    }

    /**
     * @return The raw object that this text node represents.
     */
    public Object getRawObject() {
        return rawObject;
    }
}
