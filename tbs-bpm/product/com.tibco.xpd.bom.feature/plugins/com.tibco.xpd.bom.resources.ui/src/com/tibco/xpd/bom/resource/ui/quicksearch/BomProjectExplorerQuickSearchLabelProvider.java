/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.bom.resource.ui.quicksearch;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * Project explorer quick search label provider for BOM elements.
 * 
 * @author aallway
 * @since 3.2
 */
public class BomProjectExplorerQuickSearchLabelProvider extends
        AbstractQuickSearchLabelProvider {

    /**
     * @param partRef
     */
    public BomProjectExplorerQuickSearchLabelProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);
    }

    @Override
    public String getElementTypeName(Object element) {
        if (element instanceof IndexerItem) {
            String type = ((IndexerItem) element).getType();

            if (ResourceItemType.PACKAGE.name().equals(type)) {
                return Messages.BomEditorQuickSearchLabelProvider_TypeName_Package_label;
            } else if (ResourceItemType.CLASS.name().equals(equals(type))) {
                return Messages.BomEditorQuickSearchLabelProvider_TypeName_Class_label;
            } else if (ResourceItemType.ENUMERATION.name().equals(type)) {
                return Messages.BomEditorQuickSearchLabelProvider_TypeName_Enumeration_label;
            } else if (ResourceItemType.PRIMITIVE_TYPE.name().equals(type)) {
                return Messages.BomEditorQuickSearchLabelProvider_TypeName_PrimitiveType_label;
            }
        }

        return super.getElementTypeName(element);
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof IndexerItem) {
            String imgURIStr =
                    ((IndexerItem) element)
                            .get(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI);

            if (imgURIStr != null) {
                URI imageUri = URI.createURI(imgURIStr);

                if (imageUri != null) {
                    Image img =
                            ExtendedImageRegistry.getInstance()
                                    .getImage(imageUri);
                    if (img != null) {
                        return img;
                    }
                }
            }
        }
        return super.getImage(element);
    }

    @Override
    public String getText(Object element) {

        String label = null;

        if ((element instanceof IndexerItem)) {
            IndexerItem indexElem = (IndexerItem) element;
            label = indexElem.get(BOMIndexerService.INDEXER_ATTR_DISPLAY_LABEL);
            if (label == null || label.trim().length() == 0) {
                label = indexElem.getName();
                int idx =
                        label.lastIndexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR);
                if (idx >= 0) {
                    label = label.substring(idx + 1);
                }
            }
        }

        return (label != null) ? label : super.getText(element);
        //$NON-NLS-1$
    }

}
