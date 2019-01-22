/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.ActivityCompareNode;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Compare node for event activities
 * 
 * @author aallway
 * @since 26 Nov 2010
 */
public class EventActivityCompareNode extends ActivityCompareNode {

    /**
     * @param activity
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public EventActivityCompareNode(Activity activity, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(activity, listIndex, feature, parentCompareNode,
                compareNodeFactory);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        props.add(new XpdPropertyInfoNode(
                Messages.CompareNode_Type_label
                        + " " //$NON-NLS-1$
                        + EventObjectUtil.getEventTriggerType(getActivity())
                                .toString(), 20, this, this.getType(),
                "eventTypeInfo")); //$NON-NLS-1$

        return props;
    }
}
