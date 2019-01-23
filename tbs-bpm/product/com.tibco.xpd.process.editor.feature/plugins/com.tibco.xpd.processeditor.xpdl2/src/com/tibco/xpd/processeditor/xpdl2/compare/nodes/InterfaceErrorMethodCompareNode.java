/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.UniqueIdElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Compare node for process interface error events.
 * 
 * @author aallway
 * @since 30 Nov 2010
 */
public class InterfaceErrorMethodCompareNode extends UniqueIdElementCompareNode {

    private ErrorMethod errorMethod;

    /**
     * @param errorMethod
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public InterfaceErrorMethodCompareNode(ErrorMethod errorMethod,
            int listIndex, EStructuralFeature feature,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(errorMethod, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.errorMethod = errorMethod;
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
                Messages.InterfaceErrorMethodCompareNode_ErrorCode_label + " " //$NON-NLS-1$ 
                        + errorMethod.getErrorCode(), 10, this, this.getType(),
                "errorMethodCodeInfo")); //$NON-NLS-1$

        /* Add associated parameters info. */
        String assocParamList =
                Messages.InterfaceMethodCompareNode_AllIfcParams_label;

        List<AssociatedParameter> associatedParameters =
                errorMethod.getAssociatedParameters();

        if (associatedParameters != null && associatedParameters.size() > 0) {
            /* Get the explicitly associated data then. */
            List<FormalParameter> associatedData =
                    ProcessInterfaceUtil
                            .getErrorMethodAssociatedFormalParameters(errorMethod);

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
                        + assocParamList, 20, this, this.getType(),
                "associatedDataInfo")); //$NON-NLS-1$

        return props;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return errorMethod.getErrorCode();
    }
}
