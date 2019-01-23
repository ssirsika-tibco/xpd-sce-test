/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes.base;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WrappedEListCompareNode;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aallway
 * @since 27 Oct 2010
 */
public class NamedElementListCompareNode extends WrappedEListCompareNode {

    /**
     * @param list
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     * @param label
     * @param image
     */
    public NamedElementListCompareNode(EList list, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory,
            String label, Image image) {
        super(list, feature, parentCompareNode, compareNodeFactory, label,
                image);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        /* Group contents into groups of 10 items and create a list for each. */
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        EList list = getEList();

        int i = 0;
        while (i < list.size()) {
            String listNames = ""; //$NON-NLS-1$

            for (int c = 0; c < 10 && i < list.size(); c++, i++) {
                Object item = list.get(i);

                String name = null;
                if (item instanceof NamedElement) {
                    name =
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName((NamedElement) item);
                } else if (item instanceof EObject) {
                    WorkingCopyUtil.getText((EObject) item);
                }

                if (name != null && name.length() > 0) {
                    if (listNames.length() != 0) {
                        listNames += ", "; //$NON-NLS-1$
                    } else {
                        listNames += this.getName() + ": "; //$NON-NLS-1$
                    }

                    listNames += "\"" + name + "\""; //$NON-NLS-1$ //$NON-NLS-2$
                }
            }

            if (listNames.length() > 0) {
                props.add(new XpdPropertyInfoNode(listNames, 10, this, this
                        .getType(), "namedElementListInfo" + i)); //$NON-NLS-1$
            }
        }

        return props;
    }

}
