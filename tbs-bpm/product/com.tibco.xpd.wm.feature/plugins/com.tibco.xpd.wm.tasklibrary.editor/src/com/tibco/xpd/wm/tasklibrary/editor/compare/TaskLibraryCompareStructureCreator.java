/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import java.io.InputStream;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.Xpdl2WorkingCopyCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyForTemporaryStream;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;

/**
 * Compare structure creator for task libraries.
 * 
 * @author aallway
 * @since 2 Dec 2010
 */
public class TaskLibraryCompareStructureCreator extends
        EMFCompareStructureCreator {
    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator#createEMFCompareNodeFactory()
     * 
     * @return
     */
    @Override
    protected EMFCompareNodeFactory createEMFCompareNodeFactory() {
        /*
         * The compareNodeTypeId "xpdlContent" matches the extensions attribute
         * of the contentMergeViewer contribution.
         */
        return new TaskLibraryCompareNodeFactory("tasksContent"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator#createWorkingCopyForInput(java.io.InputStream,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param inputStream
     * @param resource
     * @return
     */
    @Override
    protected AbstractWorkingCopy createWorkingCopyForInput(
            InputStream inputStream, IResource resource) {

        return new Xpdl2WorkingCopyForTemporaryStream(resource,
                Xpdl2FileType.PROCESS, inputStream);
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.IStructureCreator#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.TaskLibraryCompareStructureCreator_TaskLibraryCompare_title;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator#createWorkingCopyCompareNode(com.tibco.xpd.resources.wc.AbstractWorkingCopy,
     *      boolean, java.lang.Object,
     *      com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory)
     * 
     * @param workingCopy
     * @param freeWorkingCopyOnDispose
     * @param parentObject
     * @param compareNodeFactory
     * @return
     */
    @Override
    protected WorkingCopyCompareNode createWorkingCopyCompareNode(
            AbstractWorkingCopy workingCopy, boolean freeWorkingCopyOnDispose,
            Object parentObject, EMFCompareNodeFactory compareNodeFactory) {

        return new Xpdl2WorkingCopyCompareNode(workingCopy,
                freeWorkingCopyOnDispose, parentObject, compareNodeFactory);
    }

}
