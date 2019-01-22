/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aallway
 * @since 28 Oct 2010
 */
public class MessageFlowCompareNode extends BaseConnectionCompareNode {

    private MessageFlow messageFlow;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public MessageFlowCompareNode(MessageFlow messageFlow, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(messageFlow, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.messageFlow = messageFlow;

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.BaseConnectionCompareNode#getConnectionSourceObject()
     * 
     * @return
     */
    @Override
    protected EObject getConnectionSourceObject() {
        Package pkg = Xpdl2ModelUtil.getPackage(messageFlow);

        if (pkg != null) {
            return pkg.findNamedElement(messageFlow.getSource());
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
        Package pkg = Xpdl2ModelUtil.getPackage(messageFlow);

        if (pkg != null) {
            return pkg.findNamedElement(messageFlow.getTarget());
        }

        return null;
    }

}
