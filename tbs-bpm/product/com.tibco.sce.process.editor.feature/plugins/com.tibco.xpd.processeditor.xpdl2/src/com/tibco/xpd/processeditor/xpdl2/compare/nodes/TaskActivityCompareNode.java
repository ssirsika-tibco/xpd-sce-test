/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.ActivityCompareNode;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Compare node for task activities
 * 
 * @author aallway
 * @since 20 Oct 2010
 */
public class TaskActivityCompareNode extends ActivityCompareNode {

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public TaskActivityCompareNode(Activity taskActivity, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(taskActivity, listIndex, feature, parentCompareNode,
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

        String typeDesc = Messages.CompareNode_Type_label + " " //$NON-NLS-1$
                + TaskObjectUtil.getTaskType(getActivity()).toString();

        String suffix = null;

        TaskType taskType = TaskObjectUtil.getTaskTypeStrict(getActivity());
        if (TaskType.USER_LITERAL.equals(taskType)
                || TaskType.MANUAL_LITERAL.equals(taskType)) {
            suffix = getUserManualTaskInfoSuffix(props);

        } else if (TaskType.SERVICE_LITERAL.equals(taskType)
                || TaskType.SEND_LITERAL.equals(taskType)
                || TaskType.RECEIVE_LITERAL.equals(taskType)) {
            suffix = getServiceTaskInfoSuffix(props);

        } else if (TaskType.SUBPROCESS_LITERAL.equals(taskType)) {
            suffix = getSubProcessTaskInfoSuffix(props);
        }

        if (suffix != null && suffix.length() > 0) {
            typeDesc += " (" + suffix + ")"; //$NON-NLS-1$ //$NON-NLS-2$

        }
        props.add(new XpdPropertyInfoNode(typeDesc, 20, this, this.getType(),
                "taskTypeInfo")); //$NON-NLS-1$

        return props;
    }

    /**
     * @param props
     */
    private String getSubProcessTaskInfoSuffix(
            Collection<XpdPropertyInfoNode> props) {
        String leader = Messages.TaskActivityCompareNode_InvokedProcess_label;
        String invokedProcess =
                Messages.TaskActivityCompareNode_Undefined_label;

        Activity activity = getActivity();

        if (activity.getImplementation() instanceof SubFlow) {
            String subProcId =
                    ((SubFlow) activity.getImplementation()).getProcessId();

            if (subProcId != null && subProcId.length() > 0) {
                invokedProcess =
                        Messages.TaskActivityCompareNode_InvokedProcessCantLocate_label;

                WorkingCopy wc = getWorkingCopy();
                if (wc instanceof Xpdl2WorkingCopyImpl) {
                    EObject procOrIfc =
                            TaskObjectUtil
                                    .getSubProcessOrInterface(getActivity(),
                                            (Xpdl2WorkingCopyImpl) wc);

                    if (procOrIfc instanceof NamedElement) {
                        invokedProcess =
                                Xpdl2ModelUtil
                                        .getDisplayNameOrName((NamedElement) procOrIfc);

                        if (procOrIfc instanceof ProcessInterface) {
                            leader =
                                    Messages.TaskActivityCompareNode_InvokedInterface_label;
                        }
                    }
                }
            }
        }

        return leader + " " + invokedProcess; //$NON-NLS-1$ 
    }

    /**
     * @param props
     */
    private String getServiceTaskInfoSuffix(
            Collection<XpdPropertyInfoNode> props) {
        String taskImplementationExtensionId =
                TaskObjectUtil.getTaskImplementationExtensionId(getActivity());

        if (taskImplementationExtensionId != null
                && taskImplementationExtensionId.length() > 0) {
            return Messages.TaskActivityCompareNode_ImplementationType_label
                    + " " + taskImplementationExtensionId; //$NON-NLS-1$

        } else {
            return Messages.TaskActivityCompareNode_ImplementationType_label
                    + " " + Messages.TaskActivityCompareNode_Undefined_label; //$NON-NLS-1$
        }

    }

    /**
     * @param props
     */
    private String getUserManualTaskInfoSuffix(
            Collection<XpdPropertyInfoNode> props) {
        EObject[] activityPerformers =
                TaskObjectUtil.getActivityPerformers(getActivity());
        if (activityPerformers != null && activityPerformers.length > 0) {
            String perfs = null;

            for (EObject performer : activityPerformers) {
                if (performer instanceof NamedElement) {
                    if (perfs != null) {
                        perfs += ", "; //$NON-NLS-1$
                    }
                    perfs =
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName((NamedElement) performer);

                }
            }

            return Messages.TaskActivityCompareNode_Performers_label + " " //$NON-NLS-1$
                    + perfs;
        }

        return null;
    }
}
