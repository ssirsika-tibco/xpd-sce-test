/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ActivityIconProviderDescriptor;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.adapters.FlowNodeAdapter;
import com.tibco.xpd.processwidget.policies.AllConnectionTypesNodeEditPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;

/**
 * Base class for Task, Gateway and Event EditParts - edit parts that adapt
 * FlowObject
 * 
 * @author wzurek
 */
public abstract class BaseFlowNodeEditPart extends BaseConnectableNodeEditPart {

    /**
     * The constructor
     * 
     * @param adapterFactory
     */
    public BaseFlowNodeEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * @return model adapter downcasted to FlowNodeAdapter
     */
    public FlowNodeAdapter getFlowNodeAdapter() {
        return (FlowNodeAdapter) getModelAdapter();
    }

    /**
     * Outgoing squence flow and associations
     */
    @Override
    protected List getModelSourceConnections() {
        List l1 = getFlowNodeAdapter().getSourceSequenceFlows();
        List l2 = getFlowNodeAdapter().getSourceAssociations();
        List l3 = getFlowNodeAdapter().getSourceMessageFlows();

        ArrayList result = new ArrayList(l1.size() + l2.size() + l3.size());
        result.addAll(l1);
        result.addAll(l2);
        result.addAll(l3);
        return result;
    }

    /**
     * incoming sequence flow and associations
     */
    @Override
    protected List getModelTargetConnections() {
        List l1 = getFlowNodeAdapter().getTargetSequenceFlows();
        List l2 = getFlowNodeAdapter().getTargetAssociations();
        List l3 = getFlowNodeAdapter().getTargetMessageFlows();

        ArrayList result = new ArrayList(l1.size() + l2.size() + l3.size());
        result.addAll(l1);
        result.addAll(l2);
        result.addAll(l3);
        return result;
    }

    @Override
    protected void refreshSourceConnections() {
        super.refreshSourceConnections();
    }

    @Override
    protected void refreshTargetConnections() {
        super.refreshTargetConnections();
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();

        installEditPolicy("Animation", new ConnectionsAnimatorEditPolicy( //$NON-NLS-1$
                getProcessWidget().getEditPolicyEnablementProvider()));

        // SID - use new bpmnflowroute style edit policy
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new AllConnectionTypesNodeEditPolicy(getEditingDomain()));

        return;
    }

    /**
     * Sid XPD-2516: Allow overrides from
     * processEditorConfiguration/ActivityIconOverrides extension point element.
     * 
     * @param processEditorObjectType
     * @return icon to use in palce of default icon or <code>null</code> to use
     *         default.
     */
    protected Image getActivityIconOverride(
            ProcessEditorObjectType processEditorObjectType,
            Object activityModel) {
        Image overrideImage = null;

        ProcessWidget processWidget = getProcessWidget();

        if (processWidget != null) {
            List<ActivityIconProviderDescriptor> activityIconProviders =
                    processWidget.getActivityIconProviders();

            for (ActivityIconProviderDescriptor iconProviderWrapper : activityIconProviders) {
                /*
                 * Check that this contribution's object type list contains the
                 * passed object type.
                 */
                Set<ProcessEditorObjectType> applicableObjectTypes =
                        iconProviderWrapper.getApplicableObjectTypes();

                if (applicableObjectTypes.contains(processEditorObjectType)) {
                    /*
                     * Finally ask whether the provider itself considers itself
                     * active for the given type.
                     */
                    if (iconProviderWrapper.isEnabled(activityModel)) {
                        overrideImage =
                                iconProviderWrapper.getImage(activityModel);
                        if (overrideImage != null) {
                            break;
                        }
                    }
                }
            }
        }

        return overrideImage;
    }
}
