/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes.base;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * General Activity compare node.
 * 
 * @author aallway
 * @since 25 Oct 2010
 */
public class ActivityCompareNode extends
        ProcessDescendentNamedElementCompareNode {

    private Activity activity;

    /**
     * @param object
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public ActivityCompareNode(Activity activity, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {

        super(activity, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.activity = activity;
    }

    /**
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
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

        /* Add associated parameters info. */
        String assocParamList =
                Messages.ActivityCompareNode_AllProcessData_label;

        List<AssociatedParameter> associatedParameters =
                ProcessInterfaceUtil
                        .getActivityAssociatedParameters(getActivity());

        if (associatedParameters != null && associatedParameters.size() > 0) {
            /* Get the explicitly associated data then. */
            List<ProcessRelevantData> associatedData =
                    ProcessInterfaceUtil
                            .getAssociatedProcessRelevantDataForActivity(getActivity());

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

        if (ReplyActivityUtil.isIncomingRequestActivity(getActivity())) {
            /*
             * For request activities, list associated correlation data fields
             * if there are any.
             */
            AssociatedCorrelationFields associatedCorrelation =
                    (AssociatedCorrelationFields) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedCorrelationFields());

            String assocCorrelationList =
                    Messages.ActivityCompareNode_AllCorrelationData_label;

            if (associatedCorrelation != null
                    && associatedCorrelation.getAssociatedCorrelationField()
                            .size() > 0) {
                /* Get the explicitly associated data then. */
                List<DataField> associatedData =
                        ProcessInterfaceUtil
                                .getAssociatedCorrelationDataForActivity(getActivity());

                assocCorrelationList = ""; //$NON-NLS-1$

                for (ProcessRelevantData data : associatedData) {
                    if (assocCorrelationList.length() != 0) {
                        assocCorrelationList += ", "; //$NON-NLS-1$
                    }

                    assocCorrelationList +=
                            Xpdl2ModelUtil.getDisplayNameOrName(data);
                }
            }

            props
                    .add(new XpdPropertyInfoNode(
                            Messages.ActivityCompareNode_AssociatedCorrelationData_label
                                    + " " //$NON-NLS-1$
                                    + assocCorrelationList, 30, this, this
                                    .getType(), "associatedCorrelationInfo")); //$NON-NLS-1$
        }

        return props;
    }
}
