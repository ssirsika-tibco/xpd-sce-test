/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.ProcessDescendentNamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.FilteredEListCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification.AncestorOperationType;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Node for Lane
 * 
 * @author aallway
 * @since 19 Oct 2010
 */
public class LaneCompareNode extends ProcessDescendentNamedElementCompareNode {

    private Lane lane;

    private FilteredEListCompareNode laneActivities = null;

    private FilteredEListCompareNode laneArtifacts = null;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public LaneCompareNode(Lane lane, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(lane, listIndex, feature, parentCompareNode, compareNodeFactory);

        this.lane = lane;
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

        Process process = getAncestorProcess(this);

        /*
         * Add filtered lists of elements that are 'in process' but stored at
         * package level.
         */
        laneActivities =
                new FilteredEListCompareNode(process.getActivities(),
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        this, compareNodeFactory,
                        Messages.ProcessCompareNodeFactory_Activities_label,
                        Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2ResourcesConsts.ICON_TASK_FOLDER),
                        ".laneActivities") { //$NON-NLS-1$

                    @Override
                    protected EList getTargetList(EMFCompareNode targetParent) {
                        Process targetProcess =
                                getAncestorProcess(targetParent);
                        if (targetProcess != null) {
                            return targetProcess.getActivities();
                        }
                        return null;
                    }

                    @Override
                    protected boolean includeInVirtualParent(Object object) {
                        NodeGraphicsInfo nodeGraphicsInfo =
                                Xpdl2ModelUtil
                                        .getNodeGraphicsInfo((GraphicalNode) object);
                        if (nodeGraphicsInfo != null) {

                            if (lane.getId().equals(nodeGraphicsInfo
                                    .getLaneId())) {
                                return true;
                            }
                        }
                        return false;
                    }

                };

        laneActivities.setAtomic(true);
        children.add(laneActivities);

        laneArtifacts =
                new FilteredEListCompareNode(process.getPackage()
                        .getArtifacts(), Xpdl2Package.eINSTANCE
                        .getPackage_Artifacts(), this, compareNodeFactory,
                        Messages.ProcessCompareNodeFactory_Artifacts_label,
                        Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2ResourcesConsts.IMG_DATAOBJECT),
                        ".laneArtifacts") { //$NON-NLS-1$

                    @Override
                    protected EList getTargetList(EMFCompareNode targetParent) {
                        Package targetPackage = getTargetPackage(targetParent);
                        if (targetPackage != null) {
                            return targetPackage.getArtifacts();
                        }
                        return null;
                    }

                    @Override
                    protected boolean includeInVirtualParent(Object object) {

                        EObject parent =
                                Xpdl2ModelUtil.getContainer((EObject) object);
                        if (parent instanceof Lane
                                && lane.getId().equals(((Lane) parent).getId())) {
                            return true;
                        }
                        return false;
                    }

                };

        laneArtifacts.setAtomic(true);
        children.add(laneArtifacts);

        /* Create all the normal children. */
        Object[] defaultChildren = super.createChildren(compareNodeFactory);
        if (defaultChildren != null) {
            for (Object child : defaultChildren) {
                children.add(child);
            }
        }

        Object[] childArray = children.toArray();

        return childArray;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        Object added = super.doAddYourself(targetParent);

        /* When I'm added, then add lane's activities etc. */
        laneActivities.doAddYourself(targetParent);
        laneArtifacts.doAddYourself(targetParent);

        return added;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
     *      com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode)
     * 
     * @param targetParent
     * @param targetNode
     * @return
     */
    @Override
    protected Object doReplaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode) {
        Object replaced = super.doReplaceYourself(targetParent, targetNode);

        /* When I'm replaced, then replace lane's activities etc. */
        laneActivities.doReplaceYourself(targetParent, targetNode);
        laneArtifacts.doReplaceYourself(targetParent, targetNode);

        return replaced;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     */
    @Override
    protected void doRemoveYourself(ITypedElement targetParent) {
        super.doRemoveYourself(targetParent);

        /* When I'm removed, then remove lane's activities etc. */
        laneActivities.doRemoveYourself(targetParent);

        laneArtifacts.doRemoveYourself(targetParent);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#ancestorOperationPerformed(com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification)
     * 
     * @param acn
     */
    @Override
    protected void ancestorOperationPerformed(AncestorOperationNotification acn) {
        if (true) {
            throw new RuntimeException("Artifacts merging is untested"); //$NON-NLS-1$
        }

        /*
         * If operation is performed on ancestor below process (i.e. pools,
         * pool, lanes) then we should perform the same operation on this lane's
         * activities (Becuase they are not a descendent in the actual xpdl
         * model).
         */

        EMFCompareNode notifyingAncestorNode = acn.getNotifierNode();

        ProcessCompareNode notifierAncestorProcessNode =
                getAncestorProcessNode(notifyingAncestorNode);

        /*
         * The ancestor node must be above us in tree, if we found
         * ProcessCompareNode from that ancestor then operation must have been
         * performed ON or UNDER the PRocess node.
         * 
         * If the notifier IS the process or above in tree then we don't need to
         * do anything because the lane's activities are under the process niode
         * so will be dealt with naturally as model descendents of the node.
         */
        if (notifierAncestorProcessNode != null
                && notifierAncestorProcessNode != notifyingAncestorNode) {

            /*
             * NOTE that the notifierAncestorProcessNode is in SOURCE tree for
             * ADD/REPLACE and TARGET TREE for remove.
             */
            ProcessCompareNode sourceProcessCompareNode =
                    getAncestorProcessNode(this);

            ProcessCompareNode targetProcessCompareNode =
                    getTargetAncestorProcessNode(acn);

            if (AncestorOperationType.POST_ADD.equals(acn.getType())) {
                /* Add is called on the SOURCE node */
                laneActivities.addYourself(targetProcessCompareNode);
                laneArtifacts.addYourself(targetProcessCompareNode);

            } else if (AncestorOperationType.PRE_REMOVE.equals(acn.getType())) {
                /*
                 * Remove is called on the TARGET node - so find this lane node
                 * in target process node
                 */
                EMFCompareNode laneActivitiesInTarget =
                        laneActivities
                                .findMeInDescendents(targetProcessCompareNode);

                if (laneActivitiesInTarget != null) {
                    laneActivitiesInTarget
                            .removeYourself(notifierAncestorProcessNode);
                }

                EMFCompareNode laneArtifactsInTarget =
                        laneArtifacts
                                .findMeInDescendents(targetProcessCompareNode);

                if (laneArtifactsInTarget != null) {
                    laneArtifactsInTarget
                            .removeYourself(notifierAncestorProcessNode);
                }

            } else if (AncestorOperationType.POST_REPLACE.equals(acn.getType())) {
                /*
                 * Replace is called on the SOURCE node.
                 */
                EMFCompareNode thisLaneInTarget =
                        findMeInDescendents(targetProcessCompareNode);

                if (thisLaneInTarget != null) {
                    EMFCompareNode laneActivitiesInTarget =
                            laneActivities
                                    .findMeInDescendents(thisLaneInTarget);

                    if (laneActivitiesInTarget != null) {
                        laneActivities.replaceYourself(thisLaneInTarget,
                                laneActivitiesInTarget);
                    }

                    EMFCompareNode laneArtifactsInTarget =
                            laneArtifacts.findMeInDescendents(thisLaneInTarget);

                    if (laneArtifactsInTarget != null) {
                        laneArtifacts.replaceYourself(thisLaneInTarget,
                                laneArtifactsInTarget);
                    }
                }
            }
        }

        return;
    }

}
