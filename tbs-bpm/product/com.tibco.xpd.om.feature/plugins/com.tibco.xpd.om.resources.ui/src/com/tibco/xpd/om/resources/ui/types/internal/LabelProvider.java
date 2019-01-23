/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.types.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.types.ITypeLabelProvider;
import com.tibco.xpd.resources.ui.types.TypeUtil;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * Label Provider for the PickerDialog.
 * 
 * @author rassisi
 * 
 */
public class LabelProvider implements ITypeLabelProvider, IPluginContribution {

    private String providerId;

    /*
     * SID XPD-1605: Was getting problems starting studio so changed this label
     * provider construction to lazy
     */
    private ILabelProvider adapterFactoryLabelProvider = null;

    public String getProviderId() {
        return providerId;
    }

    @Override
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
    @Override
    public String getText(TypedItem element) {

        try {
            String text = null;
            Object resolved = TypeUtil.getTypedItemResolvedElement(element);
            String displayName = ""; //$NON-NLS-1$
            String name = element.getName();
            if (resolved instanceof NamedElement) {
                displayName = ((NamedElement) resolved).getDisplayName();
                name = ((NamedElement) resolved).getName();
            }

            boolean isNameFiltered = WorkbenchActivityHelper.filterItem(this);
            if (isNameFiltered) {
                text = displayName;
            } else {
                text = displayName + " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
            }

            String qualification = element.getQualifiedName();
            if (name.length() > 0) {
                if (qualification.length() > 0) {
                    int offset = 0;
                    if (qualification
                            .indexOf(OMWorkingCopy.JAVA_PACKAGE_SEPARATOR) != -1) {
                        offset = OMWorkingCopy.JAVA_PACKAGE_SEPARATOR.length();
                    } else if (qualification
                            .indexOf(OMWorkingCopy.UML_PACKAGE_SEPARATOR) != -1) {
                        offset = OMWorkingCopy.UML_PACKAGE_SEPARATOR.length();
                    }
                    qualification =
                            qualification.substring(0, qualification.length()
                                    - displayName.length() - offset);

                    if (isNameFiltered) {
                        text = displayName;
                    } else {
                        text = displayName + " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                    }

                    if (qualification.length() > 0) {
                        text = text + " - " + qualification; //$NON-NLS-1$
                    }

                } else {
                    text = displayName + " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                }
            } else {
                text = qualification;
            }

            return text;
        } catch (Exception ex) {
            return ""; //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#decorateText(java
     * .lang.String, java.lang.Object)
     */
    @Override
    public String decorateText(String text, TypedItem element) {
        String text2 = getText(element);
        if (text.equals(text2)) {
            return text;
        }
        if (text2.length() > 0) {
            return String.format("%s - %s", text, //$NON-NLS-1$
                    text2);
        }
        return text;
    }

    /**
     * Get the qualification of the given item
     * 
     * @param item
     * @return qualification of the event, empty string of no qualification
     *         found.
     */
    protected String getQualification(TypedItem item) {
        String qualification = ""; //$NON-NLS-1$

        if (item != null) {
            String qualifiedName = item.getQualifiedName();
            String name = item.getName();

            if (qualifiedName != null) {
                int q1 = (qualifiedName.indexOf("::") != -1) ? 1 : 0; //$NON-NLS-1$
                if (qualifiedName != name) {
                    qualification =
                            qualifiedName.substring(0, qualifiedName.length()
                                    - name.length() - q1);
                }
            } else {
                qualification = name;
            }
        }

        return qualification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#getImage(com.tibco
     * .xpd.resources.ui.types.TypedItem)
     */
    @Override
    public Image getImage(TypedItem element) {
        Object resolved = TypeUtil.getTypedItemResolvedElement(element);

        if (resolved != null) {
            if (adapterFactoryLabelProvider == null) {
                adapterFactoryLabelProvider =
                        new TransactionalAdapterFactoryLabelProvider(
                                XpdResourcesPlugin.getDefault()
                                        .getEditingDomain(), XpdResourcesPlugin
                                        .getDefault().getAdapterFactory());
            }
            return adapterFactoryLabelProvider.getImage(resolved);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeLabelProvider#decorateImage(org
     * .eclipse.swt.graphics.Image, java.lang.Object)
     */
    @Override
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
    @Override
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
                text = getQualification(element);
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
    @Override
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

    @Override
    public String getLocalId() {
        return "developer-name"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return OMResourcesUIActivator.PLUGIN_ID;
    }

}
