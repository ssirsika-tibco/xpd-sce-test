/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.specialfolders.extpoint;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;

/**
 * Represents an extension of the <i>specialFolder</i> extension point.
 * 
 * @author njpatel
 */
public class SpecialFoldersExtension implements ISpecialFolderModel {

    /**
     * Default multiplicity value is MANY.
     */
    private static final MultiplicityType MULTIPLICITY_DEFAULT = MultiplicityType.MANY;

    /**
     * <code>SpecialFolder</code> kind is settable by default.
     */
    private static final boolean UNSETTABLE_DEFAULT = false;

    private static final String ATT_KIND = "kind"; //$NON-NLS-1$

    private static final String ATT_NAME = "name"; //$NON-NLS-1$

    private static final String ATT_ICON = "icon"; //$NON-NLS-1$

    private static final String ATT_UNSETTABLE = "unsettable"; //$NON-NLS-1$

    private static final String ATT_MULTIPLE = "multiple"; //$NON-NLS-1$

    private static final String ATT_NAVIGATORCONTENTID = "navigatorContentId"; //$NON-NLS-1$

    private static final String ATT_ASSETID = "projectAssetId"; //$NON-NLS-1$

    private static final String ATT_ALLOWDUPLICATERES = "allowDuplicateResources"; //$NON-NLS-1$

    private static final boolean ALLOWDUPLICATERES_DEFAULT = true;

    private static final String ATT_ISSUEID = "issueId"; //$NON-NLS-1$

    private final IConfigurationElement configElement;

    private ImageDescriptor icon = null;

    private MultiplicityType multiplicity = null;

    /**
     * Represents extension of the <i>specialFolder</i> extension point
     * 
     * @param configElement
     *            Extension configuration element.
     */
    public SpecialFoldersExtension(IConfigurationElement configElement) {
        this.configElement = configElement;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderKindInfo#getIcon()
     */
    public ImageDescriptor getIcon() {
        if (icon == null) {
            String iconPath = getAttributeValue(ATT_ICON);
            // Get the icon descriptor
            if (iconPath != null) {
                icon = AbstractUIPlugin.imageDescriptorFromPlugin(configElement
                        .getNamespaceIdentifier(), iconPath);
            }
        }

        return icon;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderKindInfo#getKind()
     */
    public String getKind() {
        return getAttributeValue(ATT_KIND);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderKindInfo#getMultiplicity()
     */
    public MultiplicityType getMultiplicity() {
        if (multiplicity == null) {
            /*
             * Get the multiplicity value
             */
            String value = getAttributeValue(ATT_MULTIPLE);

            if (value != null) {
                multiplicity = Boolean.parseBoolean(value) ? MultiplicityType.MANY
                        : MultiplicityType.SINGLE;
            } else {
                // Set the default value
                multiplicity = MULTIPLICITY_DEFAULT;
            }
        }

        return multiplicity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderKindInfo#getName()
     */
    public String getName() {
        return getAttributeValue(ATT_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderKindInfo#getNavigatorContentId()
     */
    public String getNavigatorContentId() {
        return getAttributeValue(ATT_NAVIGATORCONTENTID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel#getAllowDuplicateResources()
     */
    public boolean isDuplicateResourcesAllowed() {
        String value = getAttributeValue(ATT_ALLOWDUPLICATERES);

        if (value != null) {
            return Boolean.parseBoolean(value);
        }

        return ALLOWDUPLICATERES_DEFAULT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderKindInfo#isUnsettable()
     */
    public boolean isUnsettable() {
        String value = getAttributeValue(ATT_UNSETTABLE);

        if (value != null) {
            return Boolean.parseBoolean(value);
        }

        return UNSETTABLE_DEFAULT;
    }

    /**
     * Get the value of the given attribute
     * 
     * @param attr
     * @return
     */
    private String getAttributeValue(String attr) {
        String value = null;
        if (attr != null && configElement != null) {
            value = configElement.getAttribute(attr);
        }

        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderKindInfo#getProjectAssetId()
     */
    public String getProjectAssetId() {
        return getAttributeValue(ATT_ASSETID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel#getIssueId()
     */
    public String getIssueId() {
        return getAttributeValue(ATT_ISSUEID);
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     *
     * @param o
     * @return
     */
    public int compareTo(ISpecialFolderModel o) {
        String currentName = this.getName().toLowerCase();
        String passedName = o.getName().toLowerCase();
        return currentName.compareTo(passedName);            
    }
}
