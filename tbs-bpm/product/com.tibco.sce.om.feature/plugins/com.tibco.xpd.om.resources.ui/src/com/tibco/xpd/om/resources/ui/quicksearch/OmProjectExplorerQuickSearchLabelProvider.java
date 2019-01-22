/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.internal.indexing.OMResourceIndexProvider;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * 
 * 
 * @author patkinso
 * @since 21 Jun 2012
 */
public class OmProjectExplorerQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {

    /**
     * @param partRef
     */
    public OmProjectExplorerQuickSearchLabelProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getElementTypeName(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getElementTypeName(Object element) {

        if (element instanceof IndexerItem) {
            String typeId = ((IndexerItem) element).getType();

            switch (OmProjectExplorerQuickSearchCategoryEnum.get(typeId)) {
            case OM_ORGANISATION:
                return Messages.OmProjectExplorerQuickSearchLabelProvider_TypeName_Organisation_label;
            case OM_ORG_UNIT:
                return Messages.OmProjectExplorerQuickSearchLabelProvider_TypeName_OrgUnit_label;
            case OM_POSITION:
                return Messages.OmProjectExplorerQuickSearchLabelProvider_TypeName_Position_label;
            case OM_GROUP:
                return Messages.OmProjectExplorerQuickSearchLabelProvider_TypeName_Group_label;
            }
        }

        return super.getElementTypeName(element);
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {

        String label = null;

        if ((element instanceof IndexerItem)) {
            IndexerItem indexElem = (IndexerItem) element;
            label = indexElem.get(OMResourceIndexProvider.DISPLAY_NAME);
            if (label == null || label.trim().length() == 0) {
                label = indexElem.getName();
                int idx =
                        label.lastIndexOf(OMWorkingCopy.JAVA_PACKAGE_SEPARATOR);
                if (idx >= 0) {
                    label = label.substring(idx + 1);
                }
            }
        }

        return (label != null) ? label : super.getText(element); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {
        if (element instanceof IndexerItem) {
            String typeId = ((IndexerItem) element).getType();
            String imgURIStr =
                    OmProjectExplorerQuickSearchCategoryEnum.get(typeId)
                            .getImageUri();

            if (imgURIStr != null) {
                Object imgObj = OMModelImages.getImageObject(imgURIStr);
                Image img =
                        ExtendedImageRegistry.getInstance().getImage(imgObj);
                if (img != null) {
                    return img;
                }
            }
        }
        return super.getImage(element);
    }

}
