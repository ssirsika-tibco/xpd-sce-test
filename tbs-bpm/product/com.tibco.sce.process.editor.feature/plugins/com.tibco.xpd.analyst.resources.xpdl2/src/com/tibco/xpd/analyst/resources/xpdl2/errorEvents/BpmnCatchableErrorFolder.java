/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.graphics.Image;

/**
 * Folder of IBpmnCatchableError's / BpmnCatchableErrorFolder's
 * 
 * @author aallway
 * @since 3.2
 */
public final class BpmnCatchableErrorFolder implements IBpmnCatchableErrorTreeItem {
    private List<IBpmnCatchableErrorTreeItem> children =
            new ArrayList<IBpmnCatchableErrorTreeItem>();

    private boolean changedSinceSort = false;
    
    private String label;
    
    private Image image;

    private BpmnCatchableErrorFolder parentFolder;
    
    /**
     * Create a folder of catchable errors with the given label and image 
     * 
     * @param label Label
     * @param image Image (or null)
     */
    public BpmnCatchableErrorFolder(String label, Image image) {
        super();
        this.label = label;
        this.image = image;
    }

    public Collection<IBpmnCatchableErrorTreeItem> getChildren() {
        if (changedSinceSort) {
            changedSinceSort = false;
            Collections.sort(children,
                    new Comparator<IBpmnCatchableErrorTreeItem>() {

                        public int compare(IBpmnCatchableErrorTreeItem o1,
                                IBpmnCatchableErrorTreeItem o2) {
                            if (o1 instanceof BpmnCatchableErrorFolder
                                    && !(o2 instanceof BpmnCatchableErrorFolder)) {
                                // Folders before other entries
                                return -1;
                            } else if (o2 instanceof BpmnCatchableErrorFolder
                                    && !(o1 instanceof BpmnCatchableErrorFolder)) {
                                // Folders before other entries
                                return 1;
                            } 
                            
                            String l1 = o1.getLabel();
                            l1 = (l1 != null ? l1 : ""); //$NON-NLS-1$ 

                            String l2 = o2.getLabel();
                            l2 = (l2 != null ? l2 : ""); //$NON-NLS-1$ 

                            return l1.compareTo(l2);
                        }
                    });
            

        }
        
        return Collections.unmodifiableCollection(children);
    }

    public void addChild(IBpmnCatchableErrorTreeItem child) {
        children.add(child);
        child.setParent(this);
        changedSinceSort = true;
    }

    public void removeChild(IBpmnCatchableErrorTreeItem child) {
        children.remove(child);
        child.setParent(null);
        changedSinceSort = true;
    }

    public Image getImage() {
        return image;
    }

    public String getLabel() {
        return label != null ? label : ""; //$NON-NLS-1$
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public BpmnCatchableErrorFolder getParentFolder() {
        return parentFolder;
    }

    public void setParent(BpmnCatchableErrorFolder parentFolder) {
        this.parentFolder = parentFolder;
    }

    @Override
    public String toString() {
        return label;
    }
}

