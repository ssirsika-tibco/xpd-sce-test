/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.compare.Messages;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.IChildCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.XpdPropertyInfoNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.FilteredEListCompareNode;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Specialised compare node for processes.
 * <p>
 * Pools, artifacts, message flows and associations are stored in model at
 * Package level whereas as far as the user is concerned they are stored within
 * a process.
 * <p>
 * So {@link PackageCompareNode} excludes them and we must add them.
 * 
 * @author aallway
 * @since 19 Oct 2010
 */
public class ProcessCompareNode extends NamedElementCompareNode {

    private FilteredEListCompareNode processMessageFlows = null;

    private FilteredEListCompareNode processAssociations = null;

    private FilteredEListCompareNode processPools = null;

    private FilteredEListCompareNode dataFields = null;

    private FilteredEListCompareNode correlationFields = null;

    private FilteredEListCompareNode pageflowArtifacts = null;

    private Process process;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public ProcessCompareNode(Process process, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(process, listIndex, feature, parentCompareNode,
                compareNodeFactory);
        this.process = process;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#excludeChildFeature(org.eclipse.emf.ecore.EStructuralFeature,
     *      java.lang.Object)
     * 
     * @param feature
     * @param child
     * @return
     */
    @Override
    protected boolean excludeChildFeature(EStructuralFeature feature,
            Object child) {
        /*
         * Exclude the activities feature because activities are shown in their
         * respective parent lanes (except for pageflow in which case we don't
         * show the lanes so we include the activities directly)
         */
        if (feature == Xpdl2Package.eINSTANCE.getFlowContainer_Activities()) {
            if (!Xpdl2ModelUtil.isPageflow(process)) {
                return true;
            }

        } else if (feature == Xpdl2Package.eINSTANCE.getProcess_ActivitySets()) {
            return true;
        } else if (feature == Xpdl2Package.eINSTANCE
                .getDataFieldsContainer_DataFields()) {
            /*
             * We create separate lists for datafields and correlation data
             * separately.
             */
            return true;
        }

        return super.excludeChildFeature(feature, child);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#createChildren(com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory)
     * 
     * @param compareNodeFactory
     * @return
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        Process process = (Process) getEObject();
        List<Object> children = new ArrayList<Object>();

        /*
         * Add filtered lists of elements that are 'in process' but stored at
         * package level.
         */
        if (!Xpdl2ModelUtil.isPageflow(process)) {
            processPools =
                    new FilteredEListCompareNode(process.getPackage()
                            .getPools(), Xpdl2Package.eINSTANCE
                            .getPackage_Pools(), this, compareNodeFactory,
                            Messages.ProcessCompareNodeFactory_Pools_label,
                            Xpdl2ResourcesPlugin.getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.IMG_POOL),
                            ".ProcessPools") { //$NON-NLS-1$

                        @Override
                        protected EList getTargetList(
                                EMFCompareNode targetParent) {
                            Package targetPackage =
                                    getTargetPackage(targetParent);
                            if (targetPackage != null) {
                                return targetPackage.getPools();
                            }
                            return null;
                        }

                        @Override
                        protected boolean includeInVirtualParent(Object object) {
                            if (getProcess().getId().equals(((Pool) object)
                                    .getProcessId())) {
                                return true;
                            }
                            return false;
                        }

                    };

            processPools.setAtomic(true);
            children.add(processPools);
        }

        processAssociations =
                new FilteredEListCompareNode(
                        process.getPackage().getAssociations(),
                        Xpdl2Package.eINSTANCE.getPackage_Associations(),
                        this,
                        compareNodeFactory,
                        Messages.ProcessCompareNodeFactory_Associations_label,
                        Xpdl2ResourcesPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(Xpdl2ResourcesConsts.ICON_ASSOCIATIONS_FOLDER),
                        ".ProcessAssocaitions") { //$NON-NLS-1$

                    @Override
                    protected EList getTargetList(EMFCompareNode targetParent) {
                        Package targetPackage = getTargetPackage(targetParent);
                        if (targetPackage != null) {
                            return targetPackage.getAssociations();
                        }
                        return null;
                    }

                    @Override
                    protected boolean includeInVirtualParent(Object object) {
                        Process parentProcess =
                                Xpdl2ModelUtil.getProcess((Association) object);
                        if (parentProcess != null
                                && getProcess().getId().equals(parentProcess
                                        .getId())) {
                            return true;
                        }
                        return false;
                    }

                };

        processAssociations.setAtomic(true);
        children.add(processAssociations);

        if (!Xpdl2ModelUtil.isPageflow(process)) {
            processMessageFlows =
                    new FilteredEListCompareNode(
                            process.getPackage().getMessageFlows(),
                            Xpdl2Package.eINSTANCE.getPackage_MessageFlows(),
                            this,
                            compareNodeFactory,
                            Messages.ProcessCompareNodeFactory_MessageFlows_label,
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.ICON_MESSAGEFLOW_FOLDER),
                            ".ProcessMessageFlows") { //$NON-NLS-1$

                        @Override
                        protected EList getTargetList(
                                EMFCompareNode targetParent) {
                            Package targetPackage =
                                    getTargetPackage(targetParent);
                            if (targetPackage != null) {
                                return targetPackage.getMessageFlows();
                            }
                            return null;
                        }

                        @Override
                        protected boolean includeInVirtualParent(Object object) {
                            Process parentProcess =
                                    Xpdl2ModelUtil
                                            .getProcess((MessageFlow) object);
                            if (parentProcess != null
                                    && getProcess().getId()
                                            .equals(parentProcess.getId())) {
                                return true;
                            }
                            return false;
                        }

                    };
            processMessageFlows.setAtomic(true);
            children.add(processMessageFlows);
        }

        /*
         * Separate fields into data fields and correlation data.
         */
        dataFields =
                new FilteredEListCompareNode(process.getDataFields(),
                        Xpdl2Package.eINSTANCE
                                .getDataFieldsContainer_DataFields(), this,
                        compareNodeFactory,
                        Messages.ProcessCompareNodeFactory_DataFields_label,
                        Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2ResourcesConsts.ICON_DATAFIELD),
                        ".NormalDataFields") { //$NON-NLS-1$

                    @Override
                    protected boolean includeInVirtualParent(Object object) {
                        if (!((DataField) object).isCorrelation()) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    protected EList getTargetList(EMFCompareNode targetParent) {
                        return ((ProcessCompareNode) targetParent).getProcess()
                                .getDataFields();
                    }
                };

        dataFields.setAtomic(true);
        children.add(dataFields);

        correlationFields =
                new FilteredEListCompareNode(
                        process.getDataFields(),
                        Xpdl2Package.eINSTANCE
                                .getDataFieldsContainer_DataFields(),
                        this,
                        compareNodeFactory,
                        Messages.ProcessCompareNode_CorrelationData_label,
                        Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2ResourcesConsts.ICON_CORRELATIONDATA),
                        ".CorrelationDataFields") { //$NON-NLS-1$

                    @Override
                    protected boolean includeInVirtualParent(Object object) {
                        if (((DataField) object).isCorrelation()) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    protected EList getTargetList(EMFCompareNode targetParent) {
                        return ((ProcessCompareNode) targetParent).getProcess()
                                .getDataFields();
                    }
                };
        correlationFields.setAtomic(true);
        children.add(correlationFields);

        /*
         * For pageflow we need to include artifacts directly
         */
        if (Xpdl2ModelUtil.isPageflow(process)) {
            pageflowArtifacts =
                    new FilteredEListCompareNode(process.getPackage()
                            .getArtifacts(), Xpdl2Package.eINSTANCE
                            .getPackage_Artifacts(), this, compareNodeFactory,
                            Messages.ProcessCompareNodeFactory_Artifacts_label,
                            Xpdl2ResourcesPlugin.getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.IMG_DATAOBJECT),
                            ".laneArtifacts") { //$NON-NLS-1$

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
                            List<Lane> pageflowLanes =
                                    Xpdl2ModelUtil
                                            .getProcessLanes(getProcess());
                            if (!pageflowLanes.isEmpty()) {
                                /*
                                 * Pageflows have only one (hidden) lane that
                                 * artifact will be in if we are to include it
                                 * for this process
                                 */
                                Lane lane = pageflowLanes.get(0);

                                EObject parent =
                                        Xpdl2ModelUtil
                                                .getContainer((EObject) object);
                                if (parent instanceof Lane
                                        && lane.getId().equals(((Lane) parent)
                                                .getId())) {
                                    return true;
                                }
                            }
                            return false;
                        }

                    };

            pageflowArtifacts.setAtomic(true);
            children.add(pageflowArtifacts);
        }

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
     * @param childArray
     * 
     * @deprecated don't think we ned this now.
     */
    @Deprecated
    private void preCreateDescendents(Object[] childArray) {
        for (Object child : childArray) {
            if (child instanceof IStructureComparator) {
                Object[] descends =
                        ((IStructureComparator) child).getChildren();

                preCreateDescendents(descends);
            }
        }
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

        /* When I'm added, then add my pools etc. */
        if (Xpdl2ModelUtil.isPageflow(process)) {
            /* Deadl with pageflow process artifacts in target package. */
            if (pageflowArtifacts != null) {
                pageflowArtifacts.addYourself(targetParent);
            }

            // TODO Need to deal with pageflow hidden pool when process merged
            throw new RuntimeException(
                    "Need to deadl with pageflow hidden pool when process merged"); //$NON-NLS-1$
        } else {
            /*
             * For non-pageflow processes we need to tell the pools list to add
             * itself to target too.
             */
            if (processPools != null) {
                processPools.doAddYourself(targetParent);
            }

            /* Message flows can only exist for non-pageflow */
            if (processMessageFlows != null) {
                processMessageFlows.doAddYourself(targetParent);
            }
        }

        processAssociations.doAddYourself(targetParent);

        /*
         * Don't do anything special for data fields / correlation fields cos
         * even though we've separated them into separate lists they are just
         * children of process object so will be copied anyway.
         */

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

        /* When I'm replaced, then replaced my pools etc. */
        if (Xpdl2ModelUtil.isPageflow(process)) {
            /* Deadl with pageflow process artifacts in target package. */
            if (pageflowArtifacts != null) {
                pageflowArtifacts.replaceYourself(targetParent, targetNode);
            }

            // TODO Need to deal with pageflow hidden pool when process merged
            throw new RuntimeException(
                    "Need to deadl with pageflow hidden pool when process merged"); //$NON-NLS-1$
        } else {
            /*
             * For non-pageflow processes we need to tell the pools list to
             * replace itself to target too.
             */
            if (processPools != null) {
                processPools.doReplaceYourself(targetParent, targetNode);
            }
            /* Message flows can only exist for non-pageflow */
            if (processMessageFlows != null) {
                processMessageFlows.doReplaceYourself(targetParent, targetNode);
            }
        }

        processAssociations.doReplaceYourself(targetParent, targetNode);

        /*
         * Don't do anything special for data fields / correlation fields cos
         * even though we've separated them into separate lists they are just
         * children of process object so will be copied anyway.
         */

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

        /* When I'm removed, then remove my pools etc. */
        if (Xpdl2ModelUtil.isPageflow(process)) {
            /* Deadl with pageflow process artifacts in target package. */
            if (pageflowArtifacts != null) {
                pageflowArtifacts.removeYourself(targetParent);
            }

            // TODO Need to deal with pageflow hidden pool when process merged
            throw new RuntimeException(
                    "Need to deadl with pageflow hidden pool when process merged"); //$NON-NLS-1$
        } else {
            /*
             * For non-pageflow processes we need to tell the pools list to
             * remove itself from target too.
             */
            if (processPools != null) {
                processPools.doRemoveYourself(targetParent);
            }

            /* Message flows can only exist for non-pageflow */
            if (processMessageFlows != null) {
                processMessageFlows.doRemoveYourself(targetParent);
            }
        }

        processAssociations.doRemoveYourself(targetParent);

        /*
         * Don't do anything special for data fields / correlation fields cos
         * even though we've separated them into separate lists they are just
         * children of process object so will be copied anyway.
         */

    }

    /**
     * 
     * @param targetParent
     * @return Target's {@link Package} object or <code>null</code>
     */
    protected Package getTargetPackage(EMFCompareNode targetParent) {
        IChildCompareNode parent = targetParent;
        while (parent != null && !(parent instanceof PackageCompareNode)) {
            Object p = parent.getParent();
            if (p instanceof IChildCompareNode) {
                parent = (IChildCompareNode) p;
            } else {
                parent = null;
            }
        }

        if (parent instanceof PackageCompareNode) {
            return ((PackageCompareNode) parent).getPackage();
        }
        return null;
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

        ProcessInterface implementedProcessInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);
        if (implementedProcessInterface != null) {
            props
                    .add(new XpdPropertyInfoNode(
                            Messages.ProcessCompareNode_ImplementsInterface_label
                                    + " " //$NON-NLS-1$
                                    + Xpdl2ModelUtil
                                            .getDisplayNameOrName(implementedProcessInterface),
                            20, this, this.getType(),
                            "implementedInterfaceInfo")); //$NON-NLS-1$
        }

        Object bsCategory =
                Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory());
        if (bsCategory instanceof String && ((String) bsCategory).length() > 0) {
            props.add(new XpdPropertyInfoNode(
                    Messages.ProcessCompareNode_BusinessCategory_label + " " //$NON-NLS-1$
                            + bsCategory, 20, this, this.getType(),
                    "businessServiceCategoryInfo")); //$NON-NLS-1$
        }

        return props;
    }

    /**
     * @return the process
     */
    public Process getProcess() {
        return process;
    }

}
