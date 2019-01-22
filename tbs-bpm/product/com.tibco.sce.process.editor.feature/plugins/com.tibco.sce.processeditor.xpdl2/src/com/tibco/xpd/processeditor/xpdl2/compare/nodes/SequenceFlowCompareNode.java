/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;

/**
 * 
 * 
 * @author aallway
 * @since 28 Oct 2010
 */
public class SequenceFlowCompareNode extends BaseConnectionCompareNode {

    private Transition transition;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public SequenceFlowCompareNode(Transition transition, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(transition, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.transition = transition;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode#getConnectionSourceObject()
     * 
     * @return
     */
    @Override
    protected EObject getConnectionSourceObject() {
        FlowContainer flowContainer = transition.getFlowContainer();
        if (flowContainer != null) {
            return flowContainer.getActivity(transition.getFrom());
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode#getConnectionTargetObject()
     * 
     * @return
     */
    @Override
    protected EObject getConnectionTargetObject() {
        FlowContainer flowContainer = transition.getFlowContainer();
        if (flowContainer != null) {
            return flowContainer.getActivity(transition.getTo());
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode#createInfoPropertyChildren()
     * 
     * @return
     */
    @Override
    protected Collection<XpdPropertyInfoNode> createInfoPropertyChildren() {
        Collection<XpdPropertyInfoNode> props =
                super.createInfoPropertyChildren();

        String type = SequenceFlowType.UNCONTROLLED_LITERAL.toString();

        if (transition.getCondition() != null) {
            if (ConditionType.CONDITION_LITERAL.equals(transition
                    .getCondition().getType())) {
                type = SequenceFlowType.CONDITIONAL_LITERAL.toString();
            } else {
                type = SequenceFlowType.DEFAULT_LITERAL.toString();
            }
        }

        props.add(new XpdPropertyInfoNode(Messages.CompareNode_Type_label + " " //$NON-NLS-1$
                + type, 20, this, this.getType(), "connectionTypeInfo")); //$NON-NLS-1$

        return props;
    }

}
