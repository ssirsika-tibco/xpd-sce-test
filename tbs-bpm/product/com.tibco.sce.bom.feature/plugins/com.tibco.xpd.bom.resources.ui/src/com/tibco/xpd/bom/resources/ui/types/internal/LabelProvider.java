/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.types.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.types.ITypeLabelProvider;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * @author rassisi
 * 
 */
@Deprecated
public class LabelProvider implements ITypeLabelProvider, IPluginContribution {

    private String providerId;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#getText(java.lang
     * .Object)
     */
    public String getText(TypedItem element) {
        String text = null;
        String name = element.getName();
        String displayLabel = null;
        // Get display name
        if (element.getData() instanceof IndexerItem) {
            displayLabel =
                    ((IndexerItem) element.getData())
                            .get(BOMIndexerService.INDEXER_ATTR_DISPLAY_LABEL);
        }

        // Check if label and name should be displayed
        boolean isNameFiltered = WorkbenchActivityHelper.filterItem(this);
        if (displayLabel != null) {
            if (isNameFiltered) {
                text = displayLabel;
            } else {
                text = displayLabel + " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            text = name;
        }

        String qualifiedName = element.getQualifiedName();
        if (qualifiedName.length() > 0) {
            int pos =
                    qualifiedName
                            .lastIndexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR
                                    + name);
            if (pos != -1) {
                String packageName = qualifiedName.substring(0, pos);
                text += " - " + packageName; //$NON-NLS-1$
            }
        }

        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#decorateText(java
     * .lang.String, java.lang.Object)
     */
    public String decorateText(String text, TypedItem element) {
        try {
            String result = text;
            String text2 = getText(element);
            if (!text.equals(text2)) {
                result = text + " - " + getText(element); //$NON-NLS-1$
            }
            return result;
        } catch (Exception ex) {
            return ""; //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#getImage(com.tibco
     * .xpd.resources.ui.types.TypedItem)
     */
    public Image getImage(TypedItem element) {
        String imageUri = null;
        Image image = null;
        if (element.getImages().length > 0) {
            imageUri = (String) element.getImages()[0];
            if (imageUri != null) {
                image =
                        ExtendedImageRegistry.getInstance().getImage(URI
                                .createURI(imageUri));
            }
        }

        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#decorateImage(org
     * .eclipse.swt.graphics.Image, java.lang.Object)
     */
    public Image decorateImage(Image image, TypedItem element) {
        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#getResourceText(com
     * .tibco.xpd.resources.ui.types.TypedItem)
     */
    public String getResourceText(TypedItem element) {
        String text = null;
        if (element.getUriString() == null) {
            return element.getQualifiedName();
        }
        URI uri = URI.createURI(element.getUriString());
        if (uri != null) {
            uri = uri.trimFragment();
            // If this is not a platform URI then just display the
            // qualification
            if (uri.isPlatformResource()) {
                text = uri.toPlatformString(true);
            } else {
                text = uri.lastSegment();
            }
        }
        return text != null ? text : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#getImage(com.tibco
     * .xpd.resources.ui.types.TypeInfo, java.lang.Object)
     */
    public Image getResourceImage(TypedItem element) {
        final WorkbenchLabelProvider wbLabelProvider =
                new WorkbenchLabelProvider();
        Image img = null;
        if (element.getUriString() == null) {
            return null;
        }
        URI uri = URI.createURI(element.getUriString());
        if (uri != null) {
            uri = uri.trimFragment();
            String platformString = uri.toPlatformString(true);
            if (platformString != null) {
                IResource resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .findMember(platformString);
                if (resource != null) {
                    img = wbLabelProvider.getImage(resource);
                }
            }
        }
        return img;
    }

    public String getLocalId() {
        return "developer-name"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }
}
