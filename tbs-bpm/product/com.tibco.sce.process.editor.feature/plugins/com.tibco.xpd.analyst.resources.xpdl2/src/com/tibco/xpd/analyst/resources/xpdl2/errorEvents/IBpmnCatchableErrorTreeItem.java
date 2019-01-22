/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.errorEvents;

import java.util.Collection;

import org.eclipse.swt.graphics.Image;

/**
 * Interface for an object that can appear in the hierarchical list of catchable
 * errors and their parent folders.
 * 
 * @author aallway
 * @since 3.2
 */
public interface IBpmnCatchableErrorTreeItem {

    /**
     * @return the item's text label.
     */
    String getLabel();

    /**
     * @return The item's icon image
     */
    Image getImage();

    /**
     * @return Can this item have child items?
     */
    boolean hasChildren();

    /**
     * @return Get the children for this item.
     */
    Collection<IBpmnCatchableErrorTreeItem> getChildren();

    /**
     * @return Parent catchable errors folder or null if top level.
     */
    BpmnCatchableErrorFolder getParentFolder();
    
    void setParent(BpmnCatchableErrorFolder parentFolder);

}
