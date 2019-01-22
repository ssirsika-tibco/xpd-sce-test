/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.commonpicker;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Link;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;
import com.tibco.xpd.resources.ui.picker.PickerContentExtension;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.picker.PickerItemImpl;

/**
 * Provides picker items for BOM specific types. Type constants are specified in
 * {@link Link BOMTypeQuery}.
 * 
 * @see BOMTypeQuery
 * @author Jan Arciuchiewicz
 */
public class BOMPickerItemProvider extends BasePickerItemProvider {
    /** URL of the base primitive types. */
    private static final String BASE_TYPES_URL =
            "pathmap://BOM_TYPES/BomPrimitiveTypes.library.uml"; //$NON-NLS-1$

    @Override
    public String getIndexerId() {
        return BOMResourcesPlugin.BOM_INDEXER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<PickerItem> getItemsForType(
            PickerContentExtension pickerExtension, Object context, String type) {

        if (Arrays.asList(BOMTypeQuery.PACKAGE_TYPE,
                BOMTypeQuery.CLASS_TYPE,
                BOMTypeQuery.CASE_CLASS_TYPE,
                BOMTypeQuery.GLOBAL_CLASS_TYPE,
                BOMTypeQuery.PRIMITIVE_TYPE,
                BOMTypeQuery.ENUMERATION_TYPE,
                BOMTypeQuery.ASSOC_CLASS_TYPE).contains(type)) {
            // main index types
            return getItemsForTypeFromIndexer(getIndexerId(),
                    pickerExtension,
                    type);
        } else if (type.equals(BOMTypeQuery.BASE_PRIMITIVE_TYPE)) {
            return getAllBasePrimitives(pickerExtension, type);
        } else if (type.equals(BOMTypeQuery.PROFILE_TYPE)) {
            return getAllProfiles(pickerExtension, context, type);
        } else if (type.equals(BOMTypeQuery.STEREOTYPE_TYPE)) {
            return getAllStereotypes(pickerExtension, context, type);
        }
        return Collections.emptyList();

    }

    /**
     * @see com.tibco.xpd.resources.ui.picker.BasePickerItemProvider#getItemsForTypeFromIndexer(java.lang.String,
     *      com.tibco.xpd.resources.ui.picker.PickerContentExtension,
     *      java.lang.String)
     * 
     * @param indexerId
     * @param pickerExtension
     * @param type
     * @return
     */
    @Override
    protected Collection<PickerItem> getItemsForTypeFromIndexer(
            String indexerId, PickerContentExtension pickerExtension,
            String type) {

        /* For Case Reference types the picker must list only the case classes */
        if (ResourceItemType.CASE_CLASS.toString().equals(type)) {

            Set<PickerItem> items = new HashSet<PickerItem>();
            IndexerItemImpl queryItem = new IndexerItemImpl();
            queryItem
                    .set(BOMResourcesPlugin.INDEXER_ATTRIBUTE_CASE_OR_GLOBAL_TYPE,
                            ResourceItemType.CASE_CLASS.toString());
            Collection<IndexerItem> result =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(indexerId, queryItem);

            for (IndexerItem indexerItem : result) {
                items.add(createPickerItem(indexerItem, pickerExtension));
            }
            return items;
        }
        return super.getItemsForTypeFromIndexer(indexerId,
                pickerExtension,
                type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PickerItem createPickerItem(IndexerItem indexerItem,
            PickerContentExtension pickerExtension) {
        PickerItemImpl pickerItem =
                new PickerItemImpl(indexerItem, pickerExtension);
        String qualifiedName = pickerItem.getName();
        pickerItem.setName(getBOMItemName(qualifiedName));
        pickerItem.setQualifiedName(qualifiedName);
        return pickerItem;
    }

    /**
     * Gets item name from qualified name.
     */
    protected String getBOMItemName(String qualifiedName) {
        String name = null;
        if (qualifiedName != null) {
            int idx =
                    qualifiedName
                            .lastIndexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR);

            if (idx >= 0) {
                name = qualifiedName.substring(idx + 1);
            } else {
                name = qualifiedName;
            }
        }
        return name;
    }

    /**
     * Get all base primitive types.
     * 
     * @param monitor
     *            progress monitor.
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     */
    private Set<PickerItem> getAllBasePrimitives(
            PickerContentExtension pickerExtension, String type) {

        Set<PickerItem> items = new HashSet<PickerItem>();
        EditingDomain ed = XpdResourcesPlugin.getDefault().getEditingDomain();

        if (ed != null) {
            Resource resource = ed.loadResource(BASE_TYPES_URL);
            if (resource != null) {
                EList<EObject> contents = resource.getContents();
                for (EObject content : contents) {
                    if (content instanceof Model) {
                        TreeIterator<Object> iterator =
                                EcoreUtil.getAllProperContents(content, false);

                        while (iterator.hasNext()) {
                            Object next = iterator.next();

                            if (next instanceof PrimitiveType) {
                                items.add(getPickerItemFor((PrimitiveType) next,
                                        pickerExtension,
                                        type));
                            }
                        }
                    }
                }
            }
        }
        return items;
    }

    /**
     * Convert the given <code>PrimitiveType</code> to the picker item type.
     * 
     * @param primitiveType
     *            <code>PrimitiveType</code>
     * @return <code>TypedItem</code>
     */
    private PickerItem getPickerItemFor(PrimitiveType primitiveType,
            PickerContentExtension pickerExtension, String type) {

        // Make sure to pick up the localised name
        String qualifiedName = UML2ModelUtil.getQualifiedLabel(primitiveType);

        if (qualifiedName == null) {
            qualifiedName = primitiveType.getLabel();
        }

        // Convert UML delimiter to JAVA style delimiter
        qualifiedName = BOMWorkingCopy.getQualifiedName(qualifiedName);
        PickerItemImpl item =
                new PickerItemImpl(primitiveType.getLabel(),
                        BOMTypeQuery.BASE_PRIMITIVE_TYPE,
                        getURI(primitiveType), new HashMap<String, String>(),
                        pickerExtension);
        item.setQualifiedName(qualifiedName);
        item.set(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI,
                URI.createPlatformPluginURI(Activator.PLUGIN_ID + '/'
                        + BOMImages.PRIMITIVE_TYPE,
                        true).toString());

        return item;
    }

    /**
     * Gets all profiles from indexer.
     */
    private Set<PickerItem> getAllProfiles(
            PickerContentExtension pickerExtension, Object context, String type) {
        Set<PickerItem> result = new HashSet<PickerItem>();
        IndexerItem[] content = BOMIndexerService.getInstance().getProfiles();
        for (IndexerItem indexerItem : content) {
            PickerItemImpl item =
                    new PickerItemImpl(indexerItem, pickerExtension);
            item.setType(type);
            result.add(item);
        }
        return result;
    }

    /**
     * Gets all stereotypes form indexer.
     */
    private Set<PickerItem> getAllStereotypes(
            PickerContentExtension pickerExtension, Object context, String type) {
        Set<PickerItem> result = new HashSet<PickerItem>();
        IndexerItem[] content =
                BOMIndexerService.getInstance()
                        .getStereotypesFor((EClass) context, null);

        for (IndexerItem indexerItem : content) {
            PickerItemImpl item =
                    new PickerItemImpl(indexerItem, pickerExtension);
            item.setType(type);
            String qualifiedName = indexerItem.getName();
            String name = qualifiedName;
            int pos =
                    qualifiedName
                            .lastIndexOf(BOMWorkingCopy.UML_PACKAGE_SEPARATOR);
            if (pos != -1) {
                name =
                        qualifiedName
                                .substring(pos
                                        + BOMWorkingCopy.UML_PACKAGE_SEPARATOR
                                                .length());
                qualifiedName = qualifiedName.substring(0, pos);
            }
            item.setQualifiedName(qualifiedName);
            item.setName(name);
            result.add(item);
        }

        return result;
    }
}
