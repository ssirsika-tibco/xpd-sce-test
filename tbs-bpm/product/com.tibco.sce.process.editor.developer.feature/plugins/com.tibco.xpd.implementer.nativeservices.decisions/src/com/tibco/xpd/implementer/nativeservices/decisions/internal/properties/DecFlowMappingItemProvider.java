/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.decisions.internal.properties;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.Mapper;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.DecisionStandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mappings for an activity that is implemented with a Sub Proc. Converts XPDL
 * {@link DataMapping} into the generic {@link Mapping} element understood by
 * the {@link Mapper}. Understands expressions for actual parameters that use
 * the WsdlPath script syntax, and returns the approriate XSD element for these
 * as part of the mapping.
 * 
 * @author mtorres
 */
public class DecFlowMappingItemProvider implements IStructuredContentProvider {
    private MappingDirection direction;

    /**
     */
    public DecFlowMappingItemProvider(MappingDirection direction) {
        this.direction = direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getElements(java.lang
     * .Object)
     */
    public Object[] getElements(Object parent) {
        Activity activity = (Activity) parent;
        if (activity != null && activity.getProcess() != null) {

            List<FormalParameter> formalParams =
                    DecisionStandardMappingUtil.getDecFlowFormalParameters(activity, direction);

            List<DataMapping> xpdlMappings = getDataMappings(activity);

            EObject decisionFlow =
                    DecisionTaskObjectUtil.getDecisionFlow(activity);
            if (decisionFlow instanceof Process) {
                return DecisionStandardMappingUtil
                        .getProcessDataToOtherProcessDataMappings(formalParams,
                                activity,
                                (Process) decisionFlow,
                                xpdlMappings,
                                direction);
            }
        }

        return new Object[0];
    }

    

    /**
     * @param target
     * @return
     */
    private SubFlow getSubFlow(Notifier target) {
        SubFlow subFlow = null;
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskService() != null) {
                    Object otherElement =
                            Xpdl2ModelUtil.getOtherElement(task
                                    .getTaskService(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DecisionService());
                    if (otherElement instanceof SubFlow) {
                        return (SubFlow) otherElement;
                    }
                }
            }
        }
        return subFlow;
    }

    private List<DataMapping> getDataMappings(Activity input) {
        SubFlow subFlow = getSubFlow(input);
        if (subFlow != null) {
            return subFlow.getDataMappings();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#dispose()
     */
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
