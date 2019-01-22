/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.commonpicker;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.indexing.OMResourceIndexProvider;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * Provides labels for Org. Model specific {@link PickerItem}s.
 * 
 * @author Jan Arciuchiewicz
 */
public class OMPickerLabelProvider extends BasePickerLabelProvider implements
        IPluginContribution {

    @Override
    public Image getImage(PickerItem element) {
        if (element instanceof PickerItem) {
            PickerItem pickerItem = element;
            String imgPath = TYPE_TO_IMAGE_PATH_MAP.get(pickerItem.getType());
            if (imgPath != null) {
                Object imageObject = OMModelImages.getImageObject(imgPath);
                if (imageObject != null) {
                    return ExtendedImageRegistry.INSTANCE.getImage(imageObject);
                }
            }
        }
        return super.getImage(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(PickerItem element) {
        if (element instanceof PickerItem) {
            PickerItem pickerItem = element;
            try {
                String text = null;
                String name = pickerItem.getName();
                String displayName =
                        pickerItem.get(OMResourceIndexProvider.DISPLAY_NAME);
                displayName = displayName == null ? "" : displayName; //$NON-NLS-1$

                boolean isNameFiltered =
                        WorkbenchActivityHelper.filterItem(this);
                if (isNameFiltered && !displayName.trim().isEmpty()) {
                    text = displayName;
                } else {
                    text = displayName + " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                }

                String qualification = pickerItem.getQualifiedName();
                if (name.length() > 0) {
                    if (qualification.length() > 0) {
                        int offset = 0;
                        if (qualification
                                .indexOf(OMWorkingCopy.JAVA_PACKAGE_SEPARATOR) != -1) {
                            offset =
                                    OMWorkingCopy.JAVA_PACKAGE_SEPARATOR
                                            .length();
                        } else if (qualification
                                .indexOf(OMWorkingCopy.UML_PACKAGE_SEPARATOR) != -1) {
                            offset =
                                    OMWorkingCopy.UML_PACKAGE_SEPARATOR
                                            .length();
                        }
                        qualification =
                                qualification
                                        .substring(0, qualification.length()
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
        return super.getText(element);
    }

    @Override
    public String getLocalId() {
        return "developer-name"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return OMResourcesUIActivator.PLUGIN_ID;
    }

    /** Maps picker element types to image paths. */
    private static final Map<String, String> TYPE_TO_IMAGE_PATH_MAP =
            new HashMap<String, String>();
    static {
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_LOCATION_TYPE,
                OMModelImages.IMAGE_LOCATION_TYPE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORGANIZATION_TYPE,
                OMModelImages.IMAGE_ORGANISATION_TYPE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORG_UNIT_TYPE,
                OMModelImages.IMAGE_ORG_UNIT_TYPE);
        TYPE_TO_IMAGE_PATH_MAP
                .put(OMTypeQuery.TYPE_ID_ORG_UNIT_RELATIONSHIP_TYPE,
                        OMModelImages.IMAGE_ORG_UNIT_RELATIONSHIP_TYPE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_POSITION_TYPE,
                OMModelImages.IMAGE_POSITION_TYPE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_RESOURCE_TYPE,
                OMModelImages.IMAGE_RESOURCE_TYPE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ABSTRACT_CAPABILITY,
                OMModelImages.IMAGE_CAPABILITY);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ATTRIBUTE,
                OMModelImages.IMAGE_ATTRIBUTE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ATTRIBUTE_TYPE,
                OMModelImages.IMAGE_ATTRIBUTE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ATTRIBUTE_VALUE,
                OMModelImages.IMAGE_ATTRIBUTE_VALUE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_AUTHORIZABLE, null);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_BASE_ORG_MODEL,
                OMModelImages.IMAGE_ORG_MODEL);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORG_MODEL,
                OMModelImages.IMAGE_ORG_MODEL);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORG_META_MODEL,
                OMModelImages.IMAGE_ORG_META_MODEL);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_CAPABILITY,
                OMModelImages.IMAGE_CAPABILITY);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_CAPABILITY_CATEGORY,
                OMModelImages.IMAGE_CAPABILITY_CATEGORY);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_PRIVILEGE_CATEGORY,
                OMModelImages.IMAGE_PRIVILEGE_CATEGORY);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ENUM_VALUE,
                OMModelImages.IMAGE_ENUM_VALUE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_FEATURE, null);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_GROUP,
                OMModelImages.IMAGE_GROUP);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_LOCATION,
                OMModelImages.IMAGE_LOCATION);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_MULTIPLE_FEATURE, null);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORGANIZATION,
                OMModelImages.IMAGE_ORGANISATION);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORG_UNIT,
                OMModelImages.IMAGE_ORG_UNIT);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORG_UNIT_FEATURE,
                OMModelImages.IMAGE_ORG_UNIT_FEATURE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORG_UNIT_RELATIONSHIP,
                OMModelImages.IMAGE_ORG_UNIT_RELATIONSHIP);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_POSITION,
                OMModelImages.IMAGE_POSITION);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_POSITION_FEATURE,
                OMModelImages.IMAGE_POSITION_FEATURE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_PRIVILEGE,
                OMModelImages.IMAGE_PRIVILEGE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_RESOURCE,
                OMModelImages.IMAGE_RESOURCE_TYPE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_ORG_QUERY,
                OMModelImages.IMAGE_ORG_QUERY);
        // XPD-5300 Dynamic elements
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_DYNAMIC_ORGANIZATION,
                OMModelImages.IMAGE_DYNAMIC_ORGANISATION);
        TYPE_TO_IMAGE_PATH_MAP
                .put(OMTypeQuery.TYPE_ID_DYNAMIC_ORGANIZATION_TYPE,
                        OMModelImages.IMAGE_DYNAMIC_ORGANISATION_TYPE);
        TYPE_TO_IMAGE_PATH_MAP.put(OMTypeQuery.TYPE_ID_DYNAMIC_ORG_UNIT,
                OMModelImages.IMAGE_DYNAMIC_ORG_UNIT);
    }
}
