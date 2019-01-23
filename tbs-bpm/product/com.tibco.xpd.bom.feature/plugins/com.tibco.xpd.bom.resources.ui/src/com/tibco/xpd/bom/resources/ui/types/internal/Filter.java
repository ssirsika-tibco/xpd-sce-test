/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.types.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.types.BOMTypesFactory;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.types.ITypeFilterProvider;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypeUtil;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * @author rassisi
 * 
 */
@Deprecated
public class Filter implements ITypeFilterProvider {

    // Singleton instance of this class
    private final static Filter INSTANCE = new Filter();

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_project' one of them.
     */
    public static final String ATTRIBUTE_PROJECT = "internal_project"; //$NON-NLS-1$

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_type' one of them.
     */
    public static final String ATTRIBUTE_TYPE = "internal_type"; //$NON-NLS-1$

    private String providerId;

    public static Filter getDefault() {
        return INSTANCE;
    }

    // URL of the base primitive types
    private static final String BASE_TYPES_URL =
            "pathmap://BOM_TYPES/BomPrimitiveTypes.library.uml"; //$NON-NLS-1$

    // Primitive type image URI
    private static final URI IMAGE_URI = URI
            .createPlatformPluginURI(Activator.PLUGIN_ID
                    + "/" + BOMImages.PRIMITIVE_TYPE, true); //$NON-NLS-1$;

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>BOMPickerProviderUtil</code>
     */
    public static Filter getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider#getContent(org
     * .eclipse.core.runtime.IProgressMonitor,
     * com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider.BOMType)
     */
    @Override
    public Set<TypedItem> getContent(IProgressMonitor monitor,
            IResource[] queryResources, TypeInfo queryType,
            Object[] contentToExclude) {
        Set<TypedItem> items = new HashSet<TypedItem>();

        if (queryType.equals(BOMTypesFactory.TYPE_CLASS)) {
            items = getAllClasses(monitor, queryResources);
        } else if (queryType.equals(BOMTypesFactory.TYPE_PRIMITIVE_TYPE)) {
            items = getAllPrimitiveTypes(monitor, queryResources);
        } else if (queryType.equals(BOMTypesFactory.TYPE_BASE_PRIMITIVE_TYPE)) {
            items = getAllBasePrimitives(monitor);
        } else if (queryType.equals(BOMTypesFactory.TYPE_PROFILE)) {
            items = getAllProfiles(queryResources, queryType, monitor);
        } else if (queryType.equals(BOMTypesFactory.TYPE_STEREOTYPE)) {
            items = getAllStereotypes(queryResources, queryType, monitor);
        } else if (queryType.equals(BOMTypesFactory.TYPE_ENUMERATION)) {
            items = getAllEnumerations(monitor, queryResources);
        }

        if (contentToExclude != null) {
            for (Object excludeObject : contentToExclude) {
                items.remove(excludeObject);
            }
        }
        return items;
    }

    /**
     * @param queryResources
     * @param monitor
     * @return
     */
    Set<TypedItem> getAllStereotypes(IResource[] queryResources,
            TypeInfo queryType, IProgressMonitor monitor) {
        Set<TypedItem> result = new HashSet<TypedItem>();

        IProject project = null;

        if (queryResources != null && queryResources.length > 0) {
            if (queryResources[0] instanceof IProject) {
                project = (IProject) queryResources[0];
            }
        }

        IndexerItem[] content;

        content =
                BOMIndexerService.getInstance()
                        .getStereotypesFor((EClass) queryType.getData(),
                                project);

        for (IndexerItem indexerItem : content) {
            TypeInfo typeInfo = BOMTypesFactory.TYPE_STEREOTYPE;
            TypedItem item = new TypedItem(typeInfo);

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
            item.setUriString(indexerItem.getURI());
            item.setImages(new Object[] { indexerItem
                    .get(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI) });
            result.add(item);
        }

        return result;
    }

    @SuppressWarnings("restriction")
    public IndexerItem[] getStereotypesFor(TypeInfo item, IProject project) {
        IndexerItem[] items = null;
        String key = BOMTypesFactory.getKey(item);
        if (key != null) {
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.set(key, BOMIndexerService.VALUE_SET);
            if (project != null) {
                criteria.set(ATTRIBUTE_PROJECT, project.getName());
            }

            items = BOMIndexerService.getInstance().getStereotypes(criteria);
        }

        return items != null ? items : new IndexerItem[0];
    }

    /**
     * @param queryResources
     * @param queryType
     * @param monitor
     * @return
     */
    Set<TypedItem> getAllProfiles(IResource[] queryResources,
            TypeInfo queryType, IProgressMonitor monitor) {
        Set<TypedItem> result = new HashSet<TypedItem>();

        IProject project = null;

        if (queryResources != null && queryResources.length > 0) {
            if (queryResources[0] instanceof IProject) {
                project = (IProject) queryResources[0];
            }
        }

        IndexerItem[] content;

        if (project != null) {
            // Only get profiles from the given project
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.set(ATTRIBUTE_PROJECT, project.getName());
            content = BOMIndexerService.getInstance().getProfiles(criteria);
        } else {
            // Get all profiles from entire workspace
            content = BOMIndexerService.getInstance().getProfiles();
        }

        for (IndexerItem indexerItem : content) {
            TypeInfo typeInfo = BOMTypesFactory.TYPE_PROFILE;
            TypedItem item = new TypedItem(typeInfo);
            item.setQualifiedName(indexerItem.getName());
            item.setUriString(indexerItem.getURI());
            item.setImages(new Object[] { indexerItem
                    .get(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI) });
            result.add(item);
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.resources.ui.picker.IBOMPickerProvider#getItem(org.
     * eclipse.emf.common.util.URI)
     */
    public TypedItem getItem(String uri, String name) {
        TypedItem item = null;
        if (uri != null && name != null) {
            IndexerItemImpl criteria = new IndexerItemImpl();
            Collection<IndexerItem> result =
                    XpdResourcesPlugin.getDefault().getIndexerService()
                            .query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);

            if (result != null && !result.isEmpty()) {
                for (IndexerItem indexerItem : result) {
                    String resultUri = indexerItem.getURI();
                    if (resultUri.equals(uri)) {
                        item = BOMTypesFactory.createItem(indexerItem);
                        break;
                    }
                }
            }

            if (item == null) {
                // Maybe this is a base type
                Set<TypedItem> basePrimitives =
                        getAllBasePrimitives(new NullProgressMonitor());
                if (basePrimitives != null) {
                    for (TypedItem primitive : basePrimitives) {
                        if (primitive.getQualifiedName().equals(name)
                                && primitive.getUriString().equals(uri)) {
                            item = primitive;
                            break;
                        }
                    }
                }
            }
        }
        return item;
    }

    /**
     * Get all classes from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     * 
     */
    @SuppressWarnings("deprecation")
    private Set<TypedItem> getAllClasses(IProgressMonitor monitor) {
        Set<TypedItem> items = new HashSet<TypedItem>();
        if (monitor != null) {
            monitor.beginTask(Messages.BOMPickerProviderUtil_gettingClass_monitor_shortdesc,
                    2);
        }

        HashMap<String, String> criteria = new HashMap<String, String>();
        criteria.put(ATTRIBUTE_TYPE, ResourceItemType.CLASS.toString());
        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);

        if (monitor != null) {
            monitor.worked(1);
        }

        if (result != null) {
            for (IndexerItem indexerItem : result) {
                items.add(BOMTypesFactory.createItem(indexerItem));
            }
        }
        if (monitor != null) {
            monitor.worked(1);
        }
        return items;
    }

    /**
     * Get all classes from the database.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     * 
     */
    @SuppressWarnings("deprecation")
    private Set<TypedItem> getAllClasses(IProgressMonitor monitor,
            IResource[] queryResources) {
        Set<TypedItem> items = new HashSet<TypedItem>();
        if (monitor != null) {
            monitor.beginTask(Messages.BOMPickerProviderUtil_gettingClass_monitor_shortdesc,
                    2);
        }

        HashMap<String, String> criteria = new HashMap<String, String>();
        criteria.put(ATTRIBUTE_TYPE, ResourceItemType.CLASS.toString());
        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);

        if (monitor != null) {
            monitor.worked(1);
        }

        if (queryResources != null) {
            Set<String> fullPaths = new HashSet<String>();
            for (IResource res : queryResources) {
                String fullPath = res.getFullPath().toString();
                if (fullPath != null) {
                    fullPaths.add(fullPath);
                }
            }

            if (result != null) {
                for (IndexerItem indexerItem : result) {

                    // Check if this item is in the same resource diagram

                    TypedItem typedIt = BOMTypesFactory.createItem(indexerItem);
                    String uriString = typedIt.getUriString();

                    if (uriString != null) {
                        URI uri = URI.createURI(uriString);
                        if (uri != null) {
                            String platformStr = uri.toPlatformString(true);

                            if (platformStr != null
                                    && fullPaths.contains(platformStr)) {
                                items.add(typedIt);

                            }
                        }
                    }
                }
            }
        } else {
            // Add them all
            if (result != null) {
                for (IndexerItem indexerItem : result) {
                    items.add(BOMTypesFactory.createItem(indexerItem));
                }
            }
        }

        if (monitor != null) {
            monitor.worked(1);
        }
        return items;
    }

    /**
     * Get all primitive types from the database.
     * 
     * @param monitor
     *            progress monitor.
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     */
    @SuppressWarnings("deprecation")
    private Set<TypedItem> getAllEnumerations(IProgressMonitor monitor,
            IResource[] queryResources) {
        Set<TypedItem> items = new HashSet<TypedItem>();

        monitor.beginTask(Messages.BOMPickerProviderUtil_gettingPrimType_monitor_shortdesc,
                2);

        HashMap<String, String> criteria = new HashMap<String, String>();
        criteria.put(ATTRIBUTE_TYPE, ResourceItemType.ENUMERATION.toString());
        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);

        monitor.worked(1);

        if (queryResources != null) {
            Set<String> fullPaths = new HashSet<String>();
            for (IResource res : queryResources) {
                String fullPath = res.getFullPath().toString();
                if (fullPath != null) {
                    fullPaths.add(fullPath);
                }
            }

            if (result != null) {
                for (IndexerItem indexerItem : result) {

                    // Check if this item is in the same resource diagram

                    TypedItem typedIt = BOMTypesFactory.createItem(indexerItem);
                    String uriString = typedIt.getUriString();

                    if (uriString != null) {
                        URI uri = URI.createURI(uriString);
                        if (uri != null) {
                            String platformStr = uri.toPlatformString(true);

                            if (platformStr != null
                                    && fullPaths.contains(platformStr)) {
                                items.add(typedIt);

                            }
                        }
                    }
                }
            }
        } else {
            // Add them all
            if (result != null) {
                for (IndexerItem indexerItem : result) {
                    items.add(BOMTypesFactory.createItem(indexerItem));
                }
            }
        }

        monitor.worked(1);

        return items;
    }

    /**
     * Get all primitive types from the database.
     * 
     * @param monitor
     *            progress monitor.
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     */
    private Set<TypedItem> getAllPrimitiveTypes(IProgressMonitor monitor,
            IResource[] queryResources) {
        Set<TypedItem> items = new HashSet<TypedItem>();

        monitor.beginTask(Messages.BOMPickerProviderUtil_gettingPrimType_monitor_shortdesc,
                2);

        HashMap<String, String> criteria = new HashMap<String, String>();
        criteria.put(ATTRIBUTE_TYPE, ResourceItemType.PRIMITIVE_TYPE.toString());
        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);

        monitor.worked(1);

        if (queryResources != null) {
            Set<String> fullPaths = new HashSet<String>();
            for (IResource res : queryResources) {
                String fullPath = res.getFullPath().toString();
                if (fullPath != null) {
                    fullPaths.add(fullPath);
                }
            }

            if (result != null) {
                for (IndexerItem indexerItem : result) {

                    // Check if this item is in the same resource diagram

                    TypedItem typedIt = BOMTypesFactory.createItem(indexerItem);
                    String uriString = typedIt.getUriString();

                    if (uriString != null) {
                        URI uri = URI.createURI(uriString);
                        if (uri != null) {
                            String platformStr = uri.toPlatformString(true);

                            if (platformStr != null
                                    && fullPaths.contains(platformStr)) {
                                items.add(typedIt);

                            }
                        }
                    }
                }
            }
        } else {
            // Add them all
            if (result != null) {
                for (IndexerItem indexerItem : result) {
                    items.add(BOMTypesFactory.createItem(indexerItem));
                }
            }
        }

        monitor.worked(1);

        return items;
    }

    /**
     * Get all base primitive types.
     * 
     * @param monitor
     *            progress monitor.
     * @return set of <code>TypedItem</code> objects. Empty set if none found.
     */
    private Set<TypedItem> getAllBasePrimitives(IProgressMonitor monitor) {

        Set<TypedItem> items = new HashSet<TypedItem>();
        EditingDomain ed = XpdResourcesPlugin.getDefault().getEditingDomain();

        if (ed != null) {
            monitor.beginTask(Messages.BOMPickerProviderUtil_gettingBasePrimType_monitor_shortdesc,
                    1);
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
                                items.add(getPickerItemFor((PrimitiveType) next));
                            }
                        }
                    }
                }
            }

            monitor.worked(1);
        }
        return items;
    }

    /**
     * Convert the given <code>PrimitiveType</code> to the picker item type.
     * 
     * @param type
     *            <code>PrimitiveType</code>
     * @return <code>TypedItem</code>
     */
    private TypedItem getPickerItemFor(PrimitiveType type) {

        // Make sure to pick up the localised name
        String qualifiedName = UML2ModelUtil.getQualifiedLabel(type);

        if (qualifiedName == null) {
            qualifiedName = type.getLabel();
        }

        // Convert UML delimiter to JAVA style delimiter
        qualifiedName = BOMWorkingCopy.getQualifiedName(qualifiedName);
        TypeInfo typeInfo = BOMTypesFactory.getType(type);
        TypedItem result = new TypedItem(typeInfo);
        result.setQualifiedName(qualifiedName);
        result.setImages(new String[] { IMAGE_URI.toString() });
        result.setUriString(getURI(type));
        result.setName(type.getLabel());
        return result;
    }

    public static String getURI(EObject modelElement) {
        Resource modelElementResource = modelElement.eResource();
        URI uri =
                modelElementResource
                        .getURI()
                        .appendFragment(modelElementResource.getURIFragment(modelElement));
        String uriToString = uri.toString();
        return uriToString;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.types.ITypeFilterProvider#matchItem(java.lang
     * .Object)
     */
    @Override
    public boolean matchItem(Object item) {
        Set<TypeInfo> typeInfos = TypeUtil.getRegisteredTypes(providerId);
        TypeInfo typeInfo = BOMTypesFactory.getType(item);
        Iterator<TypeInfo> it = typeInfos.iterator();
        while (it.hasNext()) {
            if (it.equals(typeInfo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId
     */
    @Override
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
