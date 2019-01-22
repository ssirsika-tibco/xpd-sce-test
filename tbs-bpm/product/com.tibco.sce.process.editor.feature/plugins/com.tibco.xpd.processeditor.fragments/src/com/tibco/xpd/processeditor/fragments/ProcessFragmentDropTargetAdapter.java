/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.fragments;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;

import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.dnd.FragmentDropTargetAdapter;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.processeditor.fragments.internal.Messages;

/**
 * @author rsomayaj
 * 
 */
public class ProcessFragmentDropTargetAdapter extends FragmentDropTargetAdapter {

    /**
     * 
     */
    public ProcessFragmentDropTargetAdapter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentDropTargetAdapter#canDrop(org.eclipse.swt.dnd.DropTargetEvent,
     *      org.eclipse.swt.dnd.Transfer,
     *      com.tibco.xpd.fragments.IFragmentCategory)
     * 
     * @param event
     * @param transferType
     * @param targetCategory
     * @return
     */
    @Override
    protected boolean canDrop(DropTargetEvent event, Transfer transferType,
            IFragmentCategory targetCategory) {
        return true;
    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentDropTargetAdapter#doDrop(org.eclipse.swt.dnd.DropTargetEvent,
     *      org.eclipse.swt.dnd.Transfer,
     *      com.tibco.xpd.fragments.IFragmentCategory)
     * 
     * @param event
     * @param transferType
     * @param targetCategory
     * @throws CoreException
     */
    @Override
    protected void doDrop(DropTargetEvent event, Transfer transferType,
            IFragmentCategory targetCategory) throws CoreException {
        if (transferType instanceof FragmentLocalSelectionTransfer) {
            IStructuredSelection structuredSelection =
                    (IStructuredSelection) FragmentLocalSelectionTransfer
                            .getTransfer().getSelection();
            FragmentDataObject fragmentDataObject =
                    ProcessFragmentTransferHelper.INSTANCE
                            .getFragmentData(structuredSelection);
            if (fragmentDataObject != null) {
                createFragment(targetCategory,
                        Messages.ProcessFragmentDropTargetAdapter_NewFragment_label,
                        Messages.ProcessFragmentDropTargetAdapter_FragmentDesc_shortdesc,
                        fragmentDataObject.getFragmentData(),
                        fragmentDataObject.getFragmentImageData());
            }
        }

    }

    /**
     * @see com.tibco.xpd.fragments.dnd.FragmentDropTargetAdapter#getTransferTypes()
     * 
     * @return
     */
    @Override
    public Transfer[] getTransferTypes() {
        return new Transfer[] { FragmentLocalSelectionTransfer.getTransfer() };
    }
}
