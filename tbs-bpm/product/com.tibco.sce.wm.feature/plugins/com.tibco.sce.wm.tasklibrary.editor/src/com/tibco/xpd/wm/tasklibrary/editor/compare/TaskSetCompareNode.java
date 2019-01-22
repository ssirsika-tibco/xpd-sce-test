/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.FilteredEListCompareNode;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Task set compare node.
 * <p>
 * Pretty much the same as lane except for the icon.
 * 
 * @author aallway
 * @since 2 Dec 2010
 */
public class TaskSetCompareNode extends NamedElementCompareNode {

    private Process taskLibraryProcess;

    private EListCompareNode taskSetActivities;

    private EListCompareNode taskSetArtifacts;

    private Lane taskSetLane;

    /**
     * @param taskSetLane
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public TaskSetCompareNode(Lane taskSetLane, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(taskSetLane, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.taskSetLane = taskSetLane;
        Pool pool = (Pool) taskSetLane.eContainer();
        Package pkg = (Package) pool.eContainer();
        taskLibraryProcess = pkg.getProcess(pool.getProcessId());

    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#createChildren(com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory)
     * 
     * @param compareNodeFactory
     * @return
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        List<Object> children = new ArrayList<Object>();

        /* Add the default EMF mode (expect for those excluded above children */
        Object[] defaultChildren = super.createChildren(compareNodeFactory);
        for (Object child : defaultChildren) {
            children.add(child);
        }

        /* Add activities in this set. */
        if (taskLibraryProcess != null) {
            /*
             * Add filtered lists of activities that are in the set but are
             * children of the process.
             */
            taskSetActivities =
                    new FilteredEListCompareNode(
                            taskLibraryProcess.getActivities(),
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities(),
                            this,
                            compareNodeFactory,
                            Messages.TaskSetCompareNode_Tasks_label,
                            TaskLibraryEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(TaskLibraryEditorContstants.ICON_TASKGROUP),
                            ".laneActivities") { //$NON-NLS-1$

                        @Override
                        protected EList getTargetList(
                                EMFCompareNode targetParent) {
                            if (taskLibraryProcess != null) {
                                return taskLibraryProcess.getActivities();
                            }
                            return null;
                        }

                        @Override
                        protected boolean includeInVirtualParent(Object object) {
                            NodeGraphicsInfo nodeGraphicsInfo =
                                    Xpdl2ModelUtil
                                            .getNodeGraphicsInfo((GraphicalNode) object);
                            if (nodeGraphicsInfo != null) {
                                if (taskSetLane.getId().equals(nodeGraphicsInfo
                                        .getLaneId())) {
                                    return true;
                                }
                            }
                            return false;
                        }

                    };

            taskSetActivities.setAtomic(true);
            children.add(taskSetActivities);

            taskSetArtifacts =
                    new FilteredEListCompareNode(
                            taskLibraryProcess.getPackage().getArtifacts(),
                            Xpdl2Package.eINSTANCE.getPackage_Artifacts(),
                            this,
                            compareNodeFactory,
                            Messages.TaskSetCompareNode_Artifacts_label,
                            TaskLibraryEditorPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(TaskLibraryEditorContstants.ICON_DATAOBJECT),
                            ".laneArtifacts") { //$NON-NLS-1$

                        @Override
                        protected EList getTargetList(
                                EMFCompareNode targetParent) {
                            if (taskLibraryProcess != null) {
                                return taskLibraryProcess.getPackage()
                                        .getArtifacts();
                            }
                            return null;
                        }

                        @Override
                        protected boolean includeInVirtualParent(Object object) {
                            EObject parent =
                                    Xpdl2ModelUtil
                                            .getContainer((EObject) object);
                            if (parent instanceof Lane
                                    && taskSetLane.getId()
                                            .equals(((Lane) parent).getId())) {
                                return true;
                            }
                            return false;
                        }

                    };

            taskSetArtifacts.setAtomic(true);
            children.add(taskSetArtifacts);
        }

        return children.toArray();
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        // TODO Task set merg will need special handling
        throw new RuntimeException("Task sets merge will need special handling"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     */
    @Override
    protected void doRemoveYourself(ITypedElement targetParent) {
        // TODO Task set merge will need special handling
        throw new RuntimeException("Task sets merge will need special handling"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
     *      com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode)
     * 
     * @param targetParent
     * @param targetNode
     * @return
     */
    @Override
    protected Object doReplaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode) {
        // TODO Task set merge will need special handling
        throw new RuntimeException("Task sets merge will need special handling"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        return TaskLibraryEditorPlugin.getDefault().getImageRegistry()
                .get(TaskLibraryEditorContstants.IMG_TASKSET);
    }
}
