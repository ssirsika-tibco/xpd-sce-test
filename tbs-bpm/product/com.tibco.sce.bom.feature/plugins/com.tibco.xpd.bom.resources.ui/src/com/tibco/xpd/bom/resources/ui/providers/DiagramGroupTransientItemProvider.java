/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;

/**
 * A transient item provider to represent the Diagrams virtual node (for
 * multi-diagram support)
 * 
 * @author njpatel
 * @since 3.3
 */
public class DiagramGroupTransientItemProvider extends AbstractItemProvider {

    private final BOMWorkingCopy wc;

    private IResource parent;

    private Image img;

    public DiagramGroupTransientItemProvider(AdapterFactory adapterFactory,
            BOMWorkingCopy wc) {
        super(adapterFactory);
        this.wc = wc;

        if (wc != null && wc.getEclipseResources() != null
                && !wc.getEclipseResources().isEmpty()) {
            parent = wc.getEclipseResources().get(0);
        }
    }

    /**
     * Get the "parent" working copy of this node.
     * 
     * @return
     */
    public BOMWorkingCopy getWorkingCopy() {
        return wc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ITreeItemContentProvider#getChildren(java
     * .lang.Object)
     */
    public Collection<?> getChildren(Object object) {
        List<Diagram> diagrams = new ArrayList<Diagram>();

        if (wc != null) {
            for (Diagram diagram : wc.getDiagrams()) {
                if (BomUIUtil.isUserDiagram(diagram)) {
                    diagrams.add(diagram);
                }
            }
        }
        return diagrams;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.IItemLabelProvider#getImage(java.lang.Object
     * )
     */
    public Object getImage(Object object) {
        if (img == null) {
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.DIAGRAM_FOLDER);
        }
        return img;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.IItemLabelProvider#getText(java.lang.Object
     * )
     */
    public String getText(Object object) {
        return Messages.DiagramGroupTransientItemProvider_Diagrams_label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.IItemPropertySource#getPropertyDescriptors
     * (java.lang.Object)
     */
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    @Override
    public Object getEditableValue(Object object) {
        return null;
    }

    @Override
    public Object getParent(Object object) {
        return parent;
    }
}
