/**
 * ProcessElementsQuickSearchLabelProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.quickSearchContribution;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * ProcessElementsQuickSearchLabelProvider
 * 
 */
public class ProcessElementsQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {

    /**
     * @param partRef
     */
    public ProcessElementsQuickSearchLabelProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);
    }

    @Override
    public String getElementTypeName(Object element) {
        if (element instanceof IndexerItem) {
            String type = ((IndexerItem) element).getType();

            if (ProcessResourceItemType.PROCESS.toString().equals(type)) {
                return Messages.ProcessElementsQuickSearchLabelProvider_Process_label;
                
            } else if (ProcessResourceItemType.PROCESSINTERFACE.toString()
                    .equals(type)) {
                return Messages.ProcessElementsQuickSearchLabelProvider_ProcessInterface_label;
                
            } else if (ProcessResourceItemType.PROCESSPACKAGE.toString()
                    .equals(type)) {
                return Messages.ProcessElementsQuickSearchLabelProvider_Package_label;
            } else if (ProcessResourceItemType.PAGEFLOW.toString()
                    .equals(type)) {
                return Messages.ProcessElementsQuickSearchLabelProvider_ProcessElementsQuickSearchLabelProvider_Pageflow_label;
            }
        }

        return super.getElementTypeName(element);
    }
    
    @Override
    public Image getImage(Object element) {
        if (element instanceof IndexerItem) {
            Image img = ProcessUIUtil.getImageForIndexedItem((IndexerItem)element);
            if (img != null) {
                return img;
            }
        }
        return super.getImage(element);
    }
    
    @Override
    public String getText(Object element) {
        if (element instanceof IndexerItem) {
            String label = ProcessUIUtil.getLabelForIndexedItem((IndexerItem)element);
            if (label != null) {
                return label;
            }
        }
        return super.getText(element);
    }
}
