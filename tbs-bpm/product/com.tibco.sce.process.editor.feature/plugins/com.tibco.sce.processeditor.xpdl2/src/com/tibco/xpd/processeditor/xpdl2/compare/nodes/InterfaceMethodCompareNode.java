/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * COmpare node for process interface start / intermediate events.
 * 
 * @author aallway
 * @since 30 Nov 2010
 */
public class InterfaceMethodCompareNode extends NamedElementCompareNode {

    private InterfaceMethod interfaceMethod;

    /**
     * @param interfaceMethod
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public InterfaceMethodCompareNode(InterfaceMethod interfaceMethod,
            int listIndex, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(interfaceMethod, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.interfaceMethod = interfaceMethod;
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

        /* Type */
        props
                .add(new XpdPropertyInfoNode(
                        Messages.CompareNode_Type_label
                                + " " //$NON-NLS-1$
                                + (TriggerType.MESSAGE_LITERAL
                                        .equals(interfaceMethod.getTrigger()) ? Messages.InterfaceMethodCompareNode_MessageType_label
                                        : Messages.InterfaceMethodCompareNode_NoneType_label),
                        30, this, this.getType(), "associatedDataInfo")); //$NON-NLS-1$

        /* Add associated parameters info. */
        String assocParamList =
                Messages.InterfaceMethodCompareNode_AllIfcParams_label;

        List<AssociatedParameter> associatedParameters =
                interfaceMethod.getAssociatedParameters();

        if (associatedParameters != null && associatedParameters.size() > 0) {
            /* Get the explicitly associated data then. */
            List<FormalParameter> associatedData =
                    ProcessInterfaceUtil
                            .getInterfaceMethodAssociatedFormalParameters(interfaceMethod);

            assocParamList = ""; //$NON-NLS-1$

            for (ProcessRelevantData data : associatedData) {
                if (assocParamList.length() != 0) {
                    assocParamList += ", "; //$NON-NLS-1$
                }

                assocParamList += Xpdl2ModelUtil.getDisplayNameOrName(data);
            }
        }

        props.add(new XpdPropertyInfoNode(
                Messages.CompareNode_AssociatedData_label + " " //$NON-NLS-1$
                        + assocParamList, 30, this, this.getType(),
                "associatedDataInfo")); //$NON-NLS-1$

        return props;
    }
}
