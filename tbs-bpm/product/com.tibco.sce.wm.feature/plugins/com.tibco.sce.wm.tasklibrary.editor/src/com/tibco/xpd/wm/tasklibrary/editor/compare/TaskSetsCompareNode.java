/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import org.eclipse.compare.ITypedElement;

import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WrappedEListCompareNode;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Task sets list compare node.
 * <p>
 * This list need special handling for merge because it will be created under
 * the {@link TaskLibraryPackageCompareNode} BUT in the model the list will be
 * under the ONE pool for the ONE process.
 * 
 * 
 * @author aallway
 * @since 2 Dec 2010
 */
public class TaskSetsCompareNode extends WrappedEListCompareNode {

    private Process process;

    private Pool pool;

    /**
     * @param list
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     * @param label
     * @param image
     */
    public TaskSetsCompareNode(Process process, Pool pool,
            Object parentCompareNode, EMFCompareNodeFactory compareNodeFactory) {
        super(pool.getLanes(), Xpdl2Package.eINSTANCE.getPool_Lanes(),
                parentCompareNode, compareNodeFactory,
                Messages.TaskLibraryPackageCompareNode_TaskSets_label,
                TaskLibraryEditorPlugin.getDefault().getImageRegistry()
                        .get(TaskLibraryEditorContstants.IMG_TASKSET));

        this.process = process;
        this.pool = pool;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EListCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        /*
         * Target parent should be a TasklibraryPackageCompareNode() from which
         * we can get the package, thence the one process and thence the target
         * pool with target taskssets.
         */
        // TODO Task set merg will need special handling
        // TODO get target packaghe, process and pool from targetParent.
        // TODO overwrite/add target pool with copy of our pool
        // TODO overwrite process's activities and artifacts (unless in the
        // long-run artifact/activity copy themselves when they detext parent
        // task set being copied
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
}
