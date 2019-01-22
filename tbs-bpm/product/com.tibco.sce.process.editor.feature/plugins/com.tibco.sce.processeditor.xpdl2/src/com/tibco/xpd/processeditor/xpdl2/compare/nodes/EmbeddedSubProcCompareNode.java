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
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.ActivityCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.FilteredEListCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification.AncestorOperationType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Compare node for embedded sub-process.
 * <p>
 * Places activity set activities and transitions from referenced ActivitySet
 * within itself.
 * 
 * @author aallway
 * @since 23 Nov 2010
 */
public class EmbeddedSubProcCompareNode extends ActivityCompareNode {

    private FilteredEListCompareNode subprocArtifacts = null;

    /**
     * @param activity
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public EmbeddedSubProcCompareNode(Activity activity, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(activity, listIndex, feature, parentCompareNode,
                compareNodeFactory);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#createChildren(com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory)
     * 
     * @param compareNodeFactory
     * 
     * @return Standard children PLUS activity set activities and sequence flow.
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        /*
         * Get the standard children.
         */
        Object[] stdChildren = super.createChildren(compareNodeFactory);

        List<Object> children = new ArrayList<Object>();
        if (stdChildren != null) {
            for (Object stdChild : stdChildren) {
                children.add(stdChild);
            }
        }

        ActivitySet activitySet = getActivitySet();
        if (activitySet != null) {
            if (activitySet.getActivities().size() > 0) {
                children.add(compareNodeFactory.createForEList(activitySet
                        .getActivities(), Xpdl2Package.eINSTANCE
                        .getFlowContainer_Activities(), this));
            }

            if (activitySet.getTransitions().size() > 0) {
                children.add(compareNodeFactory.createForEList(activitySet
                        .getTransitions(), Xpdl2Package.eINSTANCE
                        .getFlowContainer_Transitions(), this));
            }

            subprocArtifacts =
                    new FilteredEListCompareNode(getActivity().getProcess()
                            .getPackage().getArtifacts(),
                            Xpdl2Package.eINSTANCE.getPackage_Artifacts(),
                            this, compareNodeFactory,
                            Messages.ProcessCompareNodeFactory_Artifacts_label,
                            Xpdl2ResourcesPlugin.getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.IMG_DATAOBJECT),
                            ".SubProcArtifacts") { //$NON-NLS-1$

                        @Override
                        protected EList getTargetList(
                                EMFCompareNode targetParent) {
                            Package targetPackage =
                                    getTargetPackage(targetParent);
                            if (targetPackage != null) {
                                return targetPackage.getArtifacts();
                            }
                            return null;
                        }

                        @Override
                        protected boolean includeInVirtualParent(Object object) {
                            EObject parent =
                                    Xpdl2ModelUtil
                                            .getContainer((EObject) object);
                            if (parent instanceof ActivitySet) {
                                ActivitySet thisActivitySet = getActivitySet();
                                if (thisActivitySet != null) {
                                    if (thisActivitySet.getId()
                                            .equals(((ActivitySet) parent)
                                                    .getId())) {
                                        return true;
                                    }
                                }

                            }
                            return false;
                        }

                    };

            subprocArtifacts.setAtomic(true);
            children.add(subprocArtifacts);
        }
        return children.toArray();
    }

    /**
     * @return activity set referenced by thos node#s activity
     */
    private ActivitySet getActivitySet() {
        return Xpdl2ModelUtil.getEmbeddedSubProcessActivitySet(getActivity());
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * 
     * @return add embedded asub-process task and it's related activity set to
     *         target.
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        // TODO Test and debug when Merge re-enabled.
        if (true) {
            throw new RuntimeException("doAddYourself untested."); //$NON-NLS-1$
        }

        /*
         * Add the embedded subprocess task itself to target.
         */
        Object addedEmbeddedSubprocAct = super.doAddYourself(targetParent);

        /*
         * Take a copy of the activity set and add it to target process.
         */
        addOrReplaceActivitySet(targetParent);

        subprocArtifacts.addYourself(targetParent);

        return addedEmbeddedSubprocAct;
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
        // TODO Test and debug when Merge re-enabled.
        if (true) {
            throw new RuntimeException("doReplaceYourself untested."); //$NON-NLS-1$
        }
        /*
         * Repalce the embedded subprocess task itself in target.
         */
        Object replacementObject =
                super.doReplaceYourself(targetParent, targetNode);

        /*
         * Add / replace copy of activity set.
         */
        addOrReplaceActivitySet(targetParent);

        subprocArtifacts.doReplaceYourself(targetParent, targetNode);

        return replacementObject;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     */
    @Override
    protected void doRemoveYourself(ITypedElement targetParent) {
        // TODO Test and debug when Merge re-enabled.
        if (true) {
            throw new RuntimeException("doRemoveYourself untested."); //$NON-NLS-1$
        }
        /*
         * Remove the embedded subprocess task itself from target.
         */
        super.doRemoveYourself(targetParent);

        /*
         * Remove the referenced activity set from target.
         */
        ActivitySet activitySet = getActivitySet();
        if (activitySet != null) {
            Process targetProcess = getAncestorProcess(targetParent);

            ActivitySet existingSet =
                    targetProcess.getActivitySet(activitySet.getId());
            if (existingSet != null) {
                targetProcess.getActivitySets().remove(existingSet);
            }
        }

        subprocArtifacts.removeYourself(targetParent);

        return;
    }

    /**
     * When operation on Pool, lane or parent embedded sub-process is performed
     * we will need to deal with OUR referenced ActivitySet too.
     * 
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#ancestorOperationPerformed(com.tibco.xpd.resources.ui.compare.nodes.emf.AncestorOperationNotification)
     * 
     * @param acn
     */
    @Override
    protected void ancestorOperationPerformed(AncestorOperationNotification acn) {
        // TODO Test and debug when Merge re-enabled.
        /*
         * I really don't think the current strategy is very feasible as if
         * Pool/Lane is replaced nothing will remove the the existing lane's
         * referenced activity sets
         */
        if (true) {
            throw new RuntimeException("ancestorOperationPerformed untested."); //$NON-NLS-1$
        }

        /*
         * If operation is performed on ancestor below process (i.e. pools,
         * pool, lanes, parent embedded sub-process) then we must perform the
         * relevant operation to our referenced ActivitySet.
         */

        EMFCompareNode notifyingAncestorNode = acn.getNotifierNode();

        ProcessCompareNode notifierAncestorProcessNode =
                getAncestorProcessNode(notifyingAncestorNode);

        /*
         * The notifying ancestor node must be above us in tree (of course), if
         * we found ProcessCompareNode from that ancestor then operation must
         * have been performed above us and ON or UNDER the Process node.
         * 
         * If the notifier IS the our ancestor process or above in tree then we
         * don't need to do anything because the lane's activities are under the
         * process node so will be dealt with naturally as model descendants of
         * the node.
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

            if (AncestorOperationType.POST_ADD.equals(acn.getType())
                    || AncestorOperationType.POST_REPLACE.equals(acn.getType())) {
                /*
                 * After our parent lane / pool / embedded sub-poc is added,
                 * then add our activity set to target process node.
                 */
                addOrReplaceActivitySet(targetProcessCompareNode);

            } else if (AncestorOperationType.POST_REMOVE.equals(acn.getType())) {
                /*
                 * Remove the referenced activity set from target process when
                 * parent lane / pool / embedded sub-process is removed.
                 */
                ActivitySet activitySet = getActivitySet();
                if (activitySet != null) {
                    Process targetProcess =
                            targetProcessCompareNode.getProcess();

                    ActivitySet existingSet =
                            targetProcess.getActivitySet(activitySet.getId());
                    if (existingSet != null) {
                        targetProcess.getActivitySets().remove(existingSet);
                    }
                }
            }

            if (AncestorOperationType.PRE_REMOVE.equals(acn.getType())) {
                /*
                 * Remove is called on the TARGET node - so find this lane node
                 * in target process node
                 */
                EMFCompareNode spArtifactsInTarget =
                        subprocArtifacts
                                .findMeInDescendents(targetProcessCompareNode);

                if (spArtifactsInTarget != null) {
                    spArtifactsInTarget
                            .removeYourself(notifierAncestorProcessNode);
                }

            } else if (AncestorOperationType.POST_REPLACE.equals(acn.getType())) {
                /*
                 * Replace is called on the SOURCE node.
                 */
                EMFCompareNode thisEmbInTarget =
                        findMeInDescendents(targetProcessCompareNode);

                if (thisEmbInTarget != null) {

                    EMFCompareNode spArtifactsInTarget =
                            subprocArtifacts
                                    .findMeInDescendents(thisEmbInTarget);

                    if (spArtifactsInTarget != null) {
                        subprocArtifacts.replaceYourself(thisEmbInTarget,
                                spArtifactsInTarget);
                    }
                }
            }
        }

        return;
    }

    /**
     * Add or replace a copy of the referenced activity set to the process
     * ancestor of the given targetParent.
     * 
     * @param targetParent
     */
    private void addOrReplaceActivitySet(ITypedElement targetParent) {
        ActivitySet activitySet = getActivitySet();
        if (activitySet != null) {
            Process targetProcess = getAncestorProcess(targetParent);

            if (targetProcess != null) {
                ActivitySet newActivitySet =
                        (ActivitySet) EcoreUtil.copy(activitySet);

                /*
                 * Make sure it doesn't already exist in target (if so, just
                 * replace it.
                 */
                ActivitySet existingSet =
                        targetProcess.getActivitySet(activitySet.getId());
                if (existingSet == null) {
                    /* Doesn't exist add it. */
                    targetProcess.getActivitySets().add(newActivitySet);

                } else {
                    /* Urk, already there - replace it. */
                    int idx =
                            targetProcess.getActivitySets()
                                    .indexOf(existingSet);

                    targetProcess.getActivitySets().set(idx, newActivitySet);
                }
            }
        }
    }

}
