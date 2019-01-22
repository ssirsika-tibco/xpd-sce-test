/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;

/**
 * {@link XpdPropertyInfoNode}'s are non-comparison nodes included as children
 * of {@link XpdCompareNode}'s. In the left/right merge viewer these are always
 * displayed for the current selected compare node regardless of whether their
 * displayed content is different on either side.
 * 
 * @author aallway
 * @since 18 Oct 2010
 */
public class XpdPropertyInfoNode extends XpdTextCompareNode {

    private int orderPosition;

    private String infoId;

    /**
     * @param infoString
     *            Text to display
     * @param orderPosition
     *            So that info's can be ordered other than by name. Info nodes
     *            are better positioned by type of info rather than name for
     *            conistent location on the list.
     * @param parentCompareNode
     *            The parenbt compare node of this info node.
     * @param contentType
     *            Content type (governs the merge viewer to be used when
     *            selected in top window - therefore should always be the same
     *            as the parent node so that the same merge tree viewers are
     *            used)
     * @param infoId
     *            id for this info node within it's parent.
     */
    public XpdPropertyInfoNode(String infoString, int orderPosition,
            Object parentCompareNode, String contentType, String infoId) {
        super(infoString, infoString, parentCompareNode, //$NON-NLS-1$ //$NON-NLS-2$
                contentType);
        this.orderPosition = orderPosition;
        this.infoId = infoId;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdTextCompareNode#calculateLocationInParent()
     * 
     * @return
     */
    @Override
    protected String calculateLocationInParent() {
        return infoId;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        return XpdResourcesUIActivator.getDefault().getImageRegistry()
                .get(XpdResourcesUIConstants.ICON_INFORMATION);
    }

    /**
     * @return the orderPosition
     */
    public int getOrderPosition() {
        return orderPosition;
    }
}
