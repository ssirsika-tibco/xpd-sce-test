/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes.base;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Specialisation of EObjectCompareNode that provides
 * 
 * @author aallway
 * @since 4 Oct 2010
 */
public class NamedElementCompareNode extends UniqueIdElementCompareNode {

    private NamedElement namedElement;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public NamedElementCompareNode(NamedElement namedElement, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(namedElement, listIndex, feature, parentCompareNode,
                compareNodeFactory);
        this.namedElement = namedElement;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        /*
         * The default for EObjectCompareNode is to used
         * WorkingCopyUtil.getText() returns "label" or "label (name)" when in
         * solution designer mode (i.e. same as project explorer.
         * 
         * This can get a bit long so we'll use label if availabnle else name.
         */
        String name = Xpdl2ModelUtil.getDisplayNameOrName(namedElement);
        if (name != null && name.length() > 0) {
            return name;
        }
        return "''"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        String name = Xpdl2ModelUtil.getDisplayName(namedElement);
        if (name != null && name.length() > 0) {
            name = name + " (" + namedElement.getName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            name = namedElement.getName();
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }
        props.add(new XpdPropertyInfoNode(
                Messages.NamedElementCompareNode_Name_label + name, 10, this,
                getCompareNodeFactory().getCompareNodeContentType(),
                "namedElementInfo")); //$NON-NLS-1$

        return props;
    }
}
