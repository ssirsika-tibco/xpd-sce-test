/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.ITypedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode;
import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementListCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Task library package compare node.
 * <p>
 * Because task library is an xpdl2 package with a single process that represent
 * the library - we will wrap up this one-to-one relationship by placing the
 * task-library-process element CONTENT directly under this package content.
 * 
 * @author aallway
 * @since 2 Dec 2010
 */
public class TaskLibraryPackageCompareNode extends NamedElementCompareNode {

    private Package pkg;

    private Process process = null;

    private TaskSetsCompareNode taskSetsCompareNode = null;

    /**
     * @param namedElement
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public TaskLibraryPackageCompareNode(Package pkg, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(pkg, listIndex, feature, parentCompareNode, compareNodeFactory);

        this.pkg = pkg;
        if (!pkg.getProcesses().isEmpty()) {
            process = pkg.getProcesses().get(0);
        }
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
        if (feature == Xpdl2Package.eINSTANCE.getPackage_Pools()) {
            /* Pools are implied by process in task library. */
            return true;

        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Artifacts()) {
            /* Artifacts are shown under task sets. */
            return true;

        } else if (feature == Xpdl2Package.eINSTANCE.getPackage_Processes()) {
            /*
             * There is only one process in a task library. (which we will add
             * manually in createChildren)
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
        List<Object> children = new ArrayList<Object>();

        /* Add the default EMF mode (expect for those excluded above children */
        Object[] defaultChildren = super.createChildren(compareNodeFactory);
        for (Object child : defaultChildren) {
            children.add(child);
        }

        /*
         * Add the content one process that is the process for this task
         * library.
         */
        if (process != null) {
            /*
             * Add the data fields from the task library process directly
             * underneath this package.
             */
            if (!process.getDataFields().isEmpty()) {
                Object fieldsNode =
                        new DataFieldsRedirectFromProcessCompareNode(process,
                                this, compareNodeFactory);

                if (fieldsNode instanceof XpdCompareNode) {
                    ((XpdCompareNode) fieldsNode).setAtomic(true);
                }
                children.add(fieldsNode);
            }

            /*
             * Add the participants from the task library process directly
             * underneath this package.
             */
            if (!process.getDataFields().isEmpty()) {
                Object fieldsNode =
                        new ParticipantsRedirectFromProcessCompareNode(process,
                                this, compareNodeFactory);

                if (fieldsNode instanceof XpdCompareNode) {
                    ((XpdCompareNode) fieldsNode).setAtomic(true);
                }
                children.add(fieldsNode);
            }

            /*
             * Add the task sets that are in the ONE pool for the ONE process.
             */
            if (!pkg.getPools().isEmpty()) {
                Pool pool = pkg.getPools().get(0);

                taskSetsCompareNode =
                        new TaskSetsCompareNode(process, pool, this,
                                getCompareNodeFactory());
                taskSetsCompareNode.setAtomic(true);
                children.add(taskSetsCompareNode);
            }
        }

        return children.toArray();
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EObjectCompareNode#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        return TaskLibraryEditorPlugin.getDefault().getImageRegistry()
                .get(TaskLibraryEditorContstants.ICON_TASKLIBRARY);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        if (process != null) {
            return Xpdl2ModelUtil.getDisplayNameOrName(process);
        }
        return super.getName();
    }

    /**
     * NOTE: Should not need special merge handling as a whole package replace
     * should be handled by compare node.
     * 
     */

    /**
     * Data fields from task library process BUT displayed underneath task
     * library package.
     * 
     * 
     * @author aallway
     * @since 2 Dec 2010
     */
    private class DataFieldsRedirectFromProcessCompareNode extends
            NamedElementListCompareNode {

        /**
         * 
         * @param process
         * @param parentCompareNode
         * @param compareNodeFactory
         */
        public DataFieldsRedirectFromProcessCompareNode(Process process,
                Object parentCompareNode,
                EMFCompareNodeFactory compareNodeFactory) {
            super(process.getDataFields(), Xpdl2Package.eINSTANCE
                    .getDataFieldsContainer_DataFields(), parentCompareNode,
                    compareNodeFactory,
                    Messages.TaskLibraryPackageCompareNode_DataFields_label,
                    TaskLibraryEditorPlugin.getDefault().getImageRegistry()
                            .get(TaskLibraryEditorContstants.ICON_DATAFIELD));
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
         * 
         * @param targetParent
         * @return
         */
        @Override
        protected Object doAddYourself(ITypedElement targetParent) {
            // TODO When merging is implemented this list will have to copy
            // itself to the target libraries process list (Not the parent
            // package node!)
            throw new RuntimeException(
                    "Special merge handling is required for datafields in task library"); //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
         * 
         * @param targetParent
         */
        @Override
        protected void doRemoveYourself(ITypedElement targetParent) {
            // TODO When merging is implemented this list will have to copy
            // itself to the target libraries process list (Not the parent
            // package node!)
            throw new RuntimeException(
                    "Special merge handling is required for datafields in task library"); //$NON-NLS-1$
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
            // TODO When merging is implemented this list will have to copy
            // itself to the target libraries process list (Not the parent
            // package node!)
            throw new RuntimeException(
                    "Special merge handling is required for datafields in task library"); //$NON-NLS-1$
        }
    }

    /**
     * Participants from task library process BUT displayed underneath task
     * library package.
     * 
     * 
     * @author aallway
     * @since 2 Dec 2010
     */
    private class ParticipantsRedirectFromProcessCompareNode extends
            NamedElementListCompareNode {

        /**
         * 
         * @param process
         * @param parentCompareNode
         * @param compareNodeFactory
         */
        public ParticipantsRedirectFromProcessCompareNode(Process process,
                Object parentCompareNode,
                EMFCompareNodeFactory compareNodeFactory) {
            super(process.getParticipants(), Xpdl2Package.eINSTANCE
                    .getParticipantsContainer_Participants(),
                    parentCompareNode, compareNodeFactory,
                    Messages.TaskLibraryPackageCompareNode_Participants_label,
                    TaskLibraryEditorPlugin.getDefault().getImageRegistry()
                            .get(TaskLibraryEditorContstants.ICON_PARTICIPANT));
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
         * 
         * @param targetParent
         * @return
         */
        @Override
        protected Object doAddYourself(ITypedElement targetParent) {
            // TODO When merging is implemented this list will have to copy
            // itself to the target libraries process list (Not the parent
            // package node!)
            throw new RuntimeException(
                    "Special merge handling is required for datafields in task library"); //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
         * 
         * @param targetParent
         */
        @Override
        protected void doRemoveYourself(ITypedElement targetParent) {
            // TODO When merging is implemented this list will have to copy
            // itself to the target libraries process list (Not the parent
            // package node!)
            throw new RuntimeException(
                    "Special merge handling is required for datafields in task library"); //$NON-NLS-1$
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
            // TODO When merging is implemented this list will have to copy
            // itself to the target libraries process list (Not the parent
            // package node!)
            throw new RuntimeException(
                    "Special merge handling is required for datafields in task library"); //$NON-NLS-1$
        }
    }
}
