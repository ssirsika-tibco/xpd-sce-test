/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.quicksearch;

import java.util.LinkedHashSet;
import java.util.Set;

import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.ui.types.TypeInfo;

/**
 * Central reference for quick search category information. Links category's
 * type id with its image uri, and holds its enabled state.
 * 
 * Created to provides convenience methods and decouple deprecated TypeInfo from
 * the other OmProjectExplorerQuickSearch... classes
 * 
 * @author patkinso
 * @since 21 Jun 2012
 */
public enum OmProjectExplorerQuickSearchCategoryEnum {

    // XPD-5300: Added Dynamic organization/orgunit
    OM_ORG_UNIT(OMTypesFactory.TYPE_ORG_UNIT, OMModelImages.IMAGE_ORG_UNIT), //
    OM_DYNAMIC_ORG_UNIT(OMTypesFactory.TYPE_DYNAMIC_ORG_UNIT,
            OMModelImages.IMAGE_DYNAMIC_ORG_UNIT), //
    OM_POSITION(OMTypesFactory.TYPE_POSITION, OMModelImages.IMAGE_POSITION), //
    OM_GROUP(OMTypesFactory.TYPE_GROUP, OMModelImages.IMAGE_GROUP), //
    OM_ORGANISATION(OMTypesFactory.TYPE_ORGANIZATION,
            OMModelImages.IMAGE_ORGANISATION), //
    OM_DYNAMIC_ORGANISATION(OMTypesFactory.TYPE_DYNAMIC_ORGANIZATION,
            OMModelImages.IMAGE_DYNAMIC_ORGANISATION);

    private boolean enabled = false; // is explicitly enabled?

    private String typeId, imageUri;

    private OmProjectExplorerQuickSearchCategoryEnum(TypeInfo typeInfo,
            String imageUri) {
        typeId = typeInfo.getTypeId();
        this.imageUri = imageUri;
    }

    public String getTypeId() {
        return typeId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getImageUri() {
        return imageUri;
    }

    /**
     * @param typeId
     * @return <code>OmProjectExplorerQuickSearchCategoryEnum</code> value with
     *         the matching type ID passed in
     */
    public static OmProjectExplorerQuickSearchCategoryEnum get(String typeId) {
        for (OmProjectExplorerQuickSearchCategoryEnum value : values()) {
            if (value.getTypeId().equals(typeId)) {
                return value;
            }
        }

        return null;
    }

    /**
     * @return Array of type IDs corresponding to the
     *         <code>OmProjectExplorerQuickSearchCategoryEnum</code> values.
     *         Ordered according to the order of the declared enum values.
     */
    public static String[] getTypeIds() {

        Set<String> typeIds = new LinkedHashSet<String>();

        for (OmProjectExplorerQuickSearchCategoryEnum value : values()) {
            typeIds.add(value.typeId);
        }

        return (typeIds.size() == values().length) ? typeIds
                .toArray(new String[typeIds.size()]) : null;
    }

}
